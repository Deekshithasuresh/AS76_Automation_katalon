import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot
import java.awt.event.KeyEvent

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable


// Helper method to generate a random alphabetic string
String generateRandomAlphaString(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    StringBuilder sb = new StringBuilder()
    Random rand = new Random()
    for (int i = 0; i < length; i++) {
        sb.append(chars.charAt(rand.nextInt(chars.length())))
    }
    return sb.toString()
}

// Generate user credentials
String randomName = "user" + generateRandomAlphaString(5)
String randomEmail = randomName + "@gmail.com"
String randomUsername = randomName
String password = "Sigtuple@123"
String secondName="user" + generateRandomAlphaString(5)
String secondEmail = randomName + "@gmail.com"
String secoundUsername = randomName

// Step 1: Open Admin Console and Login
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Username_loginId'), 'manju')
WebUI.setEncryptedText(findTestObject('Object Repository/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Sign in'))

// Step 2: Create a new reviewer user
WebUI.click(findTestObject('Object Repository/Page_Admin Console/div_User'))
WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Users'))
WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Create User'))

WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Name_rbc-input-box'), randomName)
WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Email_rbc-input-box error'), randomEmail)
WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Username_rbc-input-box'), randomUsername)
WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Password_rbc-input-box'), password)
WebUI.click(findTestObject('Object Repository/Page_Admin Console/input_Reviewer_userRole'))
WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/button_Create new user'), 'Create new user')
WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Create new user'))
WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/button_Create and copy'), 'Create and copy')
WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Create and copy'))
WebUI.click(findTestObject('Object Repository/Page_Admin Console/profile_img'))
WebUI.click(findTestObject('Object Repository/Page_Admin Console/li_Logout (1)'))



// Step 3: Save credentials in Global Variables (optional)
GlobalVariable.generatedUsername = randomUsername
GlobalVariable.generatedPassword = password
GlobalVariable.generatedEmail = randomEmail

// Step 4: Open PBS portal in new tab and login
WebUI.executeJavaScript("window.open('https://as76-pbs.sigtuple.com/login','_blank');", null)
WebUI.switchToWindowIndex(1)

WebUI.setText(findTestObject('Object Repository/Login_page/input_username_loginId'), randomUsername)
WebUI.setText(findTestObject('Object Repository/Login_page/input_password_loginPassword'), password)
WebUI.click(findTestObject('Object Repository/Login_page/button_Sign In'))
WebUI.waitForPageLoad(10)

WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_PBS/button_I Accept'))

WebUI.setText(findTestObject('Object Repository/Page_Admin Console/Page_PBS/input_New password_new-password'), password)
WebUI.setText(findTestObject('Object Repository/Page_Admin Console/Page_PBS/input_Confirm password_confirm-password'), password)

WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_PBS/button_Confirm password'))

WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_PBS/button_Sign in_confirm'))


WebUI.setText(findTestObject('Object Repository/Login_page/input_username_loginId'), randomUsername)
WebUI.setText(findTestObject('Object Repository/Login_page/input_password_loginPassword'), password)
WebUI.click(findTestObject('Object Repository/Login_page/button_Sign In'))
WebUI.waitForPageLoad(10)



CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'(randomUsername, true)


String SlideIDXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/span[1]"

TestObject assignedSlideID = new TestObject('assignedSlideID')
assignedSlideID.addProperty('xpath', ConditionType.EQUALS, SlideIDXpath)

String slideID = WebUI.getText(assignedSlideID)
WebUI.comment("Slide ID is: " + slideID)



TestObject slideidBtn = findTestObject('Object Repository/WBC_m/Page_PBS/Slide_icon')
WebElement SlideElement = WebUiCommonHelper.findWebElement(slideidBtn, 10)

JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
js.executeScript("arguments[0].click();", SlideElement)

WebUI.delay(10)
//String scandateXpath="//div[@id='specimen-info']/div[3]/div/div[2]/div[2]/div[2]/div"

//TestObject scanDate = new TestObject('scanDate')
//scanDate.addProperty('xpath', ConditionType.EQUALS, scandateXpath)


WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/ScanDate_in_image'),30)
// Get the slide scanned time text
String scanDateBeforeApproval = WebUI.getText(findTestObject('Object Repository/WBC_m/Page_PBS/ScanDate_in_image'))
WebUI.refresh()
println "Scan Date before Approval: " + scanDateBeforeApproval



WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_RBC Morphology'), 'RBC Morphology')

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'),
	0)

def rbcTextfirst = 'ABC123@#%^&*def456'

WebUI.waitForElementVisible(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'),
   10)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'))

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'), rbcTextfirst)
WebUI.delay(5)
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'))




//WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/back_button'))
 WebUI.back()
 WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/PBS_profile_icon'))

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/li_Logout_report'))

WebUI.switchToWindowIndex(0)

WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Username_loginId'), 'manju')
WebUI.setEncryptedText(findTestObject('Object Repository/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Page_Admin Console/div_User'))
WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Users'))

WebUI.setText(findTestObject('Object Repository/Page_Admin Console/Page_Admin Console/search_field'), randomUsername)

Robot robot = new Robot()
robot.delay(500)  // small delay to ensure focus is set
robot.keyPress(KeyEvent.VK_ENTER)
robot.keyRelease(KeyEvent.VK_ENTER)


TestObject firstUserRow = new TestObject()
firstUserRow.addProperty("xpath", ConditionType.EQUALS, "(//tr[contains(@class, 'MuiTableRow-root')])[2]")

// Click the first row
WebUI.click(firstUserRow)



WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_Admin Console/button_Delete_user_delete'))

WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_Admin Console/button_Yes_delet_user'))
//WebUI.refresh()



// Define TestObject pointing to the table rows
TestObject rowObject = new TestObject()
rowObject.addProperty("xpath", ConditionType.EQUALS, "//table/tbody//tr")

// Get all rows
List<WebElement> rows = WebUiCommonHelper.findWebElements(rowObject, 10)

if (rows.size() == 0) {
	KeywordUtil.markFailed("❌ No rows found in the table.")
} else {
	// Get the first row   
	 WebElement firstRow = rows[0]

    // Extract 5th column (adjust index if needed)
    WebElement statusElement = firstRow.findElement(By.xpath("./td[4]"))

    String statusTextone = statusElement.getText().trim()

    // Fail test case immediately if not "Inactive"
    assert statusTextone == 'Inactive' : "❌ First row status is not Inactive. Found: " + statusTextone

    println("✅ First row status is Inactive.")
}


WebUI.delay(5)
WebUI.refresh()


WebUI.click(findTestObject('Object Repository/Page_Admin Console/profile_img'))
WebUI.click(findTestObject('Object Repository/Page_Admin Console/li_Logout (1)'))

WebUI.switchToWindowIndex(1)

WebUI.refresh()
WebUI.waitForPageLoad(10)
WebUI.setText(findTestObject('Object Repository/Login_page/input_username_loginId'), 'manju')
WebUI.setEncryptedText(findTestObject('Object Repository/Login_page/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Login_page/button_Sign In'))
WebUI.waitForPageLoad(10)

TestObject searchBox = findTestObject("Object Repository/Report_Listing/Page_PBS/search_input_text")


WebUI.setText(searchBox, slideID)
WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
WebUI.delay(5)


// 1. Define the first row of the report table
//TestObject tableRow = new TestObject().addProperty("xpath", ConditionType.EQUALS,
//	"//tbody[contains(@class,'MuiTableBody-root')]/tr")
//List<WebElement> results = WebUiCommonHelper.findWebElements(tableRow, 5)
//
//if (results.size() > 0) {
//	WebElement firstRow = results.get(0)
//	List<WebElement> columns = firstRow.findElements(By.tagName("td"))
//
//	String scanDateTextAfterAproval = columns.get(3).getText().trim()
//	String status = columns.get(4).getText().trim()
//
//	println("🔁 Scan Date after : " + scanDateTextAfterAproval)
//	println("📄 Status after : " + status)
//	
//	
//	if (scanDateBeforeApproval.equals(scanDateTextAfterAproval)) {
//		if (status.equalsIgnoreCase("To be reviewed")) {
//			println("✅ Report with same scan date is approved successfully.")
//		} else {
//			println("❌ Status mismatch. Expected 'To be reviewed' but found: " + status)
//		}
//	} else {
//		println("❌ Scan date mismatch: Before = ${scanDateBeforeApproval}, After = ${scanDateTextAfterAproval}")
//	}
//}
	
	WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/PBS_profile_icon'))
	
	WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/li_Logout_report'))
	
	WebUI.switchToWindowIndex(0)
	
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Username_loginId'), 'manju')
	WebUI.setEncryptedText(findTestObject('Object Repository/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Sign in'))
	
	
	// Step 2: Create a new reviewer user
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/div_User'))
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Users'))
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Create User'))
	
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Name_rbc-input-box'), secondName)
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Email_rbc-input-box error'), secondEmail)
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Username_rbc-input-box'), secondName)
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Password_rbc-input-box'), password)
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/input_Reviewer_userRole'))
	WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/button_Create new user'), 'Create new user')
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Create new user'))
	WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/button_Create and copy'), 'Create and copy')
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Create and copy'))
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/profile_img'))
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/li_Logout (1)'))
	
	//WebUI.executeJavaScript("window.open('https://as76-pbs.sigtuple.com/login','_blank');", null)
	WebUI.switchToWindowIndex(1)
	
	WebUI.setText(findTestObject('Object Repository/Login_page/input_username_loginId'), secondName)
	WebUI.setText(findTestObject('Object Repository/Login_page/input_password_loginPassword'), password)
	WebUI.click(findTestObject('Object Repository/Login_page/button_Sign In'))
	WebUI.waitForPageLoad(10)
	
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_PBS/button_I Accept'))
	
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/Page_PBS/input_New password_new-password'), password)
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/Page_PBS/input_Confirm password_confirm-password'), password)
	
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_PBS/button_Confirm password'))
	
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_PBS/button_Sign in_confirm'))
	
	
	WebUI.setText(findTestObject('Object Repository/Login_page/input_username_loginId'), secondName)
	WebUI.setText(findTestObject('Object Repository/Login_page/input_password_loginPassword'), password)
	WebUI.click(findTestObject('Object Repository/Login_page/button_Sign In'))
	WebUI.waitForPageLoad(10)
	
	String rowXpath="//tr/td[text()='${slideID}']/following-sibling::td/div[text()='${scanDateBeforeApproval}']/parent::td/parent::tr"
	
	TestObject sameOldREportRow = new TestObject()
	sameOldREportRow.addProperty("xpath", ConditionType.EQUALS, rowXpath)
	
	// Click the first row
	WebUI.verifyElementPresent(sameOldREportRow, 15)	
	WebUI.click(sameOldREportRow)
	
	WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')
	
	CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'(secondName, true)
	
	
	WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_RBC Morphology'), 'RBC Morphology')
	
	WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'),0)
	
	def rbcTextsecond = 'ABC123@#%^&*def456'
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'),10)
	
	WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'))
	
	WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'), rbcTextsecond)
	WebUI.delay(5)
	WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'))
	
	
	
	//WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/back_button'))
	 WebUI.back()
	 WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/PBS_profile_icon'))
	WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/li_Logout_report'))
	
	WebUI.switchToWindowIndex(0)
	
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/input_Username_loginId'), 'manju')
	WebUI.setEncryptedText(findTestObject('Object Repository/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Sign in'))
	
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/div_User'))
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Users'))
	
	
	//delete second user
	WebUI.setText(findTestObject('Object Repository/Page_Admin Console/Page_Admin Console/search_field'), secondName)
	
	robot.delay(500)  // small delay to ensure focus is set
	robot.keyPress(KeyEvent.VK_ENTER)
	robot.keyRelease(KeyEvent.VK_ENTER)
	// Click the first row
	WebUI.click(firstUserRow)
	WebUI.delay(5)
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_Admin Console/button_Delete_user_delete'))
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/Page_Admin Console/button_Yes_delet_user'))
	WebUI.delay(5)
	//WebUI.refresh()
	
	// Define TestObject pointing to the table rows
	TestObject rowObjectsecond = new TestObject()
	rowObjectsecond.addProperty("xpath", ConditionType.EQUALS, "//table/tbody//tr")
	
	// Get all rows
	List<WebElement> rowsSecound = WebUiCommonHelper.findWebElements(rowObjectsecond, 10)
	
if (rowsSecound.size() == 0) {
	KeywordUtil.markFailed("❌ No rows found in the table.")
} else {
	    WebElement firstRow = rowsSecound[0]

    // Extract 5th column (adjust index if needed)
    WebElement statusElementsecond = firstRow.findElement(By.xpath("./td[4]"))

    String statusTextsecound = statusElementsecond.getText().trim()

    // Fail test case immediately if not "Inactive"
    assert statusTextsecound == 'Inactive' : "❌ First row status is not Inactive. Found: " + statusTextsecound

    println("✅ First row status is Inactive.")
}	
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/profile_img'))
	WebUI.click(findTestObject('Object Repository/Page_Admin Console/li_Logout (1)'))
	
	
	
	
	WebUI.switchToWindowIndex(1)
	WebUI.refresh()
	WebUI.waitForPageLoad(10)
	WebUI.setText(findTestObject('Object Repository/Login_page/input_username_loginId'), 'manju')
	WebUI.setEncryptedText(findTestObject('Object Repository/Login_page/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
	WebUI.click(findTestObject('Object Repository/Login_page/button_Sign In'))
	WebUI.waitForPageLoad(10)
	
	//TestObject searchBox = findTestObject("Object Repository/Report_Listing/Page_PBS/search_input_text")
	
	
	WebUI.setText(searchBox, slideID)
	WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
	WebUI.delay(5)
	
	
	// 1. Define the first row of the report table
//	TestObject tableRow = new TestObject().addProperty("xpath", ConditionType.EQUALS,
//		"//tbody[contains(@class,'MuiTableBody-root')]/tr")
//	List<WebElement> results = WebUiCommonHelper.findWebElements(tableRow, 5)
	
//	if (results.size() > 0) {
//		WebElement firstRow = results.get(0)
//		List<WebElement> columns = firstRow.findElements(By.tagName("td"))
//	
//		String scanDateTextAfterAproval = columns.get(3).getText().trim()
//		String status = columns.get(4).getText().trim()
//	
//		println("🔁 Scan Date after : " + scanDateTextAfterAproval)
//		println("📄 Status after : " + status)
//		
//		
//		if (scanDateBeforeApproval.equals(scanDateTextAfterAproval)) {
//			if (status.equalsIgnoreCase("To be reviewed")) {
//				println("✅ Report with same scan date is approved successfully.")
//			} else {
//				println("❌ Status mismatch. Expected 'To be reviewed' but found: " + status)
//			}
//		} else {
//			println("❌ Scan date mismatch: Before = ${scanDateBeforeApproval}, After = ${scanDateTextAfterAproval}")
//		}
//	}
	
	WebUI.verifyElementPresent(sameOldREportRow, 15)
	WebUI.click(sameOldREportRow)
	WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'), rbcTextsecond)
	
	
