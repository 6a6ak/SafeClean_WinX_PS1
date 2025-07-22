@echo off
REM SafeClean Installer Helper
REM This script runs the SafeClean installer with administrator privileges

title SafeClean Installer

echo.
echo ==========================================
echo  SafeClean v2.0.1 - Installation Helper
echo ==========================================
echo.
echo This script will run the SafeClean installer
echo with administrator privileges.
echo.
echo Administrator privileges are required for:
echo  - Installing the application
echo  - Creating desktop shortcuts
echo  - Registering system components
echo.

pause

echo.
echo Launching SafeClean installer...
echo.

REM Check if installer exists
if not exist "SafeClean_Setup_v2.0.1.exe" (
    echo ERROR: SafeClean_Setup_v2.0.1.exe not found!
    echo Please ensure the installer is in the same directory as this script.
    echo.
    pause
    exit /b 1
)

REM Run installer with administrator privileges
powershell -Command "Start-Process 'SafeClean_Setup_v2.0.1.exe' -Verb RunAs"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Installer launched successfully!
    echo Follow the installation wizard to complete setup.
) else (
    echo.
    echo Failed to launch installer or user cancelled UAC prompt.
    echo Please try running the installer manually as administrator.
)

echo.
pause
