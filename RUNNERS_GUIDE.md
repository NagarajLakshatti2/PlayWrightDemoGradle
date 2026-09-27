# Jira Integration Runners Guide

This document explains how to use the Jira integration runners for automated test case management.

## 📁 Configuration Files

### 1. Automation Approval Config
**File:** `src/main/resources/config/automation-approval.json`

This file controls which test cases are approved for automation.

```json
{
  "approvedTestCases": [
    "QAP-3",
    "QAP-5"
  ]
}
```

**How to use:**
- Add test case IDs to `approvedTestCases` array to approve automation
- Remove IDs to skip automation
- Only test cases with status "TO BE AUTOMATE" in Jira will be considered

---

### 2. Jira Status Update Config
**File:** `src/main/resources/config/jira-config.json`

This file controls Jira status updates after test execution.

```json
{
  "enableStatusUpdate": false,
  "statusMapping": {
    "pass": "PASS",
    "fail": "FAIL"
  }
}
```

**How to use:**
- Set `enableStatusUpdate` to `true` to enable Jira status updates
- Set to `false` for dry run (no Jira updates)
- Customize `statusMapping` if your Jira uses different status names

---

## 🚀 Runners

### 1. Automation Approval Runner

**Purpose:** Review and approve test cases for automation

**Run:**
```bash
./gradlew.bat runAutomationApprovalRunner
```

**What it does:**
1. Fetches test cases with status "TO BE AUTOMATE" from Jira
2. Filters by IDs in `automation-approval.json`
3. Displays Epic, Story, Subtask, Title, and Jira URL
4. Shows which test cases are approved and which are skipped

**Note:** If Jira MCP is not configured, it uses mock data for demonstration.

**Output Example:**
```
===========================================
Approved for Automation
===========================================
Epic       Story      Subtask    Title                                  Jira URL
QAP-1      QAP-2      QAP-3      Verify Text Input Functionality         https://nagarajlakshatti11.atlassian.net/browse/QAP-3
QAP-1      QAP-4      QAP-5      Verify Email Input Validation           https://nagarajlakshatti11.atlassian.net/browse/QAP-5
===========================================
```

---

### 2. Jira Status Update Runner

**Purpose:** Run tests and update Jira test case status

**Run:**
```bash
# Run regression tests (default)
./gradlew.bat runJiraStatusUpdateRunner

# Run with specific tags
./gradlew.bat runJiraStatusUpdateRunner --args="sanity"

# Run with multiple tags
./gradlew.bat runJiraStatusUpdateRunner --args="sanity smoke"
```

**What it does:**
1. Reads configuration from `jira-config.json`
2. Runs tests by specified tags (regression, sanity, smoke, etc.)
3. If `enableStatusUpdate = true`:
   - Updates Jira subtask status to PASS/FAIL
4. If `enableStatusUpdate = false`:
   - Dry run (no Jira updates)

**Note:** Currently uses mock test results. Actual test execution integration pending.

**Output Example:**
```
===========================================
Test Results
===========================================
Test Case        Status     Jira URL
QAP-3            PASS       https://nagarajlakshatti11.atlassian.net/browse/QAP-3
QAP-5            PASS       https://nagarajlakshatti11.atlassian.net/browse/QAP-5
===========================================
```

---

## 🔄 Workflow

### Step 1: Review Test Cases for Automation
1. Update `automation-approval.json` with test case IDs to automate
2. Run `AutomationApprovalRunner` to review approved cases
3. Proceed with automation or adjust approval list

### Step 2: Run Tests and Update Status
1. Set `enableStatusUpdate = true` in `jira-config.json` (for production runs)
2. Run `JiraStatusUpdateRunner` with appropriate tags
3. Review results in console output
4. Jira subtask status will be updated to PASS/FAIL

### Step 3: Review Parent Story Status
- QA team manually reviews parent story status
- Mark user story as PASS/FAIL based on test results

---

## 📝 Notes

- **Safety First:** `enableStatusUpdate` defaults to `false` to prevent accidental Jira updates
- **Test Case Status:** Only updates subtask status, not parent story
- **Customization:** You can customize status names in `jira-config.json`
- **Tags:** Use tags like `regression`, `sanity`, `smoke` to organize test runs

---

## 🔧 Development Notes

These runners currently use **placeholder implementations** for:
- Jira MCP integration (fetching test cases)
- Test execution (running Cucumber tests)
- Jira status updates (updating Jira via MCP)

To complete the implementation:
1. Integrate Jira MCP to fetch real test cases
2. Integrate Cucumber test execution
3. Integrate Jira MCP to update status

The framework and configuration are ready for these integrations.
