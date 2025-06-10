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

// Small wait to ensure page loads
WebUI.delay(2)

// Get WebDriver instance
def driver = DriverFactory.getWebDriver()

// Get all grade-div containers (each cell row with 4 grades)
def allGradeRows = driver.findElements(By.xpath("//div[contains(@class,'grade-div')]"))

// Set all RBC grades to 1 (non-significant)
for (WebElement gradeRow : allGradeRows) {
	def gradeButtons = gradeRow.findElements(By.xpath(".//span[contains(@class,'MuiButtonBase-root')]"))
	
	for (int i = 0; i < gradeButtons.size(); i++) {
		if (gradeButtons[i].getAttribute("class").contains("Mui-checked")) {
			if (i >= 2) {
				// Click grade 1 to set as non-significant
				gradeButtons[1].click()
				WebUI.delay(0.3)
				break
			}
		}
	}
}

// Verification step: ensure all grades are now non-significant
boolean allNonSignificant = true

for (WebElement gradeRow : allGradeRows) {
	def gradeButtons = gradeRow.findElements(By.xpath(".//span[contains(@class,'MuiButtonBase-root')]"))
	
	for (int i = 0; i < gradeButtons.size(); i++) {
		if (gradeButtons[i].getAttribute("class").contains("Mui-checked")) {
			if (i >= 2) {
				allNonSignificant = false
				break
			}
		}
	}
}

if (allNonSignificant) {
	WebUI.comment("✅ All RBC cells have been set to non-significant. Approving report.")
	WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
} else {
	WebUI.comment("❌ Some RBC cells are still significant")
}