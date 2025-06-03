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
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions

CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFuctions.assignOrReassignOnTabs'('pawan', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV 10_non-clickable'), 0)
WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/img_FOV1 10_clickable'), 0)

WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> FOV_rows = driver.findElements(By.className('fov-tuple'))

int default_fov_index = -1;
for (int i = 0; i < FOV_rows.size(); i++) {
    String fov_color = FOV_rows.get(i).getCssValue('background-color')
    if (fov_color == 'rgba(242, 246, 255, 1)') {
        default_fov_index = i;
        println("Default selected FOV is FOV" + (i + 1))
        // Highlight the default FOV in edit mode
        Actions actions = new Actions(driver)
        actions.doubleClick(FOV_rows.get(i)).perform()
        WebUI.delay(1)
        println("Highlighted default FOV (FOV" + (i + 1) + ") in edit mode.")
        break;
    }
}
assert default_fov_index != -1 : 'No default FOV is selected!'
