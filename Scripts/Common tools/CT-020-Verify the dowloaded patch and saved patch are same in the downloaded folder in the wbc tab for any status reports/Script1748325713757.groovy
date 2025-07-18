import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Graphics2D
import java.awt.Image
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

import javax.imageio.ImageIO

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// --- Helper method to convert Image to BufferedImage ---
BufferedImage toBufferedImage(Image img) {
	BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB)
	Graphics2D bGr = bimage.createGraphics()
	bGr.drawImage(img, 0, 0, null)
	bGr.dispose()
	return bimage
}

// --- Extract Zip ---
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

boolean compareImages(BufferedImage img1, BufferedImage img2, double tolerancePercent = 5.0) {
	if (img1.width != img2.width || img1.height != img2.height) return false

	int width = img1.width
	int height = img1.height
	int totalPixels = width * height
	int diffPixels = 0

	for (int y = 0; y < height; y++) {
		for (int x = 0; x < width; x++) {
			if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
				diffPixels++
			}
		}
	}
	double diffRatio = (diffPixels * 100.0) / totalPixels
	println("🔍 Pixel mismatch: ${String.format('%.2f', diffRatio)}%")
	return diffRatio <= tolerancePercent
}

void comparePatches(List<WebElement> patches, File[] extractedImages, String viewName) {
	for (int i = 0; i < 3; i++) {
		// Screenshot of the WebElement directly (UI Patch)
		File patchFile = patches[i].getScreenshotAs(OutputType.FILE)
		BufferedImage uiImg = ImageIO.read(patchFile)
		File uiOut = new File("./ui_patch_${viewName}_${i + 1}.png")
		ImageIO.write(uiImg, "png", uiOut)

		// Extracted image from ZIP
		BufferedImage extractedImg = ImageIO.read(extractedImages[i])
		File extractedOut = new File("./extracted_patch_${viewName}_${i + 1}.png")
		ImageIO.write(extractedImg, "png", extractedOut)

		// Compare with 10% tolerance
		boolean match = compareImages(uiImg, extractedImg, 10.0)
		WebUI.comment(match ? "✅ [${viewName}] Patch ${i + 1} matches extracted image"
							: "❌ [${viewName}] Patch ${i + 1} does NOT match")
	}
}



// --- Start Test ---
String downloadDir = System.getProperty("user.home") + File.separator + "Downloads"
CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'))
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'), 'WBC')
WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)
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
