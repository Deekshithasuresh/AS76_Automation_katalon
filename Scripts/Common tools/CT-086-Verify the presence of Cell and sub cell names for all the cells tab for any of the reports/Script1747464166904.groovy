import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.time.Duration as Duration
import java.util.concurrent.TimeoutException as TimeoutException
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
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

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/div_Neutrophils (1)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Neutrophils (1)'), 'Neutrophils')

WebUI.click(findTestObject('Object Repository/Commontools/li_Neutrophils'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Band Forms (1)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Band Forms (1)'), 'Band Forms')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Hypersegmented (1)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Hypersegmented (1)'), 'Hypersegmented')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Lymphocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Lymphocytes'), 'Lymphocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Reactive'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Reactive'), 'Reactive')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Eosinophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Eosinophils'), 'Eosinophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Monocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Monocytes'), 'Monocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Basophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Basophils'), 'Basophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Immature Granulocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Immature Granulocytes'), 'Immature Granulocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Promyelocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Promyelocytes'), 'Promyelocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Myelocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Myelocytes'), 'Myelocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Metamyelocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Metamyelocytes'), 'Metamyelocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Atypical CellsBlasts'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Atypical CellsBlasts'), 'Atypical Cells/Blasts')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_NRBC'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_NRBC'), 'NRBC')

WebUI.click(findTestObject('Object Repository/Commontools/li_Neutrophils'))

WebUI.click(findTestObject('Object Repository/Commontools/span_RBC'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/span_RBC'), 0)

WebUI.click(findTestObject('Object Repository/Commontools/span_References (2)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/div_Microcytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Microcytes'), 'Microcytes')

WebUI.click(findTestObject('Object Repository/Commontools/div_Microcytes'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Macrocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Macrocytes'), 'Macrocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Anisocytosis'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Anisocytosis'), 'Anisocytosis')

WebUI.click(findTestObject('Object Repository/Commontools/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558 (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/button_Shape'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/div_Ovalocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Ovalocytes'), 'Ovalocytes')

WebUI.click(findTestObject('Object Repository/Commontools/div_Ovalocytes'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Elliptocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Elliptocytes'), 'Elliptocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Teardrop Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Teardrop Cells'), 'Teardrop Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Fragmented Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Fragmented Cells'), 'Fragmented Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Target Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Target Cells'), 'Target Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Echinocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Echinocytes'), 'Echinocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Poikilocytosis'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Poikilocytosis'), 'Poikilocytosis')

WebUI.click(findTestObject('Object Repository/Commontools/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558 (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/button_Color'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'), 'Hypochromic Cells')

WebUI.click(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Polychromatic Cells'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Polychromatic Cells'), 'Polychromatic Cells')

WebUI.click(findTestObject('Object Repository/Commontools/div_Hypochromic Cells'))


WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'), 'Morphology')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 'References')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/span_References'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/div_Large Platelets'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Large Platelets'), 'Large Platelets')

WebUI.click(findTestObject('Object Repository/Commontools/div_Large Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Platelet Clumps'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Platelet Clumps'), 'Platelet Clumps')

