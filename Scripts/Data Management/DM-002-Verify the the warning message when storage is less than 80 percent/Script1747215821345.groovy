import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Step 1: Login
CustomKeywords.'generic.dataManagement.loginAdmin'()

// Step 2: Define dynamic test objects
TestObject percentageText = new TestObject('percentageText')
percentageText.addProperty('xpath', ConditionType.EQUALS, "//span[@class='percentage']")

TestObject progressBar = new TestObject('progressBar')
progressBar.addProperty('xpath', ConditionType.EQUALS, "//span[contains(@class, 'MuiLinearProgress-barColorPrimary')]")

// Step 3: Get percentage value
String percentageStr = WebUI.getText(percentageText)
percentageStr = percentageStr.replaceAll("[^0-9]", "")
int percent = percentageStr.toInteger()
println("Detected usage: ${percent}%")

// Step 4: Get progress bar color
WebElement barElement = WebUiCommonHelper.findWebElement(progressBar, 5)
String bgColor = barElement.getCssValue('background-color')
println("Progress bar color: ${bgColor}")

// Step 5: Define expected values
String expectedGreen = 'rgb(35, 206, 107)'
List<String> alertColors = ['rgb(255, 87, 34)', 'rgb(244, 67, 54)', 'rgb(255, 152, 0)']

// Normalize color (remove alpha if present)
if (bgColor.startsWith('rgba')) {
    bgColor = bgColor.replace('rgba', 'rgb').replaceAll(',\\s*1\\)', ')')
}

// Step 6: Perform assertions
if (percent < 80) {
    assert bgColor == expectedGreen : "Expected green progress bar for <80% but found: ${bgColor}"
   // WebUI.verifyElementNotPresent(findTestObject('warningMessageObject'), 2)
} else {
    boolean isAlertColor = alertColors.any { bgColor == it }
    assert isAlertColor : "Expected red/orange progress bar for ≥80% but found: ${bgColor}"
    WebUI.verifyElementVisible(findTestObject('warningMessageObject'))
}
