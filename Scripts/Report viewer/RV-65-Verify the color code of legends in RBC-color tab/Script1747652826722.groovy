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
// STEP 2 — Click “RBC” tab, then “Colour” sub‐tab, then “Microscopic view”
// ──────────────────────────────────────────────────────────
// 2a) Click the RBC tab
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the RBC tab")
}

// 2b) Click the “Colour” sub‐tab under RBC
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Colour']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Colour sub-tab")
}

// 2c) Click the microscopic‐view icon
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Microscopic view icon")
}

// Allow up to 90 seconds for the RBC‐microscopic pane to load fully
WebUI.delay(90)
WebUI.comment("⏲ 90s delay complete — now verifying colour‐code rows")

// ──────────────────────────────────────────────────────────
// STEP 3 — Define expected RGB color codes for each RBC‐colour row
// ──────────────────────────────────────────────────────────
Map<String,String> expectedColourMap = [
	'Hypochromic Cells'    : 'rgb(35, 0, 30)',
	'Polychromatic Cells'   : 'rgb(124, 222, 220)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 — Grab all rows in the left‐pane RBC‐microscopic table
// ──────────────────────────────────────────────────────────
// Left‐pane container has class “rbc-microscopic-left-pane”.  Under that, <table><tbody><tr> each is a color-row.
TestObject allRowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> allRows = WebUiCommonHelper.findWebElements(allRowsTO, 10)
WebUI.comment("🔎 Found ${allRows.size()} row(s) in the RBC‐microscopic left pane")

if (allRows.isEmpty()) {
	WebUI.comment("⚠️ No rows found in RBC‐microscopic left pane – aborting color‐code check")
} else {
	// ──────────────────────────────────────────────────────────
	// STEP 5 — For each row, if its name matches one of our two keys, verify its swatch color
	// ──────────────────────────────────────────────────────────
	allRows.each { WebElement row ->
		// The second <td> holds the cell’s name text
		WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
		String cellName = nameTd.getText().trim()

		if (expectedColourMap.containsKey(cellName)) {
			// The first <td> contains a <div> styled with background‐color
			WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
			String actualCssColor = colorDiv.getCssValue("background-color").trim()
			String expectedRgb   = expectedColourMap[cellName]

			if (actualCssColor == expectedRgb) {
				WebUI.comment("✔ [$cellName] → color is correct ($expectedRgb)")
			} else {
				WebUI.comment("❌ [$cellName] → color mismatch: actual='$actualCssColor' expected='$expectedRgb'")
			}
		} else {
			WebUI.comment("→ Skipping row ‘$cellName’ (not one of the two target colours)")
		}
	}
}

// ──────────────────────────────────────────────────────────
// STEP 6 — Clean up
// ──────────────────────────────────────────────────────────
WebUI.comment("✅ Done verifying RBC‐colour code rows")

