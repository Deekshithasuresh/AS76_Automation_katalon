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

// ---------- STEP 3: Find & click an “Under review” report ----------
TestObject underReview = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[normalize-space()='Under review']"
)
WebUI.waitForElementPresent(underReview, 10)
WebUI.scrollToElement(underReview, 5)
WebUI.click(underReview)

// ---------- STEP 4: Open the slide-info drawer ----------
TestObject slideInfoIcon = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// wait for drawer
TestObject drawer = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 5: Verify expected headers exist ----------
String[] headers = [
    'Slide ID:',
    // status container
    "//div[contains(@class,'slideInfoComponent_status__under-review')]//span[normalize-space()='Under review']",
    'Slide image',
    'Scanned by',
    'Scanned on'
]
// text headers
['Slide ID:', 'Slide image', 'Scanned by', 'Scanned on'].each { txt ->
    WebUI.verifyElementPresent(
        new TestObject().addProperty('xpath', ConditionType.EQUALS,
            "//*[normalize-space(text())='${txt}']"),
        5, FailureHandling.CONTINUE_ON_FAILURE)
}
// status
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, headers[1]),
    5, FailureHandling.CONTINUE_ON_FAILURE)

// ---------- STEP 6: Ensure “Scan time” header is NOT present anywhere ----------
WebUI.verifyElementNotPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS,
        "//*[normalize-space(text())='Scan time']"),
    5, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment("✔ Verified that no ‘Scan time’ header is present in the slide-info popup.")

