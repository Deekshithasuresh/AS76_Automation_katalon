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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

for (int i = 0; i < 2; i++) {
	if (verifyPlateletConditions(i)) {
		WebUI.comment('✅ Test passed for report index: ' + i)

		 WebUI.closeBrowser()
		return null
	}
}

WebUI.comment('❌ No matching reports found in the first two.')

WebUI.closeBrowser()

def createTestObject(String xpath) {
	TestObject to = new TestObject()

	to.addProperty('xpath', ConditionType.EQUALS, xpath)

	return to
}

boolean verifyPlateletConditions(int index) {
	String rowXPath = ('(//td[starts-with(text(), \'SIG\')])[' + (index + 1)) + ']'

	TestObject rowObj = createTestObject(rowXPath)

	if (!(WebUI.verifyElementPresent(rowObj, 5, FailureHandling.OPTIONAL))) {
		return false
	}
	
	WebUI.click(rowObj)

	WebUI.delay(2)

	def checkCondition = { String cell ->
		String countXPath = ('//td[text()=\'' + cell) + '\']/following-sibling::td[1]'

		String statusXPath = ('//td[text()=\'' + cell) + '\']/following-sibling::td[2]'

		String count = WebUI.getText(createTestObject(countXPath)).trim()

		String status = WebUI.getText(createTestObject(statusXPath)).trim().toLowerCase()

		return (count.isInteger() && (Integer.parseInt(count) > 0)) && status.contains('detected')
	}

	boolean largeDetected = checkCondition('Large Platelets')

	boolean clumpsDetected = checkCondition('Platelet Clumps')

	if (!(largeDetected || clumpsDetected)) {
		return false
	}
	
	WebUI.click(createTestObject('//div[text()=\'Summary\']'))

	WebUI.delay(2)

	def summaryCheck = { String cell ->
		String xpath = ('//td[text()=\'' + cell) + '\']/following-sibling::td[contains(text(),\'Detected\')]'

		return WebUI.verifyElementPresent(createTestObject(xpath), 5, FailureHandling.OPTIONAL)
	}

	boolean summaryLarge = summaryCheck('Large Platelets')

	boolean summaryClumps = summaryCheck('Platelet Clumps')

	boolean warningPresent = WebUI.verifyElementPresent(createTestObject('//div[contains(text(),\'Platelet clumps are detected\')]'),
		5, FailureHandling.OPTIONAL)

	return (summaryLarge || summaryClumps) && warningPresent
}

