import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import imageUtils.blurChecker






WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

//WebUI.delay(10)
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


//  If Platelet clumps count is zero
if (clumpCount == 0 || clumpRow == null) {
	KeywordUtil.markFailed("ℹ️ No Platelet Clumps found, no patches available.")
	return
}else {
	clumpRow.click()
	WebUI.delay(2) // wait for patches to load
	
	WebUI.click(findTestObject('Platelets/Page_PBS/split_view_img'))
	
	WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_zoom-out'))
	WebUI.delay(5)
	
	blurChecker checker = new blurChecker()
	// Option 1: Default canvas selector
	boolean isBlurry = checker.isCanvasImageBlurry()
	// Option 2: Custom canvas by ID
	//boolean isBlurry = checker.isCanvasImageBlurry("document.getElementByXPath('//div[@id='pbs-volumeViewport']/div/div/div/canvas')", 120.0)
	// Assert the image is not blurry
	assert !(isBlurry)
}

