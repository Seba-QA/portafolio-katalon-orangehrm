import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ObjectRepository as OR

def borraContenido(Object input) {
	WebUI.sendKeys(input, Keys.chord(Keys.CONTROL, 'a'))
	WebUI.sendKeys(input, Keys.chord(Keys.BACK_SPACE))
}

def abrirEdit() {
	// Paso 1: Obtener total de empleados
	int totalEmpleados = WebUI.executeJavaScript('''
    return document.querySelectorAll('div.oxd-table-card').length;
	''', null)
	
	WebUI.comment("👥 Total de empleados listados: $totalEmpleados")
	
	// Paso 2: Validar que exista al menos uno
	if (totalEmpleados == 0) {
		KeywordUtil.markFailedAndStop("❌ No hay empleados en la lista para seleccionar.")
	}
	
	// Paso 3: Elegir uno al azar
	int indiceAleatorio = new Random().nextInt(totalEmpleados)
	WebUI.comment("🎯 Seleccionando empleado en posición: $indiceAleatorio")
	
	// Paso 4: Scroll al empleado seleccionado
	WebUI.executeJavaScript('''
    const empleados = document.querySelectorAll('div.oxd-table-card');
    if (empleados.length > 0) {
        empleados[''' + indiceAleatorio + '''].scrollIntoView({ behavior: 'smooth', block: 'center' });
		}
	''', null)

	WebUI.delay(2) // Deja tiempo para que el scroll se complete
	
	// Paso 5: Click en botón editar del empleado seleccionado
	WebUI.executeJavaScript('''
			const empleados = document.querySelectorAll('div.oxd-table-card');
			if (empleados.length > 0) {
				const botones = empleados[''' + indiceAleatorio + '''].querySelectorAll('button');
	        if (botones.length > 0) {
	            botones[0].click(); // 0 = botón editar
	        }
	    }
	''', null)
	
	WebUI.comment("✏️ Click en botón de editar del empleado [$indiceAleatorio] realizado.")
	
}

def editarEmpleado(String nombre, String segundoNombre, String apellido, String id) {
	Object inputNombre = findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_firstName')
	Object inputSegundoNombre = findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_middleName')
	Object inputApellido = findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_lastName')
	Object inputId = findTestObject('Object Repository/PIM object/Modificar/input_Employee Id_oxd-input oxd-input--active')
	WebUI.delay(2)
	//Si es true es porque los datos vienen vacios
	boolean valCampo = true
	// Borra el contenido de los input
	if (nombre != "") {
		borraContenido(inputNombre)
		WebUI.setText(inputNombre, nombre)
		valCampo = false
	}
	if (segundoNombre != "") {
		borraContenido(inputSegundoNombre)
		WebUI.setText(inputSegundoNombre, segundoNombre)
		valCampo = false
	}
	if (apellido != "") {
		borraContenido(inputApellido)
		WebUI.setText(inputApellido, apellido)
		valCampo = false
	}
	if (id != "") {
		borraContenido(inputId)
		WebUI.setText(inputId, id)
		valCampo = false
	}
}

// Busca el empleado creado
def formSearch(String nombreCompleto, String id) {
	if (nombreCompleto != "" && id != "") {
		WebUI.setText(findTestObject('Object Repository/PIM object/Buscar/input_name'), nombreCompleto)
		WebUI.setText(findTestObject('Object Repository/PIM object/Buscar/input_Id'), id)
	}else if (nombreCompleto != "" || id == "") {
		WebUI.setText(findTestObject('Object Repository/PIM object/Buscar/input_name'), nombreCompleto)
	}else if(nombreCompleto == "" || id != "") {
		WebUI.setText(findTestObject('Object Repository/PIM object/Buscar/input_Id'), id)
	}else {
		KeywordUtil.markFailed("❌ Los valores de id no pueden estar vacios")
	}
	WebUI.click(findTestObject('Object Repository/PIM object/button_Search'))
	
}

// Busca y valida al empleado editado
/**
 * Busca un empleado, ingresa a su perfil y valida sus datos.
 * @param nombre - Nombre del empleado
 * @param apellido - Apellido del empleado
 * @param id - ID del empleado
 * @param tipoBusqueda - "nombre", "id" o "ambos"
 * @param tipoValidacion -  "nombre", "segundoNombre", "apellido", "idEmpleado", "todos"
 * @return true si la validación fue exitosa, false si no
 */
def buscarYValidarEmpleado(String nombre, String segundoNombre, String apellido, String id, String tipoBusqueda, String tipoValidacion) {
	WebUI.click(findTestObject('Object Repository/PIM object/Buscar/button_Employee Information'))
	String nombreCompleto = nombre + " " + apellido
	WebUI.comment("✏️ $nombreCompleto")
	WebUI.delay(2)
	// Llenar formulario
	switch (tipoBusqueda) {
		case 'nombre':
			WebUI.setText(findTestObject('PIM object/Buscar/input_name'), nombreCompleto)
			break
		case 'id':
			WebUI.setText(findTestObject('PIM object/Buscar/input_Id'), id)
			break
		case 'ambos':
			WebUI.setText(findTestObject('PIM object/Buscar/input_name'), nombreCompleto)
			WebUI.setText(findTestObject('PIM object/Buscar/input_Id'), id)
			break
		default:
			KeywordUtil.markFailedAndStop("❌ Tipo de búsqueda no válido.")
			return false
	}

	// Click en buscar
	WebUI.click(findTestObject('PIM object/button_Search'))
	WebUI.delay(3)

	// Validar cantidad de resultados
	int resultados = WebUI.executeJavaScript(
		"return document.querySelectorAll('div.oxd-table-card').length;",
		null
	)

	WebUI.comment("🔎 Resultados encontrados: $resultados")

	if (resultados == 0) {
		KeywordUtil.markFailed("❌ No se encontraron empleados.")
		return false
	}
	if (resultados > 1 && tipoBusqueda == 'nombre') {
		KeywordUtil.markWarning("⚠️ Múltiples empleados con el mismo nombre. Considera buscar por ID.")
		return false
	}
	if (resultados > 1 && tipoBusqueda != 'nombre') {
		KeywordUtil.markFailedAndStop("❌ Error crítico: ID duplicado.")
		return false
	}

	// Hacer scroll y doble clic al botón de editar
	TestObject botonEditar = findTestObject('PIM object/Buscar/button_edit') // Asegúrate que apunte al botón del primer empleado
	WebUI.scrollToElement(botonEditar, 3)
	WebUI.doubleClick(botonEditar)
	WebUI.waitForElementVisible(findTestObject('PIM object/Modificar/div_Personal DetailsEmployee'), 5)

	// Validar campos dentro del perfil
	String nombreInput = WebUI.getAttribute(findTestObject('PIM object/Modificar/input_Employee Full Name_firstName'), 'value')
	String apellidoInput = WebUI.getAttribute(findTestObject('PIM object/Modificar/input_Employee Full Name_lastName'), 'value')
	String idInput = WebUI.executeJavaScript(
		"return document.querySelectorAll('input.oxd-input.oxd-input--active')[4].value", null
	)

	// Normalizar datos
	nombreInput = nombreInput?.trim()
	apellidoInput = apellidoInput?.trim()
	idInput = idInput?.trim()

	// Validación final según tipo de búsqueda
	// =========================
// Validación según tipoValidacion
// =========================
switch (tipoValidacion) {
	case 'nombre':
		if (nombreInput == nombre) {
			WebUI.comment("✓ Nombre correcto: $nombreInput")
		} else {
			KeywordUtil.markFailed("❌ Nombre incorrecto. Esperado: $nombre - Obtenido: $nombreInput")
			return false
		}
		break

	case 'segundoNombre':
		String segundoNombreInput = WebUI.getAttribute(findTestObject('PIM object/Modificar/input_Employee Full Name_middleName'), 'value')?.trim()
		if (segundoNombreInput == apellido) {
			WebUI.comment("✓ Segundo nombre correcto: $segundoNombreInput")
		} else {
			KeywordUtil.markFailed("❌ Segundo nombre incorrecto. Esperado: $apellido - Obtenido: $segundoNombreInput")
			return false
		}
		break

	case 'apellido':
		if (apellidoInput == apellido) {
			WebUI.comment("✓ Apellido correcto: $apellidoInput")
		} else {
			KeywordUtil.markFailed("❌ Apellido incorrecto. Esperado: $apellido - Obtenido: $apellidoInput")
			return false
		}
		break

	case 'idEmpleado':
		if (idInput == id) {
			WebUI.comment("✓ ID correcto: $idInput")
		} else {
			KeywordUtil.markFailed("❌ ID incorrecto. Esperado: $id - Obtenido: $idInput")
			return false
		}
		break

	case 'todos':
		String segundoNombreInput = WebUI.getAttribute(findTestObject('PIM object/Modificar/input_Employee Full Name_middleName'), 'value')?.trim()

		boolean todoCorrecto = true

		if (nombreInput != nombre) {
			KeywordUtil.markFailed("❌ Nombre incorrecto. Esperado: $nombre - Obtenido: $nombreInput")
			todoCorrecto = false
		} else {
			WebUI.comment("✓ Nombre correcto: $nombreInput")
		}

		if (apellidoInput != apellido) {
			KeywordUtil.markFailed("❌ Apellido incorrecto. Esperado: $apellido - Obtenido: $apellidoInput")
			todoCorrecto = false
		} else {
			WebUI.comment("✓ Apellido correcto: $apellidoInput")
		}

		if (idInput != id) {
			KeywordUtil.markFailed("❌ ID incorrecto. Esperado: $id - Obtenido: $idInput")
			todoCorrecto = false
		} else {
			WebUI.comment("✓ ID correcto: $idInput")
		}

		if (segundoNombreInput != segundoNombre) {
			KeywordUtil.markFailed("❌ Segundo nombre incorrecto. Esperado: $segundoNombre - Obtenido: $segundoNombreInput")
			todoCorrecto = false
		} else {
			WebUI.comment("✓ Segundo nombre correcto: $segundoNombreInput")
		}

		if (!todoCorrecto) return false
		break

	default:
		KeywordUtil.markFailed("❌ tipoValidacion no reconocido: $tipoValidacion")
		return false
}
	return false
}



WebUI.callTestCase(findTestCase('Empleados/Validar carga PIM'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.comment('==> Validando editar empleados')

// Llena formulario
// ejemplo de acceso a los datos del map
//Nombre: datosEmpleado.nombre
Map datosEmpleado = [
	nombre      : 'Prueba',
	segundoNombre : 'Automatizada',
	paterno     : 'Portafolios',
	idEmpleado  : System.currentTimeMillis().toString()[-8..-1],
]

//// Elegir que campos seran modificados
//String campo = "nombre"
//String campo = "segundoNombre"
//String campo = "apellido"
//String campo = "idEmpleado"
String campo = "todos"

//// Elegir flujo para validar busqueda
//String tipoBusqueda = "nombre"
//String tipoBusqueda = "id"
String tipoBusqueda = "ambos"

// Valor en blanco para parametro
String valorVacio = ""

// Flujo de edicion
abrirEdit()
switch (campo) {
	case 'nombre':
	editarEmpleado(datosEmpleado.nombre, valorVacio, valorVacio, valorVacio)
	WebUI.click(findTestObject('Object Repository/PIM object/Modificar/button_Save'))
	buscarYValidarEmpleado(datosEmpleado.nombre, datosEmpleado.segundoNombre, datosEmpleado.paterno, datosEmpleado.id, tipoBusqueda, campo)
	break
	
	case 'segundoNombre':
	editarEmpleado(valorVacio, datosEmpleado.segundoNombre, valorVacio, valorVacio)
	WebUI.click(findTestObject('Object Repository/PIM object/Modificar/button_Save'))
	break
	
	case 'apellido':
	editarEmpleado(valorVacio, valorVacio, datosEmpleado.paterno, valorVacio)
	WebUI.click(findTestObject('Object Repository/PIM object/Modificar/button_Save'))
	break
	
	case 'idEmpleado':
	editarEmpleado(valorVacio, valorVacio, valorVacio, datosEmpleado.idEmpleado)
	WebUI.click(findTestObject('Object Repository/PIM object/Modificar/button_Save'))
	break
	
	case 'todos':
	editarEmpleado(datosEmpleado.nombre, datosEmpleado.segundoNombre, datosEmpleado.paterno, datosEmpleado.idEmpleado)
	WebUI.click(findTestObject('Object Repository/PIM object/Modificar/button_Save'))
	WebUI.click(findTestObject('Object Repository/Dashboard object/a_PIM'))
	WebUI.delay(2)
	buscarYValidarEmpleado(datosEmpleado.nombre, datosEmpleado.segundoNombre, datosEmpleado.paterno, datosEmpleado.idEmpleado, tipoBusqueda, campo)
	break
	
	default:
	KeywordUtil.markFailedAndStop("❌ No se pudo identificar el campo a editar.")
}




