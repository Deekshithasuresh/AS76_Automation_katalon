import org.openqa.selenium.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class PatchHelper {
	WebDriver driver
	WebDriverWait wait

	PatchHelper(WebDriver driver) {
		this.driver = driver
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10))
	}

	/**
	 * Returns all patch <img> elements.
	 */
	List<WebElement> getAllPatches() {
		return driver.findElements(By.xpath("//img[contains(@class,'qa_patch_rank-')]"))
	}

	/**
	 * Returns the rank number of the currently selected patch.
	 * Handles cases where 'selected-patch' may be on parent div or sibling.
	 */
	int getSelectedIndex() {
		try {
			// Find any img that is currently inside or next to selected container
			WebElement selected = driver.findElement(By.xpath("//div[contains(@class,'selected-patch')]/preceding-sibling::img[contains(@class,'qa_patch_rank-')]"))
			return extractRank(selected.getAttribute("class"))
		} catch (NoSuchElementException e) {
			return -1
		}
	}

	/**
	 * Safely select a patch by nth index (0-based).
	 * Uses scroll + JS click to avoid interception.
	 */
	void selectPatch(int index) {
		List<WebElement> patches = getAllPatches()
		if (index < 0 || index >= patches.size()) {
			throw new IllegalArgumentException("Patch index out of range: " + index)
		}
		WebElement patch = patches[index]
		
		wait.until(ExpectedConditions.visibilityOf(patch))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", patch)
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", patch)
	}

	private int extractRank(String classes) {
		def matcher = (classes =~ /qa_patch_rank-(\d+)/)
		return matcher ? matcher[0][1].toInteger() : -1
	}
}


import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

// ---------- Login & Setup ----------
CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Open WBC tab
WebUI.waitForElementPresent(findTestObject('retain_patchs/span_WBC'), 20)
WebUI.click(findTestObject('retain_patchs/span_WBC'))

// Init driver + helpers
WebDriver driver = DriverFactory.getWebDriver()
PatchHelper patchHelper = new PatchHelper(driver)

// Start in Split View
WebUI.click(findTestObject('retain_patchs/img_Platelets_split-view'))
WebUI.delay(5)

// Select nth patch in Split View (e.g., 3rd patch → index = 2)
int nthIndex = 4
patchHelper.selectPatch(nthIndex)
WebUI.delay(1)

// Verify selection in Split View
int beforeIndex = patchHelper.getSelectedIndex()
WebUI.comment("Split View - selected patch index: " + beforeIndex)
assert beforeIndex == nthIndex+1 : "❌ Expected patch index " + nthIndex + " selected in Split View, but got " + beforeIndex

// Switch to Patch View
WebUI.click(findTestObject('retain_patchs/img_Platelets_patch-view'))
WebUI.delay(3)

// Verify the SAME patch is still selected
int afterIndex = patchHelper.getSelectedIndex()
WebUI.comment("Patch View - selected patch index: " + afterIndex)
assert afterIndex == nthIndex+1 : "❌ Patch selection not retained. Expected " + nthIndex + " but got " + afterIndex

WebUI.comment("✅ TEST PASSED: Selected nth patch retained when switching Split View → Patch View")

