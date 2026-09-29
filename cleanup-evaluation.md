# Code Evaluation for Cleanup

This document helps you evaluate which code to keep or remove before running Phase 2 cleanup.

## 📋 **Evaluation Questions**

### 1. LeetCode Practice Code

**Location:** `src/main/java/web/pages/LeetCode/` (15+ files)

**Question:** Do you need LeetCode practice code in your test automation framework?

**Answer Options:**
- ✅ **KEEP** - If you use this for practicing algorithms or as examples
- ❌ **REMOVE** - If this is just practice code not related to test automation

**Recommendation:** ❌ **REMOVE** - LeetCode practice code doesn't belong in a test automation framework. Move to a separate repository if needed.

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

**Recommendation:** ❌ **REMOVE** - Remove unless you have active mobile testing requirements. Can be added back later if needed.

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

**Recommendation:** ❌ **REMOVE** - These are advanced AI features. Remove unless actively used. Can be added back if needed.

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

**Recommendation:** ❌ **REMOVE** - You're using Jira for test management. Confluence integration is not actively used.

---

### 6. Old Jira Integration (MCP-based)

**Location:** `src/main/java/jira/JiraIntegrationUtil.java`

**Question:** Do you need the old MCP-based Jira integration?

**Answer Options:**
- ✅ **KEEP** - If you might use MCP in the future
- ❌ **REMOVE** - If you're using the new REST API integration

**Recommendation:** ❌ **REMOVE** - You now have `JiraApiClient.java` (REST API) which is working. The old MCP-based integration is obsolete.

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

**Recommendation:** ❌ **REMOVE** - These are advanced AI/ML features. Remove unless actively used. Keep the framework simple.

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

## 🎯 **Recommended Cleanup Plan**

### **Safe to Remove (Phase 2):**

```bash
# LeetCode practice code
rm -rf src/main/java/web/pages/LeetCode/

# Mobile testing
rm -rf src/main/java/mobile/

# Old web pages
rm src/main/java/web/pages/LoginPage.java
rm src/main/java/web/pages/CartPage.java
rm src/main/java/web/pages/CheckoutPage.java
rm src/main/java/web/pages/InventoryPage.java

# AI features
rm -rf src/main/java/ai/

# Confluence integration
rm -rf src/main/java/confluence/

# Old Jira integration
rm src/main/java/jira/JiraIntegrationUtil.java
rm src/main/java/jira/JiraTestRequirement.java

# Test case generator
rm src/main/java/integration/TestCaseGenerator.java

# Advanced utilities
rm src/main/java/utils/FailureTriageUtils.java
rm src/main/java/utils/KnowledgeIndexUtils.java
rm src/main/java/utils/QaSummaryGenerator.java
rm src/main/java/utils/TestPrioritizationUtils.java
rm src/main/java/utils/VisualValidationUtils.java

# Obsolete documentation
rm AI_Plane_e2e.md
rm qodana.yaml
rm QODANA_SETUP.md
rm automation-practice-websites.md
rm jira-status-workflow-diagram.md
```

### **Keep:**
- ✅ `qapracticehub-test-cases.md` (contains test case information)
- ✅ All QAP Practice Hub test automation code
- ✅ Jira REST API integration (new)
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
