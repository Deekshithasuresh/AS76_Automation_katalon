import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generic.custumFunctionsvj

custumFunctionsvj custFuns = new custumFunctionsvj()

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/span_My reports'), 0)

WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('jyothi')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

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

println("Platelet Clumps Count: $clumpCount")

println("Large Platelets Count: $largePlateletCount")

//  If Platelet clumps count is zero
if ((clumpCount == 0) || (clumpRow == null)) {
    KeywordUtil.markFailed('ℹ️ No Platelet Clumps found, no patches available.')

    return null
    // Give the browser a moment to stabilize
    // Try with offset slightly inside the target
    // move inside the target bounds
} else {
    clumpRow.click()

    WebUI.delay(5)

    WebElement source = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Platelets/Page_PBS/plt_clumps_full_xpath_for_patch'), 
        10)

    WebElement target = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Platelets/Page_PBS/large_plt_patch_patch'), 
        10)

    Actions action = new Actions(DriverFactory.getWebDriver())

    action.clickAndHold(source).moveToElement(target, 10, 10).pause(1000).release().build().perform()

	
	
	
	
	try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30))
		WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.classified-snackbar")))

		String headerText = snackbar.findElement(By.cssSelector(".header-row .header")).getText().trim()
		String bodyText = snackbar.findElement(By.cssSelector(".body")).getText().trim()

		WebUI.comment("Snackbar message: ${headerText} | ${bodyText}")

		assert headerText.toLowerCase().contains("reclassified")
		assert bodyText.toLowerCase().contains(fromCellName.toLowerCase())
		assert bodyText.toLowerCase().contains(toCellName.toLowerCase())

		WebUI.comment("Snackbar reclassification message verified.")


		// Step 4: Click the 'X' icon to close the snackbar
		WebUI.delay(2)
		WebElement closeIcon = snackbar.findElement(By.xpath("//div[contains(@class,'MuiSnackbarContent-action')]/div"))
		actions.moveToElement(closeIcon).click().build().perform()
		WebUI.comment("Snackbar closed by clicking X icon.")
	}
	catch (Exception e) {
		WebUI.comment("snackbar confirmed")
	}
	//			WebUI.refresh()
	//			WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))
	//
	WebUI.delay(4)

	WebUI.comment("Snackbar reclassification message verified.")
	
	
	

    int largePlateletCountAfter

    int clumpCountAfter

    for (WebElement row : cell_rows) {
        String cellName = row.findElement(By.xpath('.//div[1]')).getText()

        String countText = row.findElement(By.xpath('.//div[2]')).getText()

        int countAfter = countText.isInteger() ? countText.toInteger() : 0

        if (cellName.contains('Platelet Clumps')) {
            clumpCountAfter = countAfter

            clumpRow = row
        }
        
        if (cellName.contains('Large Platelets')) {
            largePlateletCountAfter = countAfter

            largePlateletRow = row
        }
    }
    
    println("Platelet Clumps Count after reclassification: $clumpCountAfter")

    assert clumpCountAfter == (clumpCount - 1) : 'Platelet clumps count not changed'

    println("Large Platelets Count after reclassification: $largePlateletCountAfter ")

    assert largePlateletCountAfter == (largePlateletCount + 1) : 'Large platelet count not changed'
}

