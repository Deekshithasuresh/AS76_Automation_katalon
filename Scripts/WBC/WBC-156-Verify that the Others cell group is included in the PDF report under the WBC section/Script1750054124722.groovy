import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_RBC (1)'))


Map<String, String> rbcGradesSize = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Shape'))


Map<String, String> rbcGradesShape = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Colour'))

Map<String, String> rbcGradesColour = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Inclusions'))

Map<String, String> rbcGradesInclusion = CustomKeywords.'generic.Wbc_helper.getRbcGradesFromUI'()


WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Platelets'))



WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/img'))

WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/span_Download PDF report'), 'Download PDF report')

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Download PDF report'))

String downloadsPath = System.getProperty('user.home') + '/Downloads'
File latestPdf = PdfReader.getLatestPdfReport(downloadsPath)
println("📄 Latest PDF path: ${latestPdf.absolutePath}")

String pdfText = PdfReader.readText(latestPdf.absolutePath)
println("📃 PDF Text Preview:\n" + pdfText.take(5000)) 

// Normalize spaces for safer matching
def normalizedPdfText = pdfText.replaceAll(/\s+/, " ").trim()


// === Step 3: Validate 'Others' ===
def othersLine = pdfText.split('\n').find { it.contains("Others") }
assert othersLine != null : "❌ 'Others' row not found in PDF!"

othersLine = othersLine.replaceAll(/\s+/, " ").trim()

if (othersLine.contains("- %")) {
	KeywordUtil.markWarning("⚠️ 'Others' present but no percentage value in PDF (shows '- %').")
} else {
	def percentMatch = othersLine =~ /Others\s+(\d+(?:\.\d+)?)%/
	assert percentMatch.find() : "❌ Percentage value not found for 'Others'!"
	WebUI.comment("✅ Found 'Others' with percentage: ${percentMatch[0][1]}%")
}


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




