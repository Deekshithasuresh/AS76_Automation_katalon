import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




// Step 1: Login and navigate
CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')

// Step 3: Click WBC tab
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))
WebDriver driver = DriverFactory.getWebDriver()

int Neutrophilcount = Integer.parseInt(driver.findElement(By.xpath("//td[text()='Neutrophils']/following-sibling::td[1]")).getText().trim())



// Define expected value
int EXPECTED_PATCHES_IN_ROW = 6

if( EXPECTED_PATCHES_IN_ROW  >=  Neutrophilcount) {
	WebElement LymphocytesRow = driver.findElement(By.xpath("(//table/tbody/tr/td[1][contains(text(),'Lymphocytes')]/parent::tr)[1]"))
	LymphocytesRow.click()
	println("Click on lymphocyrtes")
	
}

// Create TestObject with XPath for all patch elements
TestObject patchObject = new TestObject()
patchObject.addProperty("xpath", ConditionType.EQUALS, "//div[@class='Item'][1]/div[@class='Card patches-container']")

// Get all patches
List<WebElement> patches = WebUI.findWebElements(patchObject, 10)

int patchCount = patches.size()

println("First row patch count: ${patchCount}")

// Compare with expected
if (patchCount == EXPECTED_PATCHES_IN_ROW) {
	WebUI.comment("✅ Patch count matches: ${patchCount}")
} else {
	WebUI.comment("❌ Patch count mismatch. Expected: ${EXPECTED_PATCHES_IN_ROW}, Found: ${patchCount}")
	assert false : "Patch count in first row does not match"
}


