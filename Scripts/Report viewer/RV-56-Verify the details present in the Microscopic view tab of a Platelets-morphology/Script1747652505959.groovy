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
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 4) Click on the Platelets tab
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='Platelets']]"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// 5) Click on the Morphology header
TestObject morphologyTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab' and normalize-space()='Morphology']"
)
WebUI.waitForElementClickable(morphologyTab, 10)
WebUI.click(morphologyTab)

// 6) WAIT FOR MORPHOLOGY CONTENT TO LOAD (e.g. the list container)
TestObject morphContainer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'platelet-morphology-list')]"
)
WebUI.waitForElementVisible(morphContainer, 10)

// 7) VERIFY CELL NAMES ARE LISTED
List<String> cellNames = ['Normal Platelets', 'Macro Platelets', 'Giant Platelets']
cellNames.each { name ->
	WebUI.verifyTextPresent(name, false, FailureHandling.CONTINUE_ON_FAILURE)
}

// 8) Click on the Microscopic‐view icon
TestObject microViewIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)

// 9) VERIFY DRAWING TOOLS AND CONTROLS
['line-tool','circle-tool','zoom-tool'].each { toolAlt ->
	WebUI.verifyElementPresent(
		new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//img[@alt='${toolAlt}']"),
		5, FailureHandling.CONTINUE_ON_FAILURE)
}

// home icon
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//div[img[@alt='home']]"),
	5, FailureHandling.CONTINUE_ON_FAILURE)

// zoom in/out buttons
['Zoom in','Zoom out','Overview'].each { title ->
	WebUI.verifyElementPresent(
		new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//button[@title='${title}']"),
		5, FailureHandling.CONTINUE_ON_FAILURE)
}

// the microscopic canvas itself
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//canvas"),
	5, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment("✔ Morphology tab and Microscopic view contain all expected details and tools.")
