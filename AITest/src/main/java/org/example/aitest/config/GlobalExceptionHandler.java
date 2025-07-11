package org.example.aitest.config;

import org.example.aitest.model.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ChatResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ChatResponse.error("服务器内部错误: " + e.getMessage()));
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ChatResponse> handleRestClientException(RestClientException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ChatResponse.error("调用豆包API失败: " + e.getMessage()));
    }
}