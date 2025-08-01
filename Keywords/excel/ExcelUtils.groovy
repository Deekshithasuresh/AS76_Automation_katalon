package excel

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.annotation.Keyword

class ExcelUtils {

    /**
     * Returns the value corresponding to a given key (column A → column B)
     * @param filePath: relative or absolute path to the Excel file
     * @param searchKey: key to search (in Column A)
     * @return corresponding value from Column B or null if not found
     */
    @Keyword
    static String getValueForKey(String filePath, String searchKey) {
        FileInputStream fis = new FileInputStream(filePath)
        Workbook workbook = new XSSFWorkbook(fis)
        Sheet sheet = workbook.getSheetAt(0)
        String valueFound = null

        for (Row row : sheet) {
            Cell keyCell = row.getCell(0)
            Cell valueCell = row.getCell(1)

            if (keyCell != null && valueCell != null) {
                String key = keyCell.getStringCellValue().trim()
                if (key.equalsIgnoreCase(searchKey.trim())) {
                    valueFound = valueCell.toString().trim()
                    break
                }
            }
        }

        workbook.close()
        fis.close()

        return valueFound
    }

    /**
     * Returns all key-value pairs as a Map from the Excel file (Column A → Column B)
     * @param filePath: path to Excel file
     * @return Map<String, String> of all keys and values
     */
    @Keyword
    static Map<String, String> getAllKeyValuePairs(String filePath) {
        Map<String, String> dataMap = [:]

        FileInputStream fis = new FileInputStream(filePath)
        Workbook workbook = new XSSFWorkbook(fis)
        Sheet sheet = workbook.getSheetAt(0)

        for (Row row : sheet) {
            Cell keyCell = row.getCell(0)
            Cell valueCell = row.getCell(1)

            if (keyCell != null && valueCell != null) {
                String key = keyCell.getStringCellValue().trim()
                String value = valueCell.toString().trim()
                dataMap.put(key, value)
            }
        }

        workbook.close()
        fis.close()

        return dataMap
    }
}
