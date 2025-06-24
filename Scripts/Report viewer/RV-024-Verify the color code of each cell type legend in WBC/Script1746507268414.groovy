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
// STEP 2 – Click “WBC” tab → Microscopic view icon
// ──────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='WBC']]"
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

// give the microscopic‐canvas plenty of time to load
WebUI.delay(120)

// ──────────────────────────────────────────────────────────
// STEP 3 – Define our expected WBC→color map
// ──────────────────────────────────────────────────────────
Map<String,String> expectedColorByName = [
	'Neutrophils'           : 'rgb(255, 0, 18)',
	'Lymphocytes'           : 'rgb(0, 255, 43)',
	'Eosinophils'           : 'rgb(255, 246, 0)',
	'Monocytes'             : 'rgb(0, 255, 212)',
	'Basophils'             : 'rgb(229, 0, 255)',
	'Immature Granulocytes' : 'rgb(0, 207, 131)',
	'Atypical Cells/Blasts' : 'rgb(236, 80, 173)',
	'Immature Eosinophils'  : 'rgb(0, 178, 255)',
	'Immature Basophils'    : 'rgb(255, 168, 0)',
	'Promonocytes'          : 'rgb(0, 133, 133)',
	'Prolymphocytes'        : 'rgb(111, 0, 163)',
	'Hairy Cells'           : 'rgb(176, 48, 96)',
	'Sezary Cells'          : 'rgb(3, 171, 9)',
	'Plasma Cells'          : 'rgb(255, 0, 127)',
	'Others'                : 'rgb(255, 52, 127)',
	'NRBC'                  : 'rgb(189, 255, 0)',
	'Smudge Cells'          : 'rgb(255, 122, 0)',
	'Degenerate Cells'      : 'rgb(255, 213, 64)',
	'Stain Artefacts'       : 'rgb(116, 0, 88)',
	'Unclassified'          : 'rgb(94, 94, 94)',
	'Rejected'              : 'rgb(0, 0, 0)'
]

// ──────────────────────────────────────────────────────────
// STEP 4 – Locate all WBC‐microscopic left‐pane rows
// ──────────────────────────────────────────────────────────
// This XPath looks under the WBC microscopic‐left pane for each <tr>:
TestObject rowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'wbc-microscopic-left-pane')]//table//tbody/tr"
)

List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsTO, 10)
WebUI.comment("🔎 Found ${rows.size()} WBC rows on the left")

// ──────────────────────────────────────────────────────────
// STEP 5 – For each row, if the name is in our map, verify its bubble’s color
// ──────────────────────────────────────────────────────────
rows.each { WebElement row ->
	// cell name sits in the second <td>
	WebElement nameTd = row.findElement(By.xpath(".//td[2]"))
	String cellName = nameTd.getText().trim()

	if (expectedColorByName.containsKey(cellName)) {
		// find the little color‐swatch DIV in the first <td>
		WebElement colorDiv = row.findElement(By.xpath(".//td[1]//div"))
		String actualCssColor = colorDiv.getCssValue("background-color").trim()

		String expectedRgb = expectedColorByName[cellName]
		if (actualCssColor == expectedRgb) {
			WebUI.comment("✔ [$cellName] color matches expected ($expectedRgb)")
		} else {
			WebUI.comment("❌ [$cellName] color mismatch: found '$actualCssColor' but expected '$expectedRgb'")
		}
	} else {
		// If it isn’t one of our keys, we skip it
		WebUI.comment("→ Skipping extra cell '$cellName'")
	}
}

WebUI.comment("✅ Done verifying WBC cell‐color codes.")

