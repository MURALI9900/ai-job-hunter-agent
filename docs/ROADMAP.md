# Implementation Roadmap

## Phase 1 — Foundation
- [x] Repository and architecture
- [x] Spring Boot API and PostgreSQL persistence
- [x] Angular dashboard starter
- [x] Local Docker Compose database
- [ ] Verify backend and frontend builds locally

## Phase 2 — Profile and matching
- [ ] Profile CRUD for roles, skills, locations, salary and notice period
- [ ] Explainable deterministic matching score
- [ ] Keyword and must-have filters
- [ ] Duplicate detection and source provenance

## Phase 3 — Job ingestion
- [ ] Import from manually supplied job URLs
- [ ] Parse job-alert emails after Gmail read-only OAuth
- [ ] Add only sources whose access and terms permit automation
- [ ] Scheduled scans, freshness checks and retry handling

## Phase 4 — Recruiter outreach
- [ ] Recruiter contact model with evidence/source and verification state
- [ ] Draft tailored messages
- [ ] Gmail send permission with explicit opt-in
- [ ] Per-day limits, duplicate prevention, pause switch and audit events

## Phase 5 — Applications
- [ ] Application status tracker
- [ ] User-reviewed application profile and resume selection
- [ ] Supported application form assistance
- [ ] Submit only where permitted and with clear consent
- [ ] Log confirmation, timestamp, URL and errors

## Phase 6 — hardening
- [ ] Authentication for any network-exposed deployment
- [ ] Encrypt tokens and sensitive fields
- [ ] Database migrations and backups
- [ ] Unit/integration tests and CI
- [ ] Deployment instructions
