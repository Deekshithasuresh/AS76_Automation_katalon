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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Users'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'), 'Create User')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

WebUI.verifyElementText(findTestObject('IAM Model/Page_Admin Console/span_Contact number (optional)'), 'Contact number (optional)')

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        1))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        2))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.verifyElementAttributeValue(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), 'value', '', 10)

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        3))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        4))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        5))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        6))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        7))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        8))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        9))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        10))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        11))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        12))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        13))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        14))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        15))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        16))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        17))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        18))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), findTestData('Contact_1').getValue(1, 
        19))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('IAM Model/Page_Admin Console/Contact_number_field'), Keys.chord(Keys.BACK_SPACE))

