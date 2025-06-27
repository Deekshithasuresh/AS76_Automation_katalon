import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

// ────────────────────────────────────────────────────────────────────
// THESE THREE IMPORTS FIX the “unable to resolve class WebElement” ERROR
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
// ────────────────────────────────────────────────────────────────────

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY PBS HEADER
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

// ──────────────────────────────────────────────────────────
// STEP 2 — Navigate to RBC → Shape → Microscopic view
// ──────────────────────────────────────────────────────────
// Click RBC tab
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the RBC tab")
}

// Click Shape sub-tab
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Shape']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Shape sub-tab")
}

// Click Microscopic view icon
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Microscopic view icon")
}

// allow pane to load
WebUI.delay(90)
WebUI.comment("⏲ 90s delay complete — now verifying shape-color codes")

// ──────────────────────────────────────────────────────────
// STEP 3 — Define expected RGB color codes for each RBC-shape
// ──────────────────────────────────────────────────────────
Map<String,String> expectedShapeColors = [
	'Ovalocytes'      : 'rgb(88, 236, 153)',
	'Elliptocytes'    : 'rgb(224, 66, 77)',
	'Teardrop Cells'  : 'rgb(69, 69, 225)',
	'Fragmented Cells': 'rgb(80, 210, 228)',
	'Target Cells'    : 'rgb(236, 80, 173)',
	'Echinocytes'     : 'rgb(160, 239, 32)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 — Grab all rows in the left-pane RBC-microscopic table
// ──────────────────────────────────────────────────────────
TestObject allRowsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> allRows = WebUiCommonHelper.findWebElements(allRowsTO, 10)
WebUI.comment("🔎 Found ${allRows.size()} row(s) in the RBC-microscopic left pane")

if (allRows.isEmpty()) {
	WebUI.comment("⚠️ No rows found – check that the microscopic pane loaded correctly.")
} else {
	// ──────────────────────────────────────────────────────────
	// STEP 5 — Verify each shape’s swatch color
	// ──────────────────────────────────────────────────────────
	allRows.each { WebElement row ->
		WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
		String shapeName = nameTd.getText().trim()

		if (expectedShapeColors.containsKey(shapeName)) {
			WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
			String actualCssColor = colorDiv.getCssValue("background-color").trim()
			String expectedRgb   = expectedShapeColors[shapeName]

			if (actualCssColor == expectedRgb) {
				WebUI.comment("✔ [$shapeName] color is correct ($expectedRgb)")
			} else {
				WebUI.comment("❌ [$shapeName] mismatch: actual='$actualCssColor' expected='$expectedRgb'")
			}
		} else {
			WebUI.comment("→ Skipping non-target row '$shapeName'")
		}
	}
}

WebUI.comment("✅ Done verifying RBC-shape color codes")
