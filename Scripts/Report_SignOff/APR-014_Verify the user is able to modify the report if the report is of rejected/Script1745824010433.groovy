import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Reviewed'))

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Rejected')

//Step 3: Navigate through tabs
WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_RBC'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Platelets'))
