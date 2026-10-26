# Run AI Job Hunter Agent in GitHub Codespaces

## 1. Create or rebuild the Codespace

Open the repository and choose **Code -> Codespaces -> Create codespace on main**.

If the Codespace was created before `.devcontainer/devcontainer.json` was added, open the Command Palette and run **Codespaces: Rebuild Container**. The development container provides Java 17, Maven, Node.js 20, and Docker tooling.

## 2. Start PostgreSQL

In the Codespaces terminal, from the repository root:

```bash
docker compose up -d postgres
docker compose ps
```

Wait until the postgres service is healthy before starting the backend.

## 3. Start the backend

Open a new terminal:

```bash
cd backend
mvn spring-boot:run
```

Wait for Spring Boot to finish starting. Open the forwarded port **8080** from the Ports tab, or visit `/actuator/health` on that forwarded URL. Expected response: `{"status":"UP"}`.

## 4. Start the Angular dashboard

Open another terminal from the repository root:

```bash
cd frontend
npm install
npm start
```

Open forwarded port **4200** from the Ports tab. If the browser does not open automatically, select the port's globe/open-in-browser action.

## 5. Add a sample job

In another terminal, run this shell command. It uses the forwarded localhost endpoint from inside the Codespace:

```bash
curl -i -X POST http://localhost:8080/api/jobs \
  -H 'Content-Type: application/json' \
  -d '{
    "title": "Senior Full Stack Developer",
    "company": "Demo Bank",
    "location": "Bengaluru",
    "sourceUrl": "https://example.com/demo-job-001",
    "source": "Manual Test",
    "description": "Java Spring Boot Microservices Angular PostgreSQL REST API",
    "matchScore": 80
  }'
```

Then retrieve jobs:

```bash
curl http://localhost:8080/api/jobs
```

The sample should appear in the dashboard after refreshing it. The API prevents adding the same source URL twice.

## 6. Build checks

Backend (from `backend`):

```bash
mvn test
mvn package
```

Frontend (from `frontend`):

```bash
npm run build
```

These commands verify the current code; resolve any reported errors before treating the app as tested.

## Notes

- Keep the backend and frontend terminals running while testing.
- In the Codespaces **Ports** tab, the dashboard/API ports should be forwarded. For personal use, keep them private rather than making them public.
- Gmail OAuth, automatic job collection, recruiter outreach, and application submission are not implemented yet. The current version is a starter with a jobs API and dashboard.
- No Gmail password, OAuth secret, or access token should be committed to GitHub.
