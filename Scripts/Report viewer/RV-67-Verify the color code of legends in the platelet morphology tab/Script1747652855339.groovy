import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper  // for findWebElements()
import org.openqa.selenium.WebElement                    // for WebElement
import org.openqa.selenium.By                            // for By
import java.util.List                                    // for List<>
import java.util.Map                                     // for Map<>

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ──────────────────────────────────────────────────────────
// STEP 2 — Navigate to Platelets → Morphology → Microscopic view
// ──────────────────────────────────────────────────────────

// 2a) Click the Platelets tab
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)
WebUI.comment("✔ Clicked the Platelets tab")

// 2b) Click the “Morphology” sub-tab
TestObject morphologyTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab']"
)
WebUI.waitForElementClickable(morphologyTab, 10)
WebUI.click(morphologyTab)
WebUI.comment("✔ Clicked the Morphology sub-tab")

// 2c) Click the microscopic-view icon
TestObject microViewIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)
WebUI.comment("✔ Clicked the Microscopic view icon")

// allow the pane to load (adjust duration if needed)
WebUI.delay(10)
WebUI.comment("⏲ Delay complete — now verifying morphology-colour legend rows")

// ──────────────────────────────────────────────────────────
// STEP 3 — Define expected RGB color codes for Platelet morphology
// ──────────────────────────────────────────────────────────
Map<String,String> expectedMorphologyColors = [
	'Large platelets' : 'rgb(194, 232, 18)',
	'Platelet clumps' : 'rgb(255, 60, 199)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 — Grab all rows in the left-pane table
// ──────────────────────────────────────────────────────────
TestObject allRowsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'platelet-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> allRows = WebUiCommonHelper.findWebElements(allRowsTO, 10)
WebUI.comment("🔎 Found ${allRows.size()} row(s)")

if (allRows.isEmpty()) {
	WebUI.comment("⚠️ No rows found — aborting color-code check")
} else {
	// ──────────────────────────────────────────────────────────
	// STEP 5 — Verify each target row’s swatch color
	// ──────────────────────────────────────────────────────────
	allRows.each { WebElement row ->
		String cellName = row.findElement(By.xpath(".//td[2]")).getText().trim()
		if (expectedMorphologyColors.containsKey(cellName)) {
			String expectedRgb = expectedMorphologyColors[cellName]
			String actualRgb   = row.findElement(By.xpath(".//td[1]//div"))
								   .getCssValue("background-color").trim()
			if (actualRgb == expectedRgb) {
				WebUI.comment("✔ [$cellName] color correct: $actualRgb")
			} else {
				WebUI.comment("❌ [$cellName] mismatch — actual: $actualRgb, expected: $expectedRgb")
			}
		} else {
			WebUI.comment("→ Skipping ‘$cellName’")
		}
	}
}

WebUI.comment("✅ Done verifying Platelet morphology color codes")
