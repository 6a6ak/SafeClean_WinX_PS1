@echo off
echo SafeClean Installer Builder
echo ==========================
echo.

REM Try to find NSIS installation
set NSIS_PATH=""
if exist "C:\Program Files (x86)\NSIS\makensis.exe" (
    set NSIS_PATH="C:\Program Files (x86)\NSIS\makensis.exe"
) else if exist "C:\Program Files\NSIS\makensis.exe" (
    set NSIS_PATH="C:\Program Files\NSIS\makensis.exe"
) else (
    where makensis >nul 2>&1
    if not errorlevel 1 (
        set NSIS_PATH=makensis
    )
)

if %NSIS_PATH%=="" (
    echo ERROR: NSIS not found in standard locations.
    echo.
    echo Please install NSIS from: https://nsis.sourceforge.io/Download
    echo Or add makensis.exe to your PATH.
    echo.
    echo Alternative: Use Setup.bat for PowerShell installer.
    pause
    exit /b 1
)

echo Found NSIS at: %NSIS_PATH%
echo.

REM Build the project first
echo Step 1: Building SafeClean application...
mvn clean package -DskipTests
if errorlevel 1 (
    echo ERROR: Failed to build the application.
    pause
    exit /b 1
)

echo.
echo Step 2: Creating Windows installer with NSIS...

REM Build installer with NSIS
%NSIS_PATH% src\main\nsis\setup-simple.nsi

if errorlevel 1 (
    echo ERROR: Failed to create installer.
    pause
    exit /b 1
)

echo.
if exist "target\SafeClean-Setup.exe" (
    echo SUCCESS! Installer created: target\SafeClean-Setup.exe
    echo.
    dir target\SafeClean-Setup.exe
) else (
    echo ERROR: Installer file not found.
)
echo.
pause
