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
import loginPackage.Login as Login
import zoom.ZoomInOut as ZoomInOut

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)
lg.selectReportByStatus('Under review')

lg.assignOrReassignOnTabs('pawankumar')// pass a reviwer name to different user not a user with which we have logged in

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()==true

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()== true

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()== true

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Inclusions'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()== true
