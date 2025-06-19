import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN & OPEN A REPORT
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// enter credentials
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) FIND & CLICK FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
// the raw xpath for all “Under review” spans
String xpathAll = "//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"

// use Selenium to see if any exist
List<WebElement> els = DriverFactory
	.getWebDriver()
	.findElements(By.xpath(xpathAll))

if (els.isEmpty()) {
	KeywordUtil.markFailedAndStop("No ‘Under review’ reports were found on the page")
}

// build a TestObject that points at the first one
TestObject firstUnder = new TestObject('firstUnder')
firstUnder.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"(" + xpathAll + ")[1]"    // the “[1]” picks out the first match
)

// scroll it into view (in case it’s off-screen)
WebUI.scrollToElement(firstUnder, 10)

// now click it
WebUI.click(firstUnder)


// ────────────────────────────────────────────────────────────────────
// 2) VERIFY & CLICK KEBAB MENU (⋮) IN TOP-RIGHT
// ────────────────────────────────────────────────────────────────────
// locate the “three-dots” button by its icon
TestObject kebabBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'/icons/kebab_menu.svg')]]")

WebUI.waitForElementClickable(kebabBtn, 10)
WebUI.comment("✔ Kebab menu button is present")
WebUI.click(kebabBtn)
WebUI.comment("✔ Clicked the kebab menu")

// ────────────────────────────────────────────────────────────────────
// 3) VERIFY THE TWO MENU OPTIONS
// ────────────────────────────────────────────────────────────────────
// “Switch to original report”
TestObject switchOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li[normalize-space() = 'Switch to original report']")
WebUI.waitForElementVisible(switchOpt, 5)
WebUI.verifyElementPresent(switchOpt, 1)
WebUI.comment("✔ 'Switch to original report' option is visible")

// “History”
TestObject historyOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li[normalize-space() = 'History']")
WebUI.waitForElementVisible(historyOpt, 5)
WebUI.verifyElementPresent(historyOpt, 1)
WebUI.comment("✔ 'History' option is visible")

WebUI.comment("Kebab-menu options verified successfully.")
