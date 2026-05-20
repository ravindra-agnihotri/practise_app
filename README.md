# 🤖 AutoPractice — UI & API Automation Test App

A fully-featured Spring Boot + SQLite web application built specifically for practising **Selenium UI automation**, **REST API testing**, and **end-to-end automation** scenarios.

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+

### Run the Application
```bash
cd automation-practice
mvn spring-boot:run
```

Open your browser at: **http://localhost:8080**

---

## 🔐 Login Credentials

| Username   | Password   | Role        |
|------------|------------|-------------|
| admin      | admin123   | ADMIN, USER |
| user       | user123    | USER        |
| testuser   | Test@1234  | USER        |

---

## 📄 Pages Available

| Page          | URL           | Description                                          |
|---------------|---------------|------------------------------------------------------|
| Home          | /             | Landing page with stats and featured products        |
| UI Elements   | /elements     | All HTML element types for UI automation practice    |
| Forms         | /forms        | Registration, multi-step, validation, dynamic forms  |
| Tables        | /tables       | Sortable, paginated, and searchable data tables      |
| Products      | /products     | Product grid with search, filter, cart               |
| Todos         | /todos        | CRUD todo manager with tabs and priorities           |
| Contact       | /contact      | Contact form with validation                         |
| Alerts        | /alerts       | Browser dialogs, toasts, countdown timers            |
| Modals        | /modals       | Modals, drawers, tooltips, nested dialogs            |
| API Docs      | /about        | REST API documentation + live tester                 |
| Login         | /login        | Spring Security login page                           |
| Dashboard     | /dashboard    | Protected page (requires login)                      |
| Users         | /users        | User management (CRUD)                               |

---

## 📡 REST API Endpoints

Base URL: `http://localhost:8080/api/v1`

### Health & Stats
```
GET  /api/v1/health
GET  /api/v1/stats
```

### Users
```
GET    /api/v1/users
GET    /api/v1/users/{id}
POST   /api/v1/users
PUT    /api/v1/users/{id}
PATCH  /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

### Products
```
GET    /api/v1/products
GET    /api/v1/products?category=BOOKS
GET    /api/v1/products?search=selenium
GET    /api/v1/products?featured=true
GET    /api/v1/products/{id}
POST   /api/v1/products
PUT    /api/v1/products/{id}
DELETE /api/v1/products/{id}
```

### Todos
```
GET    /api/v1/todos
GET    /api/v1/todos?completed=false
GET    /api/v1/todos?completed=true
GET    /api/v1/todos/{id}
POST   /api/v1/todos
PUT    /api/v1/todos/{id}
PATCH  /api/v1/todos/{id}/toggle
DELETE /api/v1/todos/{id}
```

### Contacts
```
GET    /api/v1/contacts
GET    /api/v1/contacts/{id}
POST   /api/v1/contacts
PATCH  /api/v1/contacts/{id}/status
DELETE /api/v1/contacts/{id}
```

---

## 🎯 What to Automate

### UI Automation (Selenium/Playwright/Cypress)
- ✅ Login / Logout flow
- ✅ Form filling — text, email, password, date, file upload, range
- ✅ Dropdowns — single select, multi-select, grouped options
- ✅ Checkboxes and radio buttons
- ✅ Modal dialogs — open, close (✕ button / backdrop / Escape key)
- ✅ Browser native alerts — alert(), confirm(), prompt()
- ✅ Dynamic content — appearing/disappearing elements
- ✅ iFrame interaction
- ✅ Drag and drop
- ✅ Multi-step wizard form
- ✅ Client-side form validation
- ✅ Table search, sort, and pagination
- ✅ Toast notifications
- ✅ Side drawers (left/right)
- ✅ Tabs switching
- ✅ Role-based UI (admin vs user sections)
- ✅ Product cart operations
- ✅ Countdown timer / waiting scenarios
- ✅ CRUD operations (Create, Read, Update, Delete)

### API Automation (RestAssured/Postman/Karate)
- ✅ GET requests (all records, by ID, filtered)
- ✅ POST requests with JSON body
- ✅ PUT requests (full update)
- ✅ PATCH requests (partial update)
- ✅ DELETE requests (returns 204)
- ✅ Response status code validation
- ✅ Response body schema validation
- ✅ Query parameter filtering
- ✅ Error response handling (400, 404)

---

## 🗄️ Database

SQLite database file: `automation_practice.db` (auto-created on first run)

### Tables
- `users` — App users with full profile data
- `products` — Product catalog with categories, pricing, stock
- `todos` — Todo items with priorities and due dates
- `contacts` — Contact form submissions

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2, Spring Security, Spring Data JPA
- **Database**: SQLite (via Hibernate Community Dialects)
- **Templating**: Thymeleaf
- **Validation**: Jakarta Bean Validation
- **Build**: Maven
- **Frontend**: Vanilla HTML/CSS/JS (no frameworks — easier for automation)

---

## 📁 Project Structure

```
src/main/java/com/automationpractice/
├── AutomationPracticeApplication.java
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── HomeController.java     # Page routes
│   └── ApiController.java      # REST API
├── model/
│   ├── AppUser.java
│   ├── Product.java
│   ├── TodoItem.java
│   └── Contact.java
├── repository/
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   ├── TodoRepository.java
│   └── ContactRepository.java
└── service/
    ├── UserService.java
    ├── ProductService.java
    ├── TodoService.java
    ├── ContactService.java
    └── DataSeeder.java         # Seeds sample data on startup

src/main/resources/
├── application.properties
├── static/
│   ├── css/main.css
│   └── js/main.js
└── templates/
    ├── index.html
    ├── login.html
    ├── dashboard.html
    ├── elements.html
    ├── forms.html
    ├── tables.html
    ├── products.html
    ├── todos.html
    ├── contact.html
    ├── alerts.html
    ├── modals.html
    ├── about.html
    ├── users.html
    └── user-edit.html
```

---

## 💡 Tips for Automation

1. All interactive elements have unique `id` attributes for easy locating
2. Data attributes like `data-product-id`, `data-category` help with data-driven tests
3. The API returns JSON — use it with RestAssured, Postman, or Karate
4. Sample data is seeded automatically on every fresh start
5. The live API tester on `/about` lets you manually test endpoints in the browser
