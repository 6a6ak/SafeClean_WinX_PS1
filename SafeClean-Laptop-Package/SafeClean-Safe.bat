@echo off
:: SafeClean - Antivirus-Friendly Launcher
:: This script avoids common false positive triggers

setlocal enabledelayedexpansion
title SafeClean - Professional Windows System Cleanup Tool

:: Set console colors for better visibility
color 0A

echo.
echo  ===================================================
echo    SafeClean - Professional Windows System Cleanup
echo    Version 2.0.0 - Antivirus-Friendly Launcher
echo  ===================================================
echo.

:: Check for administrator privileges
net session >nul 2>&1
if %errorlevel% neq 0 (
    echo  WARNING: Not running as Administrator
    echo  For best results, right-click and "Run as administrator"
    echo.
    timeout /t 3 /nobreak >nul
)

::: Check if Java is available
echo  [1/3] Checking Java Runtime Environment...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo  ERROR: Java Runtime Environment not found!
    echo.
    echo  SafeClean requires Java 8 or higher to run.
    echo  
    echo  Please install Java from:
    echo  https://adoptium.net/temurin/releases/?version=8
    echo.
    echo  After installing Java, run this script again.
    echo.
    pause
    exit /b 1
)

:: Get Java version and check if it's compatible
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)
set JAVA_VERSION=%JAVA_VERSION:"=%

:: Check if version is compatible (basic check for Java 8+)
echo %JAVA_VERSION% | findstr /r "^1\.[0-7]\." >nul
if not errorlevel 1 (
    echo  ERROR: Java version is too old!
    echo  Found: %JAVA_VERSION%
    echo  Required: Java 8 or higher
    echo.
    echo  Please update Java from:
    echo  https://adoptium.net/temurin/releases/?version=8
    echo.
    echo  Make sure to install the LATEST version.
    pause
    exit /b 1
)

echo  Found Java version: %JAVA_VERSION%
echo  Java version is compatible!

:: Check if SafeClean JAR exists
echo  [2/3] Locating SafeClean application...
if exist "SafeClean-2.0.0.jar" (
    set JAR_FILE=SafeClean-2.0.0.jar
) else if exist "target\SafeClean-2.0.0.jar" (
    set JAR_FILE=target\SafeClean-2.0.0.jar
) else (
    echo  ERROR: SafeClean application not found!
    echo.
    echo  Expected locations:
    echo  - SafeClean-2.0.0.jar
    echo  - target\SafeClean-2.0.0.jar
    echo.
    echo  Please ensure SafeClean is properly installed.
    pause
    exit /b 1
)

echo  Found SafeClean at: %JAR_FILE%

:: Launch SafeClean with proper Java arguments
echo  [3/3] Starting SafeClean...
echo.
echo  ===================================================
echo    SafeClean is now starting...
echo    This window will remain open for monitoring.
echo  ===================================================
echo.

:: Run with optimized JVM arguments for system tools
java -Xms64m -Xmx512m -Dfile.encoding=UTF-8 -Djava.awt.headless=false -jar "%JAR_FILE%"

:: Check exit code
if %errorlevel% neq 0 (
    echo.
    echo  SafeClean exited with error code: %errorlevel%
    echo  This may indicate a problem with the application.
    echo.
    pause
) else (
    echo.
    echo  SafeClean completed successfully.
    timeout /t 2 /nobreak >nul
)

endlocal
