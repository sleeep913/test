package org.example.aitest.controller;

import org.example.aitest.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置信息控制器
 * 提供API配置信息查询接口
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Value("${doubao.api-url}")
    private String apiUrl;

    @Value("${doubao.api-key}")
    private String apiKey;

    /**
     * 获取API配置信息
     * 注意：为了安全，API密钥只显示前6位和后4位
     * @return 配置信息
     */
    @GetMapping("/api-info")
    public ResponseEntity<ChatResponse> getApiInfo() {
        Map<String, String> configInfo = new HashMap<>();
        configInfo.put("apiUrl", apiUrl);
        
        // 安全处理API密钥，只显示部分信息
        String maskedApiKey = maskApiKey(apiKey);
        configInfo.put("apiKey", maskedApiKey);
        
        return ResponseEntity.ok(ChatResponse.success("获取API配置成功", configInfo));
    }
    
    /**
     * 安全处理API密钥，只显示前6位和后4位
     * @param apiKey 完整的API密钥
     * @return 掩码处理后的API密钥
     */
    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() <= 10) {
            return "***********";
        }
        
        int visibleChars = 10; // 可见字符总数（前6位+后4位）
        int prefixLength = 6;  // 前缀长度
        int suffixLength = 4;  // 后缀长度
        
        if (apiKey.length() <= visibleChars) {
            return apiKey; // 如果API密钥很短，直接返回
        }
        
        String prefix = apiKey.substring(0, prefixLength);
        String suffix = apiKey.substring(apiKey.length() - suffixLength);
        int maskedLength = apiKey.length() - visibleChars;
        String masked = "*".repeat(maskedLength);
        
        return prefix + masked + suffix;
    }
}