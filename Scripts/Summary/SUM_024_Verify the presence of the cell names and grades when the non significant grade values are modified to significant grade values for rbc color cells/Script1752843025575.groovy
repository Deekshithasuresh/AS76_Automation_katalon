
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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
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
JavascriptExecutor js = (JavascriptExecutor) driver

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')



println("\n==== Initial Summary Tab Check ====")
// Initially land on Summary tab and check which cells are present
boolean initialHypochromicPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/20_sum_Hypochromic Cells'), 3, FailureHandling.OPTIONAL)
boolean initialPolychromaticPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/20_sum_Polychromatic'), 3, FailureHandling.OPTIONAL)

println("INITIAL SUMMARY CELLS PRESENT:")
println("Hypochromic Cells: " + (initialHypochromicPresent ? "PRESENT" : "NOT PRESENT"))
println("Polychromatic Cells: " + (initialPolychromaticPresent ? "PRESENT" : "NOT PRESENT"))

println("\n==== Navigating to RBC Color Tab for Regrading ====")
// Verify and click on RBC
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC'), 'RBC')
WebUI.click(findTestObject('Object Repository/Summary/span_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Color'), 'Colour')
WebUI.click(findTestObject('Object Repository/Summary/button_Color'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')
WebUI.comment("Successfully navigated to RBC Color tab")

// HYPOCHROMIC CELLS SECTION - Check which grade is currently SELECTED
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Hypochromic_row'), 'Hypochromic Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Hypochromic Cells_grade_row'), '')

boolean hypochromicGrade0Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Hypochromic_grade0'), 3, FailureHandling.OPTIONAL)
boolean hypochromicGrade1Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Hypochromic_grade1'), 3, FailureHandling.OPTIONAL)
boolean hypochromicGrade2Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Hypochromic_grade2'), 3, FailureHandling.OPTIONAL)
boolean hypochromicGrade3Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Hypochromic_grade3'), 3, FailureHandling.OPTIONAL)

// Apply the logic: Grade 0/1 -> Grade 3, Grade 2/3 -> Grade 0
if (hypochromicGrade0Selected || hypochromicGrade1Selected) {
	WebUI.comment("Current hypochromic cells grade is 0 or 1, changing to Grade 3")
	WebUI.click(findTestObject('Object Repository/Summary/Hypochromic_grade3'))
	WebUI.comment("Successfully set hypochromic cells grade to 3")
} else if (hypochromicGrade2Selected || hypochromicGrade3Selected) {
	WebUI.comment("Current hypochromic cells grade is 2 or 3, changing to Grade 0")
	WebUI.click(findTestObject('Object Repository/Summary/Hypochromic_grade0'))
	WebUI.comment("Successfully set hypochromic cells grade to 0")
} else {
	WebUI.comment("Unable to determine current hypochromic cells grade, setting to Grade 3 as default")
	WebUI.click(findTestObject('Object Repository/Summary/Hypochromic_grade3'))
	WebUI.comment("Set hypochromic cells grade to 3 as default")
}

// POLYCHROMATIC CELLS SECTION - Check which grade is currently SELECTED
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Polychromatic_row'), 'Polychromatic Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Polychromatic_grade_row'), '')

boolean polychromaticGrade0Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Polychromatic_grade0'), 3, FailureHandling.OPTIONAL)
boolean polychromaticGrade1Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Polychromatic_grade1'), 3, FailureHandling.OPTIONAL)
boolean polychromaticGrade2Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Polychromatic_grade2'), 3, FailureHandling.OPTIONAL)
boolean polychromaticGrade3Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/Polychromatic_grade3'), 3, FailureHandling.OPTIONAL)

// Apply the logic: Grade 0/1 -> Grade 3, Grade 2/3 -> Grade 0
if (polychromaticGrade0Selected || polychromaticGrade1Selected) {
	WebUI.comment("Current polychromatic cells grade is 0 or 1, changing to Grade 3")
	WebUI.click(findTestObject('Object Repository/Summary/Polychromatic_grade3'))
	WebUI.comment("Successfully set polychromatic cells grade to 3")
} else if (polychromaticGrade2Selected || polychromaticGrade3Selected) {
	WebUI.comment("Current polychromatic cells grade is 2 or 3, changing to Grade 0")
	WebUI.click(findTestObject('Object Repository/Summary/Polychromatic_grade0'))
	WebUI.comment("Successfully set polychromatic cells grade to 0")
} else {
	WebUI.comment("Unable to determine current polychromatic cells grade, setting to Grade 3 as default")
	WebUI.click(findTestObject('Object Repository/Summary/Polychromatic_grade3'))
	WebUI.comment("Set polychromatic cells grade to 3 as default")
}

WebUI.comment("Completed regrading all RBC Color cells")

println("\n==== Navigating Back to Summary Tab ====")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load

println("\n==== Final Summary Tab Verification ====")
WebUI.comment("Checking final cells present in summary page after regrading")
// Verify which cells are now present in summary (should show only significant cells - Grade 2 and 3)
boolean finalHypochromicPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/20_sum_Hypochromic Cells'), 3, FailureHandling.OPTIONAL)
boolean finalPolychromaticPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/20_sum_Polychromatic'), 3, FailureHandling.OPTIONAL)

println("FINAL SUMMARY CELLS PRESENT:")
println("Hypochromic Cells: " + (finalHypochromicPresent ? "PRESENT" : "NOT PRESENT"))
println("Polychromatic Cells: " + (finalPolychromaticPresent ? "PRESENT" : "NOT PRESENT"))

println("Summary verification complete - only significant cells (Grade 2 and 3) should be displayed")