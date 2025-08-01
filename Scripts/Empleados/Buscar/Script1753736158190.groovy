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
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory

// Completa formulario de busqueda
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
}

// Valida al usar boton reset desaparezcan los campos
def reset() {
	WebUI.click(findTestObject('Object Repository/PIM object/Buscar/button_Reset'))
	String nombre = WebUI.getAttribute(findTestObject('Object Repository/PIM object/Buscar/input_name'), 'value')
	String id = WebUI.getAttribute(findTestObject('Object Repository/PIM object/Buscar/input_Id'), 'value')
	if (nombre.trim().isEmpty() || id.trim().isEmpty()) {
		WebUI.comment('✓ Reset borro correcamente el contenido')
	}else {
		KeywordUtil.markFailed("❌ Los valores no se borraron")
	}
}

// Valida cuantos empleados encuentra y da mensaje correspondiente
def validarResultadoBusqueda(String tipoBusqueda) {
	WebUI.delay(3)
	int cantidadResultados = WebUI.executeJavaScript(
	    "return document.querySelectorAll('div.oxd-table-card').length;",
	    null
	)

	WebUI.comment("🔎 Resultados encontrados: $cantidadResultados")

	if (cantidadResultados == 1) {
		WebUI.comment("✅ Solo un empleado encontrado.")
	} else if (cantidadResultados > 1) {
		if (tipoBusqueda == 'nombre') {
			KeywordUtil.markWarning("⚠️ Se encontraron múltiples empleados con el mismo nombre. Considera buscar por ID.")
			WebUI.comment("⛔ Prueba detenida: múltiples coincidencias.")
			WebUI.closeBrowser()
			KeywordUtil.markFailedAndStop("❌ Resultado ambiguo: múltiples empleados con el mismo nombre.")
		} else {
			WebUI.comment("⛔ Se encontraron múltiples empleados con el mismo ID. Esto no debería suceder.")
			WebUI.closeBrowser()
			KeywordUtil.markFailedAndStop("❌ Error crítico: ID duplicado.")
		}
	} else {
		KeywordUtil.markFailedAndStop("❌ No se encontró ningún empleado.")
	}
}


WebUI.callTestCase(findTestCase('Empleados/Validar carga PIM'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.comment('==> Validando buscar empleados')

// Captura id y nombre del primer empleado
WebUI.waitForElementVisible(findTestObject('PIM object/h5_Employee Information'), 10)
WebUI.delay(2)

// Extraer datos del primer empleado
Map datos = WebUI.executeJavaScript('''
    const fila = document.querySelector('div.oxd-table-card');
    if (!fila) return null;

    const texto = fila.innerText.split("\\n");
    let resultado = {
        id: '',
        nombre: '',
        apellido: ''
    };

    for (let i = 0; i < texto.length; i++) {
        if (texto[i].includes('Id')) {
            resultado.id = texto[i + 1];
        }
        if (texto[i].includes('First')) {
            resultado.nombre = texto[i + 1];
        }
        if (texto[i].includes('Last')) {
            resultado.apellido = texto[i + 1];
        }
    }
    return resultado;
''', null)

// Si el mapa tiene los valores, los mostramos
//WebUI.comment("🆔 ID empleado: ${datos['id']}")
//WebUI.comment("👤 Nombre: ${datos['nombre']}")
//WebUI.comment("👥 Apellido: ${datos['apellido']}")

//=\
//=== Flujo con llenado de formulario
//=/

// Variable para seleccionar la opción de búsqueda
// Cambia a 'nombre', 'id', o 'ambos' según sea necesario
//String opcionBusqueda = 'id'
//String opcionBusqueda = 'nombre'
String opcionBusqueda = 'ambos' 

switch (opcionBusqueda) {
    case 'nombre':
        WebUI.comment("Buscando solo por nombre...")
        formSearch(datos['nombre'], "")
        reset() // Valida que el botón de reset funciona
		formSearch(datos['nombre'], "")
		WebUI.click(findTestObject('Object Repository/PIM object/Buscar/button_Search'))
		validarResultadoBusqueda(opcionBusqueda)
        break
        
    case 'id':
        WebUI.comment("Buscando solo por ID...")
        formSearch("", datos['id'])
        reset() // Valida que el botón de reset funciona
		formSearch("", datos['id'])
		WebUI.click(findTestObject('Object Repository/PIM object/Buscar/button_Search'))
		validarResultadoBusqueda(opcionBusqueda)
        break
        
    case 'ambos':
        WebUI.comment("Buscando por nombre y ID...")
        formSearch(datos['nombre'], datos['id'])
        reset() // Valida que el botón de reset funciona
		formSearch(datos['nombre'], datos['id'])
		WebUI.click(findTestObject('Object Repository/PIM object/Buscar/button_Search'))
		validarResultadoBusqueda(opcionBusqueda)
        break
        
    default:
		KeywordUtil.markFailed("❌ Opción de búsqueda no válida. Por favor, elige 'nombre', 'id' o 'ambos'")
        break
}