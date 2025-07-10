import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// === LOGIN AND APPROVAL STEPS ===
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Step 2: Capture the Slide ID dynamically (assuming it's visible before approval)
TestObject slideIdObject = new TestObject().addProperty("xpath", ConditionType.EQUALS, "/html/body/div/div/div[1]/div[1]/div[2]/div[2]/span")
String slideId = WebUI.getText(slideIdObject).trim()
WebUI.comment("Captured Slide ID: " + slideId)

// Approve the report
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report_1'))
WebUI.click(findTestObject('Object Repository/Page_PBS/review_Approve report_button'))


// Go to Reviewed tab
WebUI.click(findTestObject('Object Repository/Page_PBS/back_arrow_button')) // hitting back arrow button
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Ready for review'))
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Reviewed'))

// === SEARCH BY SLIDE ID ===
TestObject searchInput = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//input[@placeholder='Search']")
WebUI.waitForElementVisible(searchInput, 10)
WebUI.setText(searchInput, slideId)
WebUI.delay(2) // Wait for filtering

// === VERIFY SLIDE ID IS VISIBLE ===
TestObject slideIdCell = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//td[contains(text(), '" + slideId + "')]")
if (WebUI.verifyElementPresent(slideIdCell, 5, FailureHandling.OPTIONAL)) {
	WebUI.comment("✅ Slide ID '" + slideId + "' is found in the Reviewed tab.")

	// === VERIFY STATUS IN SAME ROW IS "Approved" ===
	String statusXpath = "//td[contains(text(), '" + slideId + "')]/following-sibling::td//span[contains(@class,'reportStatusComponent_text') and text()='Approved']"
	TestObject statusCell = new TestObject().addProperty("xpath", ConditionType.EQUALS, statusXpath)

	if (WebUI.verifyElementPresent(statusCell, 5, FailureHandling.OPTIONAL)) {
		WebUI.comment("✅ Status for slide '" + slideId + "' is 'Approved'.")
	} else {
		WebUI.comment("❌ Status for slide '" + slideId + "' is NOT 'Approved'. Check the report state.")
	}
} else {
	WebUI.comment("❌ Slide ID '" + slideId + "' not found in the Reviewed tab.")
}
