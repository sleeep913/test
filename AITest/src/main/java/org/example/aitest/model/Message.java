package org.example.aitest.model;

import lombok.Data;
import java.util.List;

@Data
public class Message {
    private String role;
    private List<Content> content;
    
    public Message() {
    }
    
    public Message(String role, List<Content> content) {
        this.role = role;
        this.content = content;
    }
}
