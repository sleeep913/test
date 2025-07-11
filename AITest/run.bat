@echo off
echo ======================================
echo 豆包API测试应用启动脚本
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
echo 正在启动应用...
echo 启动后请访问: http://localhost:8080
echo.

echo 如果遇到API调用失败，请检查application.properties中的配置：
echo - doubao.api-url 是否正确
echo - doubao.api-key 是否有效
echo.

echo 按Ctrl+C可以停止应用
echo ======================================

.\mvnw.cmd spring-boot:run

pause