# MyKino Backend - Spring Boot API

## 📋 Description
Backend API for MyKino cinema management system built with Spring Boot 3.5.4 and PostgreSQL.

## 🚀 Features
- RESTful API for cinema management
- PostgreSQL database integration
- Email notifications
- Swagger/OpenAPI documentation
- Docker support
- Render deployment ready

## 🛠️ Technology Stack
- **Java**: 21 (LTS)
- **Spring Boot**: 3.5.4
- **Database**: PostgreSQL (Production) / H2 (Development)
- **ORM**: Spring Data JPA / Hibernate
- **Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Email**: Spring Mail (SMTP)

## 📁 Project Structure
```
src/
├── main/
│   ├── java/CodersBay/Kino/
│   │   ├── cinema/          # Cinema management
│   │   ├── movie/           # Movie management
│   │   ├── hall/            # Hall management
│   │   ├── seat/            # Seat management
│   │   ├── customer/        # Customer management
│   │   ├── user/            # User management
│   │   ├── bills/           # Billing system
│   │   ├── config/          # Configuration classes
│   │   └── controllerExceptionhandler/  # Error handling
│   └── resources/
│       ├── application.properties           # Development config
│       └── application-prod.properties     # Production config
```

## 🏗️ API Endpoints
- **Cinema**: `/api/cinema`
- **Movies**: `/api/movie`
- **Halls**: `/api/hall`
- **Seats**: `/api/seats`
- **Reservations**: `/api/reservations`
- **Customers**: `/api/customers`
- **Bills**: `/api/bill`
- **Users**: `/api/users`
- **Times**: `/api/times`

## 📚 API Documentation
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🔧 Setup & Installation

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- PostgreSQL (for production)

### Development Setup
1. Clone the repository
2. Navigate to the project directory
3. Run with H2 database (development):
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access H2 Console: `http://localhost:8080/h2-console`

### Production Setup
1. Set up PostgreSQL database
2. Configure environment variables:
   ```bash
   export DATABASE_URL=jdbc:postgresql://host:port/database
   export DATABASE_USERNAME=your_username
   export DATABASE_PASSWORD=your_password
   ```
3. Run with production profile:
   ```bash
   ./mvnw spring-boot:run -Dspring.profiles.active=prod
   ```

## 🐳 Docker Support
Build and run with Docker:
```bash
# Build
./mvnw clean package -DskipTests
docker build -t mykino-backend .

# Run
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host:port/database \
  -e DATABASE_USERNAME=username \
  -e DATABASE_PASSWORD=password \
  mykino-backend
```

## ☁️ Deployment

### Render Deployment
1. Connect your GitHub repository to Render
2. Create a new Web Service
3. Configure build settings:
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/Kino-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`
4. Set environment variables:
   - `DATABASE_URL`: Your PostgreSQL connection string
   - `DATABASE_USERNAME`: Database username
   - `DATABASE_PASSWORD`: Database password
   - `SPRING_PROFILES_ACTIVE`: `prod`

### Manual Build
```bash
# Clean and package
./mvnw clean package -DskipTests

# Run the JAR
java -jar target/Kino-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 🌍 Environment Variables
| Variable | Description | Default |
|----------|-------------|---------|
| `DATABASE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/kinodb` |
| `DATABASE_USERNAME` | Database username | `postgres` |
| `DATABASE_PASSWORD` | Database password | `password` |
| `EMAIL_USERNAME` | SMTP email username | `ettore.pw@gmail.com` |
| `EMAIL_PASSWORD` | SMTP email password | - |
| `PORT` | Server port | `8080` |

## 🔍 Configuration Profiles
- **Development** (`dev`): Uses H2 in-memory database
- **Production** (`prod`): Uses PostgreSQL database

## 📧 Email Configuration
The application supports email notifications using Gmail SMTP:
- Host: smtp.gmail.com
- Port: 587
- TLS enabled

## 🐛 Troubleshooting

### Common Issues
1. **Database Connection Failed**
   - Check PostgreSQL is running
   - Verify connection credentials
   - Ensure database exists

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`
   - Or set environment variable: `PORT=8081`

3. **Build Failures**
   - Ensure Java 21 is installed
   - Run `./mvnw clean install -DskipTests`

## 📞 Support
For issues and questions, please check the API documentation at `/swagger-ui.html` or contact the development team.

## 📄 License
This project is proprietary software developed for MyKino cinema management.
