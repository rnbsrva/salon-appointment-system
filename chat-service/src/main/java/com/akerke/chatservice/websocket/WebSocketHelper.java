package com.akerke.chatservice.websocket;

import lombok.experimental.UtilityClass;
import org.springframework.web.socket.WebSocketSession;

import static com.akerke.chatservice.constants.AppConstants.USER_SESSION_KEY;

@UtilityClass
public class WebSocketHelper {

    public static String getUserIdFromSessionAttribute(WebSocketSession webSocketSession) {
        return (String) webSocketSession.getAttributes().get(USER_SESSION_KEY);
    }

    public static String getUserIdFromUrl(String path){
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
