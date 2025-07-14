@echo off
title Java Version Diagnostics

echo ===============================================
echo  SafeClean - Java Version Diagnostics
echo ===============================================
echo.

echo [1] Testing Java installation...
java -version
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Java command not found!
    echo Java is not installed or not in PATH.
    goto :end
)

echo.
echo [2] Detailed Java information...
java -XshowSettings:properties -version 2>&1 | findstr "java.version\|java.vm.version\|java.runtime.version"

echo.
echo [3] Testing JAR execution capability...
echo Creating test JAR...

:: Create a simple test class
mkdir temp_test 2>nul
echo public class TestJava { > temp_test\TestJava.java
echo     public static void main(String[] args) { >> temp_test\TestJava.java
echo         System.out.println("Java execution test successful!"); >> temp_test\TestJava.java
echo         System.out.println("Java version: " + System.getProperty("java.version")); >> temp_test\TestJava.java
echo     } >> temp_test\TestJava.java
echo } >> temp_test\TestJava.java

:: Compile test class
javac temp_test\TestJava.java 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java compilation failed!
    echo This indicates a problem with your Java installation.
    goto :cleanup
)

:: Create test JAR
cd temp_test
jar cfe TestJava.jar TestJava *.class 2>&1
if %errorlevel% neq 0 (
    echo ERROR: JAR creation failed!
    goto :cleanup
)

:: Test JAR execution
echo.
echo Testing JAR execution...
java -jar TestJava.jar
if %errorlevel% neq 0 (
    echo ERROR: JAR execution failed!
    echo This indicates a compatibility issue.
)

cd ..

:cleanup
echo.
echo [4] Cleaning up test files...
rmdir /s /q temp_test 2>nul

echo.
echo [5] Checking for SafeClean JAR...
if exist "SafeClean-2.0.0.jar" (
    echo ✓ Found: SafeClean-2.0.0.jar
) else if exist "target\SafeClean-2.0.0.jar" (
    echo ✓ Found: target\SafeClean-2.0.0.jar
) else (
    echo ✗ SafeClean JAR not found!
)

echo.
echo [6] Testing SafeClean JAR (if found)...
if exist "target\SafeClean-2.0.0.jar" (
    echo Attempting to run SafeClean...
    java -jar target\SafeClean-2.0.0.jar
    echo SafeClean execution completed with exit code: %errorlevel%
) else if exist "SafeClean-2.0.0.jar" (
    echo Attempting to run SafeClean...
    java -jar SafeClean-2.0.0.jar
    echo SafeClean execution completed with exit code: %errorlevel%
) else (
    echo Cannot test SafeClean - JAR file not found.
)

:end
echo.
echo ===============================================
echo  Diagnostics completed.
echo  
echo  If you see errors above:
echo  1. Install/update Java 8+ from:
echo     https://adoptium.net/temurin/releases/
echo  2. Restart your computer
echo  3. Run this diagnostic again
echo ===============================================
pause
