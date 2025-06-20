import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment(" 'Approve report' is now visible.")

// ---------- STEP 7: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='WBC']]"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// ---------- STEP 8: Open Image Settings ----------
TestObject imgSettingsIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[contains(@class,'image-settings') and @alt='image_setting']"
)
WebUI.waitForElementClickable(imgSettingsIcon, 10)
WebUI.click(imgSettingsIcon)

// ---------- STEP 9: Verify popup & “Image Size” header ----------
TestObject popup = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'img-utils-container')]"
)
WebUI.waitForElementVisible(popup, 10)
TestObject sizeHeader = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'control-title') and normalize-space()='Image Size']"
)
WebUI.verifyElementPresent(sizeHeader, 10)

// ---------- STEP 10: Measure thumbnail width BEFORE change ----------
Number widthBefore = WebUI.executeJavaScript(
	"return document.querySelectorAll('div.microscopic-right-pane img')[0].clientWidth;",
	null
) as Number

// ---------- STEP 11: Slide “Image Size” to max via JS ----------
WebUI.executeJavaScript("""
  var slider = document.evaluate(
    "//div[contains(text(),'Image Size')]/following-sibling::div//input[@type='range']",
    document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null
  ).singleNodeValue;
  slider.value = slider.max;
  slider.dispatchEvent(new Event('input',{bubbles:true}));
  slider.dispatchEvent(new Event('change',{bubbles:true}));
""", null)
WebUI.delay(1)  // allow re-render

// ---------- STEP 12: Measure thumbnail width AFTER change ----------
Number widthAfter = WebUI.executeJavaScript(
	"return document.querySelectorAll('div.microscopic-right-pane img')[0].clientWidth;",
	null
) as Number

// ---------- STEP 13: Assert thumbnails grew ----------
WebUI.verifyGreaterThan(widthAfter, widthBefore)
WebUI.comment("✅ Thumbnail width grew from ${widthBefore}px to ${widthAfter}px")


