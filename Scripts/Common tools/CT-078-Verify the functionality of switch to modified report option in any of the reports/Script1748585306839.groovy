import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// 1. Login
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (21)'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (21)'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (21)'))
WebDriver driver = DriverFactory.getWebDriver()
// 2. Open report
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1145 AM (IST)'))

// 3. RBC Morph field object
TestObject RBC_Morph = findTestObject('Object Repository/Commontools/Page_PBS/RBC_Morph')

// 4. Capture initial value
WebUI.waitForElementVisible(RBC_Morph, 10)
String initialValue = WebUI.getAttribute(RBC_Morph, 'value')
println "Initial RBC Morph Value: " + initialValue

// 5. Enter test data
String testData = '12as#'
enterAlphaNumSpecialInField(RBC_Morph, testData)

// 6. Simulate Save: click logo/back
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/img'))

// 7. Switch to original report
WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Switch to original report'), 5)
WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Switch to original report'), 'Switch to original report')
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/span_Switch to original report'))

// 8. Validate modified data is not present
WebUI.waitForElementVisible(RBC_Morph, 10)
String valueInOriginal = WebUI.getAttribute(RBC_Morph, 'value')
println "Value in Original Report RBC Morph: " + valueInOriginal
assert valueInOriginal != testData : 'Modified data should NOT be present in original report'

// 9. Refresh view by clicking logo/back
WebUI.click(findTestObject('Object Repository/Commontools/img'))

// 10. Wait for and click 'Switch to modified report'
TestObject switchToModified = findTestObject('Object Repository/Commontools/span_Switch to modified report')
boolean isPresent = WebUI.waitForElementPresent(switchToModified, 10, FailureHandling.OPTIONAL)
println "Switch to Modified Report Present: " + isPresent

if (isPresent) {
	WebUI.waitForElementVisible(switchToModified, 10)
	WebUI.waitForElementClickable(switchToModified, 10)
	WebUI.click(switchToModified)
	WebUI.delay(2)

	//(//div[@role='textbox'])[1]
	// 11. Validate modified data is restored (with retry)
	WebUI.waitForElementVisible(RBC_Morph, 10)
	
	String valueInModified = null
	int retry = 0
	while (retry < 5 && (valueInModified == null || valueInModified.trim() == '')) {
		
		valueInModified=driver.findElement(By.xpath("(//div[@role='textbox'])[1]/p")).getText()
		
		//valueInModified = WebUI.getAttribute(RBC_Morph, 'value')
		println "Attempt ${retry + 1}: Value in Modified Report RBC Morph: " + valueInModified
		WebUI.delay(1)
		retry++
	}

	WebUI.takeScreenshot()  // capture final state

	assert valueInModified == testData : "Modified data is NOT present in modified report as expected. Expected: '${testData}', Found: '${valueInModified}'"
} else {
	WebUI.takeScreenshot()
	assert false : 'Switch to modified report button NOT found.'
}

// === Reusable field entry function ===
def enterAlphaNumSpecialInField(TestObject fieldObject, String value) {
	WebUI.waitForElementVisible(fieldObject, 10)
	WebUI.click(fieldObject)
	WebUI.clearText(fieldObject)
	WebUI.setText(fieldObject, value)
	WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB))
}
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

