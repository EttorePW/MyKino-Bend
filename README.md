# 🎬 MyKino - Backend API

API REST para el sistema de gestión de cines MyKino, desarrollada con Spring Boot y Java. Proporciona todos los endpoints necesarios para la gestión completa de cines, películas, salas, reservas y facturación.

## 🚀 Características Principales

### 🎯 Funcionalidades Core
- **🎭 Gestión de Películas**: CRUD completo con información detallada
- **🏢 Gestión de Cines**: Administración de múltiples ubicaciones
- **🏛️ Gestión de Salas**: Configuración de salas y capacidades
- **🪑 Sistema de Reservas**: Reserva de asientos en tiempo real
- **💰 Facturación**: Sistema completo de tickets y pagos
- **👥 Gestión de Usuarios**: Autenticación y autorización
- **📧 Notificaciones**: Envío de confirmaciones por email

### 📊 Características Técnicas
- **🔄 API REST**: Endpoints RESTful bien estructurados
- **📖 Documentación**: Swagger UI integrado
- **🗄️ Base de datos**: Soporte para H2 (desarrollo) y PostgreSQL (producción)
- **🔒 Seguridad**: Spring Security (comentado, listo para activar)
- **✉️ Email**: Integración con Gmail SMTP
- **🧪 Testing**: Framework de testing incluido

## 🛠️ Tecnologías Utilizadas

- **Framework**: Spring Boot 3.5.4
- **Lenguaje**: Java 21
- **Build Tool**: Maven
- **ORM**: Spring Data JPA + Hibernate
- **Base de Datos**: H2 (desarrollo) / PostgreSQL (producción)
- **Documentación**: Swagger/OpenAPI 3
- **Email**: Spring Mail
- **Testing**: JUnit + Spring Boot Test

## 📋 Requisitos Previos

- **Java**: JDK 21 o superior
- **Maven**: 3.8+ (o usar el wrapper incluido)
- **PostgreSQL**: Para producción (opcional para desarrollo)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <tu-repositorio>
cd MyKino-Bend
```

### 2. Compilar el proyecto
```bash
# Usar Maven wrapper (recomendado)
./mvnw clean compile

# O si tienes Maven instalado
mvn clean compile
```

### 3. Ejecutar en modo desarrollo
```bash
# Con Maven wrapper
./mvnw spring-boot:run

# O con Maven
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

## 📦 Scripts Disponibles

```bash
# Compilar
./mvnw clean compile

# Ejecutar tests
./mvnw test

# Empaquetar (JAR)
./mvnw clean package

# Ejecutar aplicación
./mvnw spring-boot:run

# Limpiar proyecto
./mvnw clean
```

## 🗄️ Configuración de Base de Datos

### Desarrollo (H2)
```properties
# En application.properties (por defecto)
spring.datasource.url=jdbc:h2:file:./data/kinoDB
spring.h2.console.enabled=true
# Consola H2: http://localhost:8080/h2-console
```

### Producción (PostgreSQL)
```properties
# En application-prod.properties
spring.profiles.active=prod
spring.datasource.url=${DATABASE_URL}
spring.datasource.driver-class-name=org.postgresql.Driver
```

## 🔧 Configuración para Deploy en Render

### Variables de Entorno Necesarias:
```env
# Base de Datos (Render las proporciona automáticamente)
DATABASE_URL=postgresql://...
DATABASE_USERNAME=usuario
DATABASE_PASSWORD=contraseña

# Configuración del servidor
PORT=8080

# Email
EMAIL_USERNAME=tu-email@gmail.com
EMAIL_PASSWORD=tu-app-password

# Seguridad
ADMIN_USERNAME=admin
ADMIN_PASSWORD=tu-contraseña-segura

# Frontend URL para CORS
FRONTEND_URL=https://tu-frontend.netlify.app
```

### Build Command para Render:
```bash
./mvnw clean package -DskipTests
```

### Start Command para Render:
```bash
java -Dspring.profiles.active=prod -jar target/Kino-0.0.1-SNAPSHOT.jar
```

## 📖 Documentación de la API

Una vez ejecutando, la documentación interactiva está disponible en:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### Principales Endpoints:

```
# Películas
GET    /api/movies          # Listar películas
POST   /api/movies          # Crear película
PUT    /api/movies/{id}     # Actualizar película
DELETE /api/movies/{id}     # Eliminar película

# Cines
GET    /api/cinemas         # Listar cines
POST   /api/cinemas         # Crear cine

# Salas
GET    /api/halls           # Listar salas
POST   /api/halls           # Crear sala

# Reservas
GET    /api/reservations    # Listar reservas
POST   /api/reservations    # Crear reserva

# Usuarios
GET    /api/users           # Listar usuarios
POST   /api/users           # Registrar usuario
```

## 🔒 Seguridad

El proyecto incluye Spring Security configurado pero comentado. Para activarlo:

1. Descomenta las dependencias de Security y JWT en `pom.xml`
2. Configura los beans de seguridad
3. Implementa autenticación JWT

```xml
<!-- Descomentar en pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## 🧪 Testing

```bash
# Ejecutar todos los tests
./mvnw test

# Ejecutar tests con cobertura
./mvnw test jacoco:report

# Tests de integración
./mvnw verify
```

## 📧 Configuración de Email

Para el envío de emails, configura:

1. **Gmail App Password**: Genera una contraseña de aplicación
2. **Variables de entorno**: Configura EMAIL_USERNAME y EMAIL_PASSWORD
3. **SMTP**: Ya configurado para Gmail

## 🌐 CORS

El CORS está configurado para permitir requests del frontend. Ajustar en `application-prod.properties`:

```properties
management.endpoints.web.cors.allowed-origins=${FRONTEND_URL}
```

## 🐛 Troubleshooting

### Problemas Comunes:

1. **Puerto en uso**: Cambiar puerto en `application.properties`
2. **Java Version**: Verificar que usas Java 21
3. **Database**: Verificar conexión a PostgreSQL en producción
4. **CORS**: Verificar configuración para tu frontend

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📝 Convenciones de Código

- Usar **Lombok** para reducir boilerplate
- **RESTful** naming para endpoints
- **DTOs** para transferencia de datos
- **Repository Pattern** para acceso a datos
- **Service Layer** para lógica de negocio

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.

## 👨‍💻 Autor

**CodersBay**
- Proyecto Kino

---

⭐ API documentada y lista para producción!
