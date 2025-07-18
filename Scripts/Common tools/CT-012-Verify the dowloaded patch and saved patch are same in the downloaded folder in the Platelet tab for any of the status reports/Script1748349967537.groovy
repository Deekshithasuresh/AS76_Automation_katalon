import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets (3)'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets (3)'), 'Platelets')
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology (3)'), 'Morphology')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology (3)'))

// ========== DEFINE AND VERIFY FIRST PATCH IMG ==========
TestObject firstPatchImage = new TestObject('dynamicPatchImage')
firstPatchImage.addProperty('xpath', ConditionType.EQUALS, "(//div[contains(@class, 'Card patches-container')])[1]//img")
WebUI.waitForElementPresent(firstPatchImage, 10)
WebUI.waitForElementVisible(firstPatchImage, 10)

boolean isPresent = WebUI.verifyElementPresent(firstPatchImage, 10, FailureHandling.OPTIONAL)
boolean isVisible = WebUI.verifyElementVisible(firstPatchImage, FailureHandling.OPTIONAL)
if (!isPresent || !isVisible) {
	WebUI.comment("❌ Patch image not found or not visible on screen.")
	assert false
}

// ========== GET IMAGE SRC ==========
String patchSrc = WebUI.getAttribute(firstPatchImage, 'src')
if (patchSrc == null) {
	WebUI.comment("❌ 'src' attribute not found in patch image.")
	assert false
}

String uiBase64 = ''
if (patchSrc.startsWith("data:image")) {
	uiBase64 = patchSrc.split(",")[1]
} else {
	WebUI.comment("⚠️ Image is not base64-embedded. Will verify downloaded file only.")
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

// ========== CONVERT DOWNLOADED IMAGE TO BASE64 USING CORRECT EXTENSION ==========
String extension = latestFile.getName().split("\\.")[1].toLowerCase()
WebUI.comment("🧩 Detected file extension: " + extension)

BufferedImage downloadedImage = ImageIO.read(latestFile)
ByteArrayOutputStream baos = new ByteArrayOutputStream()
ImageIO.write(downloadedImage, extension, baos) // Use detected format, not hardcoded PNG
String downloadedBase64 = Base64.encoder.encodeToString(baos.toByteArray())

// ========== COMPARE UI AND DOWNLOADED PATCH ==========
if (uiBase64 != '') {
	if (uiBase64 == downloadedBase64) {
		WebUI.comment("✅ UI and downloaded patch match.")
	} else {
		WebUI.comment("❌ UI and downloaded patch DO NOT match.")
		WebUI.verifyMatch(uiBase64, downloadedBase64, true) // Will fail test
	}
} else {
	WebUI.comment("⚠️ UI patch not base64; verifying only that image was downloaded.")
	WebUI.verifyEqual(latestFile.exists(), true)
}
