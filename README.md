# 🎮 Video Game Store API

A RESTful E-Commerce API built with **Spring Boot**, **MySQL**, and **JDBC DAO pattern**. This project was completed as a multi-phase capstone and demonstrates backend API design, database integration, authentication, and shopping cart functionality.

---

## 🧩 Project Overview

This API powers a video game store and supports:

* Browsing categories and products
* Filtering products by category, price range, and subcategory
* Admin management of categories and products
* User-specific shopping carts

The project was completed in three phases, each building on the previous one.

---

## ✅ Phase 1 – Categories & Products (Read Operations)

### 🔹 Features Implemented

* Retrieve all categories
* Retrieve a single category by ID
* Retrieve all products
* Retrieve a product by ID
* Retrieve products by category

### 🔹 Technologies Used

* Spring Boot REST Controllers
* DAO pattern with JDBC
* MySQL database

### 🔹 Sample Endpoints

```http
GET /categories
GET /categories/{id}
GET /categories/{id}/products
GET /products
GET /products/{id}
```

---

## ✅ Phase 2 – Admin CRUD & Advanced Product Search

### 🔹 Features Implemented

* Admin-only category management (Create, Update, Delete)
* Admin-only product management
* Advanced product search with optional filters

### 🔹 Product Search Filters

* Category ID
* Minimum price
* Maximum price
* Subcategory

All filters can be combined or used individually.

### 🔹 Sample Search Requests

```http
GET /products?cat=2
GET /products?minPrice=25&maxPrice=75
GET /products?cat=1&subCategory=RPG
```

### 🔹 Security

* Spring Security role-based authorization
* Admin-only endpoints protected with `ROLE_ADMIN`

---

## ✅ Phase 3 – Shopping Cart (Authenticated Users)

### 🔹 Features Implemented

* User-specific shopping carts
* Add product to cart
* Update product quantity
* View current cart
* Clear cart

### 🔹 Key Design Decisions

* Cart is tied to the authenticated user
* No userId is passed from the client
* Shopping cart data is persisted in the database
* Clean separation of controller and DAO logic

### 🔹 Sample Cart Endpoints

```http
GET /cart
POST /cart/products/{productId}
PUT /cart/products/{productId}
DELETE /cart
```

### 🔹 Example Request Body (Update Quantity)

```json
{
  "quantity": 3
}
```

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **JDBC (DAO Pattern)**
* **MySQL**
* **Insomnia** (API Testing)

---

## 📸 API Test Screenshots (Insomnia)

> The following screenshots demonstrate successful testing of all required endpoints using **Insomnia**.

### 🔹 Phase 1 – Categories & Products

* [ ] Get all categories
* [ ] Get category by ID
* [ ] Get products by category
* [ ] Get product by ID

*(![getAllCategories_Insomnia](https://github.com/user-attachments/assets/465a6fb6-d554-4700-aaa1-3cff8c56de2e)*
*![getAllProducts_Insomnia](https://github.com/user-attachments/assets/30804024-19a4-4a23-bbf8-ef4185b5b2d6)*

---

### 🔹 Phase 2 – Admin CRUD & Product Search

* [ ] Create category (ADMIN)
* [ ] Update category (ADMIN)
* [ ] Delete category (ADMIN)
* [ ] Product search with filters

*![addNewProduct_Insomnia](https://github.com/user-attachments/assets/c5a80804-ee99-448e-b0c7-e394e6c7c9e1)*

---

### 🔹 Phase 3 – Shopping Cart (Authenticated User)

* [ ] View empty cart
* [ ] Add product to cart
* [ ] Update product quantity
* [ ] View updated cart
* [ ] Clear cart

*![shoppingCartWithAutheUser_Insomnia](https://github.com/user-attachments/assets/82699476-e566-4b95-82a1-8da8d8f71dfe)*

---

## 🧪 Testing

All endpoints were tested using **Insomnia**:

* Authentication required for cart and admin actions
* Public access allowed for browsing products and categories

---

## 🚀 How to Run

1. Clone the repository
2. Configure your MySQL database
3. Update `application.properties`
4. Run the Spring Boot application
5. Test endpoints using Insomnia or Postman

---

## 👤 Author

**Andy A**
YearUp Software Development Capstone Project

---

## 📌 Notes

This project demonstrates:

* Real-world REST API structure
* Secure user-based data access
* Clean JDBC-based persistence
* Scalable backend architecture
