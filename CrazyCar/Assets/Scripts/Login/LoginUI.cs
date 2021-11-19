﻿using LitJson;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using UnityEngine;
using UnityEngine.UI;
using Utils;
using TFramework;

public class LoginUI : MonoBehaviour, IController {
    public InputField userNameInput;
    public InputField passwordInput;
    public Toggle rememberToggle; 
    public Button loginBtn;
    public Button registerBtn;

    public IArchitecture GetArchitecture() {
        return CrazyCar.Interface;
    }

    private void Start() {
        rememberToggle.isOn = PlayerPrefs.GetInt(PrefKeys.rememberPassword.ToString()) == 1;
        if (rememberToggle.isOn) {
            userNameInput.text = PlayerPrefs.GetString(PrefKeys.userName);
            passwordInput.text = PlayerPrefs.GetString(PrefKeys.password);
        } 

        loginBtn.onClick.AddListener(() => {
            if (userNameInput.text == "" || passwordInput.text == "") {
                GameController.manager.warningAlert.ShowWithText(I18N.manager.GetText("Please enter the content"));
                return;
            }

            StringBuilder sb = new StringBuilder();
            JsonWriter w = new JsonWriter(sb);
            w.WriteObjectStart();
            w.WritePropertyName("UserName");
            w.Write(userNameInput.text);
            w.WritePropertyName("Password");
            w.Write(passwordInput.text);
            w.WriteObjectEnd();
            Debug.Log("++++++ " + sb.ToString());
            byte[] bytes = Encoding.UTF8.GetBytes(sb.ToString());
            StartCoroutine(Util.POSTHTTP(url : NetworkController.manager.HttpBaseUrl + RequestUrl.loginUrl,
                data : bytes, succData : (data) => {
                    GameController.manager.token = (string)data["token"];
                    GameController.manager.userInfo = this.GetModel<IPlayerInfoModel>().ParsePlayerInfoData(data);
                }, code : (code) => {
                    if (code == 200) {
                        Util.DelayExecuteWithSecond(Util.btnASTime, () => {
                            if (userNameInput.text.ToLower() == "tast") {
                                GameController.manager.gameHelper.gameObject.SetActiveFast(true);
                            }
                            GameController.manager.warningAlert.ShowWithText(text: I18N.manager.GetText("Login Success"), callback: () => {
                                PlayerPrefs.SetString(PrefKeys.userName, userNameInput.text);
                                PlayerPrefs.SetString(PrefKeys.password, passwordInput.text);
                                PlayerPrefs.SetInt(PrefKeys.rememberPassword.ToString(), (rememberToggle.isOn ? 1 : 0));
                                Util.LoadingScene(SceneID.Index);
                            });
                        });
                       
                    } else if (code == 423) {
                        GameController.manager.warningAlert.ShowWithText(I18N.manager.GetText("Password Error"));
                    } else if (code == 404) {
                        GameController.manager.warningAlert.ShowWithText(I18N.manager.GetText("User not registered"));
                    } else {
                        GameController.manager.warningAlert.ShowWithText(I18N.manager.GetText("Unknown Error"));
                    }
                }));
        });

        registerBtn.onClick.AddListener(() => {
            GameController.manager.tinyMsgHub.Publish(new RegisterUIMessage());
            Util.DelayExecuteWithSecond(Util.btnASTime, () => {
                gameObject.SetActiveFast(false);
            });
        });
    }
}
