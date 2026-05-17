# Pádel Point

Aplicación web para la gestión de reservas de pistas de pádel desarrollada como proyecto final de Programación de Aplicaciones Telemáticas (PAT).

Proyecto realizado por:
- Cristina Molina
- Carla Rodríguez
- Paula Díez
- Patricia Urquijo

---

## Descripción

Pádel Point es una aplicación web completa para la gestión de reservas de pistas de pádel.

La aplicación permite:
- Consultar pistas disponibles
- Consultar disponibilidad por fecha
- Registrarse e iniciar sesión
- Crear reservas
- Gestionar reservas personales
- Consultar perfil de usuario
- Administrar pistas y reservas mediante rol ADMIN

El proyecto integra:
- Frontend desarrollado con HTML, CSS y JavaScript
- Backend REST desarrollado con Spring Boot
- Comunicación cliente-servidor mediante Fetch API
- Autenticación y autorización con Spring Security
- Gestión de roles USER y ADMIN
- Persistencia mediante entidades y repositorios
- Testing manual con Postman
- Despliegue del frontend mediante GitHub Pages

---

## Tecnologías utilizadas

### Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Maven
- JUnit

### Frontend
- HTML5
- CSS3
- JavaScript
- Fetch API
- DOM API
- LocalStorage

### Herramientas
- IntelliJ IDEA
- Visual Studio Code
- Git
- GitHub
- GitHub Pages
- Postman

---

## Estructura del proyecto

Durante el desarrollo se eliminó la carpeta `/frontend` y todos los archivos HTML, CSS y JS se movieron a la raíz del proyecto para permitir que GitHub Pages detectase correctamente `index.html`.

```
/
├── index.html
├── login.html
├── register.html
├── courts-public.html
├── availability-public.html
├── user-home.html
├── courts-user.html
├── availability-user.html
├── reservation.html
├── my-reservations.html
├── profile.html
├── admin-home.html
├── admin-courts.html
├── admin-reservations.html
│
├── styles.css
│
├── login.js
├── registro.js
├── reserva.js
├── availability-user.js
├── admin.js
├── user-home.js
├── verReserva.js
│
├── images/
│   └── capturas/
│
├── backend/
│   ├── controllers/
│   ├── modelos/
│   ├── repos/
│   ├── security/
│   └── ProyectoPatApplication.java
│
└── README.md
```

---

## Roles de usuario

### Usuario no autenticado
Puede:
- Acceder a la página principal
- Consultar pistas
- Consultar disponibilidad
- Registrarse
- Iniciar sesión

### Usuario autenticado
Puede:
- Acceder a su zona privada
- Consultar pistas
- Ver disponibilidad
- Crear reservas
- Cancelar reservas
- Consultar sus reservas
- Gestionar su perfil

### Administrador
Puede:
- Acceder al dashboard de administración
- Consultar todas las reservas
- Filtrar reservas
- Activar/desactivar pistas
- Gestionar el sistema

---

## Frontend

El frontend está desarrollado completamente con HTML, CSS y JavaScript. La aplicación se diseñó con:
- Separación clara por roles
- Navegación coherente
- Diseño responsive
- Integración preparada para backend REST
- Componentes reutilizables
- Estilo visual homogéneo

### Páginas públicas
- `index.html`
- `login.html`
- `register.html`
- `courts-public.html`
- `availability-public.html`

### Páginas privadas de usuario
- `user-home.html`
- `courts-user.html`
- `availability-user.html`
- `reservation.html`
- `my-reservations.html`
- `profile.html`

### Páginas privadas de administrador
- `admin-home.html`
- `admin-courts.html`
- `admin-reservations.html`

---

## JavaScript

La lógica cliente se implementó mediante JavaScript y Fetch API.

### Funcionalidades implementadas
- Registro de usuarios
- Inicio de sesión
- Logout
- Gestión de roles
- Guardado de sesión con localStorage
- Consulta dinámica de disponibilidad
- Creación de reservas
- Cancelación de reservas
- Protección de vistas ADMIN
- Renderizado dinámico de tablas y tarjetas
- Integración frontend-backend

### Archivos JavaScript principales

#### `login.js`
- Inicio de sesión
- Peticiones fetch de login
- Guardado de datos de sesión
- Redirección según rol

#### `registro.js`
- Registro de usuario
- Conversión FormData → JSON
- Validaciones frontend
- Comunicación con backend

#### `reserva.js`
- Creación de reservas
- Envío de formularios
- Basic Auth
- Gestión de errores

#### `availability-user.js`
- Consulta de disponibilidad
- Renderizado dinámico de franjas horarias
- Consulta por fecha y pista

#### `verReserva.js`
- Carga de reservas del usuario
- Cancelación de reservas
- Estado ACTIVA/CANCELADA

#### `admin.js`
- Protección de vistas admin
- Activar/desactivar pistas
- Consultar reservas
- Aplicar filtros admin

---

## Backend

El backend está desarrollado como una API REST utilizando Spring Boot.

### Controladores principales
- `UsersController`
- `CourtsController`
- `AvailabilityController`
- `AdminController`
- `HealthController`

### Entidades principales

#### Usuario
| Campo | Tipo |
|---|---|
| idUsuario | Long |
| nombre | String |
| apellidos | String |
| email | String |
| password | String |
| telefono | String |
| rol | Enum |

Roles disponibles: `USER`, `ADMIN`

#### Pista
| Campo | Tipo |
|---|---|
| idPista | Long |
| nombre | String |
| ubicacion | String |
| precioHora | Double |
| activa | Boolean |

#### Reserva
| Campo | Tipo |
|---|---|
| idReserva | Long |
| usuario | Usuario |
| pista | Pista |
| fechaReserva | LocalDate |
| horaInicio | LocalTime |
| duracionMinutos | Integer |
| estado | Enum |

Estados disponibles: `ACTIVA`, `CANCELADA`

#### Disponibilidad
| Campo | Tipo |
|---|---|
| idPista | Long |
| fecha | LocalDate |
| franjasDisponibles | List\<String\> |

---

## Seguridad

La aplicación utiliza Spring Security con Basic Authentication y roles USER y ADMIN.

El frontend almacena temporalmente en `localStorage`:
```
usuarioEmail
usuarioPassword
usuarioRol
usuarioId
```

---

## Usuarios de prueba

### ADMIN
```
Email:    admin@padel.com
Password: admin
Rol:      ADMIN
```
> Este usuario se crea automáticamente al arrancar la aplicación mediante el `CommandLineRunner`.

### Usuario 1
```
Email:    ana@padelpoint.com
Password: password123
Rol:      USER
```

### Usuario 2
```
Email:    carlos@padelpoint.com
Password: password123
Rol:      USER
```

---

## Datos iniciales cargados automáticamente

### Pistas

| ID | Nombre | Ubicación | Precio |
|---|---|---|---|
| 1 | Central Indoor | Interior | 18 € |
| 2 | Jardín | Exterior | 16 € |
| 3 | Lima Pro | Interior | 20 € |

### Reservas

| Usuario | Pista | Hora | Estado |
|---|---|---|---|
| Ana | Central Indoor | 10:00 – 11:00 | ACTIVA |
| Carlos | Jardín | 18:00 – 20:00 | ACTIVA |
| Ana | Lima Pro | — | CANCELADA |

---

## Endpoints principales

### Auth
| Método | Endpoint | Auth |
|---|---|---|
| POST | `/pistaPadel/auth/register` | Pública |
| POST | `/pistaPadel/auth/login` | Pública |
| GET | `/pistaPadel/auth/me` | USER / ADMIN |

### Pistas
| Método | Endpoint | Auth |
|---|---|---|
| GET | `/pistaPadel/courts` | USER / ADMIN |
| PATCH | `/pistaPadel/courts/{id}` | ADMIN |

### Reservas
| Método | Endpoint | Auth |
|---|---|---|
| POST | `/pistaPadel/reservations` | USER |
| GET | `/pistaPadel/reservations` | USER |
| DELETE | `/pistaPadel/reservations/{id}` | USER |

### Disponibilidad
| Método | Endpoint | Auth |
|---|---|---|
| GET | `/pistaPadel/availability` | Pública |

### Administración
| Método | Endpoint | Auth |
|---|---|---|
| GET | `/pistaPadel/admin/reservations` | ADMIN |

### Healthcheck
| Método | Endpoint | Auth |
|---|---|---|
| GET | `/pistaPadel/health` | Pública |

---

## Capturas Frontend

### Página principal
![Página principal](images/capturas/front-index.png)

### Registro
![Registro](images/capturas/front-register.png)

### Login USER
![Login USER](images/capturas/front-login-user.png)

### Login ADMIN
![Login ADMIN](images/capturas/front-login-admin.png)

### Home usuario
![Home usuario](images/capturas/front-user-home.png)

### Consulta pública de pistas
![Pistas públicas](images/capturas/front-courts-public.png)

### Consulta pública de disponibilidad
![Disponibilidad pública](images/capturas/front-availability-public.png)

### Pistas usuario
![Pistas usuario](images/capturas/front-courts-user.png)

### Disponibilidad usuario
![Disponibilidad usuario](images/capturas/front-availability-user.png)

### Crear reserva
![Crear reserva](images/capturas/front-create-reservation.png)

### Mis reservas
![Mis reservas](images/capturas/front-my-reservations.png)

### Perfil usuario
![Perfil usuario](images/capturas/front-profile.png)

### Dashboard ADMIN
![Dashboard admin](images/capturas/front-admin-home.png)

### Gestión de pistas ADMIN
![Gestión pistas](images/capturas/front-admin-courts.png)

### Gestión reservas ADMIN
![Gestión reservas](images/capturas/front-admin-reservations.png)

---

## Pruebas Backend (Postman)

Todas las pruebas se realizaron con Postman utilizando Basic Auth y los usuarios cargados automáticamente por la aplicación.

### 1. Healthcheck
```
GET http://localhost:8080/pistaPadel/health
→ 200 OK
```
![Healthcheck](images/capturas/postman-health.png)

### 2. Registro correcto
```
POST http://localhost:8080/pistaPadel/auth/register
→ 201 CREATED
```
```json
{
  "nombre": "Laura",
  "apellidos": "Martinez",
  "email": "laura@padelpoint.com",
  "telefono": "612345678",
  "password": "password123"
}
```
![Registro correcto](images/capturas/postman-register-ok.png)

### 3. Registro duplicado
```
POST http://localhost:8080/pistaPadel/auth/register  (mismo email)
→ 409 CONFLICT
```
![Registro duplicado](images/capturas/postman-register-duplicado.png)

### 4. Login USER
```
POST http://localhost:8080/pistaPadel/auth/login
Basic Auth: ana@padelpoint.com / password123
→ 200 OK
```
![Login USER](images/capturas/postman-login-user.png)

### 5. Login ADMIN
```
POST http://localhost:8080/pistaPadel/auth/login
Basic Auth: admin@padel.com / admin
→ 200 OK
```
![Login ADMIN](images/capturas/postman-login-admin.png)

### 6. Obtener usuario autenticado
```
GET http://localhost:8080/pistaPadel/auth/me
→ 200 OK
```
![Usuario autenticado](images/capturas/postman-auth-me.png)

### 7. Consultar pistas
```
GET http://localhost:8080/pistaPadel/courts
→ 200 OK
```
![Consultar pistas](images/capturas/postman-courts.png)

### 8. Consultar disponibilidad general
```
GET http://localhost:8080/pistaPadel/availability?date=2026-05-20
→ 200 OK
```
![Disponibilidad general](images/capturas/postman-availability-general.png)

### 9. Consultar disponibilidad de una pista
```
GET http://localhost:8080/pistaPadel/availability?date=2026-05-20&courtId=1
→ 200 OK
```
![Disponibilidad pista](images/capturas/postman-availability-court.png)

### 10. Crear reserva
```
POST http://localhost:8080/pistaPadel/reservations
→ 201 CREATED
```
```json
{
  "idPista": 1,
  "fechaReserva": "2026-05-20",
  "horaInicio": "18:00",
  "duracionMinutos": 60
}
```
![Crear reserva](images/capturas/postman-create-reservation.png)

### 11. Reserva duplicada
```
POST http://localhost:8080/pistaPadel/reservations  (misma pista y hora)
→ 409 CONFLICT
```
![Reserva duplicada](images/capturas/postman-reservation-conflict.png)

### 12. Ver reservas del usuario
```
GET http://localhost:8080/pistaPadel/reservations
→ 200 OK
```
![Reservas usuario](images/capturas/postman-my-reservations.png)

### 13. Obtener reserva concreta
```
GET http://localhost:8080/pistaPadel/reservations/{id}
→ 200 OK
```
![Reserva concreta](images/capturas/postman-get-reservation.png)

### 14. Cancelar reserva
```
DELETE http://localhost:8080/pistaPadel/reservations/{id}
→ 204 NO CONTENT
```
![Cancelar reserva](images/capturas/postman-delete-reservation.png)

### 15. Reservas ADMIN
```
GET http://localhost:8080/pistaPadel/admin/reservations
→ 200 OK
```
![Reservas admin](images/capturas/postman-admin-reservations.png)

### 16. Filtro ADMIN por pista
```
GET /pistaPadel/admin/reservations?courtId=1
→ 200 OK
```
![Filtro pista](images/capturas/postman-admin-filter-court.png)

### 17. Filtro ADMIN por usuario
```
GET /pistaPadel/admin/reservations?userId=1
→ 200 OK
```
![Filtro usuario](images/capturas/postman-admin-filter-user.png)

### 18. Filtro ADMIN por fecha
```
GET /pistaPadel/admin/reservations?date=2026-05-20
→ 200 OK
```
![Filtro fecha](images/capturas/postman-admin-filter-date.png)

### 19. Activar/desactivar pista
```
PATCH http://localhost:8080/pistaPadel/courts/1
→ 200 OK
```
```json
{
  "activa": false
}
```
![Patch pista](images/capturas/postman-patch-court.png)

### 20. Acceso denegado USER → endpoint ADMIN
```
GET /pistaPadel/admin/reservations  (con credenciales USER)
→ 403 FORBIDDEN
```
![Error 403](images/capturas/postman-403-admin.png)

### 21. Endpoint protegido sin autenticación
```
GET /pistaPadel/reservations  (sin credenciales)
→ 401 UNAUTHORIZED
```
![Error 401](images/capturas/postman-401-noauth.png)

### 22. Login incorrecto
```
POST /pistaPadel/auth/login  (contraseña incorrecta)
→ 401 UNAUTHORIZED
```
![Login incorrecto](images/capturas/postman-login-error.png)

### 23. Reserva en pista inexistente
```
POST /pistaPadel/reservations  (idPista no existe)
→ 404 NOT FOUND
```
![Reserva pista inexistente](images/capturas/postman-court-notfound.png)

### 24. Disponibilidad con fecha inválida
```
GET /pistaPadel/availability?date=fecha-invalida
→ 400 BAD REQUEST
```
![Fecha inválida](images/capturas/postman-invalid-date.png)

### 25. Reservar pista desactivada
```
POST /pistaPadel/reservations  (pista con activa=false)
→ 409 CONFLICT
```
![Pista desactivada](images/capturas/postman-disabled-court.png)

---

## Validaciones implementadas

### Frontend
- Campos obligatorios
- Validación de email
- Confirmación de contraseña
- Pattern de teléfono (9 dígitos)
- Validación de formularios
- Duraciones permitidas

### Backend
- Usuario duplicado
- Reserva en conflicto de horario
- Recursos inexistentes
- Accesos sin permisos
- Validaciones REST
- Gestión de conflictos

---

## Flujo principal de la aplicación

### Usuario público
```
Inicio → Consulta pistas/disponibilidad → Registro/Login
```

### Usuario autenticado
```
Login → Zona privada → Consultar disponibilidad → Crear reserva → Gestionar reservas
```

### Administrador
```
Login ADMIN → Dashboard admin → Gestionar pistas → Consultar reservas → Aplicar filtros
```

---

## Despliegue

### Frontend — GitHub Pages
```
AÑADIR ENLACE
```

### Repositorio GitHub
```
https://github.com/CristinaMolinaAlvarez/Proyecto_Frontend
```

---

## Conclusión

Pádel Point es una aplicación web completa para la gestión de reservas de pistas de pádel. El proyecto integra frontend, backend y JavaScript mediante arquitectura REST y separación por roles.

Durante el desarrollo se aplicaron conceptos de HTML estructurado, CSS responsive, JavaScript moderno con Fetch API, Spring Boot con Spring Security, gestión de roles y autenticación, validaciones cliente y servidor, integración cliente-servidor, testing manual con Postman y despliegue web.

El resultado final es una aplicación funcional, organizada y preparada para futuras ampliaciones.
