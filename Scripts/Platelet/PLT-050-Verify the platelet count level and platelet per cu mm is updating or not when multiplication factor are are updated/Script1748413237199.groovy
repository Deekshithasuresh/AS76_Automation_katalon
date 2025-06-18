import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))
//Moving from Fov1 to Fov10 and verifying the values present or not and adding the values.,
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> FOV_rows = driver.findElements(By.className('fov-tuple'))

// === Update NMG values for FOV 1 to 10 ===
for (int i = 0; i < 10; i++) {
	FOV_rows.get(i).click()
	List<WebElement> NMG_fields = FOV_rows.get(i).findElements(By.xpath("//input[@class='fov-edit-input']"))

	// Log existing values before changing
	println("FOV ${i + 1} - Current NMG values:")
	println("  N: " + NMG_fields[0].getAttribute("value"))
	println("  M: " + NMG_fields[1].getAttribute("value"))
	println("  G: " + NMG_fields[2].getAttribute("value"))

	// Optional assertion if you want to ensure values are not blank
	assert NMG_fields[0].getAttribute("value").trim() != ""
	assert NMG_fields[1].getAttribute("value").trim() != ""
	assert NMG_fields[2].getAttribute("value").trim() != ""

	// Clear and update
	NMG_fields[0].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
	NMG_fields[0].sendKeys(Keys.BACK_SPACE)
	NMG_fields[0].sendKeys("12")

	NMG_fields[1].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
	NMG_fields[1].sendKeys(Keys.BACK_SPACE)
	NMG_fields[1].sendKeys("99")

	NMG_fields[2].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
	NMG_fields[2].sendKeys(Keys.BACK_SPACE)
	NMG_fields[2].sendKeys("100")
}

FOV_rows[1].click()

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'), 0)

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'))



WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), 0)

//set the input box to empty by this line.
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '')

// Clear the input box by setting the text to an empty string
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '')

// Set the value to 5000 after clearing
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '5000')

// Click the save button
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/button_Save'))

// Wait for the changes to be saved
WebUI.delay(2)

// Dynamically create the TestObject for the multiplication factor display
TestObject multiplicationFactorDisplay = new TestObject()
multiplicationFactorDisplay.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, "//div[@class='multiplication-factor']/span")

// Check if the object is present before getting the text
if (WebUI.verifyElementPresent(multiplicationFactorDisplay, 5)) {
	// Get the saved value from the multiplication factor field
	String savedValue = WebUI.getText(multiplicationFactorDisplay)
	
	// Print the saved value
	WebUI.comment('Saved value in multiplication factor: ' + savedValue)
	
	// Verify if the saved value is correct
	WebUI.verifyMatch(savedValue, '5000', false)
} else {
	WebUI.comment('Multiplication factor display not found!')
}

//Checking the platelet calculated estimate value updation here.
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

// ✅ Step 1: Verify value "1055" in the span element
String expectedText = "1055"
String spanXPath = "//div[@class='description selected']/div[2]/span"

TestObject spanObject = new TestObject("targetSpan")
spanObject.addProperty("xpath", ConditionType.EQUALS, spanXPath)

WebUI.waitForElementVisible(spanObject, 10)
String actualText = WebUI.getText(spanObject).trim()

println("🔍 Fetched span text: '${actualText}'")

if (actualText == expectedText) {
    println("✅ Value match passed: '${actualText}' == '${expectedText}'")
} else {
    println("❌ Value mismatch: Expected '${expectedText}', but found '${actualText}'")
    WebUI.verifyEqual(actualText, expectedText, FailureHandling.CONTINUE_ON_FAILURE)
}

// ✅ Step 2: Verify text "significant decreased" is present on the page
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Calculated level'))
WebUI.delay(2)

String keywordText = "Significantly decreased"
boolean isTextPresent = WebUI.verifyTextPresent(keywordText, false, FailureHandling.CONTINUE_ON_FAILURE)

if (isTextPresent) {
    println("✅ Text presence check passed: '${keywordText}' is present on the page.")
} else {
    println("❌ Text presence check failed: '${keywordText}' is NOT present on the page.")
}
