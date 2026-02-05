# **E-Commerce Microservices Platform**

A **Java-based e-commerce backend system** built using **Spring Boot microservices**, following industry-standard patterns such as **service discovery**, **centralized configuration**, **API gateway routing**, and **event-driven communication**.

---

## **1️⃣ Tech Stack**

### **Backend**
- **Java 17**
- **Spring Boot**
- **Spring Cloud** (Eureka, Config Server, API Gateway)
- **Spring Security**
- **REST APIs**

### **Messaging**
- **Apache Kafka**
- **RabbitMQ**

### **Database**
- **MySQL / PostgreSQL / MongoDB**

---

## **2️⃣ Microservices Overview**

1. **User Service** – User registration and authentication  
2. **Product Service** – Product catalog management  
3. **Order Service** – Order processing and lifecycle  
4. **Notification Service** – Event-driven notifications  
5. **API Gateway** – Central entry point and routing  
6. **Config Server** – Centralized configuration  
7. **Eureka Server** – Service discovery  

---

## **3️⃣ Architecture Overview**

- **Eureka Server** for service discovery  
- **Spring Cloud Config Server** for centralized configuration  
- **API Gateway** for routing and security  
- **Kafka** for event-driven workflows  
- **RabbitMQ** for asynchronous processing  

---

## **4️⃣ Security**

- **Spring Security** integration  
- **Stateless authentication** design  
- **Role-based access control (RBAC)**  
- Secure APIs via gateway  

---

## **5️⃣ How to Run Locally**

### **Prerequisites**
- Java 17  
- Maven  
- MySQL / PostgreSQL  
- Kafka & RabbitMQ  

### **Steps**
1. Clone the repository
   ```bash
   git clone https://github.com/kajal2328/Ecommerce-Microservices-Platform.git
   ```
2. Navigate to the project
   ```bash
   cd Ecommerce-Microservices-Platform
   ```

---

### **Start services in the following order:**

- Config Server
- Eureka Server
- API Gateway
- Other microservices (User, Product, Order, Notification)

### **Access services**

- All client requests go through the API Gateway
- Services register automatically with Eureka Server

---

## **6️⃣ Project Structure**

```text
Ecommerce-Microservices-Platform
├── Notification/
├── Order/
├── Product/
├── user/
├── gatewayAPI/
├── configserver/
├── eureka/
└── README.md
```
---

## **7️⃣ Future Enhancements**

* **Docker & Docker Compose**
* **Kubernetes deployment**
* **Azure cloud deployment**
* **CI/CD pipelines**


