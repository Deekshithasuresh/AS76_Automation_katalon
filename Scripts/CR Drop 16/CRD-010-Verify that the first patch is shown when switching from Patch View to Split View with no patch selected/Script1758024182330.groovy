import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



// ---------- Test Flow ----------
CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

// Navigate to WBC tab
WebUI.waitForElementPresent(findTestObject('retain_patchs/span_WBC'), 20)
WebUI.click(findTestObject('retain_patchs/span_WBC'))

// Init driver + helper
def driver = DriverFactory.getWebDriver()

// 1. Get all patch thumbnails (imgs)
List<WebElement> patches = driver.findElements(By.xpath("//div[@class='Card patches-container']/div"))
assert patches.size() > 0 : "No patches found!"

// 2. (Optional) Save first patch's src
String firstPatchSrc = patches[0].getAttribute("src")

// Switch to Split View
WebUI.click(findTestObject('retain_patchs/img_Platelets_split-view'))
WebUI.delay(3)

// 4. Check if the first patch is visually focused (e.g., by borders, extra class)
// For example, the focused patch container might have a class like 'patch-focus-mode'
List<WebElement> patchContainers = driver.findElements(By.xpath("//div[@class='Card patches-container']/div"))
boolean firstPatchFocused = patchContainers[0].getAttribute("class").contains("focused-patch")
assert firstPatchFocused : "First patch is not focused after switching to Split View!"

WebUI.comment("✅ Verified: First patch remains focused (thumbnail highlight/overlay) after Split View is activated.")
