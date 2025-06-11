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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import loginPackage.Login as Login
import adimin_pbs_Settings.PBS_Settings as PBS_Settings
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.util.ArrayList as ArrayList
import java.util.Arrays as Arrays
import java.util.List as List
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import java.time.Duration as Duration
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import java.util.regex.*
import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.*

Login lg = new Login()

PBS_Settings pbs_set = new PBS_Settings()

lg.AdminLogin('prem', 'prem@2807')

WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 10)

WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

WebUI.delay(2)

WebUI.waitForElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/svg_Admin Portal_MuiSvgIcon-root MuiSvgIcon_5f2eb8'), 
    10)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/svg_Admin Portal_MuiSvgIcon-root MuiSvgIcon_5f2eb8'), 
    10)

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/svg_Admin Portal_MuiSvgIcon-root MuiSvgIcon_5f2eb8'))

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/li_History'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/li_History'), 10)

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/li_History'))

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_History'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_History'), 'History')

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/X_icon'), 10)
