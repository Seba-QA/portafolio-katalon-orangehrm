# 🧩 Módulo Dashboard - Katalon Portafolio

Este módulo contiene una serie de pruebas automatizadas orientadas a validar la interfaz principal de usuario tras el login en OrangeHRM. Incluye validaciones visuales, navegación por secciones y pruebas dinámicas del menú lateral.

---

## 🔹 Ejercicio 1: Validar carga del dashboard

- **Objetivo:** Confirmar que el usuario llegue correctamente al dashboard tras el login.
- **Precondición:** Login exitoso.
- **Pasos:**
  1. Login como Admin.
  2. Validar URL del dashboard.
  3. Validar título visible ("Dashboard").
  4. Validar que el widget "Employee Distribution by Location" esté presente.
- **Validaciones:**
  - Redirección a `dashboard/index`.
  - Título principal visible y correcto.
  - Widget ubicado fuera del viewport sea visible tras hacer scroll.

---

## 🔹 Ejercicio 2: Validar menú lateral visible y comportamiento de minimizado

- **Objetivo:** Asegurar que el menú lateral se despliegue correctamente luego del login y que pueda minimizarse y maximizarse mediante su botón correspondiente.
- **Precondición:** Usuario autenticado.
- **Pasos:**
  1. Login como Admin.
  2. Verificar que se muestre el panel lateral con las secciones: Admin, PIM, Leave, etc.
  3. Hacer clic en el botón para minimizar el menú lateral.
  4. Verificar que el menú se oculta correctamente.
  5. Hacer clic nuevamente para maximizar.
  6. Verificar que el menú vuelve a mostrarse.
- **Validaciones:**
  - El objeto del menú está visible al inicio.
  - Contiene al menos 5 secciones principales.
  - El menú puede minimizarse y luego volver a desplegarse correctamente mediante el ícono de flecha (`chevron-right` / `chevron-left`).

---

## 🔹 Ejercicio 3: Validar acceso a sección "My Info"

- **Objetivo:** Verificar que el acceso al módulo "My Info" desde el dashboard sea exitoso.
- **Precondición:** Usuario autenticado y módulo visible.
- **Pasos:**
  1. Login como Admin.
  2. Click en sección "My Info".
  3. Verificar redirección a URL esperada.
  4. Validar visibilidad y contenido del título "Personal Details".
- **Validaciones:**
  - Sección "My Info" visible en menú lateral.
  - Redirección correcta a `pim/viewPersonalDetails/empNumber/{n}`.
  - Título visible y que contenga el texto "Personal Details".

---