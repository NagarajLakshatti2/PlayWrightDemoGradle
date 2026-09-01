# ReportPortal setup for local and GitHub Actions

## Is it free?

- Self-hosted ReportPortal is free and open source.
- The official Docker-based deployment is the normal choice for local development and private CI.
- Managed/hosted ReportPortal plans are paid; for local or GitHub Actions usage, the free self-hosted path is recommended.

## Local prerequisites

- Docker Desktop or Docker Engine + Docker Compose
- CPU: 2+ cores recommended
- Memory: 4–8 GB recommended
- Disk: 20–50 GB free space
- Browser access to `http://localhost:8080`

## Local startup

1. Download the official ReportPortal Docker Compose file from the ReportPortal repo.
2. Start the stack:

```bash
docker compose -p reportportal up -d
```

3. Open the UI:

```text
http://localhost:8080
```

4. Sign in with the default demo credentials:

```text
default / 1q2w3e
```

5. Change the password after login.

## GitHub Actions prerequisites

- A running ReportPortal server reachable from GitHub Actions
- A ReportPortal project name
- A ReportPortal API key for that project
- GitHub repository secrets:
  - `RP_ENDPOINT`
  - `RP_API_KEY`
  - `RP_PROJECT`

Example values:

```text
RP_ENDPOINT=https://reportportal.example.com
RP_API_KEY=xxxxxxxxxxxxxxxx
RP_PROJECT=default_personal
```

## How this repo is configured

- `build.gradle` includes the ReportPortal Java agent for TestNG and logback.
- `src/test/resources/reportportal.properties` stores the default local endpoint and launch metadata.
- `.github/workflows/playwright-java-tests.yml` runs tests with ReportPortal when the API key is provided and falls back to normal execution when it is not configured.

## Running locally without ReportPortal

The workflow gracefully skips ReportPortal when `RP_API_KEY` is absent. The repo defaults to `rp.enabled=false` in `src/test/resources/reportportal.properties`, so local runs do not fail when no server is available.

For manual local runs with ReportPortal enabled, export your values before executing Gradle:

```bash
export RP_ENDPOINT=http://localhost:18080
export RP_API_KEY=YOUR_API_KEY
export RP_PROJECT=default_personal
export RP_LAUNCH="Playwright Demo Gradle - local"
./gradlew clean test \
  -Denv=dev \
  -Dbrowser=chromium \
  -Dheadless=true \
  -Drp.enabled=true \
  -Drp.endpoint="$RP_ENDPOINT" \
  -Drp.api.key="$RP_API_KEY" \
  -Drp.project="$RP_PROJECT" \
  -Drp.launch="$RP_LAUNCH" \
  --rerun-tasks
```

If `RP_API_KEY` is not set, the run still works normally and produces the standard HTML/Gradle reports.

## Notes

- This setup is best for live execution tracking, log correlation, and failure triage.
- Add the same environment variables to your CI secrets to upload each run to ReportPortal automatically.
