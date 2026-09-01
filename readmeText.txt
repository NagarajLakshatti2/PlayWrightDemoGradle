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

    Start-Process powershell -WorkingDirectory "D:\PlayWrightDemoGradle" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=prod -Dbrowser=chromium --rerun-tasks'
    Start-Process powershell -WorkingDirectory "D:\PlayWrightDemoGradle" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=dev -Dbrowser=edge --rerun-tasks'
    Start-Process powershell -WorkingDirectory "D:\PlayWrightDemoGradle" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=staging -Dbrowser=firefox --rerun-tasks'
    Start-Process powershell -WorkingDirectory "D:\PlayWrightDemoGradle" -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=prod -Dbrowser=webkit --rerun-tasks'

======================

    $project = "D:\PlayWrightDemoGradle"

    Set-Location $project
    .\gradlew.bat clean
    .\gradlew.bat installPlaywrightBrowsers

     Start-Process powershell -WorkingDirectory $project -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=prod -Dbrowser=chromium --rerun-tasks -x openExtentReport' -PassThru
     Start-Process powershell -WorkingDirectory $project -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=dev -Dbrowser=edge --rerun-tasks -x openExtentReport' -PassThru
     Start-Process powershell -WorkingDirectory $project -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=staging -Dbrowser=firefox --rerun-tasks -x openExtentReport' -PassThru
     Start-Process powershell -WorkingDirectory $project -ArgumentList '-NoExit', '-Command', '.\gradlew.bat test -Denv=prod -Dbrowser=webkit --rerun-tasks -x openExtentReport' -PassThru

    Wait-Process -Id $p1.Id, $p2.Id, $p3.Id, $p4.Id

    Start-Process "$project\test-output\prod\chromium\ExtentReport.html"
    Start-Process "$project\test-output\dev\edge\ExtentReport.html"
    Start-Process "$project\test-output\staging\firefox\ExtentReport.html"
    Start-Process "$project\test-output\prod\webkit\ExtentReport.html"

    ================

    To ignore signing for this one commit, run:
    git commit --no-gpg-sign -m "your commit message"
    Or short form:
    git commit -n -m "your commit message"

    But note: -n means --no-verify, not only no GPG signing. So better use:
    git commit --no-gpg-sign -m "your commit message"
    To disable GPG signing only in this repo:
    git config commit.gpgsign false

    To disable globally:
    git config --global commit.gpgsign false
    Best temporary solution:
    git commit --no-gpg-sign -m "message"
    ==========================

For your project, do this first:
1. Start Artifactory in Docker
2. Create a private local repo for your JARs
3. Publish your internal library there
4. Use GitHub for repo and code
5. Add MCP later only if you want AI integrations


==========
power shell
.\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dvisual.strict=true" --rerun-tasks

cmd not working
gradlew.bat clean test -Denv=dev -Dbrowser=chromium -Dheadless=true -Dvisual.strict=true --rerun-tasks

$env:AI_API_KEY="your_real_key_here"

.\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dai.enabled=true" --rerun-tasks
.\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dai.enabled=true" "-Dvisual.strict=true" --rerun-tasks

Run a quick AI smoke test from PowerShell:
Invoke-RestMethod -Method Post `
  -Uri "http://127.0.0.1:11435/v1/chat/completions" `
  -Headers @{ "Authorization" = "Bearer ollama" } `
  -ContentType "application/json" `
  -Body '{"model":"llama3.2:latest","messages":[{"role":"user","content":"Hello from Ollama"}],"max_tokens":50}'

==========

$env:RP_ENDPOINT="http://localhost:19080"
$env:RP_API_KEY="5145b879-83d9-4692-8b07-928cc4b2af7a"
$env:RP_PROJECT="default_personal"

& .\gradlew.bat clean test `
  "-Denv=dev" `
  "-Dbrowser=chromium" `
  "-Dheadless=true" `
  "-Drp.enabled=true" `
  "-Drp.endpoint=$env:RP_ENDPOINT" `
  "-Drp.api.key=$env:RP_API_KEY" `
  "-Drp.project=$env:RP_PROJECT" `
  --rerun-tasks

or one line
$env:RP_ENDPOINT="http://localhost:19080"; $env:RP_API_KEY="5145b879-83d9-4692-8b07-928cc4b2af7a"; $env:RP_PROJECT="default_personal"; & .\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Drp.enabled=true" "-Drp.endpoint=$env:RP_ENDPOINT" "-Drp.api.key=$env:RP_API_KEY" "-Drp.project=$env:RP_PROJECT" --rerun-tasks

ngrok config add-authtoken <YOUR_NGROK_AUTH_TOKEN>
3Ijz6jg8UMxFFowGgZJZem5svw5_5ddt5dVcEpapmnxRjZgut

ngrok config add-authtoken 3Ijz6jg8UMxFFowGgZJZem5svw5_5ddt5dVcEpapmnxRjZgut
ngrok config add-authtoken ng-3Ik0Pj8SvQxJ0j2mnCMtA4T9gNa-g1-fu4XPUgLnX747aZdPzBwYWUsLPj9hqdzw

ng-3Ik1OK31JtbFchlAD8pw1kYuJuK-g1-dHEqUnmDBXqAPt3gxU5BHQ5RQ3pS5zXEQ

https://dashboard.ngrok.com/get-started/ng-3Ik1OK31JtbFchlAD8pw1kYuJuK-g1-dHEqUnmDBXqAPt3gxU5BHQ5RQ3pS5zXEQ

ngrok config add-authtoken 3Ijz6jg8UMxFFowGgZJZem5svw5_5ddt5dVcEpapmnxRjZgut

api key
JeshAI-GitHub-CI-8618153273

JeshAI-GitHub-CI-8618153273_iA_EWfmWQrCm68o4fsbpcE7slv0N--wgarSK5Jwh4cxPFxVM_QiUpQi8Y3ABNxfk
