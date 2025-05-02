# IWS Manager API

![Java CI](https://github.com/Piramide-Informatik/iws-manager-api/actions/workflows/ci.yml/badge.svg)
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=Piramide-Informatik_iws-manager-api)

Spring Boot backend API with Java 21, PostgreSQL 16.6, and Gradle 8.12. Features CI/CD via GitHub Actions with SonarCloud integration.

## 🛠 Tech Stack
- **Java**: 21.0.4 (Oracle)
- **Spring Boot**: 3.4.3
- **PostgreSQL**: 16.6
- **Gradle**: 8.                12

## 🚀 Quick Start

### Prerequisites
```bash
java -version  # Verify Java 21
gradle -v      # Verify Gradle 8.12
psql --version # Verify PostgreSQL 16.6
```

## 1. Database Setup

### Option A: Local PostgreSQL
```bash
# Create database (adjust credentials if needed)
createdb -U postgres iwsmanager_bd

# Verify creation
psql -U postgres -l | grep iwsmanager_bd

# Using psql client
psql -h localhost -U admin -d iwsmanager_bd -W

# Using JDBC URL (test from your app)
jdbc:postgresql://localhost:5432/iwsmanager_bd
```
## 2. Configuration

### Environment Setup

#### For Windows (`env.bat`):
Create a file named `env.bat` with:
```batch
@echo off
set DB_URL=jdbc:postgresql://localhost:5432/iwsmanager_bd
set DB_USERNAME=admin
set DB_PASSWORD=1234
```
#### For Linux/MacOS (`env.sh`):
Create a file named `env.sh` with:
```batch
#!/bin/bash
export DB_URL="jdbc:postgresql://localhost:5432/iwsmanager_bd"
export DB_USERNAME="admin"
export DB_PASSWORD="1234"
```