# HLVS Residencial API y Frontend

## Introducción

Este repositorio contiene el código del sistema de control de acceso basado en códigos QR para la colonia HLVS. El sistema está compuesto por un backend desarrollado en Spring Boot y un frontend desarrollado con Vue.js. La arquitectura sigue un enfoque en capas para asegurar la modularidad y escalabilidad del proyecto.

---

## Tecnologías utilizadas

### Backend (API):

- **Lenguaje:** Java
- **Framework:** Spring Boot
- **Seguridad:** Spring Security con JWT
- **Base de datos:** PostgreSQL
- **Herramientas adicionales:**
  - JPA (Hibernate) para el manejo de datos.
  - WebSocket para la comunicación en tiempo real.
  - Control de acceso basado en roles.

### Frontend:

- **Framework:** Vue.js
- **Herramienta de compilación:** Vite
- **Lenguaje:** JavaScript/ES6
- **Estilos:** Vuetify
- **Administración de estados:** Pinia
- **Ruteo:** Vue Router

---

## Estructura del Proyecto

### Backend:

El backend sigue una arquitectura en capas que divide las responsabilidades en:

1. **Configuración:**

   - Contiene configuraciones generales del proyecto, como seguridad y configuraciones de WebSocket.
   - Directorio: `config`

2. **Controladores:**

   - Gestiona las peticiones HTTP y se comunica con los servicios.
   - Directorio: `controllers`

3. **Servicios:**

   - Contiene la lógica de negocio.
   - Directorio: `services` con implementaciones en `services/impls`

4. **Repositorios:**

   - Acceso directo a la base de datos utilizando JPA.
   - Directorio: `repositories`

5. **Entidades:**

   - Representación de las tablas de la base de datos.
   - Directorio: `domain/entities`

6. **DTOs:**

   - Objetos de transferencia de datos para estructurar y enviar datos.
   - Directorio: `domain/dtos`

7. **Handlers:**

   - Manejo de errores global.
   - Directorio: `handlers`

8. **Archivos importantes:**

   - `HlvsApiApplication`: Clase principal de la aplicación.
   - `application.properties`: Configuraciones generales del proyecto.

### Frontend:

El frontend está organizado de la siguiente manera:

1. **Público:**

   - Recursos accesibles directamente como `index.html`.
   - Directorio: `public`

2. **SRC:**

   - Código fuente principal.
     - **Componentes:** Elementos reutilizables de la interfaz.
     - **Rutas:** Manejo de navegación entre vistas.
     - **Servicios:** Integración con la API del backend.
     - **Stores:** Manejo del estado global utilizando Pinia.
     - **Vistas:** Pantallas completas de la aplicación.
   - Directorio: `src`

3. **Configuración:**

   - Archivo de configuración: `vite.config.js`

4. **Archivos importantes:**

   - `.env`: Variables de entorno.
   - `main.js`: Punto de entrada de la aplicación Vue.



## Documentación

### Endpoints importantes del backend:

1. **Control de acceso con QR:**

   - `POST /api/qr/scan`
   - Valida y registra el uso de un código QR.

2. **Generación de QR:**

   - `POST /api/qr/generate`
   - Genera códigos QR basados en roles o solicitudes de acceso.

3. **Familias:**

   - `PUT /api/family`
   - Actualiza miembros de la familia.

4. **Correos:**

   - `POST /api/email/send-email`
   - Envía correos de notificación.

### Funcionalidades del Frontend:

- Inicio de sesión con autenticación JWT.
- Generación y escaneo de códigos QR.
- Panel de control para residentes y seguridad.
- Administración de permisos por rol (residente, visitante, seguridad).

---

## Documentación extra

En este repositorio también se incluyen:

- [https://documenter.getpostman.com/view/36758312/2sA3e1CAK6](https://documenter.getpostman.com/view/36758312/2sA3e1CAK6)
