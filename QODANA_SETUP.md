# Qodana GitHub Actions Setup (Free GitHub-based version)

This setup follows the requirement:

- Use GitHub Actions
- Show results in GitHub
- Do not use `QODANA_TOKEN`
- Avoid Qodana Cloud
- Keep the setup in the free GitHub-based flow

## Why this is free

`QODANA_TOKEN` connects the action to JetBrains Qodana Cloud. If you use a Cloud token, the project may be subject to Qodana Cloud pricing and plan limits.

This configuration does not use `QODANA_TOKEN`, so it stays in the free GitHub-based reporting path.

---

## 1) GitHub Actions workflow file

Create the file:

`.github/workflows/qodana_code_quality.yml`

```yaml
name: Qodana

on:
  workflow_dispatch:
  pull_request:
  push:
    branches:
      - master

jobs:
  qodana:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      pull-requests: write
      security-events: write
      checks: write

    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'

      - name: 'Qodana Scan'
        uses: JetBrains/qodana-action@v2026.1
        with:
          pr-mode: false
          use-caches: true
          post-pr-comment: true
          use-annotations: true
          upload-result: false

      - name: Upload SARIF to GitHub
        if: always()
        uses: github/codeql-action/upload-sarif@v3
        with:
          sarif_file: ${{ runner.temp }}/qodana/results/qodana.sarif.json
```

### What this workflow does

- Checks out the repository
- Sets up Java 21
- Runs Qodana scan
- Posts PR comments if needed
- Uses annotations in GitHub checks
- Saves Qodana report as SARIF
- Uploads the SARIF to GitHub Security / Code Scanning

### Important note

This workflow intentionally does not include:

```yaml
env:
  QODANA_TOKEN: ${{ secrets.QODANA_TOKEN }}
```

That is the key to keeping it in the free GitHub-based mode.

---

## 2) Qodana configuration file

Create the file:

`qodana.yaml`

```yaml
version: "1.0"

profile:
  name: qodana.starter

projectJDK: "21"

linter: jetbrains/qodana-jvm-community:2026.1
```

### What this config does

- `version: "1.0"` -> Qodana configuration version
- `profile: qodana.starter` -> uses the default starter inspection profile
- `projectJDK: "21"` -> tells Qodana to inspect against Java 21
- `linter: jetbrains/qodana-jvm-community:2026.1` -> runs the community linter for JVM projects

---

## 3) Summary

This is the correct free version for your requirement:

- GitHub Action enabled
- GitHub report generated
- No Qodana Cloud token
- No paid plan required for the normal GitHub-based flow

Use this setup if you want Qodana analysis visible in GitHub without connecting to JetBrains Qodana Cloud.

---

## 4) If your project uses Java 17 instead of Java 21

Change both places from `21` to `17`:

```yaml
java-version: '21'
```

and

```yaml
projectJDK: "21"
```

to:

```yaml
java-version: '17'
```

and

```yaml
projectJDK: "17"
```

---

## 5) Final note

If you later add:

```yaml
env:
  QODANA_TOKEN: ${{ secrets.QODANA_TOKEN }}
```

then you are using Qodana Cloud, and the cost may depend on the JetBrains plan.

So for a free GitHub-only setup, keep the token out.
