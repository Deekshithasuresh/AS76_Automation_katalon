import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
	
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

	
	// Create a dynamic TestObject with XPath where text equals Large Platelets
	TestObject LargePlateletsElement = new TestObject('dynamicLargePlatelets')
	LargePlateletsElement.addProperty('xpath', ConditionType.EQUALS, "//*[text()='Large Platelets']")
	
	// Click the element
	WebUI.click(LargePlateletsElement)
	

// Ensure the page is loaded
WebUI.waitForPageLoad(10)

// Locate the count value next to "Large Platelets"
TestObject largePlateletsCount = new TestObject('largePlateletsCount')
largePlateletsCount.addProperty('xpath', ConditionType.EQUALS, " (//div[contains(@class,'platelet-morph-row')])[1]")

String countText = WebUI.getText(largePlateletsCount).trim()
WebUI.comment("Raw text extracted: '${countText}'")

// Extract only the digits 
String numericPart = countText.replaceAll("[^0-9]", "")
int displayedCount = numericPart.isInteger() ? numericPart.toInteger() : 0

WebUI.comment("Extracted Large Platelets count: ${displayedCount}")


// Define JavaScript code as a single string
// Step 4: Execute JS to count unique image patches
//Handle zero case
if (displayedCount == 0) {
	KeywordUtil.markFailed("ℹ️ Large Platelets count is 0. No patches are available.")
	
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

	// Define a filter to ignore unwanted images
	const isValidPatch = src => {
		if (!src) return false;
		// Add your logic here – customize based on what your patch images contain
		return (
			!src.includes('placeholder') &&
			!src.includes('logo') &&
			!src.includes('loading') &&
			!src.startsWith('data:image') && // base64 embedded images
			!src.endsWith('.svg') // typically UI icons, not patches
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

	getVisibleImageSrcs(); // Final collection

	return {
		count: uniqueImages.size,
		images: [...uniqueImages] // you can log this for verification
	};
})();

'''

def result = WebUI.executeJavaScript(jsCode, null)

// Step 5: Compare and validate
if (result instanceof Map && result.containsKey('error')) {
	WebUI.comment("❌ Error: " + result.error)
} else {
	int imageCount = result.count
	WebUI.comment("✅ Displayed Large Platelets Count: " + displayedCount)
	WebUI.comment("✅ Counted Unique Image Patches: " + imageCount)
	
	if (imageCount == displayedCount) {
		WebUI.comment("✅ Counts match.")
	} else {
		WebUI.comment("❌ Mismatch detected. Expected: ${displayedCount}, Found: ${imageCount}")
		WebUI.verifyEqual(imageCount, displayedCount, FailureHandling.CONTINUE_ON_FAILURE)
	}
}
}