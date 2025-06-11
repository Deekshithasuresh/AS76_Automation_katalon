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
import loginPackage.Login

Login lg= new Login()
lg.login()
WebUI.delay(2)
lg.selectReportByStatus('To be reviewed')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape (1)'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Acanthocytes (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Acanthocytes'),
	10)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Acanthocytes_1'),
	'No patches available for Acanthocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 'No preview')

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Sickle Cells (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Sickle Cells'),
	0)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Sickle Cells_1'),
	'No patches available for Sickle Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 'No preview')

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Stomatocytes (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Stomatocytes'),
	0)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Stomatocytes_1'),
	'No patches available for Stomatocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 'No preview')