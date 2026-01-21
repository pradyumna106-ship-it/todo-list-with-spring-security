# 📝 Todo List API

A RESTful API that allows users to register, authenticate, and manage their personal to-do list securely. This project demonstrates backend fundamentals such as authentication, authorization, CRUD operations, pagination, validation, and error handling.

---

## 🚀 Features

* User Registration & Login with token-based authentication (JWT or token)
* Secure password hashing
* Create, Read, Update, Delete (CRUD) To-Do items
* Authorization: users can only manage their own to-dos
* Pagination support for fetching to-do items
* Proper error handling and HTTP status codes
* Database-backed persistence

---

## 🛠️ Tech Stack (Example)

> You may adapt this section based on your implementation

* Backend: Java (Spring Boot) / Node.js (Express)
* Authentication: JWT
* Database: MySQL / PostgreSQL / MongoDB
* ORM: JPA / Hibernate / Sequelize / Mongoose
* Security: Password hashing (BCrypt)

---

## 📌 API Endpoints

### 🔐 User Registration

**Endpoint**

```
POST /register
```

**Request Body**

```json
{
  "name": "John Doe",
  "email": "john@doe.com",
  "password": "password"
}
```

**Success Response**

```json
{
  "token": "<auth_token>"
}
```

**Validations**

* Email must be unique
* Password is hashed before storage

---

### 🔑 User Login

**Endpoint**

```
POST /login
```

**Request Body**

```json
{
  "email": "john@doe.com",
  "password": "password"
}
```

**Success Response**

```json
{
  "token": "<auth_token>"
}
```

---

## ✅ To-Do APIs (Authenticated)

> All requests must include the token in the header

```
Authorization: Bearer <token>
```

---

### ➕ Create To-Do

**Endpoint**

```
POST /todos
```

**Request Body**

```json
{
  "title": "Buy groceries",
  "description": "Buy milk, eggs, and bread"
}
```

**Success Response**

```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Buy milk, eggs, and bread"
}
```

**Error (Unauthorized)**

```json
{
  "message": "Unauthorized"
}
```

---

### ✏️ Update To-Do

**Endpoint**

```
PUT /todos/{id}
```

**Request Body**

```json
{
  "title": "Buy groceries",
  "description": "Buy milk, eggs, bread, and cheese"
}
```

**Error (Forbidden)**

```json
{
  "message": "Forbidden"
}
```

**Success Response**

```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Buy milk, eggs, bread, and cheese"
}
```

---

### 🗑️ Delete To-Do

**Endpoint**

```
DELETE /todos/{id}
```

**Success Response**

```
HTTP 204 No Content
```

---

### 📄 Get To-Do List (Paginated)

**Endpoint**

```
GET /todos?page=1&limit=10
```

**Success Response**

```json
{
  "data": [
    {
      "id": 1,
      "title": "Buy groceries",
      "description": "Buy milk, eggs, bread"
    },
    {
      "id": 2,
      "title": "Pay bills",
      "description": "Pay electricity and water bills"
    }
  ],
  "page": 1,
  "limit": 10,
  "total": 2
}
```

---

## 🔒 Security

* Passwords are securely hashed
* Token-based authentication
* Authorization checks for update/delete operations
* Proper HTTP status codes (401, 403, 400, 204)

---

## ❗ Error Handling

| Status Code | Meaning               |
| ----------- | --------------------- |
| 400         | Bad Request           |
| 401         | Unauthorized          |
| 403         | Forbidden             |
| 404         | Not Found             |
| 500         | Internal Server Error |

---

## 📦 How to Run (Example)

```bash
# Clone repository
git clone <repo-url>

# Install dependencies
mvn install  # or npm install

# Run application
mvn spring-boot:run  # or npm start
```

---

## 🧠 Learning Outcomes

* Implement authentication & authorization
* Design secure REST APIs
* Work with databases & schemas
* Handle real-world API errors
* Apply pagination & filtering

---
Link: https://roadmap.sh/projects/todo-list-api
## 📜 License

This project is open-source and free to use for learning purposes.

---

### 👨‍💻 Author

**Pradyumna J Kumar**
