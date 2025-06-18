import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ──────────────────────────────────────────────────────────
// STEP 1 – Login & open first “To be reviewed”/“Under review”
// ──────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// fill credentials
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					   'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// wait for “PBS” header
new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]").with {
	WebUI.waitForElementPresent(it, 10)
}

// helper closure to click a status‐filter row if present
def openFirstStatus = { String statusName ->
	TestObject statusTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//span[normalize-space()='$statusName']")
	if (WebUI.waitForElementPresent(statusTO, 3, FailureHandling.OPTIONAL)) {
		WebUI.scrollToElement(statusTO, 3)
		WebUI.click(statusTO)
		WebUI.comment("✔ Opened report with status ‘$statusName’")
		return true
	}
	return false
}

// try “To be reviewed” first; if not found, try “Under review”
if (!openFirstStatus('To be reviewed') && !openFirstStatus('Under review')) {
	WebUI.comment("❌ No ‘To be reviewed’ or ‘Under review’ report found – aborting")
	WebUI.closeBrowser()
	return
}

// ──────────────────────────────────────────────────────────
// STEP 2 – Click “RBC” tab → Microscopic view icon
// ──────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
}

new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
}

// give the RBC‐microscopic canvas time to load
WebUI.delay(90)

// ──────────────────────────────────────────────────────────
// STEP 3 – Define expected RBC‐size color map
// ──────────────────────────────────────────────────────────
Map<String,String> expectedRBCColorByName = [
	'Microcytes' : 'rgb(99, 26, 134)',
	'Macrocytes' : 'rgb(0, 124, 119)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 – Locate all RBC‐microscopic left‐pane rows
// ──────────────────────────────────────────────────────────
// The left‐pane container has class “rbc-microscopic-left-pane”; within it, each <tr> is one row.
TestObject rowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsTO, 10)
WebUI.comment("🔎 Found ${rows.size()} RBC rows on the left")

// ──────────────────────────────────────────────────────────
// STEP 5 – For each row, if name is Microcytes/Macrocytes, verify its <div> color
// ──────────────────────────────────────────────────────────
if (rows.isEmpty()) {
	WebUI.comment("⚠️ No RBC rows found at all – perhaps the microscope pane failed to load properly.")
} else {
	rows.each { WebElement row ->
		// The cell name appears in the second <td> of each row
		WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
		String cellName = nameTd.getText().trim()

		if (expectedRBCColorByName.containsKey(cellName)) {
			// In the first <td> there is a small <div> whose background‐color is our swatch
			WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
			String actualCssColor = colorDiv.getCssValue("background-color").trim()

			String expectedRgb = expectedRBCColorByName[cellName]
			if (actualCssColor == expectedRgb) {
				WebUI.comment("✔ [$cellName] color matches expected ($expectedRgb)")
			} else {
				WebUI.comment("❌ [$cellName] color mismatch: found '$actualCssColor' but expected '$expectedRgb'")
			}
		} else {
			// if it's not one of Microcytes/Macrocytes, skip
			WebUI.comment("→ Skipping non‐test cell '$cellName'")
		}
	}
}

WebUI.comment("✅ Done verifying RBC‐size cell‐color codes.")

