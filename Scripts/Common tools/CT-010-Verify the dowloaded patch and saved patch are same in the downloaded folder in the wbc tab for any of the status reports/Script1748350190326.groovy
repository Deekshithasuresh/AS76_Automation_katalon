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

// ========== DEFINE AND VERIFY FIRST PATCH IMG ==========
TestObject firstPatchImage = new TestObject('dynamicPatchImage')
firstPatchImage.addProperty('xpath', ConditionType.EQUALS, "(//div[contains(@class, 'Card patches-container')])[1]//img")

// Scroll to image and wait
//WebUI.scrollToElement(firstPatchImage, 5)
WebUI.waitForElementPresent(firstPatchImage, 10)
WebUI.waitForElementVisible(firstPatchImage, 10)

// Verify visibility and log
boolean isPresent = WebUI.verifyElementPresent(firstPatchImage, 10, FailureHandling.OPTIONAL)
boolean isVisible = WebUI.verifyElementVisible(firstPatchImage, FailureHandling.OPTIONAL)

if (!isPresent || !isVisible) {
	WebUI.comment("❌ Patch image not found or not visible on screen. Check if it exists or is lazy-loaded.")
	assert false
}

// ========== GET IMAGE SRC ==========
String patchSrc = WebUI.getAttribute(firstPatchImage, 'src')
if (patchSrc == null) {
	WebUI.comment("❌ 'src' attribute not found in patch image. Exiting test.")
	assert false
}

String uiBase64 = ''
if (patchSrc.startsWith("data:image")) {
	uiBase64 = patchSrc.split(",")[1]
} else {
	WebUI.comment("⚠️ Image is not base64-embedded. Proceeding to compare downloaded image only.")
}

// ========== RIGHT CLICK AND DOWNLOAD ==========
WebUI.rightClick(firstPatchImage)
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))
WebUI.delay(10) // Wait for download to complete

// ========== GET LATEST IMAGE FROM DOWNLOADS ==========
String downloadDir = System.getProperty("user.home") + "/Downloads"
File downloadFolder = new File(downloadDir)
File latestFile = downloadFolder.listFiles()
	.findAll { it.isFile() && (it.name.endsWith(".png") || it.name.endsWith(".jpg")) }
	.sort { -it.lastModified() }
	.first()

if (latestFile == null) {
	WebUI.comment("❌ No image file found in Downloads folder.")
	assert false
}
WebUI.comment("📁 Downloaded patch: " + latestFile.getAbsolutePath())

// ========== CONVERT DOWNLOADED IMAGE TO BASE64 ==========
BufferedImage downloadedImage = ImageIO.read(latestFile)
ByteArrayOutputStream baos = new ByteArrayOutputStream()
ImageIO.write(downloadedImage, "png", baos)
String downloadedBase64 = Base64.encoder.encodeToString(baos.toByteArray())

// ========== COMPARE UI AND DOWNLOADED PATCH ==========
if (uiBase64 != '') {
	if (uiBase64 == downloadedBase64) {
		WebUI.comment("✅ UI and downloaded patch match.")
	} else {
		WebUI.comment("❌ UI and downloaded patch DO NOT match.")
		WebUI.verifyMatch(uiBase64, downloadedBase64, true) // This will fail test if they differ
	}
} else {
	WebUI.comment("⚠️ UI patch not base64; verifying that image was downloaded successfully.")
	WebUI.verifyEqual(latestFile.exists(), true)
}