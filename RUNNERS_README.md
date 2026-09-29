# Quick Start Runners

Easy-to-use scripts for running the automation framework. Choose your preferred method:

## 🚀 Three Ways to Run

### 1. **Windows Batch Files** (.bat) - Double-click to run
Simply double-click any `.bat` file to execute on Windows.

### 2. **Shell Scripts** (.sh) - For Git Bash/Linux/Mac
Run from terminal: `./run-tests.sh` or make executable with `chmod +x *.sh`

### 3. **VS Code Tasks** - Dropdown menu in VS Code
Press `Ctrl+Shift+P` → "Tasks: Run Task" → Select from dropdown

---

## 📁 Available Runners

### 1. **Automation Approval Runner**
Fetches test cases with status "TO BE AUTOMATE" and automates only approved ones.

**Files:**
- Windows: `run-automation-approval.bat`
- Linux/Mac: `run-automation-approval.sh`
- VS Code: "Automation Approval Runner"

**How to use:**
- Double-click `.bat` file (Windows)
- Run `./run-automation-approval.sh` (Linux/Mac)
- Or use VS Code task dropdown

**Configuration:**
```batch
# Windows (.bat)
SET JIRA_EPIC_KEY=        # Custom Jira epic key (optional)
SET CONFIG_FILE=          # Custom config file path (optional)
```

```bash
# Linux/Mac (.sh)
JIRA_EPIC_KEY=""          # Custom Jira epic key (optional)
CONFIG_FILE=""            # Custom config file path (optional)
```

**Config file:** `src/main/resources/config/automation-approval.json`

---

### 2. **Jira Status Update Runner**
Updates Jira test case status based on test execution results.

**Files:**
- Windows: `run-jira-status-update.bat`
- Linux/Mac: `run-jira-status-update.sh`
- VS Code: "Jira Status Update Runner"

**How to use:**
- Double-click `.bat` file (Windows)
- Run `./run-jira-status-update.sh` (Linux/Mac)
- Or use VS Code task dropdown
- Enable/disable status updates in `jira-config.json`

**Configuration:**
```batch
# Windows (.bat)
SET TEST_TAGS=regression    # Test tags: regression, sanity, smoke
SET ENV=dev                 # Environment: dev, staging, prod
SET BROWSER=chromium        # Browser: chromium, firefox, webkit
SET HEADLESS=true           # Headless mode: true, false
```

```bash
# Linux/Mac (.sh)
TEST_TAGS="regression"      # Test tags: regression, sanity, smoke
ENV="dev"                   # Environment: dev, staging, prod
BROWSER="chromium"          # Browser: chromium, firefox, webkit
HEADLESS="true"             # Headless mode: true, false
```

**Config file:** `src/main/resources/config/jira-config.json`

**Status mapping:**
```json
{
  "enableStatusUpdate": false,  // Set to true to enable Jira updates
  "statusMapping": {
    "pass": "Done",   // Jira status for passing tests
    "fail": "FAIL"    // Jira status for failing tests
  }
}
```

---

### 3. **Test Runner**
Executes the Cucumber test suite.

**Files:**
- Windows: `run-tests.bat`
- Linux/Mac: `run-tests.sh`
- VS Code: Multiple options (see below)

**How to use:**
- Double-click `.bat` file (Windows)
- Run `./run-tests.sh` (Linux/Mac)
- Or use VS Code task dropdown (multiple options)

**Configuration:**
```batch
# Windows (.bat)
SET ENV=dev                 # Environment: dev, staging, prod
SET BROWSER=chromium        # Browser: chromium, firefox, webkit
SET HEADLESS=true           # Headless mode: true, false
SET TEST_TAGS=              # Cucumber tags: @regression, @sanity, @smoke
SET VISUAL_STRICT=false     # Visual validation strict mode
SET RERUN_TASKS=true        # Force rerun of tasks
```

```bash
# Linux/Mac (.sh)
ENV="dev"                   # Environment: dev, staging, prod
BROWSER="chromium"          # Browser: chromium, firefox, webkit
HEADLESS="true"             # Headless mode: true, false
TEST_TAGS=""                # Cucumber tags: @regression, @sanity, @smoke
VISUAL_STRICT="false"       # Visual validation strict mode
RERUN_TASKS="true"          # Force rerun of tasks
```

---

---

## 🎯 VS Code Tasks (Dropdown Menu)

Press `Ctrl+Shift+P` → "Tasks: Run Task" → Select from dropdown:

### Test Execution Tasks:
- **Run Tests (Chromium - Dev - Headless)** - Default test run
- **Run Tests (Firefox - Dev - Headless)** - Firefox browser
- **Run Tests (Chromium - Dev - Visible)** - Visible browser window
- **Run Regression Tests** - Only @regression tagged tests
- **Run Sanity Tests** - Only @sanity tagged tests
- **Run Smoke Tests** - Only @smoke tagged tests
- **Run QAP-1 Tests** - Only @qap-1 tagged tests

### Runner Tasks:
- **Automation Approval Runner** - Approve and automate test cases
- **Jira Status Update Runner** - Update Jira status based on results

### Build Tasks:
- **Install Playwright Browsers** - Install browser dependencies
- **Build Project** - Compile and build the project
- **Clean Build** - Clean build artifacts

**Tip:** You can also run tasks from the terminal: `Ctrl+Shift+B` (Windows) or `Cmd+Shift+B` (Mac)

---

## 🔧 Modifying Runner Parameters

### Option 1: Edit Batch/Shell File Directly
**Windows (.bat):**
1. Right-click the `.bat` file
2. Select "Edit"
3. Modify the parameter values at the top
4. Save and close
5. Double-click to run

**Linux/Mac (.sh):**
1. Open the `.sh` file in any text editor
2. Modify the parameter values at the top
3. Save and close
4. Run from terminal: `./run-tests.sh`

### Option 2: Edit Config Files
For the approval and status update runners, modify the JSON config files:

**automation-approval.json:**
```json
{
  "approvedTestCases": [
    "QAP-3",
    "QAP-5"
  ]
}
```

**jira-config.json:**
```json
{
  "enableStatusUpdate": false,
  "statusMapping": {
    "pass": "Done",
    "fail": "FAIL"
  }
}
```

---

## 🚀 Quick Examples

### Run Regression Tests
Edit `run-tests.bat`:
```batch
SET TEST_TAGS=@regression
```

### Run Sanity Tests with Firefox
Edit `run-tests.bat`:
```batch
SET TEST_TAGS=@sanity
SET BROWSER=firefox
```

### Enable Jira Status Updates
Edit `jira-config.json`:
```json
{
  "enableStatusUpdate": true
}
```

### Approve Specific Test Cases
Edit `automation-approval.json`:
```json
{
  "approvedTestCases": [
    "QAP-3",
    "QAP-5",
    "QAP-7"
  ]
}
```

---

## 📝 Notes

- All batch files pause at the end so you can see the output
- Press any key to close the window after execution
- Gradle downloads dependencies on first run (may take a few minutes)
- Check the console output for any errors or warnings
- Jira credentials are loaded from `.devin/jira-confluence.env`

---

## 🐛 Troubleshooting

**Issue:** Batch file closes immediately
- **Solution:** Check for syntax errors in the batch file
- **Solution:** Run from Command Prompt to see error messages

**Issue:** Shell script permission denied
- **Solution:** Run `chmod +x *.sh` to make scripts executable
- **Solution:** Check file permissions

**Issue:** VS Code tasks not showing
- **Solution:** Ensure `.vscode/tasks.json` exists
- **Solution:** Reload VS Code window
- **Solution:** Check VS Code workspace settings

**Issue:** Jira connection fails
- **Solution:** Verify credentials in `.devin/jira-confluence.env`
- **Solution:** Check internet connection
- **Solution:** Verify Jira API token is valid

**Issue:** Tests fail to run
- **Solution:** Ensure Playwright browsers are installed: `gradlew.bat installPlaywrightBrowsers`
- **Solution:** Check environment and browser settings in batch file
- **Solution**: Verify test data files exist

---

## 📚 Additional Resources

- **Full documentation:** See `RUNNERS_GUIDE.md` for detailed runner documentation
- **Project rules:** See `.devin/AGENTS.md` for project-specific guidelines
- **Build commands:** See `AGENTS.md` for all available Gradle commands
