import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

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



// 8) Click on the Microscopic‐view icon
TestObject microViewIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)

// 7) VERIFY CELL NAMES ARE LISTED
List<String> cellNames = ['Large Platelets', 'Platelet Clumps']
cellNames.each { name ->
	WebUI.verifyTextPresent(name, false, FailureHandling.CONTINUE_ON_FAILURE)
}



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
