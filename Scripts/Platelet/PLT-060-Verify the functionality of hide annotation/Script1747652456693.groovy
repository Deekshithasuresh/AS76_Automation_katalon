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

//WebUI.verifyElementVisible(findTestObject('Object Repository/Platelet/Page_PBS/div_Hide annotations_icon-eye-active'))
//
//WebUI.verifyElementVisible(findTestObject('Object Repository/Platelet/Page_PBS/div_Hide annotations'))
//
//
//WebUI.verifyElementVisible(findTestObject('Object Repository/Platelet/Page_PBS/div_Show annotations'))

//Steps for clicking and showing the hide annotations icon
// Get WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Get the list of FOV rows using the specified class name
List<WebElement> FOV_rows = driver.findElements(By.className('fov-tuple'))

// Loop through the first 10 FOV rows (or fewer if available)
for (int i = 0; i < Math.min(10, FOV_rows.size()); i++) {
	// Click on the FOV row
	FOV_rows.get(i).click()
	WebUI.comment("Clicked on FOV row: " + (i + 1))

	// Find the NMG fields within the selected FOV row
	List<WebElement> NMG_fields = FOV_rows.get(i).findElements(By.xpath(".//input[@class='fov-edit-input']"))

	// Check if "Hide annotations" is visible
	if (WebUI.verifyElementVisible(findTestObject('Object Repository/Platelet/Page_PBS/div_Hide annotations_icon-eye-active'), FailureHandling.OPTIONAL)) {
		WebUI.comment("Hide annotations is currently visible.")

		// Click to hide annotations
		WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/div_Hide annotations_icon-eye-active'))
		WebUI.comment("Clicked to hide annotations.")

		// Verify if "Show annotations" is now visible
		if (WebUI.verifyElementVisible(findTestObject('Object Repository/Platelet/Page_PBS/div_Show annotations'), FailureHandling.OPTIONAL)) {
			WebUI.comment("Annotations are now hidden. Show annotations is visible.")
		} else {
			WebUI.comment("Failed to hide annotations.")
		}

	} else if (WebUI.verifyElementVisible(findTestObject('Object Repository/Platelet/Page_PBS/div_Show annotations'), FailureHandling.OPTIONAL)) {
		WebUI.comment("Show annotations is currently visible.")

		// Click to show annotations
		WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/div_Show annotations'))
		WebUI.comment("Clicked to show annotations.")

		// Verify if "Hide annotations" is now visible
		if (WebUI.verifyElementVisible(findTestObject('Object Repository/Platelet/Page_PBS/div_Hide annotations_icon-eye-active'), FailureHandling.OPTIONAL)) {
			WebUI.comment("Annotations are now visible. Hide annotations is visible.")
		} else {
			WebUI.comment("Failed to show annotations.")
		}
	} else {
		WebUI.comment("Neither 'Hide annotations' nor 'Show annotations' is visible.")
	}
}

WebUI.comment("Annotation toggle process completed.")