import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

// Step 1: Login and navigate to WBC section
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_To be reviewed'))
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_WBC'))
WebUI.verifyElementVisible(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_WBC'))
WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_WBC'), 'WBC')

// Step 2: Click on Neutrophils tab
TestObject neutrophilsElement = new TestObject('dynamicNeutrophils')
neutrophilsElement.addProperty('xpath', ConditionType.EQUALS, "//*[text()='Neutrophils']")
WebUI.click(neutrophilsElement)

// Step 3: Execute JS to count unique images in scroll
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
    const getVisibleImageSrcs = () => {
      const imgs = scrollContainer.querySelectorAll('img');
      imgs.forEach(img => {
        const src = img.getAttribute('src');
        if (src) {
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

// Step 4: Handle scroll result
if (result instanceof Map && result.containsKey('error')) {
	WebUI.comment("❌ Error: " + result.error)
} else {
	WebUI.comment("✅ Total unique images loaded: " + result.count)
	WebUI.comment("🖼️ Unique image URLs: " + result.images)
}

// Step 5: Get neutrophil count from table
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> WBC_rows = driver.findElements(By.xpath("//table/tbody/tr"))

int neutrophilCountFromTable
for (WebElement row : WBC_rows) {
	List<WebElement> cells = row.findElements(By.tagName("td"))
	if (cells.size() >= 2) {
		String cellName = cells[0].getText().trim()
		if (cellName.equalsIgnoreCase('Neutrophils')) {
			String countText = cells[1].getText().trim()
			neutrophilCountFromTable = Integer.parseInt(countText)
			println("📊 Neutrophil count from table: ${neutrophilCountFromTable}")
			break
		}
	}
}

// Step 6: Assertion
assert result.count == neutrophilCountFromTable : "❌ Image count (${result.count}) does not match neutrophil count from table (${neutrophilCountFromTable})"
WebUI.comment("✅ Image count matches Neutrophils count: ${neutrophilCountFromTable}")
