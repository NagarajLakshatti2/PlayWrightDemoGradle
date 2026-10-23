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

# Phase 2: Code Evaluation (Based on user decisions)
echo "Phase 2: Code Evaluation (Based on user decisions)"
echo "-------------------------------------------"
echo ""
echo "User Decisions:"
echo "  ✅ REMOVE: LeetCode practice code"
echo "  ✅ KEEP: Mobile testing (planning to work later)"
echo "  ✅ REMOVE: Old web pages (SauceDemo)"
echo "  ✅ REMOVE: AI features (for now)"
echo "  ✅ REMOVE: Confluence integration (using Jira instead)"
echo "  ✅ KEEP: Old Jira MCP integration (need it)"
echo "  ✅ KEEP: Advanced utilities (need them)"
echo ""
echo "Executing Phase 2 cleanup based on user decisions..."
echo ""

# Remove LeetCode practice code
echo "Removing LeetCode practice code..."
rm -rf src/main/java/web/pages/LeetCode/
echo "✅ Removed LeetCode practice code"

# Remove old web pages (SauceDemo)
echo "Removing old web pages (SauceDemo)..."
rm src/main/java/web/pages/LoginPage.java
rm src/main/java/web/pages/CartPage.java
rm src/main/java/web/pages/CheckoutPage.java
rm src/main/java/web/pages/InventoryPage.java
echo "✅ Removed old web pages"

# Remove AI features
echo "Removing AI features..."
rm -rf src/main/java/ai/
echo "✅ Removed AI features"

# Remove Confluence integration
echo "Removing Confluence integration..."
rm -rf src/main/java/confluence/
echo "✅ Removed Confluence integration"

# Remove test case generator
echo "Removing test case generator..."
rm src/main/java/integration/TestCaseGenerator.java
echo "✅ Removed test case generator"

# Remove obsolete documentation
echo "Removing obsolete documentation..."
rm AI_Plane_e2e.md
rm qodana.yaml
rm QODANA_SETUP.md
rm automation-practice-websites.md
rm jira-status-workflow-diagram.md
echo "✅ Removed obsolete documentation"

echo ""
echo "Keeping (per user decision):"
echo "  ✅ Mobile testing (src/main/java/mobile/)"
echo "  ✅ Old Jira MCP integration (src/main/java/jira/)"
echo "  ✅ Advanced utilities (src/main/java/utils/)"
echo "  ✅ qapracticehub-test-cases.md"
echo ""
echo "Phase 2 completed successfully!"
echo ""
echo "==========================================="
echo "Phase 1: ✅ Completed"
echo "Phase 2: ✅ Completed"
echo "==========================================="
echo ""
echo "Next steps:"
echo "1. Test your automation: ./gradlew.bat clean test"
echo "2. Commit changes: git add . && git commit -m 'Phase 2 cleanup'"
echo "3. Push to GitHub: git push origin feature/qap-1-test-automation"
