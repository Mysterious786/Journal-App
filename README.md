# 📓 Journal App

**Journal App** is a full-stack web application built using **Spring Boot** (Java) on the backend and is designed to help users log, track, and analyze their personal journal entries securely. It integrates sentiment analysis, scheduling, and email services to provide a smarter, more reflective journaling experience.

## 🔧 Key Features

- **User Authentication & Management**  
  Secure login and registration functionality backed by MongoDB, with role-based access.

- **Journal Entries CRUD**  
  Users can create, view, edit, and delete journal entries, which are stored in a MongoDB collection.

- **Sentiment Analysis Integration**  
  Each journal entry is automatically analyzed for sentiment (positive, neutral, negative) using a custom sentiment analysis service.

- **Email Reminders & Reports**  
  Scheduled tasks send daily or weekly sentiment reports to users' registered email addresses using SMTP (Gmail).

- **Redis Caching**  
  Utilizes Redis to cache frequently accessed user data and optimize backend performance.

- **Robust Backend with Spring Boot**  
  Clean and modular code using best practices in Spring Boot, including Service and Repository layers, configuration via `application.yml`, and scheduler integration.

- **RESTful API Support**  
  Exposes APIs for interacting with journal entries and user data, making it easy to integrate with a frontend or mobile application.

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot, Spring Data MongoDB, Spring Scheduler, Spring Mail  
- **Database:** MongoDB (cloud-hosted via MongoDB Atlas)  
- **Caching:** Redis (via Redis Cloud)  
- **Mail Service:** SMTP via Gmail  
- **Deployment Ready:** Runs on port `9091`, easy to integrate with any frontend
