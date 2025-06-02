# 💰 Budget Buddy App

A simple budgeting web app for tracking income, expenses, and balance, built with a **Spring Boot backend** and **React frontend**, using **PostgreSQL** for storage.

---

### 🚀 How to Run

#### 1️⃣ Clone the repository
```bash
git clone https://github.com/milamartyna/budget-app.git
cd buget-app
```

#### 2️⃣  Build backend JAR
Before running Docker Compose, you must first build the backend JAR file:
```bash
cd backend
gradle bootRun
```

#### 3️⃣ Build and run everything with Docker Compose
```bash
docker-compose up --build
```
This will:\
✅ start the backend \
✅ start the frontend \
✅ start the Postgres database 

#### 3️⃣ Access the application
Frontend app → http://localhost:3000

Backend API & Swagger docs → http://localhost:8080/swagger-ui/index.html

### ⚙️ Requirements
- Docker installed

- Docker Compose installed
  
- Java & Gradle installed (for local backend build)

### 📦 Build Artifacts
- **Backend Dockerfile** → builds the JAR and runs it.

- **Frontend Dockerfile** → builds the React app and serves it via Nginx.

- **docker-compose.yml** → orchestrates backend, frontend, and Postgres containers.

### ✨ Features
✅ User registration + login (JWT-secured) \
✅ Add and view income & expenses \
✅ Dashboard summary with balance \
✅ React frontend with MUI styling \
✅ Swagger UI for API testing
