import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.waitForElementPresent(findTestObject('retain_patchs/span_WBC'), 20)
WebUI.click(findTestObject('retain_patchs/span_WBC'))

def driver = DriverFactory.getWebDriver()

// 1. Get all patch thumbnail divs
List<WebElement> patchDivs = driver.findElements(By.xpath("//div[@class='Card patches-container']/div"))
assert patchDivs.size() > 0 : "No patches found!"

// 2. Get src of first image inside first div patch container
WebElement firstImg = patchDivs[0].findElement(By.tagName("img"))
String firstPatchSrc = firstImg.getAttribute("src")

// 3. Click Split View
WebUI.click(findTestObject('retain_patchs/img_Platelets_split-view'))
WebUI.delay(3)

// 4. Verify first patch focused (class check)
List<WebElement> splitViewPatchDivs = driver.findElements(By.xpath("//div[@class='Card patches-container']/div"))
boolean firstPatchFocused = splitViewPatchDivs[0].getAttribute("class").contains("focused-patch")

assert firstPatchFocused : "First patch is not focused after switching to Split View!"
WebUI.comment("✅ Verified: First patch is focused after Split View")

// Switch to Patch View
WebUI.click(findTestObject('retain_patchs/img_Platelets_patch-view'))
WebUI.delay(3)

// 6. Verify first patch focused after switching back
List<WebElement> patchViewDivs = driver.findElements(By.xpath("//div[@class='Card patches-container']/div"))
assert patchViewDivs.size() > 0 : "No patch thumbnails found after switching to Patch View!"

boolean firstPatchFocusedPatchView = patchViewDivs[0].getAttribute("class").contains("patch-focus-mode")

assert firstPatchFocusedPatchView : "First patch is not focused after switching to Patch View!"
WebUI.comment("✅ First patch is focused after switching back to Patch View.")
