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
import loginPackage.Login as Login
import zoom.ZoomInOut as ZoomInOut

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('Under review')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Inclusions'),10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Inclusions'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Howell-Jolly Bodies'))

WebUI.verifyElementPresent(findTestObject('RBC_Objects/Page_PBS/split-view_button'), 10)
WebUI.click(findTestObject('RBC_Objects/Page_PBS/split-view_button'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Howell-Jolly Bodies'), 10)
WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Howell-Jolly Bodies'),
	'No patches available for Howell-Jolly Bodies')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 10)
WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 'No preview')



WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Pappenheimer Bodies'))

WebUI.verifyElementPresent(findTestObject('RBC_Objects/Page_PBS/split-view_button'), 10)
WebUI.click(findTestObject('RBC_Objects/Page_PBS/split-view_button'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Pappenheimer Bodies'), 10)
WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Pappenheimer Bodies'),
	'No patches available for Pappenheimer Bodies')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 10)
WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 'No preview')



WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Basophilic Stippling'))

WebUI.verifyElementPresent(findTestObject('RBC_Objects/Page_PBS/split-view_button'), 10)
WebUI.click(findTestObject('RBC_Objects/Page_PBS/split-view_button'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Basophilic Stippling'), 10)
WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No patches available for Basophilic Stippling'),
	'No patches available for Basophilic Stippling')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 10)
WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (2)'), 'No preview')

