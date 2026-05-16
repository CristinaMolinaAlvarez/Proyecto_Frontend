# Pádel Point

Aplicación web para la gestión de reservas de pistas de pádel desarrollada como proyecto final de Programación de Aplicaciones Telemáticas (PAT).

Proyecto realizado por:

- Cristina Molina
- Carla Rodríguez
- Paula Díez
- Patricia Urquijo

---

# Descripción

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

# Tecnologías utilizadas

## Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Maven
- JUnit

## Frontend

- HTML5
- CSS3
- JavaScript
- Fetch API
- DOM API
- LocalStorage

## Herramientas

- IntelliJ IDEA
- Visual Studio Code
- Git
- GitHub
- GitHub Pages
- Postman

---

# Estructura del proyecto

Durante el desarrollo se eliminó la carpeta `/frontend` y todos los archivos HTML, CSS y JS se movieron a la raíz del proyecto para permitir que GitHub Pages detectase correctamente `index.html`.

```txt
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

# Roles de usuario

## Usuario no autenticado

Puede:

- Acceder a la página principal
- Consultar pistas
- Consultar disponibilidad
- Registrarse
- Iniciar sesión

---

## Usuario autenticado

Puede:

- Acceder a su zona privada
- Consultar pistas
- Ver disponibilidad
- Crear reservas
- Cancelar reservas
- Consultar sus reservas
- Gestionar su perfil

---

## Administrador

Puede:

- Acceder al dashboard de administración
- Consultar todas las reservas
- Filtrar reservas
- Activar/desactivar pistas
- Gestionar el sistema

---

# Frontend

El frontend está desarrollado completamente con HTML, CSS y JavaScript.

La aplicación se diseñó con:

- Separación clara por roles
- Navegación coherente
- Diseño responsive
- Integración preparada para backend REST
- Componentes reutilizables
- Estilo visual homogéneo

---

# Páginas públicas

- `index.html`
- `login.html`
- `register.html`
- `courts-public.html`
- `availability-public.html`

---

# Páginas privadas de usuario

- `user-home.html`
- `courts-user.html`
- `availability-user.html`
- `reservation.html`
- `my-reservations.html`
- `profile.html`

---

# Páginas privadas de administrador

- `admin-home.html`
- `admin-courts.html`
- `admin-reservations.html`

---

# JavaScript

La lógica cliente se implementó mediante JavaScript y Fetch API.

---

# Funcionalidades implementadas

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

---

# Archivos JavaScript principales

## login.js

Gestiona:

- Inicio de sesión
- Peticiones fetch de login
- Guardado de datos de sesión
- Redirección según rol

---

## registro.js

Gestiona:

- Registro de usuario
- Conversión FormData → JSON
- Validaciones frontend
- Comunicación con backend

---

## reserva.js

Gestiona:

- Creación de reservas
- Envío de formularios
- Basic Auth
- Gestión de errores

---

## availability-user.js

Gestiona:

- Consulta de disponibilidad
- Renderizado dinámico de franjas horarias
- Consulta por fecha y pista

---

## verReserva.js

Gestiona:

- Carga de reservas del usuario
- Cancelación de reservas
- Estado ACTIVA/CANCELADA

---

## admin.js

Gestiona:

- Protección de vistas admin
- Activar/desactivar pistas
- Consultar reservas
- Aplicar filtros admin

---

# Backend

El backend está desarrollado como una API REST utilizando Spring Boot.

---

# Controladores principales

- UsersController
- CourtsController
- AvailabilityController
- AdminController
- HealthController

---

# Entidades principales

## Usuario

Campos principales:

- idUsuario
- nombre
- apellidos
- email
- password
- telefono
- rol

Roles:

```txt
USER
ADMIN
```

---

## Pista

Campos principales:

- idPista
- nombre
- ubicacion
- precioHora
- activa

---

## Reserva

Campos principales:

- idReserva
- usuario
- pista
- fechaReserva
- horaInicio
- duracionMinutos
- estado

Estados:

```txt
ACTIVA
CANCELADA
```

---

## Disponibilidad

Campos principales:

- idPista
- fecha
- franjasDisponibles

---

# Seguridad

La aplicación utiliza:

- Spring Security
- Basic Authentication
- Roles USER y ADMIN

El frontend almacena temporalmente:

```txt
usuarioEmail
usuarioPassword
usuarioRol
usuarioId
```

mediante `localStorage`.

---

# Usuarios de prueba

## ADMIN

```txt
Email: admin@padel.com
Password: admin
Rol: ADMIN
```

Este usuario administrador se crea automáticamente al arrancar la aplicación mediante el `CommandLineRunner`.

---

## USERS DE EJEMPLO

### Usuario 1

```txt
Email: ana@padelpoint.com
Password: password123
Rol: USER
```

### Usuario 2

```txt
Email: carlos@padelpoint.com
Password: password123
Rol: USER
```

---

# Datos iniciales cargados automáticamente

## Pistas iniciales

| ID | Nombre | Ubicación | Precio |
|---|---|---|---|
| 1 | Central Indoor | Interior | 18€ |
| 2 | Jardín | Exterior | 16€ |
| 3 | Lima Pro | Interior | 20€ |

---

## Reservas iniciales

### Reserva activa

```txt
Usuario: Ana
Pista: Central Indoor
Hora: 10:00 - 11:00
Estado: ACTIVA
```

### Reserva activa

```txt
Usuario: Carlos
Pista: Jardín
Hora: 18:00 - 20:00
Estado: ACTIVA
```

### Reserva cancelada

```txt
Usuario: Ana
Pista: Lima Pro
Estado: CANCELADA
```

---

# Endpoints principales

## Auth

| Método | Endpoint |
|---|---|
| POST | /pistaPadel/auth/register |
| POST | /pistaPadel/auth/login |
| GET | /pistaPadel/auth/me |

---

## Pistas

| Método | Endpoint |
|---|---|
| GET | /pistaPadel/courts |
| PATCH | /pistaPadel/courts/{id} |

---

## Reservas

| Método | Endpoint |
|---|---|
| POST | /pistaPadel/reservations |
| GET | /pistaPadel/reservations |
| DELETE | /pistaPadel/reservations/{id} |

---

## Disponibilidad

| Método | Endpoint |
|---|---|
| GET | /pistaPadel/availability |

---

## Administración

| Método | Endpoint |
|---|---|
| GET | /pistaPadel/admin/reservations |

---

## Healthcheck

| Método | Endpoint |
|---|---|
| GET | /pistaPadel/health |

---

# Capturas Frontend

## Página principal

![Página principal](images/capturas/front-index.png)

---

## Registro

![Registro](images/capturas/front-register.png)

---

## Login USER

![Login USER](images/capturas/front-login-user.png)

---

## Login ADMIN

![Login ADMIN](images/capturas/front-login-admin.png)

---

## Home usuario

![Home usuario](images/capturas/front-user-home.png)

---

## Consulta pública de pistas

![Pistas públicas](images/capturas/front-courts-public.png)

---

## Consulta pública de disponibilidad

![Disponibilidad pública](images/capturas/front-availability-public.png)

---

## Pistas usuario

![Pistas usuario](images/capturas/front-courts-user.png)

---

## Disponibilidad usuario

![Disponibilidad usuario](images/capturas/front-availability-user.png)

---

## Crear reserva

![Crear reserva](images/capturas/front-create-reservation.png)

---

## Mis reservas

![Mis reservas](images/capturas/front-my-reservations.png)

---

## Perfil usuario

![Perfil usuario](images/capturas/front-profile.png)

---

## Dashboard ADMIN

![Dashboard admin](images/capturas/front-admin-home.png)

---

## Gestión de pistas ADMIN

![Gestión pistas](images/capturas/front-admin-courts.png)

---

## Gestión reservas ADMIN

![Gestión reservas](images/capturas/front-admin-reservations.png)

---

# PRUEBAS BACKEND (POSTMAN)

Todas las pruebas backend se realizaron con Postman utilizando Basic Auth y los usuarios cargados automáticamente por la aplicación.

---

# 1. Healthcheck

## GET

```txt
GET http://localhost:8080/pistaPadel/health
```

## Resultado esperado

```txt
200 OK
ok
```

![Healthcheck](images/capturas/postman-health.png)

---

# 2. Registro correcto

## POST

```txt
POST http://localhost:8080/pistaPadel/auth/register
```

## Body JSON

```json
{
  "nombre": "Laura",
  "apellidos": "Martinez",
  "email": "laura@padelpoint.com",
  "telefono": "612345678",
  "password": "password123"
}
```

## Resultado esperado

```txt
201 CREATED
```

![Registro correcto](images/capturas/postman-register-ok.png)

---

# 3. Registro duplicado

Repetir el mismo registro anterior.

## Resultado esperado

```txt
409 CONFLICT
```

![Registro duplicado](images/capturas/postman-register-duplicado.png)

---

# 4. Login USER

## POST

```txt
POST http://localhost:8080/pistaPadel/auth/login
```

## Basic Auth

```txt
Email: ana@padelpoint.com
Password: password123
```

## Resultado esperado

```txt
200 OK
```

![Login USER](images/capturas/postman-login-user.png)

---

# 5. Login ADMIN

## POST

```txt
POST http://localhost:8080/pistaPadel/auth/login
```

## Basic Auth

```txt
Email: admin@padel.com
Password: admin
```

## Resultado esperado

```txt
200 OK
```

![Login ADMIN](images/capturas/postman-login-admin.png)

---

# 6. Obtener usuario autenticado

## GET

```txt
GET http://localhost:8080/pistaPadel/auth/me
```

## Resultado esperado

```txt
200 OK
```

![Usuario autenticado](images/capturas/postman-auth-me.png)

---

# 7. Consultar pistas

## GET

```txt
GET http://localhost:8080/pistaPadel/courts
```

![Consultar pistas](images/capturas/postman-courts.png)

---

# 8. Consultar disponibilidad general

## GET

```txt
GET http://localhost:8080/pistaPadel/availability?date=2026-05-20
```

![Disponibilidad general](images/capturas/postman-availability-general.png)

---

# 9. Consultar disponibilidad de una pista

## GET

```txt
GET http://localhost:8080/pistaPadel/availability?date=2026-05-20&courtId=1
```

![Disponibilidad pista](images/capturas/postman-availability-court.png)

---

# 10. Crear reserva

## POST

```txt
POST http://localhost:8080/pistaPadel/reservations
```

## Body JSON

```json
{
  "idPista": 1,
  "fechaReserva": "2026-05-20",
  "horaInicio": "18:00",
  "duracionMinutos": 60
}
```

![Crear reserva](images/capturas/postman-create-reservation.png)

---

# 11. Reserva duplicada

Intentar reservar la misma pista y hora.

## Resultado esperado

```txt
409 CONFLICT
```

![Reserva duplicada](images/capturas/postman-reservation-conflict.png)

---

# 12. Ver reservas usuario

## GET

```txt
GET http://localhost:8080/pistaPadel/reservations
```

![Reservas usuario](images/capturas/postman-my-reservations.png)

---

# 13. Obtener reserva concreta

## GET

```txt
GET http://localhost:8080/pistaPadel/reservations/{id}
```

![Reserva concreta](images/capturas/postman-get-reservation.png)

---

# 14. Cancelar reserva

## DELETE

```txt
DELETE http://localhost:8080/pistaPadel/reservations/{id}
```

![Cancelar reserva](images/capturas/postman-delete-reservation.png)

---

# 15. Reservas ADMIN

## GET

```txt
GET http://localhost:8080/pistaPadel/admin/reservations
```

![Reservas admin](images/capturas/postman-admin-reservations.png)

---

# 16. Filtro ADMIN por pista

## GET

```txt
GET /pistaPadel/admin/reservations?courtId=1
```

![Filtro pista](images/capturas/postman-admin-filter-court.png)

---

# 17. Filtro ADMIN por usuario

## GET

```txt
GET /pistaPadel/admin/reservations?userId=1
```

![Filtro usuario](images/capturas/postman-admin-filter-user.png)

---

# 18. Filtro ADMIN por fecha

## GET

```txt
GET /pistaPadel/admin/reservations?date=2026-05-20
```

![Filtro fecha](images/capturas/postman-admin-filter-date.png)

---

# 19. Activar/desactivar pista

## PATCH

```txt
PATCH http://localhost:8080/pistaPadel/courts/1
```

## Body JSON

```json
{
  "activa": false
}
```

![Patch pista](images/capturas/postman-patch-court.png)

---

# 20. Acceso denegado USER → ADMIN

Intentar acceder como USER a endpoint ADMIN.

## Resultado esperado

```txt
403 FORBIDDEN
```

![Error 403](images/capturas/postman-403-admin.png)

---

# 21. Endpoint protegido sin login

Intentar acceder sin autenticación a endpoint protegido.

## Resultado esperado

```txt
401 UNAUTHORIZED
```

![Error 401](images/capturas/postman-401-noauth.png)

---

# 22. Login incorrecto

Intentar login con contraseña incorrecta.

## Resultado esperado

```txt
401 UNAUTHORIZED
```

![Login incorrecto](images/capturas/postman-login-error.png)

---

# 23. Reserva en pista inexistente

Intentar reservar una pista inexistente.

## Resultado esperado

```txt
404 NOT FOUND
```

![Reserva pista inexistente](images/capturas/postman-court-notfound.png)

---

# 24. Consulta disponibilidad fecha inválida

## GET

```txt
GET /pistaPadel/availability?date=fecha-invalida
```

## Resultado esperado

```txt
400 BAD REQUEST
```

![Fecha inválida](images/capturas/postman-invalid-date.png)

---

# 25. Desactivar pista y comprobar reservas

Intentar reservar pista desactivada.

## Resultado esperado

```txt
409 CONFLICT
```

![Pista desactivada](images/capturas/postman-disabled-court.png)

---

# Validaciones implementadas

## Frontend

- Campos obligatorios
- Validación email
- Confirmación de contraseña
- Pattern teléfono
- Validación formularios
- Duraciones permitidas

---

## Backend

- Usuario duplicado
- Reserva inválida
- Recursos inexistentes
- Accesos sin permisos
- Validaciones REST
- Gestión de conflictos

---

# Flujo principal de la aplicación

## Usuario público

```txt
Inicio
↓
Consulta pistas/disponibilidad
↓
Registro/Login
```

---

## Usuario autenticado

```txt
Login
↓
Zona privada
↓
Consultar disponibilidad
↓
Crear reserva
↓
Gestionar reservas
```

---

## Administrador

```txt
Login ADMIN
↓
Dashboard admin
↓
Gestionar pistas
↓
Consultar reservas
↓
Aplicar filtros
```

---

# Despliegue

## Frontend

GitHub Pages:

```txt
AÑADIR ENLACE
```

---

## Repositorio GitHub

```txt
https://github.com/CristinaMolinaAlvarez/Proyecto_Frontend
```

---

# Conclusión

Pádel Point es una aplicación web completa para la gestión de reservas de pistas de pádel.

El proyecto integra frontend, backend y JavaScript mediante arquitectura REST y separación por roles. Durante el desarrollo se aplicaron conceptos de:

- HTML estructurado
- CSS responsive
- JavaScript moderno
- Fetch API
- Spring Boot
- Seguridad
- Roles y autenticación
- Validaciones
- Integración cliente-servidor
- Testing manual con Postman
- Despliegue web

El resultado final es una aplicación funcional, organizada y preparada para futuras ampliaciones.
