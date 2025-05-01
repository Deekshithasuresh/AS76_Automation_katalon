import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as Helper
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

CustomKeywords.'generic.custumFunctions.login'()

// 2) Build a dynamic TestObject targeting the FIRST row matching that status//tr[.//td[contains(normalize-space(), 'To be reviewed')]]//input[@value=""]/ancestor::tr
String xpath = "(" +"//tr[.//td[contains(normalize-space(), 'To be reviewed')]]//input[@value='']/ancestor::tr" +")[1]"
TestObject firstMatchingRow = new TestObject('dynamicReportRow')
		.addProperty('xpath', ConditionType.EQUALS, xpath)

// 3) Scroll to ensure it is in view
WebUI.scrollToElement(firstMatchingRow, 10)

// 4) Click it
WebUI.click(firstMatchingRow)

WebUI.comment("Selected first report with status = '${status}'")

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Summary'))

WebUI.waitForPageLoad(10)

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju',true)

