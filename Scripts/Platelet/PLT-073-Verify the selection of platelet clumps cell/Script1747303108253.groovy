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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.testobject.ConditionType as ConditionType

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

// Select a sample
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)

// Get cell rows
List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Platelets/Page_PBS/morphology _row'), 
    10)

// Helper to check if a row is clickable
int clumpCount = 0

int largePlateletCount = 0

WebElement clumpRow = null

WebElement largePlateletRow = null

for (WebElement row : cell_rows) {
    String cellName = row.findElement(By.xpath('.//div[1]')).getText()

    String countText = row.findElement(By.xpath('.//div[2]')).getText()

    int count = countText.isInteger() ? countText.toInteger() : 0

    if (cellName.contains('Platelet Clumps')) {
        clumpCount = count

        clumpRow = row
    }
    
    if (cellName.contains('Large Platelets')) {
        largePlateletCount = count

        largePlateletRow = row
    }
}

println("Large Platelets Count: $largePlateletCount")

println("Platelet Clumps Count: $clumpCount")

// Final decision logic
if ((clumpCount > 0) && (largePlateletCount > 0)) {
    // Case 1: Both present, click clumps to override default selection
    assert isRowClickable(clumpRow) : '❌ Platelet Clumps should be clickable'

    clumpRow.click()

    println('✅ Platelet Clumps is clicked')

    WebUI.delay(1) // Case 2: Only clumps — already selected by default
    // Case 3: Only large platelets — already selected by default
    // Case 4: Neither present
} else if ((clumpCount > 0) && (largePlateletCount == 0)) {
    println('ℹ️ Only Platelet Clumps present. Already selected by default. No click needed.')
} else if ((largePlateletCount > 0) && (clumpCount == 0)) {
    println('ℹ️ Only Large Platelets present. Already selected by default. No click needed.')
} else {
    println('✅ No Platelet Clumps or Large Platelets present. Skipping clicks.')
}

//method
// ---- Status & Legend Validation ----
TestObject largePlateletsStatusText = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'platelet-morph-row\')][.//div[contains(text(),\'Large Platelets\')]]//div[contains(@class,\'dropdown-container\')]')

TestObject largePlateletsLegendDot = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'platelet-morph-row\')][.//div[contains(text(),\'Large Platelets\')]]//div[contains(@class,\'detected-dot\')]')

TestObject plateletClumpsStatusText = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'platelet-morph-row\')][.//div[contains(text(),\'Platelet Clumps\')]]//div[contains(@class,\'dropdown-container\')]')

TestObject plateletClumpsLegendDot = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'platelet-morph-row\')][.//div[contains(text(),\'Platelet Clumps\')]]//div[contains(@class,\'detected-dot\')]')

// ✅ Validate Both Cells
verifyLegendAndStatus(largePlateletsStatusText, largePlateletsLegendDot, 'Large Platelets')

verifyLegendAndStatus(plateletClumpsStatusText, plateletClumpsLegendDot, 'Platelet Clumps')

boolean isRowClickable(WebElement row) {
    try {
        if (!(row.isDisplayed()) || !(row.isEnabled())) {
            return false
        }
        
        String classAttr = row.getAttribute('class')

        if ((classAttr.toLowerCase().contains('disabled') || classAttr.toLowerCase().contains('not-clickable')) || classAttr.toLowerCase().contains(
            'inactive')) {
            return false
        }
        
        return true
    }
    catch (Exception e) {
        println("⚠️ Exception in isRowClickable: $e.message")

        return false
    } 
}

boolean verifyLegendAndStatus(TestObject statusTextObj, TestObject legendDotObj, String cellType) {
	try {
		if (!WebUI.waitForElementPresent(statusTextObj, 10) || !WebUI.waitForElementVisible(statusTextObj, 10)) {
			WebUI.comment("❌ $cellType: Status text element not present/visible")
			return false
		}

		if (!WebUI.waitForElementPresent(legendDotObj, 10) || !WebUI.waitForElementVisible(legendDotObj, 10)) {
			WebUI.comment("❌ $cellType: Legend dot element not present/visible")
			return false
		}

		String statusText = WebUI.getText(statusTextObj).trim()
		String legendClass = WebUI.getAttribute(legendDotObj, "class")

		WebUI.comment("🧪 $cellType => status: '$statusText' | class: '${legendClass}'")

		if (legendClass == null || legendClass.trim() == "") {
			WebUI.comment("⚠️ $cellType: Legend class is null or empty")
			return false
		}

		legendClass = legendClass.toLowerCase()

		if (statusText.equalsIgnoreCase("Not detected")) {
			assert legendClass.contains("not-detected-dot") : "❌ $cellType: Status is 'Not detected' but legend is not GREEN"
			println "✅ $cellType: Not detected (green)"
		} else if (statusText.equalsIgnoreCase("Detected")) {
			assert legendClass.contains("detected-dot") : "❌ $cellType: Status is 'Detected' but legend is not RED"
			println "✅ $cellType: Detected (red)"
		} else {
			assert false : "❌ $cellType: Unknown status text '$statusText'"
		}

		return true
	} catch (Exception e) {
		WebUI.comment("❌ Exception in verifying $cellType: " + e.message)
		return false
	}
}
