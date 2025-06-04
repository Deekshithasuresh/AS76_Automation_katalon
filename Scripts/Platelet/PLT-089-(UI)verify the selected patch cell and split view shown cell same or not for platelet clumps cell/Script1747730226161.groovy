import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

// Select a sample
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)


// Step 1: Get cell rows
List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Platelets/Page_PBS/morphology _row'), 10)

int clumpCount = 0
int largePlateletCount = 0
WebElement clumpRow = null
WebElement largePlateletRow = null

for (WebElement row : cell_rows) {
	String cellName = row.findElement(By.xpath('.//div[1]')).getText()
	String countText = row.findElement(By.xpath('.//div[2]')).getText()
	int count = countText.isInteger() ? countText.toInteger() : 0

	if (cellName.contains('Platelet Clumps')) {
		clumpCount = count
		clumpRow = row
	}

	if (cellName.contains('Large Platelets')) {
		largePlateletCount = count
		largePlateletRow = row
	}
}

println("Large Platelets Count: $largePlateletCount")
println("Platelet Clumps Count: $clumpCount")

// ✅ Step 2: If Platelet clumps count is zero, skip patch logic
if (clumpCount == 0 || clumpRow == null) {
	KeywordUtil.markFailed("ℹ️ No Platelet Clumps found, skipping patch verification.")
	return
}

// ✅ Step 3: Click  Platelets clumps row to load patches
WebUI.executeJavaScript("arguments[0].scrollIntoView(true);", Arrays.asList(clumpRow))
WebUI.delay(1)
clumpRow.click()
WebUI.delay(2) // wait for patches to load

// ✅ Step 4: Locate Platelet clumps patch thumbnails
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> patchThumbnails = driver.findElements(By.xpath("//div[contains(@class,'patch-img-container')]/img[1]"))
println("Total patches found for Platelet Clumps: " + patchThumbnails.size())

if (patchThumbnails.size() == 0) {
	println("⚠️ No patch thumbnails found for Platelet Clumps.")
	return
}

WebUI.click(findTestObject('Platelets/Page_PBS/split_view_img'))
int maxPatches = Math.min(patchThumbnails.size(), 2)
int successfulPatches = 0

for (int i = 0; i < maxPatches; i++) {
	println("\n--- Verifying Patch " + (i + 1) + " ---")

	WebElement patch = patchThumbnails.get(i)
	WebUI.executeJavaScript("arguments[0].scrollIntoView(true);", Arrays.asList(patch))
	WebUI.delay(1)

	// Double-click patch via JavaScript
	WebUI.executeJavaScript("""
		var evt = new MouseEvent('dblclick', {
			bubbles: true,
			cancelable: true,
			view: window
		});
		arguments[0].dispatchEvent(evt);
	""", Arrays.asList(patch))

	WebUI.delay(2)

	String patchSrc = patch.getAttribute("src")
	println("Double-clicked Patch Src: " + patchSrc)

	// ✅ Check if split view canvas or image is now displayed
	List<WebElement> canvases = driver.findElements(By.xpath("//div[contains(@class,'pbs-volumeViewport')]//canvas"))
	List<WebElement> images = driver.findElements(By.xpath("//div[contains(@class,'pbs-volumeViewport')]//img"))

	if (!canvases.isEmpty() || !images.isEmpty()) {
		println("✅ Split view is visible for Patch " + (i + 1))
		successfulPatches++
	} else {
		println("❌ No split view canvas/image for Patch " + (i + 1))
	}

	WebUI.delay(1)
}


println("\nSummary: $successfulPatches out of $maxPatches patches showed split view.")
assert successfulPatches > 0 : "No patch resulted in visible split view!"
