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

//select 1st row report
//CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/td_3edtfygu'))

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

//verfiying the presence of Cell name header under details like Normal platelets,macro,giant total platelets.
WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/th_Cell name'), 'Cell name')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/td_Normal Platelets (N)'), 'Normal Platelets (N)')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/td_Macro Platelets (M)'), 'Macro Platelets (M)')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/td_Giant Platelets (G)'), 'Giant Platelets (G)')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/td_Total Platelet'), 'Total Platelet')



