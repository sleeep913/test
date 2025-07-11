package org.example.aitest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aitest.client.DoubaoClient;
import org.example.aitest.model.Content;
import org.example.aitest.model.DoubaoRequest;
import org.example.aitest.model.DoubaoResponse;
import org.example.aitest.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DoubaoService {

    private final DoubaoClient doubaoClient;
    private final ObjectMapper objectMapper;

    public DoubaoService(DoubaoClient doubaoClient) {
        this.doubaoClient = doubaoClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 发送文本消息到豆包API
     * @param prompt 用户输入的文本
     * @param model 模型名称，默认为Doubao-lite
     * @return API响应
     */
    public DoubaoResponse sendMessage(String prompt, String model) {
        try {
            // 创建文本内容
            Content textContent = new Content();
            textContent.setType("text");
            textContent.setContent(prompt);

            // 创建用户消息
            Message userMessage = new Message();
            userMessage.setRole("user");
            userMessage.setContent(Collections.singletonList(textContent));

            // 创建请求
            DoubaoRequest request = new DoubaoRequest();
            request.setModel(model != null ? model : "Doubao-lite");
            request.setMessages(Collections.singletonList(userMessage));

            // 发送请求
            ResponseEntity<String> response = doubaoClient.chat(request);
            
            // 解析响应
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readValue(response.getBody(), DoubaoResponse.class);
            } else {
                throw new RuntimeException("调用豆包API失败，状态码: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("调用豆包API失败", e);
        }
    }

    /**
     * 发送多轮对话消息到豆包API
     * @param messages 消息列表
     * @param model 模型名称，默认为Doubao-lite
     * @return API响应
     */
    public DoubaoResponse sendMessages(List<Message> messages, String model) {
        try {
            // 创建请求
            DoubaoRequest request = new DoubaoRequest();
            request.setModel(model != null ? model : "Doubao-lite");
            request.setMessages(messages);

            // 发送请求
            ResponseEntity<String> response = doubaoClient.chat(request);
            
            // 解析响应
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readValue(response.getBody(), DoubaoResponse.class);
            } else {
                throw new RuntimeException("调用豆包API失败，状态码: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("调用豆包API失败", e);
        }
    }

    /**
     * 创建文本消息
     * @param role 角色（user或assistant）
     * @param text 文本内容
     * @return 消息对象
     */
    public Message createTextMessage(String role, String text) {
        Content textContent = new Content();
        textContent.setType("text");
        textContent.setContent(text);

        Message message = new Message();
        message.setRole(role);
        message.setContent(Collections.singletonList(textContent));
        
        return message;
    }
}