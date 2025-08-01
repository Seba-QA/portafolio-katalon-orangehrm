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

WebUI.comment('==> Validando Acceso a PIM')

// Valida ingreso a My info
Object myInfo = findTestObject('Object Repository/Dashboard object/a_My Info')

if(WebUI.verifyElementVisible(myInfo, FailureHandling.OPTIONAL)) {
	KeywordUtil.logInfo('✓ My info visible')
	WebUI.click(myInfo)
}else {
	KeywordUtil.markFailed('❌ No se encontro Mi info')
}

// Validacion de URL
String url = 'https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/7'
String myInfoUrl = WebUI.getUrl()
if (url == myInfoUrl) {
	// Valida titulo "Personal Details"
	Object title = findTestObject('Object Repository/My info object/h6_Personal Details')
	String titleText = WebUI.getText(title)
	if (WebUI.verifyElementVisible(title, FailureHandling.OPTIONAL) && titleText.trim().equalsIgnoreCase('Personal Details')) {
		KeywordUtil.logInfo("✓ El titulo '$titleText' es correcto")
	}else {
		KeywordUtil.markFailed("❌ El titulo '$titleText' es incorrecto")
	}
}else {
	KeywordUtil.markFailed('❌ La URL no es la correcta')
}

// Cierra el navegador
WebUI.closeBrowser()
