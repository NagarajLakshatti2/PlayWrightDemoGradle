#!/bin/bash
# ============================================
# Jira Status Update Runner
# ============================================
# This runner updates Jira test case status based on test results.
# To enable/disable, edit the jira-config.json file.
#
# To modify parameters, edit the values below:
# ============================================

# Optional: Test tags to run (e.g., regression, sanity, smoke)
# Leave empty to run all tests
TEST_TAGS="regression"

# Optional: Environment (e.g., dev, staging, prod)
ENV="dev"

# Optional: Browser (e.g., chromium, firefox, webkit)
BROWSER="chromium"

# Optional: Headless mode (true/false)
HEADLESS="true"

# ============================================
# Do not modify below this line
# ============================================

echo "==========================================="
echo "Jira Status Update Runner"
echo "==========================================="
echo ""
echo "Configuration:"
echo "  Test Tags: $TEST_TAGS"
echo "  Environment: $ENV"
echo "  Browser: $BROWSER"
echo "  Headless: $HEADLESS"
echo ""
echo "==========================================="
echo ""

# Run the Gradle task
./gradlew runJiraStatusUpdateRunner

echo ""
echo "==========================================="
echo "Press Enter to close..."
read
