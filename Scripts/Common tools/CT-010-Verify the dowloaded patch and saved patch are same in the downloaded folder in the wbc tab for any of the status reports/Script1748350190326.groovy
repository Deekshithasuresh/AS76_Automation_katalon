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

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import java.util.Base64

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

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.*
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.Base64
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import java.util.Base64
// ========== LOGIN ==========
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (20)'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (20)'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (20)'))

// ========== NAVIGATE TO PATCH ==========
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_02-May-2025, 0938 AM (IST) (4)'))
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'), 'WBC')

// ========== DEFINE IMAGE OBJECT ==========
TestObject patchImage = new TestObject('dynamicPatchImage')
patchImage.addProperty('xpath', ConditionType.EQUALS, "(//div[contains(@class, 'Card patches-container')])[1]//img")
WebUI.waitForElementVisible(patchImage, 10)
String patchSrc = WebUI.getAttribute(patchImage, 'src')
// ========== BUILD FULL URL ==========
String baseUrl = 'https://as76-pbs.sigtuple.com'
String fullImageUrl = patchSrc.startsWith('http') ? patchSrc : baseUrl + patchSrc
WebUI.comment(":globe_with_meridians: Full image URL: " + fullImageUrl)
// ========== FETCH IMAGE FROM URL ==========
BufferedImage onlineImage
try {
	URL url = new URL(fullImageUrl)
	onlineImage = ImageIO.read(url)
} catch (Exception e) {
	WebUI.comment(":x: Failed to load image from URL. Error: " + e.message)
	assert false
}
// ========== CONVERT UI IMAGE TO BASE64 ==========
ByteArrayOutputStream baosOnline = new ByteArrayOutputStream()
ImageIO.write(onlineImage, "png", baosOnline)
String uiBase64 = Base64.encoder.encodeToString(baosOnline.toByteArray())
WebUI.comment(":white_check_mark: UI image encoded.")
// ========== DOWNLOAD IMAGE ==========
WebUI.rightClick(patchImage)
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))
WebUI.delay(10) // Adjust if your download is slow
// ========== LOCATE LATEST DOWNLOADED IMAGE ==========
String downloadDir = System.getProperty("user.home") + "/Downloads"
File downloadFolder = new File(downloadDir)
File latestFile = downloadFolder.listFiles()
	.findAll { it.isFile() && (it.name.endsWith(".png") || it.name.endsWith(".jpg") || it.name.endsWith(".jpeg")) }
	.sort { -it.lastModified() }
	.first()
if (latestFile == null) {
	WebUI.comment(":x: No downloaded image found.")
	assert false
}
WebUI.comment(":file_folder: Downloaded image path: " + latestFile.getAbsolutePath())
// ========== CONVERT DOWNLOADED IMAGE TO BASE64 ==========
BufferedImage downloadedImage = ImageIO.read(latestFile)
ByteArrayOutputStream baosDownloaded = new ByteArrayOutputStream()
ImageIO.write(downloadedImage, "png", baosDownloaded)
String downloadedBase64 = Base64.encoder.encodeToString(baosDownloaded.toByteArray())
if (uiBase64 == downloadedBase64) {
	WebUI.comment(":white_check_mark: UI and downloaded image match exactly.")
} else {
	WebUI.comment(":x: UI and downloaded image DO NOT match.")
	WebUI.verifyMatch(uiBase64, downloadedBase64, true)
}





// === Pixel-wise image comparison ===
boolean matchImages(BufferedImage img1, BufferedImage img2, int tolerance) {
	if (img1.width != img2.width || img1.height != img2.height) {
		WebUI.comment(":warning: Image dimensions differ.")
		return false
	}

	int width = img1.width
	int height = img1.height
	int diffPixels = 0
	for (int y = 0; y < height; y++) {
		for (int x = 0; x < width; x++) {
			int rgb1 = img1.getRGB(x, y)
			int rgb2 = img2.getRGB(x, y)
			if (Math.abs((rgb1 & 0xFFFFFF) - (rgb2 & 0xFFFFFF)) > tolerance) {
				diffPixels++
			}
		}
	}

	double diffPercent = (diffPixels * 100.0) / (width * height)
	WebUI.comment(":mag: Difference: ${String.format('%.2f', diffPercent)}%")
	return diffPercent < 2.0 // Allow 2% difference
}

// Call image comparison
boolean result = matchImages(onlineImage, downloadedImage, 10)

if (result) {
	WebUI.comment(":white_check_mark: UI and downloaded patch visually match.")
} else {
	WebUI.comment(":x: UI and downloaded patch do NOT match visually.")
	assert false
}






