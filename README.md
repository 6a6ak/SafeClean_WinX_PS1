# SafeClean - Professional Windows System Cleanup Tool

A modern Java Swing desktop application for comprehensive Windows system cleanup operations.

## 🚀 Features

- 🛠️ **Modern GUI Interface** with custom icon and professional design
- 🎨 **Enhanced User Experience** with gradient backgrounds and styled buttons
- 🎯 **Individual Operations** - Run specific cleanup tasks with precision
- 🔄 **Run All Option** - Execute all cleanup operations sequentially
- 📊 **Real-time Output** - Live feedback and progress monitoring
- ⚠️ **Safety Warnings** - Built-in warnings and confirmations
- 🔐 **Administrator Detection** - Automatic privilege checking
- ☕ **Pure Java** - Cross-platform compatible JAR application

## 📁 Project Structure

```
SafeClean_WinX_PS1/
├── pom.xml                           # Maven configuration
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── safeclean/
│                   └── SafeCleanGUI.java    # Main application
├── target/                           # Build output directory
│   └── SafeClean-2.0.0.jar         # Shaded JAR with dependencies
├── SafeClean-Safe.bat               # Windows batch launcher
├── SafeClean-Safe.py                # Python launcher
├── Java-Diagnostics.bat             # Java environment tester
└── README.md                         # This file
```

## 🛠️ Building the Application

### Prerequisites
- **Java JDK 8 or higher** - Target compatibility maintained
- **Maven 3.6 or higher** - For automated builds and packaging

### Maven Build
```bash
# Build JAR with all dependencies
mvn clean package
```
Creates: `target/SafeClean-2.0.0.jar` - Shaded JAR with all dependencies
### Alternative Build (Manual)
```bash
# Use the simple build script
.\build.bat
```
Creates: `dist/SafeCleanGUI.jar`

## 🏃 Running the Application

### Option 1: Launcher Scripts (Recommended)
**Best user experience with Java environment validation:**

```bash
# Antivirus-Safe Batch Launcher (recommended)
SafeClean-Safe.bat

# Python Launcher (requires Python 3.6+)
SafeClean-Safe.py

# Windows Batch Launcher
SafeClean-Launcher.bat

# PowerShell Launcher
SafeClean-Launcher.ps1
```

**Right-click → "Run as administrator"** for best results.

### Option 2: Direct JAR Execution
```bash
# Direct execution - requires Java 8+ to be installed
java -jar target/SafeClean-2.0.0.jar

# Or with specific JVM options
java -Xms64m -Xmx512m -jar target/SafeClean-2.0.0.jar
```

### Option 3: From Source
```bash
java -cp target/classes com.safeclean.SafeCleanGUI
```

### Option 2: Native Windows Executable
```bash
# Direct execution - requires Java 8+ to be installed
target\SafeClean.exe
```

**⚠️ Antivirus Warning**: This file may trigger false positives (`wacapew.C!ML`). See [Antivirus False Positive Guide](ANTIVIRUS-FALSE-POSITIVE.md) for solutions.

**Note**: If you get "Java Runtime Environment version 1.8.0 not found" or "Java too old", see:
- [Java Runtime Guide](JAVA-RUNTIME-GUIDE.md) for basic Java issues
- [Java Too Old Fix Guide](JAVA-TOO-OLD-FIX.md) for version compatibility
- Run `Java-Diagnostics.bat` to test your Java installation

### Option 3: Professional Installer
```bash
# Run the NSIS installer for professional installation
.\SafeClean-Setup.exe
```

**⚠️ Installer Antivirus Warning**: The installer may be flagged as suspicious by antivirus software.

**🚀 If installer is blocked by antivirus:**
1. **Quick Solution**: Use `.\SafeClean-Safe.bat` instead (zero antivirus issues)
2. **Create Shortcut**: Run `.\Create-Shortcut.bat` for desktop shortcut
3. **Manual Install**: See [Bypass Installer Guide](BYPASS-INSTALLER-GUIDE.md)
4. **Fix Antivirus**: See [Installer Antivirus Fix](INSTALLER-ANTIVIRUS-FIX.md)

### Option 4: JAR File (Cross-platform)
```bash
java -jar target/SafeClean-2.0.0.jar
```

### Option 5: From Source
```bash
java -cp target/classes com.safeclean.SafeCleanGUI
```

### Option 6: PowerShell Installer
```powershell
# Alternative professional installer
.\Install-SafeClean.ps1
```

## 🎨 Custom Features

### Professional Branding
- **Rebranded from "SafeClean WinX" to "SafeClean"** for broader Windows compatibility
- Custom-drawn cleaning brush icon with professional appearance
- Gradient background with modern color schemes
- "SC" text overlay for brand recognition
- High-quality 64x64 icon rendering

### Enhanced UI Components
- **Header**: Gradient background with logo and safety warnings
- **Operation Buttons**: Color-coded for safety levels (Green=Safe, Yellow=Advanced, Red=Permanent)
- **Real-time Console**: Professional output with live progress updates
- **Status Bar**: Progress tracking with informative status messages
- **Menu System**: File menu with About dialog and enhanced functionality

### Professional Installation Experience
- **Modern UI Installer**: NSIS-based setup with professional appearance
- **Component Selection**: Choose desktop shortcuts, Start Menu integration
- **Registry Integration**: Proper Windows "Add/Remove Programs" support
- **Uninstaller**: Clean removal with registry cleanup
- **Administrator Handling**: Automatic privilege elevation when needed

## 🎯 Cleanup Operations

1. **Clean Temporary Files** - Removes user and system temp files
2. **Clean Windows Update Cache** - Clears Windows Update downloads
3. **Clean Thumbnail Cache** - Removes Explorer thumbnail cache
4. **Clear Event Logs** - Clears Windows Event Logs (filtered for safety)
5. **Reduce WinSxS Folder** - Advanced component store cleanup
6. **Disable Hibernation** - Removes hiberfil.sys and disables hibernation
7. **Clean Recycle Bin** - Permanently empties recycle bin

## 🔧 Development

### Maven Build System
```bash
# Clean and compile
mvn clean compile

# Run tests (if any)
mvn test

# Package with all dependencies
mvn package

# Create Windows executable (Launch4j integration)
mvn package  # Automatically creates SafeClean.exe

# Build installer (automated)
.\Build-Setup.bat
```

### Technology Stack
- **Java 8** - Target compatibility for broad Windows support
- **Swing GUI** - Native cross-platform interface
- **Maven Shade Plugin** - Dependency bundling
- **Launch4j** - Java-to-EXE wrapper for native Windows execution
- **NSIS** - Professional Windows installer creation
- **PowerShell** - Alternative installer system

### IDE Setup
- **Import**: As Maven project
- **Main Class**: `com.safeclean.SafeCleanGUI`
- **Java Version**: 8+ (maintains Windows 7/8/10/11 compatibility)
- **Build Target**: Windows x86/x64 systems

## 📦 Distribution Options

SafeClean provides multiple professional distribution methods:

### 1. Portable Executable
- **File**: `SafeClean.exe` (62KB)
- **Type**: Standalone native Windows executable
- **Benefits**: No installation required, runs anywhere
- **Use Case**: Quick deployment, portable usage

### 2. Professional Installer
- **File**: `SafeClean-Setup.exe` (143KB)
- **Type**: NSIS-based Windows installer with Modern UI
- **Features**:
  - Installation to `C:\Program Files\SafeClean\`
  - Desktop shortcut creation (optional)
  - Start Menu integration with folder
  - Registry entries for "Add/Remove Programs"
  - Professional uninstaller with cleanup
  - Administrator privilege handling
- **Use Case**: Enterprise deployment, end-user distribution

### 3. PowerShell Installer
- **File**: `Install-SafeClean.ps1`
- **Type**: Alternative professional installer script
- **Features**:
  - Program Files installation
  - System PATH integration
  - Registry entries
  - Shortcut creation
- **Use Case**: System administrator deployment

### 4. Cross-Platform JAR
- **File**: `SafeClean-2.0.0.jar` (23KB)
- **Type**: Java application with all dependencies
- **Benefits**: Runs on any system with Java 8+
- **Use Case**: Cross-platform deployment

## ⚠️ Important Notes

- **Java Runtime Requirement**: SafeClean requires **Java 8 or higher** to run
- **Java Version Issues**: If you get "Java too old" errors, see [Java Too Old Fix Guide](JAVA-TOO-OLD-FIX.md)
- **Java Diagnostics**: Run `Java-Diagnostics.bat` to check your Java installation
- **Antivirus False Positives**: Both `SafeClean.exe` and `SafeClean-Setup.exe` may trigger antivirus warnings - these are false positives
- **Installer Antivirus Issues**: If the installer is flagged, see [Installer Antivirus Fix](INSTALLER-ANTIVIRUS-FIX.md) or [Bypass Installer Guide](BYPASS-INSTALLER-GUIDE.md)
- **Antivirus-Safe Alternatives**: Use `SafeClean-Safe.bat`, `SafeClean-Safe.py`, or run `Create-Shortcut.bat` to avoid false positives
- **Administrator Privileges**: Required for proper functionality and system access
- **Windows Compatibility**: Designed and tested for Windows 7/8/10/11
- **Data Deletion Warning**: Some operations permanently delete files - review before execution
- **System Modifications**: Advanced operations modify Windows components and registry
- **Network Requirements**: None - fully offline operation
- **Troubleshooting**: See [Java Runtime Guide](JAVA-RUNTIME-GUIDE.md), [Antivirus False Positive Guide](ANTIVIRUS-FALSE-POSITIVE.md), [Java Too Old Fix](JAVA-TOO-OLD-FIX.md), and [Installer Fix](INSTALLER-ANTIVIRUS-FIX.md)

## 🚀 Quick Start Guide

### For End Users (Recommended)
1. **Download**: `SafeClean-Setup.exe` from releases
2. **Install**: Run installer with administrator privileges
3. **Launch**: Use desktop shortcut or Start Menu entry (automatic Java detection)
4. **Java Setup**: If prompted, follow the automatic Java installation guide
5. **Select Operations**: Choose individual tasks or "Run All"
6. **Monitor**: Watch real-time progress output

### For Portable Use
1. **Download**: `SafeClean.exe` and launcher scripts from releases
2. **Check Java**: Ensure Java 8+ is installed, or use `SafeClean-Launcher.bat`
3. **Run**: Right-click → "Run as administrator"
4. **Use**: Direct operation without installation

### For Developers
1. **Clone**: `git clone https://github.com/6a6ak/SafeClean_WinX_PS1.git`
2. **Build**: `.\Build-Setup.bat` or `mvn package`
3. **Test**: Run generated executables
4. **Distribute**: Share installer or portable EXE with launcher scripts

## 📄 License

MIT License - see LICENSE file for details.

## � Build Requirements Summary

| Component | Version | Purpose |
|-----------|---------|---------|
| Java JDK | 8+ | Application compilation and runtime |
| Maven | 3.6+ | Build automation and dependency management |
| NSIS | 3.11 | Professional Windows installer creation |
| Launch4j | 3.14 | Java-to-EXE wrapper (auto-downloaded) |
| Windows OS | 7+ | Target platform and build environment |

## 📊 File Size Comparison

| File Type | Size | Description |
|-----------|------|-------------|
| `SafeClean.exe` | 62KB | Native Windows executable |
| `SafeClean-Setup.exe` | 143KB | Professional installer |
| `SafeClean-2.0.0.jar` | 23KB | Cross-platform Java application |
| Source Code | ~50KB | Complete project source |

## �👨‍💻 Author

Created by **6a6ak** - Professional Windows system maintenance solution.

**Repository**: https://github.com/6a6ak/SafeClean_WinX_PS1

---

⭐ **Professional. Reliable. Effective. Now with Modern Installer.** ⭐
