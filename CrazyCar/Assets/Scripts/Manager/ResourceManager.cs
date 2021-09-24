﻿using LitJson;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.Networking;
using Utils;

public enum ResourceType {
    None = 0,
    Loaded,
    ToDownload
}

public class ResourceManager {
    public static AssetBundle avatar;
    public static AssetBundle equip;
    public ResourceType curResourceType = ResourceType.None;
    public delegate void ProgressCallback(float value, float totalBytes, bool isDownloading);

    private string avatarHash = "";
    private uint avatarCRC = 0;
    private string avatarURL = "";
    private float avatarSize = 1000;
    private string localAvatarString = Application.streamingAssetsPath + "/avatar";

    private string equipHash = "";
    private uint equipCRC = 0;
    private string equipURL = "";
    private float equipSize = 1000;
    private string localEquipString = Application.streamingAssetsPath + "/equip";

    public void CheckNewResource() {
        //Debug.Log("CheckNewResource");
#if UNITY_EDITOR
        if (avatar == null) {
            avatar = AssetBundle.LoadFromFile(localAvatarString);
        }
        if (equip == null) {
            equip = AssetBundle.LoadFromFile(localEquipString);
        }
        curResourceType = ResourceType.Loaded;
        return;
#else
        CheckNew();
#endif
    }

    private void CheckNew() {
        if (avatar != null && equip != null) {
            curResourceType = ResourceType.Loaded;
        } else {
            CheckCoroutine();
        }
    }

    private void CheckCoroutine() {
        Debug.Log("CheckCoroutine......");

        GameController.manager.StartCoroutine(Util.POSTHTTP(url: NetworkController.manager.HttpBaseUrl +
            RequestUrl.resourceUrl,
            succData: (data) => {
                Debug.Log(data.ToJson());
                avatarHash = (string)data["avatar"]["hash"];
                avatarCRC = uint.Parse((string)data["avatar"]["crc"]);
                string avatarStr = (string)data["avatar"]["url"];
                if (avatarStr.Contains("http")) {
                    avatarURL = avatarStr;
                } else {
                    avatarURL = NetworkController.manager.HttpBaseUrl + (string)data["avatar"]["url"];
                }
                avatarURL = NetworkController.manager.HttpBaseUrl + (string)data["avatar"]["url"];
                avatarSize = float.Parse((string)data["avatar"]["size"]);

                equipHash = (string)data["equip"]["hash"];
                equipCRC = uint.Parse((string)data["equip"]["crc"]);
                string equipStr = (string)data["equip"]["url"];
                if (equipStr.Contains("http")) {
                    equipURL = equipStr;
                } else {
                    equipURL = NetworkController.manager.HttpBaseUrl + (string)data["equip"]["url"];
                }          
                equipSize = float.Parse((string)data["equip"]["size"]);

                GetLocalResource();
            },
            code : (code) => {
                if (code != 200) {
                    string content = "资源下载失败";
                    GameController.manager.warningAlert.ShowWithText(
                        text: content,
                        callback: () => {
                            Application.Quit();
                        });
                }
            }));
    }

    private void GetLocalResource() {
        GameController.manager.StartCoroutine(ParseLocalResource());
    }

    private IEnumerator ParseLocalResource() {
        string localManiFest = Application.streamingAssetsPath + "/config.json";
#if UNITY_ANDROID
        WWW www = new WWW(localManiFest);
        yield return www;
        string data = www.text;
        if (data != null && data != "") 
        {
            JsonData maniData = JsonMapper.ToObject(data);
#else
        if (File.Exists(localManiFest)) {
            JsonData maniData = JsonMapper.ToObject(File.ReadAllText(localManiFest));
#endif
            Debug.Log(maniData.ToJson());
            string localAvatarHash = (string)maniData["avatar"];
            string localEquipHash = (string)maniData["equip"];

            Debug.Log("++++++ remote avatarHash = " + avatarHash + "   localAvatarHash = " + localAvatarHash);
            Debug.Log("++++++ remote equipHash = " + equipHash + "   localEquipHash = " + localEquipHash);
            if (localAvatarHash == avatarHash) {
                avatar = AssetBundle.LoadFromFile(localAvatarString);
            }
            if (localEquipHash == equipHash) {
                equip = AssetBundle.LoadFromFile(localEquipString);
            }
        }

        // 下载线上之后，会缓存到一个文件夹，不会替换本地文件
        //Caching.ClearCache();
        if (!Caching.IsVersionCached(avatarURL, Hash128.Parse(avatarHash)) && avatar == null ||
            (!Caching.IsVersionCached(equipURL, Hash128.Parse(equipHash)) && equip == null)) {
            curResourceType = ResourceType.ToDownload;
        } else {
            curResourceType = ResourceType.Loaded;
        }
        yield return null;
    }

    public void DownloadAssets(Util.NoneParamFunction success, ProgressCallback progressCallback, Util.NoneParamFunction fail) {
        GameController.manager.StartCoroutine(Download(success, progressCallback, fail));
    }

    private IEnumerator Download(Util.NoneParamFunction success, ProgressCallback progressCallback, Util.NoneParamFunction fail) {
        if (avatar == null) {
            Debug.Log("Try to Load res From Web");
            float lastProgress = -1;
            var _req = UnityWebRequestAssetBundle.GetAssetBundle(avatarURL, Hash128.Parse(avatarHash), avatarCRC);
            _req.SendWebRequest();
            long lastTime = Util.GetTime();
            Debug.Log("dowload before");
            while (!_req.isDone) {
                if (Mathf.Approximately(lastProgress, _req.downloadProgress)) {
                    if (Util.GetTime() - lastTime > 100 * 1000) {
                        //fail
                        fail?.Invoke();
                        yield break;
                    } else {
                    }
                } else {
                    lastProgress = _req.downloadProgress;
                    lastTime = Util.GetTime();
                }

                try {
                    progressCallback(_req.downloadProgress, avatarSize * 1024 * 1024, true);
                } catch {
                    Debug.LogError("bundleError");
                }

                yield return null;
            }

            Debug.Log("dowload avatar finish");
            try {
                avatar = DownloadHandlerAssetBundle.GetContent(_req);
            } catch (System.Exception e) {
                Debug.Log(e.ToString());
            }

            Debug.Log("get bundle finish");
            if (avatar == null) {
                fail?.Invoke();
                yield break;
            }
        }

        if (equip == null) {
            Debug.Log("Try to Load res From Web");
            float lastProgress = -1;
            var _req = UnityWebRequestAssetBundle.GetAssetBundle(equipURL, Hash128.Parse(equipHash), equipCRC);
            _req.SendWebRequest();
            long lastTime = Util.GetTime();
            Debug.Log("dowload before");
            while (!_req.isDone) {
                if (Mathf.Approximately(lastProgress, _req.downloadProgress)) {
                    if (Util.GetTime() - lastTime > 10 * 1000) {
                        //fail
                        fail?.Invoke();
                        yield break;
                    } else {
                    }
                } else {
                    lastProgress = _req.downloadProgress;
                    lastTime = Util.GetTime();
                }

                try {
                    progressCallback(_req.downloadProgress, equipSize * 1024 * 1024, true);
                } catch {
                    Debug.LogError("bundleError");
                }

                yield return null;
            }

            Debug.Log("dowload equip finish");
            try {
                equip = DownloadHandlerAssetBundle.GetContent(_req);
            } catch (System.Exception e) {
                Debug.Log(e.ToString());
            }

            Debug.Log("get bundle finish");
            if (equip == null) {
                fail?.Invoke();
                yield break;
            }
        }

        success?.Invoke();
    }

    public Sprite GetAvatarResource(int aid) {
        Sprite resultSprite;
        try {
#if !UNITY_EDITOR
            Debug.Log("+++ " + "Assets/AB/Avatar/" + aid + ".png");
            resultSprite = avatar.LoadAsset<Sprite>("Assets/AB/Avatar/" + aid + ".png");
            if (resultSprite == null) {
                return null;
            }
#else
            resultSprite = UnityEditor.AssetDatabase.LoadAssetAtPath<Sprite>(
                "Assets/AB/Avatar/" + aid.ToString() + ".png");

#endif
            return resultSprite;
        } catch (Exception e) {
            Debug.LogError(e);
            return null;
        }
    }

    public EquipResource GetCarResource(string rid) {
        try {
            rid = rid.Trim();
            if (GameController.manager.equipManager.equipResource.ContainsKey(rid)) {
                return GameController.manager.equipManager.equipResource[rid];
            }
#if !UNITY_EDITOR
            var o = equip.LoadAsset<GameObject>("Assets/AB/Equip/Car/Items/" + rid + ".prefab");
			if(o == null) {
				return null;
			}
			var e = o.GetComponent<EquipResource>();
#else
            var e = UnityEditor.AssetDatabase.LoadAssetAtPath<EquipResource>(
                "Assets/AB/Equip/Car/Items/" + rid + ".prefab");

#endif
            GameController.manager.equipManager.equipResource[rid] = e;
            return e;
        } catch (Exception e) {
            Debug.LogError(e);
            return null;
        }
    }
}
