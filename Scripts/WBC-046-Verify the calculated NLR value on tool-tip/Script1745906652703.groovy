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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
WebDriver driver = new ChromeDriver();

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_28-Apr-2025, 1248 PM (IST)'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_WBC'))

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_16'),0)
WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_2'),0)

String neut_value=WebUI.getText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_16'))
String lump_value=WebUI.getText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_2'))

neut_value_flaot=Float.parseFloat(neut_value)
lump_value_flaot=Float.parseFloat(neut_value)

Calculated_NLR=(neut_value_flaot/lump_value_flaot)
WebUI.delay(4)

WebElement hoverElement = driver.findElement(By.xpath("//div[@class='nlr-tag ']"));

		// Hover over the element
		Actions actions = new Actions(driver);
		actions.moveToElement(hoverElement).perform();
		
		WebElement nlrhoverelemnet = driver.findElement(By.xpath("//div[@class='nlr-tag ']//span"));
		String Act_nlr_value = nlrhoverelemnet.getText();
		//String sub = text.substring(6);
		System.out.println("Hovered value: " + Act_nlr_value);
		System.out.println("Calculated_NLR value: " + Calculated_NLR);
		
		

//if (Calculated_NLR==)


//WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_NLRNLR  8.0'), 'NLR')
//float check=Neut_val/Lymp_val
