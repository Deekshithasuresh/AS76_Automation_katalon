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
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV 10_non-clickable'), 0)
WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV1 10_clickable'), 0)

//trying to enter  the special characters in the  NMG values for all the Fovs from Fov1 to Fov10
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> FOV_rows = driver.findElements(By.className('fov-tuple'))

for(int i=0; i<10;i++)
{
	FOV_rows.get(i).click()
	List<WebElement> NMG_fields=FOV_rows.get(i).findElements(By.xpath("//input[@class='fov-edit-input']"))
	//NMG_fields[0].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
	//NMG_fields[0].sendKeys(Keys.chord(Keys.BACK_SPACE))
	NMG_fields[0].sendKeys('Aabczxsetydgh') //passing value to N field
	
	//NMG_fields[1].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
	//NMG_fields[1].sendKeys(Keys.chord(Keys.BACK_SPACE))
	NMG_fields[1].sendKeys('mvtyQpawan') //passing value to M field
	
	//NMG_fields[2].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
	//NMG_fields[2].sendKeys(Keys.chord(Keys.BACK_SPACE))
	NMG_fields[2].sendKeys('AqsqaonepLus') //passing value to G field
}
