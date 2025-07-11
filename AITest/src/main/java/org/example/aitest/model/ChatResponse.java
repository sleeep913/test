package org.example.aitest.model;

import lombok.Data;

@Data
public class ChatResponse {
    private String message;
    private boolean success;
    private Object data;
    
    public ChatResponse() {
    }
    
    public ChatResponse(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }
    
    public static ChatResponse success(String message, Object data) {
        return new ChatResponse(message, true, data);
    }
    
    public static ChatResponse error(String message) {
        return new ChatResponse(message, false, null);
    }
}