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

// Modulos con logica
def eliminarEmpleado() {
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

	WebUI.delay(2)
	
	// Paso 5: Guarda el id del empleado
	String idEmpleado = WebUI.executeJavaScript('''
	const empleados = document.querySelectorAll('div.oxd-table-card');
	if (empleados.length > 0) {
		const texto = empleados[''' + indiceAleatorio + '''].innerText.split("\\n");
				for (let i = 0; i < texto.length; i++) {
					if (texto[i].includes('Id')) {
						return texto[i + 1];
					}
				}
			}
			return null;
		''', null)

	
	// Paso 6: Click en botón eliminar del empleado seleccionado y confirma eliminacion
	WebUI.executeJavaScript('''
			const empleados = document.querySelectorAll('div.oxd-table-card');
			if (empleados.length > 0) {
				const botones = empleados[''' + indiceAleatorio + '''].querySelectorAll('button');
	        if (botones.length > 0) {
	            botones[1].click(); // 1 = botón eliminar
	        }
	    }
	''', null)
	WebUI.comment("✏️ Click en botón de eliminar del empleado [$indiceAleatorio] realizado.")
	WebUI.delay(1)
	TestObject botonConfirmar = findTestObject('Object Repository/PIM object/Eliminar/button_Yes, Delete')
	
	if (WebUI.verifyElementVisible(botonConfirmar, FailureHandling.STOP_ON_FAILURE)) {
		WebUI.waitForElementClickable(botonConfirmar, 5)
		WebUI.scrollToElement(botonConfirmar, 2)
		WebUI.delay(1)
		WebUI.comment("🔘 Intentando hacer clic en el botón de confirmación de eliminación...")
		WebUI.executeJavaScript(
			"document.querySelector('button.oxd-button--label-danger').click();",
			null
		)
		//WebUI.click(botonConfirmar)
	} else {
		KeywordUtil.markFailed("❌ Mensaje de confirmación no es visible")
	}

	
	if(WebUI.verifyElementVisible(findTestObject('Object Repository/PIM object/Eliminar/div_SuccessSuccessfully Deleted'), FailureHandling.STOP_ON_FAILURE)) {
		WebUI.comment('✓ Mensaje de confirmacion se despliega correctamente')
	}else {
		KeywordUtil.markFailed("❌ No se desplego mensaje de eliminacion confirmada")
	}
	
	return idEmpleado
}

def buscaEmpleado(String id) {
	WebUI.delay(1)
	WebUI.scrollToElement(findTestObject('Object Repository/PIM object/Buscar/input_Id'), 3)
	WebUI.setText(findTestObject('Object Repository/PIM object/Buscar/input_Id'), id)
	WebUI.click(findTestObject('Object Repository/PIM object/button_Search'))
	if (WebUI.verifyElementVisible(findTestObject('Object Repository/PIM object/Eliminar/div_InfoNo Records Found'), FailureHandling.STOP_ON_FAILURE)) {
		WebUI.comment('✓ La eliminacion del empleado fue exitosa')
	}else {
		KeywordUtil.markFailed("❌ El empleado no se borro correctamente")
	}
}
WebUI.callTestCase(findTestCase('Empleados/Validar carga PIM'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.comment('==> Validando eliminar empleados')

idEmpleado = eliminarEmpleado()
buscaEmpleado(idEmpleado)