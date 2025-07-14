@echo off
:: Create SafeClean Desktop Shortcut - Antivirus Safe Alternative
setlocal enabledelayedexpansion

title SafeClean - Create Desktop Shortcut

echo.
echo  =====================================================
echo    SafeClean - Create Desktop Shortcut
echo    Antivirus-Safe Alternative to Installer
echo  =====================================================
echo.

:: Get current directory
set "CURRENT_DIR=%~dp0"

:: Create desktop shortcut using PowerShell
echo  Creating desktop shortcut...

powershell -Command "$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%USERPROFILE%\Desktop\SafeClean.lnk'); $Shortcut.TargetPath = '%CURRENT_DIR%SafeClean-Safe.bat'; $Shortcut.WorkingDirectory = '%CURRENT_DIR%'; $Shortcut.Description = 'SafeClean - Professional Windows System Cleanup'; $Shortcut.Save()"

if %errorlevel% equ 0 (
    echo  ✓ Desktop shortcut created successfully!
    echo.
    echo  Shortcut created: %USERPROFILE%\Desktop\SafeClean.lnk
    echo  Target: %CURRENT_DIR%SafeClean-Safe.bat
    echo.
    echo  =====================================================
    echo    IMPORTANT: To run SafeClean properly:
    echo    Right-click the shortcut → Run as administrator
    echo  =====================================================
    echo.
) else (
    echo  ✗ Failed to create shortcut. Trying manual method...
    echo.
    echo  MANUAL SHORTCUT CREATION:
    echo  1. Right-click on Desktop → New → Shortcut
    echo  2. Target: %CURRENT_DIR%SafeClean-Safe.bat
    echo  3. Name: SafeClean
    echo  4. Right-click shortcut → Properties → Advanced → Run as administrator
    echo.
)

:: Optional: Create Start Menu shortcut
echo  Would you like to create a Start Menu shortcut too? (y/n)
set /p CREATE_START_MENU=

if /i "!CREATE_START_MENU!"=="y" (
    echo  Creating Start Menu shortcut...
    
    powershell -Command "$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%APPDATA%\Microsoft\Windows\Start Menu\Programs\SafeClean.lnk'); $Shortcut.TargetPath = '%CURRENT_DIR%SafeClean-Safe.bat'; $Shortcut.WorkingDirectory = '%CURRENT_DIR%'; $Shortcut.Description = 'SafeClean - Professional Windows System Cleanup'; $Shortcut.Save()"
    
    if !errorlevel! equ 0 (
        echo  ✓ Start Menu shortcut created successfully!
    ) else (
        echo  ✗ Failed to create Start Menu shortcut
    )
)

echo.
echo  =====================================================
echo    Setup Complete!
echo  
echo    You can now run SafeClean using:
echo    • Desktop shortcut (right-click → Run as admin)
echo    • Start Menu → SafeClean
echo    • Double-click: SafeClean-Safe.bat
echo  
echo    No installer needed - Zero antivirus issues!
echo  =====================================================
echo.

pause
endlocal
