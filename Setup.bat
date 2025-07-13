@echo off
echo SafeClean Professional Installer
echo.
echo This will install SafeClean to Program Files and create shortcuts.
echo You need Administrator privileges to continue.
echo.
pause

REM Check if PowerShell is available
powershell.exe -Command "Get-Host" >nul 2>&1
if errorlevel 1 (
    echo ERROR: PowerShell is required but not found.
    pause
    exit /b 1
)

REM Run the PowerShell installer with admin privileges
powershell.exe -ExecutionPolicy Bypass -Command "Start-Process PowerShell -ArgumentList '-ExecutionPolicy Bypass -File \"%~dp0Install-SafeClean.ps1\"' -Verb RunAs"

echo.
echo Installation script launched with administrator privileges.
echo Please follow the instructions in the PowerShell window.
pause
