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
import internal.GlobalVariable

import java.time.Duration as Duration
import java.util.concurrent.TimeoutException

import org.apache.logging.log4j.core.appender.rolling.action.IfAccumulatedFileCount
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("To be reviewed")


CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_WBC'))

WebDriver driver = DriverFactory.getWebDriver()
String initialCount =driver.findElement(By.xpath("//td[text()='Total']/following-sibling::td[1]")).getText();

CustomKeywords.'generic.Reclassification.classifyFromCellToCell'("Neutrophils", "NRBC")

// 6. Click back arrow or logo to trigger save
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Commontools/span_Switch to original report (2)'))

WebUI.click(findTestObject('Object Repository/Commontools/img (5)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/span_Switch to modified report (1)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/span_Switch to modified report (1)'), 'Switch to modified report')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/span_Switch to modified report (1)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/span_Switch to modified report (1)'), 'Switch to modified report')

WebUI.click(findTestObject('Object Repository/Commontools/span_Switch to modified report (1)'))


String finalCount =driver.findElement(By.xpath("//td[text()='Total']/following-sibling::td[1]")).getText();

if (initialCount.equals(finalCount)) {
	WebUI.comment("❌ Total count is unchanged from original to modified — this is a failure case.")
	WebUI.verifyMatch(initialCount.toString(), finalCount.toString(), false, FailureHandling.STOP_ON_FAILURE)
} else {
	WebUI.comment("✅ Total count is different from original to modified — expected behavior.")
}


