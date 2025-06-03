import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI 
import java.text.SimpleDateFormat


CustomKeywords.'generic.dataManagement.loginAdmin'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_Storage management'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'), 'Configure retention policy')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Edit Policy'), 'Edit Policy')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Edit Policy'))
 
CustomKeywords.'generic.myReoort.setRetentionPolicy'(950)