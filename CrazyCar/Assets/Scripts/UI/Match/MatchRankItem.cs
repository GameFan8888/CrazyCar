﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using QFramework;
using UnityEngine.ResourceManagement.AsyncOperations;

public class MatchRankItem : MonoBehaviour, IController {
    public Text nameText;
    public Image avatarImage;
    public Text completeTimeText;
    public Text rankText;

    public IArchitecture GetArchitecture() {
        return CrazyCar.Interface;
    }

    public void SetContent(MatchRankInfo info) {
        nameText.text = info.name;
        this.GetSystem<IAddressableSystem>().GetAvatarResource(info.aid, (obj) => {
            if (obj.Status == AsyncOperationStatus.Succeeded) {
                avatarImage.sprite = Instantiate(obj.Result, transform, false);
            }
        });
        completeTimeText.text = info.completeTime.ToString();
        rankText.text = info.rank.ToString();
    }
}
