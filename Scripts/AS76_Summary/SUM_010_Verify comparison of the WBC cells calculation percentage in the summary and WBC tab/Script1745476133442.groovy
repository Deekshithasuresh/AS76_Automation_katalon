import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'))

// Define the main cell types to compare
def mainCellTypes = [
    'Neutrophils',
    'Lymphocytes',
    'Eosinophils',
    'Monocytes',
    'Basophils',
    'Immature Granulocytes',
    'Atypical Cells/Blasts',
    'NRBC',
    'Smudge Cells'
]

// Get WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Create a map to store percentage values from Summary tab
def summaryPercentValues = [:]

// Click on the Summary tab first
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Summary'))

// Wait for elements to load - INCREASED WAIT TIME
WebUI.delay(5)

// Collect values from Summary tab
WebUI.comment('Collecting values from Summary tab...')

try {
    // Get table from Summary tab with explicit wait
    WebUI.waitForElementPresent(findTestObject('Object Repository/Page_PBS/table_summary'), 10)
    
    // Use a more specific XPath to find the table in the Summary tab
    List<WebElement> summaryRows = driver.findElements(By.xpath("//div[contains(@class, 'summary-tab') or contains(@id, 'summary')]//table//tr"))
    
    // If no rows found, try alternative selectors
    if (summaryRows.size() == 0) {
        WebUI.comment("No rows found with first selector, trying alternatives...")
        
        // Try other possible table locators
        summaryRows = driver.findElements(By.xpath("//div[contains(@class, 'tab-content') and contains(@class, 'active')]//table//tr"))
        
        // If still no rows, try a more general approach
        if (summaryRows.size() == 0) {
            summaryRows = driver.findElements(By.xpath("//table//tr"))
        }
    }
    
    WebUI.comment("Found " + summaryRows.size() + " rows in Summary tab")
    
    // Print the entire table HTML for debugging
    WebElement tableElement = driver.findElement(By.xpath("//div[contains(@class, 'tab-content') and contains(@class, 'active')]//table"))
    if (tableElement != null) {
        WebUI.comment("Summary Table HTML structure: " + tableElement.getAttribute("outerHTML").substring(0, 500) + "...")
    }
    
    // Iterate through the rows to find our cell types with better cell selection
    for (WebElement row : summaryRows) {
        try {
            // Get cell content with improved handling
            List<WebElement> cells = row.findElements(By.tagName("td"))
            
            if (cells.size() > 0) {
                String cellText = cells.get(0).getText().trim()
                
                // Try to find the cell type by text content
                for (String cellType : mainCellTypes) {
                    if (cellText.contains(cellType)) {
                        // For percentage, check if we have a dedicated percentage column (could be column 2 or 3)
                        String percentValue = "N/A"
                        if (cells.size() >= 3) {
                            percentValue = cells.get(2).getText().trim()
                        } else if (cells.size() == 2) {
                            percentValue = cells.get(1).getText().trim()
                        }
                        
                        // If the value is still empty, try looking for spans or divs inside the cell
                        if (percentValue == null || percentValue.isEmpty() || percentValue == "N/A") {
                            WebElement percentCell = cells.size() >= 3 ? cells.get(2) : (cells.size() == 2 ? cells.get(1) : null)
                            if (percentCell != null) {
                                List<WebElement> spans = percentCell.findElements(By.tagName("span"))
                                if (spans.size() > 0) {
                                    percentValue = spans.get(0).getText().trim()
                                }
                            }
                        }
                        
                        summaryPercentValues[cellType] = percentValue
                        WebUI.comment("Summary - " + cellType + ": % = " + percentValue)
                        break
                    }
                }
            }
        } catch (Exception e) {
            WebUI.comment("Error processing a row in Summary tab: " + e.getMessage())
        }
    }
} catch (Exception e) {
    WebUI.comment("Error processing Summary tab: " + e.getMessage())
}

// If we found no values in the Summary tab, try an alternative approach
if (summaryPercentValues.isEmpty()) {
    WebUI.comment("No values found in Summary tab with first approach, trying direct XPath for each cell type...")
    
    // Try direct XPath for each cell type
    for (String cellType : mainCellTypes) {
        try {
            // Look for rows containing our cell type names
            List<WebElement> matchingRows = driver.findElements(By.xpath("//tr[contains(., '" + cellType + "')]"))
            
            if (matchingRows.size() > 0) {
                WebElement row = matchingRows.get(0)
                List<WebElement> cells = row.findElements(By.tagName("td"))
                
                // The percentage might be in the last column
                if (cells.size() >= 2) {
                    String percentValue = cells.get(cells.size() - 1).getText().trim()
                    summaryPercentValues[cellType] = percentValue
                    WebUI.comment("Summary (alternative) - " + cellType + ": % = " + percentValue)
                }
            }
        } catch (Exception e) {
            WebUI.comment("Error with alternative lookup for " + cellType + ": " + e.getMessage()) 
        }
    }
}

// Click on WBC tab
WebUI.click(findTestObject('Object Repository/Summary/span_WBC'))

// Wait for elements to load in the WBC tab
WebUI.delay(2)

// Create a map to store percentage values from WBC tab
def wbcPercentValues = [:]

// Collect values from WBC tab
WebUI.comment('Collecting values from WBC tab...')

// Get all table rows in the WBC tab
List<WebElement> wbcRows = driver.findElements(By.xpath("//table//tr"))

// Iterate through the rows to find our cell types
for (WebElement row : wbcRows) {
	try {
		// Get the first cell (cell name)
		List<WebElement> cells = row.findElements(By.tagName("td"))
		if (cells.size() > 0) {
			String cellName = cells.get(0).getText().trim()
			
			// Check if this is one of our main cell types
			if (mainCellTypes.contains(cellName)) {
				// If this is a main cell type, get the percentage value (last column)
				String percentValue = cells.size() >= 3 ? cells.get(2).getText().trim() : "N/A"
				wbcPercentValues[cellName] = percentValue
				WebUI.comment("WBC - " + cellName + ": % = " + percentValue)
			}
		}
	} catch (Exception e) {
		WebUI.comment("Error processing a row in WBC tab: " + e.getMessage())
	}
}

// Compare percentage values between Summary and WBC tabs
WebUI.comment('Comparing percentage values between Summary and WBC tabs...')
boolean allMatch = true
int matchCount = 0
int mismatchCount = 0
int notFoundCount = 0

for (cellType in mainCellTypes) {
    String summaryValue = summaryPercentValues.get(cellType)
    String wbcValue = wbcPercentValues.get(cellType)
    
    if (summaryValue == null && wbcValue == null) {
        WebUI.comment(cellType + " not found in both tabs")
        notFoundCount++
        continue
    }
    
//    if (summaryValue == null) {
//        WebUI.comment(cellType + " found only in WBC tab with value: " + wbcValue)
//        mismatchCount++
//        allMatch = false
//        continue
//    }
    
//    if (wbcValue == null) {
//        WebUI.comment(cellType + " found only in Summary tab with value: " + summaryValue)
//        mismatchCount++
//        allMatch = false
//        continue
//    }
    
    // Compare percentage values
    if (summaryValue == wbcValue) {
        WebUI.comment("MATCH: " + cellType + " percentage values are identical: " + summaryValue)
        matchCount++
    } else {
        WebUI.comment("MISMATCH: " + cellType + " percentage values differ - Summary: " + summaryValue + ", WBC: " + wbcValue)
        mismatchCount++
        allMatch = false
    }
}

// Display summary of comparison
WebUI.comment("Comparison Summary:")
WebUI.comment("Total cell types compared: " + mainCellTypes.size())
WebUI.comment("Matches: " + matchCount)
WebUI.comment("Mismatches: " + mismatchCount)
WebUI.comment("Not found in both tabs: " + notFoundCount)

if (allMatch && matchCount > 0) {
    WebUI.comment("All found cell type percentage values match between Summary and WBC tabs!")
} else if (matchCount == 0) {
    KeywordUtil.markWarning("No matching values were found between the tabs!")
} else {
    KeywordUtil.markWarning("There are mismatches in percentage values between Summary and WBC tabs")
}

// Go back to Summary tab
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Summary'))