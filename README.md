# Pádel Point – Frontend
## Proyecto realizado por Cristina Molina, Carla Rodríguez, Paula Diez y Patricia Urquijo
## Descripción

Este proyecto consiste en el desarrollo del frontend de una aplicación web para la gestión de pistas de pádel.

El objetivo principal ha sido diseñar una interfaz estructurada, coherente y escalable, centrada en la experiencia de usuario y preparada para su integración con un backend en futuras fases.

Se ha priorizado:
- La claridad en la navegación
- La separación de roles
- La coherencia visual y estructural
- La preparación para una arquitectura REST

---

## Enfoque del desarrollo

El diseño del sistema se ha basado en una separación clara por perfiles de usuario, permitiendo una organización lógica tanto de las vistas como del flujo de navegación.

Se han definido tres tipos de usuario:

- Usuario no autenticado
- Usuario autenticado
- Administrador

Cada perfil tiene acceso a diferentes funcionalidades, lo que se refleja directamente en la estructura de páginas y navegación del sistema.

---

## Estructura del proyecto

Las páginas se han organizado según el nivel de acceso:

### Páginas públicas
Accesibles sin autenticación:

- `index.html` → Página de inicio  
- `login.html` → Inicio de sesión  
- `register.html` → Registro de usuario  
- `courts-public.html` → Visualización de pistas  
- `availability-public.html` → Consulta de disponibilidad  

---

### Páginas de usuario
Accesibles tras autenticación:

- `user-home.html` → Panel de usuario  
- `courts-user.html` → Pistas con opción de reserva  
- `availability-user.html` → Consulta de disponibilidad  
- `reservation.html` → Creación de reservas  
- `my-reservations.html` → Gestión de reservas propias  

---

### Páginas de administrador
Accesibles con rol administrador:

- `admin-home.html` → Panel principal  
- `admin-courts.html` → Gestión de pistas  
- `admin-reservations.html` → Gestión de reservas  

---

## Flujo de navegación

### Usuario no autenticado

1. Accede a `index.html`
2. Puede navegar a:
   - Pistas (`courts-public.html`)
   - Disponibilidad (`availability-public.html`)
3. Para realizar acciones:
   - Login (`login.html`)
   - Registro (`register.html`)

---

### Usuario autenticado

1. Accede a `user-home.html`
2. Desde ahí puede:
   - Consultar pistas (`courts-user.html`)
   - Ver disponibilidad (`availability-user.html`)
   - Crear reservas (`reservation.html`)
   - Ver sus reservas (`my-reservations.html`)

---

### Administrador

1. Accede a `admin-home.html`
2. Desde ahí puede:
   - Gestionar pistas (`admin-courts.html`)
   - Gestionar reservas (`admin-reservations.html`)

---

## Preparación para backend

Aunque esta práctica no incluye lógica en JavaScript ni conexión con backend, el proyecto ha sido diseñado para facilitar su integración futura.

Se ha preparado:

- Separación de vistas según permisos (roles)
- Flujo de navegación coherente con endpoints REST

Esto permitirá en la siguiente fase:

- Conectar con Spring Boot
- Implementar autenticación real
- Gestionar datos dinámicos
- Persistencia de reservas y usuarios

