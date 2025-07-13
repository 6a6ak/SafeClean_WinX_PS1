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

REM Create build directory
if not exist "build" mkdir build
if not exist "dist" mkdir dist

echo Compiling Java source...
javac -d build SafeCleanGUI.java

if errorlevel 1 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo Creating JAR file...
cd build
jar cfe ../dist/SafeCleanGUI.jar SafeCleanGUI *.class

if errorlevel 1 (
    echo ERROR: JAR creation failed
    cd ..
    pause
    exit /b 1
)

cd ..

echo.
echo ✅ Build completed successfully!
echo.
echo Output files:
echo   - dist/SafeCleanGUI.jar (Java application)
echo.
echo To run the application:
echo   java -jar dist/SafeCleanGUI.jar
echo.
echo To create a Windows executable, you can use:
echo   - Launch4j (recommended)
echo   - jpackage (Java 14+)
echo   - GraalVM native-image
echo.
pause
