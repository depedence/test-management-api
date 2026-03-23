**📦 Сущности:**

**Company**
{
"company_name": "google",
"email": "google@gmail.com",
"password": "zxc123"
}

🧪 TestCase
{
"id": 1,
"title": "Login works",
"steps": "1. Open ru.depedence.page...",
"expectedResult": "User logged in",
"priority": "HIGH"
}

📊 TestRun
{
"id": 1,
"name": "Regression 1.0",
"startedAt": "2026-03-17"
}

📈 TestResult
{
"id": 1,
"testCaseId": 1,
"testRunId": 1,
"status": "PASSED",
"comment": "All good"
}

⚙️ 2. API endpoints

TestCase
POST   /api/testcases
GET    /api/testcases
GET    /api/testcases/{id}
DELETE /api/testcases/{id}

TestRun
POST   /api/testruns
GET    /api/testruns

TestResult
POST /api/results
GET  /api/results?runId=1