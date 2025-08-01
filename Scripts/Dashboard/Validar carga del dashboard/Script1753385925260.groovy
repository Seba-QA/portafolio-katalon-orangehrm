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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.testobject.ObjectRepository as OR

// Llama a test "Login reutilizable"
WebUI.callTestCase(findTestCase('Auth/Login reutilizable'), [
    ('username') : 'Admin',
    ('password') : 'hUKwJTbofgPU9eVlw/CnDQ=='
], FailureHandling.STOP_ON_FAILURE)


WebUI.comment('==> Validando dashboard cargado')

// Valida url de dashboard
String urlActual = WebUI.getUrl()

if (urlActual == 'https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index') {
    WebUI.comment('✓ Redirigió correctamente al dashboard')
} else {
    KeywordUtil.markFailed("❌ Redirección incorrecta. URL obtenida: '$urlActual'")
}

// Valida el titulo del dasboard "Dashboard"
String titulo = WebUI.getText(findTestObject('Object Repository/Dashboard object/h6_Dashboard'))

if (titulo.trim().equalsIgnoreCase('Dashboard')) {
    KeywordUtil.logInfo('✓ Titulo correcto.')
} else {
    KeywordUtil.markFailed("⚠️ Titulo inesperado: Se recibió '$titulo' en lugar de 'Dashboard'.")
}

// Valida widget "Employee Distribution by Location"
TestObject widget = findTestObject('Object Repository/Dashboard object/div_Employee Distribution by Location')

// Verifica si el widget existe antes de hacer scroll
if (WebUI.verifyElementPresent(widget, 2, FailureHandling.OPTIONAL)) {
    WebUI.scrollToElement(widget, 2)
    
    if (WebUI.verifyElementVisible(widget, FailureHandling.OPTIONAL)) {
        WebUI.comment('✓ Existe widget Employee Distribution by Location')
    } else {
        KeywordUtil.markFailed('❌ El widget existe pero no es visible.')
    }
} else {
    KeywordUtil.markFailed('❌ No se encontró el widget Employee Distribution by Location en el DOM.')
}


// Cierra el navegador
WebUI.closeBrowser()

