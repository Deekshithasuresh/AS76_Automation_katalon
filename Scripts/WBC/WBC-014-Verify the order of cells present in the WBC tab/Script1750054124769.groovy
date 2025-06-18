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

WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_13-May-2025, 1127 AM (IST)'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_WBC'))

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_WBC'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Neutrophils'), 'Neutrophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Neutrophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Band Forms'), 'Band Forms')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Band Forms'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Hypersegmented'), 'Hypersegmented*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Hypersegmented'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Neutrophils with Toxic Granules'), 'Neutrophils with Toxic Granules*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Neutrophils with Toxic Granules'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Lymphocytes'), 'Lymphocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Lymphocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Reactive'), 'Reactive')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Reactive'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Eosinophils'), 'Eosinophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Eosinophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Monocytes'), 'Monocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Monocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Basophils'), 'Basophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Basophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Granulocytes'), 'Immature Granulocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Granulocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Promyelocytes'), 'Promyelocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Promyelocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Myelocytes'), 'Myelocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Myelocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Metamyelocytes'), 'Metamyelocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Metamyelocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Atypical CellsBlasts'), 'Atypical Cells/Blasts')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Atypical CellsBlasts'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Atypical Cells'), 'Atypical Cells*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Atypical Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Lymphoid Blasts'), 'Lymphoid Blasts*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Lymphoid Blasts'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Myeloid Blasts'), 'Myeloid Blasts*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Myeloid Blasts'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Eosinophils'), 'Immature Eosinophils*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Eosinophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Basophils'), 'Immature Basophils*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Basophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Promonocytes'), 'Promonocytes*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Promonocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Prolymphocytes'), 'Prolymphocytes*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Prolymphocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Hairy Cells'), 'Hairy Cells*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Hairy Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Sezary Cells'), 'Sezary Cells*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Sezary Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Plasma Cells'), 'Plasma Cells*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Plasma Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Others'), 'Others*')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Others'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_NRBC'), 'NRBC')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_NRBC'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Smudge Cells'), 'Smudge Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Smudge Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Degenerate Cells'), 'Degenerate Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Degenerate Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Stain Artefacts'), 'Stain Artefacts')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Stain Artefacts'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Unclassified'), 'Unclassified')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Unclassified'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Rejected'), 'Rejected')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Rejected'), 0)

