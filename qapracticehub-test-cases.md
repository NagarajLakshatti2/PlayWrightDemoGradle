# QA Practice Hub Test Cases for Jira Import

## Jira Structure: Epic → User Story → Test Case

### Template Structure:
- **Epic**: Major module/category
- **User Story**: Specific feature within the epic
- **Test Case**: Detailed test scenario with steps and expected results

---

## EPIC-1: Text Input Module
*Epic for testing various input field types and their validation*

### USER STORY-1.1: Basic Text Input Functionality
*As a QA engineer, I want to test basic text input fields so that I can verify users can enter and clear text correctly*

#### Test Case QPH-001: Verify Text Input Field Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the text input field
  2. Enter text "Hello World"
  3. Verify the text is entered correctly
  4. Clear the text field
  5. Verify the field is empty
- **Expected Results**: Text should be entered and cleared successfully
- **Test Data**: "Hello World"

### USER STORY-1.2: Email Input Validation
*As a QA engineer, I want to test email input validation so that only valid email addresses are accepted*

#### Test Case QPH-002: Verify Email Input Validation
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the email input field
  2. Enter valid email "test@example.com"
  3. Verify the email is accepted
  4. Enter invalid email "invalid-email"
  5. Verify validation error if present
- **Expected Results**: Valid email should be accepted, invalid email should show validation

### USER STORY-1.3: Password Input Security
*As a QA engineer, I want to test password input masking so that sensitive data is protected*

#### Test Case QPH-003: Verify Password Input Masking
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the password input field
  2. Enter "password123"
  3. Verify the password is masked (shows dots/asterisks)
  4. Verify the password value is stored correctly
- **Expected Results**: Password should be masked in display but stored correctly

### USER STORY-1.4: Numeric Input Validation
*As a QA engineer, I want to test number input fields so that only numeric values are accepted*

#### Test Case QPH-004: Verify Number Input Constraints
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the number input field
  2. Enter valid number "123"
  3. Verify the number is accepted
  4. Enter invalid text "abc"
  5. Verify the input is rejected or ignored
- **Expected Results**: Only numbers should be accepted

### USER STORY-1.5: Date Input Functionality
*As a QA engineer, I want to test date input fields so that users can select dates correctly*

#### Test Case QPH-005: Verify Date Input Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the date input field
  2. Click on the date picker
  3. Select a date (e.g., 2026-09-25)
  4. Verify the date is displayed correctly
- **Expected Results**: Date should be selected and displayed in correct format

### USER STORY-1.6: Phone Input with Country Code
*As a QA engineer, I want to test phone input with country code so that phone numbers are validated correctly*

#### Test Case QPH-006: Verify Phone Input with Country Code
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the phone input field with India (+91) country code
  2. Enter 10-digit phone number "9876543210"
  3. Verify the phone number is accepted
  4. Enter less than 10 digits
  5. Verify validation error if present
- **Expected Results**: Valid 10-digit number should be accepted

### USER STORY-1.7: Multi-line Text Input
*As a QA engineer, I want to test textarea fields so that users can enter multi-line content*

#### Test Case QPH-007: Verify Textarea Multi-line Input
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the textarea field
  2. Enter multi-line text
  3. Verify the text is displayed with line breaks
  4. Clear the textarea
- **Expected Results**: Multi-line text should be displayed correctly

### USER STORY-1.8: Read-only Input Behavior
*As a QA engineer, I want to test read-only input fields so that users cannot modify pre-filled data*

#### Test Case QPH-008: Verify Read-only Input Behavior
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the read-only input field
  2. Attempt to enter text
  3. Verify the field cannot be modified
  4. Verify the existing value is displayed
- **Expected Results**: Read-only field should not accept input

### USER STORY-1.9: Disabled Input Behavior
*As a QA engineer, I want to test disabled input fields so that users cannot interact with them*

#### Test Case QPH-009: Verify Disabled Input Behavior
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the disabled input field
  2. Attempt to click or enter text
  3. Verify the field is not interactive
  4. Verify the disabled attribute is present
- **Expected Results**: Disabled field should not be interactive

---

## EPIC-2: Authentication Module
*Epic for testing login, registration, and OTP authentication functionality*

### USER STORY-2.1: User Login Functionality
*As a QA engineer, I want to test user login so that authenticated users can access the system*

#### Test Case QPH-010: Verify Successful Login
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the login form
  2. Enter username "tester"
  3. Enter password "password123"
  4. Click Login button
  5. Verify successful login
- **Expected Results**: Login should be successful with valid credentials
- **Test Data**: Username: "tester", Password: "password123"

#### Test Case QPH-011: Verify Failed Login with Invalid Credentials
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the login form
  2. Enter invalid username "invalid"
  3. Enter invalid password "wrong"
  4. Click Login button
  5. Verify error message is displayed
- **Expected Results**: Login should fail with appropriate error message
- **Test Data**: Username: "invalid", Password: "wrong"

#### Test Case QPH-012: Verify Login with Empty Fields
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the login form
  2. Leave username field empty
  3. Leave password field empty
  4. Click Login button
  5. Verify validation error is displayed
- **Expected Results**: Form validation should prevent empty submission

### USER STORY-2.2: User Registration Functionality
*As a QA engineer, I want to test user registration so that new users can create accounts*

#### Test Case QPH-013: Verify Registration Form Submission
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the registration form
  2. Enter First Name "John"
  3. Enter Last Name "Doe"
  4. Enter Email "john.doe@example.com"
  5. Select Country "United States"
  6. Click Register button
  7. Verify successful registration
- **Expected Results**: Registration should be successful with valid data
- **Test Data**: First Name: "John", Last Name: "Doe", Email: "john.doe@example.com", Country: "United States"

#### Test Case QPH-014: Verify Registration Form Clear Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Fill in all registration form fields
  2. Click Clear button
  3. Verify all fields are cleared
- **Expected Results**: All form fields should be cleared

#### Test Case QPH-015: Verify Country Dropdown Selection
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the country dropdown in registration form
  2. Select "India"
  3. Verify the selection is displayed
  4. Select "United Kingdom"
  5. Verify the selection changes
- **Expected Results**: Country selection should work correctly

### USER STORY-2.3: OTP Authentication
*As a QA engineer, I want to test OTP-based authentication so that users can verify their identity*

#### Test Case QPH-016: Verify OTP Fetch Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section
- **Test Steps**:
  1. Locate the OTP login form
  2. Enter username "testuser"
  3. Click "Fetch OTP" button
  4. Wait for OTP to be generated/displayed
  5. Verify OTP is received
- **Expected Results**: OTP should be generated and displayed
- **Test Data**: Username: "testuser"

#### Test Case QPH-017: Verify OTP Login with Valid OTP
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section, Fetch OTP first
- **Test Steps**:
  1. Enter username "testuser"
  2. Fetch OTP
  3. Enter the received 6-digit OTP
  4. Click Submit button
  5. Verify successful login
- **Expected Results**: Login should be successful with valid OTP
- **Test Data**: Username: "testuser", OTP: (as received)

#### Test Case QPH-018: Verify OTP Resend Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section
- **Test Steps**:
  1. Enter username "testuser"
  2. Click "Fetch OTP" button
  3. Wait for 30 seconds (or until fetch tab closes)
  4. Click "Resend OTP" button
  5. Verify new OTP is generated
- **Expected Results**: New OTP should be generated on resend
- **Test Data**: Username: "testuser"

#### Test Case QPH-019: Verify OTP Login with Invalid OTP
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section
- **Test Steps**:
  1. Enter username "testuser"
  2. Enter invalid 6-digit OTP "000000"
  3. Click Submit button
  4. Verify error message is displayed
- **Expected Results**: Login should fail with invalid OTP
- **Test Data**: Username: "testuser", Invalid OTP: "000000"

---

## EPIC-3: Form Selection Controls Module
*Epic for testing radio buttons, checkboxes, dropdowns, and other selection controls*

### USER STORY-3.1: Radio Button Selection
*As a QA engineer, I want to test radio buttons so that users can select single options from a group*

#### Test Case QPH-020: Verify Radio Button Selection
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the Gender radio buttons
  2. Select "Male"
  3. Verify "Male" is selected
  4. Select "Female"
  5. Verify "Female" is selected and "Male" is deselected
- **Expected Results**: Only one radio button should be selected at a time

### USER STORY-3.2: Checkbox Selection
*As a QA engineer, I want to test checkboxes so that users can select multiple options*

#### Test Case QPH-021: Verify Checkbox Selection
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the Skills checkboxes
  2. Select "Selenium"
  3. Select "Playwright"
  4. Verify both are selected
  5. Deselect "Selenium"
  6. Verify only "Playwright" is selected
- **Expected Results**: Multiple checkboxes can be selected/deselected independently

### USER STORY-3.3: Dropdown Selection
*As a QA engineer, I want to test dropdown menus so that users can select from predefined options*

#### Test Case QPH-022: Verify Single Select Dropdown
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the country dropdown
  2. Click to open dropdown
  3. Select "India"
  4. Verify "India" is displayed as selected
  5. Change selection to "Canada"
  6. Verify "Canada" is now selected
- **Expected Results**: Single selection should work correctly

### USER STORY-3.4: Multi-Selection Controls
*As a QA engineer, I want to test multi-select controls so that users can select multiple items*

#### Test Case QPH-023: Verify Multi Select Listbox
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the multi-select listbox
  2. Select "JavaScript"
  3. Select "Python"
  4. Verify both are selected
  5. Deselect "JavaScript"
  6. Verify only "Python" remains selected
- **Expected Results**: Multiple items can be selected in listbox

#### Test Case QPH-024: Verify Multi Select Dropdown
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the multi-select dropdown
  2. Select "Selenium"
  3. Select "Cypress"
  4. Verify both are selected
  5. Verify selection display
- **Expected Results**: Multiple items can be selected in dropdown

### USER STORY-3.5: Advanced Selection Controls
*As a QA engineer, I want to test advanced selection controls like sliders, color pickers, and toggles*

#### Test Case QPH-025: Verify Range Slider Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the experience range slider
  2. Move slider to different positions
  3. Verify the value changes (e.g., "5 years")
  4. Set slider to minimum value
  5. Set slider to maximum value
- **Expected Results**: Slider should move and display correct values

#### Test Case QPH-026: Verify Color Picker
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the color picker
  2. Click to open color palette
  3. Select a color (e.g., red)
  4. Verify the color code is displayed
  5. Verify the color is applied
- **Expected Results**: Color picker should select and display color codes

#### Test Case QPH-027: Verify Toggle Switch
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the "Enable Notifications" toggle
  2. Click to turn ON
  3. Verify state changes to "On"
  4. Click to turn OFF
  5. Verify state changes to "Off"
- **Expected Results**: Toggle should switch between On/Off states

#### Test Case QPH-028: Verify Auto-complete Input
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the auto-complete input
  2. Start typing (e.g., "test")
  3. Verify suggestions appear
  4. Select a suggestion from the list
  5. Verify the selected value is populated
- **Expected Results**: Auto-complete should show suggestions and allow selection

---

## EPIC-4: Button and Link Interaction Module
*Epic for testing various button types, click actions, and navigation links*

### USER STORY-4.1: Basic Button Clicks
*As a QA engineer, I want to test basic button clicks so that users can trigger actions*

#### Test Case QPH-029: Verify Primary Button Click
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the Primary button
  2. Click the button
  3. Verify button click action is performed
  4. Verify output is displayed
- **Expected Results**: Primary button should respond to click

#### Test Case QPH-030: Verify Secondary Button Click
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the Secondary button
  2. Click the button
  3. Verify button click action is performed
- **Expected Results**: Secondary button should respond to click

#### Test Case QPH-031: Verify Disabled Button Behavior
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the Disabled button
  2. Attempt to click the button
  3. Verify button does not respond
  4. Verify disabled attribute is present
- **Expected Results**: Disabled button should not be clickable

### USER STORY-4.2: Advanced Click Actions
*As a QA engineer, I want to test advanced click actions like double-click and right-click*

#### Test Case QPH-032: Verify Double Click Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Double Click Me" button
  2. Perform double click action
  3. Verify double click action is performed
  4. Verify appropriate output
- **Expected Results**: Double click should trigger the intended action

#### Test Case QPH-033: Verify Right Click Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Right Click Me" button
  2. Perform right click action
  3. Verify right click action is performed
  4. Verify appropriate output
- **Expected Results**: Right click should trigger the intended action

#### Test Case QPH-034: Verify Hover Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Hover Over Me" button
  2. Move mouse over the button
  3. Verify hover effect/action is triggered
  4. Move mouse away
  5. Verify hover effect is removed
- **Expected Results**: Hover should trigger and remove effect appropriately

### USER STORY-4.3: Navigation and Action Links
*As a QA engineer, I want to test navigation links and action buttons so that users can navigate and perform actions*

#### Test Case QPH-035: Verify Link Navigation
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Next Page" link
  2. Click the link
  3. Verify navigation to next page
  4. Verify page content is loaded
- **Expected Results**: Link should navigate to the correct page

#### Test Case QPH-036: Verify Download File Button
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Download Sample File" button
  2. Click the button
  3. Verify file download starts
  4. Verify file is downloaded successfully
- **Expected Results**: File should be downloaded when button is clicked

#### Test Case QPH-037: Verify Popup Open Button
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Open Popup" button
  2. Click the button
  3. Verify popup window opens
  4. Verify popup content is displayed
- **Expected Results**: Popup should open when button is clicked

### USER STORY-4.4: Info Button Interactions
*As a QA engineer, I want to test info buttons that display contextual information*

#### Test Case QPH-038: Verify Click Info Button
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the info button (i)
  2. Click the info button
  3. Verify info message is displayed
  4. Click anywhere on page to close
  5. Verify info message is closed
- **Expected Results**: Info should open on click and close when clicking elsewhere

#### Test Case QPH-039: Verify Hover Info Button
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the hover info button (i)
  2. Hover over the button
  3. Verify info message is displayed
  4. Move mouse away
  5. Verify info message is closed
- **Expected Results**: Info should show on hover and hide when mouse moves away

---

## EPIC-5: Data Table Module
*Epic for testing table functionality including sorting, pagination, and data manipulation*

### USER STORY-5.1: Table Display and Structure
*As a QA engineer, I want to test table display so that data is presented correctly*

#### Test Case QPH-040: Verify Table Data Display
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate the data table
  2. Verify table headers are displayed (ID, Name, Email, Role, Age, Actions)
  3. Verify table data is displayed
  4. Verify table structure is correct
- **Expected Results**: Table should display headers and data correctly

### USER STORY-5.2: Table Sorting
*As a QA engineer, I want to test table sorting so that users can organize data*

#### Test Case QPH-041: Verify Table Sort by Name
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate the "Sort by Name" header
  2. Click on "Sort by Name"
  3. Verify table is sorted by name in ascending order
  4. Click again to sort descending
  5. Verify table is sorted by name in descending order
- **Expected Results**: Table should sort by name correctly

#### Test Case QPH-042: Verify Table Sort by Age
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate the "Sort by Age" header
  2. Click on "Sort by Age"
  3. Verify table is sorted by age in ascending order
  4. Click again to sort descending
  5. Verify table is sorted by age in descending order
- **Expected Results**: Table should sort by age correctly

### USER STORY-5.3: Table Data Manipulation
*As a QA engineer, I want to test adding and selecting table data so that users can manage table content*

#### Test Case QPH-043: Verify Add New User to Table
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Click "Add New User" button
  2. Fill in user details form
  3. Submit the form
  4. Verify new user is added to table
  5. Verify row count increases
- **Expected Results**: New user should be added to the table
- **Test Data**: User details as per form requirements

#### Test Case QPH-044: Verify Table Row Selection
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Select a row in the table
  2. Verify row is highlighted/selected
  3. Verify selection count updates
  4. Select multiple rows
  5. Verify multiple selection works
- **Expected Results**: Table rows should be selectable

### USER STORY-5.4: Table Pagination
*As a QA engineer, I want to test table pagination so that users can navigate through large datasets*

#### Test Case QPH-045: Verify Table Pagination
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate pagination controls
  2. Click "Next Page"
  3. Verify next page data is displayed
  4. Click "Previous Page"
  5. Verify previous page data is displayed
  6. Verify page number updates
- **Expected Results**: Pagination should navigate between pages correctly

---

## EPIC-6: Dynamic Content Module
*Epic for testing dynamic content including loading states, delays, and DOM updates*

### USER STORY-6.1: Dynamic List Management
*As a QA engineer, I want to test dynamic list additions and removals so that content can be managed in real-time*

#### Test Case QPH-046: Verify Add New Item Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Locate the "Add New Item" button
  2. Click the button
  3. Verify new item is added to the list
  4. Verify item count increases
- **Expected Results**: New item should be added to the list

#### Test Case QPH-047: Verify Remove Last Item Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Add some items to the list
  2. Click "Remove Last Item" button
  3. Verify last item is removed
  4. Verify item count decreases
- **Expected Results**: Last item should be removed from the list

### USER STORY-6.2: Delayed Content Loading
*As a QA engineer, I want to test delayed content loading so that loading states are handled correctly*

#### Test Case QPH-048: Verify Load Data with Delay
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Locate the "Load Data" button
  2. Click the button
  3. Wait for 2 seconds (as indicated)
  4. Verify data is loaded after delay
  5. Verify loading state is displayed during wait
- **Expected Results**: Data should load after 2-second delay with loading indicator

#### Test Case QPH-049: Verify Custom Delay Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Enter custom delay value (e.g., 3000ms)
  2. Click "Load with Custom Delay" button
  3. Wait for the specified delay
  4. Verify data loads after custom delay
- **Expected Results**: Data should load after specified custom delay
- **Test Data**: Custom delay: 3000ms

### USER STORY-6.3: Tab Navigation
*As a QA engineer, I want to test tab navigation so that users can switch between content sections*

#### Test Case QPH-050: Verify Tab Navigation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Locate the tabs (Home, Profile, Settings)
  2. Click on "Profile" tab
  3. Verify Profile content is displayed
  4. Click on "Settings" tab
  5. Verify Settings content is displayed
  6. Click on "Home" tab
  7. Verify Home content is displayed
- **Expected Results**: Tab navigation should switch content correctly

### USER STORY-6.4: DOM Update Handling
*As a QA engineer, I want to test DOM update handling so that automation can handle element staleness*

#### Test Case QPH-051: Verify Element Staleness Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Add and remove items to trigger DOM updates
  2. Verify automation handles stale element references
  3. Verify no stale element exceptions
- **Expected Results**: Automation should handle DOM updates without errors

---

## EPIC-7: Dynamic Attributes Module
*Epic for testing elements with changing attributes and stable locator strategies*

### USER STORY-7.1: Time-Based Attribute Changes
*As a QA engineer, I want to test elements with time-based attribute changes so that automation can handle dynamic attributes*

#### Test Case QPH-052: Verify Live Clock Attributes
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the clock button
  2. Verify button text is stable
  3. Verify data-timestamp attribute updates every second
  4. Verify data-epoch attribute updates
  5. Verify aria-label attribute updates
- **Expected Results**: Attributes should update while button text remains stable

### USER STORY-7.2: Status-Based Attribute Changes
*As a QA engineer, I want to test elements with status-based attribute changes so that automation waits for correct states*

#### Test Case QPH-053: Verify Rotating Status Wait
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the rotating status badge
  2. Wait until data-status="ready"
  3. Click the badge when ready
  4. Verify click is successful
  5. Verify ready clicks count increases
- **Expected Results**: Should wait for ready state before clicking

### USER STORY-7.3: ID-Based Attribute Changes
*As a QA engineer, I want to test elements with changing IDs so that automation uses stable locators*

#### Test Case QPH-054: Verify Changing ID Trap Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the flip ID input
  2. Use stable data-testid="flip-id-input" locator
  3. Enter text in the input
  4. Verify input works despite changing id/name attributes
  5. Verify current id display changes
- **Expected Results**: Should use stable locators instead of changing IDs

### USER STORY-7.4: Countdown-Based Attribute Changes
*As a QA engineer, I want to test countdown-based attribute changes so that automation waits for completion*

#### Test Case QPH-055: Verify Countdown and Disabled Button
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the countdown button
  2. Verify button is disabled initially
  3. Wait for data-countdown to complete
  4. Verify data-ready="true" is set
  5. Verify button becomes enabled
  6. Click the button
- **Expected Results**: Button should enable after countdown completes

### USER STORY-7.5: Href-Based Attribute Changes
*As a QA engineer, I want to test elements with changing hrefs so that automation uses stable locators*

#### Test Case QPH-056: Verify Mutating Link href Handling
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the dynamic ticket link
  2. Verify link text is stable
  3. Verify href attribute changes
  4. Click by role/name instead of storing href
  5. Verify navigation works
- **Expected Results**: Should use stable locators instead of changing hrefs

---

## EPIC-8: Alerts and Modals Module
*Epic for testing JavaScript alerts, confirms, prompts, and modal dialogs*

### USER STORY-8.1: JavaScript Alert Handling
*As a QA engineer, I want to test JavaScript alerts so that users can receive important notifications*

#### Test Case QPH-057: Verify JavaScript Alert Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Show Alert" button
  2. Click the button
  3. Verify JavaScript alert appears
  4. Accept the alert
  5. Verify alert is closed
  6. Verify dialog result is displayed
- **Expected Results**: Alert should appear and be handled correctly

### USER STORY-8.2: JavaScript Confirm Handling
*As a QA engineer, I want to test JavaScript confirm dialogs so that users can make confirmations*

#### Test Case QPH-058: Verify JavaScript Confirm Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Show Confirm" button
  2. Click the button
  3. Verify confirm dialog appears
  4. Click OK
  5. Verify confirm result is displayed
  6. Repeat and click Cancel
  7. Verify cancel result is displayed
- **Expected Results**: Confirm dialog should handle both OK and Cancel

### USER STORY-8.3: JavaScript Prompt Handling
*As a QA engineer, I want to test JavaScript prompts so that users can input data*

#### Test Case QPH-059: Verify Prompt Without Validation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Prompt Without Validation" button
  2. Click the button
  3. Verify prompt appears
  4. Enter text and submit
  5. Verify prompt result is displayed
- **Expected Results**: Prompt should accept and display input

#### Test Case QPH-060: Verify Prompt With Validation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Prompt With Validation" button
  2. Click the button
  3. Verify prompt appears
  4. Enter invalid data
  5. Verify validation error
  6. Enter valid data
  7. Verify prompt result is displayed
- **Expected Results**: Prompt should validate input before accepting

### USER STORY-8.4: Toast Notifications
*As a QA engineer, I want to test toast notifications so that users receive non-intrusive notifications*

#### Test Case QPH-061: Verify Toast Notification
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Show Toast" button
  2. Click the button
  3. Verify toast notification appears
  4. Verify toast auto-dismisses after timeout
  5. Verify toast message content
- **Expected Results**: Toast should appear and auto-dismiss

---

## EPIC-9: Advanced UI Elements Module
*Epic for testing advanced UI elements like file upload, drag-drop, and Shadow DOM*

### USER STORY-9.1: File Upload Functionality
*As a QA engineer, I want to test file upload so that users can upload files*

#### Test Case QPH-062: Verify Single File Upload
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the single file upload input
  2. Select a file for upload
  3. Verify file name is displayed
  4. Verify file is selected
  5. Click Clear button
  6. Verify file selection is cleared
- **Expected Results**: Single file should be uploaded and cleared correctly
- **Test Data**: Sample file for upload

#### Test Case QPH-063: Verify Multiple File Upload
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the multiple file upload input
  2. Select multiple files for upload
  3. Verify multiple file names are displayed
  4. Verify files are selected
  5. Click Clear button
  6. Verify all file selections are cleared
- **Expected Results**: Multiple files should be uploaded and cleared
- **Test Data**: Sample files for upload

### USER STORY-9.2: Drag and Drop Functionality
*As a QA engineer, I want to test drag and drop so that users can interact with drag-drop interfaces*

#### Test Case QPH-064: Verify Drag and Drop Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the drag and drop zone
  2. Drag a file into the zone
  3. Verify file is accepted
  4. Verify file is displayed in drop zone
  5. Click Clear button
  6. Verify drop zone is cleared
- **Expected Results**: Drag and drop should work correctly
- **Test Data**: Sample file for drag and drop

### USER STORY-9.3: Shadow DOM Interaction
*As a QA engineer, I want to test Shadow DOM elements so that automation can interact with encapsulated components*

#### Test Case QPH-065: Verify Shadow DOM Element Interaction
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the Shadow DOM element
  2. Access the shadow DOM
  3. Interact with elements inside shadow DOM
  4. Verify interactions work correctly
- **Expected Results**: Should be able to interact with Shadow DOM elements

### USER STORY-9.4: Progress Bar Functionality
*As a QA engineer, I want to test progress bars so that users can see progress indicators*

#### Test Case QPH-066: Verify Progress Bar Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the progress bar
  2. Verify initial progress (30%)
  3. Click "-10%" button
  4. Verify progress decreases
  5. Click "+10%" button
  6. Verify progress increases
- **Expected Results**: Progress bar should update correctly

---

## EPIC-10: E-Commerce Module
*Epic for testing e-commerce functionality including search, filters, cart, and checkout*

### USER STORY-10.1: Product Search and Filtering
*As a QA engineer, I want to test product search and filtering so that users can find products*

#### Test Case QPH-067: Verify Product Search
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Locate the search input
  2. Enter search term (e.g., "phone")
  3. Verify search results are displayed
  4. Verify results match search term
- **Expected Results**: Search should return matching products
- **Test Data**: Search term: "phone"

#### Test Case QPH-068: Verify Category Filter
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Locate the category dropdown
  2. Select "Electronics"
  3. Verify only electronics products are shown
  4. Select "Accessories"
  5. Verify only accessories are shown
  6. Select "All"
  7. Verify all products are shown
- **Expected Results**: Category filter should work correctly

#### Test Case QPH-069: Verify Clear Filters
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Apply search and category filters
  2. Click "Clear Filters" button
  3. Verify all filters are cleared
  4. Verify all products are shown
- **Expected Results**: Filters should be cleared completely

### USER STORY-10.2: Shopping Cart Functionality
*As a QA engineer, I want to test shopping cart so that users can manage their purchases*

#### Test Case QPH-070: Verify Add to Cart
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Search for a product
  2. Click "Add to Cart" on a product
  3. Verify cart count increases
  4. Verify product is added to cart
  5. Verify cart total updates
- **Expected Results**: Product should be added to cart successfully

#### Test Case QPH-071: Verify Cart Display
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section, Add items to cart
- **Test Steps**:
  1. Click on cart icon/count
  2. Verify cart modal/section opens
  3. Verify added products are displayed
  4. Verify quantities are correct
  5. Verify total is calculated correctly
- **Expected Results**: Cart should display all added items with correct totals

#### Test Case QPH-072: Verify Clear Cart
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section, Add items to cart
- **Test Steps**:
  1. Open cart
  2. Click "Clear Cart" button
  3. Verify all items are removed
  4. Verify cart count returns to 0
  5. Verify total returns to $0.00
- **Expected Results**: Cart should be cleared completely

### USER STORY-10.3: Checkout Process
*As a QA engineer, I want to test checkout process so that users can complete purchases*

#### Test Case QPH-073: Verify Checkout Process
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section, Add items to cart
- **Test Steps**:
  1. Open cart with items
  2. Click "Buy / Checkout" button
  3. Verify checkout process initiates
  4. Fill in checkout details if required
  5. Complete checkout
  6. Verify order confirmation
- **Expected Results**: Checkout process should complete successfully

---

## EPIC-11: Advanced Practice Lab Module
*Epic for testing advanced automation scenarios including wizards, custom components, and edge cases*

### USER STORY-11.1: Multi-Step Wizard Forms
*As a QA engineer, I want to test multi-step wizards so that users can complete complex forms*

#### Test Case QPH-074: Verify Multi-Step Wizard Navigation
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the multi-step wizard
  2. Verify step 1 (Personal) is active
  3. Fill in personal details
  4. Click "Next" button
  5. Verify step 2 (Address) is active
  6. Fill in address details
  7. Click "Next" button
  8. Verify step 3 (Review) is active
  9. Verify all details are displayed for review
- **Expected Results**: Wizard should navigate through steps correctly
- **Test Data**: Personal details, Address details

#### Test Case QPH-075: Verify Wizard Back Navigation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Navigate to step 2 or 3
- **Test Steps**:
  1. Navigate to step 2 or 3 in wizard
  2. Click "Back" button
  3. Verify previous step is displayed
  4. Verify data is preserved
- **Expected Results**: Back navigation should work and preserve data

#### Test Case QPH-076: Verify Wizard Submission
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Complete all wizard steps
- **Test Steps**:
  1. Complete all wizard steps with valid data
  2. Navigate to Review step
  3. Click "Submit" button
  4. Verify successful submission
  5. Verify confirmation message
- **Expected Results**: Wizard should submit successfully with all data

### USER STORY-11.2: Custom Date Picker
*As a QA engineer, I want to test custom date pickers so that users can select dates from custom calendars*

#### Test Case QPH-077: Verify Custom Date Picker
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the custom date picker
  2. Click "Select Date"
  3. Verify calendar popup opens
  4. Navigate to specific month/year
  5. Select a specific date
  6. Verify date is displayed in input
  7. Verify date format is correct
- **Expected Results**: Custom date picker should select and display dates correctly

### USER STORY-11.3: Stale Element Handling
*As a QA engineer, I want to test stale element scenarios so that automation can handle DOM replacement*

#### Test Case QPH-078: Verify Stale Element Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the stale element demo button
  2. Click the button
  3. Verify button is replaced in DOM
  4. Click the new button
  5. Verify automation handles stale element reference
  6. Verify click count increases
- **Expected Results**: Should handle DOM replacement without stale element errors

### USER STORY-11.4: Popup Management
*As a QA engineer, I want to test popup management so that users can interact with modal dialogs*

#### Test Case QPH-079: Verify Practice Popup Close
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Open practice popup
- **Test Steps**:
  1. Verify practice popup is open
  2. Locate the X button in top right
  3. Click the X button
  4. Verify popup closes
- **Expected Results**: Popup should close when X is clicked

### USER STORY-11.5: Password Recovery
*As a QA engineer, I want to test password recovery so that users can reset their passwords*

#### Test Case QPH-080: Verify Forgot Password Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the forgot password form
  2. Enter username "testuser"
  3. Click Submit button
  4. Verify password is retrieved/displayed
  5. Verify password matches expected format
- **Expected Results**: Password should be retrieved for valid username
- **Test Data**: Username: "testuser"

### USER STORY-11.6: User Management Forms
*As a QA engineer, I want to test user management forms so that multiple users can be added efficiently*

#### Test Case QPH-081: Verify Add New User Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the Add New User form
  2. Fill in User 1 details (ID, Name, Email, Role, Age)
  3. Click "Add Another" button
  4. Verify User 2 form appears
  5. Fill in User 2 details
  6. Click "Save Users" button
  7. Verify users are saved successfully
- **Expected Results**: Multiple users should be added and saved
- **Test Data**: User details for multiple users

#### Test Case QPH-082: Verify Remove User Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Add multiple users
- **Test Steps**:
  1. Add multiple users to the form
  2. Click "Remove" button for a specific user
  3. Verify user is removed from form
  4. Verify other users remain
- **Expected Results**: Individual users should be removable

#### Test Case QPH-083: Verify Cancel User Addition
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Fill in user details
  2. Click "Cancel" button
  3. Verify form is cleared/closed
  4. Verify users are not saved
- **Expected Results**: Cancel should clear form without saving

---

## EPIC-12: Cookie Consent Module
*Epic for testing cookie consent banner and user privacy controls*

### USER STORY-12.1: Cookie Consent Banner
*As a QA engineer, I want to test cookie consent banner so that privacy compliance is maintained*

#### Test Case QPH-084: Verify Cookie Consent Banner
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/ for first time
- **Test Steps**:
  1. Verify cookie consent banner is displayed
  2. Verify banner blocks page interaction
  3. Click "Accept" button
  4. Verify banner closes
  5. Verify page interaction is enabled
- **Expected Results**: Cookie banner should appear and handle accept action

#### Test Case QPH-085: Verify Cookie Decline
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/ for first time
- **Test Steps**:
  1. Verify cookie consent banner is displayed
  2. Click "Decline" button
  3. Verify banner closes
  4. Verify page interaction is enabled
- **Expected Results**: Cookie banner should handle decline action

---

## Summary

### Epic Overview:
- **12 Epics** covering major functional areas
- **35 User Stories** describing specific features
- **85 Test Cases** with detailed steps and expected results

### Epic Breakdown:
1. **Text Input Module** - 9 User Stories, 9 Test Cases
2. **Authentication Module** - 3 User Stories, 10 Test Cases
3. **Form Selection Controls Module** - 5 User Stories, 9 Test Cases
4. **Button and Link Interaction Module** - 4 User Stories, 11 Test Cases
5. **Data Table Module** - 4 User Stories, 6 Test Cases
6. **Dynamic Content Module** - 4 User Stories, 6 Test Cases
7. **Dynamic Attributes Module** - 5 User Stories, 5 Test Cases
8. **Alerts and Modals Module** - 4 User Stories, 5 Test Cases
9. **Advanced UI Elements Module** - 4 User Stories, 5 Test Cases
10. **E-Commerce Module** - 3 User Stories, 7 Test Cases
11. **Advanced Practice Lab Module** - 6 User Stories, 10 Test Cases
12. **Cookie Consent Module** - 1 User Story, 2 Test Cases

### Priority Distribution:
- **High Priority**: 45 Test Cases
- **Medium Priority**: 32 Test Cases
- **Low Priority**: 8 Test Cases

### Jira Import Format:
This structure is designed for direct import into Jira with:
- **Epic** = Epic-level items
- **User Story** = Story-level items with acceptance criteria
- **Test Case** = Detailed test scenarios with steps and expected results

**Note**: All test cases include specific pre-requisites, test data, and expected results to facilitate Jira import and test execution using Selenium, Playwright, or Cypress automation frameworks.

### QPH-001: Verify Text Input Field Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the text input field
  2. Enter text "Hello World"
  3. Verify the text is entered correctly
  4. Clear the text field
  5. Verify the field is empty
- **Expected Results**: Text should be entered and cleared successfully
- **Test Data**: "Hello World"

### QPH-002: Verify Email Input Validation
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the email input field
  2. Enter valid email "test@example.com"
  3. Verify the email is accepted
  4. Enter invalid email "invalid-email"
  5. Verify validation error if present
- **Expected Results**: Valid email should be accepted, invalid email should show validation

### QPH-003: Verify Password Input Masking
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the password input field
  2. Enter "password123"
  3. Verify the password is masked (shows dots/asterisks)
  4. Verify the password value is stored correctly
- **Expected Results**: Password should be masked in display but stored correctly

### QPH-004: Verify Number Input Constraints
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the number input field
  2. Enter valid number "123"
  3. Verify the number is accepted
  4. Enter invalid text "abc"
  5. Verify the input is rejected or ignored
- **Expected Results**: Only numbers should be accepted

### QPH-005: Verify Date Input Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the date input field
  2. Click on the date picker
  3. Select a date (e.g., 2026-09-25)
  4. Verify the date is displayed correctly
- **Expected Results**: Date should be selected and displayed in correct format

### QPH-006: Verify Phone Input with Country Code
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the phone input field with India (+91) country code
  2. Enter 10-digit phone number "9876543210"
  3. Verify the phone number is accepted
  4. Enter less than 10 digits
  5. Verify validation error if present
- **Expected Results**: Valid 10-digit number should be accepted

### QPH-007: Verify Textarea Multi-line Input
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the textarea field
  2. Enter multi-line text
  3. Verify the text is displayed with line breaks
  4. Clear the textarea
- **Expected Results**: Multi-line text should be displayed correctly

### QPH-008: Verify Read-only Input Behavior
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the read-only input field
  2. Attempt to enter text
  3. Verify the field cannot be modified
  4. Verify the existing value is displayed
- **Expected Results**: Read-only field should not accept input

### QPH-009: Verify Disabled Input Behavior
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Inputs section
- **Test Steps**:
  1. Locate the disabled input field
  2. Attempt to click or enter text
  3. Verify the field is not interactive
  4. Verify the disabled attribute is present
- **Expected Results**: Disabled field should not be interactive

---

## 2. Login & Registration Forms Test Cases

### QPH-010: Verify Successful Login
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the login form
  2. Enter username "tester"
  3. Enter password "password123"
  4. Click Login button
  5. Verify successful login
- **Expected Results**: Login should be successful with valid credentials
- **Test Data**: Username: "tester", Password: "password123"

### QPH-011: Verify Failed Login with Invalid Credentials
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the login form
  2. Enter invalid username "invalid"
  3. Enter invalid password "wrong"
  4. Click Login button
  5. Verify error message is displayed
- **Expected Results**: Login should fail with appropriate error message
- **Test Data**: Username: "invalid", Password: "wrong"

### QPH-012: Verify Login with Empty Fields
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the login form
  2. Leave username field empty
  3. Leave password field empty
  4. Click Login button
  5. Verify validation error is displayed
- **Expected Results**: Form validation should prevent empty submission

### QPH-013: Verify Registration Form Submission
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the registration form
  2. Enter First Name "John"
  3. Enter Last Name "Doe"
  4. Enter Email "john.doe@example.com"
  5. Select Country "United States"
  6. Click Register button
  7. Verify successful registration
- **Expected Results**: Registration should be successful with valid data
- **Test Data**: First Name: "John", Last Name: "Doe", Email: "john.doe@example.com", Country: "United States"

### QPH-014: Verify Registration Form Clear Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Fill in all registration form fields
  2. Click Clear button
  3. Verify all fields are cleared
- **Expected Results**: All form fields should be cleared

### QPH-015: Verify Country Dropdown Selection
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Forms section
- **Test Steps**:
  1. Locate the country dropdown in registration form
  2. Select "India"
  3. Verify the selection is displayed
  4. Select "United Kingdom"
  5. Verify the selection changes
- **Expected Results**: Country selection should work correctly

---

## 3. OTP Login Test Cases

### QPH-016: Verify OTP Fetch Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section
- **Test Steps**:
  1. Locate the OTP login form
  2. Enter username "testuser"
  3. Click "Fetch OTP" button
  4. Wait for OTP to be generated/displayed
  5. Verify OTP is received
- **Expected Results**: OTP should be generated and displayed
- **Test Data**: Username: "testuser"

### QPH-017: Verify OTP Login with Valid OTP
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section, Fetch OTP first
- **Test Steps**:
  1. Enter username "testuser"
  2. Fetch OTP
  3. Enter the received 6-digit OTP
  4. Click Submit button
  5. Verify successful login
- **Expected Results**: Login should be successful with valid OTP
- **Test Data**: Username: "testuser", OTP: (as received)

### QPH-018: Verify OTP Resend Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section
- **Test Steps**:
  1. Enter username "testuser"
  2. Click "Fetch OTP" button
  3. Wait for 30 seconds (or until fetch tab closes)
  4. Click "Resend OTP" button
  5. Verify new OTP is generated
- **Expected Results**: New OTP should be generated on resend
- **Test Data**: Username: "testuser"

### QPH-019: Verify OTP Login with Invalid OTP
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to OTP Login section
- **Test Steps**:
  1. Enter username "testuser"
  2. Enter invalid 6-digit OTP "000000"
  3. Click Submit button
  4. Verify error message is displayed
- **Expected Results**: Login should fail with invalid OTP
- **Test Data**: Username: "testuser", Invalid OTP: "000000"

---

## 4. Radio Buttons, Checkboxes & Dropdowns Test Cases

### QPH-020: Verify Radio Button Selection
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the Gender radio buttons
  2. Select "Male"
  3. Verify "Male" is selected
  4. Select "Female"
  5. Verify "Female" is selected and "Male" is deselected
- **Expected Results**: Only one radio button should be selected at a time

### QPH-021: Verify Checkbox Selection
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the Skills checkboxes
  2. Select "Selenium"
  3. Select "Playwright"
  4. Verify both are selected
  5. Deselect "Selenium"
  6. Verify only "Playwright" is selected
- **Expected Results**: Multiple checkboxes can be selected/deselected independently

### QPH-022: Verify Single Select Dropdown
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the country dropdown
  2. Click to open dropdown
  3. Select "India"
  4. Verify "India" is displayed as selected
  5. Change selection to "Canada"
  6. Verify "Canada" is now selected
- **Expected Results**: Single selection should work correctly

### QPH-023: Verify Multi Select Listbox
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the multi-select listbox
  2. Select "JavaScript"
  3. Select "Python"
  4. Verify both are selected
  5. Deselect "JavaScript"
  6. Verify only "Python" remains selected
- **Expected Results**: Multiple items can be selected in listbox

### QPH-024: Verify Multi Select Dropdown
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the multi-select dropdown
  2. Select "Selenium"
  3. Select "Cypress"
  4. Verify both are selected
  5. Verify selection display
- **Expected Results**: Multiple items can be selected in dropdown

### QPH-025: Verify Range Slider Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the experience range slider
  2. Move slider to different positions
  3. Verify the value changes (e.g., "5 years")
  4. Set slider to minimum value
  5. Set slider to maximum value
- **Expected Results**: Slider should move and display correct values

### QPH-026: Verify Color Picker
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the color picker
  2. Click to open color palette
  3. Select a color (e.g., red)
  4. Verify the color code is displayed
  5. Verify the color is applied
- **Expected Results**: Color picker should select and display color codes

### QPH-027: Verify Toggle Switch
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the "Enable Notifications" toggle
  2. Click to turn ON
  3. Verify state changes to "On"
  4. Click to turn OFF
  5. Verify state changes to "Off"
- **Expected Results**: Toggle should switch between On/Off states

### QPH-028: Verify Auto-complete Input
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Selection section
- **Test Steps**:
  1. Locate the auto-complete input
  2. Start typing (e.g., "test")
  3. Verify suggestions appear
  4. Select a suggestion from the list
  5. Verify the selected value is populated
- **Expected Results**: Auto-complete should show suggestions and allow selection

---

## 5. Buttons & Links Test Cases

### QPH-029: Verify Primary Button Click
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the Primary button
  2. Click the button
  3. Verify button click action is performed
  4. Verify output is displayed
- **Expected Results**: Primary button should respond to click

### QPH-030: Verify Secondary Button Click
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the Secondary button
  2. Click the button
  3. Verify button click action is performed
- **Expected Results**: Secondary button should respond to click

### QPH-031: Verify Disabled Button Behavior
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the Disabled button
  2. Attempt to click the button
  3. Verify button does not respond
  4. Verify disabled attribute is present
- **Expected Results**: Disabled button should not be clickable

### QPH-032: Verify Double Click Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Double Click Me" button
  2. Perform double click action
  3. Verify double click action is performed
  4. Verify appropriate output
- **Expected Results**: Double click should trigger the intended action

### QPH-033: Verify Right Click Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Right Click Me" button
  2. Perform right click action
  3. Verify right click action is performed
  4. Verify appropriate output
- **Expected Results**: Right click should trigger the intended action

### QPH-034: Verify Hover Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Hover Over Me" button
  2. Move mouse over the button
  3. Verify hover effect/action is triggered
  4. Move mouse away
  5. Verify hover effect is removed
- **Expected Results**: Hover should trigger and remove effect appropriately

### QPH-035: Verify Link Navigation
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Next Page" link
  2. Click the link
  3. Verify navigation to next page
  4. Verify page content is loaded
- **Expected Results**: Link should navigate to the correct page

### QPH-036: Verify Download File Button
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Download Sample File" button
  2. Click the button
  3. Verify file download starts
  4. Verify file is downloaded successfully
- **Expected Results**: File should be downloaded when button is clicked

### QPH-037: Verify Popup Open Button
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the "Open Popup" button
  2. Click the button
  3. Verify popup window opens
  4. Verify popup content is displayed
- **Expected Results**: Popup should open when button is clicked

### QPH-038: Verify Click Info Button
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the info button (i)
  2. Click the info button
  3. Verify info message is displayed
  4. Click anywhere on page to close
  5. Verify info message is closed
- **Expected Results**: Info should open on click and close when clicking elsewhere

### QPH-039: Verify Hover Info Button
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Buttons section
- **Test Steps**:
  1. Locate the hover info button (i)
  2. Hover over the button
  3. Verify info message is displayed
  4. Move mouse away
  5. Verify info message is closed
- **Expected Results**: Info should show on hover and hide when mouse moves away

---

## 6. Tables Test Cases

### QPH-040: Verify Table Data Display
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate the data table
  2. Verify table headers are displayed (ID, Name, Email, Role, Age, Actions)
  3. Verify table data is displayed
  4. Verify table structure is correct
- **Expected Results**: Table should display headers and data correctly

### QPH-041: Verify Table Sort by Name
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate the "Sort by Name" header
  2. Click on "Sort by Name"
  3. Verify table is sorted by name in ascending order
  4. Click again to sort descending
  5. Verify table is sorted by name in descending order
- **Expected Results**: Table should sort by name correctly

### QPH-042: Verify Table Sort by Age
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate the "Sort by Age" header
  2. Click on "Sort by Age"
  3. Verify table is sorted by age in ascending order
  4. Click again to sort descending
  5. Verify table is sorted by age in descending order
- **Expected Results**: Table should sort by age correctly

### QPH-043: Verify Add New User to Table
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Click "Add New User" button
  2. Fill in user details form
  3. Submit the form
  4. Verify new user is added to table
  5. Verify row count increases
- **Expected Results**: New user should be added to the table
- **Test Data**: User details as per form requirements

### QPH-044: Verify Table Row Selection
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Select a row in the table
  2. Verify row is highlighted/selected
  3. Verify selection count updates
  4. Select multiple rows
  5. Verify multiple selection works
- **Expected Results**: Table rows should be selectable

### QPH-045: Verify Table Pagination
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Tables section
- **Test Steps**:
  1. Locate pagination controls
  2. Click "Next Page"
  3. Verify next page data is displayed
  4. Click "Previous Page"
  5. Verify previous page data is displayed
  6. Verify page number updates
- **Expected Results**: Pagination should navigate between pages correctly

---

## 7. Dynamic Content Test Cases

### QPH-046: Verify Add New Item Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Locate the "Add New Item" button
  2. Click the button
  3. Verify new item is added to the list
  4. Verify item count increases
- **Expected Results**: New item should be added to the list

### QPH-047: Verify Remove Last Item Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Add some items to the list
  2. Click "Remove Last Item" button
  3. Verify last item is removed
  4. Verify item count decreases
- **Expected Results**: Last item should be removed from the list

### QPH-048: Verify Load Data with Delay
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Locate the "Load Data" button
  2. Click the button
  3. Wait for 2 seconds (as indicated)
  4. Verify data is loaded after delay
  5. Verify loading state is displayed during wait
- **Expected Results**: Data should load after 2-second delay with loading indicator

### QPH-049: Verify Custom Delay Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Enter custom delay value (e.g., 3000ms)
  2. Click "Load with Custom Delay" button
  3. Wait for the specified delay
  4. Verify data loads after custom delay
- **Expected Results**: Data should load after specified custom delay
- **Test Data**: Custom delay: 3000ms

### QPH-050: Verify Tab Navigation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Locate the tabs (Home, Profile, Settings)
  2. Click on "Profile" tab
  3. Verify Profile content is displayed
  4. Click on "Settings" tab
  5. Verify Settings content is displayed
  6. Click on "Home" tab
  7. Verify Home content is displayed
- **Expected Results**: Tab navigation should switch content correctly

### QPH-051: Verify Element Staleness Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Dynamic section
- **Test Steps**:
  1. Add and remove items to trigger DOM updates
  2. Verify automation handles stale element references
  3. Verify no stale element exceptions
- **Expected Results**: Automation should handle DOM updates without errors

---

## 8. Changing Attributes Test Cases

### QPH-052: Verify Live Clock Attributes
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the clock button
  2. Verify button text is stable
  3. Verify data-timestamp attribute updates every second
  4. Verify data-epoch attribute updates
  5. Verify aria-label attribute updates
- **Expected Results**: Attributes should update while button text remains stable

### QPH-053: Verify Rotating Status Wait
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the rotating status badge
  2. Wait until data-status="ready"
  3. Click the badge when ready
  4. Verify click is successful
  5. Verify ready clicks count increases
- **Expected Results**: Should wait for ready state before clicking

### QPH-054: Verify Changing ID Trap Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the flip ID input
  2. Use stable data-testid="flip-id-input" locator
  3. Enter text in the input
  4. Verify input works despite changing id/name attributes
  5. Verify current id display changes
- **Expected Results**: Should use stable locators instead of changing IDs

### QPH-055: Verify Countdown and Disabled Button
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the countdown button
  2. Verify button is disabled initially
  3. Wait for data-countdown to complete
  4. Verify data-ready="true" is set
  5. Verify button becomes enabled
  6. Click the button
- **Expected Results**: Button should enable after countdown completes

### QPH-056: Verify Mutating Link href Handling
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Changing Attributes section
- **Test Steps**:
  1. Locate the dynamic ticket link
  2. Verify link text is stable
  3. Verify href attribute changes
  4. Click by role/name instead of storing href
  5. Verify navigation works
- **Expected Results**: Should use stable locators instead of changing hrefs

---

## 9. Alerts, Modals & Popups Test Cases

### QPH-057: Verify JavaScript Alert Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Show Alert" button
  2. Click the button
  3. Verify JavaScript alert appears
  4. Accept the alert
  5. Verify alert is closed
  6. Verify dialog result is displayed
- **Expected Results**: Alert should appear and be handled correctly

### QPH-058: Verify JavaScript Confirm Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Show Confirm" button
  2. Click the button
  3. Verify confirm dialog appears
  4. Click OK
  5. Verify confirm result is displayed
  6. Repeat and click Cancel
  7. Verify cancel result is displayed
- **Expected Results**: Confirm dialog should handle both OK and Cancel

### QPH-059: Verify Prompt Without Validation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Prompt Without Validation" button
  2. Click the button
  3. Verify prompt appears
  4. Enter text and submit
  5. Verify prompt result is displayed
- **Expected Results**: Prompt should accept and display input

### QPH-060: Verify Prompt With Validation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Prompt With Validation" button
  2. Click the button
  3. Verify prompt appears
  4. Enter invalid data
  5. Verify validation error
  6. Enter valid data
  7. Verify prompt result is displayed
- **Expected Results**: Prompt should validate input before accepting

### QPH-061: Verify Toast Notification
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Alerts section
- **Test Steps**:
  1. Locate the "Show Toast" button
  2. Click the button
  3. Verify toast notification appears
  4. Verify toast auto-dismisses after timeout
  5. Verify toast message content
- **Expected Results**: Toast should appear and auto-dismiss

---

## 10. Advanced Elements Test Cases

### QPH-062: Verify Single File Upload
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the single file upload input
  2. Select a file for upload
  3. Verify file name is displayed
  4. Verify file is selected
  5. Click Clear button
  6. Verify file selection is cleared
- **Expected Results**: Single file should be uploaded and cleared correctly
- **Test Data**: Sample file for upload

### QPH-063: Verify Multiple File Upload
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the multiple file upload input
  2. Select multiple files for upload
  3. Verify multiple file names are displayed
  4. Verify files are selected
  5. Click Clear button
  6. Verify all file selections are cleared
- **Expected Results**: Multiple files should be uploaded and cleared
- **Test Data**: Sample files for upload

### QPH-064: Verify Drag and Drop Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the drag and drop zone
  2. Drag a file into the zone
  3. Verify file is accepted
  4. Verify file is displayed in drop zone
  5. Click Clear button
  6. Verify drop zone is cleared
- **Expected Results**: Drag and drop should work correctly
- **Test Data**: Sample file for drag and drop

### QPH-065: Verify Shadow DOM Element Interaction
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the Shadow DOM element
  2. Access the shadow DOM
  3. Interact with elements inside shadow DOM
  4. Verify interactions work correctly
- **Expected Results**: Should be able to interact with Shadow DOM elements

### QPH-066: Verify Progress Bar Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Advanced section
- **Test Steps**:
  1. Locate the progress bar
  2. Verify initial progress (30%)
  3. Click "-10%" button
  4. Verify progress decreases
  5. Click "+10%" button
  6. Verify progress increases
- **Expected Results**: Progress bar should update correctly

---

## 11. Online Shopping Test Cases

### QPH-067: Verify Product Search
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Locate the search input
  2. Enter search term (e.g., "phone")
  3. Verify search results are displayed
  4. Verify results match search term
- **Expected Results**: Search should return matching products
- **Test Data**: Search term: "phone"

### QPH-068: Verify Category Filter
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Locate the category dropdown
  2. Select "Electronics"
  3. Verify only electronics products are shown
  4. Select "Accessories"
  5. Verify only accessories are shown
  6. Select "All"
  7. Verify all products are shown
- **Expected Results**: Category filter should work correctly

### QPH-069: Verify Clear Filters
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Apply search and category filters
  2. Click "Clear Filters" button
  3. Verify all filters are cleared
  4. Verify all products are shown
- **Expected Results**: Filters should be cleared completely

### QPH-070: Verify Add to Cart
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section
- **Test Steps**:
  1. Search for a product
  2. Click "Add to Cart" on a product
  3. Verify cart count increases
  4. Verify product is added to cart
  5. Verify cart total updates
- **Expected Results**: Product should be added to cart successfully

### QPH-071: Verify Cart Display
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section, Add items to cart
- **Test Steps**:
  1. Click on cart icon/count
  2. Verify cart modal/section opens
  3. Verify added products are displayed
  4. Verify quantities are correct
  5. Verify total is calculated correctly
- **Expected Results**: Cart should display all added items with correct totals

### QPH-072: Verify Clear Cart
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section, Add items to cart
- **Test Steps**:
  1. Open cart
  2. Click "Clear Cart" button
  3. Verify all items are removed
  4. Verify cart count returns to 0
  5. Verify total returns to $0.00
- **Expected Results**: Cart should be cleared completely

### QPH-073: Verify Checkout Process
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Shopping section, Add items to cart
- **Test Steps**:
  1. Open cart with items
  2. Click "Buy / Checkout" button
  3. Verify checkout process initiates
  4. Fill in checkout details if required
  5. Complete checkout
  6. Verify order confirmation
- **Expected Results**: Checkout process should complete successfully

---

## 12. Automation Practice Lab Test Cases

### QPH-074: Verify Multi-Step Wizard Navigation
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the multi-step wizard
  2. Verify step 1 (Personal) is active
  3. Fill in personal details
  4. Click "Next" button
  5. Verify step 2 (Address) is active
  6. Fill in address details
  7. Click "Next" button
  8. Verify step 3 (Review) is active
  9. Verify all details are displayed for review
- **Expected Results**: Wizard should navigate through steps correctly
- **Test Data**: Personal details, Address details

### QPH-075: Verify Wizard Back Navigation
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Navigate to step 2 or 3
- **Test Steps**:
  1. Navigate to step 2 or 3 in wizard
  2. Click "Back" button
  3. Verify previous step is displayed
  4. Verify data is preserved
- **Expected Results**: Back navigation should work and preserve data

### QPH-076: Verify Wizard Submission
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Complete all wizard steps
- **Test Steps**:
  1. Complete all wizard steps with valid data
  2. Navigate to Review step
  3. Click "Submit" button
  4. Verify successful submission
  5. Verify confirmation message
- **Expected Results**: Wizard should submit successfully with all data

### QPH-077: Verify Custom Date Picker
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the custom date picker
  2. Click "Select Date"
  3. Verify calendar popup opens
  4. Navigate to specific month/year
  5. Select a specific date
  6. Verify date is displayed in input
  7. Verify date format is correct
- **Expected Results**: Custom date picker should select and display dates correctly

### QPH-078: Verify Stale Element Handling
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the stale element demo button
  2. Click the button
  3. Verify button is replaced in DOM
  4. Click the new button
  5. Verify automation handles stale element reference
  6. Verify click count increases
- **Expected Results**: Should handle DOM replacement without stale element errors

### QPH-079: Verify Practice Popup Close
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Open practice popup
- **Test Steps**:
  1. Verify practice popup is open
  2. Locate the X button in top right
  3. Click the X button
  4. Verify popup closes
- **Expected Results**: Popup should close when X is clicked

### QPH-080: Verify Forgot Password Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the forgot password form
  2. Enter username "testuser"
  3. Click Submit button
  4. Verify password is retrieved/displayed
  5. Verify password matches expected format
- **Expected Results**: Password should be retrieved for valid username
- **Test Data**: Username: "testuser"

### QPH-081: Verify Add New User Functionality
- **Priority**: High
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Locate the Add New User form
  2. Fill in User 1 details (ID, Name, Email, Role, Age)
  3. Click "Add Another" button
  4. Verify User 2 form appears
  5. Fill in User 2 details
  6. Click "Save Users" button
  7. Verify users are saved successfully
- **Expected Results**: Multiple users should be added and saved
- **Test Data**: User details for multiple users

### QPH-082: Verify Remove User Functionality
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section, Add multiple users
- **Test Steps**:
  1. Add multiple users to the form
  2. Click "Remove" button for a specific user
  3. Verify user is removed from form
  4. Verify other users remain
- **Expected Results**: Individual users should be removable

### QPH-083: Verify Cancel User Addition
- **Priority**: Low
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/, Navigate to Practice Lab section
- **Test Steps**:
  1. Fill in user details
  2. Click "Cancel" button
  3. Verify form is cleared/closed
  4. Verify users are not saved
- **Expected Results**: Cancel should clear form without saving

---

## Cookie Consent Test Cases

### QPH-084: Verify Cookie Consent Banner
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/ for first time
- **Test Steps**:
  1. Verify cookie consent banner is displayed
  2. Verify banner blocks page interaction
  3. Click "Accept" button
  4. Verify banner closes
  5. Verify page interaction is enabled
- **Expected Results**: Cookie banner should appear and handle accept action

### QPH-085: Verify Cookie Decline
- **Priority**: Medium
- **Test Type**: Functional
- **Pre-requisites**: Navigate to https://qapracticehub.com/ for first time
- **Test Steps**:
  1. Verify cookie consent banner is displayed
  2. Click "Decline" button
  3. Verify banner closes
  4. Verify page interaction is enabled
- **Expected Results**: Cookie banner should handle decline action

---

## Summary

**Total Test Cases**: 85
**High Priority**: 45
**Medium Priority**: 32
**Low Priority**: 8

**Test Categories**:
- Text Inputs: 9 test cases
- Login & Registration: 6 test cases
- OTP Login: 4 test cases
- Radio Buttons/Checkboxes/Dropdowns: 9 test cases
- Buttons & Links: 11 test cases
- Tables: 6 test cases
- Dynamic Content: 6 test cases
- Changing Attributes: 5 test cases
- Alerts/Modals/Popups: 5 test cases
- Advanced Elements: 5 test cases
- Online Shopping: 7 test cases
- Practice Lab: 10 test cases
- Cookie Consent: 2 test cases

**Note**: These test cases are designed for automation practice using Selenium, Playwright, or Cypress. Each test case includes specific pre-requisites, test data, and expected results to facilitate Jira import and test execution.