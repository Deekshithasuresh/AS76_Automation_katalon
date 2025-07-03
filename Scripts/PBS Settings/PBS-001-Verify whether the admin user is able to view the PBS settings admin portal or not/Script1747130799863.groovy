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

			WebUI.openBrowser('')
	
			WebUI.maximizeWindow()
	
			WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')
	
			WebUI.waitForElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/Sign_in_header'), 20)
	
			WebUI.setText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Username_loginId'), "prem")
	
			WebUI.setText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Password_loginPassword'),
					"Sigtuple@123")
	
			WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Sign in'))
	
			try{
				WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Auth_error_msg'), 20)
				String auth_error_text=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Auth_error_msg'))
				WebUI.comment(auth_error_text)
				assert auth_error_text=='Invalid credentials'
				
			}
	
			catch(Exception e) {
				WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Admin_Portal_Heading'), 20)
				WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Admin_Portal_Heading'), 20)
			}
		
	