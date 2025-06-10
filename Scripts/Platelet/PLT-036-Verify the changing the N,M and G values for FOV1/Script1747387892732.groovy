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

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV 10_non-clickable'), 0)
WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV1 10_clickable'), 0)

////changing the NMG values for all the Fovs from Fov1 to Fov10
//WebDriver driver = DriverFactory.getWebDriver()
//List<WebElement> FOV_rows = driver.findElements(By.className('fov-tuple'))
//
//for(int i=0; i<10;i++)
//{
//	FOV_rows.get(i).click()
//	List<WebElement> NMG_fields=FOV_rows.get(i).findElements(By.xpath("//input[@class='fov-edit-input']"))
//	NMG_fields[0].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//	NMG_fields[0].sendKeys(Keys.chord(Keys.BACK_SPACE))
//	NMG_fields[0].sendKeys("12") //passing value to N field
//	
//	NMG_fields[1].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//	NMG_fields[1].sendKeys(Keys.chord(Keys.BACK_SPACE))
//	NMG_fields[1].sendKeys("99") //passing value to M field
//	
//	NMG_fields[2].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//	NMG_fields[2].sendKeys(Keys.chord(Keys.BACK_SPACE))
//	NMG_fields[2].sendKeys("100") //passing value to G field
//}
//
//
////List<WebElement> FOV_rows = driver.findElements(By.className('fov-tuple'))
////Changing the NMG values from fov 5 to Fov10.
//for(int i=5; i<10;i++)
//	{
//		FOV_rows.get(i).click()
//		List<WebElement> NMG_fields=FOV_rows.get(i).findElements(By.xpath("//input[@class='fov-edit-input']"))
//		NMG_fields[0].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//		NMG_fields[0].sendKeys(Keys.chord(Keys.BACK_SPACE))
//		NMG_fields[0].sendKeys("66") //passing value to N field
//		
//		NMG_fields[1].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//		NMG_fields[1].sendKeys(Keys.chord(Keys.BACK_SPACE))
//		NMG_fields[1].sendKeys("100") //passing value to M field
//		
//		NMG_fields[2].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//		NMG_fields[2].sendKeys(Keys.chord(Keys.BACK_SPACE))
//		NMG_fields[2].sendKeys("44") //passing value to G field
//	}
//	
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

// === Update NMG values for FOV 6 to 10 ===
//for (int i = 5; i < 10; i++) {
//	FOV_rows.get(i).click()
//	List<WebElement> NMG_fields = FOV_rows.get(i).findElements(By.xpath("//input[@class='fov-edit-input']"))
//
//	// Log existing values before changing
//	println("FOV ${i + 1} - Current NMG values before overwrite:")
//	println("  N: " + NMG_fields[0].getAttribute("value"))
//	println("  M: " + NMG_fields[1].getAttribute("value"))
//	println("  G: " + NMG_fields[2].getAttribute("value"))
//
//	assert NMG_fields[0].getAttribute("value").trim() != ""
//	assert NMG_fields[1].getAttribute("value").trim() != ""
//	assert NMG_fields[2].getAttribute("value").trim() != ""
//
//	// Clear and update
//	NMG_fields[0].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//	NMG_fields[0].sendKeys(Keys.BACK_SPACE)
//	NMG_fields[0].sendKeys("66")
//
//	NMG_fields[1].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//	NMG_fields[1].sendKeys(Keys.BACK_SPACE)
//	NMG_fields[1].sendKeys("100")
//
//	NMG_fields[2].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
//	NMG_fields[2].sendKeys(Keys.BACK_SPACE)
//	NMG_fields[2].sendKeys("44")
//}

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
// Create TestObject dynamically

//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.testobject.ConditionType
//import org.openqa.selenium.WebElement
//import com.kms.katalon.core.webui.common.WebUiCommonHelper
//
//// Define XPath for all rows
//String rowXPath = '//tr[@class="count-table-body  "]'
//
//// Create a TestObject for rows
//TestObject rowObject = new TestObject('dynamicRows')
//rowObject.addProperty('xpath', ConditionType.EQUALS, rowXPath)
//
//// Find all matching row elements
//List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObject, 10)
//
//println("Total rows found: " + rows.size())
//
//// Loop through each row to get td[2] text
//for (int i = 1; i <= rows.size(); i++) {
//    String tdXPath = "(${rowXPath})[$i]/td[2]"
//    
//    TestObject cellObject = new TestObject("tdInRow$i")
//    cellObject.addProperty('xpath', ConditionType.EQUALS, tdXPath)
//    
//    String cellText = WebUI.getText(cellObject)
//    println("Row $i - TD[2] Text: $cellText")
//}
//
//// Define the XPath
//String xpath = "//table[@class='count-table']/tr[5]/td[2]"
//
//// Create a dynamic TestObject
//TestObject cellObject = new TestObject("fifthRowSecondCell")
//cellObject.addProperty("xpath", ConditionType.EQUALS, xpath)
//
//// Wait for the element (optional but recommended)
//WebUI.waitForElementVisible(cellObject, 10)
//
//// Get the text
//String cellText = WebUI.getText(cellObject)
//
//// Print the result
//println("Text in 5th row, 2nd column: " + cellText)
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import internal.GlobalVariable as GlobalVariable

// === PART 1: Assert values from count-table-body rows ===

// Define expected values for each row's TD[2] (update as needed)
List<String> expectedValues = ["12", "99", "100", "211.0"]  // Replace with actual expected values

String rowXPath = '//tr[@class="count-table-body  "]'
TestObject rowObject = new TestObject('dynamicRows')
rowObject.addProperty('xpath', ConditionType.EQUALS, rowXPath)

List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObject, 10)
println("Total rows found: " + rows.size())

for (int i = 1; i <= rows.size(); i++) {
	String tdXPath = "(${rowXPath})[$i]/td[2]"
	
	TestObject cellObject = new TestObject("tdInRow$i")
	cellObject.addProperty('xpath', ConditionType.EQUALS, tdXPath)
	
	String actualText = WebUI.getText(cellObject)
	println("Row $i - TD[2] Text: $actualText")
	
	if (i <= expectedValues.size()) {
		String expectedText = expectedValues[i - 1]
		WebUI.verifyEqual(actualText, expectedText, FailureHandling.CONTINUE_ON_FAILURE)
	}
}

// === PART 2: Assert specific table cell value (5th row, 2nd column) ===

String xpath = "//table[@class='count-table']/tr[5]/td[2]"
TestObject cellObject = new TestObject("fifthRowSecondCell")
cellObject.addProperty("xpath", ConditionType.EQUALS, xpath)

WebUI.waitForElementVisible(cellObject, 10)

String actualFifthRowCell = WebUI.getText(cellObject)
String expectedFifthRowCell = expectedValues[3]  // Replace with actual expected value

println("Text in 5th row, 2nd column: " + actualFifthRowCell)
WebUI.verifyEqual(actualFifthRowCell, expectedFifthRowCell, FailureHandling.STOP_ON_FAILURE)

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement

// ✅ Renamed to avoid conflicts
List<String> expectedTD3Values = ["5.7", "46.9", "47.4", "100"]

String xpathTD3 = "//table[@class='count-table']/tr/td[3]"

TestObject td3CellObject = new TestObject("thirdColumnCells")
td3CellObject.addProperty("xpath", ConditionType.EQUALS, xpathTD3)

List<WebElement> td3Cells = WebUiCommonHelper.findWebElements(td3CellObject, 10)

println("✅ Total TD[3] cells found: ${td3Cells.size()}")

for (int i = 0; i < td3Cells.size(); i++) {
    String actualText = td3Cells[i].getText().trim()
    println("➡️ Row ${i + 1} - Actual TD[3] Text: '${actualText}'")

    if (i < expectedTD3Values.size()) {
        String expectedText = expectedTD3Values[i].trim()
        println("   Expected TD[3] Text: '${expectedText}'")
        WebUI.verifyEqual(actualText, expectedText, FailureHandling.CONTINUE_ON_FAILURE)
    } else {
        println("⚠️ No expected value defined for Row ${i + 1}. Skipping assertion.")
    }
}
