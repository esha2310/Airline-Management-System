# Airline Management System

Academic Java/MySQL project for managing customers, flights, bookings, journey details,
cancellations, and boarding-pass information.

## Important note

This repository is a **clean reconstruction for portfolio/GitHub use based on the submitted project report**.
It is not a byte-for-byte recovery of the original lab source code. The report documents the system design,
module responsibilities, Java/MySQL stack, and team contributions, while the exact original source files were
not included in the uploaded report.

## Course context

- Course: CSE222 — Object Oriented Programming Lab
- Institution: Daffodil International University
- Project: Airline Management System
- Academic year: 2024

## Features

- User login
- Home dashboard
- Customer registration
- Flight information
- Flight booking
- Journey history/details
- Ticket cancellation
- Boarding-pass view
- MySQL persistence

## Technologies

- Java 17
- Java Swing
- MySQL
- JDBC
- Maven

## Documented contribution

According to the project report, **Nafisa Tasneem Esha** contributed to:

- `Conn` — database connectivity
- `Home` — main navigation/dashboard
- `Login` — user authentication interface

The report also documents the team's division of work for the remaining modules.

## Project structure

```text
Airline-Management-System/
├── database/
│   └── airline_management.sql
├── report/
│   └── Airline_Management_System_Report.pdf
├── src/main/java/com/airline/management/
│   ├── App.java
│   ├── Conn.java
│   ├── Login.java
│   ├── Home.java
│   ├── AddCustomer.java
│   ├── FlightInfo.java
│   ├── BookFlight.java
│   ├── JourneyDetails.java
│   ├── Cancel.java
│   ├── BoardingPass.java
│   └── SecurityUtil.java
├── pom.xml
└── README.md
```

## Setup

### 1. Create the database

Run:

```sql
database/airline_management.sql
```

in MySQL Workbench or the MySQL command line.

### 2. Configure database access

The application uses these environment variables when present:

```text
DB_URL
DB_USER
DB_PASSWORD
```

Default values:

```text
DB_URL=jdbc:mysql://localhost:3306/airline_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USER=root
DB_PASSWORD=
```

### 3. Run

```bash
mvn clean compile
mvn exec:java
```

Demo login created by the SQL file:

```text
Username: admin
Password: admin123
```

Change the demo password before using the project beyond local testing.

## Portfolio description

**Airline Management System** — Academic Java/MySQL project for customer, flight,
booking, journey, and cancellation management. Contributed to the database connection,
home dashboard, and login modules.

## Academic integrity

This repository should be presented as a reconstructed/cleaned portfolio implementation unless the
original source files are later added and verified.
