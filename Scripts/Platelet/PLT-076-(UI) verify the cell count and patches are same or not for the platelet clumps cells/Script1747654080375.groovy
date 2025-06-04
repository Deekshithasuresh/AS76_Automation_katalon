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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.*
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
	
WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

// Select a sample
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)

	
	// Create a dynamic TestObject with XPath where text equals “Monocytes”
	TestObject PlateletClumpsElement = new TestObject('dynamicPlateletClumps')
	PlateletClumpsElement.addProperty('xpath', ConditionType.EQUALS, "//*[text()='Platelet Clumps']")
	
	// Click the element
	WebUI.click(PlateletClumpsElement)
	

// Ensure the page is loaded
WebUI.waitForPageLoad(10)

// Locate the count value next to "Large Platelets"
TestObject plateletClumpsCount = new TestObject('plateletClumpsCount')
plateletClumpsCount.addProperty('xpath', ConditionType.EQUALS, " (//div[contains(@class,'platelet-morph-row')])[2]")

String countText = WebUI.getText(plateletClumpsCount).trim()
WebUI.comment("Raw text extracted: '${countText}'")

// Extract only the digits
String numericPart = countText.replaceAll("[^0-9]", "")
int displayedCount = numericPart.isInteger() ? numericPart.toInteger() : 0

WebUI.comment("Extracted Platelet Clumps count: ${displayedCount}")


// Define JavaScript code as a single string
// Step 4: Execute JS to count unique image patches
//Handle zero case
if (displayedCount == 0) {
	KeywordUtil.markFailed("ℹ️ Platelets Clumps count is 0. No patches are available.")
	
} else {
	String jsCode = '''
	return (async () => {
		const SCROLL_STEP = 200;
		const SCROLL_DELAY = 300;
		const scrollContainer = document.querySelector('.List');
		if (!scrollContainer) {
			return { error: 'Scroll container not found!' };
		}
		const uniqueImages = new Set();
		let lastHeight = -1;
		const sleep = ms => new Promise(resolve => setTimeout(resolve, ms));

		const isValidPatch = src => {
			if (!src) return false;
			return (
				!src.includes('placeholder') &&
				!src.includes('logo') &&
				!src.includes('loading') &&
				!src.startsWith('data:image') &&
				!src.endsWith('.svg')
			);
		};

		const getVisibleImageSrcs = () => {
			const imgs = scrollContainer.querySelectorAll('img');
			imgs.forEach(img => {
				const src = img.getAttribute('src');
				if (isValidPatch(src)) {
					uniqueImages.add(src);
				}
			});
		};

		while (scrollContainer.scrollTop < scrollContainer.scrollHeight) {
			scrollContainer.scrollTop += SCROLL_STEP;
			await sleep(SCROLL_DELAY);
			getVisibleImageSrcs();
			if (scrollContainer.scrollTop === lastHeight) {
				break;
			}
			lastHeight = scrollContainer.scrollTop;
		}

		getVisibleImageSrcs();
		return {
			count: uniqueImages.size,
			images: [...uniqueImages]
		};
	})();
	'''

	def result = WebUI.executeJavaScript(jsCode, null)

	if (result instanceof Map && result.containsKey('error')) {
		WebUI.comment("❌ Error: " + result.error)
	} else {
		int imageCount = result.count
		WebUI.comment("✅ Displayed Platelet Clumps Count: " + displayedCount)
		WebUI.comment("✅ Counted Unique Image Patches: " + imageCount)
		
		if (imageCount == displayedCount) {
			WebUI.comment("✅ Counts match.")
		} else {
			WebUI.comment("❌ Mismatch detected. Expected: ${displayedCount}, Found: ${imageCount}")
			WebUI.verifyEqual(imageCount, displayedCount, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
}