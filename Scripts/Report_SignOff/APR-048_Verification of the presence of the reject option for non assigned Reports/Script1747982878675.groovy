import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

def selectReportByStatus(String status) {
	WebDriver driver = DriverFactory.getWebDriver()
	JavascriptExecutor js = (JavascriptExecutor) driver
	WebUI.delay(5)

	// 1) Wait for table to appear
	//WebUI.waitForElementVisible(findTestObject('Object Repository/Page_PBS/th_Slide Id'), 15)

	// 2) Scroll container to load all rows
	String scrollSelector = "#reportListingTable > div > div"
	int scrollStep = 300
	int delaySeconds = 1
	int maxTries = 100
	int tries = 0

	while (tries < maxTries) {
		def result = js.executeScript("""
			const el = document.querySelector(arguments[0]);
			const before = el.scrollTop;
			el.scrollBy(0, arguments[1]);
			return {
				scrollTop: el.scrollTop,
				scrollHeight: el.scrollHeight,
				clientHeight: el.clientHeight,
				before: before
			};
		""", scrollSelector, scrollStep)

		long scrollTop = result.scrollTop
		long scrollHeight = result.scrollHeight
		long clientHeight = result.clientHeight
		long before = result.before

		KeywordUtil.logInfo("Scroll position: ${scrollTop} / ${scrollHeight - clientHeight}")
		WebUI.delay(delaySeconds)

		if (scrollTop >= (scrollHeight - clientHeight - 10)) {
			KeywordUtil.logInfo("Reached bottom of scroll container.")
			break
		}
		if (scrollTop == before) {
			KeywordUtil.logInfo("ScrollTop unchanged. Possibly stuck. Breaking.")
			break
		}
		tries++
	}

	// 3) Count total rows loaded
	String rowXPath = "//*[@id='reportListingTable']/div/div/div/tbody/tr"
	int totalRows = driver.findElements(By.xpath(rowXPath)).size()
	KeywordUtil.logInfo("✅ Scrolling complete. Total rows loaded: ${totalRows}")

	// 4) Find and click first row matching the status
	try {
		String xpath = "(//tr[.//td[contains(normalize-space(), '${status}')]])[1]"
		TestObject matchingRow = new TestObject('dynamicReportRow')
		matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
		WebUI.waitForElementClickable(matchingRow, 10)
		WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
		rowElement.click()
		WebUI.comment(":white_check_mark: Clicked first row with status = '${status}'")
	}
	catch (Exception e) {
		String xpath = "(//tr[.//td[contains(normalize-space(), 'To be reviewed')]])[1]"
		TestObject matchingRow = new TestObject('dynamicReportRow')
		matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
		WebUI.waitForElementClickable(matchingRow, 10)
		WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
		rowElement.click()
		WebUI.comment(":white_check_mark: Clicked first row with status = 'To be reviewed' there is no to be reviewd report")
	}
}
// Define the TestObject for the "Reject report" button
TestObject rejectButton = new TestObject("RejectButton")
rejectButton.addProperty("xpath", ConditionType.EQUALS, "//button[contains(., 'Reject report')]")

// Wait briefly for page load
WebUI.delay(3)

// Check that the button is NOT present
boolean isRejectButtonPresent = WebUI.verifyElementNotPresent(rejectButton, 5, FailureHandling.OPTIONAL)

if (isRejectButtonPresent) {
	WebUI.comment("✅ 'Reject report' button is NOT present as expected for non-assigned report.")
} else {
	WebUI.comment("❌ 'Reject report' button IS visible for non-assigned report, which is unexpected.")
}


//CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')

//WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PBS/button_Reject report'), 0)

//WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/button_Reject report'), 'Reject report')

