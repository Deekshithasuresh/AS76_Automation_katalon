import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.List
import java.util.ArrayList
import java.util.Set
import java.util.Collections
import java.util.Arrays

// 0) Login to Reporting
CustomKeywords.'generic.custumFunctions.login'()
WebUI.waitForPageLoad(10)

// 1) Click first “Unassigned” report row
String rowXpath = '(//tr//td//input[@placeholder="Select reviewer" and @value=""]/ancestor::td)[1]/parent::tr'
TestObject row = new TestObject().addProperty('xpath', ConditionType.EQUALS, rowXpath)
WebUI.waitForElementVisible(row, 10)
//WebUI.click(row)

// 2) Open Assigned-To dropdown
TestObject dropdown = new TestObject().addProperty('xpath', ConditionType.EQUALS, rowXpath + '//input')
WebUI.waitForElementClickable(dropdown, 5)
WebUI.click(dropdown)

// 3) Collect reviewers from Reporting
TestObject reportItems = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//ul[@role="listbox"]//li')
List<WebElement> reportEls = WebUiCommonHelper.findWebElements(reportItems, 10)
List<String> reportingReviewers = reportEls.collect { it.getText().trim() }
WebUI.comment("Reporting reviewers: ${reportingReviewers}")
// close dropdown
//WebUI.click(dropdown)

// 4) Open Admin page in a new tab
String adminUrl = 'https://as76-admin.sigtuple.com/login'
WebUI.executeJavaScript('window.open(arguments[0], "_blank")', Arrays.asList(adminUrl))

// 5) Grab all window handles, switch to Admin tab
Set<String> handles = DriverFactory.getWebDriver().getWindowHandles()
List<String> tabs = new ArrayList<>(handles)
WebUI.switchToWindowIndex(tabs.size() - 1)
WebUI.waitForPageLoad(10)

// 6) Admin login
//WebUI.setText(findTestObject('Object Repository/Page_Admin_Login/input_Username_loginId'), 'adminUser')
//WebUI.setEncryptedText(findTestObject('Object Repository/Page_Admin_Login/input_Password_loginPassword'), 'adminEncryptedPass')
//WebUI.click(findTestObject('Object Repository/Page_Admin_Login/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_User'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/users_option'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/img'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/span_Administrator'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/span_Reviewer'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Status'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/span_Active'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Apply'))
WebUI.waitForPageLoad(10)





// 7) Collect reviewers from Admin page
TestObject adminItems = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//table/tbody/tr/td[2]")
List<WebElement> adminEls = WebUiCommonHelper.findWebElements(adminItems, 10)
List<String> adminReviewers = adminEls.collect { it.getText().trim() }
WebUI.comment("Admin reviewers:     ${adminReviewers}")

// 8) Close Admin tab and switch back to Reporting
WebUI.closeWindowIndex(tabs.size() - 1)
WebUI.switchToWindowIndex(0)

// 9) Compare lists (order-insensitive)
Collections.sort(reportingReviewers)
Collections.sort(adminReviewers)
assert reportingReviewers == adminReviewers : 
    "Reviewer lists do not match!\nReporting: ${reportingReviewers}\nAdmin:     ${adminReviewers}"
WebUI.comment('✅ Reviewer lists match exactly.')


