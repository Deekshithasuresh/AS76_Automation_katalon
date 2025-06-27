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
import loginPackage.Login as Login
import zoom.ZoomInOut as ZoomInOut

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('Under review')

lg.assignOrReassignOnTabs('prem')

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))


WebDriver driver =DriverFactory.getWebDriver()

        WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/Microscopic_view_CTA'))
		List<WebElement> cellRows=driver.findElements(By.xpath("//tbody/tr"))

			
		for (WebElement row : cellRows) {
			// Get the percentage element (last div inside the row)
			row.click()
			WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/Microscopic_view_CTA'))
			
			WebElement cellname_ele = row.findElement(By.xpath(".//td[1]"))
			String cellname = cellname_ele.getText()
			println(cellname)

			WebElement count_ele = row.findElement(By.xpath(".//td[2]"))
			Integer patch_count = Integer.parseInt(count_ele.getText())
			println(patch_count)
			
			if(patch_count>0)
			{
				WebUI.comment(" current report has some patches present in it, so can't verify the no patch available message")
				continue
			}
			else {
				
				WebUI.verifyElementPresent(findTestObject('RBC_Objects/Page_PBS/split-view_button'), 0)
				
				WebUI.click(findTestObject('RBC_Objects/Page_PBS/split-view_button'))
				
				WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/No_pathes_available_text'), 'No patches available for '+cellname)
				
				WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview'), 'No preview')
			}
			
		}



