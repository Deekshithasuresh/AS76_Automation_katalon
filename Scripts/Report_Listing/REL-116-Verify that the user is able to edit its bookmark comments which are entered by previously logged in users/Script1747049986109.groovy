import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType


import internal.GlobalVariable


CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_PBS_icon-img'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'), 'Logout')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'))

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/input_username_loginId'), 'santosh')

WebUI.setEncryptedText(findTestObject('Object Repository/Report_Listing/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Sign In'))


def iconObj = {
	->
	String xpath = "//tr[1]//td[1]//img[@alt='bookmark']"
	new TestObject('bookmarkIcon').addProperty('xpath', ConditionType.EQUALS, xpath)
}

TestObject icon = iconObj()
WebUI.waitForElementVisible(icon, 5)

// helper to read icon src
def isFilled = { -> WebUI.getAttribute(icon, 'src').contains('bookmark-filled') }

// 1) If already filled, remove first so we start from a clean slate
if (isFilled()) {
	WebUI.comment("Icon is already filled; clearing first.")
	WebUI.click(icon)
	WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Confirm'))

}

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/bookmark_icon'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_Additional info_commentPopoverComponent_769bcc'))

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/textarea_hi this is user 2'), 'hi this is user 2')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'))

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/input_username_loginId'), 'manju')

WebUI.setEncryptedText(findTestObject('Object Repository/Report_Listing/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Sign In'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/img_Additional info_commentPopoverComponent_769bcc_1'), 
    0)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_Additional info_commentPopoverComponent_769bcc_1'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/textarea_hi this is user 2'), 'hi this is user 2')

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/textarea_hi this is user 2'), 'edit edit')


WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1_2'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_Additional info_commentPopoverComponent_769bcc_1'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/textarea__commentPopoverComponent_comment-t_ecd7cc'), 
    '')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1_2_3'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1_2_3_4'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Confirm'))

