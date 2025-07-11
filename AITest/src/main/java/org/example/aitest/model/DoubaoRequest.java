package org.example.aitest.model;

import lombok.Data;
import java.util.List;

@Data
public class DoubaoRequest {
    private String model;
    private List<Message> messages;
    private double temperature = 0.7;
    
    public DoubaoRequest() {
    }
    
    public DoubaoRequest(String model, List<Message> messages, double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }
    
    public DoubaoRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }
}
