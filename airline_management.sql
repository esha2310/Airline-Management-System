CREATE DATABASE IF NOT EXISTS airline_management;
USE airline_management;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash CHAR(64) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'ADMIN'
);

CREATE TABLE IF NOT EXISTS customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nationality VARCHAR(60),
    nid VARCHAR(40) UNIQUE,
    address VARCHAR(255),
    gender VARCHAR(20),
    phone VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flight_code VARCHAR(30) NOT NULL UNIQUE,
    flight_name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    flight_date DATE NOT NULL,
    available_seats INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    flight_id INT NOT NULL,
    booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancelled_at TIMESTAMP NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'BOOKED',
    CONSTRAINT fk_booking_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_booking_flight
        FOREIGN KEY (flight_id) REFERENCES flights(id)
);

INSERT INTO users(username, password_hash, role)
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO flights(flight_code, flight_name, source, destination, flight_date, available_seats)
VALUES
('BG101', 'Bangladesh Airline 101', 'Dhaka', 'Chattogram', '2026-11-01', 120),
('BG205', 'Bangladesh Airline 205', 'Dhaka', 'Sylhet', '2026-11-05', 90),
('BG310', 'Bangladesh Airline 310', 'Dhaka', 'Cox''s Bazar', '2026-11-10', 100)
ON DUPLICATE KEY UPDATE flight_name=VALUES(flight_name);
