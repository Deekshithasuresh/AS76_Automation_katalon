import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// At top of your test case
//@SetUp
//def setup() {
//	binding.setVariable("TESTMO_CASE_ID", "C1622")  // Map to Testmo manual case
//}




WebUI.openBrowser('')

WebUI.navigateToUrl('https://pbsreview.as76.local/login')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/logo_img'), 30)

String title = WebUI.getWindowTitle()

WebUI.verifyMatch(title, 'PBS', false)

