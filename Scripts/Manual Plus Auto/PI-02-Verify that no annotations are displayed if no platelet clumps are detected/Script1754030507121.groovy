import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import javax.sound.sampled.*
import javax.swing.JOptionPane

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

	
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
	
	
	
	
	
	File soundFile = new File('Include/resources/Platelet_clums_no_anototion.wav')  // Place alert.wav in Include/resources
	AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)
	Clip clip = AudioSystem.getClip()
	clip.open(audioIn)
	clip.start()
	

	
	// Step 1: Prompt user with Yes/No confirmation
	int response = JOptionPane.showConfirmDialog(
		null,
		"Verify that  no annotations are displayed if no platelet clumps are detected.",
		"User Confirmation",
		JOptionPane.YES_NO_OPTION
	)
	
	// Step 2: Handle response
	if (response == JOptionPane.YES_OPTION) {		
		KeywordUtil.markPassed("✅ Test case passed based on user confirmation.")
	} else {
		KeywordUtil.markFailed("❌ Test case failed because user selected No.")
	}
	