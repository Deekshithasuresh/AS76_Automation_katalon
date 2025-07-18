import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebDriver

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under Review')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Commontools/button_WBC (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Commontools/span_References (1)'))


WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Neutrophils'), 'Neutrophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Neutrophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Lymphocytes'), 'Lymphocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Lymphocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Eosinophils'), 'Eosinophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Eosinophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Basophils'), 'Basophils')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Basophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Immature Granulocytes'), 'Immature Granulocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Immature Granulocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Atypical CellsBlasts'), 'Atypical Cells/Blasts')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Atypical CellsBlasts'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_NRBC'), 'NRBC')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_NRBC'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Smudge Cells'), 'Smudge Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Smudge Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Degenerate Cells'), 'Degenerate Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Degenerate Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Degenerate Cells'), 'Degenerate Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Degenerate Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Stain Artefacts'), 'Stain Artefacts')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Stain Artefacts'), 0)


//
//TestObject X_img = findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/img')
//WebElement element = WebUI.findWebElement(X_img, 10) // 10 = timeout in seconds
//WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(element))

WebUI.refresh()



WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/span_References'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Microcytes'), 'Microcytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Microcytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Macrocytes'), 'Macrocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Macrocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Anisocytosis'), 'Anisocytosis')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Anisocytosis'), 0)


WebUI.refresh()
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/button_Shape'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/span_References'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Ovalocytes'), 'Ovalocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Ovalocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Elliptocytes'), 'Elliptocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Elliptocytes'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Teardrop Cells'), 'Teardrop Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Teardrop Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Fragmented Cells'), 'Fragmented Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Fragmented Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Target Cells'), 'Target Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Target Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Echinocytes'), 'Echinocytes')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Echinocytes'), 0)



WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Poikilocytosis'), 'Poikilocytosis')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Poikilocytosis'), 0)



WebUI.refresh()
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/button_Colour'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/span_References'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/div_Disclaimer_MuiBackdrop-root MuiBackdrop_c2e558'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Hypochromic Cells'), 'Hypochromic Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Hypochromic Cells'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Polychromatic Cells'), 'Polychromatic Cells')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/Page_PBS/li_Polychromatic Cells'), 0)



