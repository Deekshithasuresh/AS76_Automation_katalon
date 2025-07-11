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

import java.awt.image.BufferedImage
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// --- Helper methods ---
File downloadAndExtractZip(String downloadDir) {
	WebUI.delay(5)
	File[] zipFiles = new File(downloadDir).listFiles({ d, name -> name.toLowerCase().endsWith(".zip") } as FilenameFilter)
	if (!zipFiles || zipFiles.length == 0) return null
	zipFiles = zipFiles.sort { -it.lastModified() }
	File latestZip = zipFiles[0]
	File extractDir = new File(downloadDir + File.separator + "unzipped_" + System.currentTimeMillis())
	extractDir.mkdirs()

	ZipFile zipFile = new ZipFile(latestZip)
	zipFile.entries().each { ZipEntry entry ->
		File outFile = new File(extractDir, entry.getName())
		if (entry.isDirectory()) {
			outFile.mkdirs()
		} else {
			outFile.parentFile.mkdirs()
			InputStream is = zipFile.getInputStream(entry)
			OutputStream os = new FileOutputStream(outFile)
			byte[] buffer = new byte[1024]
			int len
			while ((len = is.read(buffer)) > 0) {
				os.write(buffer, 0, len)
			}
			is.close()
			os.close()
		}
	}
	zipFile.close()
	return extractDir
}

File[] getImageFiles(File folder) {
	File imageFolder = new File(folder, "images")
	File[] images = imageFolder.exists() ? imageFolder.listFiles({ d, name -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") } as FilenameFilter)
		: folder.listFiles({ d, name -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") } as FilenameFilter)
	return images.sort()
}

boolean compareImages(BufferedImage img1, BufferedImage img2, double threshold = 0.98) {
	if (img1.width != img2.width || img1.height != img2.height) return false

	int width = img1.width
	int height = img1.height
	int totalPixels = width * height
	int matchingPixels = 0

	for (int y = 0; y < height; y++) {
		for (int x = 0; x < width; x++) {
			if (img1.getRGB(x, y) == img2.getRGB(x, y)) {
				matchingPixels++
			}
		}
	}

	double matchRatio = matchingPixels / (double) totalPixels
	WebUI.comment(String.format("📊 Match ratio: %.2f%%", matchRatio * 100))
	return matchRatio >= threshold
}

void comparePatches(List<WebElement> patches, File[] extractedImages, String viewName) {
	WebDriver driver = DriverFactory.getWebDriver()
	for (int i = 0; i < 3; i++) {
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
			BufferedImage fullImg = ImageIO.read(screenshot)
			Point point = patches[i].getLocation()
			int width = patches[i].getSize().width
			int height = patches[i].getSize().height

			BufferedImage uiImg = fullImg.getSubimage(point.getX(), point.getY(), width, height)
			BufferedImage extractedImg = ImageIO.read(extractedImages[i])

			boolean match = compareImages(uiImg, extractedImg, 0.98)
			assert match : "❌ [${viewName}] Patch ${i + 1} does NOT match"
			WebUI.comment("✅ [${viewName}] Patch ${i + 1} matches extracted image")

		} catch (Exception e) {
			WebUI.comment("❌ Error comparing patch ${i + 1} in ${viewName}: ${e.message}")
			assert false : "Error during patch comparison: ${e.message}"
		}
	}
}

// --- Start Test ---
String downloadDir = System.getProperty("user.home") + File.separator + "Downloads"
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1145 AM (IST)'))
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'), 'WBC')

WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// --- Patch View ---
List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
if (patches.size() < 3) {
	WebUI.comment(":warning: Only found ${patches.size()} patches. Cannot continue.")
	return
}
(0..<3).each { i ->
	patches[i].click()
	WebUI.comment("Patch View - Selected patch ${i + 1}")
	WebUI.delay(1)
}
actions.moveToElement(patches[0]).contextClick().build().perform()
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

File extractDirPatchView = downloadAndExtractZip(downloadDir)
File[] imagesPatchView = getImageFiles(extractDirPatchView)
comparePatches(patches, imagesPatchView, "Patch View")

// --- Split View ---
WebUI.doubleClick(findTestObject('Object Repository/Commontools/Page_PBS/div_Image settings_default-patch  patch-foc_a6a738'))
patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
if (patches.size() < 3) {
	WebUI.comment(":warning: Only found ${patches.size()} patches in split view. Cannot continue.")
	return
}
(0..<3).each { i ->
	patches[i].click()
	WebUI.comment("Split View - Selected patch ${i + 1}")
	WebUI.delay(1)
}
actions.moveToElement(patches[1]).contextClick().build().perform()
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

File extractDirSplitView = downloadAndExtractZip(downloadDir)
File[] imagesSplitView = getImageFiles(extractDirSplitView)
comparePatches(patches, imagesSplitView, "Split View")

