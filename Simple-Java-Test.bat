@echo off
:: SafeClean - Simple Java Environment Checker
setlocal enabledelayedexpansion

echo ===================================================
echo  SafeClean - Java Environment Troubleshooter
echo ===================================================
echo.

echo [1] Basic Java Test...
java -version
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Java not found or not working
    echo.
    echo SOLUTIONS:
    echo 1. Download Java from: https://adoptium.net/temurin/releases/
    echo 2. Install Java 17 or higher
    echo 3. Restart your computer
    echo 4. Make sure Java is in your PATH
    echo.
    pause
    exit /b 1
)

echo.
echo [2] Java PATH Check...
where java
echo.

echo [3] Java Home Check...
if defined JAVA_HOME (
    echo JAVA_HOME is set to: %JAVA_HOME%
) else (
    echo JAVA_HOME is not set (this is usually OK)
)
echo.

echo [4] SafeClean JAR Check...
if exist "target\SafeClean-2.0.0.jar" (
    echo ✓ Found: target\SafeClean-2.0.0.jar
    set JAR_FILE=target\SafeClean-2.0.0.jar
) else if exist "SafeClean-2.0.0.jar" (
    echo ✓ Found: SafeClean-2.0.0.jar
    set JAR_FILE=SafeClean-2.0.0.jar
) else (
    echo ✗ SafeClean JAR not found
    echo.
    echo Please run: mvn clean package
    echo.
    pause
    exit /b 1
)

echo.
echo [5] Test SafeClean Launch...
echo Running: java -jar "%JAR_FILE%"
echo.

java -jar "%JAR_FILE%"
set EXIT_CODE=%errorlevel%

echo.
echo SafeClean finished with exit code: %EXIT_CODE%

if %EXIT_CODE% neq 0 (
    echo.
    echo This indicates an error occurred.
    echo Common solutions:
    echo 1. Run as Administrator
    echo 2. Check antivirus isn't blocking
    echo 3. Ensure you have proper permissions
    echo.
)

pause
endlocal
