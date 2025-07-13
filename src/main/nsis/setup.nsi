; SafeClean Professional Windows Installer
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
!define MUI_FINISHPAGE_SHOWREADME "$INSTDIR\README.md"
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

Section "SafeClean Application (Required)" SEC01
  SectionIn RO
  SetOutPath "$INSTDIR"
  SetOverwrite ifnewer
  
  ; Copy main executable (check if it exists first)
  File /oname=SafeClean.exe "..\..\..\target\SafeClean.exe"
  
  ; Copy JAR file as backup (check if it exists first)
  IfFileExists "..\..\..\target\SafeClean-2.0.0.jar" 0 +2
  File /oname=SafeClean-2.0.0.jar "..\..\..\target\SafeClean-2.0.0.jar"
  
  ; Create README file
  FileOpen $9 "$INSTDIR\README.md" w
  FileWrite $9 "# SafeClean v${PRODUCT_VERSION}$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "Professional Windows System Cleanup Tool$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "## How to Use:$\r$\n"
  FileWrite $9 "- Double-click SafeClean.exe to start$\r$\n"
  FileWrite $9 "- Run as Administrator for best results$\r$\n"
  FileWrite $9 "- Select cleanup operations from the interface$\r$\n"
  FileWrite $9 "$\r$\n"
  FileWrite $9 "## Features:$\r$\n"
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
  
  ; Create batch launcher
  FileOpen $9 "$INSTDIR\SafeClean.bat" w
  FileWrite $9 "@echo off$\r$\n"
  FileWrite $9 "cd /d `"%~dp0`"$\r$\n"
  FileWrite $9 "SafeClean.exe$\r$\n"
  FileClose $9
SectionEnd

Section "Desktop Shortcut" SEC02
  CreateShortCut "$DESKTOP\SafeClean.lnk" "$INSTDIR\SafeClean.exe" "" "$INSTDIR\SafeClean.exe" 0
SectionEnd

Section "Start Menu Shortcuts" SEC03
  CreateDirectory "$SMPROGRAMS\SafeClean"
  CreateShortCut "$SMPROGRAMS\SafeClean\SafeClean.lnk" "$INSTDIR\SafeClean.exe" "" "$INSTDIR\SafeClean.exe" 0
  CreateShortCut "$SMPROGRAMS\SafeClean\README.lnk" "$INSTDIR\README.md"
  CreateShortCut "$SMPROGRAMS\SafeClean\Uninstall.lnk" "$INSTDIR\uninst.exe"
SectionEnd

Section "Quick Launch Shortcut" SEC04
  CreateShortCut "$QUICKLAUNCH\SafeClean.lnk" "$INSTDIR\SafeClean.exe" "" "$INSTDIR\SafeClean.exe" 0
SectionEnd

Section -AdditionalIcons
  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateShortCut "$SMPROGRAMS\SafeClean\Website.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
SectionEnd

Section -Post
  WriteUninstaller "$INSTDIR\uninst.exe"
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "" "$INSTDIR\SafeClean.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayIcon" "$INSTDIR\SafeClean.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
SectionEnd

; Section descriptions
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC01} "SafeClean application files (required)"
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC02} "Create desktop shortcut for quick access"
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC03} "Add SafeClean to Start Menu"
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC04} "Add to Quick Launch toolbar"
!insertmacro MUI_FUNCTION_DESCRIPTION_END

Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) was successfully removed from your computer."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Are you sure you want to completely remove $(^Name) and all of its components?" IDYES +2
  Abort
FunctionEnd

Section Uninstall
  Delete "$INSTDIR\${PRODUCT_NAME}.url"
  Delete "$INSTDIR\uninst.exe"
  Delete "$INSTDIR\README.md"
  Delete "$INSTDIR\SafeClean.bat"
  Delete "$INSTDIR\SafeClean-${PRODUCT_VERSION}.jar"
  Delete "$INSTDIR\SafeClean.exe"

  Delete "$SMPROGRAMS\SafeClean\Uninstall.lnk"
  Delete "$SMPROGRAMS\SafeClean\Website.lnk"
  Delete "$SMPROGRAMS\SafeClean\README.lnk"
  Delete "$SMPROGRAMS\SafeClean\SafeClean.lnk"
  Delete "$DESKTOP\SafeClean.lnk"
  Delete "$QUICKLAUNCH\SafeClean.lnk"

  RMDir "$SMPROGRAMS\SafeClean"
  RMDir "$INSTDIR"

  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  DeleteRegKey HKLM "${PRODUCT_DIR_REGKEY}"
  SetAutoClose true
SectionEnd
