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
                              capture_output=True, text=True)
        if result.returncode == 0:
            # Java -version outputs to stderr, not stdout
            version_output = result.stderr if result.stderr else result.stdout
            version_line = version_output.split('\n')[0] if version_output else "Unknown"
            print(f"✓ Java found: {version_line}")
            
            # Parse Java version to check if it's 8 or higher
            version_compatible = check_java_version_compatibility(version_output)
            if not version_compatible:
                print("✗ ERROR: Java version is too old!")
                print("  SafeClean requires Java 8 or higher")
                return False
            
            print("✓ Java version is compatible")
            return True
        else:
            return False
    except FileNotFoundError:
        return False

def check_java_version_compatibility(version_output):
    """Parse Java version output and check if it's 8 or higher"""
    try:
        import re
        
        # Look for version pattern in output
        # Handles both "1.8.0_xxx" and "11.0.x" formats
        version_match = re.search(r'"([^"]+)"', version_output)
        if not version_match:
            return False
        
        version_str = version_match.group(1)
        
        # Parse version number
        if version_str.startswith('1.'):
            # Old format: 1.8.0_xxx -> major version is 8
            parts = version_str.split('.')
            if len(parts) >= 2:
                major_version = int(parts[1])
            else:
                return False
        else:
            # New format: 11.0.x -> major version is 11
            parts = version_str.split('.')
            if len(parts) >= 1:
                major_version = int(parts[0])
            else:
                return False
        
        # Check if major version is 8 or higher
        return major_version >= 8
        
    except (ValueError, AttributeError, IndexError):
        # If parsing fails, assume compatible to avoid false negatives
        return True

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
        print("✗ ERROR: Java Runtime Environment issue detected!")
        print()
        print("SafeClean requires Java 8 or higher to run.")
        print("Your Java installation may be:")
        print("• Not installed")
        print("• Too old (version 7 or lower)")
        print("• Not properly configured")
        print()
        print("SOLUTION:")
        print("1. Download and install Java 8 or higher from:")
        print("   https://adoptium.net/temurin/releases/?version=8")
        print("2. Make sure to install the LATEST version")
        print("3. Restart your computer after installation")
        print("4. Run this script again")
        print()
        print("Alternative: Use 'java -jar SafeClean-2.0.0.jar' directly")
        print("if you have Java 8+ installed but this script fails.")
        print()
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
