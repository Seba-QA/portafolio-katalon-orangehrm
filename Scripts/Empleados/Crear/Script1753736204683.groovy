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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String timestamp = System.currentTimeMillis().toString()[-5..-1]
// Llena formulario
// ejemplo de acceso a los datos del map
//Nombre: datosEmpleado.nombre
Map datosEmpleado = [
	nombre      : 'Carlos',
	segundoNombre : 'Andrés',
	paterno     : 'González',
	idEmpleado  : System.currentTimeMillis().toString()[-8..-1],
	user : "usuario${timestamp}",
	pass : 'a123456',
	passCifrado : "ZUgKSRs8gNk=",
	status: 'Enabled'
	//status: 'Enabled'
]

// Se ejecuta login con usuario administrador para crear empleados
WebUI.callTestCase(findTestCase('Auth/Login reutilizable'), [
    ('username') : 'Admin',
    ('password') : 'hUKwJTbofgPU9eVlw/CnDQ=='
], FailureHandling.STOP_ON_FAILURE)


WebUI.comment('==> Validando crear empleados')

// Llena datos para usuario
def creaUsuario (String user, String pass, String status) {
	WebUI.click(findTestObject('Object Repository/PIM object/Crear/span login details'))
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input Username'), user)
	switch (status) {
		case "Enabled":
		WebUI.click(findTestObject('Object Repository/PIM object/Crear/label_Enabled'))
		break
		
		case "Disabled":
		WebUI.click(findTestObject('Object Repository/PIM object/Crear/label_Disabled'))
		break
		
		default:
		KeywordUtil.markFailed("❌ No se pudo identificar el status: $status")
		break
	}
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Password'), pass)
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Confirm'), pass)
	WebUI.comment('==> Crea usuario con')
	WebUI.comment("==> User: $user")
	WebUI.comment("==> User: $pass")
}

def login(String user, String pass) {
	WebUI.comment("==> User: $user")
	WebUI.comment("==> User: $pass")
	WebUI.callTestCase(findTestCase('Auth/Login reutilizable'), [
		('username') : user,
		('password') : pass
	], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.comment("✓ Inicio de sesion correcto")
}
// Ingresa a "add Employee"
WebUI.click(findTestObject('Object Repository/Dashboard object/a_PIM'))
WebUI.click(findTestObject('Object Repository/PIM object/button_Add'))

// Seleccionar flujo con todos los datos o solo los obligatorios
// True = flujo con todos los datos
// False = flujo solo con datos obligatorios
// Ambos bool son dependientes
boolean tipoFlujo = true
//boolean crearEmpleado = false
boolean validarEmpleado = true
//boolean validarEmpleado = false

// Seleccionar si flujo tendra usuario para login
boolean crearUsuarioLogin = true
//boolean crearUsuarioLogin = false

// Variables
String id = ""

// Valida si el flujo va con todos los datos o datos obligatorios y llena el formulario con los datos correspondientes
switch (tipoFlujo) {
	case true:
	WebUI.comment("▶ Ejecutando flujo con todos los datos")
	// Código para flujo con todos los datos
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Employee Full Name_firstName'), datosEmpleado.nombre)
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Employee Full Name_middleName'), datosEmpleado.segundoNombre)
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Employee Full Name_lastName'), datosEmpleado.paterno)
    WebUI.sendKeys(findTestObject('Object Repository/PIM object/Crear/input_Employee Id_oxd-input oxd-input--active'), Keys.chord(Keys.CONTROL, 'a'))
	WebUI.sendKeys(findTestObject('Object Repository/PIM object/Crear/input_Employee Id_oxd-input oxd-input--active'), Keys.chord(Keys.BACK_SPACE))
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Employee Id_oxd-input oxd-input--active'), datosEmpleado.idEmpleado)
	if (crearUsuarioLogin) {
		creaUsuario(datosEmpleado.user, datosEmpleado.pass, datosEmpleado.status)
		WebUI.click(findTestObject('Object Repository/PIM object/Crear/button_Save'))
	}else {
		WebUI.click(findTestObject('Object Repository/PIM object/Crear/button_Save'))
	}
	break
	

	case false:
	WebUI.comment("▶ Ejecutando flujo con datos obligatorios")
	// Código para flujo con todos los datos
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Employee Full Name_firstName'), datosEmpleado.nombre)
	WebUI.setText(findTestObject('Object Repository/PIM object/Crear/input_Employee Full Name_lastName'), datosEmpleado.paterno)
	id = WebUI.executeJavaScript(
		"return document.querySelectorAll('input.oxd-input.oxd-input--active')[3].value",
		null
	)
	if (crearUsuarioLogin) {
		creaUsuario(datosEmpleado.user, datosEmpleado.pass, datosEmpleado.status)
		WebUI.click(findTestObject('Object Repository/PIM object/Crear/button_Save'))
	}else {
		WebUI.click(findTestObject('Object Repository/PIM object/Crear/button_Save'))
	}
	break
	
	default:
	KeywordUtil.markFailed("❌ Tipo de flujo desconocido: $tipoFlujo")
	break
}

// Valida la correcta creacion del empleado
switch (validarEmpleado) {
	case true:
	WebUI.comment("▶ Ejecutando validacion de todos los datos")
	// Se capturan los datos registrados del empleado
	int reintentosMax = 3
	int delayEntreIntentos = 5
	int intento = 0
	boolean visible = false
	
	while (intento < reintentosMax) {
		WebUI.delay(delayEntreIntentos)
		Object campoNombre = findTestObject('Object Repository/PIM object/Modificar/div_Personal DetailsEmployee')
		if (WebUI.verifyElementVisible(campoNombre, FailureHandling.OPTIONAL)) {
			WebUI.delay(delayEntreIntentos)
			visible = true
			WebUI.comment("✓ Objeto visible tras ${intento + 1} intento(s)")
			String nombre = WebUI.getAttribute(findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_firstName'), 'value')
			WebUI.comment("${nombre}")
			String segundoNombre = WebUI.getAttribute(findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_middleName'), 'value')
			WebUI.comment("${segundoNombre}")
			String paterno = WebUI.getAttribute(findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_lastName'), 'value')
			WebUI.comment("${paterno}")
			id = WebUI.executeJavaScript(
			    "return document.querySelectorAll('input.oxd-input.oxd-input--active')[4].value",
			    null
			)
			WebUI.comment("${id}")
			
			if (nombre == datosEmpleado.nombre) {
				WebUI.comment("✓ Nombre: '$datosEmpleado.nombre' correcto")
			}else {
				WebUI.comment("✓ Nombre campo: '$nombre'")
				KeywordUtil.markFailed("❌ Nombre: '$datosEmpleado.nombre' incorrecto")
			}
			
			if (segundoNombre == datosEmpleado.segundoNombre) {
				WebUI.comment("✓ Segundo Nombre: '$datosEmpleado.segundoNombre' correcto")
			}else {
				KeywordUtil.markFailed("❌ Segundo Nombre: '$datosEmpleado.segundoNombre' incorrecto")
			}
			
			if (paterno == datosEmpleado.paterno) {
				WebUI.comment("✓ Apellido paterno: '$datosEmpleado.paterno' correcto")
			}else {
				KeywordUtil.markFailed("❌ Apellido paterno: '$datosEmpleado.paterno' incorrecto")
			}
			
			if (id == datosEmpleado.idEmpleado) {
				WebUI.comment("✓ id Empleado: '$datosEmpleado.idEmpleado' correcto")
			}else {
				KeywordUtil.markFailed("❌ id Empleado: '$datosEmpleado.idEmpleado' incorrecto")
			}
			if (crearUsuarioLogin) {
				login(datosEmpleado.user, datosEmpleado.passCifrado)
				WebUI.comment("✓ pass: '$datosEmpleado.pass'")
				WebUI.comment("✓ passCifrado: '$datosEmpleado.passCifrado'")
			}
			break
		} else {
			WebUI.comment("⏳ Intento ${intento + 1}: Objeto aún no visible. Esperando ${delayEntreIntentos}s...")
			WebUI.delay(delayEntreIntentos)
			intento++
			if (intento > 2) {
				KeywordUtil.markFailed("❌ No se pudo encontrar los campos requeridos")
			}
		}
	}
	break
	
	case false:
	WebUI.comment("▶ Ejecutando validacion de datos obligatorios")
	// Se capturan los datos registrados del empleado
	int reintentosMax = 3
	int delayEntreIntentos = 5
	int intento = 0
	boolean visible = false
	
	while (intento < reintentosMax) {
		WebUI.delay(delayEntreIntentos)
		Object campoNombre = findTestObject('Object Repository/PIM object/Modificar/div_Personal DetailsEmployee')
		if (WebUI.verifyElementVisible(campoNombre, FailureHandling.OPTIONAL)) {
			WebUI.delay(delayEntreIntentos)
			visible = true
			WebUI.comment("✓ Objeto visible tras ${intento + 1} intento(s)")
			String nombre = WebUI.getAttribute(findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_firstName'), 'value')
			String paterno = WebUI.getAttribute(findTestObject('Object Repository/PIM object/Modificar/input_Employee Full Name_lastName'), 'value')
			idLeido = WebUI.executeJavaScript(
				"return document.querySelectorAll('input.oxd-input.oxd-input--active')[4].value",
				null
			)
			
			if (nombre == datosEmpleado.nombre) {
				WebUI.comment("✓ Nombre: '$datosEmpleado.nombre' correcto")
			}else {
				WebUI.comment("✓ Nombre campo: '$nombre'")
				KeywordUtil.markFailed("❌ Nombre: '$datosEmpleado.nombre' incorrecto")
			}
			
			if (paterno == datosEmpleado.paterno) {
				WebUI.comment("✓ Apellido paterno: '$datosEmpleado.paterno' correcto")
			}else {
				KeywordUtil.markFailed("❌ Apellido paterno: '$datosEmpleado.paterno' incorrecto")
			}
			
			if (id == idLeido) {
				WebUI.comment("✓ id Empleado: '$idLeido' correcto")
			}else {
				KeywordUtil.markFailed("❌ id Empleado: '$idLeido' incorrecto")
			}
			if (crearUsuarioLogin) {
				login(datosEmpleado.user, datosEmpleado.passCifrado)
				WebUI.comment("✓ pass: '$datosEmpleado.pass'")
				WebUI.comment("✓ passCifrado: '$datosEmpleado.passCifrado'")
			}
			break
		} else {
			WebUI.comment("⏳ Intento ${intento + 1}: Objeto aún no visible. Esperando ${delayEntreIntentos}s...")
			WebUI.delay(delayEntreIntentos)
			intento++
			if (intento > 2) {
				KeywordUtil.markFailed("❌ No se pudo encontrar los campos requeridos")
			}
		}
	}
	break
	
	default:
	KeywordUtil.markFailed("❌ Tipo de flujo desconocido: $validarEmpleado")
	break
}

// Cierra el navegador
WebUI.closeBrowser()