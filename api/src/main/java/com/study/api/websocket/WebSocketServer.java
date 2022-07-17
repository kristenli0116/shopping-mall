package com.study.api.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther lkx
 * @create 2022-05-09 22:19
 * @Description:
 */
@Component
@ServerEndpoint("/webSocket/{oid}")
public class WebSocketServer {

    private static ConcurrentHashMap<String,Session> sessionOnMap = new ConcurrentHashMap<>();
    /**
     * 前端发送请求建立websocket链接，就会执行@OnOpen方法
     */
    @OnOpen
    public void open(@PathParam("oid") String orderId, Session session){
        System.out.println("---------建立链接:"  + orderId);
        sessionOnMap.put(orderId,session);
    }

    /**
     * 前端关闭页面或者主动关闭webSocket链接，都会执行close
     */
    @OnClose
    public void close(@PathParam("oid") String orderId){
        sessionOnMap.remove(orderId);
    }

    public static void  sendMsg(String orderId,String msg){
        try {
            Session session = sessionOnMap.get(orderId);
            session.getBasicRemote().sendText(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
