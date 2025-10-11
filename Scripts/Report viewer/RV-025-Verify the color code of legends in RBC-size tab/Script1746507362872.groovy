import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

// ────────────────────────────────────────────────────────────────────
// THESE IMPORTS FIX your “unable to resolve class WebElement” ERROR
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
// ────────────────────────────────────────────────────────────────────

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://pbsreview.as76.local/login')
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

// give the RBC-microscopic canvas time to load
WebUI.delay(90)

// ──────────────────────────────────────────────────────────
// STEP 3 – Define expected RBC-size color map
// ──────────────────────────────────────────────────────────
Map<String,String> expectedRBCColorByName = [
	'Microcytes' : 'rgb(99, 26, 134)',
	'Macrocytes' : 'rgb(0, 124, 119)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 – Locate all RBC-microscopic left-pane rows
// ──────────────────────────────────────────────────────────
TestObject rowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsTO, 10)
WebUI.comment("🔎 Found ${rows.size()} RBC rows on the left")

// ──────────────────────────────────────────────────────────
// STEP 5 – Verify color swatches for Microcytes/Macrocytes
// ──────────────────────────────────────────────────────────
if (rows.isEmpty()) {
	WebUI.comment("⚠️ No RBC rows found – check that the microscopic pane loaded correctly.")
} else {
	rows.each { WebElement row ->
		// name in second <td>
		WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
		String cellName = nameTd.getText().trim()

		if (expectedRBCColorByName.containsKey(cellName)) {
			// swatch DIV in first <td>
			WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
			String actualCssColor = colorDiv.getCssValue("background-color").trim()
			String expectedRgb = expectedRBCColorByName[cellName]

			if (actualCssColor == expectedRgb) {
				WebUI.comment("✔ [$cellName] color matches expected ($expectedRgb)")
			} else {
				WebUI.comment("❌ [$cellName] color mismatch: found '$actualCssColor' but expected '$expectedRgb'")
			}
		} else {
			WebUI.comment("→ Skipping non-test row '$cellName'")
		}
	}
}

WebUI.comment("✅ Done verifying RBC-size color codes.")
