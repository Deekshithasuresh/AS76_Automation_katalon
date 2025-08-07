import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import javax.sound.sampled.*
import javax.swing.JOptionPane

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))


WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed_1'), 'Reviewed')

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Approved')


// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)


// ---------- STEP: Activate Microscopic view ----------
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and contains(@src,'microscopic-view')]"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)
WebUI.comment("✔ Microscopic view activated for Platelet Morphology.")





File soundFile = new File('Include/resources/verify_color_legend_approved_report.wav')  // Place alert.wav in Include/resources
AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)
Clip clip = AudioSystem.getClip()
clip.open(audioIn)
clip.start()



// Step 1: Prompt user with Yes/No confirmation
int response = JOptionPane.showConfirmDialog(
	null,
	"Verify that the distinct color for platelet clump annotations matches the legend in the approved report.",
	"User Confirmation",
	JOptionPane.YES_NO_OPTION
)

// Step 2: Handle response
if (response == JOptionPane.YES_OPTION) {
	KeywordUtil.markPassed("✅ Test case passed based on user confirmation.")
} else {
	KeywordUtil.markFailed("❌ Test case failed because user selected No.")
}
