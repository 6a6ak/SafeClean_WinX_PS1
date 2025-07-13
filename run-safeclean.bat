@echo off
echo SafeClean WinX - System Cleanup Tool
echo.
echo Starting application...
echo.

cd /d "%~dp0"
if exist "target\SafeCleanWinX-2.0.0.jar" (
    java -jar target/SafeCleanWinX-2.0.0.jar
) else (
    echo ERROR: JAR file not found. Please build the project first with: mvn package
    echo.
)

if errorlevel 1 (
    echo.
    echo ERROR: Failed to start SafeClean WinX
    echo Make sure Java 8 or higher is installed on your system
    echo.
    pause
)
