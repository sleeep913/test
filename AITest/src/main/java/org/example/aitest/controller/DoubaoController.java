package org.example.aitest.controller;

import org.example.aitest.model.*;
import org.example.aitest.service.DoubaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doubao")
public class DoubaoController {

    private final DoubaoService doubaoService;

    public DoubaoController(DoubaoService doubaoService) {
        this.doubaoService = doubaoService;
    }

    /**
     * 发送单条文本消息到豆包API
     * @param chatRequest 聊天请求
     * @return API响应
     */
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        try {
            DoubaoResponse response = doubaoService.sendMessage(chatRequest.getPrompt(), chatRequest.getModel());
            return ResponseEntity.ok(ChatResponse.success("请求成功", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ChatResponse.error("请求失败: " + e.getMessage()));
        }
    }

    /**
     * 发送多轮对话消息到豆包API
     * @param messages 消息列表
     * @param model 模型名称，默认为Doubao-lite
     * @return API响应
     */
    @PostMapping("/chat/messages")
    public ResponseEntity<ChatResponse> chatWithMessages(@RequestBody List<Message> messages, 
                                         @RequestParam(required = false) String model) {
        try {
            DoubaoResponse response = doubaoService.sendMessages(messages, model);
            return ResponseEntity.ok(ChatResponse.success("请求成功", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ChatResponse.error("请求失败: " + e.getMessage()));
        }
    }

    /**
     * 测试接口 - 检查API连接状态
     * @return 测试结果
     */
    @GetMapping("/test")
    public ResponseEntity<ChatResponse> test() {
        try {
            // 创建一个简单的测试消息
            Message testMessage = doubaoService.createTextMessage("user", "测试连接");
            
            // 发送测试请求
            DoubaoResponse response = doubaoService.sendMessages(
                    Collections.singletonList(testMessage), 
                    "Doubao-lite"
            );
            
            // 检查响应
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                Map<String, Object> testResult = new HashMap<>();
                testResult.put("status", "success");
                testResult.put("model", response.getModel());
                testResult.put("responseId", response.getId());
                
                return ResponseEntity.ok(ChatResponse.success("豆包API连接正常", testResult));
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_GATEWAY)
                        .body(ChatResponse.error("豆包API返回异常响应"));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ChatResponse.error("豆包API连接异常: " + e.getMessage()));
        }
    }
}