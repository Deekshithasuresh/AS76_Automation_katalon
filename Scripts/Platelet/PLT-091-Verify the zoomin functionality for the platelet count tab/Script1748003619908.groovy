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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import imageutils.BlurChecker as BlurChecker
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO
import java.util.Base64


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.maximizeWindow()

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

//WebUI.delay(10)
// Select a sample
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.delay(2)

	
	// === Function to get base64 image from canvas ===
	def getCanvasImageBase64 = {
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
		String base64 = js.executeScript("""
       const canvas = document.querySelector('#pbs-volumeViewport canvas');
       return canvas.toDataURL('image/png');
   """)
		return base64
	}
	

	// === Step 1: Capture canvas before zoom in ===
	WebUI.comment("Capturing canvas before zoom in...")
	String beforePanBase64 = getCanvasImageBase64()

	
	TestObject scaleBar = findTestObject('Object Repository/Platelets/Page_PBS/scale_bar')
	scaleBarValue = WebUI.getText(scaleBar)
	WebUI.comment("Scale bar value: " + scaleBarValue)
	
	
	
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_zoom-in'))
	
	
	WebUI.delay(5) // Let canvas settle
	
	// === Step 3: Capture canvas after zoom in ===
	WebUI.comment("Capturing canvas after zoom in...")
	String afterPanBase64 = getCanvasImageBase64()

	
	scaleBarValue1 = WebUI.getText(scaleBar)
	WebUI.comment("Scale bar value: " + scaleBarValue1)
	
	// === Step 4: Compare base64 data ===
	if (beforePanBase64 == afterPanBase64) {
		WebUI.comment("❌ Canvas did NOT change ")
	} else {
		WebUI.comment("✅ Canvas has changed ")
	}
	
	if (scaleBarValue == scaleBarValue1) {
		WebUI.comment("❌ Scale bar value did not change ")
	} else {
		WebUI.comment("✅ Scale bar value has changed from "+scaleBarValue+" to "+scaleBarValue1)
	}
	
	

