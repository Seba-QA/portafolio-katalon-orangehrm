package helpers

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class Validaciones {

	/**
	 * Ejecuta una validación según el tipo:
	 * @param tipo: "visibilidad" o "css"
	 * @param objeto: TestObject
	 * @param palabraCSS: palabra a buscar dentro del atributo style (ej: "right" o "left")
	 */
	@Keyword
	def validarElemento(String tipo, TestObject objeto, String palabraCSS) {

		switch(tipo.toLowerCase()) {
			case "visibilidad":
				if (WebUI.verifyElementVisible(objeto, FailureHandling.OPTIONAL)) {
					WebUI.comment("✓ El objeto '${objeto.getObjectId()}' está visible.")
				} else {
					KeywordUtil.markFailed("❌ El objeto '${objeto.getObjectId()}' NO está visible.")
				}
				break

			case "css":
				if (!WebUI.verifyElementVisible(objeto, FailureHandling.OPTIONAL)) {
					KeywordUtil.markFailed("❌ El objeto '${objeto.getObjectId()}' no está visible. No se puede validar el CSS.")
					break
				}

				String estilo = WebUI.getAttribute(objeto, 'style')
				if (palabraCSS == 'right') {
					if (estilo.contains('right')) {
						WebUI.comment("✓ Menú lateral está minimizado (se encontró 'right' en style).")
					} else {
						KeywordUtil.markFailed("❌ El menú lateral no parece estar minimizado. No se encontró 'right' en style.")
					}
				} else if (palabraCSS == 'left') {
					if (estilo.contains('left')) {
						WebUI.comment("✓ Menú lateral está maximizado (se encontró 'left' en style).")
					} else {
						KeywordUtil.markFailed("❌ El menú lateral no parece estar maximizado. No se encontró 'left' en style.")
					}
				} else {
					KeywordUtil.markFailed("❌ Palabra no reconocida para validación CSS. Usa 'left' o 'right'.")
				}
				break

			default:
				KeywordUtil.markFailed("❌ Tipo de validación desconocido: '${tipo}'. Usa 'visibilidad' o 'css'.")
				break
		}
	}
}