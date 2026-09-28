@echo off
REM ============================================
REM Test Runner
REM ============================================
REM This runner executes the Cucumber tests.
REM
REM To modify parameters, edit the values below:
REM ============================================

REM Environment: dev, staging, prod
SET ENV=dev

REM Browser: chromium, firefox, webkit
SET BROWSER=chromium

REM Headless mode: true, false
SET HEADLESS=true

REM Test tags: @regression, @sanity, @smoke, @qap-1
REM Leave empty to run all tests
SET TEST_TAGS=

REM Visual validation strict mode: true, false
SET VISUAL_STRICT=false

REM Rerun tasks: true, false
SET RERUN_TASKS=true

REM ============================================
REM Do not modify below this line
REM ============================================

echo ===========================================
echo Test Runner
echo ===========================================
echo.
echo Configuration:
echo   Environment: %ENV%
echo   Browser: %BROWSER%
echo   Headless: %HEADLESS%
echo   Test Tags: %TEST_TAGS%
echo   Visual Strict: %VISUAL_STRICT%
echo   Rerun Tasks: %RERUN_TASKS%
echo.
echo ===========================================
echo.

REM Build the Gradle command
SET GRADLE_CMD=gradlew.bat clean test "-Denv=%ENV%" "-Dbrowser=%BROWSER%" "-Dheadless=%HEADLESS%"

IF NOT "%VISUAL_STRICT%"=="" (
    SET GRADLE_CMD=%GRADLE_CMD% "-Dvisual.strict=%VISUAL_STRICT%"
)

IF NOT "%TEST_TAGS%"=="" (
    SET GRADLE_CMD=%GRADLE_CMD% "-Dcucumber.filter.tags=%TEST_TAGS%"
)

IF "%RERUN_TASKS%"=="true" (
    SET GRADLE_CMD=%GRADLE_CMD% --rerun-tasks
)

REM Run the command
echo Running: %GRADLE_CMD%
echo.
call %GRADLE_CMD%

echo.
echo ===========================================
echo Press any key to close...
pause >nul
