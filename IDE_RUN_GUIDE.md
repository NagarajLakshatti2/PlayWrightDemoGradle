# IDE Run Guide - IntelliJ IDEA, VS Code, and Devin IDE

Complete guide for running the automation framework in your preferred IDE.

## 🎯 Quick Summary

| IDE | Method | Steps |
|-----|--------|-------|
| **IntelliJ IDEA** | Run Configurations | Run → Run Configurations → Select |
| **VS Code** | Tasks | Ctrl+Shift+P → Tasks: Run Task → Select |
| **Devin IDE** | Tasks | Ctrl+Shift+P → Tasks: Run Task → Select |
| **Any IDE** | Terminal | `./gradlew.bat clean test -Denv=dev -Dbrowser=chromium -Dheadless=true` |

---

## 🚀 IntelliJ IDEA

### Method 1: Run Configurations (Recommended)

1. **Open IntelliJ IDEA** and open the project
2. **Locate Run Configurations**:
   - Click the dropdown next to the Run button (top right)
   - Or go to `Run → Edit Configurations...`
3. **Select a configuration**:
   - ✅ **Run Tests** - Default test run (Chromium, Dev, Headless)
   - ✅ **Automation Approval Runner** - Approve and automate test cases
   - ✅ **Jira Status Update Runner** - Update Jira status based on results
4. **Click the Run button** (green triangle) or press `Shift+F10`

### Method 2: Gradle Tool Window

1. **Open Gradle Tool Window**:
   - View → Tool Windows → Gradle
   - Or click the Gradle icon on the right sidebar
2. **Expand Tasks**:
   - Expand `PlayWrightDemoGradle`
   - Expand `application` or `other`
3. **Run tasks**:
   - Double-click `runAutomationApprovalRunner`
   - Double-click `runJiraStatusUpdateRunner`
   - Double-click `clean` then `test`

### Method 3: Terminal

1. **Open Terminal** in IntelliJ IDEA
2. Run:
   ```bash
   ./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true"
   ```

---

## 🚀 VS Code

### Method 1: VS Code Tasks (Recommended)

1. **Open VS Code** and open the project
2. **Open Task Runner**:
   - Press `Ctrl+Shift+P` (Windows/Linux)
   - Press `Cmd+Shift+P` (Mac)
3. **Type "Tasks: Run Task"** and press Enter
4. **Select from dropdown**:
   - ✅ **Run Tests (Chromium - Dev - Headless)** - Default
   - ✅ **Run Tests (Firefox - Dev - Headless)** - Firefox browser
   - ✅ **Run Tests (Chromium - Dev - Visible)** - Visible browser
   - ✅ **Run Regression Tests** - Only @regression tests
   - ✅ **Run Sanity Tests** - Only @sanity tests
   - ✅ **Run Smoke Tests** - Only @smoke tests
   - ✅ **Run QAP-1 Tests** - Only @qap-1 tests
   - ✅ **Automation Approval Runner** - Approve test cases
   - ✅ **Jira Status Update Runner** - Update Jira status
   - ✅ **Install Playwright Browsers** - Install browser dependencies
   - ✅ **Build Project** - Compile and build
   - ✅ **Clean Build** - Clean build artifacts

### Method 2: Keyboard Shortcut

- Press `Ctrl+Shift+B` (Windows/Linux)
- Press `Cmd+Shift+B` (Mac)
- Select the default task: "Run Tests (Chromium - Dev - Headless)"

### Method 3: Terminal

1. **Open Terminal** in VS Code (`Ctrl+```)
2. Run:
   ```bash
   ./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true"
   ```

---

## 🚀 Devin IDE

### Method 1: VS Code Tasks (Recommended)

Devin IDE supports VS Code tasks, so it works exactly like VS Code:

1. **Open Task Runner**:
   - Press `Ctrl+Shift+P`
2. **Type "Tasks: Run Task"** and press Enter
3. **Select from dropdown** (same options as VS Code above)

### Method 2: Terminal

1. **Open Terminal** in Devin IDE
2. Run:
   ```bash
   ./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true"
   ```

### Method 3: Shell Scripts

Since Devin IDE is web-based, shell scripts might work better:

```bash
./run-tests.sh
./run-automation-approval.sh
./run-jira-status-update.sh
```

---

## 🚀 Universal Method (Works in Any IDE)

### Gradle Commands via Terminal

These commands work in **any IDE with a terminal**:

#### Run Tests
```bash
./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true"
```

#### Run with Firefox
```bash
./gradlew.bat clean test "-Denv=dev" "-Dbrowser=firefox" "-Dheadless=true"
```

#### Run with Visible Browser
```bash
./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=false"
```

#### Run Regression Tests
```bash
./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dcucumber.filter.tags=@regression"
```

#### Run Sanity Tests
```bash
./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dcucumber.filter.tags=@sanity"
```

#### Run Smoke Tests
```bash
./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dcucumber.filter.tags=@smoke"
```

#### Automation Approval Runner
```bash
./gradlew.bat runAutomationApprovalRunner
```

#### Jira Status Update Runner
```bash
./gradlew.bat runJiraStatusUpdateRunner
```

#### Install Playwright Browsers
```bash
./gradlew.bat installPlaywrightBrowsers
```

---

## 🎯 Recommended Approach by IDE

### IntelliJ IDEA Users
- **Best:** Run Configurations (dropdown menu)
- **Alternative:** Gradle Tool Window
- **Fallback:** Terminal

### VS Code Users
- **Best:** VS Code Tasks (Ctrl+Shift+P → Tasks: Run Task)
- **Alternative:** Keyboard shortcut (Ctrl+Shift+B)
- **Fallback:** Terminal

### Devin IDE Users
- **Best:** VS Code Tasks (Ctrl+Shift+P → Tasks: Run Task)
- **Alternative:** Terminal commands
- **Fallback:** Shell scripts

---

## 📝 Customizing Run Configurations

### IntelliJ IDEA

1. Go to `Run → Edit Configurations...`
2. Select a configuration
3. Modify parameters:
   - Environment: `-Denv=dev` (dev, staging, prod)
   - Browser: `-Dbrowser=chromium` (chromium, firefox, webkit)
   - Headless: `-Dheadless=true` (true, false)
   - Tags: `-Dcucumber.filter.tags=@regression`

### VS Code / Devin IDE

1. Open `.vscode/tasks.json`
2. Modify the `args` array for any task
3. Save and reload the window

### Terminal Commands

Simply modify the command parameters directly:
```bash
./gradlew.bat clean test "-Denv=staging" "-Dbrowser=firefox" "-Dheadless=false"
```

---

## 🐛 Troubleshooting

### IntelliJ IDEA
- **Issue:** Run configurations not showing
  - **Solution:** Reload Gradle project: File → Invalidate Caches → Invalidate and Restart
- **Issue:** Gradle sync failed
  - **Solution:** Open Gradle settings and configure Gradle home directory

### VS Code / Devin IDE
- **Issue:** Tasks not showing
  - **Solution:** Reload VS Code window: Ctrl+Shift+P → "Developer: Reload Window"
- **Issue:** Gradle command not found
  - **Solution:** Ensure you're in the project root directory

### All IDEs
- **Issue:** Playwright browsers not installed
  - **Solution:** Run `./gradlew.bat installPlaywrightBrowsers`
- **Issue:** Tests fail to run
  - **Solution:** Check environment and browser settings
  - **Solution:** Verify test data files exist

---

## 📚 Additional Resources

- **Project Rules:** See `.devin/AGENTS.md` for project-specific guidelines
- **Runners Guide:** See `RUNNERS_GUIDE.md` for detailed runner documentation
- **Quick Start:** See `RUNNERS_README.md` for batch/shell script usage
- **Build Commands:** See `AGENTS.md` for all available Gradle commands

---

## 🎉 Summary

**You now have three different ways to run the automation framework:**

1. **IntelliJ IDEA** - Run Configurations (dropdown menu)
2. **VS Code** - Tasks (Ctrl+Shift+P → Tasks: Run Task)
3. **Devin IDE** - Tasks (Ctrl+Shift+P → Tasks: Run Task)

**Plus a universal terminal method that works in any IDE!**

Choose the method that works best for your preferred IDE. 🚀
