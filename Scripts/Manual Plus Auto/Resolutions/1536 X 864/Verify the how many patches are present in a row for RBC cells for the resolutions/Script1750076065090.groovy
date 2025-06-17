import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



WebUI.openBrowser('')

ChromeOptions options = new ChromeOptions()
options.addArguments("--window-size=1280,720")

ChromeDriver driver = new ChromeDriver(options)
DriverFactory.changeWebDriver(driver)
// Step 1: Login and navigate
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report_Listing/Login_page/input_username_loginId'), 'manju')
WebUI.setEncryptedText(findTestObject('Object Repository/Report_Listing/Login_page/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report_Listing/Login_page/button_Sign In'))
WebUI.waitForPageLoad(10)
//WebUI.maximizeWindow()
//WebUI.setViewPortSize(1366,768)
WebUI.delay(3)

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))


 driver = DriverFactory.getWebDriver()


double microcyteDouble = Double.parseDouble(driver.findElement(By.xpath("//div[text()='Microcytes']/following-sibling::div[2]")).getText().trim());
int Microcytescount = (int) Math.round(microcyteDouble);



// Define expected value
int EXPECTED_PATCHES_IN_ROW = 11

if( EXPECTED_PATCHES_IN_ROW  >=  Microcytescount) {
	WebElement Macrocytes = driver.findElement(By.xpath("//div[text()='Macrocytes']/following-sibling::div[2]/parent::div"))
	Macrocytes.click()
	println("Click on Macrocytes")
	
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