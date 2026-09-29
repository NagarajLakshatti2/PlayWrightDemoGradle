#!/bin/bash
# ============================================
# Test Runner
# ============================================
# This runner executes the Cucumber tests.
#
# To modify parameters, edit the values below:
# ============================================

# Environment: dev, staging, prod
ENV="dev"

# Browser: chromium, firefox, webkit
BROWSER="chromium"

# Headless mode: true, false
HEADLESS="true"

# Test tags: @regression, @sanity, @smoke, @qap-1
# Leave empty to run all tests
TEST_TAGS=""

# Visual validation strict mode: true, false
VISUAL_STRICT="false"

# Rerun tasks: true, false
RERUN_TASKS="true"

# ============================================
# Do not modify below this line
# ============================================

echo "==========================================="
echo "Test Runner"
echo "==========================================="
echo ""
echo "Configuration:"
echo "  Environment: $ENV"
echo "  Browser: $BROWSER"
echo "  Headless: $HEADLESS"
echo "  Test Tags: $TEST_TAGS"
echo "  Visual Strict: $VISUAL_STRICT"
echo "  Rerun Tasks: $RERUN_TASKS"
echo ""
echo "==========================================="
echo ""

# Build the Gradle command
GRADLE_CMD="./gradlew clean test -Denv=$ENV -Dbrowser=$BROWSER -Dheadless=$HEADLESS"

if [ ! -z "$VISUAL_STRICT" ]; then
    GRADLE_CMD="$GRADLE_CMD -Dvisual.strict=$VISUAL_STRICT"
fi

if [ ! -z "$TEST_TAGS" ]; then
    GRADLE_CMD="$GRADLE_CMD -Dcucumber.filter.tags=$TEST_TAGS"
fi

if [ "$RERUN_TASKS" == "true" ]; then
    GRADLE_CMD="$GRADLE_CMD --rerun-tasks"
fi

# Run the command
echo "Running: $GRADLE_CMD"
echo ""
eval $GRADLE_CMD

echo ""
echo "==========================================="
echo "Press Enter to close..."
read
