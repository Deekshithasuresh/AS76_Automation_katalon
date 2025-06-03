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
import java.time.Duration
import java.util.concurrent.TimeoutException
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.nio.file.*
import java.io.File

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (14)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (14)'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (14)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_19-May-2025, 0931 AM (IST) (3)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'), 'RBC')
WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))
void renameLatestDownloadedPatch() {
    String downloadPath = System.getProperty("user.home") + "/Downloads"
    File dir = new File(downloadPath)

    // Find .jpg files that are not already renamed to patchX.jpg
    File[] jpgFiles = dir.listFiles({ file ->
        file.name.toLowerCase().endsWith(".jpg") && !(file.name.toLowerCase() ==~ /patch\d+\.jpg/)
    } as FileFilter)

    assert jpgFiles != null && jpgFiles.length > 0 : "❌ No new JPG file found in the folder."

    // Get the most recently modified .jpg file
    File latestFile = jpgFiles.max { it.lastModified() }
    println "📥 Most recently downloaded JPG file: ${latestFile.name}"

    // Find existing patch files
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

    File newFile = new File(downloadPath + "/patch${nextPatchNumber}.jpg")

    // If the file exists (rare), delete it first to avoid rename failure
    if (newFile.exists()) {
        println "⚠️ patch${nextPatchNumber}.jpg already exists. Replacing it."
        newFile.delete()
    }

    boolean success = latestFile.renameTo(newFile)
    assert success : "❌ Failed to rename the file."

    assert newFile.exists() : "❌ Renamed file not found in the folder."
    println "✅ Renamed file confirmed as patch${nextPatchNumber}.jpg"
}
WebUI.doubleClick(findTestObject('Object Repository/Commontools/Page_PBS/div_Image settings_default-patch  patch-foc_a6a738'))




WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

WebElement patchfirst=driver.findElement(By.xpath("(//div[@class='Card patches-container'])[1]"))
actions.contextClick(patchfirst).perform()



WebUI.click(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

void renameLatestDownloadedPatch1() {
    String downloadPath = System.getProperty("user.home") + "/Downloads"
    File dir = new File(downloadPath)

    // Find .jpg files that are not already renamed to patchX.jpg
    File[] jpgFiles = dir.listFiles({ file ->
        file.name.toLowerCase().endsWith(".jpg") && !(file.name.toLowerCase() ==~ /patch\d+\.jpg/)
    } as FileFilter)

    assert jpgFiles != null && jpgFiles.length > 0 : "❌ No new JPG file found in the folder."

    // Get the most recently modified .jpg file
    File latestFile = jpgFiles.max { it.lastModified() }
    println "📥 Most recently downloaded JPG file: ${latestFile.name}"

    // Find existing patch files
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

    File newFile = new File(downloadPath + "/patch${nextPatchNumber}.jpg")

    // If the file exists (rare), delete it first to avoid rename failure
    if (newFile.exists()) {
        println "⚠️ patch${nextPatchNumber}.jpg already exists. Replacing it."
        newFile.delete()
    }

    boolean success = latestFile.renameTo(newFile)
    assert success : "❌ Failed to rename the file."

    assert newFile.exists() : "❌ Renamed file not found in the folder."
    println "✅ Renamed file confirmed as patch${nextPatchNumber}.jpg"
}