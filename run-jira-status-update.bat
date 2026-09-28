@echo off
REM ============================================
REM Jira Status Update Runner
REM ============================================
REM This runner updates Jira test case status based on test results.
REM To enable/disable, edit the jira-config.json file.
REM
REM To modify parameters, edit the values below:
REM ============================================

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
echo Configuration:
echo   Test Tags: %TEST_TAGS%
echo   Environment: %ENV%
echo   Browser: %BROWSER%
echo   Headless: %HEADLESS%
echo.
echo ===========================================
echo.

REM Run the Gradle task
call gradlew.bat runJiraStatusUpdateRunner

echo.
echo ===========================================
echo Press any key to close...
pause >nul
