import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords.*

import org.openqa.selenium.WebDriver

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import platelet_Package.Platelet_P


Login lg = new Login()

Platelet_P plt = new Platelet_P()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

lg.assignOrReassignOnTabs('premkumar')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'))

WebDriver driver = DriverFactory.getWebDriver()


//Calculated estimate
plt.selectRadioOption("Calculated estimate");

assert plt.isOptionDisabled("Calculated level")== false
assert plt.isOptionDisabled("Manual level") == false

//assert plt.isCalculatedLevelTextVisible()== false
//assert plt.isManualLevelDropDownVisible()== false

// Check calculated level text
try {
	boolean isVisible = plt.isCalculatedLevelTextVisible()
	verifyEqual(isVisible, false, FailureHandling.CONTINUE_ON_FAILURE)
} catch (Exception e) {
	KeywordUtil.markPassed("Exception while checking calculated level text: " + e.getMessage())
}

// Check manual level dropdown
try {
	boolean isManualVisible = plt.isManualLevelDropDownVisible()
	verifyEqual(isManualVisible, false, FailureHandling.CONTINUE_ON_FAILURE)
} catch (Exception e) {
	KeywordUtil.markPassed("Exception while checking manual level dropdown: " + e.getMessage())
}



//Manual level
plt.selectRadioOption("Manual level");
WebUI.delay(2)
assert plt.isOptionDisabled("Calculated level")== false
assert plt.isOptionDisabled("Calculated estimate") == false

//assert plt.isManualLevelDropDownVisible()== true
//assert plt.isCalculatedLevelTextVisible()== false

// Check calculated level text
try {
	boolean isVisible = plt.isCalculatedLevelTextVisible()
	verifyEqual(isVisible, false, FailureHandling.CONTINUE_ON_FAILURE)
} catch (Exception e) {
	KeywordUtil.markPassed("Exception while checking calculated level text: " + e.getMessage())
}

//// Check manual level dropdown
//try {
//	boolean isManualVisible = plt.isManualLevelDropDownVisible()
//	verifyEqual(isManualVisible, true, FailureHandling.CONTINUE_ON_FAILURE)
//} catch (Exception e) {
//	KeywordUtil.markPassed("Exception while checking manual level dropdown: " + e.getMessage())
//}



//calculated level
plt.selectRadioOption("Calculated level");

assert plt.isOptionDisabled("Manual level")== false
assert plt.isOptionDisabled("Calculated estimate") == false

//assert plt.isCalculatedLevelTextVisible()== true
//assert plt.isManualLevelDropDownVisible()== false

// Check calculated level text
try {
	boolean isVisible = plt.isCalculatedLevelTextVisible()
	verifyEqual(isVisible, true, FailureHandling.CONTINUE_ON_FAILURE)
} catch (Exception e) {
	KeywordUtil.markPassed("Exception while checking calculated level text: " + e.getMessage())
}

// Check manual level dropdown
try {
	boolean isManualVisible = plt.isManualLevelDropDownVisible()
	verifyEqual(isManualVisible, false, FailureHandling.CONTINUE_ON_FAILURE)
} catch (Exception e) {
	KeywordUtil.markPassed("Exception while checking manual level dropdown: " + e.getMessage())
}

