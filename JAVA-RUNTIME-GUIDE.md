# SafeClean - Java Runtime Environment Guide

## 🔍 Java Runtime Requirement Issue

SafeClean is built with Java and requires **Java Runtime Environment (JRE) 8 or higher** to run. This document explains the issue and provides multiple solutions.

## ⚠️ The Problem

When you try to run `SafeClean.exe`, you might see:
- "Java Runtime Environment version 1.8.0 not found"
- "This application requires Java to run"
- The application fails to start

## 🚀 Solutions (Multiple Options)

### Option 1: Automatic Java Detection (Recommended)
Use the **launcher scripts** included with SafeClean:

1. **Windows Batch Launcher**: `SafeClean-Launcher.bat`
   - Double-click to run
   - Automatically detects Java installation
   - Provides download links if Java is missing
   - Offers to open Java download page

2. **PowerShell Launcher**: `SafeClean-Launcher.ps1`
   - Right-click → "Run with PowerShell"
   - More advanced detection and error handling
   - Better user experience with colored output

### Option 2: Install Java Runtime Environment

#### Quick Installation (Recommended)
1. **Visit**: https://adoptium.net/temurin/releases/?version=8
2. **Download**: OpenJDK 8 with HotSpot JVM for Windows
3. **Install**: Run the installer as Administrator
4. **Restart**: Restart your computer after installation
5. **Run**: Execute `SafeClean.exe` again

#### Alternative Installation Methods

**Via Chocolatey (Windows Package Manager):**
```powershell
# Install Chocolatey first (if not installed)
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))

# Install OpenJDK 8
choco install openjdk8
```

**Via Microsoft Store:**
- Search for "OpenJDK" in Microsoft Store
- Install OpenJDK 8 or higher

### Option 3: Use JAR File Directly
If you have Java installed but the EXE doesn't work:
```bash
java -jar SafeClean-2.0.0.jar
```

## 📁 Distribution Files Explained

### For End Users
- **`SafeClean-Setup.exe`** (150KB) - Complete installer with launchers
- **`SafeClean-Launcher.bat`** - Automatic Java detection script
- **`SafeClean-Launcher.ps1`** - Advanced PowerShell launcher

### For Advanced Users
- **`SafeClean.exe`** (62KB) - Direct executable (requires Java)
- **`SafeClean-2.0.0.jar`** (23KB) - Cross-platform Java application

## 🔧 Troubleshooting

### Java Detection Issues
```bash
# Check if Java is installed
java -version

# Check Java installation path
where java

# Test Java functionality
java -cp . com.safeclean.SafeCleanGUI
```

### Common Problems

**Problem**: "java is not recognized as an internal or external command"
**Solution**: Java is not installed or not in PATH. Install Java and restart.

**Problem**: "Java version is too old"
**Solution**: Update to Java 8 or higher.

**Problem**: "Could not find or load main class"
**Solution**: Use the launcher scripts or reinstall SafeClean.

## 📝 Installation Recommendations

### For System Administrators
1. **Pre-install Java 8** on target systems
2. **Use SafeClean-Setup.exe** for automated deployment
3. **Include launcher scripts** for error handling

### For Individual Users
1. **Use SafeClean-Setup.exe** installer (includes all components)
2. **Run SafeClean-Launcher.bat** for automatic Java detection
3. **Install Java if prompted** from official sources

### For Portable Use
1. **Ensure Java 8+ is installed** on target system
2. **Use SafeClean.exe directly** or
3. **Include launcher scripts** for better user experience

## 🎯 Why Java is Required

SafeClean is built with:
- **Java Swing GUI** - Cross-platform native interface
- **Java System APIs** - Windows integration and file operations
- **Platform Independence** - Runs on Windows 7/8/10/11

## 🔐 Security Notes

- **Download Java only** from official sources (adoptium.net, oracle.com)
- **Avoid third-party Java installers** that may include bloatware
- **Keep Java updated** for security patches
- **SafeClean does not** bundle Java to keep file size small

## 📞 Support

If you continue to have Java-related issues:
1. Try the **launcher scripts** first
2. Verify **Java 8+ is installed** correctly
3. **Restart your computer** after Java installation
4. Run SafeClean as **Administrator** if needed

---

**Note**: Future versions may include bundled JRE options for truly standalone operation.
