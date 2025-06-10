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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import applyFilterAndVerify.ApplyFilterAndVerifyTheList as ApplyFilterAndVerifyTheList

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Password_loginPassword'), 
    'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Users'))

WebUI.verifyElementVisible(findTestObject('Object Repository/View list of users/Page_Admin Console/span_Platelet level limits_material-icons n_ecbb8f'))

WebUI.verifyElementText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Platelet level limits_MuiInputBase-in_8eb492'), 
    '')

WebUI.verifyElementVisible(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Platelet level limits_MuiButtonBase-_5dde88'))


ApplyFilterAndVerifyTheList applyFilter = new ApplyFilterAndVerifyTheList()

applyFilter.applyRoleAndStatusFiltersM(['operator', 'administrator', 'reviewer'], ['Active', 'Inactive'])
applyFilter.verifyFilteredUsersByRoleAndStatusM(['operator', 'administrator', 'reviewer'], ['Active', 'Inactive'])


//applyFilter.applyRoleAndStatusFilters('operator', 'Active')
//
//applyFilter.verifyFilteredUsersByRoleAndStatus('operator', 'Active')
//
//
//applyFilter.applyRoleAndStatusFilters('administrator', 'Active')
//
//applyFilter.verifyFilteredUsersByRoleAndStatus('administrator', 'Active')

WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Clear filters'))

WebUI.delay(1)

