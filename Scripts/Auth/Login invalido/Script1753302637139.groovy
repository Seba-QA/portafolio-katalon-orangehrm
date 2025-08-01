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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login')

// Esperar a que el botón de login sea visible como indicio de carga
WebUI.waitForElementVisible(findTestObject('Auth object/button_Login'), 2)

// Ingresa user
WebUI.setText(findTestObject('Auth object/input_Username_username'), 'Admin')

// Ingresa contraseña incorrecta "1234"
WebUI.setEncryptedText(findTestObject('Auth object/input_Password_password'), '4nvbrPglk7k=')

// Click en el botón de login (no se valida visibilidad aquí)
WebUI.click(findTestObject('Auth object/button_Login'))

// Valida si el div del mensaje esta visible
if (WebUI.verifyElementVisible(findTestObject('Auth object/div_Invalid credentials'), FailureHandling.OPTIONAL)) {
    KeywordUtil.logInfo('✓ La card de mensaje de error visible.')

    String mensaje = WebUI.getText(findTestObject('Object Repository/Auth object/p_Invalid credentials'))

    if (mensaje.trim().equalsIgnoreCase('Invalid credentials')) {
        KeywordUtil.logInfo('✓ Mensaje de error correcto mostrado.')
    } else {
        KeywordUtil.markFailed("⚠️ Mensaje inesperado: Se recibió '$mensaje' en lugar de 'Invalid credentials'.")
    }
} else {
    KeywordUtil.markFailed('❌ La card del mensaje de error no aparece')
}

// Cerrar navegador
WebUI.closeBrowser()

