using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using QFramework;
using System.Text;
using LitJson;
using Utils;

public interface IMatchRoomSystem : ISystem {
    void MatchRoomCreate();
    void MatchRoomJoin();
    void MatchRoomStatus();
    void MatchRoomEixt();
    void MatchRoomStart();
    void OnCreateMsg(JsonData recJD);
    void OnJoinMsg(JsonData recJD);
    void OnStatusMsg(JsonData recJD);
    void OnExitMsg(JsonData recJD);
    void OnStartMsg(JsonData recJD);
}

public class MatchRoomSystem : AbstractSystem, IMatchRoomSystem {
    private void MatchRoomConnect() {
        string ws = "ws" + this.GetSystem<INetworkSystem>().HttpBaseUrl.Substring(4) +
            "websocket/MatchRoomWebSocket/" +
            this.GetModel<IUserModel>().Uid.Value + "," + this.GetModel<IMatchModel>().RoomId;

        this.GetSystem<IWebSocketSystem>().Connect(ws);
    }

    public void MatchRoomCreate() {
        MatchRoomConnect();
        StringBuilder sb = new StringBuilder();
        JsonWriter w = new JsonWriter(sb);
        w.WriteObjectStart();
        w.WritePropertyName("msg_type");
        w.Write((int)MsgType.MatchRoomCreate);   
        w.WritePropertyName("timestamp");
        w.Write(Util.GetTime());
        w.WritePropertyName("room_id");
        w.Write(this.GetModel<IMatchModel>().RoomId);
        w.WritePropertyName("uid");
        w.Write(this.GetModel<IUserModel>().Uid);
        w.WriteObjectEnd();
        Debug.LogError("MatchRoomCreate : " + sb.ToString());
        this.GetSystem<IWebSocketSystem>().SendMsgToServer(sb.ToString());
    }

    public void MatchRoomEixt() {
        this.GetSystem<IWebSocketSystem>().CloseConnect();
    }

    public void MatchRoomJoin() {
        MatchRoomConnect();
        StringBuilder sb = new StringBuilder();
        JsonWriter w = new JsonWriter(sb);
        w.WriteObjectStart();
        w.WritePropertyName("msg_type");
        w.Write((int)MsgType.MatchRoomCreate);
        w.WritePropertyName("timestamp");
        w.Write(Util.GetTime());
        w.WritePropertyName("room_id");
        w.Write(this.GetModel<IMatchModel>().RoomId);
        w.WritePropertyName("uid");
        w.Write(this.GetModel<IUserModel>().Uid);
        w.WriteObjectEnd();
        Debug.LogError("MatchRoomJoin : " + sb.ToString());
        this.GetSystem<IWebSocketSystem>().SendMsgToServer(sb.ToString());
    }

    public void MatchRoomStatus() {
        StringBuilder sb = new StringBuilder();
        JsonWriter w = new JsonWriter(sb);
        w.WriteObjectStart();
        w.WritePropertyName("msg_type");
        w.Write((int)MsgType.MatchRoomStatus);
        w.WritePropertyName("timestamp");
        w.Write(Util.GetTime());
        w.WritePropertyName("room_id");
        w.Write(this.GetModel<IMatchModel>().RoomId);
        w.WritePropertyName("uid");
        w.Write(this.GetModel<IUserModel>().Uid);
        w.WriteObjectEnd();
        Debug.LogError("MatchRoomStatus : " + sb.ToString());
        this.GetSystem<IWebSocketSystem>().SendMsgToServer(sb.ToString());
    }

    public void MatchRoomStart() {
        CoroutineController.manager.StartCoroutine(this.GetSystem<INetworkSystem>().POSTHTTP(url: this.GetSystem<INetworkSystem>().HttpBaseUrl +
                  RequestUrl.createMatchUrl,
                  token: this.GetModel<IGameControllerModel>().Token.Value,
                  succData: (data) => {
                      int cid = (int)data["cid"];
                      StringBuilder sb = new StringBuilder();
                      JsonWriter w = new JsonWriter(sb);
                      w.WriteObjectStart();
                      w.WritePropertyName("msg_type");
                      w.Write((int)MsgType.MatchRoomStart);
                      w.WritePropertyName("timestamp");
                      w.Write(Util.GetTime());
                      w.WritePropertyName("room_id");
                      w.Write(this.GetModel<IMatchModel>().RoomId);
                      w.WritePropertyName("uid");
                      w.Write(this.GetModel<IUserModel>().Uid);
                      w.WritePropertyName("cid");
                      w.Write(cid);
                      w.WriteObjectEnd();
                      Debug.LogError("MatchRoomStart : " + sb.ToString());
                      this.GetSystem<IWebSocketSystem>().SendMsgToServer(sb.ToString());
                  },
                  code: (code) => {

                  }
              ));
    }

    public void OnCreateMsg(JsonData recJD) {
        int code = (int)recJD["code"];
        Debug.LogError("OnCreateMsg = " + code);
        if (code == 200) {
            this.SendEvent<MatchRoomCreateOrJoinSuccEvent>();
        } else if (code == 421) {
            this.GetModel<IGameControllerModel>().WarningAlert.ShowWithText("�����Ѵ���");
        } else if (code == 422) {
            this.GetModel<IGameControllerModel>().WarningAlert.ShowWithText("�������Ѵ����ޣ����Ժ�����");
        }
    }

    public void OnExitMsg(JsonData recJD) {

    }

    public void OnJoinMsg(JsonData recJD) {
        int code = (int)recJD["code"];
        Debug.LogError("OnJoinMsg = " + code);
        if (code == 200) {
            this.SendEvent<MatchRoomCreateOrJoinSuccEvent>();
        } else if (code == 404) {
            this.GetModel<IGameControllerModel>().WarningAlert.ShowWithText("���䲻����");
        } else if (code == 422) {
            this.GetModel<IGameControllerModel>().WarningAlert.ShowWithText("������������");
        }
    }

    public void OnStatusMsg(JsonData recJD) {
        JsonData players = recJD["players"];
        var infos = this.GetModel<IMatchModel>().MemberInfoDic;
        infos.Clear();
        for (int i = 0; i < players.Count; i++) {
            MatchRoomMemberInfo info = new MatchRoomMemberInfo();
            info.memberName = (string)players[i]["member_name"];
            info.isHouseOwner = ((uint)players[i]["type"] == 1);
            info.aid = (int)players[i]["aid"];
        }
    }

    public void OnStartMsg(JsonData recJD) {
        if ((int)recJD["code"] == 200) {
            this.GetSystem<IDataParseSystem>().ParseSelectMatch(recJD);
            this.SendEvent<MatchRoomStartEvent>();
        }
    }

    protected override void OnInit() {

    }
}
