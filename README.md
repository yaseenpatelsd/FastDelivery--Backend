# 🚀 FastDelivery — Backend

A full-featured Spring Boot backend for a delivery management system — including JWT authentication, OTP verification, password reset, role-based access control, admin item management (CRUD), and Razorpay payment integration.

---

## 🧩 Overview
A Spring Boot backend for a delivery management platform that enables secure user authentication, password reset with OTP, role-based access control, item management for admins, and payment integration using Razorpay.

This project demonstrates a production-ready backend with a modular architecture, security, and real-world business logic.

---

## 🧰 Tech Stack
- **Java 17+**
- **Spring Boot 3**
- **Spring Security (JWT)**
- **Spring Data JPA / Hibernate**
- **MySQL Database**
- **Razorpay Payment Gateway (Test Mode)**
- **Java Mail (OTP Verification)**
- **Maven**

---

## ⚙️ Features

### 🧑‍💻 Authentication & Security
- User registration with email OTP verification  
- Secure login using JWT tokens  
- Password reset via email OTP  
- Role-based access control (USER / ADMIN)  
- Token-based protected routes  

### 📦 Item Management
- Admin Panel: Create, update, delete items  
- User Side: View available items and purchase them  
- Item CRUD operations handled with proper service and repository layers  

### 💰 Razorpay Payment Integration
- Users can subscribe or purchase using Razorpay (test mode)  
- Transaction details stored in database  
- Auto-update user subscription status on payment  

### 📧 OTP System
OTP sent via email for:
- Registration verification  
- Password reset requests  

---

## 🧩 Other Highlights
- Clean layered architecture (Controller → Service → Repository → Entity → DTO)  
- Global exception handling  
- Validation and proper HTTP responses  
- Fully Postman-tested APIs  

---

## 🧩 Project Structure
     Delivery-App/
    ├── .idea/ # IntelliJ project settings
    ├── .mvn/ # Maven wrapper files
    ├── src/
    │ ├── main/
    │ │ ├── java/
    │ │ │ └── com/Yua/FastDelivery/Delivery_App/
    │ │ │ ├── Config/ # Application configuration classes
    │ │ │ ├── Controller/ # REST API controllers
    │ │ │ ├── Dto/ # Data Transfer Objects
    │ │ │ ├── Entity/ # JPA entities (database models)
    │ │ │ ├── Exception/ # Custom exceptions
    │ │ │ ├── GlobleExeption/ # Global exception handler
    │ │ │ ├── Jwt/ # JWT token utilities and filters
    │ │ │ ├── Mapping/ # Entity-to-DTO mappers
    │ │ │ ├── Repository/ # Spring Data JPA repositories
    │ │ │ ├── Service/ # Business logic and services
    │ │ │ └── DeliveryAppApplication.java # Main Spring Boot application
    │ │ └── resources/
    │ │ └── application.properties # Application configuration file
    │ ├── test/ # Unit and integration tests
    │
    ├── target/ # Compiled output and build files
    ├── .gitattributes # Git settings for file normalization
    └── README.md # Project documentation
