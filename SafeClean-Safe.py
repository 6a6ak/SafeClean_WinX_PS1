#!/usr/bin/env python3
"""
SafeClean Python Launcher
Alternative launcher to avoid antivirus false positives
"""

import os
import sys
import subprocess
import platform
import shutil
from pathlib import Path

def print_header():
    print("=" * 55)
    print("  SafeClean - Professional Windows System Cleanup")
    print("  Version 2.0.0 - Python Launcher")
    print("=" * 55)
    print()

def check_java():
    """Check if Java is installed and get version info"""
    try:
        result = subprocess.run(['java', '-version'], 
                              capture_output=True, text=True, stderr=subprocess.STDOUT)
        if result.returncode == 0:
            # Extract version from output
            version_line = result.stdout.split('\n')[0] if result.stdout else "Unknown"
            print(f"✓ Java found: {version_line}")
            return True
        else:
            return False
    except FileNotFoundError:
        return False

def find_safeclean_jar():
    """Locate SafeClean JAR file"""
    possible_locations = [
        "SafeClean-2.0.0.jar",
        "target/SafeClean-2.0.0.jar",
        "target\\SafeClean-2.0.0.jar"
    ]
    
    for location in possible_locations:
        if os.path.exists(location):
            print(f"✓ Found SafeClean at: {location}")
            return location
    
    return None

def check_admin_privileges():
    """Check if running with administrator privileges on Windows"""
    if platform.system() == "Windows":
        try:
            import ctypes
            return ctypes.windll.shell32.IsUserAnAdmin()
        except:
            return False
    return True  # Assume sufficient privileges on non-Windows

def launch_safeclean(jar_path):
    """Launch SafeClean with proper Java arguments"""
    print("[3/3] Starting SafeClean...")
    print()
    print("=" * 55)
    print("  SafeClean is now starting...")
    print("  Monitor this window for any messages.")
    print("=" * 55)
    print()
    
    # Optimized JVM arguments for system tools
    java_args = [
        'java',
        '-Xms64m',
        '-Xmx512m',
        '-Dfile.encoding=UTF-8',
        '-Djava.awt.headless=false',
        '-jar',
        jar_path
    ]
    
    try:
        result = subprocess.run(java_args, check=False)
        return result.returncode
    except Exception as e:
        print(f"Error launching SafeClean: {e}")
        return 1

def main():
    print_header()
    
    # Check for administrator privileges
    if not check_admin_privileges():
        print("⚠ WARNING: Not running as Administrator")
        print("  For best results, run this script as Administrator")
        print()
    
    # Check Java installation
    print("[1/3] Checking Java Runtime Environment...")
    if not check_java():
        print("✗ ERROR: Java Runtime Environment not found!")
        print()
        print("SafeClean requires Java 8 or higher to run.")
        print()
        print("Please install Java from:")
        print("https://adoptium.net/temurin/releases/?version=8")
        print()
        print("After installing Java, run this script again.")
        input("Press Enter to exit...")
        return 1
    
    # Locate SafeClean JAR
    print("[2/3] Locating SafeClean application...")
    jar_path = find_safeclean_jar()
    if not jar_path:
        print("✗ ERROR: SafeClean application not found!")
        print()
        print("Expected locations:")
        print("- SafeClean-2.0.0.jar")
        print("- target/SafeClean-2.0.0.jar")
        print()
        print("Please ensure SafeClean is properly installed.")
        input("Press Enter to exit...")
        return 1
    
    # Launch SafeClean
    exit_code = launch_safeclean(jar_path)
    
    if exit_code != 0:
        print()
        print(f"SafeClean exited with error code: {exit_code}")
        print("This may indicate a problem with the application.")
        input("Press Enter to exit...")
    else:
        print()
        print("SafeClean completed successfully.")
    
    return exit_code

if __name__ == "__main__":
    sys.exit(main())
