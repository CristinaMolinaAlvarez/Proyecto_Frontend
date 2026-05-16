# Pádel Point

Aplicación web para la gestión de reservas de pistas de pádel desarrollada como proyecto final de PAT.

Proyecto realizado por:

- Cristina Molina
- Carla Rodríguez
- Paula Díez
- Patricia Urquijo

---

# Descripción

Pádel Point es una aplicación web completa que permite:

- Consultar pistas de pádel
- Ver disponibilidad por fecha
- Registrarse e iniciar sesión
- Crear reservas
- Gestionar reservas personales
- Administrar pistas y reservas como administrador

El proyecto integra:

- Frontend en HTML, CSS y JavaScript
- Backend REST desarrollado con Spring Boot
- Comunicación cliente-servidor mediante Fetch API
- Autenticación y autorización con Spring Security
- Persistencia simulada mediante DataInitializer/BaseDatos
- Despliegue del frontend con GitHub Pages

---

# Tecnologías utilizadas

## Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Security
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

Finalmente se eliminó la carpeta `/frontend` y todos los archivos HTML, CSS y JS se movieron a la raíz del proyecto para permitir que GitHub Pages cargase correctamente `index.html`.

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
│
├── backend/
│   └── src/main/java/
│
└── README.md
```

---

# Roles de usuario

La aplicación diferencia tres perfiles:

## Usuario no autenticado

Puede:

- Ver la página principal
- Consultar pistas
- Consultar disponibilidad
- Registrarse
- Iniciar sesión

## Usuario autenticado

Puede:

- Acceder a su zona privada
- Consultar pistas
- Ver disponibilidad
- Crear reservas
- Modificar reservas
- Cancelar reservas
- Consultar sus reservas
- Gestionar su perfil

## Administrador

Puede:

- Acceder al panel de administración
- Consultar todas las reservas
- Filtrar reservas
- Activar/desactivar pistas
- Gestionar el sistema

---

# Frontend

El frontend está desarrollado completamente con HTML, CSS y JavaScript.

Se diseñó una interfaz:

- Clara
- Escalable
- Separada por roles
- Preparada para integrarse con backend REST

## Páginas públicas

- `index.html`
- `login.html`
- `register.html`
- `courts-public.html`
- `availability-public.html`

## Páginas privadas de usuario

- `user-home.html`
- `courts-user.html`
- `availability-user.html`
- `reservation.html`
- `my-reservations.html`
- `profile.html`

## Páginas privadas de administrador

- `admin-home.html`
- `admin-courts.html`
- `admin-reservations.html`

---

# JavaScript

La lógica cliente se implementó con JavaScript y Fetch API.

## Funcionalidades implementadas

- Registro de usuarios
- Inicio de sesión
- Logout
- Almacenamiento de sesión con localStorage
- Gestión de roles
- Creación de reservas
- Cancelación de reservas
- Consulta de disponibilidad
- Panel de administración
- Protección de vistas admin
- Renderizado dinámico de tablas y tarjetas

## Archivos JS principales

### login.js

Gestiona:

- Inicio de sesión
- Fetch al backend
- Guardado de usuario en localStorage
- Redirección según rol

### registro.js

Gestiona:

- Registro de usuario
- Conversión FormData → JSON
- Validación frontend
- Envío al backend

### reserva.js

Gestiona:

- Creación de reservas
- Basic Auth
- Envío de formularios

### availability-user.js

Gestiona:

- Consulta dinámica de disponibilidad
- Renderizado de horas libres

### verReserva.js

Gestiona:

- Carga de reservas del usuario
- Cancelación de reservas
- Estado ACTIVA/CANCELADA

### admin.js

Gestiona:

- Protección de páginas admin
- Carga de pistas
- Activar/desactivar pistas
- Consulta de reservas globales
- Filtros admin

---

# Backend

El backend está desarrollado como API REST con Spring Boot.

## Controladores principales

- UsersController
- CourtsController
- AvailabilityController
- AdminController
- HealthController

## Entidades principales

### Usuario

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

### Pista

Campos principales:

- idPista
- nombre
- ubicacion
- precioHora
- activa

---

### Reserva

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

### Disponibilidad

Campos principales:

- idPista
- fecha
- franjasDisponibles

---

# Seguridad

La aplicación utiliza:

- Spring Security
- Basic Auth
- Roles USER y ADMIN

El frontend almacena temporalmente:

```txt
usuarioEmail
usuarioPassword
usuarioRol
usuarioId
```

en `localStorage`.

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

# Usuarios de prueba

## ADMIN

```txt
Email: admin@padel.com
Password: 1234
```

## USER

```txt
Email: ana@padel.com
Password: 1234
```

```txt
Email: mario@padel.com
Password: 1234
```

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

## Administrador

```txt
Login admin
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

# Validaciones implementadas

## Frontend

- Campos obligatorios
- Validación email
- Confirmación de contraseña
- Pattern teléfono
- Duraciones permitidas
- Formularios protegidos

## Backend

- Usuario duplicado
- Reserva inválida
- Acceso sin permisos
- Recursos inexistentes
- Validaciones REST

---

# Pruebas realizadas

Todas las pruebas backend se realizaron en Postman.

---

# PRUEBAS EXACTAS BACKEND (POSTMAN)

## 1. Healthcheck

### GET

```txt
GET http://localhost:8080/pistaPadel/health
```

Resultado esperado:

```txt
200 OK
ok
```

CAPTURA:

```txt
postman-health.png
```

---

# 2. Registro correcto

### POST

```txt
POST /pistaPadel/auth/register
```

Body JSON:

```json
{
  "nombre": "Laura",
  "apellidos": "Martinez",
  "email": "laura@test.com",
  "telefono": "612345678",
  "password": "123456"
}
```

Resultado esperado:

```txt
201 CREATED
```

CAPTURA:

```txt
postman-register-ok.png
```

---

# 3. Registro duplicado

Mismo email anterior.

Resultado esperado:

```txt
409 CONFLICT
```

CAPTURA:

```txt
postman-register-duplicado.png
```

---

# 4. Login correcto USER

### POST

```txt
POST /pistaPadel/auth/login
```

x-www-form-urlencoded:

```txt
username=ana@padel.com
password=1234
```

Resultado esperado:

```txt
200 OK
```

CAPTURA:

```txt
postman-login-user.png
```

---

# 5. Login correcto ADMIN

```txt
username=admin@padel.com
password=1234
```

CAPTURA:

```txt
postman-login-admin.png
```

---

# 6. Obtener usuario autenticado

### GET

```txt
GET /pistaPadel/auth/me
```

Con Basic Auth.

Resultado esperado:

```txt
200 OK
```

CAPTURA:

```txt
postman-me.png
```

---

# 7. Consultar pistas

### GET

```txt
GET /pistaPadel/courts
```

Resultado esperado:

```txt
Lista de pistas
```

CAPTURA:

```txt
postman-courts.png
```

---

# 8. Consultar disponibilidad general

### GET

```txt
GET /pistaPadel/availability?date=2026-05-20
```

CAPTURA:

```txt
postman-availability-general.png
```

---

# 9. Consultar disponibilidad de una pista

### GET

```txt
GET /pistaPadel/availability?date=2026-05-20&courtId=1
```

CAPTURA:

```txt
postman-availability-court.png
```

---

# 10. Crear reserva correcta

### POST

```txt
POST /pistaPadel/reservations
```

Body JSON:

```json
{
  "idPista": 1,
  "fechaReserva": "2026-05-20",
  "horaInicio": "18:00",
  "duracionMinutos": 60
}
```

Con Basic Auth USER.

Resultado esperado:

```txt
201 CREATED
```

CAPTURA:

```txt
postman-create-reservation.png
```

---

# 11. Ver reservas del usuario

### GET

```txt
GET /pistaPadel/reservations
```

CAPTURA:

```txt
postman-my-reservations.png
```

---

# 12. Cancelar reserva

### DELETE

```txt
DELETE /pistaPadel/reservations/{id}
```

Resultado esperado:

```txt
204 NO CONTENT
```

CAPTURA:

```txt
postman-delete-reservation.png
```

---

# 13. Ver reservas admin

### GET

```txt
GET /pistaPadel/admin/reservations
```

Con ADMIN.

CAPTURA:

```txt
postman-admin-reservations.png
```

---

# 14. Filtro reservas admin

### GET

```txt
GET /pistaPadel/admin/reservations?courtId=1
```

CAPTURA:

```txt
postman-admin-filter.png
```

---

# 15. Activar/desactivar pista

### PATCH

```txt
PATCH /pistaPadel/courts/1
```

Body:

```json
{
  "activa": false
}
```

CAPTURA:

```txt
postman-patch-court.png
```

---

# PRUEBAS FRONTEND

## 1. Página principal

- Carga correcta
- Navegación
- Responsive

CAPTURA:

```txt
front-index.png
```

---

## 2. Registro

- Registro correcto
- Contraseñas coinciden
- Validaciones

CAPTURA:

```txt
front-register.png
```

---

## 3. Login

- Login USER
- Login ADMIN
- Redirección correcta

CAPTURA:

```txt
front-login.png
```

---

## 4. Usuario HOME

- Acceso privado
- Navegación

CAPTURA:

```txt
front-user-home.png
```

---

## 5. Consultar disponibilidad

- Consulta por fecha
- Consulta por pista

CAPTURA:

```txt
front-availability.png
```

---

## 6. Crear reserva

- Reserva correcta
- Redirección

CAPTURA:

```txt
front-create-reservation.png
```

---

## 7. Mis reservas

- Reserva ACTIVA
- Cancelar reserva
- Estado CANCELADA

CAPTURA:

```txt
front-my-reservations.png
```

---

## 8. Perfil

- Visualización perfil
- Cambio contraseña
- Validación confirmación

CAPTURA:

```txt
front-profile.png
```

---

## 9. Admin dashboard

- Acceso ADMIN
- Protección de rol

CAPTURA:

```txt
front-admin-home.png
```

---

## 10. Gestión de pistas

- Activar/desactivar pista
- Tabla admin

CAPTURA:

```txt
front-admin-courts.png
```

---

## 11. Gestión de reservas admin

- Tabla reservas
- Filtros

CAPTURA:

```txt
front-admin-reservations.png
```

---

# Despliegue

## Frontend

GitHub Pages:

```txt
AÑADIR ENLACE
```

## Repositorio GitHub

```txt
https://github.com/CristinaMolinaAlvarez/Proyecto_Frontend
```

---

# Conclusión

Pádel Point es una aplicación web completa para la gestión de reservas de pistas de pádel.

El proyecto integra frontend, backend y JavaScript mediante arquitectura REST y separación por roles. Se han aplicado conceptos de:

- HTML y CSS estructurado
- JavaScript moderno
- Fetch API
- Spring Boot
- Seguridad
- Autenticación
- Roles
- Validaciones
- Gestión de estado
- Integración cliente-servidor
- Testing manual con Postman
- Despliegue web

El resultado final es una aplicación funcional, organizada y preparada para futuras ampliaciones.
