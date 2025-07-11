# 豆包API接入示例

这是一个简单的Spring Boot应用，演示如何接入豆包AI模型API。

## 功能特点

- 支持调用豆包API进行聊天对话
- 支持单轮对话和多轮对话
- 提供简单的Web界面进行测试
- 完整的错误处理和异常处理
- API连接测试和配置查看
- 详细的日志记录和故障排除

## 技术栈

- Spring Boot 3.4.7
- Java 17
- RestTemplate用于API调用
- Bootstrap用于前端界面

## 快速开始

### 配置

在`application.properties`文件中配置豆包API的URL和API密钥：

```properties
doubao.api-url=https://api.doubao.com/v1/chat/completions
doubao.api-key=你的API密钥
```

### 运行

```bash
./mvnw spring-boot:run
```

或者使用IDE运行`AiTestApplication`类。

### 访问

启动应用后，在浏览器中访问：

```
http://localhost:8080
```

## API接口

### 1. 单轮对话

```
POST /api/doubao/chat
```

请求体：

```json
{
  "prompt": "你好，请介绍一下自己",
  "model": "Doubao-lite"
}
```

### 2. 多轮对话

```
POST /api/doubao/chat/messages
```

请求体：

```json
[
  {
    "role": "user",
    "content": [
      {
        "type": "text",
        "content": "你好，请介绍一下自己"
      }
    ]
  },
  {
    "role": "assistant",
    "content": [
      {
        "type": "text",
        "content": "我是豆包AI助手，很高兴为您服务！"
      }
    ]
  },
  {
    "role": "user",
    "content": [
      {
        "type": "text",
        "content": "你能做什么？"
      }
    ]
  }
]
```

参数：

```
model=Doubao-lite (可选)
```

### 3. 测试接口

```
GET /api/doubao/test
```

### 4. API配置查看

```
GET /api/config/api-info
```

返回示例：

```json
{
  "apiUrl": "https://api.doubao.com/v1/chat/completions",
  "apiKey": "abcdef******1234"
}
```

## 项目结构

```
src/main/java/org/example/aitest/
├── AiTestApplication.java        # 应用入口
├── client/
│   └── DoubaoClient.java         # 豆包API客户端
├── config/
│   ├── GlobalExceptionHandler.java # 全局异常处理
│   └── RestTemplateConfig.java    # RestTemplate配置
├── controller/
│   ├── ConfigController.java     # 配置信息控制器
│   ├── DoubaoController.java     # API控制器
│   └── HomeController.java       # 首页控制器
├── model/
│   ├── ChatRequest.java          # 聊天请求DTO
│   ├── ChatResponse.java         # 聊天响应DTO
│   ├── Content.java              # 内容模型
│   ├── DoubaoRequest.java        # 豆包请求模型
│   ├── DoubaoResponse.java       # 豆包响应模型
│   └── Message.java              # 消息模型
├── service/
│   └── DoubaoService.java        # 豆包服务
└── util/
    └── ApiTestRunner.java        # API测试运行器
```

另外，项目还包含以下重要文件：

```
src/main/resources/
├── application.properties       # 主配置文件
├── application-test.properties  # 测试环境配置文件
└── static/
    └── index.html               # Web界面

根目录/
├── run.bat                      # 普通模式启动脚本
└── test.bat                     # 测试模式启动脚本
```

## 注意事项

- 请确保在使用前已获取有效的豆包API密钥
- 默认使用的模型是`Doubao-lite`，也可以选择其他可用模型
- 请遵守豆包API的使用条款和限制

## API配置与故障排除

### 正确的API配置

豆包API的正确配置应为：

```properties
doubao.api-url=https://api.doubao.com/v1/chat/completions
doubao.api-key=你的API密钥
```

### 常见问题排查

1. **API调用失败**
   - 检查API URL是否正确配置
   - 确认API密钥是否有效
   - 查看应用日志中的详细错误信息

2. **连接超时**
   - 应用已配置连接超时（10秒）和读取超时（30秒）
   - 如需调整，可在`application.properties`中修改：
     ```properties
     spring.rest.connection-timeout=10000
     spring.rest.read-timeout=30000
     ```

3. **日志调试**
   - 应用已配置详细日志记录，可在日志中查看API调用详情
   - 日志配置位于`application.properties`：
     ```properties
     logging.level.root=INFO
     logging.level.org.example.aitest=DEBUG
     logging.level.org.springframework.web.client.RestTemplate=DEBUG
     ```

### 使用辅助工具

1. **运行脚本**
   - 使用`run.bat`快速启动应用
   - 使用`test.bat`在测试模式下启动应用（包含更详细的日志）

2. **Web界面测试工具**
   - 点击「测试API连接」按钮验证API连接状态
   - 点击「查看API配置」按钮检查当前API配置
   - 点击「清空聊天」按钮清除聊天记录