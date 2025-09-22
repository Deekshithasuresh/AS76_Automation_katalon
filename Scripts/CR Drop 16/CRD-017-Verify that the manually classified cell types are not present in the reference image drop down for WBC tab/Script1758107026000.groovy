import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under review")

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC'), 'WBC')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 5)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 'References')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_References'))


def manuallyClassifiedCells = [
	"Immature Eosinophils*",
	"Immature Basophils*",
	"Promonocytes*",
	"Prolymphocytes*",
	"Hairy Cells*",
	"Sezary Cells*",
	"Plasma Cells*",
	"Others*"
]

// Open the dropdown
WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/div_dropdown')) // Adjust TestObject name

for(String cell : manuallyClassifiedCells) {
	TestObject optionObj = new TestObject(cell)
	optionObj.addProperty(
		"xpath",
		com.kms.katalon.core.testobject.ConditionType.EQUALS,
		"//li[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '${cell.toLowerCase()}')]"
	)
	// Option should NOT be present
	boolean isPresent = WebUI.verifyElementNotPresent(optionObj, 3, FailureHandling.CONTINUE_ON_FAILURE)
	assert isPresent : "${cell} should NOT be present in dropdown!"
	WebUI.comment("${cell} not present as expected.")
}

TestObject closeBtn = findTestObject('Object Repository/CR_Drop_16/Page_PBS/button_References_close-btn')
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(closeBtn)))
WebUI.back()