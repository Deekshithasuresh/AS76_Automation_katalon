import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 4) WAIT FOR MICROSCOPE ICON AND CLICK IT
TestObject microViewIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'microscope-button') or @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.verifyElementVisible(microViewIcon, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Microscopic view icon is present")
WebUI.click(microViewIcon)

// 5) VERIFY THE CIRCLE TOOL ICON IS PRESENT
TestObject circleToolBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@aria-label='circle-tool' or contains(@class,'circle-tool-button')]"
)
WebUI.waitForElementVisible(circleToolBtn, 10)
WebUI.verifyElementClickable(circleToolBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Circle tool icon is present")
