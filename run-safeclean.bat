@echo off
echo SafeClean - System Cleanup Tool
echo.
echo Starting application...
echo.

cd /d "%~dp0"

REM Try to run the EXE file first
if exist "target\SafeClean.exe" (
    echo Running native Windows executable...
    target\SafeClean.exe
    goto :end
)

REM Fallback to JAR file
if exist "target\SafeClean-2.0.0.jar" (
    echo Running Java JAR file...
    java -jar target/SafeClean-2.0.0.jar
) else (
    echo ERROR: Neither EXE nor JAR file found. 
    echo Please build the project first with: mvn package
    echo.
)

if errorlevel 1 (
    echo.
    echo ERROR: Failed to start SafeClean
    echo Make sure Java 8 or higher is installed on your system
    echo.
    pause
)

:end
