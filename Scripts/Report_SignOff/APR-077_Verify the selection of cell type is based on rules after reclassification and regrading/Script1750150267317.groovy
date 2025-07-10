import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// -----------------------------
// Custom Setup Steps
// -----------------------------
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

CustomKeywords.'generic.Reclassification.dragAndDropFromCellToCell'("Neutrophils", "Lymphocytes")

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Platelets'))
WebUI.delay(1)
WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_Morphology'))
WebUI.delay(1)
CustomKeywords.'generic.Reclassification.classifyFromCellToCellMultiplePlatelet'("Large Platelets", "Platelet Clumps", 2)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_RBC (1)'))

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))

valueGettingStrikeOfAfterRegrading()


// Approve and navigate to classification
TestObject approveButton = findTestObject('Object Repository/WBC_m/Page_PBS/span_Approve report')
WebElement apElement = WebUiCommonHelper.findWebElement(approveButton, 10)
JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
js.executeScript("arguments[0].click();", apElement)
WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_Confirm_after_aR'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Add supporting images'))

WebDriver driver = DriverFactory.getWebDriver()

// -----------------------------
// Utility Functions
// -----------------------------
TestObject createTestObject(String xpath) {
    TestObject to = new TestObject()
    to.addProperty("xpath", ConditionType.EQUALS, xpath)
    return to
}

float extractPercentage(String text) {
    def matcher = text =~ /\(([\d.]+)\)/
    return matcher ? Float.parseFloat(matcher[0][1]) : 0.0
}

int extractGrade(String text) {
    def matcher = text =~ /\((\d+)\+\)/
    return matcher ? Integer.parseInt(matcher[0][1]) : 0
}


int extractPlateletValue(String text) {
    def matcher = text =~ /\((\d+)\)/
    return matcher ? Integer.parseInt(matcher[0][1]) : 0
}

// -----------------------------
// Threshold Rules
// -----------------------------

Map<String, Float> wbcRules = [
    'Neutrophils'             : 0.0,
    'Lymphocytes'             : 0.0,
    'Eosinophils'             : 0.0,
    'Monocytes'               : 0.0,
    'Basophils'               : 0.0,
    'Atypical Cells/Blasts'   : 0.0,
    'Immature Granulocytes'   : 5.0,
    'NRBC'                    : 5.0
]

Map<String, Integer> rbcRules = [
    'Microcytes'      : 1,
    'Macrocytes'      : 1,
    'Target'          : 1,
    'Teardrop'        : 1,
    'Fragmented'      : 0,
    'Elliptocytes'    : 1,
    'Ovalocytes'      : 1,
    'Echinocyte'      : 1,
    'Hypochromic'     : 1,
    'Polychromatic'   : 1
]

Map<String, Integer> plateletRules = [
    'Platelet Clumps' : 1,
    'Large Platelets' : 1
]

// -----------------------------
// WBC Validation
// -----------------------------
wbcRules.each { cellName, threshold ->
    String labelXpath = "//div[@class='celle-selection-list']//div[contains(text(), '${cellName}')]"
    TestObject labelObj = createTestObject(labelXpath)

    if (WebUI.verifyElementPresent(labelObj, 5, FailureHandling.OPTIONAL)) {
        String labelText = WebUI.getText(labelObj)
        float percent = extractPercentage(labelText)

        String checkboxXpath = "${labelXpath}//parent::div//input"
        TestObject checkboxObj = createTestObject(checkboxXpath)

        if (percent > threshold) {
            WebUI.verifyElementChecked(checkboxObj, 5, FailureHandling.OPTIONAL)
            println "[WBC] ${cellName} (${percent}%) > ${threshold}% – ✅ Checked"

            WebUI.click(checkboxObj)
            WebUI.delay(1)

            TestObject cellInMiddleObj = createTestObject("(//div[contains(@class,'viewer-set-container')]//div[contains(text(),'${cellName}')])[1]")
            boolean isRemoved = WebUI.verifyElementNotPresent(cellInMiddleObj, 3, FailureHandling.OPTIONAL)
            println isRemoved ?
                "✅ ${cellName} removed from middle pane after unchecking" :
                "❌ ${cellName} still visible in middle pane after unchecking"
        } else {
            WebUI.verifyElementNotChecked(checkboxObj, 5, FailureHandling.OPTIONAL)
            println "[WBC] ${cellName} (${percent}%) ≤ ${threshold}% – ✅ Not selected"
        }
    } else {
        println "⚠️ [WBC] Label for '${cellName}' not found"
    }
}

// -----------------------------
// RBC Validation
// -----------------------------
rbcRules.each { cellName, gradeThreshold ->
    String gradeXpath = "//div[contains(text(),'${cellName}')]/parent::div/span/following-sibling::div"
    TestObject gradeObj = createTestObject(gradeXpath)

    if (WebUI.verifyElementPresent(gradeObj, 5, FailureHandling.OPTIONAL)) {
        int grade = extractGrade(WebUI.getText(gradeObj))
        TestObject checkboxObj = createTestObject("//div[contains(text(),'${cellName}')]/parent::div/span")

        if (grade > gradeThreshold) {
            WebUI.verifyElementChecked(checkboxObj, 5, FailureHandling.OPTIONAL)
            WebUI.click(checkboxObj)
            WebUI.delay(1)

            TestObject cellInMiddle = createTestObject("//div[contains(@class,'viewer-set-container')]//div[contains(text(),'${cellName}')]")
            boolean removed = WebUI.verifyElementNotPresent(cellInMiddle, 3, FailureHandling.OPTIONAL)
            println "[RBC] ${cellName} (Grade ${grade}) ≥ ${gradeThreshold} – ✅ Deselected and ${removed ? 'removed' : 'still present'} in middle pane"
        } else {
            WebUI.verifyElementNotChecked(checkboxObj, 5, FailureHandling.OPTIONAL)
            println "[RBC] ${cellName} (Grade ${grade}) < ${gradeThreshold} – ✅ Not selected"
        }
    } else {
        println "⚠️ [RBC] Label not found for ${cellName}"
    }
}

// -----------------------------
// Platelet Validation
// -----------------------------
plateletRules.each { cellName, threshold ->
    try {
        String labelXpath = "//div[@class='celle-selection-list']//div[contains(text(), '${cellName}')]"
        TestObject labelObj = createTestObject(labelXpath)

        // Safely check if element exists
        if (!WebUI.verifyElementPresent(labelObj, 5, FailureHandling.OPTIONAL)) {
            println "⚠️ [PLT] '${cellName}' label not found in DOM – skipping"
            return  // Skip to next cellName
        }

        int count = extractPlateletValue(WebUI.getText(labelObj))
        TestObject checkboxObj = createTestObject("${labelXpath}//parent::div//input")

        if (threshold > 0 && count > 0) {
            WebUI.verifyElementChecked(checkboxObj, 5, FailureHandling.OPTIONAL)
            WebUI.click(checkboxObj)
            WebUI.delay(1)

            TestObject cellInMiddle = createTestObject("//div[contains(@class,'viewer-set-container')]//div[contains(text(),'${cellName}')]")
            boolean removed = WebUI.verifyElementNotPresent(cellInMiddle, 3, FailureHandling.OPTIONAL)
            println "[PLT] ${cellName} Count > 0 – ✅ Deselected and ${removed ? 'removed' : 'still present'} in middle pane"
        } else if (threshold < 0) {
            WebUI.verifyElementNotPresent(checkboxObj, 3, FailureHandling.OPTIONAL)
            println "[PLT] ${cellName} – ❌ Should NEVER be shown"
        } else {
            WebUI.verifyElementNotChecked(checkboxObj, 5, FailureHandling.OPTIONAL)
            println "[PLT] ${cellName} Count ≤ 0 – ✅ Not selected"
        }
    } catch(Exception e) {
        println "❌ [PLT] Error occurred for '${cellName}': ${e.message}"
    }
	
}
	
	
	public void valueGettingStrikeOfAfterRegrading() {
		WebDriver driver =DriverFactory.getWebDriver()
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/Report_Listing/Page_PBS/Cell_rows'),10)
		for (WebElement row : cellRows) {
			//WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			//println(cellname)
			List<WebElement> grades= row.findElements(By.xpath(".//input[@type='radio']"))
			if(cellname.equals("Acanthocytes*") || cellname.equals("Sickle Cells*")  ||cellname.equals("Pappenheimer Bodies*") ||cellname.equals("Howell-Jolly Bodies*") ||cellname.equals("Basophilic Stippling*")) {
				grades[1].click()
				WebUI.delay(2)
				continue;
			}
			else {
				for(int i=0; i<=grades.size();i++) {
					if(!grades.get(i).isSelected()) {
						grades.get(i).click()
						WebUI.delay(1)
						WebElement percentageElement = row.findElement(By.xpath(".//div[3]/del"))
						String textDecoration = percentageElement.getCssValue("text-decoration-line");
						assert textDecoration.contains("line-through")
						println(cellname+" got regraded and percentage value got strike off and verified")
						break;
					}
				}
			}
		}
	}



