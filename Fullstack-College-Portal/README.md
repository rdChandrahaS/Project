# Fullstack College Portal Project

This is a complete full-stack web application for a college, built with Spring Boot, Spring Security, and React.

---

## 🚀 Project Structure

This project is a "monorepo" containing two separate applications:

* `/backend`: The Spring Boot REST API (Java)
* `/frontend`: The React.js single-page application

---

## 🛠️ How to Run

### 1. Backend (Spring Boot)
1.  Open the `/backend` folder in your IDE (like Eclipse or IntelliJ).
2.  Ensure your MySQL server is running.
3.  Run the main `CollegeApplication.java` file.
4.  The server will start on `http://localhost:8080`.

### 2. Frontend (React)
1.  Open a terminal and navigate to the `/frontend` folder: `cd frontend`
2.  Install all dependencies: `npm install`
3.  Run the app: `npm start`
4.  The application will open in your browser at `http://localhost:3000`.

---

## 👤 Sample Logins

The database is automatically created with three sample users:

* **Admin:**
    * **Username:** `admin`
    * **Password:** `adminpass`
* **Teacher:**
    * **Username:** `teacher202`
    * **Password:** `pass123`
* **Student:**
    * **Username:** `student101`
    * **Password:** `pass123`