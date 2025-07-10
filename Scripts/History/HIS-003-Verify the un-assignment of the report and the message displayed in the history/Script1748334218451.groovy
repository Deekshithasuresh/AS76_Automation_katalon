import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.model.FailureHandling

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/Page_PBS/button_X_unassign'))

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/Page_PBS/button_Unassign'))




// 6) OPEN KEBAB MENU & CHOOSE “History”
TestObject kebabTO = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.click(kebabTO)
TestObject historyTO = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//li//span[normalize-space()='History']"
)
WebUI.waitForElementClickable(historyTO, 5)
WebUI.click(historyTO)
WebUI.delay(2)

TestObject reportAssign = new TestObject().addProperty(
	"xpath", ConditionType.EQUALS,
	"(//h4[normalize-space(text())='Report assignment']/ancestor::li)[1]"
)

WebUI.waitForElementVisible(reportAssign, 10)

String fullText = WebUI.getText(reportAssign)
WebUI.comment("Full assignment row text:\n$fullText")

assert fullText.contains("Report assignment") : "❌ 'Report assignment' not found"
assert fullText.contains("admin → manju") : "❌ Assignee flow 'admin → manju' not found"
assert fullText.contains("manju assigned the report") : "❌ Description missing"
