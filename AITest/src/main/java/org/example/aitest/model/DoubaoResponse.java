package org.example.aitest.model;

import lombok.Data;
import java.util.List;

@Data
public class DoubaoResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    
    public DoubaoResponse() {
    }
    
    public DoubaoResponse(String id, String object, long created, String model, List<Choice> choices, Usage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.usage = usage;
    }
    
    @Data
    public static class Choice {
        private int index;
        private Message message;
        private String finishReason;
        
        public Choice() {
        }
        
        public Choice(int index, Message message, String finishReason) {
            this.index = index;
            this.message = message;
            this.finishReason = finishReason;
        }
    }
    
    @Data
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
        
        public Usage() {
        }
        
        public Usage(int promptTokens, int completionTokens, int totalTokens) {
            this.promptTokens = promptTokens;
            this.completionTokens = completionTokens;
            this.totalTokens = totalTokens;
        }
    }
}