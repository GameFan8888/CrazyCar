﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using QFramework;

public static class RequestUrl {
    public static string forcedUpdatingUrl = "v1/ForcedUpdating";
    public static string addressableUrl = "Addressable";
    public static string loginUrl = "v1/Login";
    public static string registerUrl = "v1/Register";
    public static string avatarUrl = "v2/Avatar";
    public static string changeAvatarUrl = "v2/ChangeAvatar";
    public static string buyAvatarUrl = "v2/BuyAvatar";
    public static string resourceUrl = "Resource";
    public static string timeTrialDetailUrl = "v2/TimeTrialDetail";
    public static string buyTimeTrialClassUrl = "v2/BuyTimeTrialClass";
    public static string timeTrialResultUrl = "v2/TimeTrialResult";
    public static string timeTrialRankUrl = "v2/TimeTrialRank";
    public static string modifyPersonalInfoUrl = "v2/ModifyPersonalInfo";
    public static string equipUrl = "v2/Equip";
    public static string buyEquipUrl = "v2/BuyEquip";
    public static string changeEquipUrl = "v2/ChangeEquip";
    public static string matchMapUrl = "v2/MatchMap";
    public static string matchResultUrl = "v2/MatchResult";
    public static string kcpServerUrl = "v2/KCPRttServer";
    public static string enterRoomUrl = "v2/EnterRoom";
    public static string getUserInfo = "v2/GetUserInfo";

    public static string aiUrl = "AI";
}

public static class PrefKeys {
    public const string lastLogNid = "lastLogNid";
    public const string userName = "userName";
    public const string password = "password";
    public const string rememberPassword = "rememberPassword";
    public const string isCompleteGuidance = "isCompleteGuidance";
}

public static class LocalUrl {
    public static string avatarUrl = "Avatar/";
}
