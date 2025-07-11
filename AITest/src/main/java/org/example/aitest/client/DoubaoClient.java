package org.example.aitest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aitest.model.DoubaoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class DoubaoClient {
    
    private static final Logger logger = LoggerFactory.getLogger(DoubaoClient.class);
    
    @Value("${doubao.api-url}")
    private String apiUrl;
    
    @Value("${doubao.api-key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public DoubaoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * 调用豆包API进行聊天
     * @param request 请求参数
     * @return API响应
     */
    public ResponseEntity<String> chat(DoubaoRequest request) {
        try {
            logger.info("准备调用豆包API，URL: {}", apiUrl);
            logger.debug("请求参数: {}", objectMapper.writeValueAsString(request));
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            
            HttpEntity<DoubaoRequest> entity = new HttpEntity<>(request, headers);
            
            logger.info("开始发送请求到豆包API");
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            
            logger.info("豆包API响应状态码: {}", response.getStatusCode());
            logger.debug("豆包API响应内容: {}", response.getBody());
            
            return response;
        } catch (RestClientException e) {
            logger.error("调用豆包API时发生RestClientException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("调用豆包API时发生异常: {}", e.getMessage(), e);
            throw new RestClientException("调用豆包API时发生异常: " + e.getMessage(), e);
        }
    }
}
