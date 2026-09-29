#!/bin/bash
# ============================================
# Project Cleanup Script
# ============================================
# This script helps clean up the project by removing
# unused files, logs, and generated directories.
#
# IMPORTANT: Review the cleanup-evaluation.md before running!
# ============================================

echo "==========================================="
echo "Project Cleanup Script"
echo "==========================================="
echo ""

# Phase 1: Safe Cleanup (No impact on code)
echo "Phase 1: Safe Cleanup (No impact on code)"
echo "-------------------------------------------"

# Remove error logs
echo "Removing error logs..."
rm -f hs_err_pid*.log
rm -f replay_pid*.log
echo "✅ Removed error logs"

# Remove test file
echo "Removing test file..."
rm -f test-bat.bat
echo "✅ Removed test-bat.bat"

# Remove generated directories
echo "Removing generated directories..."
rm -rf bin/
rm -rf test-output/
echo "✅ Removed bin/ and test-output/"

echo ""
echo "Phase 1 completed successfully!"
echo ""

# Phase 2: Code Evaluation (Requires manual review)
echo "Phase 2: Code Evaluation (Requires manual review)"
echo "-------------------------------------------"
echo ""
echo "The following directories/files require manual evaluation:"
echo ""
echo "📁 Potentially Unused Code:"
echo "  - src/main/java/web/pages/LeetCode/ (15+ files)"
echo "  - src/main/java/mobile/screens/ (3 files)"
echo "  - src/main/java/web/pages/LoginPage.java"
echo "  - src/main/java/web/pages/CartPage.java"
echo "  - src/main/java/web/pages/CheckoutPage.java"
echo "  - src/main/java/web/pages/InventoryPage.java"
echo "  - src/main/java/ai/ (3 files)"
echo "  - src/main/java/jira/JiraIntegrationUtil.java (old MCP)"
echo "  - src/main/java/confluence/ (2 files)"
echo "  - src/main/java/integration/TestCaseGenerator.java"
echo "  - src/main/java/utils/FailureTriageUtils.java"
echo "  - src/main/java/utils/KnowledgeIndexUtils.java"
echo "  - src/main/java/utils/QaSummaryGenerator.java"
echo "  - src/main/java/utils/TestPrioritizationUtils.java"
echo "  - src/main/java/utils/VisualValidationUtils.java"
echo ""
echo "📁 Potentially Unused Documentation:"
echo "  - AI_Plane_e2e.md"
echo "  - qodana.yaml"
echo "  - QODANA_SETUP.md"
echo "  - automation-practice-websites.md"
echo "  - jira-status-workflow-diagram.md"
echo "  - qapracticehub-test-cases.md"
echo ""
echo "⚠️  DO NOT proceed with Phase 2 without reviewing cleanup-evaluation.md"
echo ""
echo "==========================================="
echo "Phase 1: ✅ Completed"
echo "Phase 2: ⏸️  Requires manual review"
echo "==========================================="
