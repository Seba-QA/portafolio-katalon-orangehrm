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

WebUI.callTestCase(findTestCase('Auth/Login reutilizable'), [
    ('username') : 'Admin',
    ('password') : 'hUKwJTbofgPU9eVlw/CnDQ=='
], FailureHandling.STOP_ON_FAILURE)


WebUI.comment('==> Validando PIM cargado')

// Valida visibilidad de PIM y da click
if(WebUI.verifyElementVisible(findTestObject('Object Repository/Dashboard object/a_PIM'))) {
	WebUI.comment('✓ PIM visible')
	WebUI.click(findTestObject('Object Repository/Dashboard object/a_PIM'))
}else {
	KeywordUtil.markFailed('❌ PIM no se pudo encontrar')
}

// Valida redireccion a URL correcta
String urlActual = WebUI.getUrl()

if (urlActual == 'https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList') {
    WebUI.comment('✓ Redirigió correctamente al PIM')
} else {
    KeywordUtil.markFailed("❌ Redirección incorrecta. URL obtenida: '$urlActual'")
}

// Valida titulo "Employee Information"
Object titulo = findTestObject('Object Repository/PIM object/h5_Employee Information')
if (WebUI.verifyElementVisible(titulo, FailureHandling.OPTIONAL)) {
    String titleText = WebUI.getText(titulo).trim()
    if (titleText.equalsIgnoreCase('Employee Information')) {
        WebUI.comment("✓ Título '$titleText' correcto")
    } else {
        KeywordUtil.markFailed("❌ Título incorrecto. Título obtenido: '$titleText'")
    }
} else {
    KeywordUtil.markFailed("❌ El título no fue encontrado")
}


// Valida botones "searh" y "add" visibles
WebUI.click(findTestObject('Object Repository/PIM object/button_Employee Information_oxd-icon-button'))
Object search = findTestObject('Object Repository/PIM object/button_Search')
Object add = findTestObject('Object Repository/PIM object/button_Add')

if (WebUI.verifyElementVisible(search) || WebUI.verifyElementVisible(add)) {
	WebUI.comment("✓ Botones visibles")
}else {
	KeywordUtil.markFailed("❌ Los botones no fueron encontrados")
}





