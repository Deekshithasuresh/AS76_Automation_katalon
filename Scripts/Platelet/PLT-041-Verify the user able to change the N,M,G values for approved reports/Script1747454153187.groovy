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
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions

CustomKeywords.'generic.custumFunctions.login'()

//Verify the absence of edit CTA for multiplication factor for approved report.
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/img'))


WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/div_Reviewed'))

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Approved')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

// To check the Fovs are editable or not
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> FOV_rows = driver.findElements(By.className("fov-tuple"))

println("Total FOV count: " + FOV_rows.size())

boolean anyEditable = false;

for (int i = 0; i < FOV_rows.size(); i++) {
	String fov_color = FOV_rows.get(i).getCssValue("background-color")
	println("FOV" + (i + 1) + " background color: " + fov_color)

	// Check if the FOV has an 'editable' class (adjust as per your app)
	String classAttr = FOV_rows.get(i).getAttribute("class")
	if (classAttr != null && classAttr.contains("editable")) {
		println("FOV" + (i + 1) + " is editable!")
		anyEditable = true
	}
}

assert !anyEditable : "Some FOVs are editable!"

println("All FOVs are in non-editable mode.")
