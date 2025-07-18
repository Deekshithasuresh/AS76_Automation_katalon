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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty

// Add these missing imports
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper

/**
 * Test Case: PBS Report Assignment
 * This test checks for reports with "To be reviewed" status and assigns them to deekshithaS.
 * If no "To be reviewed" reports are found, it checks the 3rd report.
 * If the 3rd report is not assigned to deekshithaS, it reassigns it.
 */

// Open browser and login to PBS system
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()



CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Verify and click on RBC
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC'), 'RBC')
WebUI.click(findTestObject('Object Repository/Summary/span_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Shape'), 'Shape')
WebUI.click(findTestObject('Object Repository/Summary/button_Shape'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')

println("\n===========================Regrading to grade3=============================================================")

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Ovalocytes_row'),'Ovalocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Ovalocytes_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Ovalocytes_grade3'))
	
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Elliptocytes_row'),'Elliptocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Elliptocytes_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Elliptocytes_grade3'))
//
//
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Teardrop Cells_row'),'Teardrop Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Teardrop Cells_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Teardrop Cells_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Fragmented_Cells_row'),'Fragmented Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Fragmented Cells_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Fragmented Cells_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Target_Cells_row'),'Target Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Target_Cells_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Target_cells_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Echinocytes_cells_row'),'Echinocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Echinocytes_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Echinocytes_grade3'))

WebUI.click(findTestObject('Object Repository/Summary/Acanthocytes_grade_03'))
WebUI.click(findTestObject('Object Repository/Summary/Sickle Cells_grade_03'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Poikilocytosis_cells_row'),'Poikilocytosis')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Poikilocytosis_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Poikilocytosis_grade3'))




// Now navigate to Summary tab and verify presence of cells based on significance
println("\n==== Navigating to Summary Tab ====")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load



println("==========Checking for RBC Shape===========")

// Check if Ovalocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Ovalocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Ovalocytes - Present in Summary")
	} else {
		println("Ovalocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Ovalocytes - Error occurred: " + e.getMessage())
}

// Check if Elliptocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Elliptocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Elliptocytes - Present in Summary")
	} else {
		println("Elliptocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Elliptocytes - Error occurred: " + e.getMessage())
}

// Check if Teardrop Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Teardrop_Cells'), 10, FailureHandling.OPTIONAL)) {
		println("Teardrop Cells - Present in Summary")
	} else {
		println("Teardrop Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Teardrop Cells - Error occurred: " + e.getMessage())
}

// Check if Fragmented Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Fragmented Cells'), 10, FailureHandling.OPTIONAL)) {
		println("Fragmented Cells - Present in Summary")
	} else {
		println("Fragmented Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Fragmented Cells - Error occurred: " + e.getMessage())
}

// Check if Target Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Target Cells'), 10, FailureHandling.OPTIONAL)) {
		println("Target Cells - Present in Summary")
	} else {
		println("Target Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Target Cells - Error occurred: " + e.getMessage())
}

// Check if Echinocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Echinocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Echinocytes - Present in Summary")
	} else {
		println("Echinocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Echinocytes - Error occurred: " + e.getMessage())
}

// Check if Acanthocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_acanthocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Acanthocytes - Present in Summary")
	} else {
		println("Acanthocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Acanthocytes - Error occurred: " + e.getMessage())
}

// Check if Sickle Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Sickle_cells'), 10, FailureHandling.OPTIONAL)) {
		println("Sickle Cells - Present in Summary")
	} else {
		println("Sickle Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Sickle Cells - Error occurred: " + e.getMessage())
}

// Check if Poikilocytosis element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Poikilocytosis'), 10, FailureHandling.OPTIONAL)) {
		println("Poikilocytosis - Present in Summary")
	} else {
		println("Poikilocytosis - Not Present in Summary")
	}
} catch (Exception e) {
	println("Poikilocytosis - Error occurred: " + e.getMessage())
}

















