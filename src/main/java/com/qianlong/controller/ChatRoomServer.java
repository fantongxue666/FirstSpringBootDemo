package com.qianlong.controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
/**
 * 聊天室的服务端程序
 * @author Administrator
 *
 */
//声明websocket某个服务端的地址
@ServerEndpoint("/charRoomServer")
public class ChatRoomServer {
    private boolean firstFlag=true;
    private Session session;
    private String userName;
    //记录此次聊天室的服务端有多少个连接
    //key代表此次客户端的session的id，value代表此次连接对象
    private static final HashMap<String, Object> connectMap=new HashMap<String, Object>();
    //保存所有用户昵称信息
    //key是session的id，value是用户昵称
    private static final HashMap<String, String> userMap=new HashMap<String, String>();

    //服务端收到客户端的连接请求，连接成功后会执行此方法
    @OnOpen
    public void start(Session session) {
        this.session=session;
        connectMap.put(session.getId(), this);
    }

    //客户端发来的信息，服务端接收
    @OnMessage
    public void chat(String clientMessage,Session session) {
        //firstFlag为true是第一次进入，第二次进入之后设为false
        ChatRoomServer client=null;
        if(firstFlag) {
            this.userName=clientMessage;
            //将新进来的用户保存到用户map
            userMap.put(session.getId(), userName);
            //构造发送给客户端的提示信息
            String message=htmlMessage("请问",userName+"有什么疑惑吗？");
            //将消息广播给给所有的用户
            //Map.keySet()方法是获取到Map集合的所有的key值
            for(String connectKey:connectMap.keySet()) {
                client=(ChatRoomServer) connectMap.get(connectKey);
                //给对应的web端发送一个文本信息
                try {
                    client.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            firstFlag=false;
        }else {
            //构造发送给客户端的提示信息
            String message=htmlMessage(userMap.get(session.getId()),clientMessage);
            //将消息广播给给所有的用户
            //Map.keySet()方法是获取到Map集合的所有的key值
            for(String connectKey:connectMap.keySet()) {
                client=(ChatRoomServer) connectMap.get(connectKey);
                //给对应的web端发送一个文本信息
                try {
                    client.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 前台js的ws.close事件，会触发后台的标注onClose的方法
     */
    @OnClose
    public void close(Session session) {
        //当某个用户退出时，对其他用户进行广播
        String message=htmlMessage("系统消息", userMap.get(session.getId())+"退出了聊天室");
        userMap.remove(session.getId());
        connectMap.remove(session.getId());
        //将消息广播给给所有的用户
        //Map.keySet()方法是获取到Map集合的所有的key值
        ChatRoomServer client=null;
        for(String connectKey:connectMap.keySet()) {
            client=(ChatRoomServer) connectMap.get(connectKey);
            //给对应的web端发送一个文本信息
            try {
                client.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 渲染页面，把信息构造好标签再发送
     */
    public String htmlMessage(String userName,String message) {
        StringBuffer stringBuffer=new StringBuffer();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stringBuffer.append("<article>");
        stringBuffer.append("<span>"+sf.format(new Date())+"</span>");
        stringBuffer.append("<div class='avatar'>");
        stringBuffer.append("<h3>"+userName+"</h3>");
        stringBuffer.append("</div>");
        stringBuffer.append("<div class='msg'>");
        stringBuffer.append("<div class='tri'></div>");
        stringBuffer.append("<div class='msg_inner'>"+message+"</div>");
        stringBuffer.append("</div>");
        stringBuffer.append("</article>");

        return stringBuffer.toString();
    }
}


