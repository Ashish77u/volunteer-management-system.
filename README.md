# 🌿 Volunteer Information Management System (VIMS)

> A production-quality REST API for NGO volunteer management — built with Java 21, Spring Boot 3.x, Spring Security 6, and JWT Authentication.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-brightgreen?style=flat-square&logo=springsecurity)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-green?style=flat-square&logo=swagger)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Database Design](#-database-design)
- [API Endpoints](#-api-endpoints)
- [Security Architecture](#-security-architecture)
- [Setup Instructions](#-setup-instructions)
- [API Documentation](#-api-documentation)
- [Sample Requests & Responses](#-sample-requests--responses)
- [Testing with Postman](#-testing-with-postman)
- [Contributing](#-contributing)

---

## 🎯 Project Overview

VIMS is a secure, scalable backend system that enables NGO administrators to manage volunteer information through a RESTful API. The system implements role-based access control (RBAC), stateless JWT authentication, and follows production-grade coding standards including layered architecture, DTO patterns, global exception handling, and full API documentation.

This project was built as a portfolio showcase for backend internship selection processes and demonstrates real-world Spring Boot patterns used in industry.

---

## ✨ Features

### Authentication & Authorization
- ✅ User Registration with role assignment (ADMIN / USER)
- ✅ User Login with JWT token generation
- ✅ BCrypt password encryption
- ✅ Stateless JWT-based authentication (Spring Security 6)
- ✅ Role-based endpoint protection

### Volunteer Management
- ✅ Create, Read, Update, Delete volunteers (Admin only for write)
- ✅ View all volunteers with Pagination & Sorting
- ✅ Get volunteer by ID
- ✅ Search volunteers by Name, City, or Skill
- ✅ Volunteer status management (ACTIVE / INACTIVE)

### Production-Level Features
- ✅ Global Exception Handling (`@RestControllerAdvice`)
- ✅ Bean Validation (`@Valid` + Jakarta Validation)
- ✅ Standardized API Response Wrapper
- ✅ DTO Pattern (Request/Response separation)
- ✅ Manual Mapper (no MapStruct dependency)
- ✅ Audit Timestamps (`createdAt`, `updatedAt`)
- ✅ Structured Logging (SLF4J + Logback)
- ✅ Swagger / OpenAPI 3.0 documentation
- ✅ Docker + Docker Compose support
- ✅ Postman Collection

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.15 |
| Security | Spring Security 6 + JWT (JJWT 0.12.6) |
| Persistence | Spring Data JPA + Hibernate |
| Database | MySQL 8.0 |
| Build Tool | Maven 3.9+ |
| Boilerplate | Lombok |
| Validation | Jakarta Validation |
| Documentation | SpringDoc OpenAPI 2.5 (Swagger UI) |
| Containerization | Docker + Docker Compose |
| Testing | Postman |

---
<!-- 
## 📁 Project Structure

```
volunteer-management-system/
├── src/
│   └── main/
│       ├── java/com/nayepankh/
│       │   ├── VolunteerManagementSystem.java
|       |   ├──
│       │   ├── config/
│       │   │   ├── SecurityConfig.java          # SecurityFilterChain, CORS, auth provider
│       │   │   └── SwaggerConfig.java           # OpenAPI definition + JWT bearer scheme
│       │   ├── controller/
│       │   │   ├── AuthController.java          # /api/auth/register, /api/auth/login
│       │   │   └── VolunteerController.java     # /api/volunteers CRUD + search
│       │   ├── dto/
│       │   │   ├── request/
│       │   │   │   ├── LoginRequest.java
│       │   │   │   ├── RegisterRequest.java
│       │   │   │   └── VolunteerRequest.java
│       │   │   └── response/
│       │   │       ├── ApiResponse.java         # Generic wrapper for all responses
│       │   │       ├── AuthResponse.java
│       │   │       ├── PagedResponse.java
│       │   │       └── VolunteerResponse.java
│       │   ├── entity/
│       │   │   ├── Role.java
│       │   │   ├── User.java
│       │   │   └── Volunteer.java
│       │   ├── enums/
│       │   │   ├── RoleName.java               # ROLE_ADMIN, ROLE_USER
│       │   │   └── VolunteerStatus.java         # ACTIVE, INACTIVE
│       │   ├── exception/
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   ├── ResourceNotFoundException.java
│       │   │   ├── EmailAlreadyExistsException.java
│       │   │   └── UnauthorizedException.java
│       │   ├── mapper/
│       │   │   └── VolunteerMapper.java         # Entity <-> DTO conversion
│       │   ├── repository/
│       │   │   ├── RoleRepository.java
│       │   │   ├── UserRepository.java
│       │   │   └── VolunteerRepository.java     # Custom JPQL search queries
│       │   ├── security/
│       │   │   ├── CustomUserDetailsService.java
│       │   │   ├── JwtAuthenticationFilter.java # OncePerRequestFilter
│       │   │   └── JwtTokenProvider.java        # Token generation + validation
│       │   ├── service/
│       │   │   ├── AuthService.java
│       │   │   └── VolunteerService.java
│       │   ├── service/impl/
│       │   │   ├── AuthServiceImpl.java
│       │   │   └── VolunteerServiceImpl.java
│       │   └── util/
│       │       └── AppConstants.java            # Pagination defaults
│       └── resources/
│           ├── application.properties
│           └── db/
│               └── schema.sql                   # Database schema + seed data
├── postman/
│   └── VolunteerManagement.postman_collection.json
├── Dockerfile                                   # Multi-stage build (Maven + JRE)
├── docker-compose.yml                           # App + MySQL
├── pom.xml
└── README.md
``` -->

---

## 🗄 Database Design

### Entity Relationship Diagram

```
┌──────────────┐       ┌──────────────────┐       ┌─────────────┐
│    users     │       │   user_roles     │       │    roles    │
├──────────────┤       ├──────────────────┤       ├─────────────┤
│ id (PK)      │──────<│ user_id (FK)     │>──────│ id (PK)     │
│ username     │       │ role_id (FK)     │       │ name UNIQUE │
│ email UNIQUE │       └──────────────────┘       └─────────────┘
│ password     │
│ created_at   │
│ updated_at   │
└──────────────┘

┌─────────────────────────────────┐
│           volunteers            │
├─────────────────────────────────┤
│ id (PK)                         │
│ full_name     VARCHAR(100)      │
│ email         VARCHAR(100) UQ   │
│ phone_number  VARCHAR(15)       │
│ city          VARCHAR(50)       │
│ skills        TEXT              │
│ availability  VARCHAR(100)      │
│ status        ENUM              │
│ joined_date   DATE              │
│ created_at    TIMESTAMP         │
│ updated_at    TIMESTAMP         │
└─────────────────────────────────┘
```

### Tables

| Table | Description |
|-------|-------------|
| `users` | System login accounts (admins and users) |
| `roles` | ROLE_ADMIN, ROLE_USER |
| `user_roles` | Many-to-many join table |
| `volunteers` | Core volunteer information |

### Index Strategy

```sql
-- Volunteers — supports search and filter operations
CREATE INDEX idx_volunteers_city     ON volunteers(city);
CREATE INDEX idx_volunteers_status   ON volunteers(status);
CREATE INDEX idx_volunteers_fullname ON volunteers(full_name);
```

---

## 📡 API Endpoints

### Auth Module

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/api/auth/register` | Public | Register new user |
| `POST` | `/api/auth/login` | Public | Login, receive JWT token |

### Volunteer Module

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/api/volunteers` | ADMIN | Create volunteer |
| `GET` | `/api/volunteers` | ADMIN, USER | Get all (paginated) |
| `GET` | `/api/volunteers/{id}` | ADMIN, USER | Get by ID |
| `PUT` | `/api/volunteers/{id}` | ADMIN | Update volunteer |
| `DELETE` | `/api/volunteers/{id}` | ADMIN | Delete volunteer |

### Search Module

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/api/volunteers/search?name=` | ADMIN, USER | Search by name |
| `GET` | `/api/volunteers/search?city=` | ADMIN, USER | Search by city |
| `GET` | `/api/volunteers/search?skill=` | ADMIN, USER | Search by skill |

### Query Parameters (Pagination)

| Param | Default | Description |
|-------|---------|-------------|
| `page` | `0` | Page index (0-based) |
| `size` | `10` | Records per page |
| `sortBy` | `id` | Field to sort by |
| `sortDir` | `asc` | `asc` or `desc` |

---

## 🔐 Security Architecture

```
HTTP Request
     │
     ▼
JwtAuthenticationFilter (OncePerRequestFilter)
     │
     ├── Extract Bearer token from Authorization header
     ├── Validate token signature + expiry (JwtTokenProvider)
     ├── Load UserDetails (CustomUserDetailsService)
     ├── Set Authentication in SecurityContextHolder
     │
     ▼
SecurityFilterChain (Spring Security 6)
     │
     ├── /api/auth/**          → permitAll
     ├── /swagger-ui/**        → permitAll
     ├── POST/PUT/DELETE       → hasRole('ADMIN')
     └── GET /api/volunteers   → hasAnyRole('ADMIN','USER')
     │
     ▼
Controller → Service → Repository
```

### JWT Token Structure

```
Header:  { "alg": "HS256", "typ": "JWT" }
Payload: { "sub": "username", "iat": 1234567890, "exp": 1234654290 }
Signature: HMACSHA256(base64(header) + "." + base64(payload), secret)
```

**Token validity:** 24 hours (configurable via `app.jwt.expiration-ms`)

---

## ⚙ Setup Instructions

### Prerequisites

- Java 21+
- Maven 3.9+
- MySQL 8.0+
- Git

### Step 1 — Clone the Repository

```bash
git clone https://github.com/Ashish77u/volunteer-management-system..git
cd volunteer-managemen-system
```


### Step 2 — Configure application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/volunteer_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

app.jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
app.jwt.expiration-ms=86400000
```

### Step 3 — Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application starts at: `http://localhost:8080`

### Step 4 — Verify Setup

```bash
# Health check (should return 200)
curl http://localhost:8080/api/auth/login
```



## 📖 API Documentation

Swagger UI is available after starting the application:

```
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON spec:

```
http://localhost:8080/api-docs
```

### Using Swagger UI

1. Open Swagger UI in browser
2. Click **Authorize** (🔓 button, top right)
3. Enter: `Bearer <your_jwt_token>`
4. Click **Authorize** → **Close**
5. All endpoints are now authenticated

---

## 📬 Sample Requests & Responses

### Register Admin User

**Request:**
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "admin",
  "email": "admin@ngo.org",
  "password": "Admin@123",
  "role": "admin"
}
```

**Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "email": "admin@ngo.org"
  },
  "timestamp": "2024-06-15T10:30:00"
}
```

---

### Login

**Request:**
```http
POST /api/auth/login
Content-Type: application/json

{
  "usernameOrEmail": "admin",
  "password": "Admin@123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "email": "admin@ngo.org"
  },
  "timestamp": "2024-06-15T10:31:00"
}
```

---

### Create Volunteer

**Request:**
```http
POST /api/volunteers
Authorization: Bearer <token>
Content-Type: application/json

{
  "fullName": "Priya Sharma",
  "email": "priya@example.com",
  "phoneNumber": "+919876543210",
  "city": "Mumbai",
  "skills": "Teaching, Medical Aid, Fundraising",
  "availability": "Weekends",
  "status": "ACTIVE",
  "joinedDate": "2024-01-15"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Volunteer created successfully",
  "data": {
    "id": 1,
    "fullName": "Priya Sharma",
    "email": "priya@example.com",
    "phoneNumber": "+919876543210",
    "city": "Mumbai",
    "skills": "Teaching, Medical Aid, Fundraising",
    "availability": "Weekends",
    "status": "ACTIVE",
    "joinedDate": "2024-01-15",
    "createdAt": "2024-06-15T10:32:00",
    "updatedAt": "2024-06-15T10:32:00"
  },
  "timestamp": "2024-06-15T10:32:00"
}
```

---

### Get All Volunteers (Paginated)

**Request:**
```http
GET /api/volunteers?page=0&size=5&sortBy=fullName&sortDir=asc
Authorization: Bearer <token>
```

**Response:**
```json
{
  "success": true,
  "message": "Volunteers fetched successfully",
  "data": {
    "content": [ { "id": 1, "fullName": "Priya Sharma", "..." : "..." } ],
    "page": 0,
    "size": 5,
    "totalElements": 12,
    "totalPages": 3,
    "last": false
  },
  "timestamp": "2024-06-15T10:33:00"
}
```

---

### Search by Skill

**Request:**
```http
GET /api/volunteers/search?skill=Teaching&page=0&size=10
Authorization: Bearer <token>
```

---

### Validation Error Response

```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "email": "Invalid email format",
    "fullName": "Full name is required",
    "phoneNumber": "Invalid phone number"
  },
  "timestamp": "2024-06-15T10:34:00"
}
```

---

### Resource Not Found Response

```json
{
  "success": false,
  "message": "Volunteer not found with id: '999'",
  "timestamp": "2024-06-15T10:35:00"
}
```

---

## 🧪 Testing with Postman

### Import Collection

1. Open Postman
2. Click **Import** → drag `postman/VolunteerManagement.postman_collection.json`
3. Collection variables are pre-configured:
    - `baseUrl` → `http://localhost:8080`
    - `token` → auto-filled after login

### Test Sequence

```
1. Auth → Register Admin
2. Auth → Login          ← token auto-saved to {{token}} variable
3. Volunteers → Create Volunteer
4. Volunteers → Get All Volunteers
5. Volunteers → Get By ID
6. Volunteers → Search by City
7. Volunteers → Update Volunteer
8. Volunteers → Delete Volunteer
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'feat: add your feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Ashish Sahani** — Java Backend Developer  
📧 ashishs77u@gmail.com  
🔗 [LinkedIn](https://linkedin.com/in/ashish-sahani1)  
💻 [GitHub](https://github.com/Ashish77u)

---

> Built with ❤ for NGO volunteer management and backend development learning.