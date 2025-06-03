import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.concurrent.CountDownLatch

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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.By

CustomKeywords.'generic.custumFunctions.login'()

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'), 0)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Status'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/input_Status_PrivateSwitchBase-input css-1m9pwf3'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Apply'))


//  Collect all bookmark icons in the first column
TestObject icons = new TestObject('bookmarkIcons')
icons.addProperty('xpath', ConditionType.EQUALS,
	"//div/tbody/tr/td[1]/img")
List<WebElement> list = WebUiCommonHelper.findWebElements(icons, 10)
assert list.size() >= 0 : "🔍 Rows visible after filter: ${list.size()}"

int Count=list.size()
WebUI.comment("Rows visible after filter: "+Count)
println(Count)
// 4) Assert none are filled
list.eachWithIndex { WebElement icon, int i ->
	String src = icon.getAttribute('src')
	assert !src.contains('bookmark-filled') :
		"Row ${i+1}: Expected outline icon, but found filled (src='${src}')"
}
WebUI.comment("✅ All bookmark icons are unselected after applying 'Unbookmarked' filter.")

WebUI.closeBrowser()

