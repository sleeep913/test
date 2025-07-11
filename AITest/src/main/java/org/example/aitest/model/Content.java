package org.example.aitest.model;

import lombok.Data;
@Data
public class Content {
    private String type;
    private Object content;
    
    public Content() {
    }
    
    public Content(String type, Object content) {
        this.type = type;
        this.content = content;
    }
}
