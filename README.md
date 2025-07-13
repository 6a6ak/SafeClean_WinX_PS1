# SafeClean WinX - Java Application

A professional Java Swing desktop application for Windows system cleanup operations.

## 🚀 Features

- �️ **Modern GUI Interface** with custom icon
- 🎨 **Professional Design** with gradient backgrounds and styled buttons
- 🎯 **Individual Operations** - Run specific cleanup tasks
- � **Run All Option** - Execute all cleanup operations sequentially
- � **Real-time Output** - Live feedback and progress monitoring
- ⚠️ **Safety Warnings** - Built-in warnings and confirmations
- 🔐 **Administrator Detection** - Automatic privilege checking

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
├── target/                           # Build output
│   ├── SafeCleanWinX-2.0.0.jar     # Executable JAR
│   └── SafeCleanWinX.exe            # Windows executable
└── README.md                         # This file
```

## 🛠️ Building the Application

### Prerequisites
- Java JDK 8 or higher
- Maven 3.6 or higher (optional, for advanced builds)

### Simple Build (Using Maven)
```bash
mvn clean package
```

### Manual Build
```bash
# Compile
javac -d target/classes -cp . com/safeclean/SafeCleanGUI.java

# Create JAR
jar cfe target/SafeCleanWinX.jar com.safeclean.SafeCleanGUI -C target/classes .
```

## 🏃 Running the Application

### From JAR
```bash
java -jar target/SafeCleanWinX-2.0.0.jar
```

### From Source
```bash
java -cp target/classes com.safeclean.SafeCleanGUI
```

### Windows Executable
After building with Maven, run:
```
target/SafeCleanWinX.exe
```

## 🎨 Custom Features

### Professional Icon
- Custom-drawn cleaning brush icon
- Gradient background with professional colors
- "SC" text overlay for brand recognition
- 64x64 high-quality rendering

### Enhanced UI
- **Header**: Gradient background with logo and warnings
- **Buttons**: Color-coded operations (Green=Safe, Yellow=Advanced, Red=Permanent)
- **Output**: Professional console-style output with real-time updates
- **Status**: Progress tracking with informative messages

## 🎯 Cleanup Operations

1. **Clean Temporary Files** - Removes user and system temp files
2. **Clean Windows Update Cache** - Clears Windows Update downloads
3. **Clean Thumbnail Cache** - Removes Explorer thumbnail cache
4. **Clear Event Logs** - Clears Windows Event Logs (filtered for safety)
5. **Reduce WinSxS Folder** - Advanced component store cleanup
6. **Disable Hibernation** - Removes hiberfil.sys and disables hibernation
7. **Clean Recycle Bin** - Permanently empties recycle bin

## 🔧 Development

### Maven Commands
```bash
# Clean and compile
mvn clean compile

# Run tests (if any)
mvn test

# Package JAR
mvn package

# Create Windows executable
mvn package  # Includes Launch4j execution
```

### IDE Setup
- Import as Maven project
- Main class: `com.safeclean.SafeCleanGUI`
- Java version: 8+

## 📦 Distribution

The build process creates:
1. **JAR file**: `target/SafeCleanWinX-2.0.0.jar` - Cross-platform Java application
2. **EXE file**: `target/SafeCleanWinX.exe` - Windows native executable
3. **Icon**: Embedded custom icon for professional appearance

## ⚠️ Important Notes

- **Administrator privileges required** for proper functionality
- **Windows-specific operations** - designed for Windows 10/11
- **Data deletion warning** - some operations permanently delete files
- **System modification** - advanced operations modify Windows components

## 🚀 Quick Start

1. **Download/Clone** the repository
2. **Build** using `mvn package` or manual compilation
3. **Run as Administrator** - right-click executable and "Run as administrator"
4. **Select operations** - use individual buttons or "Run All"
5. **Monitor progress** - watch real-time output log

## 📄 License

MIT License - see LICENSE file for details.

## 👨‍💻 Author

Created by **6a6ak** - Professional Windows system maintenance tool.

---

⭐ **Professional. Reliable. Effective.** ⭐
