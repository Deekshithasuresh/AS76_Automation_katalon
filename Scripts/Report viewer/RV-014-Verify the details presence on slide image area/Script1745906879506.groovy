import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Pick “To be reviewed” or fallback to “Under review” ----------
TestObject toBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']"
)
TestObject underReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)

if (WebUI.waitForElementPresent(toBeReviewed, 5)) {
	WebUI.scrollToElement(toBeReviewed, 5)
	WebUI.click(toBeReviewed)
} else if (WebUI.waitForElementPresent(underReview, 5)) {
	WebUI.scrollToElement(underReview, 5)
	WebUI.click(underReview)
} else {
	WebUI.comment("❌ No report found with 'To be reviewed' or 'Under review' status.")
	WebUI.closeBrowser()
	return
}

// ---------- STEP 4: Click on Slide Info icon ----------
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// ---------- STEP 5: Verify Slide Image area in the drawer ----------
TestObject slideImageArea = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_drawer__body-image__')]"
)
WebUI.waitForElementVisible(slideImageArea, 10)
WebUI.verifyElementPresent(slideImageArea, 5)

// Verify the “Slide image” label
TestObject slideImageLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_drawer__body-image__')]/span[normalize-space()='Slide image']"
)
WebUI.verifyElementPresent(slideImageLabel, 5)

// Verify the actual image element
TestObject slideImageImg = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_drawer__body-image__')]//img[@alt='slide-macro-image']"
)
WebUI.verifyElementPresent(slideImageImg, 5)

WebUI.comment("✅ Slide image area is present with proper label and image.")
