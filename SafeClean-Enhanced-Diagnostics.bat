@echo off
:: SafeClean - Enhanced Antivirus-Friendly Launcher with Detailed Diagnostics
:: This script provides comprehensive Java environment testing and troubleshooting

setlocal enabledelayedexpansion
title SafeClean - Enhanced Diagnostic Launcher

:: Set console colors for better visibility
color 0A

echo.
echo  ===================================================
echo    SafeClean - Enhanced Diagnostic Launcher
echo    Version 2.0.0 - Comprehensive Java Testing
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

:: Detailed Java Environment Testing
echo  [STEP 1] Comprehensive Java Environment Check...
echo  ===============================================

:: Test 1: Basic Java availability
echo  Test 1: Checking if 'java' command is available...
java -version >nul 2>&1
set JAVA_BASIC_TEST=%errorlevel%
if %JAVA_BASIC_TEST% neq 0 (
    echo  ✗ FAILED: Java command not found in PATH
    echo.
    echo  TROUBLESHOOTING STEPS:
    echo  1. Check if Java is installed:
    echo     - Open Control Panel → Programs → Installed Programs
    echo     - Look for "OpenJDK" or "Java" entries
    echo.
    echo  2. Check Java installation directories:
    echo     - C:\Program Files\Eclipse Adoptium\
    echo     - C:\Program Files\Java\
    echo     - C:\Program Files (x86)\Java\
    echo.
    echo  3. Check if Java is in PATH:
    where java 2>nul
    if errorlevel 1 (
        echo     ✗ Java NOT found in PATH
        echo.
        echo  4. Add Java to PATH manually:
        echo     - Press Win+R, type 'sysdm.cpl'
        echo     - Advanced → Environment Variables
        echo     - System Variables → PATH → Edit
        echo     - Add Java bin directory (e.g., C:\Program Files\Eclipse Adoptium\jdk-17.0.13.11-hotspot\bin)
    ) else (
        echo     ✓ Java found in PATH at:
        where java
    )
    echo.
    echo  5. Download/Install Java 8+ from:
    echo     https://adoptium.net/temurin/releases/
    echo.
    pause
    exit /b 1
) else (
    echo  ✓ PASSED: Java command is available
)

:: Test 2: Get detailed Java version information
echo  Test 2: Getting detailed Java version information...
for /f "tokens=*" %%i in ('java -version 2^>^&1') do (
    echo     %%i
)
echo.

:: Test 3: Extract and validate Java version
echo  Test 3: Parsing and validating Java version...
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)
set JAVA_VERSION=%JAVA_VERSION:"=%
echo  Detected Java version: %JAVA_VERSION%

:: Test 4: Comprehensive version compatibility check
echo  Test 4: Checking version compatibility...

:: Check for very old Java versions (1.7 and below)
echo %JAVA_VERSION% | findstr /r "^1\.[0-7]\." >nul
if not errorlevel 1 (
    echo  ✗ FAILED: Java version is too old!
    echo  Found: %JAVA_VERSION%
    echo  Required: Java 8 (1.8) or higher
    echo.
    echo  SOLUTION:
    echo  1. Uninstall old Java version
    echo  2. Download latest Java from: https://adoptium.net/temurin/releases/
    echo  3. Install Java 17 or 21 (recommended)
    echo  4. Restart computer
    echo  5. Run this script again
    echo.
    pause
    exit /b 1
)

:: Check for Java 8+ formats
echo %JAVA_VERSION% | findstr /r "^1\.[89]\." >nul
if not errorlevel 1 (
    echo  ✓ PASSED: Java 8/9 format detected (%JAVA_VERSION%)
    goto :java_version_ok
)

echo %JAVA_VERSION% | findstr /r "^[12][0-9]\." >nul
if not errorlevel 1 (
    echo  ✓ PASSED: Modern Java format detected (%JAVA_VERSION%)
    goto :java_version_ok
)

echo  ⚠ WARNING: Unknown Java version format: %JAVA_VERSION%
echo  Proceeding anyway, but this might cause issues...

:java_version_ok
echo  ✓ Java version compatibility check passed!
echo.

:: Test 5: Java system properties and capabilities
echo  Test 5: Checking Java system properties...
java -XshowSettings:properties -version 2>nul | findstr /i "java.home java.version java.vendor" 2>nul
if errorlevel 1 (
    echo  ⚠ Could not retrieve detailed Java properties
) else (
    echo  ✓ Java properties retrieved successfully
)
echo.

:: Test 6: JAR execution capability test
echo  Test 6: Testing JAR execution capability...
echo. > test_manifest.txt
echo Main-Class: TestMain >> test_manifest.txt
echo. >> test_manifest.txt

echo public class TestMain { public static void main(String[] args) { System.out.println("JAR_TEST_SUCCESS"); } } > TestMain.java

javac TestMain.java 2>nul
if errorlevel 1 (
    echo  ✗ Java compilation test failed
    echo  This might indicate Java development tools are missing
) else (
    jar cfe test.jar TestMain TestMain.class >nul 2>&1
    if errorlevel 1 (
        echo  ✗ JAR creation test failed
    ) else (
        java -jar test.jar 2>nul | findstr "JAR_TEST_SUCCESS" >nul
        if errorlevel 1 (
            echo  ✗ JAR execution test failed
        ) else (
            echo  ✓ JAR execution test passed
        )
    )
)

:: Cleanup test files
del TestMain.java TestMain.class test.jar test_manifest.txt 2>nul

echo.
echo  [STEP 2] Locating SafeClean Application...
echo  =========================================

:: Check if SafeClean JAR exists with detailed error reporting
if exist "SafeClean-2.0.0.jar" (
    set JAR_FILE=SafeClean-2.0.0.jar
    echo  ✓ Found SafeClean at: %JAR_FILE%
) else if exist "target\SafeClean-2.0.0.jar" (
    set JAR_FILE=target\SafeClean-2.0.0.jar
    echo  ✓ Found SafeClean at: %JAR_FILE%
) else (
    echo  ✗ ERROR: SafeClean application not found!
    echo.
    echo  SEARCHED LOCATIONS:
    echo  - %CD%\SafeClean-2.0.0.jar
    echo  - %CD%\target\SafeClean-2.0.0.jar
    echo.
    echo  CURRENT DIRECTORY CONTENTS:
    dir /b *.jar 2>nul
    if errorlevel 1 (
        echo  No JAR files found in current directory
    )
    echo.
    echo  TARGET DIRECTORY CONTENTS:
    if exist "target" (
        dir /b target\*.jar 2>nul
        if errorlevel 1 (
            echo  No JAR files found in target directory
        )
    ) else (
        echo  Target directory does not exist
    )
    echo.
    echo  TROUBLESHOOTING:
    echo  1. Make sure you're in the correct directory
    echo  2. Run: mvn clean package
    echo  3. Check if build completed successfully
    echo  4. Verify target\SafeClean-2.0.0.jar exists
    echo.
    pause
    exit /b 1
)

:: Test JAR file integrity
echo  Testing JAR file integrity...
java -jar "%JAR_FILE%" --help >nul 2>&1
set JAR_TEST_RESULT=%errorlevel%
if %JAR_TEST_RESULT% equ 0 (
    echo  ✓ JAR file appears to be valid
) else (
    echo  ⚠ JAR file test returned exit code: %JAR_TEST_RESULT%
    echo  This might be normal if SafeClean doesn't support --help flag
)

echo.
echo  [STEP 3] Launching SafeClean...
echo  ===============================

echo  Starting SafeClean with enhanced monitoring...
echo  Working Directory: %CD%
echo  JAR File: %JAR_FILE%
echo  Java Command: java -Xms64m -Xmx512m -Dfile.encoding=UTF-8 -Djava.awt.headless=false -jar "%JAR_FILE%"
echo.
echo  ===================================================
echo    SafeClean is now starting...
echo    Enhanced monitoring active - watch for errors below
echo  ===================================================
echo.

:: Run with enhanced error reporting
java -Xms64m -Xmx512m -Dfile.encoding=UTF-8 -Djava.awt.headless=false -jar "%JAR_FILE%"
set SAFECLEAN_EXIT_CODE=%errorlevel%

echo.
echo  ===================================================
echo    SafeClean Execution Completed
echo  ===================================================

:: Detailed exit code analysis
if %SAFECLEAN_EXIT_CODE% equ 0 (
    echo  ✓ SafeClean completed successfully (exit code: 0)
    echo  This indicates normal program termination.
    timeout /t 3 /nobreak >nul
) else (
    echo  ✗ SafeClean exited with error code: %SAFECLEAN_EXIT_CODE%
    echo.
    echo  COMMON EXIT CODE MEANINGS:
    if %SAFECLEAN_EXIT_CODE% equ 1 (
        echo  Exit Code 1: General application error
        echo  - Check if you have administrator privileges
        echo  - Verify all required files are present
        echo  - Check system compatibility
    ) else if %SAFECLEAN_EXIT_CODE% equ 2 (
        echo  Exit Code 2: Invalid command line arguments
        echo  - This is unusual for GUI applications
    ) else if %SAFECLEAN_EXIT_CODE% equ -1 (
        echo  Exit Code -1: Abnormal termination
        echo  - Possible Java runtime error
        echo  - Check Java installation integrity
    ) else (
        echo  Exit Code %SAFECLEAN_EXIT_CODE%: Application-specific error
        echo  - Check application logs if available
        echo  - Try running as administrator
    )
    echo.
    echo  TROUBLESHOOTING SUGGESTIONS:
    echo  1. Right-click this script and "Run as administrator"
    echo  2. Check Windows Event Viewer for detailed error logs
    echo  3. Temporarily disable antivirus real-time protection
    echo  4. Verify Java installation: java -version
    echo  5. Reinstall Java if necessary
    echo  6. Check system requirements and compatibility
    echo.
    pause
)

endlocal
echo.
echo  Enhanced diagnostics completed. Press any key to exit...
pause >nul
