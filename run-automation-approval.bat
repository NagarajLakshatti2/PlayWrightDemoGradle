@echo off
REM ============================================
REM Automation Approval Runner
REM ============================================
REM This runner fetches test cases with status "TO BE AUTOMATE"
REM and automates only the approved ones from the config file.
REM
REM To modify parameters, edit the values below:
REM ============================================

REM Change to the script's directory
cd /d "%~dp0"

REM Optional: Pass custom Jira epic key (leave empty to use default)
SET JIRA_EPIC_KEY=

REM Optional: Custom config file path (leave empty to use default)
SET CONFIG_FILE=

REM ============================================
REM Do not modify below this line
REM ============================================

echo ===========================================
echo Automation Approval Runner
echo ===========================================
echo.
echo Current Directory: %CD%
echo.

REM Check if gradlew.bat exists
IF NOT EXIST "gradlew.bat" (
    echo ERROR: gradlew.bat not found in current directory
    echo Please ensure you are running this from the project root directory
    echo.
    echo Current directory: %CD%
    echo.
    pause
    exit /b 1
)

REM Check if custom epic key is provided
IF NOT "%JIRA_EPIC_KEY%"=="" (
    echo Using custom epic key: %JIRA_EPIC_KEY%
    echo.
)

REM Check if custom config file is provided
IF NOT "%CONFIG_FILE%"=="" (
    echo Using custom config file: %CONFIG_FILE%
    echo.
)

REM Run the Gradle task
call gradlew.bat runAutomationApprovalRunner

echo.
echo ===========================================
echo Press any key to close...
pause >nul
