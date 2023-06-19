# power-up-Foodcourt
Food court microservice
<br />
<div align="center">
<h3 align="center">🔌🏢 PRAGMA POWER-UP 🍽️</h3>
  <p align="center">
In this challenge, you are tasked with designing the backend of a system that centralizes the services and orders of a restaurant chain with multiple branches across the city. This microservice is responsible for implementing the business logic related to the food court, ensuring seamless operations and delightful dining experiences. 🍔🌯🍕

Get ready to power up the food court and create a robust backend solution that optimizes processes, manages orders, and enhances the overall efficiency of the restaurant chain. Let's embark on this exciting journey! 💪🚀
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites 📋✅

* ☕🔧17 JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* 📦🔧Gradle [https://gradle.org/install/](https://gradle.org/install/)
* 🐬🔧MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools 🛠️🔧
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation 🚀🔧

1. Clone the repository 📥🔧
2. Change directory
   ```sh
   cd power-up-arquetipo-v3
   ```
3. Create a new database in MySQL called powerup1
4. Update the database connection settings ⚙️🔧
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost/powerup1
          username: root
          password: <your-password>
   ```
5. After the tables are created execute src/main/resources/data.sql content to populate the database
6. Open Swagger UI and search the /auth/login endpoint and login with userDni: 123, 🕵️‍♂️🔧password: 1234

<!-- USAGE -->
## Usage 📝🔧

1. Right-click the class FoodCourtApplication and choose Run
2. Open [http://localhost:8091/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests 🧪🔧

- Right-click the test folder and choose Run tests with coverage

## How to Test the API
You can test the API using a tool like Postman or any REST client of your choice.

### Sign In 🔐
- Endpoint: POST /auth/login
- Request body /json:
```json
{
  "mail": "john_doe@gmail.com",
  "password": "password123"
}
```
Example of a successful response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTYzMzI1NDkzMCwiZXhwIjoxNjMzMTQxMzMwfQ.2_5te1vQjDG-yTeBEOsmBAPdQHzCmCtojmYHltWkWbI",
  "tokenType": "Bearer"
}
```
### Add a New Restaurant 🍽️
- Endpoint: POST /api/v1/restaurant
- Request body /json:

```json
{
  "name": "frisby",
  "adress": "cr 45-85",
  "phone": "84674128",
  "urlLogotype": "string",
  "nit": "70491543",
  "idOwner": "1235896"
}
```
Example of a successful response:

```json
{
  "message": "Restaurant created"
}
```
## Get all restaurants 🏬
Endpoint: GET /api/v1/restaurant

Example of a successful response:

```json
[
  {
  "id": 1,
  "name": "Frisby",
  "adress": "cr 45-85",
  "phone": "84674128",
  "urlLogotype": "string",
  "nit": "70491543",
  "idOwner": "1235896"
  },
  {
  "id": 2,
   "name": "Presto",
  "adress": "cr 25-85",
  "phone": "7754128",
  "urlLogotype": "http_presto",
  "nit": "5896543",
  "idOwner": "236598745"
  },
    {
  "id": 3,
   "name": "Dominos",
  "adress": "cr 75-85",
  "phone": "4584128",
  "urlLogotype": "http//Logo/dominos.com",
  "nit": "4589624",
  "idOwner": "58933585"
  }
]
```
## Get all restaurants by page 📜🔢
- Endpoint: GET /api/v1/restaurant/page/{page}/size/{size}
Example of URL: /api/v1/restaurant/page/1/size/2

Example of a successful response:
```json
[
   {
  "id": 1,
  "name": "Frisby",
  "adress": "cr 45-85",
  "phone": "84674128",
  "urlLogotype": "string",
  "nit": "70491543",
  "idOwner": "1235896"
  },
  {
  "id": 2,
   "name": "Presto",
  "adress": "cr 25-85",
  "phone": "7754128",
  "urlLogotype": "http_presto",
  "nit": "5896543",
  "idOwner": "236598745"
  }
]
```
## Add new order ➕🛒
- Endpoint: POST /api/v1/order
- Request body /json:
```json
{
  "restaurantId": 1,
  "customerId": 1,
  "items": [
    {
      "itemId": 1,
      "quantity": 2
    },
    {
      "itemId": 2,
      "quantity": 1
    }
  ]
}
```
Example of a successful response:
```json
{
  "orderId": 1,
  "message": "Orden creada"
}
```
## Take an order and update the status ✅🛒
- Endpoint: PUT /api/v1/order/takeOrderAndUpdateStatus/{idOrder}/status/{state}
- Example of URL: /api/v1/order/takeOrderAndUpdateStatus/1/status/READY

Example of a successful response:

```json
{
  "orderId": 1,
  "message": "Order updated"
}
```
## Notify that the order is ready 📣✅
- Endpoint: PUT /api/v1/order/notifyOrderReady/{idOrder}
- Example  de URL: /api/v1/order/notifyOrderReady/1

Example of a successful response:

```json
{
  "orderId": 1,
  "message": "Orden notificada como lista"
}
```
## deliver an order 🚚✅
- Endpoint: PUT /api/v1/order/deliverOrder/{idOrder}/pin/{pin}
- Example of URL: /api/v1/order/deliverOrder/1/pin/1234

Example of a successful response:
```json
{
  "orderId": 1,
  "message": "Orden entregada"
}
```
## Cancel order ❌🛒
- Endpoint: PUT /api/v1/order/cancelOrder/{idOrder}
- Example de URL: /api/v1/order/cancelOrder/1

Example of a successful response:
```json
{
  "orderId": 1,
  "message": "Orden cancelada"
}
```
## Get Logs by order ID 🔍📃
- Endpoint: GET /api/v1/order/logs/{idOrder}
- Example of URL: /api/v1/order/logs/1

Example of a successful response:
```json
[
  {
    "duration_Pending_to_Preparing": "34 min",
    "duration_Preparing_to_Ready": "15 min",
  },
  {
    "duration_Pending_to_Preparing": "34 min",
    "duration_Preparing_to_Ready": "15 min",
    "duration_Readyto_Delivered": "10 MIN"
  }
]
```
## Get orders by status and page 📜🔢🛒
- Endpoint: GET /api/v1/order/getOrdersByState/page/{page}/size/{size}/state/{state}
- Example of URL: /api/v1/order/getOrdersByState/page/1/size/10/state/in_progress

Example of a successful response:
```json
[
  {
    "orderId": 1,
    "restaurantId": 1,
    "customerId": 1,
    "state": "PENDING",
    "items": [
      {
        "itemId": 1,
        "quantity": 2
      },
      {
        "itemId": 2,
        "quantity": 1
      }
    ]
  },
  {
    "orderId": 2,
    "restaurantId": 1,
    "customerId": 2,
    "state": "in_progress",
    "items": [
      {
        "itemId": 3,
        "quantity": 3
      }
    ]
  }
]
```
### Add a new dish 🍕➕

- Endpoint: POST /api/v1/dish
- Request body /json:
```json
{
  "restaurantId": 1,
  "name": "Pizza Margherita",
  "description": "Deliciosa pizza margherita con salsa de tomate, mozzarella y albahaca.",
  "price": 12.99
}
```
Example of a successful response:
```json
{
  "dishId": 1,
  "message": "Dish added successfully"
}
```
## Enable or disable a dish ✅❌
- Endpoint: PUT /api/v1/dish/{id}/activate/{enableDisable}
Example of URL: /api/v1/dish/1/activate/true

Example of a successful response:
```json
{
  "dishId": 1,
  "message": "Plato habilitado"
}
```
## update a dish 🔄🍽️
- Endpoint: PATCH /api/v1/dish/
- Request body /json:
```json
{
  "dishId": 1,
  "name": "Pizza Margherita",
  "description": "Deliciosa pizza margherita con salsa de tomate, mozzarella y albahaca.",
  "price": 14.99
}
```
Example of a successful response:
```json
{
  "dishId": 1,
  "message": "Plato actualizado exitosamente"
}
```
## Get all dishes by restaurant or category 🍽️📜🔢
- Endpoint: GET /api/v1/dish/{idRestaurant}/page/{page}/size/{size}
- Example of URL: /api/v1/dish/1/page/0/size/2

Example of a successful response:
```json
[
  {
    "dishId": 1,
    "restaurantId": 1,
    "name": "Pizza Margherita",
    "description": "Deliciosa pizza margherita con salsa de tomate, mozzarella y albahaca.",
    "price": 12.99,
    "isActive": true
  },
  {
    "dishId": 2,
    "restaurantId": 1,
    "name": "Hamburguesa Clásica",
    "description": "Hamburguesa de carne con lechuga, tomate, cebolla y queso.",
    "price": 10.99,
    "isActive": true
  }
]
```
