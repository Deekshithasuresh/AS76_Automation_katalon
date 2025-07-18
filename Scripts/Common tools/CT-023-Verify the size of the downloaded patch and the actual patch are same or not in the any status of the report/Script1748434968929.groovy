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
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")


WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'), 'RBC')
WebUI.click(findTestObject('Commontools/Macrocytes_cell_name'), FailureHandling.STOP_ON_FAILURE)

// --- Step 3: Right-click and download patch ---
WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))
WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)
WebUI.click(findTestObject('Commontools/Page_PBS/li_Download'))
WebUI.delay(2) // Wait for download to complete

// --- Step 4: Rename downloaded patch ---
String downloadPath = System.getProperty("user.home") + "/Downloads"
File dir = new File(downloadPath)

File[] jpgFiles = dir.listFiles({ file ->
	file.name.toLowerCase().endsWith(".jpg") && !(file.name.toLowerCase() ==~ /patch\d+\.jpg/)
} as FileFilter)

assert jpgFiles != null && jpgFiles.length > 0 : "❌ No new JPG file found in the folder."

File latestFile = jpgFiles.max { it.lastModified() }
File[] patchFiles = dir.listFiles({ file ->
	file.name.toLowerCase() ==~ /patch\d+\.jpg/
} as FileFilter)

int nextPatchNumber = 1
if (patchFiles != null && patchFiles.length > 0) {
	List<Integer> numbers = patchFiles.collect { file ->
		(file.name.replaceAll("patch(\\d+)\\.jpg", "\$1") as Integer)
	}
	nextPatchNumber = numbers.max() + 1
}

File renamedPatch = new File(downloadPath + "/patch${nextPatchNumber}.jpg")
if (renamedPatch.exists()) renamedPatch.delete()
boolean renamed = latestFile.renameTo(renamedPatch)
assert renamed : "❌ Failed to rename the file."

println "✅ Renamed downloaded file as ${renamedPatch.name}"

// --- Step 5: Capture UI patch as image ---
WebDriver driver = DriverFactory.getWebDriver()
WebElement uiPatch = driver.findElement(By.xpath("(//div[@class='Card patches-container'])[1]"))
File uiScreenshot = new File(downloadPath + "/uiPatch${nextPatchNumber}.png")

File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
BufferedImage fullImg = ImageIO.read(screenshot)

Point point = uiPatch.getLocation()
int width = uiPatch.getSize().width
int height = uiPatch.getSize().height

BufferedImage patchImg = fullImg.getSubimage(point.x, point.y, width, height)
ImageIO.write(patchImg, "png", uiScreenshot)

println "🖼️ Captured UI patch image as ${uiScreenshot.name}"

// --- Step 6: Compare dimensions of UI and downloaded patch ---
BufferedImage downloadedImage = ImageIO.read(renamedPatch)
BufferedImage uiImage = ImageIO.read(uiScreenshot)

if (downloadedImage.getWidth() == uiImage.getWidth() && downloadedImage.getHeight() == uiImage.getHeight()) {
	println "✅ Patch size matched: ${downloadedImage.getWidth()}x${downloadedImage.getHeight()}"
} else {
	println "❌ Patch size mismatch!"
	println "Downloaded: ${downloadedImage.getWidth()}x${downloadedImage.getHeight()}"
	println "UI Rendered: ${uiImage.getWidth()}x${uiImage.getHeight()}"
}

WebUI.closeBrowser()


