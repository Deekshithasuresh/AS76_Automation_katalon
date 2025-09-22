import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.*

/**
 * ---------- Helper ----------
 * Works with patches identified by `qa_patch_rank-n` class
 */
class PatchHelper {
    WebDriver driver
    PatchHelper(WebDriver driver) { this.driver = driver }

    // Get patch WebElement by index (1-based rank from class name)
    WebElement getPatchByIndex(int nthIndex) {
        return driver.findElement(By.xpath("//img[@class='qa_patch_rank-${nthIndex}']/parent::div"))
    }

    // Select patch by index
    void selectPatch(int nthIndex) {
        WebElement patch = getPatchByIndex(nthIndex)
        if (patch == null) {
            throw new IllegalArgumentException("❌ Patch with index ${nthIndex} not found")
        }
        patch.click()
    }

    // Get selected patch index (by looking for "active" or "selected" in class)
    int getSelectedIndex() {
        try {
            WebElement selected = driver.findElement(
                By.xpath("//div[contains(@class,'selected-patch')]/preceding-sibling::img[contains(@class,'qa_patch_rank-')]")
            )
            String classes = selected.getAttribute("class")
            // Extract number from qa_patch_rank-n
            def matcher = (classes =~ /qa_patch_rank-(\d+)/)
            return matcher ? matcher[0][1].toInteger(): -1
        } catch (NoSuchElementException e) {
            return -1
        }
    }
}

/**
 * ---------- Test Flow ----------
 */
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Navigate to WBC tab
WebUI.waitForElementPresent(findTestObject('retain_patchs/span_WBC'), 20)
WebUI.click(findTestObject('retain_patchs/span_WBC'))

// Init driver + helper
WebDriver driver = DriverFactory.getWebDriver()
PatchHelper patchHelper = new PatchHelper(driver)

// Switch to Patch View
//WebUI.click(findTestObject('retain_patchs/img_Platelets_patch-view'))
WebUI.delay(2)

// Select the nth patch (example: 2nd patch)
int nthIndex = 5
patchHelper.selectPatch(nthIndex)
WebUI.delay(1)

// Verify before switching
int beforeIndex = patchHelper.getSelectedIndex()
WebUI.comment("Patch View - selected patch index: " + beforeIndex)
assert beforeIndex == nthIndex : "❌ Expected patch index ${nthIndex} selected in Patch View, but got ${beforeIndex}"

// Switch to Split View
WebUI.click(findTestObject('retain_patchs/img_Platelets_split-view'))
WebUI.delay(3)

// Verify after switching
int afterIndex = patchHelper.getSelectedIndex()
WebUI.comment("Split View - selected patch index: " + afterIndex)
assert afterIndex == nthIndex : "❌ Patch selection not retained. Expected ${nthIndex} but got ${afterIndex}"

WebUI.comment("✅ TEST PASSED: Selected nth patch (${nthIndex}) retained when switching Patch View → Split View")
