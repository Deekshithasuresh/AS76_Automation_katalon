import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// Selenium & Katalon driver/helper imports
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.Keys

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 4) CLICK WBC TAB
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='WBC']/ancestor::button"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// 5) OPEN MICROSCOPIC VIEW
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

// 6) ACTIVATE LINE TOOL
TestObject lineTool = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='line-tool']]"
)
WebUI.waitForElementClickable(lineTool, 10)
WebUI.click(lineTool)

// 7) DRAW A LINE ON CANVAS
WebDriver driver = DriverFactory.getWebDriver()
WebElement canvas = driver.findElement(By.tagName("canvas"))

// click+drag from (50,50) over 100px to the right
new Actions(driver)
	.moveToElement(canvas, 50, 50)
	.clickAndHold()
	.moveByOffset(100, 0)
	.release()
	.perform()

// 8) WAIT FOR DIMENSION INPUT
TestObject dimInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[contains(@id,'adornment') and @type='text']"
)
WebUI.waitForElementVisible(dimInput, 10)
WebUI.comment("✔ Dimension input is visible")

// 9) VERIFY SIZES BETWEEN 5µm AND 20µm
TestObject measureOverlay = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'-inner') and contains(.,'µm')]"
)
List<String> lengths = ['5','6','10','15','20']
for (String len : lengths) {
	WebUI.click(dimInput)
	WebUI.clearText(dimInput)
	WebUI.setText(dimInput, len)
	WebUI.sendKeys(dimInput, Keys.ENTER)
	WebUI.verifyMatch(WebUI.getAttribute(dimInput, 'value'), len, false, "✔ Input updated to ${len} µm")

	WebUI.waitForElementVisible(measureOverlay, 5)
	String overlay = WebUI.getText(measureOverlay).trim()
	WebUI.verifyMatch(overlay, "${len} µm", false, "✔ Overlay reads ${len} µm")
}

WebUI.comment("✅ All line‐length adjustments verified successfully.")
