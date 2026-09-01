# ReportPortal Local Setup (Docker)

## 1) Create the folder

```powershell
New-Item -ItemType Directory -Force -Path "C:\reportportal" | Out-Null
```

## 2) Save this docker-compose.yml

```yaml
version: "3.8"

services:
  postgres:
    image: postgres:15-alpine
    container_name: reportportal-postgres
    environment:
      POSTGRES_DB: reportportal
      POSTGRES_USER: reportportal
      POSTGRES_PASSWORD: reportportal
    volumes:
      - postgres:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U reportportal -d reportportal"]
      interval: 10s
      timeout: 5s
      retries: 5

  rabbitmq:
    image: rabbitmq:3.12-management
    container_name: reportportal-rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"

  minio:
    image: minio/minio:RELEASE.2024-01-16T16-07-38Z
    container_name: reportportal-minio
    command: server /data --console-address ":9001"
    environment:
      MINIO_ROOT_USER: minio
      MINIO_ROOT_PASSWORD: minio123
    volumes:
      - minio:/data
    ports:
      - "9000:9000"
      - "9001:9001"

  uat:
    image: reportportal/service-authorization:latest
    container_name: reportportal-uat
    environment:
      RP_DB_HOST: postgres
      RP_DB_PORT: 5432
      RP_DB_NAME: reportportal
      RP_DB_USER: reportportal
      RP_DB_PASSWORD: reportportal
      RP_AMQP_HOST: rabbitmq
      RP_AMQP_PORT: 5672
      RP_AMQP_USER: guest
      RP_AMQP_PASSWORD: guest
      RP_INITIAL_ADMIN_PASSWORD: "1q2w3e"
    depends_on:
      postgres:
        condition: service_healthy
      rabbitmq:
        condition: service_started
    ports:
      - "9999:9999"

  api:
    image: reportportal/service-api:latest
    container_name: reportportal-api
    environment:
      RP_DB_HOST: postgres
      RP_DB_PORT: 5432
      RP_DB_NAME: reportportal
      RP_DB_USER: reportportal
      RP_DB_PASSWORD: reportportal
      RP_AMQP_HOST: rabbitmq
      RP_AMQP_PORT: 5672
      RP_AMQP_USER: guest
      RP_AMQP_PASSWORD: guest
      RP_MINIO_ENDPOINT: http://minio:9000
      RP_MINIO_ACCESSKEY: minio
      RP_MINIO_SECRETKEY: minio123
      RP_UAT_SERVICE_URL: http://uat:9999
    depends_on:
      postgres:
        condition: service_healthy
      rabbitmq:
        condition: service_started
      minio:
        condition: service_started
      uat:
        condition: service_started
    ports:
      - "18080:8080"

  ui:
    image: reportportal/service-ui:latest
    container_name: reportportal-ui
    environment:
      RP_SERVER_PORT: 8081
      RP_API_BASE_URL: http://api:8080
    depends_on:
      - api
    ports:
      - "18081:8081"

  jobs:
    image: reportportal/service-jobs:latest
    container_name: reportportal-jobs
    environment:
      RP_DB_HOST: postgres
      RP_DB_PORT: 5432
      RP_DB_NAME: reportportal
      RP_DB_USER: reportportal
      RP_DB_PASSWORD: reportportal
      RP_AMQP_HOST: rabbitmq
      RP_AMQP_PORT: 5672
      RP_AMQP_USER: guest
      RP_AMQP_PASSWORD: guest
      RP_MINIO_ENDPOINT: http://minio:9000
      RP_MINIO_ACCESSKEY: minio
      RP_MINIO_SECRETKEY: minio123
      RP_UAT_SERVICE_URL: http://uat:9999
    depends_on:
      postgres:
        condition: service_healthy
      rabbitmq:
        condition: service_started
      minio:
        condition: service_started
      uat:
        condition: service_started

volumes:
  postgres:
  minio:
```

Save it to `C:\reportportal\docker-compose.yml`.

## 3) If port 8080 is already occupied, change ports before starting

Use this mapping instead:

```yaml
  api:
    ports:
      - "19080:8080"

  ui:
    ports:
      - "19081:8081"
```

Then start ReportPortal:

```powershell
cd C:\reportportal
docker compose -p reportportal down --remove-orphans
docker compose -p reportportal up -d --force-recreate
```

## 4) Check services

```powershell
docker compose -p reportportal ps
```

## 5) Open ReportPortal UI

```text
http://localhost:8081
```

## 6) Login

Default user:

```text
default / 1q2w3e
```

Then change the password.

## 7) Create a project

Create a project, for example:

```text
default_personal
```

## 8) Generate an API key

In the ReportPortal UI, create or copy an API key for that user/project.

## 9) Add GitHub secrets

Repository Settings → Secrets and variables → Actions

Set:

```text
RP_ENDPOINT=http://localhost:8081
RP_API_KEY=<your_api_key>
RP_PROJECT=default_personal
```

## 10) Local-safe reportportal.properties

```properties
rp.enabled=false
rp.launch=Playwright Demo Gradle
rp.project=default_personal
rp.attributes=framework:playwright;language:java;module:gradle
```

If you want to send this machine's local runs to ReportPortal, enable it temporarily with a valid API key and the ReportPortal API URL. For this Docker setup, the host API URL is typically `http://localhost:18080`.

Do not add `rp.api.key` here if you are checking the file into source control.

## 11) Run the workflow

Push to GitHub and run the workflow. It will send live execution details to ReportPortal.
