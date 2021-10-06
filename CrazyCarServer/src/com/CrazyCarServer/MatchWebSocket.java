package com.CrazyCarServer;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint ע����һ�����ε�ע�⣬���Ĺ�����Ҫ�ǽ�Ŀǰ���ඨ���һ��websocket��������,
 * ע���ֵ�������ڼ����û����ӵ��ն˷���URL��ַ,�ͻ��˿���ͨ�����URL�����ӵ�WebSocket��������
 * @ServerEndpoint ���԰ѵ�ǰ����websocket������
 */
@ServerEndpoint("/websocket/MatchWebSocket/{id}")
public class MatchWebSocket {
	//��̬������������¼��ǰ������������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
    private static int onlineCount = 0;
    //concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
    private static ConcurrentHashMap<String, MatchWebSocket> webSocketSet = new ConcurrentHashMap<String, MatchWebSocket>();
    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
    private Session WebSocketsession;
    //��ǰ����Ϣ����Ա���
    private String id = "";
    private JSONObject sendMsg = new JSONObject(); 
 
    /**
     * ���ӽ����ɹ����õķ���
     *
     * @param session ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String param, Session WebSocketsession, EndpointConfig config) {
        System.out.println(param);
        id = param;//���յ�������Ϣ����Ա���
        this.WebSocketsession = WebSocketsession;
        webSocketSet.put(param, this);//����map��
        addOnlineCount();           //��������1
        System.out.println("�������Ӽ��룡��ǰ��������Ϊ" + getOnlineCount());
    }
 
    /**
     * ���ӹرյ��õķ���
     */ 
    @OnClose
    public void onClose() {
        if (!id.equals("")) {
            webSocketSet.remove(id);  //��set��ɾ��
            subOnlineCount();           //��������1
            System.out.println("��һ���ӹرգ���ǰ��������Ϊ" + getOnlineCount());
        }
    }
 
    /**
     * �յ��ͻ�����Ϣ����õķ���
     *
     * @param message �ͻ��˷��͹�������Ϣ
     * @param session ��ѡ�Ĳ���
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        sendToUser(message);
        //sendAll(message) ;
    }
    /**
     * ��ָ�����˷�����Ϣ
     * @param message
     */
    public void sendToUser(String message) {
    	sendMsg = JSONObject.parseObject(message);
    	String cid = sendMsg.getString("cid");
    	
        //String now = getNowTime();
    	for (String key : webSocketSet.keySet()) {
          try {
        	  if (key.split(",")[1].equals(cid) && !key.equals(id)){
        		  webSocketSet.get(key).sendMessage(message);
        	  }              
          } catch (IOException e) {
              e.printStackTrace();
          }
       }     
    }

    /**
     * ��������ʱ����
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("��������");
        error.printStackTrace();
    }
    /**
     * ������������漸��������һ����û����ע�⣬�Ǹ����Լ���Ҫ��ӵķ�����
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
    	MatchWebSocket.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
    	MatchWebSocket.onlineCount--;
    }
}
