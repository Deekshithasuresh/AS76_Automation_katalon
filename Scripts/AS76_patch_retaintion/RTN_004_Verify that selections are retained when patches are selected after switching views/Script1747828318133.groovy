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

WebUI.setText(findTestObject('Object Repository/retain_patchs/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/retain_patchs/td_SIG013'))

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/span_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/retain_patchs/span_WBC'))

WebUI.click(findTestObject('Object Repository/retain_patchs/5_patches_selected'))

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view_1'))

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view_1'))

WebUI.click(findTestObject('Object Repository/retain_patchs/div_Image settings_default-patch  multisele_f5bdd9'))

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view_1'))

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view_1'))

