import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By

CustomKeywords.'generic.custumFunctions.login'()

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Ready for review'), 'Ready for review')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Ready for review'))


WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Preparing'), 'Preparing')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed'), 'Reviewed')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/div__MuiBackdrop-root MuiBackdrop-invisible_7e1293'))

TestObject rowsObject = new TestObject('allRows')

rowsObject.addProperty('xpath', ConditionType.EQUALS, '//div/tbody/tr//span[contains(text(),\'Under review\')]/ancestor::tr')

List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsObject, 10)

//  Iterate each row
for (int i = 0; i < rows.size(); i++) {
    WebElement row = rows[i]

    WebElement assignedInput = row.findElement(By.xpath('.//td[6]//input[@id=\'assigned_to\']'))

    String assignedVal = assignedInput.getAttribute('value').trim()

    if (assignedVal != 'Unassigned') {
        WebElement statusSpan = row.findElement(By.xpath('.//td[5]//span'))

        String statusText = statusSpan.getText().trim()

        assert statusText == 'Under review'
    }
}

//If we reach here, every assigned report was under review
WebUI.comment('✅ All assigned reports have status ‘Under review’.')

