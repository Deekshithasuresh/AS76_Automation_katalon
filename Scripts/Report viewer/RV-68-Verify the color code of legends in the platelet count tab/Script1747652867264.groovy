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
// STEP 2 — Navigate to Platelets → Count → Microscopic view
// ──────────────────────────────────────────────────────────

// 2a) Click the Platelets tab
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)
WebUI.comment("✔ Clicked the Platelets tab")

// 2b) Click the “Count” sub-tab
TestObject countTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@id='plateleteCountTab']"
)
WebUI.waitForElementClickable(countTab, 10)
WebUI.click(countTab)
WebUI.comment("✔ Clicked the Count sub-tab")

// 2c) Click the microscopic-view icon
TestObject microViewIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)
WebUI.comment("✔ Clicked the Microscopic view icon")

// allow pane to load
WebUI.delay(10)
WebUI.comment("⏲ Delay complete — now verifying platelet-colour legend rows")

// ──────────────────────────────────────────────────────────
// STEP 3 — Define expected RGB color codes for Platelet cells
// ──────────────────────────────────────────────────────────
Map<String,String> expectedPlateletColors = [
	'Normal Platelets' : 'rgb(76, 156, 235)',
	'Macro Platelets'  : 'rgb(88, 236, 153)',
	'Giant Platelets'  : 'rgb(224, 66, 77)'
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
	WebUI.comment("⚠️ No rows found — aborting color check")
} else {
	// ──────────────────────────────────────────────────────────
	// STEP 5 — For each row, verify swatch color if it’s one of our targets
	// ──────────────────────────────────────────────────────────
	allRows.each { WebElement row ->
		WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
		String cellName = nameTd.getText().trim()
		if (expectedPlateletColors.containsKey(cellName)) {
			WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
			String actualRgb   = colorDiv.getCssValue("background-color").trim()
			String expectedRgb = expectedPlateletColors[cellName]
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

WebUI.comment("✅ Done verifying Platelet-colour code rows")
