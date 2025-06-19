import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

import generic.custumFunctionsvj

custumFunctionsvj custFuns = new custumFunctionsvj()

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/span_My reports'), 0)


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

    TestObject toastMsg = findTestObject('Object Repository/Platelets/Page_PBS/whole_toast_msg')

    WebUI.waitForElementVisible(toastMsg, 10)

    TestObject toast_msg_head = findTestObject('Object Repository/Platelets/Page_PBS/toast_msg_header')

    WebUI.waitForElementVisible(toast_msg_head, 10)

    String toast_msg_header = WebUI.getText(toast_msg_head).trim()

    println('Toast message header: ' + toast_msg_header)

    assert toast_msg_header.equals('1 patch reclassified')

    TestObject toast_msg_descs = findTestObject('Object Repository/Platelets/Page_PBS/toast_msg_desc')

    WebUI.waitForElementVisible(toast_msg_descs, 10)

    String toast_msg_desc = WebUI.getText(toast_msg_descs).trim()

    println('Toast message desc: ' + toast_msg_desc)

    assert toast_msg_desc.equals('Platelet Clumps to Large Platelets')

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

