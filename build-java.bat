@echo off
echo Building SafeClean WinX Java Application...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 8 or higher
    pause
    exit /b 1
)

REM Create directories
if not exist "target" mkdir target
if not exist "target\classes" mkdir target\classes

echo Compiling Java source...
javac -d target\classes com\safeclean\SafeCleanGUI.java

if errorlevel 1 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo Creating JAR file...
cd target\classes
jar cfe ..\SafeCleanWinX.jar com.safeclean.SafeCleanGUI com\safeclean\*.class

if errorlevel 1 (
    echo ERROR: JAR creation failed
    cd ..\..
    pause
    exit /b 1
)

cd ..\..

echo.
echo ✅ Build completed successfully!
echo.
echo Output files:
echo   - target\SafeCleanWinX.jar (Java application)
echo.
echo To run the application:
echo   java -jar target\SafeCleanWinX.jar
echo.
echo Note: Make sure to run as Administrator for full functionality
echo.
pause
