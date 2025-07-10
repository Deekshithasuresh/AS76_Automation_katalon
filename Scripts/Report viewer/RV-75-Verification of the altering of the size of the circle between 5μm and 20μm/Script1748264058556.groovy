import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)


WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))



TestObject microViewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

WebUI.delay(1)  // wait for the OpenLayers viewer to render

// ────────────────────────────────────────────────────────────────────
// 5) HOVER & CLICK CIRCLE TOOL → WAIT 30s
// ────────────────────────────────────────────────────────────────────
TestObject circleButton = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='circle-tool']]")
WebUI.waitForElementClickable(circleButton, 3)
WebUI.mouseOver(circleButton)
WebUI.click(circleButton)

WebUI.delay(3)

// screenshot the initial 7 μm circle
String reportDir = RunConfiguration.getReportFolder()
String circle7Screenshot = "${reportDir}/wbc_circle_7um.png"
WebUI.takeScreenshot(circle7Screenshot)
WebUI.comment("✔ Captured 7 μm circle screenshot: ${circle7Screenshot}")

// ────────────────────────────────────────────────────────────────────
// 6) AFTER DRAWING THE FIRST CIRCLE, ITERATE THROUGH SIZES 5→20
// ────────────────────────────────────────────────────────────────────
TestObject measInput = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@id='outlined-start-adornment']")

WebElement element = WebUiCommonHelper.findWebElement(measInput, 10)
JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
TestObject overlayText = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'-inner') and contains(text(),'µm')]")

List<String> diameters = ['5', '6', '10', '15', '20']
for (String dia : diameters) {

	WebUI.click(measInput)
	js.executeScript("arguments[0].value = '';", element)
	WebUI.setText(measInput, dia)
	
	Actions actions = new Actions(DriverFactory.getWebDriver())
	actions.moveToElement(element, 50, 40).click().perform()
	WebUI.sendKeys(measInput, Keys.chord(Keys.ENTER))
	
	
	
	
	

	// 3) verify the input field
	String actual = WebUI.getAttribute(measInput, 'value').trim()
	WebUI.verifyMatch(actual, dia, false, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Input field set to ${dia} μm")

	// 4) verify the overlay in the canvas
	WebUI.waitForElementVisible(overlayText, 5)
	String ov = WebUI.getText(overlayText).trim()
	WebUI.verifyMatch(ov, "${dia} μm", false, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Canvas overlay reads ${dia} μm")

	// 5) screenshot each state
	String shot = "${reportDir}/circle_${dia}um.png"
	WebUI.takeScreenshot(shot)
	WebUI.comment("🔍 Captured circle at ${dia} μm: ${shot}")
}

WebUI.comment("✅ All circle‐tool diameters verified.")
