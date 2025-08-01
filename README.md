
# 🚀 Portafolio de Automatización Web - OrangeHRM (Katalon Studio)

Este proyecto representa un portafolio técnico de automatización en Katalon Studio sobre la plataforma **[OrangeHRM Demo](https://opensource-demo.orangehrmlive.com/)**. Está diseñado con foco en buenas prácticas, modularidad, reutilización y validación robusta de datos desde el DOM.

---

## 📁 Estructura del Proyecto

```
Test Cases/
├── Auth/           → Flujos de login, logout y login reutilizable
├── Dashboard/      → Validaciones generales del entorno post-login
├── Empleados/      → Casos CRUD completos sobre empleados
├── Negativos/      → Validaciones con entradas erróneas

Object Repository/
├── Auth object/
├── Dashboard object/
├── My info object/
├── PIM object/

Test Suites/
├── Suite_Empleados/
├── Suite_Login/

Profiles/
Data Files/
Reports/
...
```

---

## ✅ Módulos Implementados

### 🔹 Auth (Autenticación)

- Login exitoso, inválido y sin credenciales
- Logout
- Test de login reutilizable (parametrizable vía `callTestCase()`)

### 🔹 Empleados (PIM)

- **Validar carga de módulo PIM**  
  Confirma visibilidad y disponibilidad del listado de empleados

- **Crear empleado (con o sin login de usuario)**  
  Flujo completo con switch entre datos mínimos y todos los campos. Valida creación del usuario y su posterior login

- **Buscar empleado**  
  Con criterios por nombre, ID o ambos. Valida cantidad y unicidad de resultados

- **Editar empleado**  
  Permite modificar cualquier combinación de campos (`nombre`, `segundoNombre`, `apellido`, `ID`) y valida posterior actualización

- **Eliminar empleado**  
  Selecciona un empleado al azar, guarda su ID, lo elimina y valida que no exista en el sistema posteriormente

---

## 📦 Suites de Pruebas

Las `Test Suites` están preparadas para ejecución secuencial de pruebas completas. Aunque no fueron utilizadas en este portafolio, están disponibles para:

- **Suite_Login:** para validar distintas combinaciones de credenciales
- **Suite_Empleados:** para ejecución continua de todos los flujos CRUD

> 📌 *Estas suites pueden ser integradas fácilmente a CI/CD (Jenkins, GitHub Actions, etc.) usando la CLI de Katalon o Katalon Runtime Engine.*

---

## 🛠️ Tecnologías

- **Katalon Studio** `v8+`
- Groovy (scripting)
- JavaScript DOM (para manipulaciones complejas)
- `callTestCase()` para reutilización
- `Map`, `Switch`, `Funciones` para lógica dinámica
- Validaciones visibles y condicionales (timeouts, retries, fallbacks)

---

## 📌 Consideraciones

- El módulo de **Usuarios** no fue desarrollado por tratarse de un flujo prácticamente idéntico al de Empleados, con la diferencia de validación de login post-eliminación.
- Todos los scripts están preparados para ser entendidos por reclutadores técnicos y no técnicos, con comentarios claros y organización modular.

---

## 📂 Próximas Extensiones (opcional)

- Integración con datos externos (`Data Files` o `Excel`)
- Validaciones visuales (pantallazos con `takeScreenshot`)
- Reutilización avanzada con `Keywords` personalizados

---

## 👨‍💻 Autor

**Sebastián González**  
Técnico Universitario en Informática – UTFSM  
QA Automatizador | Katalon, Selenium, Postman  
📸 Fotógrafo de fauna y paisaje

---

Este proyecto fue desarrollado como parte de un portafolio técnico para demostrar habilidades en automatización de pruebas web con enfoque práctico, robusto y profesional.
