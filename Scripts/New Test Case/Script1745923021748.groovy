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
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import com.kms.katalon.core.webui.driver.WebUIDriverType
//import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeDriver
//import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.common.WebUiCommonHelper
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import com.kms.katalon.core.util.KeywordUtil
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.regex.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.logging.KeywordLogger
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By
import java.time.Duration
import com.kms.katalon.core.logging.KeywordLogger.*
import org.testng.Assert;



//public KeywordLogger logger = new KeywordLogger()
//
//public WebDriver driver =DriverFactory.getWebDriver()
//public Actions actions =new Actions(driver)
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_To be reviewed'))
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_WBC'))
WebUI.verifyElementVisible(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_WBC'))
WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_WBC'), 'WBC')
public boolean verifyCountAfterReclassification(String countXPath, String cellNameXPath) throws InterruptedException {
	boolean flag = true;
	// Fetch count and cell name elements
	List<WebElement> countElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath(countXPath))));
	List<WebElement> cellNameElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath(cellNameXPath))));
	// Define cell types for classification
	
	ArrayList<String> cellTypes = new ArrayList<>(Arrays.asList("Neutrophils","Lymphocytes", "Monocytes", "Basophils", "Eosinophils",
		"Atypical Cells/Blasts", "Immature Granulocytes", "Immature Eosinophils",
		"Immature Basophils", "Promonocytes", "Prolymphocytes", "Hairy Cells",
		"Sezary Cells", "Plasma Cells"));
	Map<String, List<String>> cellToSubCells = new HashMap<>();
	cellToSubCells.put("Neutrophils", Arrays.asList("Band Forms", "Hypersegmented","Neutrophils with Toxic Granules"));
	cellToSubCells.put("Lymphocytes", Arrays.asList("Reactive", "Large Granular Lymphocytes"));
	cellToSubCells.put("Atypical Cells/Blasts", Arrays.asList("Atypical Cells","Lymphoid Blasts","Myeloid Blasts"));
	cellToSubCells.put("Immature Granulocytes", Arrays.asList("Promyelocytes","Myelocytes","Metamyelocytes"));
	// Iterate through each cell
	for (int i = 0; i < countElements.size(); i++) {
		String actualCellName = cellNameElements.get(i).getText();
		String countText = countElements.get(i).getText();
		int initialCount = 0;
		// Parse the count
		try {
			if (!countText.isEmpty() && !countText.equals("-")) {
				initialCount = Integer.parseInt(countText);
			}
		} catch (NumberFormatException e) {
			logger.logError("Error parsing count for cell: " + actualCellName);
			continue;
		}
		// Skip cells with invalid counts
		if (initialCount == 0 || initialCount == 1 || actualCellName.equals("Total")) {
			logger.logInfo("Skipping cell: " + actualCellName + " with count: " + countText);
			continue;
		}
		// Perform classification for each cell type
		countElements.get(i).click();
		for (String cellType : cellTypes) {
			List<String> subCells = cellToSubCells.get(cellType); // Get sub-cells if present
			if (subCells != null) {
				for (String subCell : subCells) {
					boolean classified = retryClassification(cellType, subCell, 3); // Retry with sub-cell
					if (!classified) {
						logger.warn("Skipping further actions for cell type: " + cellType + " -> " + subCell + " due to repeated failures.");
						break; // Exit loop for this cell type
					}
				}
			} else {
				boolean classified = retryClassification(cellType, 3); // Retry without sub-cell
				if (!classified) {
					logger.warn("Skipping further actions for cell type: " + cellType + " due to repeated failures.");
					break; // Exit loop for this cell type
				}
			}
		}
		// Refresh count elements to get updated counts
		countElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath(countXPath))));
		String updatedCountText = countElements.get(i).getText();
		int updatedCount;
		try {
			updatedCount = Integer.parseInt(updatedCountText);
		} catch (NumberFormatException e) {
			logger.logError("Error parsing updated count for cell: " + actualCellName);
			continue;
		}
		// Validate the count
		if (updatedCountText.equals("-") && updatedCount != 1) {
			logger.logInfo("Cell count has become '-' after reclassification for: " + actualCellName);
		} else if (updatedCount == initialCount) {
			logger.logInfo("Reclassified with the same cell, count remains unchanged for: " + actualCellName);
		} else if (updatedCount < initialCount) {
			logger.logInfo("Count successfully decreased for cell: " + actualCellName + ". Initial: " + initialCount + ", Updated: " + updatedCount);
		} else {
			logger.logError("Count mismatch for cell: " + actualCellName + ". Initial: " + initialCount + ", Updated: " + updatedCount);
			flag = false;
		}
	}
	return flag;
}
public boolean classification(String cellType, String subCellType) {
	boolean flag = false;
	KeywordLogger logger = new KeywordLogger()	
	WebDriver driver =DriverFactory.getWebDriver()
	Actions actions =new Actions(driver)
	WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(1000))
	try {
		// Locate and right-click on the image
		WebElement imageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='patches-section ']//img")));
		actions.moveToElement(imageElement).contextClick(imageElement).perform();
		logger.logInfo("Hovered and right-clicked on the image.");
		// Click the 'Classify' option using JavaScript
		WebElement classifyButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Classify')]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", classifyButton);
		logger.logInfo("Clicked on 'Classify' option.");
		// Wait for the classification menu and select the cell type
		WebElement cellElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'" + cellType + "')]")));
		actions.moveToElement(cellElement).perform();
		// Handle sub-cells if present
		if (subCellType != null && !subCellType.isEmpty()) {
			WebElement subCellElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ul[contains(@class,'MuiMenu-list')])[3]//li[contains(text(),'" + subCellType + "')]")));
			actions.moveToElement(subCellElement).click().perform();
			logger.logInfo("Selected sub-cell type: " + subCellType);
		} else {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", cellElement);
			logger.logInfo("Selected cell type directly: " + cellType);
		}
		// Wait for the classification confirmation
		WebElement classificationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='body']")));
		logger.logInfo("Classification completed: " + classificationMessage.getText());
		flag = true;
	} catch (Exception e) {
		logger.logError("Error during classification for cell type: " + cellType + (subCellType != null ? " -> " + subCellType : ""));
	}
	return flag;
}
public boolean retryClassification(String cellType, int maxRetries) {
	return retryClassification(cellType, null, maxRetries);
}
public boolean retryClassification(String cellType, String subCellType, int maxRetries) {
	boolean success = false;
	int attempts = 0;
	KeywordLogger logger = new KeywordLogger()
	WebDriver driver =DriverFactory.getWebDriver()
	Actions actions =new Actions(driver)
	while (attempts < maxRetries) {
		try {
			logger.logInfo("Attempting classification for cell type: " + cellType + (subCellType != null ? " -> " + subCellType : "") + ". Attempt: " + (attempts + 1));
			success = classification(cellType, subCellType);
			if (success) {
				logger.logInfo("Classification succeeded for cell type: " + cellType + (subCellType != null ? " -> " + subCellType : ""));
				break;
			}
		} catch (Exception e) {
			logger.logError("Retry attempt failed for classification: " + e.getMessage());
		}
		attempts++;
		// Small delay before retrying
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	if (!success) {
		logger.logError("Classification failed for cell type: " + cellType + (subCellType != null ? " -> " + subCellType : "") + " after " + maxRetries + " attempts.");
	}
	return success;
}

public void VerifyTheReclassificationOfNeutrophilWithAllOtherCells() throws InterruptedException {
	KeywordLogger logger = new KeywordLogger()
	
	boolean reclassify=verifyCountAfterReclassification("//tbody//tr//td[2]","//table/tbody/tr/td[1]");
	Assert.assertTrue(reclassify);
	logger.logInfo("after reclassification count of neutrophils is verified for all the other cells");
}

VerifyTheReclassificationOfNeutrophilWithAllOtherCells()




