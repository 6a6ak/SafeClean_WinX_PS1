# SafeClean - Windows System Cleanup Tool

A Java Swing desktop application for Windows system cleanup operations.

## 🚀 Features

- Modern GUI interface with professional design
- Individual and batch cleanup operations
- Real-time output monitoring
- Administrator privilege detection
- Safe operation warnings and confirmations

## 📁 Project Structure

```
SafeClean/
├── pom.xml                           # Maven configuration
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── safeclean/
│                   └── SafeCleanGUI.java    # Main application
├── target/                           # Build output
│   └── SafeClean-2.0.0.jar         # Executable JAR
└── README.md                         # This file
```

## 🛠️ Building

### Prerequisites
- Java JDK 8 or higher
- Maven 3.6 or higher

### Build Command
```bash
mvn clean package
```

This creates `target/SafeClean-2.0.0.jar` - a standalone JAR with all dependencies.

## 🏃 Running

```bash
# Direct execution
java -jar target/SafeClean-2.0.0.jar

# With specific memory settings
java -Xms64m -Xmx512m -jar target/SafeClean-2.0.0.jar
```

**Note**: Run as administrator for full functionality.

## ⚠️ Requirements

- **Java 8+** - Required runtime environment
- **Windows OS** - Designed for Windows system cleanup
- **Administrator privileges** - Required for system operations

## 📄 License

MIT License - see LICENSE file for details.

## 👨‍💻 Author

Created by **Babak Ahari**

**Website**: Clean.tricks.se  
**GitHub**: https://github.com/6a6ak/SafeClean
