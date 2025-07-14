@echo off 
:: SafeClean - Laptop Quick Start 
echo SafeClean - Laptop Quick Start 
echo ================================ 
echo. 
echo [1] Testing Java environment... 
java -version 
if errorlevel 1 ( 
    echo ERROR: Java not found 
    echo Please install Java 17+ from: https://adoptium.net/temurin/releases/ 
    pause 
    exit /b 1 
) 
echo [2] Starting SafeClean... 
SafeClean-Safe.bat 
