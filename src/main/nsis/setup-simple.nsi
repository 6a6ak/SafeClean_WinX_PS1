; SafeClean Professional Windows Installer - Simple Version
; Created by 6a6ak - https://github.com/6a6ak/SafeClean_WinX_PS1

!define PRODUCT_NAME "SafeClean"
!define PRODUCT_VERSION "2.0.0"
!define PRODUCT_PUBLISHER "6a6ak"
!define PRODUCT_WEB_SITE "https://github.com/6a6ak/SafeClean_WinX_PS1"
!define PRODUCT_DIR_REGKEY "Software\Microsoft\Windows\CurrentVersion\App Paths\SafeClean.exe"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"

; MUI 2.0 compatible ------
!include "MUI2.nsh"

; MUI Settings
!define MUI_ABORTWARNING
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"

; Welcome page
!insertmacro MUI_PAGE_WELCOME
; Directory page
!insertmacro MUI_PAGE_DIRECTORY
; Components page
!insertmacro MUI_PAGE_COMPONENTS
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page
!define MUI_FINISHPAGE_RUN "$INSTDIR\SafeClean.exe"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "English"

; MUI end ------

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
OutFile "..\..\..\target\SafeClean-Setup.exe"
InstallDir "$PROGRAMFILES\SafeClean"
InstallDirRegKey HKLM "${PRODUCT_DIR_REGKEY}" ""
ShowInstDetails show
ShowUnInstDetails show

; Request administrator privileges
RequestExecutionLevel admin

; Main Installation Section
Section "SafeClean Application (Required)" SEC01
  SectionIn RO
  SetOutPath "$INSTDIR"
  SetOverwrite ifnewer
  
  ; Copy main executable
  File /oname=SafeClean.exe "..\..\..\target\SafeClean.exe"
  
  ; Copy JAR file as backup (optional)
  IfFileExists "..\..\..\target\SafeClean-2.0.0.jar" 0 +2
  File /oname=SafeClean-2.0.0.jar "..\..\..\target\SafeClean-2.0.0.jar"
  
  ; Copy launcher scripts
  IfFileExists "..\..\..\SafeClean-Launcher.bat" 0 +2
  File /oname=SafeClean-Launcher.bat "..\..\..\SafeClean-Launcher.bat"
  
  IfFileExists "..\..\..\SafeClean-Launcher.ps1" 0 +2
  File /oname=SafeClean-Launcher.ps1 "..\..\..\SafeClean-Launcher.ps1"
  
  ; Copy antivirus-safe alternatives
  IfFileExists "..\..\..\SafeClean-Safe.bat" 0 +2
  File /oname=SafeClean-Safe.bat "..\..\..\SafeClean-Safe.bat"
  
  IfFileExists "..\..\..\SafeClean-Safe.py" 0 +2
  File /oname=SafeClean-Safe.py "..\..\..\SafeClean-Safe.py"
  
  IfFileExists "..\..\..\ANTIVIRUS-FALSE-POSITIVE.md" 0 +2
  File /oname=ANTIVIRUS-FALSE-POSITIVE.md "..\..\..\ANTIVIRUS-FALSE-POSITIVE.md"
  
  ; Create README file
  FileOpen $9 "$INSTDIR\README.txt" w
  FileWrite $9 "SafeClean v${PRODUCT_VERSION}$\r$\n"
  FileWrite $9 "==============================$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "Professional Windows System Cleanup Tool$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "IMPORTANT: This application requires Java 8 or higher.$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "ANTIVIRUS NOTICE:$\r$\n"
  FileWrite $9 "SafeClean.exe may trigger antivirus false positives.$\r$\n"
  FileWrite $9 "This is common with Java-to-EXE converters.$\r$\n"
  FileWrite $9 "See ANTIVIRUS-FALSE-POSITIVE.md for solutions.$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "Running SafeClean (choose one):$\r$\n"
  FileWrite $9 "1. SafeClean.exe (direct - may trigger antivirus)$\r$\n"
  FileWrite $9 "2. SafeClean-Safe.bat (antivirus-friendly)$\r$\n"
  FileWrite $9 "3. SafeClean-Safe.py (Python launcher)$\r$\n"
  FileWrite $9 "4. SafeClean-Launcher.bat (with Java detection)$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "Run as Administrator for best results$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "If Java is not installed:$\r$\n"
  FileWrite $9 "- Download from: https://adoptium.net/temurin/releases/?version=8$\r$\n"
  FileWrite $9 "- Or use the launcher script for automatic detection$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "Features:$\r$\n"
  FileWrite $9 "- Temporary files cleanup$\r$\n"
  FileWrite $9 "- Windows Update cache cleanup$\r$\n"
  FileWrite $9 "- Thumbnail cache cleanup$\r$\n"
  FileWrite $9 "- Event logs cleanup$\r$\n"
  FileWrite $9 "- WinSxS component store cleanup$\r$\n"
  FileWrite $9 "- Hibernation file removal$\r$\n"
  FileWrite $9 "- Recycle bin cleanup$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "Created by: ${PRODUCT_PUBLISHER}$\r$\n"
  FileWrite $9 "Website: ${PRODUCT_WEB_SITE}$\r$\n"
  FileClose $9
  
  ; Create uninstaller
  WriteUninstaller "$INSTDIR\uninst.exe"
  
  ; Registry entries
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "" "$INSTDIR\SafeClean.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayIcon" "$INSTDIR\SafeClean.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
SectionEnd

; Desktop Shortcut Section
Section "Desktop Shortcut" SEC02
  CreateShortCut "$DESKTOP\SafeClean.lnk" "$INSTDIR\SafeClean-Safe.bat" "" "$INSTDIR\SafeClean.exe" 0
  CreateShortCut "$DESKTOP\SafeClean (Direct).lnk" "$INSTDIR\SafeClean.exe" "" "$INSTDIR\SafeClean.exe" 0
SectionEnd

; Start Menu Shortcuts Section
Section "Start Menu Shortcuts" SEC03
  CreateDirectory "$SMPROGRAMS\SafeClean"
  CreateShortCut "$SMPROGRAMS\SafeClean\SafeClean (Safe).lnk" "$INSTDIR\SafeClean-Safe.bat" "" "$INSTDIR\SafeClean.exe" 0
  CreateShortCut "$SMPROGRAMS\SafeClean\SafeClean (Direct).lnk" "$INSTDIR\SafeClean.exe" "" "$INSTDIR\SafeClean.exe" 0
  CreateShortCut "$SMPROGRAMS\SafeClean\SafeClean (Python).lnk" "$INSTDIR\SafeClean-Safe.py" "" "$INSTDIR\SafeClean.exe" 0
  CreateShortCut "$SMPROGRAMS\SafeClean\README.lnk" "$INSTDIR\README.txt"
  CreateShortCut "$SMPROGRAMS\SafeClean\Antivirus Help.lnk" "$INSTDIR\ANTIVIRUS-FALSE-POSITIVE.md"
  CreateShortCut "$SMPROGRAMS\SafeClean\Uninstall.lnk" "$INSTDIR\uninst.exe" "" "$INSTDIR\uninst.exe" 0
SectionEnd

; Component Descriptions
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC01} "SafeClean application files with multiple launcher options (required)"
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC02} "Create desktop shortcuts (antivirus-safe launcher recommended)"
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC03} "Add SafeClean to Start Menu with multiple launch options"
!insertmacro MUI_FUNCTION_DESCRIPTION_END

; Uninstaller Section
Section Uninstall
  ; Remove files
  Delete "$INSTDIR\SafeClean.exe"
  Delete "$INSTDIR\SafeClean-2.0.0.jar"
  Delete "$INSTDIR\SafeClean-Launcher.bat"
  Delete "$INSTDIR\SafeClean-Launcher.ps1"
  Delete "$INSTDIR\SafeClean-Safe.bat"
  Delete "$INSTDIR\SafeClean-Safe.py"
  Delete "$INSTDIR\ANTIVIRUS-FALSE-POSITIVE.md"
  Delete "$INSTDIR\README.txt"
  Delete "$INSTDIR\uninst.exe"
  
  ; Remove shortcuts
  Delete "$DESKTOP\SafeClean.lnk"
  Delete "$DESKTOP\SafeClean (Direct).lnk"
  Delete "$SMPROGRAMS\SafeClean\SafeClean (Safe).lnk"
  Delete "$SMPROGRAMS\SafeClean\SafeClean (Direct).lnk"
  Delete "$SMPROGRAMS\SafeClean\SafeClean (Python).lnk"
  Delete "$SMPROGRAMS\SafeClean\README.lnk"
  Delete "$SMPROGRAMS\SafeClean\Antivirus Help.lnk"
  Delete "$SMPROGRAMS\SafeClean\Uninstall.lnk"
  RMDir "$SMPROGRAMS\SafeClean"
  
  ; Remove directory
  RMDir "$INSTDIR"
  
  ; Remove registry entries
  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  DeleteRegKey HKLM "${PRODUCT_DIR_REGKEY}"
  
  SetAutoClose true
SectionEnd
