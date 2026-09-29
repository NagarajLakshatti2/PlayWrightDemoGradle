@echo off
REM ============================================
REM Jira Status Update Runner
REM ============================================
REM This runner updates Jira test case status based on test results.
REM To enable/disable, edit the jira-config.json file.
REM
REM To modify parameters, edit the values below:
REM ============================================

REM Change to the script's directory
cd /d "%~dp0"

REM Optional: Test tags to run (e.g., regression, sanity, smoke)
REM Leave empty to run all tests
SET TEST_TAGS=regression

REM Optional: Environment (e.g., dev, staging, prod)
SET ENV=dev

REM Optional: Browser (e.g., chromium, firefox, webkit)
SET BROWSER=chromium

REM Optional: Headless mode (true/false)
SET HEADLESS=true

REM ============================================
REM Do not modify below this line
REM ============================================

echo ===========================================
echo Jira Status Update Runner
echo ===========================================
echo.
echo Current Directory: %CD%
echo Configuration:
echo   Test Tags: %TEST_TAGS%
echo   Environment: %ENV%
echo   Browser: %BROWSER%
echo   Headless: %HEADLESS%
echo.
echo ===========================================
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

REM Run the Gradle task
call gradlew.bat runJiraStatusUpdateRunner

echo.
echo ===========================================
echo Press any key to close...
pause >nul
