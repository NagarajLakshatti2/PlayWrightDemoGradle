Folder structure to create:
src/main/java/config/ConfigReader.java
src/main/java/utils/PlaywrightManager.java
src/main/java/pages/BasePage.java
src/main/java/pages/LoginPage.java
src/test/java/runners/TestRunner.java
src/test/java/stepdefinitions/Hooks.java
src/test/java/stepdefinitions/LoginSteps.java
src/test/java/tests/api/UserApiTest.java
src/test/resources/config.properties
src/test/resources/features/login.feature
testng.xml

mvn clean install -DskipTests
mvn test

.\gradlew.bat test
.\gradlew.bat --stop


src/main/java/
  config/
    ConfigReader.java                 (shared across all platforms)
  utils/
    PlaywrightManager.java            (web-specific, but lives in utils since it's infrastructure)
    AppiumManager.java                (mobile-specific driver lifecycle)
  api/
    client/
      ApiClient.java                  (RestAssured/Playwright API wrapper)
    payloads/
      UserPayload.java                (request/response POJOs)
  web/
    pages/
      BasePage.java
      LoginPage.java
      DashboardPage.java
  mobile/
    screens/
      BaseScreen.java
      LoginScreen.java
      HomeScreen.java

src/test/java/
  tests/
    api/
      UserApiTest.java
      OrderApiTest.java
    web/
      (empty if fully Cucumber-driven — see runners/ and stepdefinitions/web/)
    mobile/
      (same — empty if Cucumber-driven)
  stepdefinitions/
    web/
      LoginSteps.java
      CheckoutSteps.java
    mobile/
      LoginSteps.java
      NavigationSteps.java
    hooks/
      WebHooks.java
      MobileHooks.java
  runners/
    WebTestRunner.java
    MobileTestRunner.java

src/test/resources/
  features/
    web/
      login.feature
      checkout.feature
    mobile/
      login.feature
      onboarding.feature
  config/
    config.properties                 (shared: base.url, api.base.url)
    web.properties                    (browser, headless, viewport)
    mobile.properties                 (device, platformVersion, appPath)
  testsuites/
    testng-web.xml
    testng-api.xml
    testng-mobile.xml
    testng-full.xml                   (runs all three, for nightly/regression)


    .\gradlew.bat test                    → runs against dev (default)
    .\gradlew.bat test -Denv=staging      → runs against staging
    .\gradlew.bat test -Denv=prod         → runs against prod
    .\gradlew.bat test -Denv=dev         → runs against prod


    .\gradlew.bat clean test -Denv=dev --rerun-tasks
    .\gradlew.bat clean test -Denv=dev -Dbrowser=chromium --rerun-tasks
    .\gradlew.bat test -Denv=dev -Dbrowser=edge --rerun-tasks
    .\gradlew.bat test -Denv=dev -Dbrowser=firefox --rerun-tasks
    .\gradlew.bat test -Denv=dev -Dbrowser=webkit --rerun-tasks


    But do not use clean in all 4 commands, or one run may delete another run’s report/output.
    Better:
    .\gradlew.bat clean
    Start-Process powershell -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=dev -Dbrowser=chromium --rerun-tasks'
    Start-Process powershell -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=dev -Dbrowser=edge --rerun-tasks'
    Start-Process powershell -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=dev -Dbrowser=firefox --rerun-tasks'
    Start-Process powershell -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=dev -Dbrowser=webkit --rerun-tasks'

    -Denv=dev loads config-dev.properties
    -Dbrowser=firefox overrides browser=webkit from config-dev.properties

    .\gradlew.bat clean

    Start-Process powershell -WorkingDirectory "C:\Users\nagar\PayWrightWorkSpaceMaven" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=prod -Dbrowser=chromium --rerun-tasks'
    Start-Process powershell -WorkingDirectory "C:\Users\nagar\PayWrightWorkSpaceMaven" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=dev -Dbrowser=edge --rerun-tasks'
    Start-Process powershell -WorkingDirectory "C:\Users\nagar\PayWrightWorkSpaceMaven" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=staging -Dbrowser=firefox --rerun-tasks'
    Start-Process powershell -WorkingDirectory "C:\Users\nagar\PayWrightWorkSpaceMaven" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=prod -Dbrowser=webkit --rerun-tasks'

