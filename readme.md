# HealthCareBackend

This is the backend service for the HealthCare system, implemented as a **Spring Boot monolithic application**. It powers all core business logic, APIs, and data processing for user, doctor, and admin functionalities.

---

## 🏗️ Technologies Used

- Java 19
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT Authentication
- MySQL
- Gradle (build tool)
- RESTful APIs

---

## 📚 Features

### 👤 User
- Register and login with JWT-based authentication  
- Browse all doctors  
- Book appointments (online via WebRTC or offline)  
- View prescriptions, test reports, and medicines  
- Sync with health data (Step Count, Heart Rate, Sleep Data)

### 🩺 Doctor
- View upcoming and past appointments  
- Add prescriptions, test reports, and medicine data for users  
- Mark unavailable dates to disable appointment bookings  

### 🛠️ Admin
- Add/manage doctors  
- Add/manage body parts (used in test categorization)  

---

## 📁 Project Structure

```

src/
├── config/              # Security,  JWT configs
├── controller/          # REST Controllers for users, doctors, admin
├── model/              # JPA Entities
├── repository/          # Spring Data JPA Repositories
├── service/             # Business Logic Layer
└── dto/                 # Data Transfer Objects

````

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 19
- Gradle
- MySQL or PostgreSQL
- (Optional) Redis or Firebase for signaling (if using WebRTC signaling separately)

### ⚙️ Setup & Run

1. Clone the repository:
```bash
git clone https://github.com/amanyara21/HealthCareBackend.git
cd HealthCareBackend
````

2. Configure `application.properties` or `application.yml`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/healthcare
spring.datasource.username=root
spring.datasource.password=yourpassword
jwt.secret=yourSecretKey
```

3. Run the app:

```bash
./gradlew bootRun
```

The backend will start on `http://localhost:8080`.

---

## 🔐 Authentication

* Uses **JWT** for securing APIs.
* Include `Authorization: Bearer <token>` in request headers after login.

---

## 📬 API Endpoints (Sample)

| Endpoint                 | Method | Description                   |
| ------------------------ | ------ | ----------------------------- |
| `/auth/register`         | POST   | Register user/doctor/admin    |
| `/auth/login`            | POST   | Login and get JWT token       |
| `/user/doctors`          | GET    | Get all available doctors     |
| `/user/book-appointment` | POST   | Book appointment              |
| `/doctor/appointments`   | GET    | Doctor’s appointment list     |
| `/admin/add-doctor`      | POST   | Add a new doctor (Admin only) |




## 🤝 Contributing

Contributions are welcome! Please open issues or submit PRs to help improve the project.

---

