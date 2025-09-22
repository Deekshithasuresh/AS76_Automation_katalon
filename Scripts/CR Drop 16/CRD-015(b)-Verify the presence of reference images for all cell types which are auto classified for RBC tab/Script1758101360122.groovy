import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under review")

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC'), 0)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))


WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 'References')


WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_References'))


// List of cell names to check in dropdown or panel
def rbcShapeCellTypes = [
	"Ovalocytes","Elliptocytes","Teardrop Cells","Fragmented Cells","Target Cells","Echinocytes","Poikilocytosis"]

def driver = DriverFactory.getWebDriver()

for (String cellType : rbcShapeCellTypes) {
	WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/div_dropdown')) // Update path

	TestObject optionObj = new TestObject(cellType)
	optionObj.addProperty(
		"xpath",
		com.kms.katalon.core.testobject.ConditionType.EQUALS,
		"//li[contains(text(), '${cellType}')]"
	)
	WebUI.click(optionObj)
	WebUI.delay(2)

	// Find images for this cell type
	List<WebElement> refImages = driver.findElements(
		By.xpath("//div[contains(@class,'ref-imagees-container')]//img")
	)

	if (refImages.size() > 0) {
		assert refImages.get(0).isDisplayed() : "First ref image for ${cellType} not visible"
		WebUI.comment("${cellType} images check successful, found " + refImages.size())
	} else if(cellType=="Poikilocytosis") {
		// No images found, so check for "No reference image available" message
		boolean msgPresent = WebUI.verifyTextPresent("No reference image available", false)
		assert msgPresent : "'No reference image available' message not shown for ${cellType}"
		WebUI.comment("${cellType}: No reference image, message displayed as expected")
	}
	else {
		KeywordUtil.markFailed("❌ Test case failed because no refernce image")
		
	}
}

TestObject closeBtn = findTestObject('Object Repository/CR_Drop_16/Page_PBS/button_References_close-btn')
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(closeBtn)))
WebUI.back()

WebUI.closeBrowser()

