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

CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/button_Summary'), 'Summary')

// Step 2: Assign or Reassign
WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/button_WBC'), 'WBC')

// Step 3: Click WBC tab
WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_WBC'))

WebUI.doubleClick(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_Image settings_default-patch  patch-foc_a6a738'))


WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/button_'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/button__1'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/span_'), 0)

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button__1_2'))

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/canvas__ol-layer'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/canvas'), 0)

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_'))

//WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button__1_2_3'))

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_'))

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_'))

