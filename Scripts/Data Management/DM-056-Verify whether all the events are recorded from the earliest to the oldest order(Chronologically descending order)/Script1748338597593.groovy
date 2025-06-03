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
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory

CustomKeywords.'generic.dataManagement.loginAdmin'()

WebUI.click(findTestObject('Object Repository/Page_Admin Console/path'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/li_History'), 'History')

WebUI.click(findTestObject('Object Repository/Page_Admin Console/li_History'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/div_History'), 'History')

// Grab all time elements from history
List<WebElement> timeElements = DriverFactory.getWebDriver()
	.findElements(By.xpath("//div[contains(@class,'stepper-container')]//div[contains(@class,'time')]"))


List<Date> parsedDates = []
def sdf = new java.text.SimpleDateFormat("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)

for (WebElement elem : timeElements) {
	String timeText = elem.getText().replace(" (GMT)", "").trim()
	parsedDates.add(sdf.parse(timeText))
}

// Verify that each date is >= the next one
for (int i = 0; i < parsedDates.size() - 1; i++) {
	assert parsedDates[i].after(parsedDates[i + 1]) || parsedDates[i].equals(parsedDates[i + 1]) :
		"❌ History is not in descending order at index $i"
}
println "✅ Events are listed in descending chronological order"

