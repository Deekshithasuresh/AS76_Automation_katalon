import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ──────────────────────────────────────────────────────────
// STEP 1 — Log in and open the first “To be reviewed”/“Under review” report
// ──────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Enter credentials and submit
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// Wait for the PBS list to appear
new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]").with {
	WebUI.waitForElementPresent(it, 10)
}

// Helper closure to click a status row if present
def openFirstReportWithStatus = { String statusText ->
	TestObject statusTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//span[normalize-space()='$statusText']")
	if (WebUI.waitForElementPresent(statusTO, 3, FailureHandling.OPTIONAL)) {
		WebUI.scrollToElement(statusTO, 3)
		WebUI.click(statusTO)
		WebUI.comment("✔ Opened report with status ‘$statusText’")
		return true
	}
	return false
}

// Try “To be reviewed”; if not found, try “Under review”
if (!openFirstReportWithStatus('To be reviewed') &&
	!openFirstReportWithStatus('Under review')) {
	WebUI.comment("❌ No report found in ‘To be reviewed’ or ‘Under review’ → aborting")
	WebUI.closeBrowser()
	return
}

// ──────────────────────────────────────────────────────────
// STEP 2 — Navigate to Platelets → Count → Microscopic view
// ──────────────────────────────────────────────────────────
// 2a) Click the Platelets tab
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Platelets']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Platelets tab")
}

// 2b) Click the “Count” sub‐tab under Platelets
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteCountTab']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Count sub-tab")
}

// 2c) Click the microscopic‐view icon
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.comment("✔ Clicked the Microscopic view icon")
}

// Allow up to 120 seconds for the Platelets‐microscopic pane to load fully
WebUI.delay(120)
WebUI.comment("⏲ 120s delay complete — now verifying platelet‐colour code rows")

// ──────────────────────────────────────────────────────────
// STEP 3 — Define expected RGB color codes for Platelet cells
// ──────────────────────────────────────────────────────────
Map<String,String> expectedPlateletColors = [
	'Normal Platelets'    : 'rgb(76, 156, 235)',
	'Macro Platelets'     : 'rgb(88, 236, 153)',
	'Giant Platelets'     : 'rgb(224, 66, 77)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 — Grab all rows in the left‐pane Platelets‐microscopic table
// ──────────────────────────────────────────────────────────
// The left‐pane container has class “platelet-microscopic-left-pane”; each <tr> is one row.
TestObject allRowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'platelet-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> allRows = WebUiCommonHelper.findWebElements(allRowsTO, 10)
WebUI.comment("🔎 Found ${allRows.size()} row(s) in the Platelets‐microscopic left pane")

if (allRows.isEmpty()) {
	WebUI.comment("⚠️ No rows found in Platelets‐microscopic left pane – aborting color check")
} else {
	// ──────────────────────────────────────────────────────────
	// STEP 5 — For each row, if its name matches one of our three keys, verify its swatch color
	// ──────────────────────────────────────────────────────────
	allRows.each { WebElement row ->
		// The second <td> holds the cell’s name text
		WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
		String cellName = nameTd.getText().trim()

		if (expectedPlateletColors.containsKey(cellName)) {
			// The first <td> contains a <div> styled with background‐color
			WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
			String actualCssColor = colorDiv.getCssValue("background-color").trim()
			String expectedRgb   = expectedPlateletColors[cellName]

			if (actualCssColor == expectedRgb) {
				WebUI.comment("✔ [$cellName] → color is correct ($expectedRgb)")
			} else {
				WebUI.comment("❌ [$cellName] → color mismatch: actual='$actualCssColor' expected='$expectedRgb'")
			}
		} else {
			WebUI.comment("→ Skipping row ‘$cellName’ (not one of the three target platelet types)")
		}
	}
}

WebUI.comment("✅ Done verifying Platelet‐colour code rows")

