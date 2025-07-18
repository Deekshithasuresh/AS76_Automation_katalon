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
import java.time.Duration as Duration
import java.util.concurrent.TimeoutException as TimeoutException
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.nio.file.*
import java.io.File as File

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")


WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'), 'RBC')
WebUI.click(findTestObject('Commontools/Macrocytes_cell_name'), FailureHandling.STOP_ON_FAILURE)

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

// Find .jpg files that are not already renamed to patchX.jpg
// Get the most recently modified .jpg file
// Find existing patch files
// If the file exists (rare), delete it first to avoid rename failure
WebUI.doubleClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)

WebElement patchfirst = driver.findElement(By.xpath('(//div[@class=\'Card patches-container\'])[1]'))

actions.contextClick(patchfirst).perform()

WebUI.click(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download')) // Find .jpg files that are not already renamed to patchX.jpg
// Get the most recently modified .jpg file
// Find existing patch files
// If the file exists (rare), delete it first to avoid rename failure

void renameLatestDownloadedPatch() {
    String downloadPath = System.getProperty('user.home') + '/Downloads'

    File dir = new File(downloadPath)

    File[] jpgFiles = dir.listFiles((({ def file ->
                file.name.toLowerCase().endsWith('.jpg') && !(file.name.toLowerCase() ==~ 'patch\\d+\\.jpg')
            }) as FileFilter))

    assert (jpgFiles != null) && (jpgFiles.length > 0) : '❌ No new JPG file found in the folder.'

    File latestFile = jpgFiles.max({ 
            it.lastModified()
        })

    println("📥 Most recently downloaded JPG file: $latestFile.name")

    File[] patchFiles = dir.listFiles((({ def file ->
                file.name.toLowerCase() ==~ 'patch\\d+\\.jpg'
            }) as FileFilter))

    int nextPatchNumber = 1

    if ((patchFiles != null) && (patchFiles.length > 0)) {
        List<Integer> numbers = patchFiles.collect({ def file ->
                    ((file.name.replaceAll('patch(\\d+)\\.jpg', '$1')) as Integer)
            })

        nextPatchNumber = (numbers.max() + 1)
    }
    
    File newFile = new File(downloadPath + "/patch$nextPatchNumber.jpg")

    if (newFile.exists()) {
        println("⚠️ patch$nextPatchNumber.jpg already exists. Replacing it.")

        newFile.delete()
    }
    
    boolean success = latestFile.renameTo(newFile)

    assert success : '❌ Failed to rename the file.'

    assert newFile.exists() : '❌ Renamed file not found in the folder.'

    println("✅ Renamed file confirmed as patch$nextPatchNumber.jpg")
}

void renameLatestDownloadedPatch1() {
    String downloadPath = System.getProperty('user.home') + '/Downloads'

    File dir = new File(downloadPath)

    File[] jpgFiles = dir.listFiles((({ def file ->
                file.name.toLowerCase().endsWith('.jpg') && !(file.name.toLowerCase() ==~ 'patch\\d+\\.jpg')
            }) as FileFilter))

    assert (jpgFiles != null) && (jpgFiles.length > 0) : '❌ No new JPG file found in the folder.'

    File latestFile = jpgFiles.max({ 
            it.lastModified()
        })

    println("📥 Most recently downloaded JPG file: $latestFile.name")

    File[] patchFiles = dir.listFiles((({ def file ->
                file.name.toLowerCase() ==~ 'patch\\d+\\.jpg'
            }) as FileFilter))

    int nextPatchNumber = 1

    if ((patchFiles != null) && (patchFiles.length > 0)) {
        List<Integer> numbers = patchFiles.collect({ def file ->
                    ((file.name.replaceAll('patch(\\d+)\\.jpg', '$1')) as Integer)
            })

        nextPatchNumber = (numbers.max() + 1)
    }
    
    File newFile = new File(downloadPath + "/patch$nextPatchNumber.jpg")

    if (newFile.exists()) {
        println("⚠️ patch$nextPatchNumber.jpg already exists. Replacing it.")

        newFile.delete()
    }
    
    boolean success = latestFile.renameTo(newFile)

    assert success : '❌ Failed to rename the file.'

    assert newFile.exists() : '❌ Renamed file not found in the folder.'

    println("✅ Renamed file confirmed as patch$nextPatchNumber.jpg")
}

