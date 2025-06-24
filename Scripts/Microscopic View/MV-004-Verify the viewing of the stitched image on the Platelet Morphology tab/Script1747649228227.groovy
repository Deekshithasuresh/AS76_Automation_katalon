import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.Keys

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(
	findTestObject('Report viewer/Page_PBS/input_username_loginId'),
	'adminuserr'
)
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ---------- STEP: Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab') and .//span[normalize-space()='Platelets']]"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)
WebUI.comment("✔ Platelets tab clicked successfully.")

// ---------- STEP: Click on the Morphology sub-tab ----------
TestObject morphTab = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[@id='plateleteMorphologyTab']"
)
WebUI.waitForElementClickable(morphTab, 10)
WebUI.click(morphTab)
WebUI.comment("✔ Platelet Morphology view selected.")

// ---------- STEP: Activate Microscopic view ----------
TestObject microViewBtn = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and contains(@src,'microscopic-view')]"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)
WebUI.comment("✔ Microscopic view activated for Platelet Morphology.")

// ---------- STEP: Allow canvas to finish rendering ----------
WebUI.delay(3)

// ---------- STEP: Verify the stitched canvas is present ----------
TestObject pltMorphCanvas = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'ol-layer')]//canvas"
)
WebUI.waitForElementPresent(pltMorphCanvas, 20)
WebUI.verifyElementVisible(pltMorphCanvas)
WebUI.comment("✔ Stitched Platelet‐Morphology canvas is present and visible.")
