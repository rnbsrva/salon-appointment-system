# Salon Appointment System

> This project is a online reservation service designed specifically for the beauty  and health industry. It streamlines the appointment booking process, facilitates communication between salons and customers, and enhances overall appointment management.

## Features

- User Registration/Authorization: Secure user accounts with registration and authorization functionality.
- Mail Sending Service: Integrated email service for confirmations, resetting password.
- Chats Between Salon and Customer: Real-time chat for seamless communication between customers and salon staff.
- Appointment Management: Efficient scheduling tools for salons to manage appointments and view schedules.
- Feedback After Appointments for Masters: Customer feedback and ratings for continuous improvement.
- QR Service: Convenient QR code service for sharing salon links.
- Utilized storage provider MinIO for storing salon, master images.

## Tech

- Java - Strong, object-oriented programming language for robust backend development.
- Spring Boot 3.0.4 - Simplifies Java development with production-ready features and a fast deployment model.
- Postgres 15.1 - Open-source relational database for efficient data storage.
- Message Broker Kafka - For event sourcing
- Grafana Prometheus - Monitoring and alerting solution for visualizing system performance metrics.
- Elasticsearch - Search engine for efficient and scalable data retrieval.
- MinIO Storage Provider - object storage for handling images of salons and masters.
- Redis - Used for saving chat messages.
- SMTP for Mail - Standard email protocol for reliable email communication.
- MongoDB - NoSQL database for flexible and scalable data storage.
- Docker - Containerization for easy deployment and scalability.
- Liquibase - Tool for database migration
- WebSockets - Used to enable communication between client and salon
- Maven - Package manager to manipulate with dependencies

>While all services predominantly utilize REST API, the notifications-service stands out by incorporating GraphQL for enhanced query and interaction capabilities.


## Installation

Cloning a Repository

```sh
git clone https://github.com/rnbsrva/salon-appointment-system.git
```

Start Docker containers defined in a docker-compose.yml file in detached mode

```sh
docker compose up -d
```

