import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

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

// — STEP 4: Click the **Platelets** tab —
TestObject pltTab = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(pltTab, 10)
WebUI.click(pltTab)
WebUI.comment("✔ Platelets tab clicked")

// — STEP 5: Switch to Microscopic view —
TestObject microBtn = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)
WebUI.comment("✔ Switched to microscopic view")

// — STEP 6: Give the canvas time to render —
WebUI.delay(3)

// — STEP 7: Verify the stitched-image canvas is present —
TestObject pltCanvas = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'ol-viewport')]//canvas"
)
WebUI.waitForElementVisible(pltCanvas, 20)
WebUI.verifyElementVisible(pltCanvas)
WebUI.comment("✔ Stitched Platelets microscopic image canvas is present and visible")


