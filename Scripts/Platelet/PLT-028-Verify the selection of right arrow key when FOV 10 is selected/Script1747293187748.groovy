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
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)


WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

//Verifying the presence of the arrow keys.
WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV 10_non-clickable'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV1 10_clickable'), 0)

// Steps for navigating through all ten FOVs by clicking on the right arrow icon

WebDriver driver =DriverFactory.getWebDriver()
WebElement left_naviagtion_icon=driver.findElement(By.xpath("(//img)[15]"))

WebElement right_naviagtion_icon=driver.findElement(By.xpath("(//img)[16]"))
List<WebElement> FOV_rows=driver.findElements(By.className("fov-tuple"))

for(int i=1; i<=FOV_rows.size()-1;i++)
{
	right_naviagtion_icon.click()
	WebUI.delay(1)
	String selected_fov_bg_color=FOV_rows.get(i).getCssValue("background-color")
	println(selected_fov_bg_color)
	//assert selected_fov_bg_color=='rgba(242, 246, 255, 1)'
}

//Verify the state of the right arrow key when Fov10 is selected
WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV 10_non-clickable'), 0)