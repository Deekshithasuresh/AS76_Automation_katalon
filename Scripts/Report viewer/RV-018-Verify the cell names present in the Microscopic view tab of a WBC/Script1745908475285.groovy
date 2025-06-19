import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ─── STEP 1: Login ─────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ─── STEP 2: Verify PBS header ─────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
).with {
	WebUI.verifyElementPresent(it, 10)
	WebUI.comment("✅ Logged in and saw PBS header")
}

// ─── STEP 3: Open a report in “To be reviewed” or “Under review” ────
TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']"
)
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.scrollToElement(toBe, 5)
	WebUI.click(toBe)
	WebUI.comment("✔ Opened a ‘To be reviewed’ report")
} else {
	TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//span[normalize-space()='Under review']"
	)
	if (WebUI.waitForElementPresent(under, 5)) {
		WebUI.scrollToElement(under, 5)
		WebUI.click(under)
		WebUI.comment("✔ Opened an ‘Under review’ report")
	} else {
		WebUI.comment("❌ No report found in either status")
		WebUI.closeBrowser()
		return
	}
}

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


