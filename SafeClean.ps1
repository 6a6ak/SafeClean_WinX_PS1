# Run this script as Administrator!

function Clear-Folder($Path) {
    if (Test-Path $Path) {
        Get-ChildItem -Path $Path -Recurse -Force | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "Cleared: $Path"
    } else {
        Write-Host "Path does not exist: $Path"
    }
}

function Clear-RecycleBin {
    Write-Host "Cleaning Recycle Bin..."
    $(New-Object -ComObject Shell.Application).NameSpace(0xA).Items() | ForEach-Object {
        Remove-Item $_.Path -Recurse -Force -ErrorAction SilentlyContinue
    }
    Write-Host "Recycle Bin cleaned." -ForegroundColor Green
}

function Clear-TempFiles {
    Write-Host "Cleaning TEMP files..."
    Clear-Folder "$env:TEMP"
    Clear-Folder "C:\Windows\Temp"
    Write-Host "Temporary files cleaned." -ForegroundColor Green
}

function Clear-WindowsUpdate {
    Write-Host "Cleaning Windows Update cache..."
    Stop-Service -Name wuauserv -Force
    Clear-Folder "C:\Windows\SoftwareDistribution\Download"
    Start-Service -Name wuauserv
    Write-Host "Windows Update cache cleaned." -ForegroundColor Green
}

function Clear-ThumbnailCache {
    Write-Host "Cleaning Thumbnail cache..."
    Clear-Folder "$env:LOCALAPPDATA\Microsoft\Windows\Explorer"
    Write-Host "Thumbnail cache cleaned." -ForegroundColor Green
}

function Clear-EventLogs {
    Write-Host "Clearing Event Logs..."
    $logs = wevtutil el
    foreach ($log in $logs) {
        try {
            wevtutil cl $log
            Write-Host "Cleared: $log"
        } catch {
            Write-Host "Skipped: $log (`$($_.Exception.Message)`)" -ForegroundColor Yellow
        }
    }
    Write-Host "Event logs clearing completed." -ForegroundColor Green
}

function Clean-WinSxS {
    Write-Host "Reducing WinSxS (Component Store)..."
    Dism.exe /online /Cleanup-Image /StartComponentCleanup /ResetBase
    Write-Host "WinSxS cleanup completed." -ForegroundColor Green
}

# Main menu loop
do {
    Write-Host ""
    Write-Host "===== Safe Cleanup Menu =====" -ForegroundColor Cyan
    Write-Host "1) Clean Temporary Files"
    Write-Host "2) Clean Windows Update Cache"
    Write-Host "3) Clean Thumbnail Cache"
    Write-Host "4) Clear Event Logs"
    Write-Host "5) Reduce WinSxS Folder (Advanced)"
    Write-Host "6) Clean Recycle Bin"
    Write-Host "0) Exit"
    Write-Host ""
    $choice = Read-Host "Select an option (0-6)"

    switch ($choice) {
        "1" { Clear-TempFiles }
        "2" { Clear-WindowsUpdate }
        "3" { Clear-ThumbnailCache }
        "4" { Clear-EventLogs }
        "5" { Clean-WinSxS }
        "6" { Clear-RecycleBin }
        "0" { Write-Host "Exiting..." -ForegroundColor Green }
        default { Write-Host "Invalid choice, try again." -ForegroundColor Yellow }
    }
} while ($choice -ne "0")

Write-Host "`nAll done. Goodbye!" -ForegroundColor Green
