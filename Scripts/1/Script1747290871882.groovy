import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/Commontools/input_username_loginId (2)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/input_password_loginPassword (2)'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/button_Sign In (2)'))

WebUI.click(findTestObject('Object Repository/Commontools/div_29-May-2025, 1154 AM (IST)'))

WebUI.click(findTestObject('Object Repository/Commontools/button_WBC (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/span_References (1)'))
WebUI.click(findTestObject('Object Repository/Commontools/span_References (1)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Neutrophils (1)'), 'Neutrophils')
WebUI.click(findTestObject('Object Repository/Commontools/li_Neutrophils'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Band Forms (1)'), 'Band Forms')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Hypersegmented (1)'), 'Hypersegmented')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Lymphocytes'), 'Lymphocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Reactive'), 'Reactive')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Eosinophils'), 'Eosinophils')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Monocytes'), 'Monocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Basophils'), 'Basophils')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Immature Granulocytes'), 'Immature Granulocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Promyelocytes'), 'Promyelocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Myelocytes'), 'Myelocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Metamyelocytes'), 'Metamyelocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Atypical CellsBlasts'), 'Atypical Cells/Blasts')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_NRBC'), 'NRBC')

WebUI.click(findTestObject('Object Repository/Commontools/li_Neutrophils'))

WebUI.click(findTestObject('Object Repository/Commontools/span_RBC'))
WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/span_RBC'), 0)
WebUI.click(findTestObject('Object Repository/Commontools/span_References (2)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Microcytes'), 'Microcytes')
WebUI.click(findTestObject('Object Repository/Commontools/div_Microcytes'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Macrocytes'), 'Macrocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Anisocytosis'), 'Anisocytosis')

WebUI.click(findTestObject('Object Repository/Commontools/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558 (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/button_Shape'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Ovalocytes'), 'Ovalocytes')
WebUI.click(findTestObject('Object Repository/Commontools/div_Ovalocytes'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Elliptocytes'), 'Elliptocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Teardrop Cells'), 'Teardrop Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Fragmented Cells'), 'Fragmented Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Target Cells'), 'Target Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Echinocytes'), 'Echinocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Poikilocytosis'), 'Poikilocytosis')

WebUI.click(findTestObject('Object Repository/Commontools/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558 (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/button_Color'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'), 'Hypochromic Cells')

// 🛠 Fix: Wait for modal backdrop to disappear
WebUI.waitForElementNotPresent(findTestObject('Object Repository/Commontools/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558 (1)'), 10)
WebUI.scrollToElement(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'), 5)
WebUI.click(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Polychromatic Cells'), 'Polychromatic Cells')
WebUI.click(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets'), 'Platelets')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'), 'Morphology')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 'References')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/span_References'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Large Platelets'), 'Large Platelets')
WebUI.click(findTestObject('Object Repository/Commontools/div_Large Platelets'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Platelet Clumps'), 'Platelet Clumps')


def enterAlphaNumSpecialInField(TestObject fieldObject, String alphaNumSpecialToEnter) {
    WebUI.waitForElementVisible(fieldObject, 20)

    WebUI.click(fieldObject)

    WebUI.clearText(fieldObject)

    WebUI.setText(fieldObject, alphaNumSpecialToEnter)

    WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB))
}

