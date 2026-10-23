# Code Evaluation for Cleanup

This document helps you evaluate which code to keep or remove before running Phase 2 cleanup.

## 📋 **Evaluation Questions**

### 1. LeetCode Practice Code

**Location:** `src/main/java/web/pages/LeetCode/` (15+ files)

**Question:** Do you need LeetCode practice code in your test automation framework?

**Answer Options:**
- ✅ **KEEP** - If you use this for practicing algorithms or as examples
- ❌ **REMOVE** - If this is just practice code not related to test automation

**User Decision:** ❌ **REMOVE** - Remove LeetCode practice code

**Recommendation:** ❌ **REMOVE** - LeetCode practice code doesn't belong in a test automation framework.

---

### 2. Mobile Testing

**Location:** `src/main/java/mobile/screens/` (3 files)

**Files:**
- `BaseScreen.java`
- `HomeScreen.java`
- `LoginScreen.java`

**Question:** Do you need mobile testing (Appium) in your framework?

**Answer Options:**
- ✅ **KEEP** - If you plan to test mobile apps
- ❌ **REMOVE** - If you only test web applications

**User Decision:** ✅ **KEEP** - Planning to work on mobile testing later

**Recommendation:** ✅ **KEEP** - User plans to work on mobile testing later.

---

### 3. Old Web Pages (SauceDemo)

**Location:** `src/main/java/web/pages/`

**Files:**
- `LoginPage.java`
- `CartPage.java`
- `CheckoutPage.java`
- `InventoryPage.java`

**Question:** Do you need these old SauceDemo web pages?

**Answer Options:**
- ✅ **KEEP** - If you still test SauceDemo website
- ❌ **REMOVE** - If you only test QAP Practice Hub

**User Decision:** ❌ **REMOVE** - Remove old SauceDemo web pages

**Recommendation:** ❌ **REMOVE** - You're now focused on QAP Practice Hub testing. These old pages are not used.

---

### 4. AI Features

**Location:** `src/main/java/ai/` (3 files)

**Files:**
- `AiConfig.java`
- `AiQaOrchestrator.java`
- `LlmGateway.java`

**Question:** Do you use AI features in your test automation?

**Answer Options:**
- ✅ **KEEP** - If you use AI for test generation, triage, or analysis
- ❌ **REMOVE** - If you don't use AI features

**User Decision:** ❌ **REMOVE** - Remove AI features for now

**Recommendation:** ❌ **REMOVE** - These are advanced AI features. Remove for now, can be added back if needed.

---

### 5. Confluence Integration

**Location:** `src/main/java/confluence/` (2 files)

**Files:**
- `ConfluenceIntegrationUtil.java`
- `ConfluenceRequirement.java`

**Question:** Do you need Confluence integration for documentation?

**Answer Options:**
- ✅ **KEEP** - If you publish test results to Confluence
- ❌ **REMOVE** - If you don't use Confluence

**Analysis:**
- ❌ **NOT ACTIVELY USED** - No references in test code
- ❌ **NOT NEEDED** - Jira handles all test case management
- ✅ **ALTERNATIVE EXISTS** - Using Jira REST API instead

**User Decision:** ❌ **REMOVE** - Not needed, using Jira instead

**Recommendation:** ❌ **REMOVE** - Confluence integration is not actively used. Jira handles all test management needs.

---

### 6. Old Jira Integration (MCP-based)

**Location:** `src/main/java/jira/JiraIntegrationUtil.java`

**Files:**
- `JiraIntegrationUtil.java`
- `JiraTestRequirement.java`

**Question:** Do you need the old MCP-based Jira integration?

**Answer Options:**
- ✅ **KEEP** - If you might use MCP in the future
- ❌ **REMOVE** - If you're using the new REST API integration

**User Decision:** ✅ **KEEP** - Need the old Jira MCP integration

**Recommendation:** ✅ **KEEP** - User wants to keep both MCP and REST API integrations for flexibility.

---

### 7. Advanced Utilities

**Location:** `src/main/java/utils/`

**Files:**
- `FailureTriageUtils.java`
- `KnowledgeIndexUtils.java`
- `QaSummaryGenerator.java`
- `TestPrioritizationUtils.java`
- `VisualValidationUtils.java`

**Question:** Do you use these advanced utilities?

**Answer Options:**
- ✅ **KEEP** - If you use failure triage, knowledge indexing, or visual validation
- ❌ **REMOVE** - If you don't use these advanced features

**User Decision:** ✅ **KEEP** - Need the advanced utilities

**Recommendation:** ✅ **KEEP** - User wants to keep advanced utilities for future use.

---

### 8. Test Case Generator

**Location:** `src/main/java/integration/TestCaseGenerator.java`

**Question:** Do you use the test case generator?

**Answer Options:**
- ✅ **KEEP** - If you auto-generate test cases
- ❌ **REMOVE** - If you write test cases manually

**Recommendation:** ❌ **REMOVE** - You're manually creating test cases. This generator is not actively used.

---

### 9. Documentation Files

**Location:** Project root

**Files:**
- `AI_Plane_e2e.md` (19KB)
- `qodana.yaml`
- `QODANA_SETUP.md`
- `automation-practice-websites.md`
- `jira-status-workflow-diagram.md`
- `qapracticehub-test-cases.md`

**Question:** Do you need these documentation files?

**Answer Options:**
- ✅ **KEEP** - If they contain useful information
- ❌ **REMOVE** - If they are outdated or not needed

**Recommendation:** 
- ❌ **REMOVE** - `AI_Plane_e2e.md`, `qodana.yaml`, `QODANA_SETUP.md` (not used)
- ❌ **REMOVE** - `automation-practice-websites.md`, `jira-status-workflow-diagram.md` (outdated)
- ✅ **KEEP** - `qapracticehub-test-cases.md` (contains test case info)

---

## 🎯 **Updated Cleanup Plan Based on User Decisions**

### **Safe to Remove (Phase 2):**

```bash
# LeetCode practice code
rm -rf src/main/java/web/pages/LeetCode/

# Old web pages (SauceDemo)
rm src/main/java/web/pages/LoginPage.java
rm src/main/java/web/pages/CartPage.java
rm src/main/java/web/pages/CheckoutPage.java
rm src/main/java/web/pages/InventoryPage.java

# AI features
rm -rf src/main/java/ai/

# Confluence integration (not actively used, using Jira instead)
rm -rf src/main/java/confluence/

# Test case generator (not actively used)
rm src/main/java/integration/TestCaseGenerator.java

# Obsolete documentation
rm AI_Plane_e2e.md
rm qodana.yaml
rm QODANA_SETUP.md
rm automation-practice-websites.md
rm jira-status-workflow-diagram.md
```

### **Keep (User Decision):**
- ✅ `src/main/java/mobile/` - Planning to work on mobile testing later
- ✅ `src/main/java/jira/JiraIntegrationUtil.java` - Need old Jira MCP integration
- ✅ `src/main/java/jira/JiraTestRequirement.java` - Need old Jira MCP integration
- ✅ `src/main/java/utils/FailureTriageUtils.java` - Need advanced utilities
- ✅ `src/main/java/utils/KnowledgeIndexUtils.java` - Need advanced utilities
- ✅ `src/main/java/utils/QaSummaryGenerator.java` - Need advanced utilities
- ✅ `src/main/java/utils/TestPrioritizationUtils.java` - Need advanced utilities
- ✅ `src/main/java/utils/VisualValidationUtils.java` - Need advanced utilities
- ✅ `qapracticehub-test-cases.md` - Contains test case information
- ✅ All QAP Practice Hub test automation code
- ✅ Jira REST API integration (new)
- ✅ Jira MCP integration (old - keeping both)
- ✅ GUI and runners
- ✅ Core utilities (PlaywrightManager, TestDataLoader, etc.)

---

## ⚠️ **Before Running Phase 2:**

1. **Review this document** - Make sure you agree with the recommendations
2. **Backup your project** - Create a backup branch: `git checkout -b backup-before-cleanup`
3. **Test locally** - Ensure your tests still work after cleanup
4. **Commit changes** - Commit the cleanup in a separate commit

---

## 🚀 **How to Proceed:**

1. **Edit this file** - Change ❌ to ✅ for anything you want to keep
2. **Run Phase 1** - Execute `cleanup-script.sh` for safe cleanup
3. **Review and approve** - Confirm you agree with Phase 2 recommendations
4. **Run Phase 2** - Execute the recommended cleanup commands
5. **Test** - Run your tests to ensure everything still works
6. **Commit** - Commit the cleanup changes

---

## 📝 **Customize Your Cleanup:**

If you disagree with any recommendation:
1. Change ❌ to ✅ in this document
2. Remove that file/directory from the cleanup commands
3. Add a note explaining why you want to keep it

**Example:**
```markdown
### 2. Mobile Testing
**Recommendation:** ✅ **KEEP** - We will be testing mobile apps next month
```

---

**Ready to proceed?** Edit this file to customize your cleanup plan, then run the cleanup script.
