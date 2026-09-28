# Test Runner GUI - Visual Parameter Selection

A user-friendly Java Swing GUI popup for selecting test parameters and running tests.

## 🎯 What is it?

A visual popup window that allows you to:
- ✅ Select environment from dropdown (dev, staging, prod)
- ✅ Select browser from dropdown (chromium, firefox, webkit)
- ✅ Toggle headless mode with checkbox
- ✅ Select test tags from dropdown (All, @regression, @sanity, @smoke, @qap-1)
- ✅ Select runner type (Test Runner, Automation Approval, Jira Status Update)
- ✅ See live command preview
- ✅ Click "Run" button to execute
- ✅ Confirmation dialog before running

## 🚀 How to Launch

### Method 1: VS Code Tasks (Recommended for VS Code/Devin IDE)
1. Press `Ctrl+Shift+P`
2. Type "Tasks: Run Task"
3. Select "Test Runner GUI"

### Method 2: IntelliJ IDEA
1. Click the dropdown next to the Run button (top right)
2. Select "Test Runner GUI"
3. Click the Run button (green triangle)

### Method 3: Gradle Command
```bash
./gradlew.bat runTestRunnerGUI
```

### Method 4: Batch File (Windows Desktop)
```cmd
launch-gui.bat
```

---

## 🎨 GUI Features

### **Parameter Selection**

**Environment Dropdown:**
- dev
- staging
- prod

**Browser Dropdown:**
- chromium
- firefox
- webkit

**Headless Mode Checkbox:**
- Checked = Run tests in background (no visible browser)
- Unchecked = Show browser window during test execution

**Test Tags Dropdown:**
- All Tests - Run all tests
- @regression - Run only regression tests
- @sanity - Run only sanity tests
- @smoke - Run only smoke tests
- @qap-1 - Run only QAP-1 epic tests

**Runner Dropdown:**
- Test Runner - Execute Cucumber tests
- Automation Approval Runner - Approve and automate test cases from Jira
- Jira Status Update Runner - Update Jira status based on test results

### **Command Preview**

Shows the exact Gradle command that will be executed:
```
./gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true"
```

Updates automatically as you change parameters.

### **Run Button**

Click to execute the command:
1. Shows confirmation dialog
2. Displays progress dialog during execution
3. Shows success/error message when complete

### **Cancel Button**

Closes the GUI without running anything.

---

## 📸 GUI Layout

```
┌─────────────────────────────────────────────┐
│         Test Automation Runner              │
├─────────────────────────────────────────────┤
│                                             │
│  Environment: [dev ▼]                        │
│  Browser:     [chromium ▼]                  │
│  Mode:        ☑ Headless Mode              │
│  Test Tags:   [All Tests ▼]                 │
│  Runner:      [Test Runner ▼]               │
│                                             │
│  Command Preview:                           │
│  ┌─────────────────────────────────────┐   │
│  │ ./gradlew.bat clean test...        │   │
│  └─────────────────────────────────────┘   │
│                                             │
│                      [Cancel]  [▶ Run]     │
└─────────────────────────────────────────────┘
```

---

## 🎯 Use Cases

### **Scenario 1: Run Regression Tests**
1. Select Environment: `dev`
2. Select Browser: `chromium`
3. Check Headless Mode: `☑`
4. Select Test Tags: `@regression`
5. Select Runner: `Test Runner`
6. Click **Run**

### **Scenario 2: Run Visible Tests**
1. Select Environment: `dev`
2. Select Browser: `chromium`
3. Check Headless Mode: `☐` (unchecked)
4. Select Test Tags: `All Tests`
5. Select Runner: `Test Runner`
6. Click **Run**

### **Scenario 3: Approve Test Cases**
1. Select Runner: `Automation Approval Runner`
2. Click **Run**

### **Scenario 4: Update Jira Status**
1. Select Runner: `Jira Status Update Runner`
2. Click **Run**

---

## 🔧 Customization

### **Adding New Environments**

Edit `TestRunnerGUI.java`:
```java
environmentCombo = new JComboBox<>(new String[]{"dev", "staging", "prod", "uat"});
```

### **Adding New Browsers**

Edit `TestRunnerGUI.java`:
```java
browserCombo = new JComboBox<>(new String[]{"chromium", "firefox", "webkit", "edge"});
```

### **Adding New Test Tags**

Edit `TestRunnerGUI.java`:
```java
testTagsCombo = new JComboBox<>(new String[]{"All Tests", "@regression", "@sanity", "@smoke", "@qap-1", "@custom"});
```

---

## 🐛 Troubleshooting

### **Issue: GUI doesn't launch**
- **Solution:** Ensure Java is installed and in PATH
- **Solution:** Check that the project compiled successfully: `./gradlew.bat compileJava`

### **Issue: Command execution fails**
- **Solution:** Check that `gradlew.bat` exists in project root
- **Solution:** Verify the command preview shows the correct parameters
- **Solution:** Try running the command manually in terminal

### **Issue: Progress dialog doesn't close**
- **Solution:** The command may still be running in background
- **Solution:** Check terminal for any errors
- **Solution:** Force close the GUI and check process manager

---

## 📝 Notes

- The GUI uses Java Swing, which is included in standard Java installations
- Commands run in a separate thread to keep the GUI responsive
- The GUI automatically detects your OS (Windows/Linux/Mac) and uses the appropriate command syntax
- Confirmation dialog prevents accidental execution
- Progress dialog shows during execution with status updates

---

## 🎉 Benefits

- ✅ **Visual Interface** - No need to remember command-line parameters
- ✅ **Live Preview** - See the exact command before running
- ✅ **Confirmation** - Prevents accidental execution
- ✅ **Progress Feedback** - Shows status during execution
- ✅ **Cross-Platform** - Works on Windows, Linux, Mac
- ✅ **Easy to Use** - Just select and click Run

---

## 📚 Related Documentation

- **IDE Run Guide:** See `IDE_RUN_GUIDE.md` for IDE-specific run methods
- **Runners Guide:** See `RUNNERS_GUIDE.md` for detailed runner documentation
- **Quick Start:** See `RUNNERS_README.md` for batch/shell script usage
