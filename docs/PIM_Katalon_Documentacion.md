# 📂 Módulo: Empleados

Este módulo agrupa un conjunto completo de pruebas automatizadas sobre la funcionalidad de gestión de empleados del sistema OrangeHRM. Cubre flujos CRUD fundamentales (crear, buscar, editar, eliminar) y validaciones adicionales como visibilidad de componentes clave y comportamiento condicional.

---

## 🔹 1. Validar carga del módulo PIM

- **Objetivo:** Confirmar que el módulo PIM (Employee Management) está disponible y se carga correctamente tras el inicio de sesión.
- **Pasos cubiertos:**
  - Click en el menú `PIM` desde el dashboard
  - Validación de visibilidad del título `Employee Information`
  - Confirmación de que se muestran empleados en la tabla (`div.oxd-table-card`)

---

## 🔹 2. Crear empleado

### ➤ Flujo sin login de usuario

- **Objetivo:** Validar que un empleado puede ser creado correctamente con solo los datos requeridos o con todos los campos.
- **Variables utilizadas:**
  - `tipoFlujo`: define si se llenan solo campos obligatorios o todos los campos
- **Validaciones incluidas:**
  - Captura de valores ingresados para comparar con los campos de la ficha final
  - Validación del nombre, apellido e ID del empleado

### ➤ Flujo con creación de login de usuario

- **Objetivo:** Validar la creación de un nuevo usuario al mismo tiempo que se crea un empleado.
- **Adicionales:**
  - Se despliega sección `Login Details` para ingresar usuario, contraseña y estado
  - Se verifica posteriormente que ese usuario puede iniciar sesión correctamente (`login reutilizable`)
- **Variables adicionales:**
  - `crearUsuarioLogin`: activa la sección de creación de login
  - Se genera usuario dinámico (`usuarioXXXXX`) para evitar colisiones

---

## 🔹 3. Buscar empleado

- **Objetivo:** Validar que el sistema permite filtrar correctamente empleados por distintos criterios.
- **Opciones de búsqueda (`opcionBusqueda`):**
  - `"nombre"`: busca por nombre completo (nombre + apellido)
  - `"id"`: busca por identificador único del empleado
  - `"ambos"`: usa ambos criterios combinados

- **Validaciones realizadas:**
  - Verifica cantidad de resultados encontrados
  - En búsqueda por nombre: permite múltiples coincidencias con advertencia
  - En búsqueda por ID: solo se acepta 1 coincidencia exacta
  - En búsqueda combinada: deben coincidir nombre completo e ID

- **Adicionales:**
  - Se valida funcionalidad del botón `Reset`
  - Se emplea scroll y JavaScript para navegar dinámicamente por el DOM y capturar datos reales

---

## 🔹 4. Editar empleado

- **Objetivo:** Validar que se puede modificar correctamente uno o varios campos del perfil de un empleado.
- **Campos que pueden modificarse (`campo`):**
  - `"nombre"`
  - `"segundoNombre"`
  - `"apellido"`
  - `"idEmpleado"`
  - `"todos"`

- **Pasos cubiertos:**
  - Selección aleatoria de un empleado del listado
  - Ingreso a su perfil vía botón de edición
  - Limpieza y modificación de campos con `sendKeys` (`Ctrl + A`, `Backspace`)
  - Guardado de cambios y búsqueda posterior para validar edición
- **Validaciones finales:**
  - Se compara cada campo editado con los valores esperados, respetando el flujo definido

---

## 🔹 5. Eliminar empleado

- **Objetivo:** Validar que un empleado puede ser eliminado correctamente del sistema.
- **Pasos cubiertos:**
  - Selección aleatoria del empleado a eliminar
  - Captura previa de su ID
  - Click en botón eliminar + confirmación mediante `Yes, Delete`
  - Verificación del mensaje `Successfully Deleted`
  - Búsqueda posterior por ID eliminado → espera el mensaje `No Records Found`

- **Detalles técnicos:**
  - Se fuerza el click de eliminación mediante JavaScript debido a comportamiento inconsistente con `WebUI.click()`
  - El scroll al empleado es automático con `scrollIntoView`
  - El ID eliminado es retornado para búsqueda final

---

## 📌 Observaciones generales

- Se utilizó el mismo flujo base de navegación en todos los casos, invocando el login reutilizable.
- Se implementaron condiciones dinámicas mediante `switch`, `Map`, y estructuras reutilizables.
- La extracción de datos de DOM se realizó mediante `JavaScript` por la complejidad de los `Test Objects` estándar.
- El diseño es modular y flexible: cada flujo puede activarse o no mediante booleanos o parámetros predefinidos.

---

Este módulo representa una cobertura completa y práctica del ciclo de vida de un empleado en OrangeHRM, diseñado para ser reutilizable, fácilmente integrable en pipelines CI/CD y mantenible a futuro.



