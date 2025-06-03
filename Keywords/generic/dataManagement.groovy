package generic

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


public class dataManagement {

	@Keyword
	def loginAdmin() {
		WebUI.openBrowser('')

		WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

		WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Username_loginId'), 'manju')

		WebUI.setEncryptedText(findTestObject('Object Repository/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

		WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Sign in'))

		WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/h2_Admin Portal'), 'Admin Portal')

		WebUI.waitForPageLoad(5)
	}
}


