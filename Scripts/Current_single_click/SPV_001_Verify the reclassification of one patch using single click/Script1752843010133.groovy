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
WebUI.maximizeWindow()

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

//WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// First navigate to WBC tab if not already there
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/Summary/span_WBC'))

// Now perform drag and drop using Actions class
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper

// Define source element (first neutrophil patch)
// Based on the image, this would be the first cell image in the grid
//WebElement sourceElement = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/WBC/img_first_neutrophil_patch'), 30)
WebElement sourceElement = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/retain_patchs/wbc image first neutrophile'), 30)


// Define target element (monocytes category/row in the table)
WebElement targetElement = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/retain_patchs/td_Monocytes'), 30)


// Create Actions object and perform drag and drop
Actions actions = new Actions(DriverFactory.getWebDriver())
actions.dragAndDrop(sourceElement, targetElement).build().perform()

// Add verification after drag and drop (e.g., check if counts updated)
// For example, verify that Neutrophil count decreased and Monocyte count increased


//WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/neutrophil_count'),94)
String	NeutrophilCountText = WebUI.getText(findTestObject('Object Repository/retain_patchs/neutrophil_count'))
println("Neutrophil Count:" + NeutrophilCountText)

String monocyteCountText = WebUI.getText(findTestObject('Object Repository/retain_patchs/monocyte_count'))
println("Monocyte Count: " + monocyteCountText)



