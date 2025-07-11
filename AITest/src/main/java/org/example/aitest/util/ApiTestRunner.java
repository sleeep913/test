package org.example.aitest.util;

import org.example.aitest.client.DoubaoClient;
import org.example.aitest.model.Content;
import org.example.aitest.model.DoubaoRequest;
import org.example.aitest.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * API测试运行器
 * 用于在应用启动时进行API连接测试
 */
@Component
public class ApiTestRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApiTestRunner.class);

    private final DoubaoClient doubaoClient;

    @Autowired
    public ApiTestRunner(DoubaoClient doubaoClient) {
        this.doubaoClient = doubaoClient;
    }

    @Override
    public void run(String... args) {
        logger.info("应用启动完成，开始进行API连接测试...");
        testApiConnection();
    }

    private void testApiConnection() {
        try {
            // 创建一个简单的测试请求
            Content textContent = new Content();
            textContent.setType("text");
            textContent.setContent("测试连接");

            Message userMessage = new Message();
            userMessage.setRole("user");
            userMessage.setContent(Collections.singletonList(textContent));

            DoubaoRequest request = new DoubaoRequest();
            request.setModel("Doubao-lite");
            request.setMessages(Collections.singletonList(userMessage));

            // 发送请求
            logger.info("发送测试请求到豆包API...");
            ResponseEntity<String> response = doubaoClient.chat(request);

            // 输出响应
            logger.info("豆包API测试响应状态码: {}", response.getStatusCode());
            logger.info("豆包API测试响应头信息: {}", response.getHeaders());
            
            if (response.getBody() != null && response.getBody().length() > 1000) {
                logger.info("豆包API测试响应内容(截断): {}...", response.getBody().substring(0, 1000));
            } else {
                logger.info("豆包API测试响应内容: {}", response.getBody());
            }

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("豆包API连接测试成功!");
            } else {
                logger.error("豆包API连接测试失败! 状态码: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("豆包API连接测试异常: {}", e.getMessage(), e);
        }
    }
}