import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

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
// ─── STEP 4: Click the WBC tab ──────────────────────────────
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='WBC']]"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)
WebUI.comment("✔ WBC tab clicked")

// ─── STEP 5: Verify WBC header ─────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[@class='table-cell-name' and normalize-space()='WBC']"
).with {
	WebUI.verifyElementPresent(it, 5)
	WebUI.comment("✅ WBC header is present")
}

// ─── STEP 6: Open microscopic view ─────────────────────────
TestObject microIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
)
WebUI.waitForElementClickable(microIcon, 10)
WebUI.click(microIcon)
WebUI.comment("✔ Microscopic view opened")

// ─── STEP 7: Verify each WBC cell name exists ──────────────
List<String> expectedCells = [
	'Neutrophils','Lymphocytes','Eosinophils','Monocytes','Basophils',
	'Immature Granulocytes','Atypical Cells/Blasts','Immature Eosinophils','Immature Basophils',
	'Promonocytes','Prolymphocytes','Hairy Cells','Sezary Cells','Plasma Cells','Others',
	'NRBC','Smudge Cells','Degenerate Cells','Stain Artefacts','Unclassified','Rejected'
]
expectedCells.each { name ->
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//div[contains(@class,'cell-label') and normalize-space()='$name']"
	).with {
		WebUI.verifyElementPresent(it, 5, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.comment("✔ Found cell name: $name")
	}
}

// ─── STEP 8: Verify the ORDER of those cells ──────────────
TestObject allLabelsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'cell-label')]"
)
List<WebElement> actualLabels = WebUiCommonHelper.findWebElements(allLabelsTO, 10)
assert actualLabels.size() == expectedCells.size() :
	"Expected ${expectedCells.size()} labels, but found ${actualLabels.size()}"

for (int i = 0; i < expectedCells.size(); i++) {
	String actual = actualLabels[i].getText().trim()
	String expect = expectedCells[i]
	WebUI.verifyMatch(actual, expect, false, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.comment("Row ${i+1}: expected='${expect}', actual='${actual}'")
}


