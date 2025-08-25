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
import internal.GlobalVariable

import org.openqa.selenium.By
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement as WebElement


CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.waitForElementVisible(findTestObject('Object Repository/WBC/Page_PBS/button_WBC'), 0)

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/button_WBC'))
WebDriver driver = DriverFactory.getWebDriver()

OverAll_count = WebUI.getText(findTestObject('Object Repository/WBC/Page_PBS/td_56'), FailureHandling.STOP_ON_FAILURE)
float_OverAll_count = Float.parseFloat(OverAll_count)

List<WebElement> WBC_cellname_row=driver.findElements(By.xpath("//table/tbody/tr"))

for(WebElement row:WBC_cellname_row) 
	{
		List<WebElement> cells=row.findElements(By.tagName("td"))
		String cellname=(cells[0]).getText()
		println(cellname)
		
		String count=(cells[1]).getText()
		println(count)
			
		String percentage=(cells[2]).getText()
		println(percentage)
		
		if(count.equals("-") || count.equals("") || percentage.equals("-"))
			{
				println(cellname+" has zero count/percentage, so skipping the current itration")
				continue;
			}
		
		float_Act_count=Float.parseFloat(count)
		float_act_percentage = Float.parseFloat(percentage)
		
		println(float_Act_count)
		println(float_act_percentage)
		
		float calculated_cell_percentage=(((float_Act_count/float_OverAll_count))*100)
		float rounded_calculated_neut_percentage = Math.round(calculated_cell_percentage * 10) / 10.0
		println "Rounded: " + rounded_calculated_neut_percentage
		
		if(float_act_percentage==rounded_calculated_neut_percentage)
		{
			println("Percentage of a "+cellname+"is verified")
		}
		
}

