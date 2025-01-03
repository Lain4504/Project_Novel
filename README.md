# Project_Novel

**Project_Novel** is a comprehensive web application designed for reading novels, supporting premium features, user
posting, author management, and statistical analysis. The project follows a **microservices architecture** for
scalability, modularity, and maintainability.

---

## Development Environment

- **MySQL 8.0** - Relational database for structured data.
- **MongoDB** - NoSQL database for flexible and unstructured data.
- **Elasticsearch 8.2.0** - Full-text search engine for efficient search functionality.
- **JDK 21** - Java Development Kit for backend development.
- **Maven 3.8** - Build and dependency management tool.
- **Node.js 18** - Runtime environment for server-side JavaScript.

---

## IDEs

- **IntelliJ IDEA** - Backend development.
- **Visual Studio Code** - Frontend development.

---

## Backend Technology Stack

| Technology        | Version | Description                                                                                                    | 
|-------------------|---------|----------------------------------------------------------------------------------------------------------------|
| **Spring Boot**   | 3.0.0   | Container + MVC Framework                                                                                      | 
| **JWT**           | 0.11.5  | JSON Web Token support for authentication                                                                      |
| **Lombok**        | 1.18.24 | Simplifies object encapsulation and reduces boilerplate code                                                   |
| **MySQL**         | 8.0     | Relational database for structured data                                                                        | 
| **Elasticsearch** | 8.2.0   | Full-text search engine for fast and efficient queries                                                         |
| **Docker**        | -       | Application containerization for deployment                                                                    | 
| **MongoDB**       | -       | NoSQL database for storing unstructured data                                                                   | 
| **Kafka**         | 3.0.0   | Distributed event streaming platform for building real-time data pipelines and streaming applications          |
| **OAuth2**        | 3.0.0   | Authorization framework that enables applications to obtain limited access to user accounts on an HTTP service |

### Microservices Architecture Features:

- **Independent Services**: Each functionality (authentication, search, statistics, etc.) is developed and deployed
  independently.
- **Scalability**: Services can be scaled based on load.
- **Resilience**: Failures in one service do not impact others.

---

## Frontend Technology Stack

| Technology              | Version | Description                                                                                                                        |
|-------------------------|---------|------------------------------------------------------------------------------------------------------------------------------------|
| **Vue.js**              | 3.2.13  | Progressive JavaScript framework for building user interfaces                                                                      |
| **Vue Router**          | 4.0.15  | Official routing library for Vue.js                                                                                                | 
| **Axios**               | 0.27.2  | Promise-based HTTP client for API communication                                                                                    |
| **TailwindCSS**         | 2.2.0   | Utility-first CSS framework for styling                                                                                            |
| **Vite**                | 2.7.13  | Build tool that aims to provide a faster and leaner development experience for modern web projects                                 |
| **FontAwesome**         | 6.0.0   | Icon library for scalable vector icons                                                                                             |
| **Tiptap**              | 2.0.0   | Headless, framework-agnostic text editor                                                                                           |
| **vue3-draggable-next** |         | A Vue 3 component for draggable elements, providing a simple and flexible way to create drag-and-drop interfaces.                  |
| **vuex**                |         | A state management pattern + library for Vue.js applications, enabling centralized state management with a single source of truth. |
| **Ant Design Vue**      | 4.2.6   | A Vue 3 UI library based on Ant Design, providing a set of high-quality components for building rich user interfaces.              |

## Key Features

- **Novel Reading**: Supports free and premium chapters with a seamless reading experience.
- **User Management**: Registration, login, and role-based access control (Reader, Author, Admin).
- **Author Dashboard**: Tools for authors to manage their novels, chapters, and earnings.
- **Content Management**: Users can post and manage their content.
- **Advanced Search**: Elasticsearch-powered search for novels, authors, and chapters.
- **Analytics and Statistics**: Admins can view user engagement, top novels, and revenue trends.
- **Microservices**: Each feature is implemented as an independent service for better scalability.

---

## Setup Instructions

1. **Clone Repository**:
   ```bash
   git clone https://github.com/yourusername/project_novel.git
   cd project_novel

---

## Backend Setup

1. Install **JDK 21**, **Maven 3.8**, and **Docker**.
2. Configure database credentials in `application.yaml`.
3. Build and run services:
   ```bash
   mvn clean install
   docker-compose up

---

## Frontend Setup

1. Install **Node.js 18** and dependencies:
   ```bash
   cd frontend
   npm install
   npm run dev

---

## Access Application

- Visit [http://localhost:8888](http://localhost:8888) for the backend.
- Visit [http://localhost:5173](http://localhost:5173) and [http://localhost:5174](http://localhost:5174) for the
  frontend.

---

# Current Features Implemented

## Mobile App

- [ ] Kotlin app

## Web Application

- Base UI : 100%
- Demo:
  ![auth.png](frontend/data/auth.png)
  ![home1.png](frontend/data/home1.png)
  ![home2.png](frontend/data/home2.png)
  ![novel1.png](frontend/data/novel1.png)
  ![novel2.png](frontend/data/novel2.png)
  ![chapter1.png](frontend/data/chapter1.png)
  ![bookmark.png](frontend/data/bookmark.png)
  ![account.png](frontend/data/account.png)
  ![dashboard.png](frontend/data/dashboard.png)
  ![post.png](frontend/data/post.png)
  ![novelmanagement.png](frontend/data/novelmanagement.png)
## Social Features

- [x] User profiles
- [x] Follow system
- [x] Commenting system
- [x] Likes and ratings
- [x] Activity feed

## Authentication & Email

- [x] User authentication system
- [x] Send email functionality
- [x] OAuth2 integration

## Reading & Writing Novel & Paid Content

- [x] Read novel feature
- [x] Write novel service
- [ ] Pay-per-chapter system
- [x] Novel categories
- [x] Upload Image
- [x] Setup for reading novels settings

## Posting

- [x] Post service
- [x] Post management system
- [x] Post categories

## Search

- [x] Search system
- [x] Advanced search system(by author, by description, by category, by status)

## Author Dashboard

- [x] Author dashboard
- [ ] Earnings and revenue system
- [ ] Author statistics
- [x] Author profile
- [x] Author settings
- [x] Author notifications
- [ ] Author payment system

## Admin Dashboard

- [x] Admin dashboard
- [x] User management
- [x] Novel management
- [x] Post management
- [ ] Revenue management
- [ ] Analytics and statistics
- [ ] Payment system
- [x] Notification system
- [x] Settings
- [x] User roles
- [x] User permissions

## Notifications

- [x] Notification system
- [x] Email notifications
- [x] Push notifications
- [x] Notification settings
- [x] Notification management



