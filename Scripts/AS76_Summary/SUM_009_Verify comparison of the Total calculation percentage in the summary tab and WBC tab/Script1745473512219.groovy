import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG013'))

// Wait for page to load, if necessary
WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Summary'), 10)

// Extract Summary total as string and convert to float
String summaryTotalStr = WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_100.0'))
Float summaryTotal = summaryTotalStr.toFloat()

// Go to WBC tab
WebUI.click(findTestObject('Object Repository/Summary/span_WBC'))

// Extract WBC total as string and convert to float
String wbcTotalStr = WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_100'))
Float wbcTotal = wbcTotalStr.toFloat()

// Compare both values and log the result
if (summaryTotal == wbcTotal) {
	WebUI.comment("✅ Summary total ($summaryTotal) matches WBC total ($wbcTotal)")
} else {
	WebUI.comment("❌ Mismatch: Summary total ($summaryTotal) does NOT match WBC total ($wbcTotal)")
	WebUI.takeScreenshot()
	WebUI.verifyMatch(summaryTotal.toString(), wbcTotal.toString(), false)  // This will fail the test
}

WebUI.closeBrowser()


