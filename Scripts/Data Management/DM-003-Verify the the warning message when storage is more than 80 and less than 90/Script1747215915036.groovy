import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Step 1: Login
CustomKeywords.'generic.dataManagement.loginAdmin'()

// Step 2: Define TestObjects
TestObject percentageText = new TestObject('percentageText')
percentageText.addProperty('xpath', ConditionType.EQUALS, "//span[@class='percentage']")

TestObject progressBar = new TestObject('progressBar')
progressBar.addProperty('xpath', ConditionType.EQUALS, "//span[contains(@class, 'MuiLinearProgress-barColorPrimary')]")

TestObject warningMessage = findTestObject('warningMessageObject') // replace with correct TestObject

// Step 3: Get percentage value
String percentageStr = WebUI.getText(percentageText)
percentageStr = percentageStr.replaceAll("[^0-9]", "")
int percent = percentageStr.toInteger()
println("Detected usage: ${percent}%")

// Step 4: Get background color
WebElement barElement = WebUiCommonHelper.findWebElement(progressBar, 5)
String bgColor = barElement.getCssValue('background-color')
println("Progress bar color: ${bgColor}")

// Normalize color if rgba
if (bgColor.startsWith('rgba')) {
    bgColor = bgColor.replace('rgba', 'rgb').replaceAll(',\\s*1\\)', ')')
}

// Step 5: Define expected colors
String expectedGreen = 'rgb(35, 206, 107)'
String expectedOrange = 'rgb(255, 152, 0)'
String expectedRed = 'rgb(244, 67, 54)' // or another red used in your app

// Step 6: Assertions
if (percent < 80) {
    assert bgColor == expectedGreen : "Expected green (<80%) but found: ${bgColor}"
    //WebUI.verifyElementNotPresent(warningMessage, 2)
} else if (percent >= 80 && percent < 90) {
    assert bgColor == expectedOrange : "Expected orange (80–89%) but found: ${bgColor}"
    WebUI.verifyElementVisible(warningMessage)
    WebUI.verifyTextPresent("Hot storage is nearing full capacity", false) // update this if text is different
} else {
    assert bgColor == expectedRed : "Expected red (>=90%) but found: ${bgColor}"
    WebUI.verifyElementVisible(warningMessage)
    WebUI.verifyTextPresent("Hot storage is critically full", false) // update as needed
}