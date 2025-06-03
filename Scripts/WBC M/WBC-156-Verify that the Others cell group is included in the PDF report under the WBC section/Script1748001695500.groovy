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
import pdfutils.PdfReader


CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Reviewed'))

WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/span_Reviewed_1'), 'Reviewed')

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Approved')

WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/button_Summary'), 'Summary')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

Map<String, String> uiWbcData =CustomKeywords.'generic.Wbc_helper.getWbcDifferentialFromUI'()

WebUI.click(findTestObject('Object Repository/Page_PBS/button_RBC'))


Map<String, String> rbcGradesSize = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Shape'))


Map<String, String> rbcGradesShape = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Colour'))

Map<String, String> rbcGradesColour = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Inclusions'))

Map<String, String> rbcGradesInclusion = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()


WebUI.click(findTestObject('Object Repository/Page_PBS/button_Platelets'))



WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/img'))

WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/span_Download PDF report'), 'Download PDF report')

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Download PDF report'))

// Step 1: Get Downloads folder path
String downloadsPath = System.getProperty('user.home') + '/Downloads'

// Step 2: Find the latest 'pdfReport*.pdf'
File latestPdf = PdfReader.getLatestPdfReport(downloadsPath)

println("Latest pdfReport file: ${latestPdf.absolutePath}")

// Step 3: Read text from PDF
String pdfText = PdfReader.readText(latestPdf.absolutePath)
println("PDF Content:\n$pdfText")

uiWbcData.each { key, value ->
	String expectedEntry = "${key} ${value}".replaceAll("\\s+", " ").trim()
	WebUI.comment("🔍 Verifying PDF contains: ${expectedEntry}")
	assert pdfText.contains(expectedEntry) : "❌ PDF missing '${expectedEntry}'"
}

rbcGradesSize.each { cellType, grade ->
	String expectedEntry = "${cellType} ${grade}".trim()
	WebUI.comment("🔍 Verifying PDF contains: ${expectedEntry}")
	assert pdfText.contains(expectedEntry) : "❌ PDF missing '${expectedEntry}'"
}

rbcGradesShape.each { cellType, grade ->
	String expectedEntry = "${cellType} ${grade}".trim()
	WebUI.comment("🔍 Verifying PDF contains: ${expectedEntry}")
	assert pdfText.contains(expectedEntry) : "❌ PDF missing '${expectedEntry}'"
}
rbcGradesColour.each { cellType, grade ->
	String expectedEntry = "${cellType} ${grade}".trim()
	WebUI.comment("🔍 Verifying PDF contains: ${expectedEntry}")
	assert pdfText.contains(expectedEntry) : "❌ PDF missing '${expectedEntry}'"
}
rbcGradesInclusion.each { cellType, grade ->
	String expectedEntry = "${cellType} ${grade}".trim()
	WebUI.comment("🔍 Verifying PDF contains: ${expectedEntry}")
	assert pdfText.contains(expectedEntry) : "❌ PDF missing '${expectedEntry}'"
}


// === Step 4: Validate 'Others*' is present ===
//assert pdfText.contains("Others*") : "❌ 'Others*' not found in PDF report!"

// === Step 5: Validate percentage is shown next to 'Others*' ===
//def othersLine = pdfText.split('\n').find { it.contains("Others*") }
//assert othersLine != null : "❌ No line with 'Others*' found in PDF!"
//
//def percentMatch = othersLine =~ /Others\*\s+(\d{1,2}\.\d+)%/
//assert percentMatch.find() : "❌ Percentage value not found for 'Others*'!"

//println("✅ Found 'Others*' with percentage: ${percentMatch[0][1]}%")

