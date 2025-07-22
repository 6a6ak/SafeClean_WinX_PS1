# Contributing to SafeClean

Thank you for your interest in contributing to SafeClean! This document provides guidelines and instructions for contributing to the project.

## 🌟 Ways to Contribute

- **Bug Reports**: Help us identify and fix issues
- **Feature Requests**: Suggest new cleanup operations or improvements
- **Code Contributions**: Submit fixes or enhancements
- **Documentation**: Improve documentation and guides
- **Testing**: Test the application on different Windows versions

## 🐛 Reporting Bugs

When reporting bugs, please include:

1. **Windows Version** (e.g., Windows 10 21H2, Windows 11)
2. **SafeClean Version** (e.g., v2.0.1)
3. **Steps to Reproduce** the issue
4. **Expected Behavior** vs **Actual Behavior**
5. **Screenshots** or **Log Output** if applicable
6. **Administrator Privileges** status when the issue occurred

### Bug Report Template

```
**Environment:**
- Windows Version: 
- SafeClean Version: 
- Administrator Mode: Yes/No

**Description:**
A clear description of the bug.

**Steps to Reproduce:**
1. Step one
2. Step two
3. Step three

**Expected Behavior:**
What you expected to happen.

**Actual Behavior:**
What actually happened.

**Additional Information:**
Any additional context, screenshots, or logs.
```

## 💡 Feature Requests

For feature requests, please provide:

1. **Use Case**: Why is this feature needed?
2. **Description**: Detailed description of the proposed feature
3. **Benefits**: How will this improve SafeClean?
4. **Implementation Ideas**: Any thoughts on how to implement it

## 🔧 Development Setup

### Prerequisites

- **Java Development Kit (JDK)** 8 or later
- **Apache Maven** 3.6 or later
- **Git** for version control
- **Windows** development environment

### Getting Started

1. **Fork** the repository on GitHub
2. **Clone** your fork:
   ```bash
   git clone https://github.com/YOUR_USERNAME/SafeClean.git
   cd SafeClean
   ```

3. **Switch to Java branch** for development:
   ```bash
   git checkout java
   ```

4. **Build the project**:
   ```bash
   mvn clean compile
   ```

5. **Run the application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.safeclean.SafeCleanGUI"
   ```

### Project Structure

```
SafeClean/
├── src/main/java/com/safeclean/
│   └── SafeCleanGUI.java          # Main application class
├── src/main/resources/
│   ├── icons/                     # Application icons
│   └── images/                    # UI images
├── pom.xml                        # Maven configuration
└── README.md                      # Project documentation
```

## 📝 Code Style Guidelines

### Java Code Style

- Use **4 spaces** for indentation (no tabs)
- Follow **Java naming conventions**:
  - Classes: `PascalCase`
  - Methods/Variables: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
- Add **JavaDoc comments** for public methods
- Keep lines under **120 characters**
- Use **meaningful variable names**

### Example Code Style

```java
/**
 * Cleans temporary files from the system.
 * 
 * @param includeSystemTemp whether to include system temp directory
 * @return number of files cleaned
 */
public int cleanTemporaryFiles(boolean includeSystemTemp) {
    int filesCleanedCount = 0;
    
    // Clean user temp directory
    String userTempPath = System.getProperty("java.io.tmpdir");
    filesCleanedCount += cleanDirectory(userTempPath);
    
    // Clean system temp if requested
    if (includeSystemTemp) {
        String systemTempPath = "C:\\Windows\\Temp";
        filesCleanedCount += cleanDirectory(systemTempPath);
    }
    
    return filesCleanedCount;
}
```

## 🔄 Pull Request Process

1. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** following the code style guidelines

3. **Test your changes** thoroughly:
   - Test on different Windows versions if possible
   - Verify all cleanup operations work correctly
   - Check that the GUI responds properly

4. **Commit your changes**:
   ```bash
   git add .
   git commit -m "Add feature: descriptive commit message"
   ```

5. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request** with:
   - Clear title and description
   - Reference any related issues
   - Include screenshots for UI changes
   - List what you've tested

### Pull Request Template

```
**Description:**
Brief description of changes made.

**Type of Change:**
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Code refactoring

**Testing:**
- [ ] Tested on Windows 10
- [ ] Tested on Windows 11
- [ ] All cleanup operations work
- [ ] GUI functions correctly
- [ ] No errors in log output

**Screenshots:**
(If applicable)

**Related Issues:**
Fixes #issue_number
```

## 🧪 Testing Guidelines

### Manual Testing

1. **Administrator Mode**: Always test with administrator privileges
2. **All Operations**: Test each cleanup operation individually
3. **Run All**: Test the "Run All Operations" functionality
4. **Error Handling**: Test with locked files or permission issues
5. **Different Windows Versions**: Test on Windows 10 and 11 if possible

### Testing Checklist

- [ ] Application launches correctly
- [ ] All buttons are responsive
- [ ] Cleanup operations complete successfully
- [ ] Log output is clear and informative
- [ ] No crashes or exceptions
- [ ] Files are actually cleaned (verify manually)
- [ ] Administrator warnings appear when needed

## 🏷️ Versioning

We use [Semantic Versioning](https://semver.org/):

- **MAJOR** version: Incompatible API changes
- **MINOR** version: New functionality (backward compatible)
- **PATCH** version: Bug fixes (backward compatible)

## 📄 License

By contributing to SafeClean, you agree that your contributions will be licensed under the same license as the project.

## 🤝 Code of Conduct

### Our Standards

- **Be respectful** and inclusive
- **Be patient** with newcomers
- **Provide constructive feedback**
- **Focus on what's best** for the community
- **Show empathy** towards other contributors

### Unacceptable Behavior

- Harassment, discrimination, or offensive comments
- Personal attacks or trolling
- Publishing private information without permission
- Any conduct inappropriate in a professional setting

## 📞 Getting Help

If you need help or have questions:

1. **Check existing issues** on GitHub
2. **Create a new issue** with the "question" label
3. **Join discussions** in issue comments
4. **Contact the maintainer**: Babak Ahari

## 🙏 Recognition

Contributors will be recognized in:

- **CONTRIBUTORS.md** file
- **Release notes** for significant contributions
- **README.md** acknowledgments section

Thank you for contributing to SafeClean! 🚀
