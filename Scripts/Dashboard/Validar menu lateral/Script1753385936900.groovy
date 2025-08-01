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

// Llama a test "Login reutilizable"
WebUI.callTestCase(findTestCase('Auth/Login reutilizable'), [
    ('username') : 'Admin',
    ('password') : 'hUKwJTbofgPU9eVlw/CnDQ=='
], FailureHandling.STOP_ON_FAILURE)


WebUI.comment('==> Validando dashboard cargado')

// Lista de elementos del menu lateral
def validarObjetosMenu() {
	List<TestObject> items = [
		findTestObject('Object Repository/Dashboard object/a_Admin'),
		findTestObject('Object Repository/Dashboard object/a_PIM'),
		findTestObject('Object Repository/Dashboard object/a_Leave'),
		findTestObject('Object Repository/Dashboard object/a_Time'),
		findTestObject('Object Repository/Dashboard object/a_Recruitment'),
		findTestObject('Object Repository/Dashboard object/a_My Info'),
		findTestObject('Object Repository/Dashboard object/a_Performance'),
		findTestObject('Object Repository/Dashboard object/a_Dashboard'),
		findTestObject('Object Repository/Dashboard object/a_Directory'),
		findTestObject('Object Repository/Dashboard object/a_Maintenance'),
		findTestObject('Object Repository/Dashboard object/a_Claim'),
		findTestObject('Object Repository/Dashboard object/a_Buzz')
	]

	boolean todosVisibles = true

	for (item in items) {
		if (WebUI.verifyElementVisible(item, FailureHandling.OPTIONAL)) {
			WebUI.comment("✓ Visible: ${item.getObjectId()}")
		} else {
			KeywordUtil.markFailed("❌ NO visible: ${item.getObjectId()}")
			todosVisibles = false
		}
	}

	return todosVisibles
}

def validaMenu() {
	boolean resultado = validarObjetosMenu()
	if (resultado) {
		WebUI.comment("✓ Todos los ítems del menú están visibles.")
	} else {
		KeywordUtil.markWarning("⚠️ Algunos ítems del menú NO están visibles.")
	}
}

// Valida todas las secciones del menu
validaMenu()

// Minimiza menu y valida que se realice - Flecha apunta a la derecha
WebUI.click(findTestObject('Object Repository/Dashboard object/left_arrow'))

String direccionRight = WebUI.getAttribute(findTestObject('Object Repository/Dashboard object/right_arrow'), 'class')
if(direccionRight.contains('right')) {
	WebUI.comment("✓ Minimizado correcto")
}else {
	KeywordUtil.markFailed("❌ Hubo una falla al Minimizar")
}

// Maximiza menu y valida que se realice - Flecha apunta a la derecha
WebUI.click(findTestObject('Object Repository/Dashboard object/right_arrow'))

String direccionLeft = WebUI.getAttribute(findTestObject('Object Repository/Dashboard object/left_arrow'), 'class')
if(direccionLeft.contains('left')) {
	WebUI.comment("✓ Maximizado correcto")
}else {
	KeywordUtil.markFailed("❌ Hubo una falla al Maximizar")
}

// Valida todas las secciones del menu
validaMenu()

// Cierra el navegador
WebUI.closeBrowser()
