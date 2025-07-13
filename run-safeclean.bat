@echo off
echo SafeClean WinX - System Cleanup Tool
echo.
echo Starting application...
echo.

cd /d "%~dp0"
java -jar target/SafeCleanWinX-2.0.0.jar

if errorlevel 1 (
    echo.
    echo ERROR: Failed to start SafeClean WinX
    echo Make sure Java 8 or higher is installed on your system
    echo.
    pause
)
