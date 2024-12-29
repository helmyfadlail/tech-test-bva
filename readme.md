# Member Management Application

## Overview

This application is a Member Management System built using **React TypeScript** for the frontend and **Spring Boot** for the backend. It allows users to perform CRUD operations on members after logging in or registering. The system ensures secure access through user authentication.

## Features

### Authentication

- **Register**: New users can create an account.
- **Login**: Existing users can log in to access the application.
- **JWT Authentication**: Secure communication between the frontend and backend.

### Member Management

- **Create**: Add new members to the system.
- **Read**: View a list of all members.
- **Update**: Modify member details.
- **Delete**: Remove members from the system.

### Docker Integration

- **Dockerized Frontend and Backend**: Both the frontend and backend are containerized for seamless deployment.
- **Docker Compose**: A `docker-compose.yml` file is provided to orchestrate multi-container deployment.

## Technology Stack

### Frontend

- **React TypeScript**
- **React Router** for navigation
- **Axios** for API communication
- **Tailwind CSS** for styling

### Backend

- **Spring Boot**
- **Spring Security** for authentication and authorization
- **Hibernate** for ORM (Object-Relational Mapping)
- **MySQL** as the database
- **Lombok** to reduce boilerplate code
- **Maven** for dependency management

## Project Structure

### Frontend

```
src/
├── components/          # Reusable UI components
├── pages/               # Application pages (Login, Register, Dashboard, etc.)
├── utils/               # Helpers function
├── hooks/               # Custom hooks
├── routes/              # Protected routes
├── App.tsx              # Main application file
├── index.tsx            # Entry point
└── types/               # Types for describe the type generic props, data, or else
```

### Backend

```
src/main/java/com/example/membermanagement/
├── controller/          # REST controllers
├── service/             # Business logic
├── repository/          # Data access layer
├── Entity/              # Entity classes
├── dto/                 # Data request and response
├── resolver/            # Authentication logic
├── util/                # Helper function
├── config/              # Security and other configurations
└── MemberManagementApplication.java # Main Spring Boot application
```

## Installation and Setup

### Prerequisites

- **Node.js** and **npm** installed
- **Java 23** or later
- **MySQL** server running
- **Docker** and **Docker Compose** installed

### Backend Setup

1. Clone the repository.
2. Navigate to the backend directory:
   ```bash
   cd your-app-name/backend
   ```
3. Update the `application.properties` file with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your-database-name
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Build and run the backend:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd your-app-name/frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

### Docker Setup

1. Ensure Docker and Docker Compose are installed on your system.
2. Build and start the containers:
   ```bash
   docker-compose up --build
   ```
3. Access the application:
   - Frontend: `http://localhost:5173`
   - Backend: `http://localhost:8080`

## API Endpoints

### Authentication

| Method | Endpoint             | Description         |
| ------ | -------------------- | ------------------- |
| POST   | `/api/auth/register` | Register a new user |
| POST   | `/api/auth/login`    | Authenticate user   |
| DELETE | `/api/auth/logout`   | Authenticate user   |

### Member Management

| Method | Endpoint            | Description           |
| ------ | ------------------- | --------------------- |
| GET    | `/api/members`      | Get all members       |
| GET    | `/api/members/{id}` | Get a member by id    |
| POST   | `/api/members`      | Create a new member   |
| PUT    | `/api/members/{id}` | Update a member by ID |
| DELETE | `/api/members/{id}` | Delete a member by ID |

**Note:** For detailed API documentation, visit the backend at `/docs`.

## Usage

1. **Login/Register**: Start by creating an account or logging in.
2. **Dashboard**: Access the member management dashboard after authentication.
3. **Manage Members**:
   - Add new members.
   - View, edit, or delete existing members.

## Contact

For any queries, contact:

- **Email**: helmyfadlail.5@gmail.com
- **GitHub**: [helmyfadlail](https://github.com/helmyfadlail)
