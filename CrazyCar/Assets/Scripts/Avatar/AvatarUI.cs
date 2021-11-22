﻿using LitJson;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using TinyMessenger;
using UnityEngine;
using UnityEngine.UI;
using Utils;
using TFramework;

public class AvatarUI : MonoBehaviour, IController {
    public Image curAvatar;
    public Text curAvatarName;
    public Button applyBtn;
    public AvatarItem avatarItem;
    public Transform avatarItemParent;
    public Button closeBtn;

    private TinyMessageSubscriptionToken avatarToken;
    private int curAid = 0;

    private void OnEnable() {
        StartCoroutine(Util.POSTHTTP(url: NetworkController.manager.HttpBaseUrl + RequestUrl.avatarUrl,
        token: GameController.manager.token,
        succData: (data) => {
            GameController.manager.avatarManager.ParseAvatarRes(data, UpdataUI);
            curAid = this.GetModel<IUserModel>().Aid.Value;
        }));
    }

    private void UpdataUI() {
        curAvatar.sprite = this.GetSystem<IResourceSystem>().GetAvatarResource(GameController.manager.avatarManager.curAid);
        curAvatarName.text = GameController.manager.avatarManager.avatarDic[GameController.manager.avatarManager.curAid].name;
        Util.DeleteChildren(avatarItemParent);
        foreach (var kvp in GameController.manager.avatarManager.avatarDic) {
            AvatarItem item = Instantiate(avatarItem);
            item.transform.SetParent(avatarItemParent, false);
            item.SetContent(kvp.Value);
        }
    }

    private void Start() {
        applyBtn.onClick.AddListener(() => {
            StringBuilder sb = new StringBuilder();
            JsonWriter w = new JsonWriter(sb);
            w.WriteObjectStart();
            w.WritePropertyName("aid");
            w.Write(curAid);
            w.WriteObjectEnd();
            Debug.Log("++++++ " + sb.ToString());
            byte[] bytes = Encoding.UTF8.GetBytes(sb.ToString());
            StartCoroutine(Util.POSTHTTP(url: NetworkController.manager.HttpBaseUrl + RequestUrl.changeAvatarUrl,
                data: bytes, token: GameController.manager.token,
                succData: (data) => {
                    this.GetModel<IUserModel>().Aid.Value = (int)data["aid"];
                    GameController.manager.warningAlert.ShowWithText(I18N.manager.GetText("Successfully Set"));
                },
                code: (code) => {
                    if (code == 423) {
                        GameController.manager.warningAlert.ShowWithText(I18N.manager.GetText("Did not have"));
                    }
                }));
        });

        closeBtn.onClick.AddListener(() => {
            Util.DelayExecuteWithSecond(Util.btnASTime, () => {
                this.SendCommand(new HidePageCommand(UIPageType.AvatarUI));
                this.SendCommand<UpdateHomepageUICommand>();
            });
        });

        avatarToken = GameController.manager.tinyMsgHub.Subscribe<AvatarUIMessage>((data) => {
            curAvatar.sprite = this.GetSystem<IResourceSystem>().GetAvatarResource(data.aid);
            curAvatarName.text = GameController.manager.avatarManager.avatarDic[data.aid].name;
            curAid = data.aid;
        });
    }

    private void OnDestroy() {
        GameController.manager.tinyMsgHub.Unsubscribe(avatarToken);
    }

    public IArchitecture GetArchitecture() {
        return CrazyCar.Interface;
    }
}
