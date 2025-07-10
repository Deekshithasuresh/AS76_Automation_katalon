import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper           // needed for findWebElements
import org.openqa.selenium.WebElement                             // needed for WebElement
import org.openqa.selenium.By                                     // needed for By
import java.util.List                                             // needed for List

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

// STEP 2a: Click the RBC tab
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.comment("✔ Clicked the RBC tab")

// STEP 2b: Click the “Colour” sub-tab
TestObject colourTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Colour']"
)
WebUI.waitForElementClickable(colourTab, 10)
WebUI.click(colourTab)
WebUI.comment("✔ Clicked the Colour sub-tab")

// STEP 2c: Click the microscopic-view icon
TestObject microViewIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)
WebUI.comment("✔ Clicked the Microscopic view icon")

// allow the pane to finish loading (adjust as needed)
WebUI.delay(5)

// STEP 3: define expected RGB values
Map<String,String> expectedColourMap = [
	'Hypochromic Cells'  : 'rgb(35, 0, 30)',
	'Polychromatic Cells' : 'rgb(124, 222, 220)'
]

// STEP 4: grab all rows in the left-pane table
TestObject allRowsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> allRows = WebUiCommonHelper.findWebElements(allRowsTO, 10)
WebUI.comment("🔎 Found ${allRows.size()} row(s)")

// STEP 5: iterate and verify
allRows.each { WebElement row ->
	String cellName = row.findElement(By.xpath(".//td[2]")).getText().trim()
	if (expectedColourMap.containsKey(cellName)) {
		String expectedRgb = expectedColourMap[cellName]
		String actualRgb   = row.findElement(By.xpath(".//td[1]//div"))
							  .getCssValue("background-color").trim()
		if (actualRgb == expectedRgb) {
			WebUI.comment("✔ [$cellName] color correct: $actualRgb")
		} else {
			WebUI.comment("❌ [$cellName] color mismatch — actual: $actualRgb, expected: $expectedRgb")
		}
	} else {
		WebUI.comment("→ Skipping ‘$cellName’")
	}
}

WebUI.comment("✅ Done verifying RBC-colour legends")
