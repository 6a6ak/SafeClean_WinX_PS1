@echo off
:: Package SafeClean for Laptop Deployment
setlocal enabledelayedexpansion

title SafeClean - Create Laptop Deployment Package

echo.
echo  ===================================================
echo    SafeClean - Laptop Deployment Package Creator
echo  ===================================================
echo.

:: Create deployment directory
set DEPLOY_DIR=SafeClean-Laptop-Package
if exist "%DEPLOY_DIR%" (
    echo  Cleaning existing package directory...
    rmdir /s /q "%DEPLOY_DIR%"
)

echo  Creating deployment package directory...
mkdir "%DEPLOY_DIR%"

:: Copy core application files
echo  [1/6] Copying core application files...
if exist "target\SafeClean-2.0.0.jar" (
    copy "target\SafeClean-2.0.0.jar" "%DEPLOY_DIR%\" >nul
    echo  ✓ SafeClean-2.0.0.jar
) else (
    echo  ✗ SafeClean-2.0.0.jar not found - run 'mvn clean package' first
)

if exist "target\SafeClean.exe" (
    copy "target\SafeClean.exe" "%DEPLOY_DIR%\" >nul
    echo  ✓ SafeClean.exe
) else (
    echo  ✗ SafeClean.exe not found
)

if exist "target\SafeClean-Setup.exe" (
    copy "target\SafeClean-Setup.exe" "%DEPLOY_DIR%\" >nul
    echo  ✓ SafeClean-Setup.exe
) else (
    echo  ✗ SafeClean-Setup.exe not found
)

:: Copy launcher scripts
echo  [2/6] Copying launcher scripts...
copy "SafeClean-Safe.bat" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ SafeClean-Safe.bat
copy "SafeClean-Safe.py" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ SafeClean-Safe.py
copy "Simple-Java-Test.bat" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ Simple-Java-Test.bat
copy "Java-Diagnostics.bat" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ Java-Diagnostics.bat
copy "Create-Shortcut.bat" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ Create-Shortcut.bat

:: Copy documentation
echo  [3/6] Copying documentation...
copy "README.md" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ README.md
copy "LAPTOP-DEPLOYMENT-GUIDE.md" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ LAPTOP-DEPLOYMENT-GUIDE.md
copy "JAVA-TOO-OLD-FIX.md" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ JAVA-TOO-OLD-FIX.md
copy "INSTALLER-ANTIVIRUS-FIX.md" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ INSTALLER-ANTIVIRUS-FIX.md
copy "BYPASS-INSTALLER-GUIDE.md" "%DEPLOY_DIR%\" >nul 2>&1 && echo  ✓ BYPASS-INSTALLER-GUIDE.md

:: Create laptop-specific quick start script
echo  [4/6] Creating laptop quick start script...
echo @echo off > "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo :: SafeClean - Laptop Quick Start >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo echo SafeClean - Laptop Quick Start >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo echo ================================ >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo echo. >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo echo [1] Testing Java environment... >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo java -version >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo if errorlevel 1 ( >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo     echo ERROR: Java not found! >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo     echo Please install Java 17+ from: https://adoptium.net/temurin/releases/ >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo     pause >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo     exit /b 1 >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo ^) >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo echo [2] Starting SafeClean... >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo SafeClean-Safe.bat >> "%DEPLOY_DIR%\LAPTOP-QUICK-START.bat"
echo  ✓ LAPTOP-QUICK-START.bat

:: Create simple Java installer script for laptop
echo  [5/6] Creating Java installer helper...
echo @echo off > "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo :: Java Installation Helper for Laptop >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo Java Installation Helper >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo ========================= >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo. >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo This script will help you install Java on your laptop. >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo. >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo Step 1: Download Java 17 LTS from: >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo https://adoptium.net/temurin/releases/?version=17 >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo. >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo Step 2: Choose your platform: >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo - Windows x64 (most common^) >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo - Download the .msi installer >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo. >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo Step 3: Install with default settings >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo Step 4: Restart your laptop >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo Step 5: Run LAPTOP-QUICK-START.bat >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo echo. >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo start https://adoptium.net/temurin/releases/?version=17 >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo pause >> "%DEPLOY_DIR%\INSTALL-JAVA-ON-LAPTOP.bat"
echo  ✓ INSTALL-JAVA-ON-LAPTOP.bat

:: Create README for laptop
echo  [6/6] Creating laptop README...
echo SafeClean - Laptop Deployment Package > "%DEPLOY_DIR%\README-LAPTOP.txt"
echo ====================================== >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo. >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo QUICK START: >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo 1. Run: LAPTOP-QUICK-START.bat >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo 2. If Java errors, run: INSTALL-JAVA-ON-LAPTOP.bat >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo 3. For detailed help, see: LAPTOP-DEPLOYMENT-GUIDE.md >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo. >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo MAIN APPLICATION: >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - SafeClean-Safe.bat (recommended launcher^) >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - SafeClean-2.0.0.jar (core application^) >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - SafeClean-Setup.exe (installer - may trigger antivirus^) >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo. >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo TROUBLESHOOTING: >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - Simple-Java-Test.bat (basic Java check^) >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - Java-Diagnostics.bat (comprehensive testing^) >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo. >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo REQUIREMENTS: >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - Windows 7/8/10/11 >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - Java 8+ (Java 17+ recommended^) >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo - Administrator privileges (recommended^) >> "%DEPLOY_DIR%\README-LAPTOP.txt"
echo  ✓ README-LAPTOP.txt

:: Show package contents
echo.
echo  ===================================================
echo    Package Created Successfully!
echo  ===================================================
echo.
echo  Package location: %DEPLOY_DIR%
echo  Package contents:
dir /b "%DEPLOY_DIR%"

echo.
echo  ===================================================
echo    LAPTOP DEPLOYMENT INSTRUCTIONS
echo  ===================================================
echo.
echo  1. TRANSFER TO LAPTOP:
echo     - Copy '%DEPLOY_DIR%' folder to laptop
echo     - Or create ZIP and transfer
echo     - Or push to GitHub and download
echo.
echo  2. ON LAPTOP:
echo     - Run: LAPTOP-QUICK-START.bat
echo     - If Java issues: INSTALL-JAVA-ON-LAPTOP.bat
echo     - Then run: SafeClean-Safe.bat
echo.
echo  3. FOR TROUBLESHOOTING:
echo     - Read: LAPTOP-DEPLOYMENT-GUIDE.md
echo     - Test: Simple-Java-Test.bat
echo     - Diagnose: Java-Diagnostics.bat
echo.

pause
endlocal
