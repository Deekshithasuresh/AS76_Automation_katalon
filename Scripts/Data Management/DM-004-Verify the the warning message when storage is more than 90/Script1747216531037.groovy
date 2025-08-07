import javax.sound.sampled.*
import javax.swing.JOptionPane

import org.openqa.selenium.*

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// -------------------- Configuration --------------------
float totalSize = 769  // Total storage in GB (as configured on device)
int targetPercent = 92 // Target usage percentage (you can change this)
int fileSizePerFileMB = 1024 // 1 GB per dummy file

// Get Data from Excel
String filePath = 'Time_zone_data/Manual_data.xlsx'
String hostIP = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Host IP')
String username =CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'User name')
String password = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Password')
String targetDirectory = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Target storege area')

String expectedGreen = 'rgb(35, 206, 107)'
String expectedOrange = 'rgb(255, 142, 9)'
String expectedRed = 'rgb(224, 66, 77)'

// -------------------- TestObjects --------------------
TestObject percentageText = new TestObject('percentageText')
percentageText.addProperty('xpath', ConditionType.EQUALS, "//span[@class='percentage']")

TestObject progressBar = new TestObject('progressBar')
progressBar.addProperty('xpath', ConditionType.EQUALS, "//span[contains(@class, 'MuiLinearProgress-barColorPrimary')]")

TestObject warningMessage = new TestObject('warningMessage')
warningMessage.addProperty('xpath', ConditionType.EQUALS, "//div[@class='warning-text']")

// -------------------- Login --------------------
CustomKeywords.'generic.dataManagement.loginAdmin'()

// -------------------- Read current usage from UI --------------------
WebUI.waitForElementVisible(percentageText, 10)
String percentageStr = WebUI.getText(percentageText, FailureHandling.OPTIONAL)

if (percentageStr == null || percentageStr.trim().isEmpty()) {
	WebUI.comment("❌ Could not get current usage percent.")
	assert false : "percentageText is missing or empty"
}

percentageStr = percentageStr.replaceAll("[^0-9]", "")
int currentPercent = percentageStr.toInteger()
println("📊 Current usage from UI: ${currentPercent}%")

// -------------------- Calculate current usage in GB --------------------
float currentUsed = (currentPercent / 100.0) * totalSize
float uploadRequired = ((targetPercent / 100.0) * totalSize) - currentUsed
uploadRequired = uploadRequired > 0 ? uploadRequired : 0

int numberOfFiles = Math.ceil(uploadRequired).toInteger()
println("📦 Upload required to reach ${targetPercent}%: ${uploadRequired} GB (~${numberOfFiles} files)")

// -------------------- Upload dummy files --------------------
if (numberOfFiles > 0) {
	CustomKeywords.'manual.dataManagement.uploadDummyFileToDevice'(
		hostIP, username, password, numberOfFiles, fileSizePerFileMB, targetDirectory
	)
	WebUI.refresh()
	WebUI.delay(15)
}

// -------------------- Re-check usage after upload --------------------
WebUI.waitForElementVisible(percentageText, 10)
String updatedPercentageStr = WebUI.getText(percentageText, FailureHandling.OPTIONAL)

if (updatedPercentageStr == null || updatedPercentageStr.trim().isEmpty()) {
	WebUI.comment("❌ Could not read updated usage percentage.")
	assert false : "percentageText is still missing or empty"
}

updatedPercentageStr = updatedPercentageStr.replaceAll("[^0-9]", "")
int updatedPercent = updatedPercentageStr.toInteger()
println("🧠 Updated usage: ${updatedPercent}%")

// -------------------- Read progress bar color --------------------
WebElement barElement = WebUiCommonHelper.findWebElement(progressBar, 5)
String bgColor = barElement.getCssValue('background-color')
if (bgColor.startsWith('rgba')) {
	bgColor = bgColor.replace('rgba', 'rgb').replaceAll(',\\s*1\\)', ')')
}
println("🎨 Progress bar color: ${bgColor}")

// -------------------- Assertions --------------------
try {
	if (updatedPercent < 80) {
		assert bgColor == expectedGreen : "❌ Expected green (<80%), found: ${bgColor}"
		WebUI.verifyElementNotPresent(warningMessage, 2)
	} else if (updatedPercent >= 80 && updatedPercent < 90) {
		assert bgColor == expectedOrange : "❌ Expected orange (80–89%), found: ${bgColor}"
		WebUI.verifyElementVisible(warningMessage)
		WebUI.verifyTextPresent(
			"Available space on the report store is low. Scanning will be disabled when it reaches a critical level. Please take action to free up storage.",
			false
		)
	} else {
		assert bgColor == expectedRed : "❌ Expected red (≥90%), found: ${bgColor}"
		WebUI.verifyElementVisible(warningMessage)
		WebUI.verifyTextPresent(
			"Available space on the report store is critically low. Scanning has been disabled. Please take action to free up storage space to resume scanning.",
			false
		)
		File soundFile = new File('Include/resources/warning_msg_device.wav')  // Place alert.wav in Include/resources
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)
		Clip clip = AudioSystem.getClip()
		clip.open(audioIn)
		clip.start()
		
		
		
		// Step 1: Ask user to confirm if warning message is displayed
		int warningResponse = JOptionPane.showConfirmDialog(
			null,
			"Please verify that warning message is displayed on the device.",
			"User Confirmation",
			JOptionPane.YES_NO_OPTION
		)
		
		if (warningResponse == JOptionPane.YES_OPTION) {
			
			// Step 2: Ask user if scanning is possible
			int scanResponse = JOptionPane.showConfirmDialog(
				null,
				"Are you able to scan?",
				"User Confirmation",
				JOptionPane.YES_NO_OPTION
			)
		
			if (scanResponse == JOptionPane.YES_OPTION) {
				KeywordUtil.markFailed("❌ Test failed: Warning shown, but scanning not possible.")
				
			} else {
				KeywordUtil.markPassed("✅ Test case passed based on user confirmations.")
			}
		
		} else {
			KeywordUtil.markFailed("❌ Test failed: Warning message not displayed.")
		}
		
		
		
	}
} finally {
	// -------------------- Clean up dummy files --------------------
	println "🧹 Cleaning up dummy files..."
	CustomKeywords.'manual.dataManagement.deleteDummyFilesFromDevice'(hostIP, username, password, targetDirectory)
	WebUI.refresh()
	WebUI.delay(10)
}
