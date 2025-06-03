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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.support.Color as Color
import java.awt.Color as Color

// Additional necessary imports that were missing
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import com.kms.katalon.core.testobject.ConditionType

/**
 * Test Case: Verify the presence of the cell names and grades in the summary tab if the RBC size grade values are significant
 * Expected: Only significant size cell names and grade values (marked in red) shall be present in the summary tab
 * Non-significant values (marked in green) should not be present in the summary tab
 */


// Login to the application
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
// Select the sample
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0004'))
// Verify and click on RBC
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC'), 'RBC')
WebUI.click(findTestObject('Object Repository/Summary/span_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Size'), 'Size')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_'), '%')
// Get the WebDriver
WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))
// Define the specific hex color codes to check against
String redHexColor = '#c61b1c'
String greenHexColor = '#32985d' // lowercase for consistent comparison
// Function to convert RGB/RGBA to hex
def rgbaToHex = { String rgba ->
	try {
		// Extract RGB values from rgba string
		def rgbaValues = rgba.replace('rgba(', '').replace('rgb(', '').replace(')', '').split(',')
		int r = Integer.parseInt(rgbaValues[0].trim())
		int g = Integer.parseInt(rgbaValues[1].trim())
		int b = Integer.parseInt(rgbaValues[2].trim())
		// Convert to hex
		String hex = String.format("#%02x%02x%02x", r, g, b).toLowerCase()
		return hex
	} catch (Exception e) {
		println("Error converting color: " + e.getMessage())
		return ""
	}
}

// Map to store significance status of each cell type
Map<String, Boolean> cellSignificance = [:]

// Improved approach that checks cell significance
def checkCellDotColor = { String cellType ->
	println("\n==== Checking " + cellType + " ====")
	boolean isSignificant = false
	
	try {
		// Define the cell object with a more direct XPath
		TestObject cellObject = new TestObject('Cell - ' + cellType)
		cellObject.addProperty('xpath', ConditionType.EQUALS,
			"//div[contains(@class, 'cell-row') and contains(., '" + cellType + "')]")
		
		WebElement cellElement = null
		
		try {
			// Try to find the cell element
			cellElement = WebUiCommonHelper.findWebElement(cellObject, 10)
			println("Found cell element for: " + cellType)
			
			// Use JavaScript to print the HTML content for debugging
			String cellHTML = (String) js.executeScript("return arguments[0].outerHTML;", cellElement)
			println("Cell HTML structure: " + cellHTML.substring(0, Math.min(cellHTML.length(), 200)) + "...")
			
			// Check if the cell element is visible and scroll to it if needed
			if (!cellElement.isDisplayed()) {
				js.executeScript("arguments[0].scrollIntoView(true);", cellElement)
				wait.until(ExpectedConditions.visibilityOf(cellElement))
				println("Scrolled to cell element: " + cellType)
			}
			
			// Skip the cell name verification and directly look for dots
			boolean foundDot = false
			
			// Try these different approaches to find dots
			List<String> dotXpaths = [
				".//div[contains(@class, 'default') and contains(@class, 'significant')]",
				".//div[contains(@class, 'default') and contains(@class, 'non-significant')]",
				".//div[contains(@class, 'dot') and contains(@class, 'significant')]",
				".//div[contains(@class, 'dot') and contains(@class, 'non-significant')]",
				".//div[contains(@class, 'dot')]",
				".//div[contains(@class, 'default')]"
			]
			
			// Try each XPath until we find something
			for (String xpath : dotXpaths) {
				try {
					List<WebElement> dots = cellElement.findElements(By.xpath(xpath))
					if (dots.size() > 0) {
						WebElement dot = dots.get(0)
						String dotColor = dot.getCssValue('background-color')
						String hexDotColor = rgbaToHex(dotColor)
						println("Found dot with XPath '" + xpath + "' for " + cellType + " with color: " + hexDotColor)
						
						if (hexDotColor.equals(redHexColor)) {
							println("RESULT: " + cellType + " is marked as SIGNIFICANT (Red)")
							isSignificant = true
						} else if (hexDotColor.equals(greenHexColor)) {
							println("RESULT: " + cellType + " is marked as NON-SIGNIFICANT (Green)")
							isSignificant = false
						} else {
							println("RESULT: " + cellType + " has dot with unexpected color: " + hexDotColor)
						}
						
						foundDot = true
						break
					}
				} catch (Exception e) {
					println("Tried XPath '" + xpath + "' but no dot found")
				}
			}
			
			// If no dot found with any approach
			if (!foundDot) {
				println("WARNING: Could not find any dot for " + cellType + " with any of the attempted XPaths")
				
				// Last resort: check for ANY div that might be a dot
				try {
					List<WebElement> allDivs = cellElement.findElements(By.xpath(".//div"))
					println("Found " + allDivs.size() + " divs within the cell. Checking for possible dots...")
					
					for (WebElement div : allDivs) {
						try {
							String className = div.getAttribute("class")
							String style = div.getAttribute("style")
							
							// Only check elements that might be dots (small, square, with color)
							if ((className != null && (className.contains("dot") || className.contains("default"))) ||
								(style != null && style.contains("background"))) {
								
								String divColor = div.getCssValue('background-color')
								String hexDivColor = rgbaToHex(divColor)
								
								if (hexDivColor.equals(redHexColor) || hexDivColor.equals(greenHexColor)) {
									println("FOUND POTENTIAL DOT: " + cellType + " with color: " + hexDivColor)
									if (hexDivColor.equals(redHexColor)) {
										println("RESULT: " + cellType + " is possibly marked as SIGNIFICANT (Red)")
										isSignificant = true
									} else {
										println("RESULT: " + cellType + " is possibly marked as NON-SIGNIFICANT (Green)")
										isSignificant = false
									}
									break
								}
							}
						} catch (Exception e) {
							// Ignore individual div errors
						}
					}
				} catch (Exception e) {
					println("Error with last resort dot search: " + e.getMessage())
				}
			}
			
		} catch (Exception e) {
			println("Error finding cell element for " + cellType + ": " + e.getMessage())
		}
	} catch (Exception e) {
		println("Major error checking " + cellType + ": " + e.getMessage())
	}
	
	// Store significance status
	cellSignificance[cellType] = isSignificant
	return isSignificant
}

// Check all three cell types
checkCellDotColor('Microcytes')
checkCellDotColor('Macrocytes')
checkCellDotColor('Anisocytosis')

// Now navigate to Summary tab and verify presence of cells based on significance
println("\n==== Navigating to Summary Tab ====")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load

// Try to scroll to make sure all elements are visible
try {
	// Use the existing js variable, don't redeclare it
	js.executeScript("window.scrollTo(0, 0)")
	WebUI.delay(1)
	js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)")
	WebUI.delay(1)
} catch (Exception e) {
	println("Warning: Unable to scroll page: " + e.getMessage())
}

// Map of cell types to their exact XPaths provided by the user
Map<String, String> cellXPaths = [
	'Microcytes': "//div[contains(@class, 'reportSummary_pane-body__table-row__name__XCUeF') and .//span[text()='Microcytes']]",
	'Macrocytes': "//div[contains(@class, 'reportSummary_pane-body__table-row__name__XCUeF') and .//span[text()='Macrocytes']]",
	'Anisocytosis': "//div[contains(@class, 'reportSummary_pane-body__table-row__name__XCUeF') and .//span[text()='Anisocytosis']]"
]

// Function to verify if a cell type is present in Summary Tab - using the exact XPaths
def verifyInSummaryTab = { String cellType ->
	println("\n==== Verifying " + cellType + " in Summary Tab ====")
	boolean shouldBePresent = cellSignificance[cellType]
	boolean isPresent = false
	
	try {
		// Get the exact XPath for this cell type
		String exactXPath = cellXPaths[cellType]
		
		// Create TestObject with the exact XPath
		TestObject summaryCell = new TestObject('Summary Cell - ' + cellType)
		summaryCell.addProperty('xpath', ConditionType.EQUALS, exactXPath)
		
		// Print the XPath for debugging
		println("Using exact XPath: " + exactXPath)
		
		// Check if element exists
		isPresent = WebUI.verifyElementPresent(summaryCell, 5, FailureHandling.OPTIONAL)
		
		if (isPresent) {
			println("Found " + cellType + " using the exact XPath")
			
			// Try to get more information about the element for debugging
			try {
				WebElement element = WebUiCommonHelper.findWebElement(summaryCell, 5)
				String elementText = element.getText()
				String elementHTML = element.getAttribute("outerHTML")
				println("Element text: " + elementText)
				println("Element HTML: " + elementHTML.substring(0, Math.min(elementHTML.length(), 200)) + "...")
			} catch (Exception e) {
				println("Failed to get element details: " + e.getMessage())
			}
		} else {
			println("Could not find " + cellType + " using the exact XPath")
		}
		
		// Evaluate results
		if (shouldBePresent && isPresent) {
			println("PASS: " + cellType + " is significant (red) and correctly present in Summary Tab")
		} else if (!shouldBePresent && !isPresent) {
			println("PASS: " + cellType + " is non-significant (green) and correctly NOT present in Summary Tab")
		} else if (shouldBePresent && !isPresent) {
			println("FAIL: " + cellType + " is significant (red) but NOT present in Summary Tab")
			
			// Additional debugging for when an element should be present but isn't
			try {
				// Check if ANY element with the cell type text exists anywhere on the page
				String textSearchXPath = "//*[contains(text(), '" + cellType + "')]"
				List<WebElement> anyMatches = driver.findElements(By.xpath(textSearchXPath))
				
				if (anyMatches.size() > 0) {
					println("NOTE: Found " + anyMatches.size() + " element(s) containing text '" + cellType + "' elsewhere on page")
					WebElement firstMatch = anyMatches.get(0)
					String matchHTML = firstMatch.getAttribute("outerHTML")
					println("First match HTML: " + matchHTML.substring(0, Math.min(matchHTML.length(), 200)) + "...")
					
					// Try to get parent element context
					try {
						WebElement parent = firstMatch.findElement(By.xpath("./.."))
						String parentHTML = parent.getAttribute("outerHTML")
						println("Parent HTML: " + parentHTML.substring(0, Math.min(parentHTML.length(), 200)) + "...")
					} catch (Exception e2) {
						// Ignore parent element errors
					}
				} else {
					println("NOTE: No elements containing text '" + cellType + "' found anywhere on the page")
				}
			} catch (Exception e) {
				println("Error during additional debugging: " + e.getMessage())
			}
		} else {
			println("FAIL: " + cellType + " is non-significant (green) but incorrectly present in Summary Tab")
		}
		
	} catch (Exception e) {
		println("Error verifying " + cellType + " in Summary Tab: " + e.getMessage())
	}
}

// Verify each cell type in summary tab
verifyInSummaryTab('Microcytes')
verifyInSummaryTab('Macrocytes')
verifyInSummaryTab('Anisocytosis')

//WebUI.closeBrowser()

