@echo off
REM ============================================
REM Test Runner GUI Launcher
REM ============================================
REM This launches the visual GUI popup for selecting test parameters

REM Change to the script's directory
cd /d "%~dp0"

echo ===========================================
echo Launching Test Runner GUI...
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

REM Run the GUI
call gradlew.bat runTestRunnerGUI

echo.
echo ===========================================
echo GUI closed
echo ===========================================
