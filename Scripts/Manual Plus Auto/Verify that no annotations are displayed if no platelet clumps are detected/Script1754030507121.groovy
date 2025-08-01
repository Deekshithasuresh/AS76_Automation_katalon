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
import org.openqa.selenium.*
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
	
CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)

	
	// Create a dynamic TestObject with XPath where text equals “Monocytes”
	TestObject PlateletClumpsElement = new TestObject('dynamicPlateletClumps')
	PlateletClumpsElement.addProperty('xpath', ConditionType.EQUALS, "//*[text()='Platelet Clumps']")
	
	// Click the element
	WebUI.click(PlateletClumpsElement)
	
	// ---------- STEP: Activate Microscopic view ----------
	TestObject microViewBtn = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//img[@alt='Microscopic view' and contains(@src,'microscopic-view')]"
	)
	WebUI.waitForElementClickable(microViewBtn, 10)
	WebUI.click(microViewBtn)
	WebUI.comment("✔ Microscopic view activated for Platelet Morphology.")
	
	
	
	import javax.sound.sampled.*
	import java.io.File
	
	File soundFile = new File('Include/resources/Platelet_clums_no_anototion.wav')  // Place alert.wav in Include/resources
	AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)
	Clip clip = AudioSystem.getClip()
	clip.open(audioIn)
	clip.start()
	
	
	import javax.swing.JOptionPane
	import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
	import com.kms.katalon.core.util.KeywordUtil
	
	// Step 1: Prompt user with Yes/No confirmation
	int response = JOptionPane.showConfirmDialog(
		null,
		"Do you want to continue this test case?",
		"User Confirmation",
		JOptionPane.YES_NO_OPTION
	)
	
	// Step 2: Handle response
	if (response == JOptionPane.YES_OPTION) {		
		KeywordUtil.markPassed("✅ Test case passed based on user confirmation.")
	} else {
		KeywordUtil.markFailed("❌ Test case failed because user selected No.")
	}
	