# 🌿 GreenPath - Sustainable Route Optimization Platform

GreenPath is a full-stack application designed to promote **sustainable travel** by calculating optimized routes between locations and estimating **carbon emissions** based on different transport modes. This platform integrates Java Spring Boot for backend APIs and Angular for the frontend.

---

## 🚀 Tech Stack

| Layer        | Technology              |
|--------------|-------------------------|
| Backend      | Java 17, Spring Boot 3  |
| Frontend     | Angular 17 (SCSS, RxJS) |
| Database     | MYSQL             |
| Dev Tools    | Maven, Postman, VSCode  |
| Deployment   | Docker (Planned), AWS (Planned) |

---

## 📦 Project Structure

greenpath/
│
├── server/ # Spring Boot backend
│ ├── controller/ # REST API controllers
│ ├── service/ # Business logic layer
│ ├── dto/ # Data transfer objects
│ ├── entity/ # JPA entities
│ └── repository/ # Spring Data JPA
│
└── client/ # Angular frontend
├── src/app/
│ ├── components/ # UI components
│ ├── services/ # API services (e.g., route.service.ts)
│ └── environment/ # API URL configs

## 🌐 Backend API Overview

### Base URL
http://localhost:8085/api/v1


### Endpoints

| Method | Endpoint                        | Description                            |
|--------|----------------------------------|----------------------------------------|
| GET    | `/routes/best`                  | Get shortest route between locations   |
| GET    | `/routes/best-with-emission`    | Get route with carbon emission stats   |
| GET    | `/locations`                    | List all supported locations           |
| POST   | `/locations`                    | Add a new location                     |

### Sample Request

GET /routes/best-with-emission?source=Mumbai Airport&destination=Gateway of India&mode=bus


### Sample Response

```json
{
  "totalDistance": 18.93,
  "route": [
    "Mumbai Airport",
    "Gateway of India"
  ],
  "carbonEmission": 3.97
}

💡 Features
🧭 Shortest route finder using Dijkstra's Algorithm

🌱 Carbon emission calculation based on transport mode

📍 Support for dynamic location data (via DB)

🔐 Modular code structure for adding new services easily

📊 Future-ready for analytics (e.g., most eco-friendly routes)

🖥️ Frontend (Angular Setup)

npm install -g @angular/cli
ng new client --routing --style=scss
cd client
npm install
ng serve --open

Make sure to update environment.ts:

export const environment = {
  production: false,
  apiUrl: 'http://localhost:8085/api/v1'
};

⚙️ Run Backend

cd server
./mvnw spring-boot:run

📌 Future Enhancements
Add user authentication & rate limiting

Dockerize frontend/backend

Integrate with Google Maps for real-time directions

ML-based emission prediction models

CI/CD Pipeline using GitHub Actions or Jenkins

🧠 Contributing
Pull requests are welcome! For major changes, please open an issue first.

👨‍💻 Authors
Bhavesh M. (@bhavesh07-ms)



