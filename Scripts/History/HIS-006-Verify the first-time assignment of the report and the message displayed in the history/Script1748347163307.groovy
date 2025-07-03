import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// 4) OPEN HISTORY
TestObject kebabBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu')]]")
WebUI.click(kebabBtn)
TestObject historyOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li")
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)
WebUI.delay(2)

// 5) PICK THE “Assignment” ENTRY
String assignLiXpath = "(//h4[contains(@class,'event-title') and contains(normalize-space(text()),'Report assignment')]/ancestor::li)[1]"
TestObject assignEntry = new TestObject().addProperty('xpath', ConditionType.EQUALS, assignLiXpath)
WebUI.waitForElementVisible(assignEntry, 10)

WebDriver driver = DriverFactory.getWebDriver()
// 6) VERIFY TITLE & DESCRIPTION
WebElement entry = driver.findElement(By.xpath(assignLiXpath))
String title       = entry.findElement(By.cssSelector("h4.event-title")).getText().trim()
String description = entry.findElement(By.cssSelector("div.event-description")).getText().trim()

WebUI.comment("History title: ${title}")
WebUI.comment("History desc : ${description}")

WebUI.verifyMatch(title,       "Report assignment",    false, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyMatch(description, "manju assigned the report", false, FailureHandling.STOP_ON_FAILURE)

WebUI.comment("✅ First-time assignment history verified.")
