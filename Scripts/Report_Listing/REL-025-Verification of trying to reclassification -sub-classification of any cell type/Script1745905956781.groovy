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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

//WebUI.click(findTestObject('Object Repository/Page_PBS/first_patch'))

// assume you’ve already built your dynamic TestObject or have one in the repo:
TestObject myElement = findTestObject('Object Repository/Page_PBS/first_patch')

// wait for it to be clickable/visible first
WebUI.waitForElementVisible(myElement, 10)

// then double-click it
WebUI.rightClick(myElement)

TestObject menuList = new TestObject('menuList')
menuList.addProperty('xpath', ConditionType.EQUALS,
	"//ul[@role='menu' and contains(@class,'MuiList-root')]")
WebUI.waitForElementVisible(menuList, 5)

// 3) Define the “Reclassification” menu item
TestObject reclassItem = new TestObject('reclassItem')
reclassItem.addProperty('xpath', ConditionType.EQUALS,
	"//ul[@role='menu']//li[normalize-space(text())='Reclassification']")

// 4) Verify it is NOT present
WebUI.verifyElementNotPresent(reclassItem, 3)

WebUI.comment("✅ 'Reclassification' menu option is not present as expected.")

WebUI.closeBrowser()

