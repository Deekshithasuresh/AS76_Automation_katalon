import com.kms.katalon.core.testobject.ConditionType as ConditionType

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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/retain_patchs/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), Keys.chord(Keys.ENTER))

WebUI.maximizeWindow()

// Navigate to the specific slide
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/td_SIG013'), 20)

WebUI.click(findTestObject('Object Repository/retain_patchs/td_SIG013'))



WebUI.comment('Starting  platletes tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_plateletes'),'Platelets')

WebUI.click(findTestObject('Object Repository/single_click/button_plateletes'))

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Morphology'),'Morphology')

WebUI.click(findTestObject('Object Repository/single_click/button_Morphology'))


// Start with patch view

WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

try {
	// Wait for page to load completely
	WebUI.delay(2)

	// Create the test object for double click
	TestObject targetElement = new TestObject()

	targetElement.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patch-img-container")]')

	// Wait for element and verify it exists
	WebUI.waitForElementPresent(targetElement, 30)

	WebUI.waitForElementVisible(targetElement, 30)

	// Perform double click
	WebUI.doubleClick(targetElement)

	println('✅ Cell has been double clicked successfully')

	// Wait a moment for split view to load
	WebUI.delay(2)

	// Verify split view is displayed
	boolean splitViewExists = WebUI.verifyElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'),
		10)

	if (splitViewExists) {
		println('✅ Split view is now displayed - verification successful')

		// Additional verification - check if element is visible (removed timeout parameter)
		boolean splitViewVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))

		if (splitViewVisible) {
			println('✅ Split view element is visible on screen')
		} else {
			println('⚠️ Split view element exists but is not visible')
		}
	} else {
		println('There are no cells present in RBC shape')

		WebUI.takeScreenshot()
	}
}
catch (Exception e) {
	println('❌ Failed to double-click element: ' + e.getMessage())

	WebUI.takeScreenshot()
}
