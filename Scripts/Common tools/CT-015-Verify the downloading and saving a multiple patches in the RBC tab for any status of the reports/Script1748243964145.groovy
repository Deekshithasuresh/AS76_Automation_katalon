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

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.interactions.Actions
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream



WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1145 AM (IST)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'), 'RBC')

WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)


List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
if (patches.size() < 3) {
	WebUI.comment(":warning: Only found ${patches.size()} patches. Cannot select ${3}.")
	return
}
for (int i = 0; i < 3; i++) {
	patches[i].click()
	WebUI.comment("Selected patch ${i + 1}")
	WebUI.delay(0.2)
}
actions.moveToElement(patches[0]).contextClick().build().perform()
WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))
WebUI.doubleClick(findTestObject('Object Repository/Commontools/Page_PBS/div_Image settings_default-patch  patch-foc_a6a738'))
if (patches.size() < 3) {
	WebUI.comment(":warning: Only found ${patches.size()} patches. Cannot select ${3}.")
	return
}
for (int i = 0; i < 3; i++) {
	patches[i].click()
	WebUI.comment("Selected patch ${i + 1}")
	WebUI.delay(5)
}


actions.moveToElement(patches[1]).contextClick().build().perform()
WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

// === 1. Set paths ===
String downloadDir = System.getProperty("user.home") + File.separator + "Downloads"
String sourceImagePath = downloadDir + File.separator + "patch.png" // Change if different
String jpgPath = downloadDir + File.separator + "patch.jpg"
String zipPath = downloadDir + File.separator + "patch.zip"

try {
	// === 2. Read original image ===
	File sourceFile = new File(sourceImagePath)
	BufferedImage bufferedImage = ImageIO.read(sourceFile)

	// === 3. Convert and save as JPG ===
	File jpgFile = new File(jpgPath)
	ImageIO.write(bufferedImage, "jpg", jpgFile)
	println "Converted image to JPG: ${jpgPath}"

	// === 4. Zip the JPG file ===
	FileOutputStream fos = new FileOutputStream(zipPath)
	ZipOutputStream zos = new ZipOutputStream(fos)

	FileInputStream fis = new FileInputStream(jpgFile)
	ZipEntry zipEntry = new ZipEntry(jpgFile.getName())
	zos.putNextEntry(zipEntry)

	byte[] buffer = new byte[1024]
	int length
	while ((length = fis.read(buffer)) >= 0) {
		zos.write(buffer, 0, length)
	}

	zos.closeEntry()
	fis.close()
	zos.close()
	fos.close()

	println "ZIP file created at: ${zipPath}"

} catch (Exception e) {
	println "Error during image conversion or zipping: ${e.message}"
}
