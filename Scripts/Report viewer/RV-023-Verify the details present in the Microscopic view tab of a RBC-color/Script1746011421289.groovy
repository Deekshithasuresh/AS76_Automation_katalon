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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/span_PBS'), 'PBS')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/td_SIG013'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/button_Summary'), 'Summary')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_RBC'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/button_Color'), 'Color')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Color'))

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/img_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2'), 0)

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_1'))

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/div_Cell name_1'))

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/div_Cell name_1'))

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/th_Count'))

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/th_'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_Cell name_1'), 'Cell name')

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/th_Count'), 'Count')

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/th_'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_Hypochromic Cells_1'), 'Hypochromic Cells')

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_Polychromatic Cells_1'), 'Polychromatic Cells')

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2_3'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2_3_4'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2_3_4_5'), 0)

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2_3_4_5_6'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2_3_4_5_6_7'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/canvas'), 0)

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2_3_4_5_6_7_8'))

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/button_'))

WebUI.rightClick(findTestObject('Object Repository/Report viewer/Page_PBS/button__1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/img_1_2_3_4_5_6_7_8'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/button_'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/button__1'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report viewer/Page_PBS/button__1_2'), 0)

