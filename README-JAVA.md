# SafeClean WinX - Java GUI Application

A modern Java Swing desktop application that provides a graphical interface for the SafeClean WinX PowerShell cleanup operations.

## Features

- 🖥️ **Modern GUI Interface** - User-friendly desktop application
- 🎯 **Individual Operations** - Run specific cleanup tasks
- 🚀 **Run All Option** - Execute all cleanup operations sequentially
- 📊 **Real-time Output** - Live feedback and progress monitoring
- ⚠️ **Safety Warnings** - Built-in warnings and confirmations
- 🎨 **Color-coded Buttons** - Visual distinction for operation types
- 📝 **Detailed Logging** - Comprehensive output log with timestamps

## GUI Components

### Operation Buttons
- **Green Buttons**: Standard safe operations (Temp files, Update cache, Thumbnails)
- **Yellow Buttons**: Advanced operations requiring caution (WinSxS, Hibernation)
- **Red Button**: Permanent deletion operations (Recycle Bin)

### Output Panel
- Real-time command output
- Scrollable log area
- Automatic scroll to latest output
- Color-coded status messages

## Requirements

- **Java Runtime Environment (JRE) 8 or higher**
- **Windows Operating System** (Windows 10/11 recommended)
- **Administrator privileges** required for proper functionality

## Building from Source

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command Prompt or PowerShell

### Build Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/6a6ak/SafeClean_WinX_PS1.git
   cd SafeClean_WinX_PS1
   ```

2. **Run the build script**:
   ```cmd
   build.bat
   ```

3. **The built JAR will be in the `dist` folder**:
   ```
   dist/SafeCleanGUI.jar
   ```

### Manual Build
If you prefer to build manually:

```cmd
# Compile
javac -d build SafeCleanGUI.java

# Create JAR
cd build
jar cfe ../dist/SafeCleanGUI.jar SafeCleanGUI *.class
```

## Running the Application

### From JAR File
```cmd
java -jar dist/SafeCleanGUI.jar
```

### Running as Administrator
**Important**: Right-click on Command Prompt and select "Run as Administrator", then run the JAR file.

## Creating an Executable (.exe)

You can convert the JAR to a Windows executable using several tools:

### Option 1: Launch4j (Recommended)
1. Download Launch4j from http://launch4j.sourceforge.net/
2. Create a configuration file pointing to your JAR
3. Generate the .exe file
4. The exe will automatically require admin privileges

### Option 2: jpackage (Java 14+)
```cmd
jpackage --input dist --name SafeCleanGUI --main-jar SafeCleanGUI.jar --main-class SafeCleanGUI --type exe --win-console
```

### Option 3: GraalVM Native Image
For a truly native executable with faster startup times.

## Application Structure

```
SafeCleanGUI/
├── Header Panel          # Title and warnings
├── Main Panel           
│   ├── Buttons Panel    # Cleanup operation buttons
│   └── Output Panel     # Real-time log output
└── Status Panel         # Progress bar and status info
```

## Operation Details

Each button corresponds to the PowerShell script operations:

1. **Clean Temporary Files** - Removes user and system temp files
2. **Clean Windows Update Cache** - Clears Windows Update downloads
3. **Clean Thumbnail Cache** - Removes Explorer thumbnail cache
4. **Clear Event Logs** - Clears all Windows Event Logs
5. **Reduce WinSxS Folder** - Advanced component store cleanup
6. **Disable Hibernation** - Removes hiberfil.sys and disables hibernation
7. **Clean Recycle Bin** - Permanently empties recycle bin

## Safety Features

- **Administrator Check**: Warns if not running as admin
- **Confirmation Dialogs**: Confirms dangerous operations
- **Error Handling**: Graceful handling of failed operations
- **Operation Status**: Real-time feedback on operation progress
- **Button States**: Disables buttons during operations to prevent conflicts

## Screenshots

*Note: Add screenshots of your application here when ready*

## Troubleshooting

### Common Issues

**"Java is not recognized as an internal or external command"**
- Install Java JDK/JRE and ensure it's in your system PATH

**Operations fail silently**
- Ensure you're running as Administrator
- Check Windows permissions for the target directories

**GUI doesn't start**
- Verify Java version with `java -version`
- Check if any antivirus is blocking the application

**PowerShell execution errors**
- The application automatically sets execution policy bypass
- Ensure PowerShell is available on the system

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

Created by 6a6ak for safe and efficient Windows system maintenance.

---

⭐ If this GUI application helped you, please give it a star!
