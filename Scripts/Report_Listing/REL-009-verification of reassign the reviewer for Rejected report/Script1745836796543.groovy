import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper as Helper
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


// Open browser and navigate to login
CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed_1'), 'Reviewed')

// Create dynamic TestObject for the input field
TestObject assignedToInput = new TestObject('AssignedToInput')
assignedToInput.addProperty("xpath", ConditionType.EQUALS, "//span[text()='Rejected']//parent::div/parent::td/following-sibling::td[1]//input")

// Get the value of 'disabled' attribute
String isDisabled = WebUI.getAttribute(assignedToInput, "disabled")

// Fail the test if not disabled
assert isDisabled != null : "❌ Assigned To input is editable. Expected it to be disabled."

println("✅ Assigned To input is not editable (disabled).")



