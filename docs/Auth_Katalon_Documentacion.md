# 📂 Módulo: Auth (Autenticación)

Este módulo cubre los flujos fundamentales de autenticación del sistema OrangeHRM. Cada prueba está diseñada para validar comportamientos esperados ante distintos escenarios de inicio y cierre de sesión.

---

## 🔹 Login exitoso

- **Objetivo:** Validar que un usuario con credenciales válidas pueda iniciar sesión correctamente.
- **Pasos cubiertos:**
  - Acceso a la página de login
  - Ingreso de credenciales válidas (`Admin` / `admin123`)
  - Verificación del dashboard tras el login
  - Validación del nombre de usuario y menú lateral visibles

---

## 🔹 Login incorrecto

- **Objetivo:** Verificar que el sistema responda adecuadamente ante credenciales inválidas.
- **Pasos cubiertos:**
  - Ingreso de usuario válido y contraseña inválida
  - Validación de mensaje de error: `Invalid credentials`

---

## 🔹 Login sin credenciales

- **Objetivo:** Asegurar que el sistema bloquee el acceso si los campos están vacíos.
- **Pasos cubiertos:**
  - Click en login sin ingresar datos
  - Verificación de mensajes `Required` debajo de los campos de usuario y contraseña
  - Validación independiente para ambos mensajes

---

## 🔹 Logout

- **Objetivo:** Validar que el usuario pueda cerrar sesión correctamente y sea redirigido al login.
- **Pasos cubiertos:**
  - Login exitoso previo
  - Acceso al menú de usuario (esquina superior derecha)
  - Selección de la opción `Logout`
  - Verificación de redirección a la URL del login

---

## 🔹 Login reutilizable (sin cierre de navegador)

- **Objetivo:** Proveer un test de login exitoso reutilizable para otros módulos, sin cerrar el navegador al finalizar.
- **Uso:** Este test se invoca desde otros test cases mediante `callTestCase()`, permitiendo iniciar sesión sin repetir pasos de autenticación.
- **Pasos cubiertos:**
  - Apertura de navegador
  - Acceso a la página de login
  - Ingreso de credenciales válidas (`Admin` / `admin123`)
  - Click en botón de login
  - Validación de llegada al dashboard (`h6_Dashboard` visible)
- **Consideraciones:**
  - Este test no incluye `closeBrowser()`.
  - Se debe usar en test que requieran sesión activa al inicio.

---

Este conjunto de pruebas constituye la base del portafolio en Katalon y está diseñado para ser reutilizable, modular y fácil de extender en escenarios futuros.