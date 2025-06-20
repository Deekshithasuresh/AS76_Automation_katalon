import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ────────────────────────────────────────────────────────────────────
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
TestObject underReviewRow = new TestObject('underReviewRow').addProperty(
	'xpath', ConditionType.EQUALS, underReviewXpath
)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject('assignedDropdown').addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject('adminOption').addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject('assignedInput').addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)

// ────────────────────────────────────────────────────────────────────
// 5) NAVIGATE TO RBC → Colour → Microscopic view
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
}

new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Colour']"
).with {
	WebUI.waitForElementClickable(it, 5)
	WebUI.click(it)
}

new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.delay(2)
}

// ──────────────────────────────────────────────────────────
// 6) VERIFY RIGHT-SIDE CONTROLS
// ──────────────────────────────────────────────────────────
[
	"//img[@alt='line-tool']",
	"//img[@alt='circle-tool']",
	"//img[@alt='zoom-tool']",
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']",
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']",
	"//button[@title='Overview']",
	"//div[.//img[@alt='home']]"
].each { xpath ->
	WebUI.verifyElementPresent(
		new TestObject().addProperty('xpath', ConditionType.EQUALS, xpath),
		5,
		FailureHandling.CONTINUE_ON_FAILURE
	)
}

// ──────────────────────────────────────────────────────────
// 7) VERIFY LEFT-SIDE COLOUR TABLE NAMES & ORDER
// ──────────────────────────────────────────────────────────
List<String> expectedColours = [
	'Hypochromatic Cells',
	'Polychromatic Cells',
]

TestObject colourRowsTO = new TestObject('colourRowsTO').addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)

List<WebElement> colourRows = WebUiCommonHelper.findWebElements(colourRowsTO, 10)
List<String> actualColourLabels = colourRows.collect { row ->
	row.findElement(By.xpath(".//td[2]")).getText().trim()
}

WebUI.comment("🔎 Colour rows found: ${actualColourLabels}")

expectedColours.eachWithIndex { name, idx ->
	if (actualColourLabels.contains(name)) {
		WebUI.comment("✔ Found '${name}'")
		if (idx > 0 && actualColourLabels.contains(expectedColours[idx - 1])) {
			int prevIndex = actualColourLabels.indexOf(expectedColours[idx - 1])
			int thisIndex = actualColourLabels.indexOf(name)
			WebUI.verifyTrue(
				thisIndex > prevIndex,
				FailureHandling.CONTINUE_ON_FAILURE,
				"❌ '${name}' (idx ${thisIndex}) should come after '${expectedColours[idx - 1]}' (idx ${prevIndex})"
			)
		}
	} else {
		WebUI.comment("⚠ Missing expected colour '${name}'")
	}
}

WebUI.comment("✅ Finished RBC-Colour microscopic view checks.")
