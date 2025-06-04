import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

DriverFactory

WebUI.openBrowser('')
CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_RBC'))


// Get WebDriver instance
def driver = DriverFactory.getWebDriver()

// Get all grade-div containers (each cell row with 4 grades)
def allGradeRows = driver.findElements(By.xpath("//div[contains(@class,'grade-div')]"))

// Loop through and set each cell to grade 2 if it's non-significant
for (WebElement gradeRow : allGradeRows) {
	def gradeButtons = gradeRow.findElements(By.xpath(".//span[contains(@class,'MuiButtonBase-root')]"))

	for (int i = 0; i < gradeButtons.size(); i++) {
		if (gradeButtons[i].getAttribute("class").contains("Mui-checked")) {
			if (i < 2) { // Grade 0 or 1 is non-significant
				// Set to grade 2 (index 2)
				gradeButtons[2].click()
				WebUI.delay(0.3)
				break
			}
		}
	}
}

// Verification step: ensure all grades are now significant
boolean allSignificant = true

for (WebElement gradeRow : allGradeRows) {
	def gradeButtons = gradeRow.findElements(By.xpath(".//span[contains(@class,'MuiButtonBase-root')]"))
	
	for (int i = 0; i < gradeButtons.size(); i++) {
		if (gradeButtons[i].getAttribute("class").contains("Mui-checked")) {
			if (i < 2) {
				allSignificant = false
				break
			}
		}
	}
}

if (allSignificant) {
	WebUI.comment("✅ All RBC cells are now marked significant. Approving report.")
	WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
} else {
	WebUI.comment("❌ Some RBC cells are still non-significant. Report NOT approved.")
}
