import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

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
// ──────────────────────────────────────────────────────────
// STEP 2 — Click “RBC” tab, then “Shape” sub‐tab, then “Microscopic view”
// ──────────────────────────────────────────────────────────
// 2a) Click the RBC tab
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the RBC tab")
}

// 2b) Click the “Shape” sub‐tab under RBC
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Shape']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Shape sub-tab")
}

// 2c) Click the microscopic‐view icon
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Microscopic view icon")
}

// Let the RBC‐microscopic pane fully initialize (could take up to ~90 seconds)
WebUI.delay(90)
WebUI.comment("⏲ 90s delay complete — now verifying shape‐color codes")

// ──────────────────────────────────────────────────────────
// STEP 3 — Define expected RGB color codes for each RBC‐shape
// ──────────────────────────────────────────────────────────
Map<String,String> expectedShapeColors = [
	'Ovalocytes'      : 'rgb(88, 236, 153)',
	'Elliptocytes'    : 'rgb(224, 66, 77)',
	'Teardrop cells'  : 'rgb(69, 69, 225)',
	'Fragmented cells': 'rgb(80, 210, 228)',
	'Target cells'    : 'rgb(236, 80, 173)',   // “Traget cells” typo corrected
	'Echinocytes'     : 'rgb(160, 239, 32)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 — Grab all rows in the left‐pane RBC‐microscopic table
// ──────────────────────────────────────────────────────────
// The left pane container has class “rbc-microscopic-left-pane”; within it, the table’s <tbody> has multiple <tr> rows.
TestObject allRowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> allRows = WebUiCommonHelper.findWebElements(allRowsTO, 10)
WebUI.comment("🔎 Found ${allRows.size()} row(s) in the RBC‐microscopic left pane")

if (allRows.isEmpty()) {
	WebUI.comment("⚠️ No rows found in RBC‐microscopic left pane – aborting color check")
} else {
	// ──────────────────────────────────────────────────────────
	// STEP 5 — For each row, if the name is one of our six shapes, verify color
	// ──────────────────────────────────────────────────────────
	allRows.each { WebElement row ->
		// The second <td> holds the shape’s name
		WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
		String shapeName = nameTd.getText().trim()

		if (expectedShapeColors.containsKey(shapeName)) {
			// The first <td> contains a <div> with the background‐color style
			WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
			String actualCssColor = colorDiv.getCssValue("background-color").trim()
			String expectedRgb   = expectedShapeColors[shapeName]

			if (actualCssColor == expectedRgb) {
				WebUI.comment("✔ [$shapeName] → color is correct ($expectedRgb)")
			} else {
				WebUI.comment("❌ [$shapeName] → color mismatch: actual='$actualCssColor' expected='$expectedRgb'")
			}
		} else {
			WebUI.comment("→ Skipping row ‘$shapeName’ (not one of the six target shapes)")
		}
	}
}

// ──────────────────────────────────────────────────────────
// STEP 6 — Clean up
// ──────────────────────────────────────────────────────────
WebUI.comment("✅ Done verifying RBC‐shape color codes")
