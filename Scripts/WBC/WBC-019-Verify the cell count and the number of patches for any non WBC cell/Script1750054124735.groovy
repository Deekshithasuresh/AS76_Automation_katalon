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
import org.openqa.selenium.By
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_To be reviewed'))
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_WBC'))
WebUI.verifyElementVisible(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_WBC'))
WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_WBC'), 'WBC')
	import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
	import com.kms.katalon.core.testobject.TestObject
	import com.kms.katalon.core.testobject.ConditionType
	
	// Create a dynamic TestObject with XPath where text equals "Monocytes"
	TestObject monocytesElement = new TestObject('dynamicMonocytes')
	monocytesElement.addProperty('xpath', ConditionType.EQUALS, "//*[text()='Rejected']")
	
	// Click the element
	WebUI.click(monocytesElement)
	
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
// Ensure the page is loaded
WebUI.waitForPageLoad(10)
// Define JavaScript code as a single string
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
// Execute JavaScript and retrieve result
def result = WebUI.executeJavaScript(jsCode, null)
if (result instanceof Map && result.containsKey('error')) {
	WebUI.comment("❌ Error: " + result.error)
} else {
	WebUI.comment("✅ Total unique images loaded: " + result.count)
	WebUI.comment("🖼️ Unique image URLs: " + result.images)
}


WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> WBC_cellname_row=driver.findElements(By.xpath("//table/tbody/tr"))
int cell_count1
for(WebElement row:WBC_cellname_row)
	{
		List<WebElement> cells=row.findElements(By.tagName("td"))
		String cellname=(cells[0]).getText()
		println(cellname)
		if (cellname.equals('Rejected'))
		{
		String cell_count=(cells[1]).getText()
		cell_count1=Integer.parseInt(cell_count)
		println(cell_count1)
		
	}
	}
	assert result.count == cell_count1 : "❌ Image count (${result.count}) does not match neutrophil count from table (${cell_count1})"
	
	//assert ((result.count)==(cell_count1)): "failed in assertion"

