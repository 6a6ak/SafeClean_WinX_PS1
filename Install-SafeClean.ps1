# SafeClean Professional Installer Script
# Run this as Administrator to install SafeClean to Program Files

param(
    [switch]$Uninstall = $false
)

$ProductName = "SafeClean"
$ProductVersion = "2.0.0"
$Publisher = "6a6ak"
$InstallDir = "$env:ProgramFiles\SafeClean"
$SourceDir = ".\target"

function Test-Admin {
    $currentUser = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = New-Object Security.Principal.WindowsPrincipal($currentUser)
    return $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

function Install-SafeClean {
    Write-Host "SafeClean Professional Installer v$ProductVersion" -ForegroundColor Green
    Write-Host "Created by $Publisher" -ForegroundColor Gray
    Write-Host ""
    
    if (-not (Test-Admin)) {
        Write-Host "ERROR: This installer must be run as Administrator!" -ForegroundColor Red
        Write-Host "Right-click PowerShell and select 'Run as Administrator'" -ForegroundColor Yellow
        pause
        exit 1
    }
    
    # Check if source files exist
    if (-not (Test-Path "$SourceDir\SafeClean.exe")) {
        Write-Host "ERROR: SafeClean.exe not found in $SourceDir" -ForegroundColor Red
        Write-Host "Please run 'mvn package' first to build the application." -ForegroundColor Yellow
        pause
        exit 1
    }
    
    Write-Host "Installing SafeClean to: $InstallDir" -ForegroundColor Cyan
    
    # Create installation directory
    if (-not (Test-Path $InstallDir)) {
        New-Item -ItemType Directory -Path $InstallDir -Force | Out-Null
        Write-Host "✓ Created installation directory" -ForegroundColor Green
    }
    
    # Copy files
    try {
        Copy-Item "$SourceDir\SafeClean.exe" "$InstallDir\" -Force
        Write-Host "✓ Copied SafeClean.exe" -ForegroundColor Green
        
        if (Test-Path "$SourceDir\SafeClean-$ProductVersion.jar") {
            Copy-Item "$SourceDir\SafeClean-$ProductVersion.jar" "$InstallDir\" -Force
            Write-Host "✓ Copied JAR backup file" -ForegroundColor Green
        }
        
        # Create README
        $readme = @"
# SafeClean v$ProductVersion

Professional Windows System Cleanup Tool

## How to Use:
- Double-click SafeClean.exe to start
- Run as Administrator for best results
- Select cleanup operations from the interface

## Features:
- Temporary files cleanup
- Windows Update cache cleanup  
- Thumbnail cache cleanup
- Event logs cleanup
- WinSxS component store cleanup
- Hibernation file removal
- Recycle bin cleanup

Created by: $Publisher
Website: https://github.com/6a6ak/SafeClean_WinX_PS1
"@
        
        $readme | Out-File "$InstallDir\README.md" -Encoding UTF8
        Write-Host "✓ Created README.md" -ForegroundColor Green
        
    } catch {
        Write-Host "ERROR: Failed to copy files: $($_.Exception.Message)" -ForegroundColor Red
        exit 1
    }
    
    # Create Desktop Shortcut
    $WshShell = New-Object -comObject WScript.Shell
    $Shortcut = $WshShell.CreateShortcut("$env:USERPROFILE\Desktop\SafeClean.lnk")
    $Shortcut.TargetPath = "$InstallDir\SafeClean.exe"
    $Shortcut.WorkingDirectory = $InstallDir
    $Shortcut.Description = "SafeClean - Professional System Cleanup Tool"
    $Shortcut.Save()
    Write-Host "✓ Created Desktop shortcut" -ForegroundColor Green
    
    # Create Start Menu shortcuts
    $StartMenuDir = "$env:ProgramData\Microsoft\Windows\Start Menu\Programs\SafeClean"
    if (-not (Test-Path $StartMenuDir)) {
        New-Item -ItemType Directory -Path $StartMenuDir -Force | Out-Null
    }
    
    $Shortcut = $WshShell.CreateShortcut("$StartMenuDir\SafeClean.lnk")
    $Shortcut.TargetPath = "$InstallDir\SafeClean.exe"
    $Shortcut.WorkingDirectory = $InstallDir
    $Shortcut.Description = "SafeClean - Professional System Cleanup Tool"
    $Shortcut.Save()
    
    $Shortcut = $WshShell.CreateShortcut("$StartMenuDir\README.lnk")
    $Shortcut.TargetPath = "$InstallDir\README.md"
    $Shortcut.Save()
    
    Write-Host "✓ Created Start Menu shortcuts" -ForegroundColor Green
    
    # Add to Windows Registry (Add/Remove Programs)
    $UninstallKey = "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\SafeClean"
    New-Item -Path $UninstallKey -Force | Out-Null
    Set-ItemProperty -Path $UninstallKey -Name "DisplayName" -Value "SafeClean v$ProductVersion"
    Set-ItemProperty -Path $UninstallKey -Name "DisplayVersion" -Value $ProductVersion
    Set-ItemProperty -Path $UninstallKey -Name "Publisher" -Value $Publisher
    Set-ItemProperty -Path $UninstallKey -Name "InstallLocation" -Value $InstallDir
    Set-ItemProperty -Path $UninstallKey -Name "DisplayIcon" -Value "$InstallDir\SafeClean.exe"
    Set-ItemProperty -Path $UninstallKey -Name "UninstallString" -Value "powershell.exe -ExecutionPolicy Bypass -File `"$InstallDir\Uninstall.ps1`""
    Set-ItemProperty -Path $UninstallKey -Name "NoModify" -Value 1 -Type DWord
    Set-ItemProperty -Path $UninstallKey -Name "NoRepair" -Value 1 -Type DWord
    
    Write-Host "✓ Registered in Add/Remove Programs" -ForegroundColor Green
    
    # Create uninstaller
    $uninstaller = @"
# SafeClean Uninstaller
`$InstallDir = "$InstallDir"
`$StartMenuDir = "$StartMenuDir"

Write-Host "Uninstalling SafeClean..." -ForegroundColor Yellow

# Remove files
Remove-Item "`$InstallDir" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "`$StartMenuDir" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "`$env:USERPROFILE\Desktop\SafeClean.lnk" -Force -ErrorAction SilentlyContinue

# Remove registry entry
Remove-Item "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\SafeClean" -Force -ErrorAction SilentlyContinue

Write-Host "SafeClean has been uninstalled." -ForegroundColor Green
pause
"@
    
    $uninstaller | Out-File "$InstallDir\Uninstall.ps1" -Encoding UTF8
    Write-Host "✓ Created uninstaller" -ForegroundColor Green
    
    # Add to PATH (optional)
    $currentPath = [Environment]::GetEnvironmentVariable("PATH", "Machine")
    if ($currentPath -notlike "*$InstallDir*") {
        $newPath = "$currentPath;$InstallDir"
        [Environment]::SetEnvironmentVariable("PATH", $newPath, "Machine")
        Write-Host "✓ Added to system PATH" -ForegroundColor Green
    }
    
    Write-Host ""
    Write-Host "🎉 SafeClean has been successfully installed!" -ForegroundColor Green
    Write-Host ""
    Write-Host "You can now:" -ForegroundColor Cyan
    Write-Host "• Double-click the Desktop shortcut to run SafeClean" -ForegroundColor White
    Write-Host "• Find it in Start Menu > SafeClean" -ForegroundColor White
    Write-Host "• Run 'SafeClean' from any command prompt" -ForegroundColor White
    Write-Host "• Uninstall via Settings > Apps or run Uninstall.ps1" -ForegroundColor White
    Write-Host ""
    
    $response = Read-Host "Would you like to launch SafeClean now? (y/n)"
    if ($response -eq 'y' -or $response -eq 'Y') {
        Start-Process "$InstallDir\SafeClean.exe"
    }
}

function Uninstall-SafeClean {
    Write-Host "SafeClean Uninstaller" -ForegroundColor Red
    Write-Host ""
    
    if (-not (Test-Admin)) {
        Write-Host "ERROR: This uninstaller must be run as Administrator!" -ForegroundColor Red
        pause
        exit 1
    }
    
    $confirm = Read-Host "Are you sure you want to completely remove SafeClean? (y/n)"
    if ($confirm -ne 'y' -and $confirm -ne 'Y') {
        Write-Host "Uninstall cancelled." -ForegroundColor Yellow
        exit 0
    }
    
    Write-Host "Removing SafeClean..." -ForegroundColor Yellow
    
    # Remove files and folders
    Remove-Item $InstallDir -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item "$env:ProgramData\Microsoft\Windows\Start Menu\Programs\SafeClean" -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item "$env:USERPROFILE\Desktop\SafeClean.lnk" -Force -ErrorAction SilentlyContinue
    
    # Remove from PATH
    $currentPath = [Environment]::GetEnvironmentVariable("PATH", "Machine")
    $newPath = $currentPath -replace [regex]::Escape(";$InstallDir"), ""
    $newPath = $newPath -replace [regex]::Escape("$InstallDir;"), ""
    $newPath = $newPath -replace [regex]::Escape("$InstallDir"), ""
    [Environment]::SetEnvironmentVariable("PATH", $newPath, "Machine")
    
    # Remove registry entry
    Remove-Item "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\SafeClean" -Force -ErrorAction SilentlyContinue
    
    Write-Host "✓ SafeClean has been completely removed." -ForegroundColor Green
    pause
}

# Main execution
if ($Uninstall) {
    Uninstall-SafeClean
} else {
    Install-SafeClean
}
