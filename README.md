# 🔐 Spring Boot JWT Authentication with Refresh Token & Swagger

This repository contains an **educational Spring Boot project** focused on understanding **JWT-based authentication**, **Refresh Token mechanisms**, and **Spring Security internals**, with full **Swagger (OpenAPI)** integration.

> 🎯 **Purpose:**  
> This project was built for **learning, practice, and interview preparation**, not for production deployment.

---

## 🚀 Technologies Used

- Java 22  
- Spring Boot 3.x  
- Spring Security  
- JWT (JSON Web Token)  
- Refresh Token Strategy (Token Rotation)  
- Spring Data JPA  
- PostgreSQL  
- Swagger / OpenAPI (springdoc-openapi)  
- Maven  

---

## 🎯 Project Purpose (Educational Focus)

This project was developed to deeply understand:

- How **JWT authentication** works internally
- The concept of **stateless authentication**
- **Refresh Token lifecycle & rotation**
- **Spring Security filter chain**
- Custom security components (filters & entry points)
- Securing APIs while keeping **Swagger UI accessible**
- Real-world backend authentication design patterns

> ⚠️ **Important Note**  
> This project is **not production-ready**.  
> It is intentionally designed as a **learning-oriented security project**.

---

## 🧱 Project Structure

```text
src/main/java/com/caglacakir
│
├── config
│   ├── AppConfig.java
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
│
├── controller
│   └── REST Controllers
│
├── dto
│   ├── DtoDepartment.java
│   ├── DtoEmployee.java
│   └── DtoUser.java
│
├── jwt
│   ├── AuthEntryPoint.java
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   └── RefreshTokenRequest.java
│
├── model
│   ├── User.java
│   ├── RefreshToken.java
│   ├── Employee.java
│   └── Department.java
│
├── repository
│   ├── UserRepository.java
│   ├── RefreshTokenRepository.java
│   └── EmployeeRepository.java
│
├── service
│   ├── IAuthService.java
│   ├── IEmployeeService.java
│   └── impl
│       └── IRefreshTokenService.java
│
└── starter
    └── JwtApplicationStarter.java


⸻

🔐 Authentication Flow Overview

Client
  |
  |  (username + password)
  v
/authenticate
  |
  |--> AuthenticationProvider
  |--> PasswordEncoder
  |
  v
JWT Access Token + Refresh Token

🔄 How Authentication Works
	1.	User registers or logs in
	2.	JWT Access Token is generated
	3.	Refresh Token is created and stored in the database
	4.	Client sends JWT in Authorization header
	5.	JwtAuthenticationFilter validates the token
	6.	If JWT expires:
	•	Client sends Refresh Token
	•	New Access Token + Refresh Token are issued

⸻

🔁 Refresh Token Strategy
	•	Refresh tokens are stored in the database
	•	Each refresh token:
	•	Belongs to a specific user
	•	Has an expiration date
	•	Token rotation is applied
	•	Old refresh token is invalidated
	•	A new refresh token is generated on each refresh request

🔄 Refresh Token Logic (Step-by-Step)
	1.	Client sends refresh token
	2.	Token is searched in database
	3.	Expiration date is validated
	4.	New access token is generated
	5.	New refresh token is issued
	6.	Old refresh token is revoked

✅ This approach prevents:
	•	Token reuse
	•	Long-lived access tokens
	•	Unauthorized session continuation

⸻

🔒 Spring Security Highlights
	•	Stateless authentication (SessionCreationPolicy.STATELESS)
	•	Custom JwtAuthenticationFilter
	•	Custom AuthenticationEntryPoint
	•	No HTTP session usage
	•	Token-based authorization only

⸻

🧪 Swagger (OpenAPI) Integration

Swagger is integrated to document and test secured APIs.

📦 Swagger Dependency

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>

🌐 Swagger UI

http://localhost:8080/swagger-ui.html


⸻

🔐 Swagger & JWT Security

By default, Swagger requests are blocked by Spring Security.

✅ Solution

Swagger paths are explicitly permitted in SecurityConfig:

"/swagger-ui/**",
"/v3/api-docs/**"

Swagger supports Bearer Authentication:
Authorization: Bearer <JWT_TOKEN>

Once the token is entered, secured endpoints become accessible.


⸻

📡 API Endpoints

🔐 Authentication Endpoints


| Method | Endpoint        | Description                     |
|--------|-----------------|---------------------------------|
| POST   | `/authenticate` | User login                      |
| POST   | `/register`     | User registration               |
| POST   | `/refreshToken` | Generate new Access Token       |

🔒 Protected Endpoints

All other endpoints require a valid JWT Access Token.

⸻

❗ Common Error Handling

401 Unauthorized

Returned when:
	•	JWT is missing
	•	JWT is invalid
	•	JWT is expired

Handled by custom AuthEntryPoint.

⸻

📌 Key Concepts Learned
	•	JWT structure & validation
	•	Refresh Token lifecycle
	•	Spring Security filter chain
	•	Stateless API security
	•	Swagger security configuration
	•	Clean layered architecture


⸻

🧠 Interview-Friendly Summary

This project clearly demonstrates:
	•	Why JWT is stateless
	•	Why Refresh Tokens are required
	•	How Spring Security processes requests
	•	Why Swagger returns 401 without configuration
	•	How token rotation improves security

⸻

📘 Final Note

This repository represents a learning journey into modern backend authentication using Spring Boot.

It was intentionally designed to be:
	•	✅ Readable
	•	✅ Explainable
	•	✅ Interview-friendly
	•	✅ Educational

---

## 👤 Author

**Çağla Çakır**  
Backend Development & Spring Boot Training Project  

---
