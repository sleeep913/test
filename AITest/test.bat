@echo off
echo ======================================
echo 豆包API测试环境启动脚本
echo ======================================
echo.

echo 正在检查Java环境...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo [错误] 未检测到Java环境，请安装Java 17或更高版本。
    pause
    exit /b 1
)

echo.
echo 正在启动测试环境...
echo 启动后请访问: http://localhost:8080
echo.

echo 测试环境将输出更详细的日志信息，便于排查问题
echo 如果遇到API调用失败，请检查application.properties中的配置：
echo - doubao.api-url 是否正确
echo - doubao.api-key 是否有效
echo.

echo 按Ctrl+C可以停止应用
echo ======================================

.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=test

pause