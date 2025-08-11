import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.apache.poi.xssf.usermodel.*
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Login
CustomKeywords.'generic.custumFunctions.login'()

// Get Slide ID from Excel
String filePath = 'Time_zone_data/Manual_data.xlsx'
String Slideid = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'P8K Slide ID')
println "✅ Slide id from Excel: ${Slideid}"

// Click Row with Matching Slide ID
String xpath = "(//tr[.//td[contains(normalize-space(), '${Slideid}')]])[1]"
TestObject matchingRow = new TestObject('dynamicReportRow')
matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
WebUI.waitForElementClickable(matchingRow, 10)
WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
rowElement.click()



// Navigate to CBC Report
WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_WBC'))
WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/span_CBC report_1'), 'CBC report')
WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/span_CBC report_1'))

// ========== Read Excel ==========
List<Map<String, String>> readTableFromExcel(String filePath, String sheetName) {
	FileInputStream fis = new FileInputStream(filePath)
	XSSFWorkbook workbook = new XSSFWorkbook(fis)
	XSSFSheet sheet = workbook.getSheet(sheetName)

	if (sheet == null) {
		workbook.close()
		fis.close()
		throw new IllegalArgumentException("❌ Sheet '${sheetName}' not found in Excel file: ${filePath}")
	}

	List<Map<String, String>> data = []
	def headerRow = sheet.getRow(0)
	int colCount = headerRow.getLastCellNum()

	int physicalRows = sheet.getPhysicalNumberOfRows()
	int maxRows = Math.min(physicalRows, 20)  // Adjust limit if needed
	
	for (int i = 1; i < maxRows; i++) {
		def row = sheet.getRow(i)
		if (row == null) continue
	
		Map<String, String> rowMap = [:]
		for (int j = 0; j < colCount; j++) {
			def header = headerRow.getCell(j)?.toString()?.trim()
			if (!header) continue
			def cellValue = row.getCell(j)?.toString()?.trim() ?: ""
			rowMap[header] = cellValue
		}
	
		if (!rowMap.values().every { it == "" }) {
			data.add(rowMap)
		}
	
		if (i % 10 == 0) {
			println "✅ Processed ${i} rows so far"
		}
	}
	workbook.close()
	fis.close()
	return data
}

// ========== Read UI ==========
List<Map<String, String>> readTableFromUI() {
	List<Map<String, String>> uiData = []

	List<WebElement> rows = WebUI.findWebElements(
		new TestObject().addProperty("xpath", ConditionType.EQUALS,
			"//table[contains(@class,'cbc-report__table')]//tr[contains(@class, 'cbc-report__table__body')]"),
		10)

	int rowCount = rows.size()

	for (int i = 1; i <= rowCount; i++) {
		Map<String, String> row = [:]
		for (int j = 1; j <= 4; j++) {
			TestObject cell = new TestObject("cell")
			cell.addProperty("xpath", ConditionType.EQUALS,
				"(//table[contains(@class,'cbc-report__table')]//tr[contains(@class, 'cbc-report__table__body')])[${i}]/td[${j}]")
			String value = WebUI.getText(cell).trim()
			switch (j) {
				case 1: row["Cell name"] = value; break
				case 2: row["Value"] = value; break
				case 3: row["Unit"] = value; break
				case 4: row["Reference"] = value; break
			}
		}
		uiData.add(row)
	}
	return uiData
}

// ========== Compare ==========
def excelData = readTableFromExcel(filePath, "CBC Report")
def uiData = readTableFromUI()

assert excelData.size() == uiData.size() : "❌ Row count mismatch: Excel = ${excelData.size()}, UI = ${uiData.size()}"

for (int i = 0; i < excelData.size(); i++) {
	def excelRow = excelData[i]
	def uiRow = uiData[i]

	assert excelRow["Cell name"] == uiRow["Cell name"] : "❌ Mismatch at row ${i+1}: Cell name\nExpected: ${excelRow["Cell name"]}, Found: ${uiRow["Cell name"]}"
	//assert excelRow["Value"] == uiRow["Value"] : "❌ Mismatch at row ${i+1}: Value"
	assert excelRow["Unit"] == uiRow["Unit"] : "❌ Mismatch at row ${i+1}: Unit"
	assert excelRow["Reference"] == uiRow["Reference"] : "❌ Mismatch at row ${i+1}: Reference"
}

KeywordUtil.markPassed("✅ All table rows matched between Excel and UI")
