# AI Job Hunter Agent

A privacy-conscious job-search assistant that collects permitted job listings, scores them against a career profile, drafts recruiter outreach, and tracks applications.

## Project goals

- Discover jobs from permitted sources and user-configured job-alert emails.
- Match roles against a configurable profile (skills, experience, location and preferences).
- Track jobs, recruiter contacts, outreach drafts and application status.
- Integrate Gmail using Google OAuth 2.0; never collect or store a Gmail password.
- Add browser-assisted application workflows only where the target site permits them.
- Keep an audit trail and use approval-first defaults for sending emails and submitting applications.

## Technology

- Backend: Java 17, Spring Boot, Spring Data JPA, PostgreSQL
- Frontend: Angular
- Local development: Docker Compose
- Integrations planned: Gmail API, permitted job feeds/search sources, optional local AI

## Status

**Current status:** initial scaffold, job CRUD API, PostgreSQL persistence, Angular dashboard, career-profile API, and deterministic match scoring are committed. Gmail OAuth, automated job ingestion, recruiter outreach, and application workflows are the next phases. The project has not yet been built or runtime-tested in this environment.

## Run locally

Requirements: Java 17+, Maven 3.9+, Node.js LTS, npm, and Docker Desktop.

1. Start PostgreSQL:

   ```bash
   docker compose up -d postgres
   ```

2. Start the backend:

   ```bash
   cd backend
   mvn spring-boot:run
   ```

   API base URL: http://localhost:8080/api

3. Start the frontend after installing Angular dependencies:

   ```bash
   cd frontend
   npm install
   npm start
   ```

   Dashboard URL: http://localhost:4200

## Configuration

The backend reads environment variables. Local defaults are for development only.

- `DB_URL` (default: `jdbc:postgresql://localhost:5432/jobhunter`)
- `DB_USERNAME` (default: `jobhunter`)
- `DB_PASSWORD` (default: `jobhunter_local_only`)

Never commit real passwords, OAuth client secrets, access tokens, refresh tokens, or personal application data. Use a local `.env` file excluded by Git, and rotate any credential that is accidentally exposed.

## Safety and platform compliance

- Use official APIs, job alerts, feeds, or sources that permit automated access.
- Do not bypass CAPTCHAs, login controls, rate limits, or anti-bot protections.
- Do not guess recruiter email addresses. Store the source and verification status of contact details.
- Email sending and application submission must remain approval-gated until explicitly configured and tested.
- Keep a daily sending limit, duplicate prevention, unsubscribe/stop controls, and an audit log.

## Roadmap

1. [x] Initial repository and architecture scaffold
2. [x] Job listing API and database setup
3. [ ] Profile management and explainable match scoring
4. [ ] Import jobs from user-provided links and permitted feeds
5. [ ] Gmail OAuth and read-only job-alert ingestion
6. [ ] Recruiter contact records and editable email drafts
7. [ ] Approval-gated Gmail sending and audit log
8. [ ] Application tracker and permitted form assistance
9. [ ] Tests, deployment guide, and optional local AI

## License

MIT (see `LICENSE`).
