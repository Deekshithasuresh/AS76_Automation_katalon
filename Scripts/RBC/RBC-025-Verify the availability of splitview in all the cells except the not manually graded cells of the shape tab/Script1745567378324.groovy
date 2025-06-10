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
import zoom.ZoomInOut
Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')


//lg.assignOrReassignOnTabs('prem')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape (1)'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Ovalocytes (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view (2)'), 0)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1 (2)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas (1)'), 0)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Elliptocytes (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas (1)'), 0)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Teardrop Cells (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas (1)'), 0)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Fragmented Cells (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas (1)'), 0)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Target Cells (1)'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Echinocytes (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas (1)'), 0)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Poikilocytosis (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Please review individual cell types for_bd04fe (2)'), 
    0)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Please review individual cell types for_bd04fe_1 (1)'), 
    'Please review individual cell types for viewing patches')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 'No preview')