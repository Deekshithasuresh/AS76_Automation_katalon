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
CustomKeywords.'generic.custumFuctions.assignOrReassignOnTabs'('pawan', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV 10_non-clickable'), 0)
WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV1 10_clickable'), 0)


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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import internal.GlobalVariable as GlobalVariable

// Define expected values for each row's TD[2]
List<String> expectedValues = ["12", "99", "100", "211.0"]  // Update this as needed

// XPath to locate all data rows
String rowXPath = '//tr[@class="count-table-body  "]'
TestObject rowObject = new TestObject('dynamicRows')
rowObject.addProperty('xpath', ConditionType.EQUALS, rowXPath)

// Get list of all row elements
List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObject, 10)
println("✅ Total rows found: " + rows.size())

// Iterate over each row to check TD[2] values
for (int i = 1; i <= rows.size(); i++) {
    // XPath to fetch TD[2] of current row
    String tdXPath = "(${rowXPath})[$i]/td[2]"
    TestObject cellObject = new TestObject("tdInRow$i")
    cellObject.addProperty('xpath', ConditionType.EQUALS, tdXPath)

    // Get text content from cell
    String actualText = WebUI.getText(cellObject).trim()
    println("🔍 Row $i - TD[2] Text: '$actualText'")

    if (i <= expectedValues.size()) {
        String expectedText = expectedValues[i - 1].trim()

        try {
            // Convert both expected and actual to float
            float actualValue = Float.parseFloat(actualText)
            float expectedValue = Float.parseFloat(expectedText)

            // Round both to 1 decimal
            float actualRounded = Math.round(actualValue * 10) / 10.0f
            float expectedRounded = Math.round(expectedValue * 10) / 10.0f

            // Assertion: values after rounding must match
            if (actualRounded == expectedRounded) {
                println("✅ Row $i - Values match after rounding: $actualRounded")
            } else {
                println("⚠️ Row $i - Values do not match after rounding. Expected: $expectedRounded, Actual: $actualRounded")
            }

            // Check if actual value is properly rounded to 1 decimal (optional)
            if (actualValue != actualRounded) {
                println("⚠️ Row $i - Actual value '$actualValue' is NOT rounded to 1 decimal.")
            }

        } catch (Exception e) {
            println("❌ Row $i - Failed to parse as float. Actual='${actualText}', Expected='${expectedText}'")
        }
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


//percentage column of all the values in the platelet.
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement

// ✅ Expected values (1 decimal precision)
List<String> expectedTD3Values = ["5.7", "46.9", "47.4", "100"]

// ✅ XPath to 3rd column of the count table
String xpathTD3 = "//table[@class='count-table']//tr/td[3]"

TestObject td3CellObject = new TestObject("thirdColumnCells")
td3CellObject.addProperty("xpath", ConditionType.EQUALS, xpathTD3)

List<WebElement> td3Cells = WebUiCommonHelper.findWebElements(td3CellObject, 10)

println("✅ Total TD[3] cells found: ${td3Cells.size()}")

for (int i = 0; i < td3Cells.size(); i++) {
	String actualText = td3Cells[i].getText().trim()
	
	try {
		double actualValue = Double.parseDouble(actualText)
		double roundedActual = Math.round(actualValue * 10) / 10.0
		String formattedActual = String.format("%.1f", roundedActual)
		
		println("➡️ Row ${i + 1} - Rounded Actual: '${formattedActual}'")

		if (i < expectedTD3Values.size()) {
			String expectedText = expectedTD3Values[i].trim()
			println("   Expected: '${expectedText}'")
			WebUI.verifyEqual(formattedActual, expectedText, FailureHandling.CONTINUE_ON_FAILURE)
		} else {
			println("⚠️ No expected value for row ${i + 1}. Skipping.")
		}
	} catch (NumberFormatException e) {
		println("❌ Row ${i + 1} - '${actualText}' is not a valid number.")
	}
}


//Checking the platelet calculated estimate value updation here.
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

// ✅ Step 1: Verify value "3165" in the span element
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

String keywordText = "Increased"
boolean isTextPresent = WebUI.verifyTextPresent(keywordText, false, FailureHandling.CONTINUE_ON_FAILURE)

if (isTextPresent) {
    println("✅ Text presence check passed: '${keywordText}' is present on the page.")
} else {
    println("❌ Text presence check failed: '${keywordText}' is NOT present on the page.")
}
