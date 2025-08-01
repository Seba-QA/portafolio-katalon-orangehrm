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

// Abrir navegador y navegar al sitio
WebUI.openBrowser('')

WebUI.navigateToUrl('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login')

// Esperar a que el botón de login sea visible como indicio de carga
WebUI.waitForElementVisible(findTestObject('Auth object/button_Login'), 2)

// Validar campo de usuario visible
if (WebUI.verifyElementVisible(findTestObject('Auth object/input_Username_username'), FailureHandling.OPTIONAL)) {
    WebUI.setText(findTestObject('Auth object/input_Username_username'), 'Admin')
} else {
    KeywordUtil.markFailed('❌ No se encontró el campo de usuario en pantalla. Verifica que esté cargado correctamente.')
}

// Validar campo de contraseña visible
if (WebUI.verifyElementVisible(findTestObject('Auth object/input_Password_password'), FailureHandling.OPTIONAL)) {
    WebUI.setEncryptedText(findTestObject('Auth object/input_Password_password'), 'hUKwJTbofgPU9eVlw/CnDQ==')
} else {
    KeywordUtil.markFailed('❌ El campo de contraseña no está visible. Podría haber fallado la carga del sitio.')
}

// Click en el botón de login (no se valida visibilidad aquí)
WebUI.click(findTestObject('Auth object/button_Login'))

// Validar campo username
if (WebUI.verifyElementVisible(findTestObject('Object Repository/Dashboard object/p_manda user'), FailureHandling.OPTIONAL)) {
	KeywordUtil.logInfo('✓ El campo de username está visible.')
	// Validar menu lateral
	if (WebUI.verifyElementVisible(findTestObject('Object Repository/Dashboard object/div_AdminPIMLeaveTimeRecruitmentMy InfoPerformanceDashboardDirectoryMaintenanceClaimBuzz'), FailureHandling.OPTIONAL)) {
		KeywordUtil.logInfo('✓ El menu lateral está visible.')
		KeywordUtil.logInfo('✓ Login completo')
	} else {
		KeywordUtil.markFailed('❌ El menu lateral no está visible. Podría haber fallado la carga del sitio.')
	}
} else {
	KeywordUtil.markFailed('❌ El campo de username no está visible. Podría haber fallado la carga del sitio.')
}

// Cerrar navegador
WebUI.closeBrowser()

