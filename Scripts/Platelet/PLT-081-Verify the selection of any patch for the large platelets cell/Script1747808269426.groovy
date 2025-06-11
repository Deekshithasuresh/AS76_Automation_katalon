import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
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

// Get cell rows
List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Platelets/Page_PBS/morphology _row'), 
    10)

// Helper to check if a row is clickable
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

List<WebElement> all_patches = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Platelets/Page_PBS/patch_container'), 30)


if (largePlateletCount == 0 || largePlateletRow == null) {
	KeywordUtil.markFailed("ℹ️ No Large Platelets found, No patches found.")
	return
}else {
for (WebElement patch : all_patches) {
	if (patch.isDisplayed() && patch.isEnabled()) {
		patch.click()
		println("Patch is selected")
	} else {
		println("Patch is not clickable")
	}
}
}
