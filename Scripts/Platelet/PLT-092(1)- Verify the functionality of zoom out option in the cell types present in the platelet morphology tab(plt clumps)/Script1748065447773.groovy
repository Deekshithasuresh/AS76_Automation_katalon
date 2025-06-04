import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.maximizeWindow()

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
List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Platelets/Page_PBS/morphology _row'),
	10)

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

	WebUI.delay(2)

	
	// === Function to get base64 image from canvas ===
	def getCanvasImageBase64 = {
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
		String base64 = js.executeScript("""
       const canvas = document.querySelector('#pbs-volumeViewport canvas');
       return canvas.toDataURL('image/png');
   """)
		return base64
	}
	

	// === Step 1: Capture canvas before zoom out ===
	WebUI.comment("Capturing canvas before zoom out...")
	String beforePanBase64 = getCanvasImageBase64()

	
	TestObject scaleBar = findTestObject('Object Repository/Platelets/Page_PBS/scale_bar')
	scaleBarValue = WebUI.getText(scaleBar)
	WebUI.comment("Scale bar value: " + scaleBarValue)
	
	
	
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_zoom-out'))
	
	
	WebUI.delay(5) // Let canvas settle
	
	// === Step 3: Capture canvas after zoom out ===
	WebUI.comment("Capturing canvas after zoom out...")
	String afterPanBase64 = getCanvasImageBase64()

	
	scaleBarValue1 = WebUI.getText(scaleBar)
	WebUI.comment("Scale bar value: " + scaleBarValue1)
	
	// === Step 4: Compare base64 data ===
	if (beforePanBase64 == afterPanBase64) {
		WebUI.comment("❌ Canvas did NOT change ")
	} else {
		WebUI.comment("✅ Canvas has changed ")
	}
	
	if (scaleBarValue == scaleBarValue1) {
		WebUI.comment("❌ Scale bar value did not change ")
	} else {
		WebUI.comment("✅ Scale bar value has changed from"+scaleBarValue+" to "+scaleBarValue1)
	}
	
	
}

