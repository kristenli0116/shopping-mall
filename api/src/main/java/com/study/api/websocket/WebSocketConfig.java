package com.study.api.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.net.ServerSocket;

/**
 * @auther lkx
 * @create 2022-05-09 22:17
 * @Description:
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter getServerEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
