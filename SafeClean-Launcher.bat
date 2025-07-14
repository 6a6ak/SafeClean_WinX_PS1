@echo off
setlocal enabledelayedexpansion

echo SafeClean Java Runtime Checker
echo ===============================
echo.

REM Check if Java is installed and get version
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java Runtime Environment not found!
    echo.
    echo SafeClean requires Java 8 or higher to run.
    echo.
    echo Please choose an option:
    echo [1] Download Java automatically (opens browser)
    echo [2] Install manually 
    echo [3] Exit
    echo.
    choice /c 123 /n /m "Your choice: "
    
    if !errorlevel!==1 (
        echo Opening Java download page...
        start https://adoptium.net/temurin/releases/?version=8
        echo.
        echo After installing Java, run SafeClean.exe again.
        pause
        exit /b 1
    )
    
    if !errorlevel!==2 (
        echo.
        echo Manual Installation Instructions:
        echo 1. Visit: https://adoptium.net/temurin/releases/?version=8
        echo 2. Download: OpenJDK 8 with HotSpot JVM
        echo 3. Install: Follow the installer instructions
        echo 4. Run: SafeClean.exe again
        echo.
        pause
        exit /b 1
    )
    
    if !errorlevel!==3 (
        exit /b 1
    )
) else (
    echo Java Runtime Environment found!
    
    REM Get Java version
    for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
        set JAVA_VERSION=%%g
    )
    set JAVA_VERSION=%JAVA_VERSION:"=%
    echo Java Version: %JAVA_VERSION%
    echo.
    
    REM Check if version is 1.8 or higher
    for /f "delims=. tokens=1,2" %%v in ("%JAVA_VERSION%") do (
        set /a MAJOR=%%v
        set /a MINOR=%%w
    )
    
    if %MAJOR% geq 1 (
        if %MINOR% geq 8 (
            echo Java version is compatible!
            echo Starting SafeClean...
            echo.
            SafeClean.exe
            exit /b 0
        )
    )
    
    echo WARNING: Java version is too old!
    echo SafeClean requires Java 8 or higher.
    echo Current version: %JAVA_VERSION%
    echo.
    echo Please update Java and try again.
    echo Download from: https://adoptium.net/temurin/releases/?version=8
    pause
    exit /b 1
)
