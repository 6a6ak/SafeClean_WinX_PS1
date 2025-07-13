# SafeClean WinX PS1

A PowerShell script for safely cleaning up Windows system files and freeing disk space. This utility provides a menu-driven interface to perform various cleanup operations commonly needed for Windows maintenance.

## Features

- 🗑️ **Clean Temporary Files** - Removes user and system temporary files
- 🔄 **Clean Windows Update Cache** - Clears Windows Update download cache
- 🖼️ **Clean Thumbnail Cache** - Removes Windows Explorer thumbnail cache
- 📋 **Clear Event Logs** - Clears Windows Event Logs to free space
- 🗂️ **Reduce WinSxS Folder** - Advanced cleanup of Windows Component Store
- 💤 **Disable Hibernation** - Disables hibernation and removes hiberfil.sys
- ♻️ **Clean Recycle Bin** - Empties the Recycle Bin completely

## Prerequisites

- **Windows Operating System** (Windows 10/11 recommended)
- **Administrator privileges** required
- **PowerShell 5.1 or later**

## Installation

1. Clone this repository or download the script:
   ```bash
   git clone https://github.com/6a6ak/SafeClean_WinX_PS1.git
   ```

2. Navigate to the script directory:
   ```powershell
   cd SafeClean_WinX_PS1
   ```

## Usage

### Running the Script

1. **Right-click** on PowerShell and select **"Run as Administrator"**
2. Navigate to the script location
3. Execute the script:
   ```powershell
   .\SafeClean.ps1
   ```

### Menu Options

The script presents an interactive menu with the following options:

```
===== Safe Cleanup Menu =====
1) Clean Temporary Files
2) Clean Windows Update Cache
3) Clean Thumbnail Cache
4) Clear Event Logs
5) Reduce WinSxS Folder (Advanced)
6) Disable Hibernation & Remove hiberfil.sys
7) Clean Recycle Bin
0) Exit
```

Simply enter the number corresponding to the cleanup operation you want to perform.

## What Each Option Does

### 1. Clean Temporary Files
- Clears `%TEMP%` (user temporary files)
- Clears `C:\Windows\Temp` (system temporary files)

### 2. Clean Windows Update Cache
- Stops Windows Update service
- Clears `C:\Windows\SoftwareDistribution\Download`
- Restarts Windows Update service

### 3. Clean Thumbnail Cache
- Removes thumbnail cache from `%LOCALAPPDATA%\Microsoft\Windows\Explorer`

### 4. Clear Event Logs
- Clears all Windows Event Logs
- Provides feedback on which logs were cleared or skipped

### 5. Reduce WinSxS Folder (Advanced)
- Uses DISM to clean up Windows Component Store
- Runs `StartComponentCleanup` with `ResetBase` option
- **Warning**: This is an advanced operation

### 6. Disable Hibernation & Remove hiberfil.sys
- Disables Windows hibernation feature
- Removes the hibernation file (hiberfil.sys) which can be several GB in size
- Frees up space equal to your system's RAM size
- **Note**: Can be re-enabled later with `powercfg /hibernate on`

### 7. Clean Recycle Bin
- Completely empties the Recycle Bin
- Removes all items permanently

## Safety Features

- **Error handling**: Operations continue even if individual items can't be deleted
- **Path validation**: Checks if paths exist before attempting cleanup
- **Service management**: Properly stops and starts services when needed
- **Informative output**: Shows what's being cleaned and completion status

## Important Notes

⚠️ **Administrator Rights Required**: This script must be run as Administrator to access system folders and services.

⚠️ **Data Loss Warning**: Some operations (especially Recycle Bin and Event Logs) will permanently delete data. Use with caution.

⚠️ **WinSxS Cleanup**: Option 5 is an advanced operation that modifies the Windows Component Store. Only use if you understand the implications.

⚠️ **Hibernation Disable**: Option 6 will disable hibernation permanently and remove hiberfil.sys. This affects fast startup and hibernate features. Can be re-enabled later if needed.

## Compatibility

- ✅ Windows 10
- ✅ Windows 11
- ✅ Windows Server 2016+
- ⚠️ Windows 8.1 (limited testing)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

**Testing Notice**: While this script has been tested by the author, it is provided as-is and use is entirely at your own risk.

## Disclaimer

This software is provided "as is" without warranty of any kind. Use at your own risk. Always backup important data before running cleanup operations. While the author has tested this script, each system configuration is unique and results may vary.

## Author

Created for safe and efficient Windows system maintenance.

---

⭐ If this script helped you free up disk space, please give it a star!
