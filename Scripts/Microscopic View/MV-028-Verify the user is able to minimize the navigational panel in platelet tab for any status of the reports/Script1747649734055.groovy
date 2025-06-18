import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Wait for list to load ----------
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Open a “To be reviewed” or “Under review” report ----------
TestObject toBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']"
)
TestObject underReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)
if (WebUI.waitForElementPresent(toBeReviewed, 5)) {
	WebUI.scrollToElement(toBeReviewed, 5)
	WebUI.click(toBeReviewed)
} else {
	WebUI.scrollToElement(underReview, 5)
	WebUI.click(underReview)
}
WebUI.comment("✔ Opened a report in ‘To be reviewed’ / ‘Under review’ status.")

// ---------- STEP 4: Click the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)
WebUI.comment("✔ Platelets tab clicked.")

// ---------- STEP 5: Verify Count sub-tab is selected by default ----------
TestObject countSubtab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'sub-tab')]//span[normalize-space()='Count' and contains(@aria-selected,'true')]"
)
WebUI.verifyElementVisible(countSubtab)
WebUI.comment("✔ ‘Count’ sub-tab is active by default.")

// ---------- STEP 6: Activate Microscopic view ----------
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)
WebUI.comment("✔ Microscopic view activated for Platelets→Count.")

// ---------- STEP 7: Pause for the viewer to render ----------
WebUI.delay(5)

// ---------- STEP 8: Toggle the Overview panel on/off ----------
TestObject overviewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@title='Overview']"
)
TestObject overviewBox = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]"
)

// click once → overview‐map box appears
WebUI.waitForElementClickable(overviewBtn, 5)
WebUI.click(overviewBtn)
WebUI.waitForElementVisible(overviewBox, 5)
WebUI.comment("✔ Overview panel appeared on Platelets→Count view.")

// click again → overview‐map box disappears
WebUI.click(overviewBtn)
WebUI.waitForElementNotPresent(overviewBox, 5)
WebUI.comment("✔ Overview panel closed again on Platelets→Count view.")



