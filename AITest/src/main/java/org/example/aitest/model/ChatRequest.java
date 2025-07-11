package org.example.aitest.model;

import lombok.Data;

@Data
public class ChatRequest {
    private String prompt;
    private String model;
    
    public ChatRequest() {
    }
    
    public ChatRequest(String prompt, String model) {
        this.prompt = prompt;
        this.model = model;
    }
}