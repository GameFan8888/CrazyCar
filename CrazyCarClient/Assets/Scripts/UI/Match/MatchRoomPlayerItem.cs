using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using QFramework;
using UnityEngine.ResourceManagement.AsyncOperations;
using Utils;

public class MatchRoomPlayerItem : MonoBehaviour, IController {
    public Text nickName;
    public GameObject defaultImage;
    public Image avatarImage;

    public void SetContent(MatchRoomMemberInfo info) {
        nickName.text = info.memberName;
        defaultImage.gameObject.SetActiveFast(false);
        avatarImage.gameObject.SetActiveFast(true);
        
        this.GetSystem<IAddressableSystem>().LoadAsset<Sprite>(Util.GetAvatarUrl(info.aid), (obj) => {
            if (obj.Status == AsyncOperationStatus.Succeeded) {
                avatarImage.sprite = Instantiate(obj.Result, transform, false);
            }
        });
    }

    public void CleanItem() {
        nickName.text = this.GetSystem<II18NSystem>().GetText("Waiting...");
        defaultImage.gameObject.SetActiveFast(true);
        avatarImage.gameObject.SetActiveFast(false);
    }

    public IArchitecture GetArchitecture() {
        return CrazyCar.Interface;
    }
}
