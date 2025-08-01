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
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import javax.swing.JOptionPane as JOptionPane
import java.awt.Toolkit as Toolkit


// Step 1: Open the browser and navigate to URL
WebUI.openBrowser('')
WebUI.navigateToUrl('https://smoke.sigtuple.com' // Replace with your actual URL
   )
// Step 2: Play an alert to the tester


	import javax.sound.sampled.*
	import java.io.File
	
	File soundFile = new File('Include/Enter_user_name.wav')  // Place alert.wav in Include/resources
	AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)
	Clip clip = AudioSystem.getClip()
	clip.open(audioIn)
	clip.start()
	
	
// Step 3: Prompt user to enter the username
String username = JOptionPane.showInputDialog('Please enter the username:')
WebUI.click(findTestObject('Page_Sigtuple Mandara/input_User Name_mat-input-0'))
// Step 4: Set the username in the input field
WebUI.setText(findTestObject('Page_Sigtuple Mandara/input_User Name_mat-input-0'), username // Adjust the object path
   )

