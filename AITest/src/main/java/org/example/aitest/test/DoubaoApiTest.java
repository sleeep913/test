package org.example.aitest.test;

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
 * 豆包API测试类
 * 仅在测试环境下运行
 */
@Component
@Profile("test")
public class DoubaoApiTest implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DoubaoApiTest.class);

    private final DoubaoClient doubaoClient;

    @Autowired
    public DoubaoApiTest(DoubaoClient doubaoClient) {
        this.doubaoClient = doubaoClient;
    }

    @Override
    public void run(String... args) {
        logger.info("开始测试豆包API连接...");
        testApiConnection();
    }

    private void testApiConnection() {
        try {
            // 创建一个简单的测试请求
            Content textContent = new Content();
            textContent.setType("text");
            textContent.setContent("你好，这是一个测试消息");

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
            logger.info("豆包API测试响应内容: {}", response.getBody());

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