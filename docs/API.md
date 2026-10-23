# Initial API

Base URL: `http://localhost:8080/api`

## List jobs

`GET /jobs`

Optional query: `?status=DISCOVERED`

## Get one job

`GET /jobs/{id}`

## Add a job

`POST /jobs` with JSON:

```json
{
  "title": "Full Stack Developer",
  "company": "Example Company",
  "location": "Bengaluru / Remote",
  "sourceUrl": "https://example.com/careers/job-123",
  "source": "MANUAL",
  "description": "Java, Spring Boot, Microservices, Angular and SQL",
  "matchScore": 82
}
```

A duplicate `sourceUrl` returns HTTP 409. Validation failures return HTTP 400.

## Update status

`PATCH /jobs/{id}/status?status=REVIEW`

Valid statuses: `DISCOVERED`, `REVIEW`, `OUTREACH_DRAFTED`, `OUTREACH_SENT`, `APPLIED`, `INTERVIEW`, `REJECTED`, `CLOSED`, `NOT_RELEVANT`.

## Delete a job

`DELETE /jobs/{id}`

## Health

`GET /actuator/health`

> The initial API is intended for local development. It does not yet have user authentication. Do not expose it publicly until authentication and authorization are implemented.
