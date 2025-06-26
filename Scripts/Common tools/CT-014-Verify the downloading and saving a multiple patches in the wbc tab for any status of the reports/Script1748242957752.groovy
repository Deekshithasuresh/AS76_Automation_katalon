import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

// Open browser and login
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In'))
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1145 AM (IST)'))
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_WBC'))

// Get driver instance
WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// Method to get patches elements — now accepts driver parameter!
def getPatches(WebDriver driver) {
    return driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
}

// Get patches
List<WebElement> patches = getPatches(driver)

if (patches.size() < 3) {
    WebUI.comment(":warning: Only found ${patches.size()} patches. Cannot select 3.")
    WebUI.closeBrowser()
    return
}

// Click first 3 patches
for (int i = 0; i < 3; i++) {
    patches[i].click()
    WebUI.comment("Selected patch ${i + 1}")
    WebUI.delay(0.5)
}

// Right click on first patch
actions.moveToElement(patches[0]).contextClick().build().perform()

// Verify download option present and click
WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

// Double click on specific patch to open
WebUI.doubleClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.delay(2) // Let download happen

// Again, click first 3 patches (to avoid stale element issue by re-fetching them)
patches = getPatches(driver)

if (patches.size() < 3) {
    WebUI.comment(":warning: Only found ${patches.size()} patches. Cannot select 3.")
    WebUI.closeBrowser()
    return
}

for (int i = 0; i < 3; i++) {
    patches[i].click()
    WebUI.comment("Selected patch ${i + 1} again")
    WebUI.delay(0.5)
}

// Right click on second patch
actions.moveToElement(patches[1]).contextClick().build().perform()

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

WebUI.delay(3) // Wait for download

// Define file paths
String downloadDir = System.getProperty("user.home") + File.separator + "Downloads"
String sourceImagePath = downloadDir + File.separator + "patch.png"
String jpgPath = downloadDir + File.separator + "patch.jpg"
String zipPath = downloadDir + File.separator + "patch.zip"

try {
    // Read image file
    File sourceFile = new File(sourceImagePath)
    if (!sourceFile.exists()) {
        WebUI.comment("Source image not found at: ${sourceImagePath}")
        WebUI.closeBrowser()
        return
    }

    BufferedImage bufferedImage = ImageIO.read(sourceFile)

    // Save as JPG
    File jpgFile = new File(jpgPath)
    ImageIO.write(bufferedImage, "jpg", jpgFile)
    println "Converted image to JPG: ${jpgPath}"

    // Zip the JPG file
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



