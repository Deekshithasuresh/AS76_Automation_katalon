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

// ---------- STEP 3: Find & click a “To be reviewed” report ----------
TestObject toBeReviewed = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[normalize-space()='To be reviewed']"
)
WebUI.waitForElementPresent(toBeReviewed, 10)
WebUI.scrollToElement(toBeReviewed, 5)
WebUI.click(toBeReviewed)

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
def headers = [
    'Slide ID:',
    'Slide image',
    // status container header
    "//div[contains(@class,'slideInfoComponent_status__to-be-reviewed')]//span[normalize-space()='To be reviewed']",
    'Scanned by',
    'Scanned on'
]

// verify text-based headers
headers[0..1].each { text ->
    WebUI.verifyElementPresent(
        new TestObject().addProperty('xpath', ConditionType.EQUALS,
            "//*[normalize-space(text())='${text}']"),
        5, FailureHandling.CONTINUE_ON_FAILURE)
}

// verify status specially via its container
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, headers[2]),
    5, FailureHandling.CONTINUE_ON_FAILURE)

// verify the last two headers
['Scanned by','Scanned on'].each { text ->
    WebUI.verifyElementPresent(
        new TestObject().addProperty('xpath', ConditionType.EQUALS,
            "//*[normalize-space(text())='${text}']"),
        5, FailureHandling.CONTINUE_ON_FAILURE)
}

// ---------- STEP 6: Ensure “Scan time” header is NOT present anywhere ----------
WebUI.verifyElementNotPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS,
        "//*[normalize-space(text())='Scan time']"),
    5, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment("✔ Verified that no ‘Scan time’ header is present in the slide-info popup.")


