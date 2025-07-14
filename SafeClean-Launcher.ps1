# SafeClean Java Runtime Environment Checker and Launcher
# Automatically detects Java and helps with installation if needed

param(
    [switch]$Silent,
    [switch]$AutoDownload
)

Write-Host "SafeClean Java Runtime Checker" -ForegroundColor Cyan
Write-Host "===============================" -ForegroundColor Cyan
Write-Host ""

function Test-JavaInstallation {
    try {
        $javaVersion = & java -version 2>&1
        if ($LASTEXITCODE -eq 0) {
            $versionLine = $javaVersion | Select-String "version" | Select-Object -First 1
            if ($versionLine -match '"([^"]+)"') {
                $version = $matches[1]
                Write-Host "✅ Java Runtime Environment found!" -ForegroundColor Green
                Write-Host "📦 Java Version: $version" -ForegroundColor Yellow
                
                # Parse version (handle both 1.8.x and 8.x formats)
                if ($version -match '^1\.(\d+)') {
                    $majorVersion = [int]$matches[1]
                } elseif ($version -match '^(\d+)') {
                    $majorVersion = [int]$matches[1]
                } else {
                    $majorVersion = 0
                }
                
                if ($majorVersion -ge 8) {
                    Write-Host "✅ Java version is compatible!" -ForegroundColor Green
                    return $true
                } else {
                    Write-Host "❌ Java version is too old! (Requires Java 8+)" -ForegroundColor Red
                    return $false
                }
            }
        }
        return $false
    } catch {
        Write-Host "❌ Java Runtime Environment not found!" -ForegroundColor Red
        return $false
    }
}

function Install-JavaPrompt {
    Write-Host ""
    Write-Host "SafeClean requires Java 8 or higher to run." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Available options:" -ForegroundColor Cyan
    Write-Host "[1] 🌐 Download Java automatically (opens browser)" -ForegroundColor White
    Write-Host "[2] 📋 Show manual installation instructions" -ForegroundColor White
    Write-Host "[3] 🚪 Exit" -ForegroundColor White
    Write-Host ""
    
    if ($AutoDownload) {
        $choice = "1"
    } elseif ($Silent) {
        $choice = "3"
    } else {
        $choice = Read-Host "Your choice (1-3)"
    }
    
    switch ($choice) {
        "1" {
            Write-Host "🌐 Opening Java download page..." -ForegroundColor Green
            Start-Process "https://adoptium.net/temurin/releases/?version=8"
            Write-Host ""
            Write-Host "📝 After installing Java:" -ForegroundColor Yellow
            Write-Host "   1. Restart your computer (recommended)" -ForegroundColor White
            Write-Host "   2. Run SafeClean.exe again" -ForegroundColor White
            if (-not $Silent) {
                Read-Host "Press Enter to exit"
            }
            exit 1
        }
        "2" {
            Write-Host ""
            Write-Host "📋 Manual Installation Instructions:" -ForegroundColor Cyan
            Write-Host "=====================================" -ForegroundColor Cyan
            Write-Host "1. 🌐 Visit: https://adoptium.net/temurin/releases/?version=8" -ForegroundColor White
            Write-Host "2. 💾 Download: OpenJDK 8 with HotSpot JVM for Windows" -ForegroundColor White
            Write-Host "3. 🔧 Install: Run the downloaded installer as Administrator" -ForegroundColor White
            Write-Host "4. 🔄 Restart: Restart your computer after installation" -ForegroundColor White
            Write-Host "5. ▶️  Run: Execute SafeClean.exe again" -ForegroundColor White
            Write-Host ""
            Write-Host "💡 Alternative: Install via Chocolatey:" -ForegroundColor Yellow
            Write-Host "   choco install openjdk8" -ForegroundColor Gray
            Write-Host ""
            if (-not $Silent) {
                Read-Host "Press Enter to exit"
            }
            exit 1
        }
        "3" {
            Write-Host "👋 Exiting..." -ForegroundColor Yellow
            exit 1
        }
        default {
            Write-Host "❌ Invalid choice. Exiting..." -ForegroundColor Red
            exit 1
        }
    }
}

function Start-SafeClean {
    Write-Host ""
    Write-Host "🚀 Starting SafeClean..." -ForegroundColor Green
    Write-Host ""
    
    if (Test-Path "SafeClean.exe") {
        try {
            & ".\SafeClean.exe"
            exit $LASTEXITCODE
        } catch {
            Write-Host "❌ Error starting SafeClean: $_" -ForegroundColor Red
            if (-not $Silent) {
                Read-Host "Press Enter to exit"
            }
            exit 1
        }
    } else {
        Write-Host "❌ SafeClean.exe not found in current directory!" -ForegroundColor Red
        Write-Host "📁 Current directory: $(Get-Location)" -ForegroundColor Yellow
        if (-not $Silent) {
            Read-Host "Press Enter to exit"
        }
        exit 1
    }
}

# Main execution
if (Test-JavaInstallation) {
    Start-SafeClean
} else {
    Install-JavaPrompt
}
