# 💰 Budget Buddy App

A simple budgeting web app for tracking income, expenses, and balance, built with a **Spring Boot backend** and **React frontend**, using **PostgreSQL** for storage.

---

### 🚀 How to Run

#### 1️⃣ Clone the repository
```bash
git clone https://github.com/milamartyna/budget-app.git
cd budget-app
```

#### 2️⃣ Create environment variables
Create a .env file based on the example:

```bash
cp .env.example .env
```

The .env file is ignored by Git and contains only local configuration.

#### 3️⃣ Build and run the application
```bash
docker-compose up --build
```
This will:
- start the backend 
- start the frontend 
- start the Postgres database 

#### 4️⃣ Access the application
Frontend app → http://localhost:3000

Backend API & Swagger docs → http://localhost:8080/swagger-ui/index.html

### ⚙️ Requirements
- Docker installed

- Docker Compose installed

### 📦 Build Artifacts
- **Backend Dockerfile** → builds the JAR and runs it.

- **Frontend Dockerfile** → builds the React app and serves it via Nginx.

- **docker-compose.yml** → orchestrates backend, frontend, and Postgres containers.

## 🧪 Development Notes

### Initial demo data

On application startup, the backend loads **demo data** for development purposes:

- sample users
- categories
- income and expense transactions

This is implemented in the `DataInitializer` class.

⚠️ **Important:**

- the initializer clears the database on startup
- it is intended for local development and demos only
- it should be disabled or removed for production use


### ✨ Features
✅ User registration + login (JWT-secured) \
✅ Add and view income & expenses \
✅ Dashboard summary with balance \
✅ React frontend with MUI styling \
✅ Swagger UI for API testing
