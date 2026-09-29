#!/bin/bash
# ============================================
# Automation Approval Runner
# ============================================
# This runner fetches test cases with status "TO BE AUTOMATE"
# and automates only the approved ones from the config file.
#
# To modify parameters, edit the values below:
# ============================================

# Optional: Pass custom Jira epic key (leave empty to use default)
JIRA_EPIC_KEY=""

# Optional: Custom config file path (leave empty to use default)
CONFIG_FILE=""

# ============================================
# Do not modify below this line
# ============================================

echo "==========================================="
echo "Automation Approval Runner"
echo "==========================================="
echo ""

# Check if custom epic key is provided
if [ ! -z "$JIRA_EPIC_KEY" ]; then
    echo "Using custom epic key: $JIRA_EPIC_KEY"
    echo ""
fi

# Check if custom config file is provided
if [ ! -z "$CONFIG_FILE" ]; then
    echo "Using custom config file: $CONFIG_FILE"
    echo ""
fi

# Run the Gradle task
./gradlew runAutomationApprovalRunner

echo ""
echo "==========================================="
echo "Press Enter to close..."
read
