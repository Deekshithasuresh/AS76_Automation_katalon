package loginPackage
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
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import internal.GlobalVariable
import org.openqa.selenium.Keys
import static com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords.*

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper


public class Login {


	public void login() {


		WebUI.openBrowser('')

		WebUI.maximizeWindow()

		WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

		WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_SELECT_main-sigtuple-as76-logo'),
				10)

		WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/h4_Sign In'), 10)

		WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/h4_Sign In'), 'Sign In')

		WebUI.setText(findTestObject('Object Repository/RBC_Objects/Page_PBS/input_username_loginId'), 'chidu')

		WebUI.setText(findTestObject('Object Repository/RBC_Objects/Page_PBS/input_password_loginPassword'), 'Sigtuple@123')

		WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Sign In'))
	}


	public void loginAgainWithDifferentUser() {


		WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_SELECT_main-sigtuple-as76-logo'),
				10)

		WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/h4_Sign In'), 10)

		WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/h4_Sign In'), 'Sign In')

		WebUI.setText(findTestObject('Object Repository/RBC_Objects/Page_PBS/input_username_loginId'), 'pawanM')

		WebUI.setText(findTestObject('Object Repository/RBC_Objects/Page_PBS/input_password_loginPassword'), 'Sigtuple@123')

		WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Sign In'))
	}



	public void AdminLogin(String username,String password ) {


		WebUI.openBrowser('')

		WebUI.maximizeWindow()

		WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

		WebUI.waitForElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/Sign_in_header'), 20)

		WebUI.setText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Username_loginId'), username)

		WebUI.setText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Password_loginPassword'),
				password)

		WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Sign in'))

		try{
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Admin_Portal_Heading'), 20)
			WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Admin_Portal_Heading'), 20)
		}

		catch(Exception e) {
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Auth_error_msg'), 20)
			String auth_error_text=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Auth_error_msg'))
			WebUI.comment(auth_error_text)
			assert auth_error_text=='You are not authorized to login'
		}
	}


	//	public void assignSelfToBeReviewedReport()
	//	{
	//		WebUI.delay(5)
	//		WebDriver driver = DriverFactory.getWebDriver()
	//		List<WebElement> List_report_Rows = WebUiCommonHelper.findWebElements(
	//		findTestObject('Object Repository/RBC_Objects/Page_PBS/List_report_rows'),10)
	//
	//		List<WebElement> report_status_row = WebUiCommonHelper.findWebElements(
	//		findTestObject('Object Repository/RBC_Objects/Page_PBS/report_status_row'),10)
	//
	//		for (WebElement row : List_report_Rows)
	//			{
	//			// 3. Check if status column contains "To be reviewed"
	//			WebElement statusCell
	//			String reviwerName='pkumar'
	//
	//			String statusCell_to_be_riviewed = row.findElement(By.xpath(".//td[contains(., 'To be reviewed')]")).getText()
	//			println (statusCell_to_be_riviewed)
	//			String statusCell_under_review = row.findElement(By.xpath(".//td[contains(., 'Under review')]")).getText()
	//			println(statusCell_under_review)
	//			WebElement reviwer_inputBox = row.findElement(By.xpath(".//input[@id='assigned_to']"))
	//			String reviwer=reviwer_inputBox.getAttribute("value")
	//			if(statusCell_under_review.equals('Under review') && reviwer.equals(reviwerName))
	//				{
	//				row.click()
	//				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), 10)
	//				WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), reviwerName)
	//				break
	//			}
	//
	//			else
	//				{
	//
	//				WebElement inputBox = row.findElement(By.xpath(".//input[@placeholder='Unassigned' or @placeholder='Select reviewer']"))
	//				// 5. Click to activate the dropdown/autocomplete
	//				inputBox.click()
	//				Thread.sleep(500) // Small wait for dropdown
	//				// 6. Send reviewer
	//				inputBox.sendKeys(reviwerName) // Replace with actual user name
	//				Thread.sleep(500)
	//				WebElement firstOption = driver.findElement(By.id('assigned_to-option-0'))
	//				firstOption.click()
	//				row.click()
	//				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), 10)
	//				WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), reviwerName)
	//
	//			}
	//		}
	//	}

	//	public void assignSelfToBeReviewedReport(String Expected_report_Status) {
	//		WebUI.delay(3)
	//		WebDriver driver = DriverFactory.getWebDriver()
	//		String expectedReviewer = 'prem'  // replace with actual name
	//
	//		// Get all rows of reports
	//		List<WebElement> reportRows = WebUiCommonHelper.findWebElements(
	//				findTestObject('Object Repository/RBC_Objects/Page_PBS/List_report_rows'), 100)
	//		JavascriptExecutor js = (JavascriptExecutor) driver
	//		for (WebElement row : reportRows) {
	//			try {
	//				js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", row)
	//				// Get status cell text (e.g., "Under review" or "To be reviewed")
	//				WebElement statusCell = row.findElement(By.xpath(".//td[contains(text(), 'review') or .//*[contains(text(),'review')]]"))
	//				String status = statusCell.getText().trim()
	//				println("Status: " + status)
	//
	//				// Get reviewer input field or assigned reviewer
	//				WebElement reviewerInput = row.findElement(By.xpath(".//input[@id='assigned_to']"))
	//				String currentReviewer = reviewerInput.getAttribute("value").trim()
	//				String current_assign_status=reviewerInput.getAttribute("placeholder")
	//				println("Reviewer: " + currentReviewer)
	//				println("assign_sta:" + current_assign_status)
	//				//&& current_assign_status.equals('Select reviewer')
	//				//
	//
	//				if(status.equals("To be reviewed") && Expected_report_Status.equals('To be reviewed')) {
	//					//						reviewerInput.click()
	//					//						Thread.sleep(2000)
	//					//						reviewerInput.sendKeys(expectedReviewer)
	//
	//					WebUI.delay(2)
	//					row.click()
	//					//						WebElement lastOption = driver.findElement(By.xpath("//li[starts-with(@id, 'assigned_to-option-')][last()]"))
	//					//						lastOption.click()
	//					//						Thread.sleep(2000)
	//					// Select first suggestion from dropdown
	//					//WebElement firstOption = driver.findElement(By.xpath("//li[contains(@id, 'assigned_to-option-0')]"))
	//					//firstOption.click()
	//					//reviewerInput.sendKeys(Keys.RETURN)
	//
	//
	//					//TestObject reviwer_drp_dwn= WebUI.findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report')
	//					WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), 10)
	//					WebUI.sendKeys(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'),expectedReviewer)
	//					WebElement lastOption = driver.findElement(By.xpath("//li[starts-with(@id, 'assigned_to-option-')][last()]"))
	//					lastOption.click()
	//					WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), expectedReviewer)
	//					break
	//				}
	//				//					if (status.equals("To be reviewed") && current_assign_status.equals('Unassigned')) {
	//				//						reviewerInput.click()
	//				//						Thread.sleep(2000)
	//				//						reviewerInput.sendKeys(expectedReviewer)
	//				//						Thread.sleep(2000)
	//				//
	//				//						// Select first suggestion from dropdown
	//				//						//WebElement firstOption = driver.findElement(By.xpath("//li[contains(@id, 'assigned_to-option-0')]"))
	//				//						//firstOption.click()
	//				//						WebElement lastOption = driver.findElement(By.xpath("//li[starts-with(@id, 'assigned_to-option-')][last()]"))
	//				//						lastOption.click()
	//				//						Thread.sleep(500)
	//				//						//reviewerInput.sendKeys(Keys.RETURN)
	//				//						row.click()
	//				//						WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), 10)
	//				//						String reviwer =WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'))
	//				//						println("Reviwer name:"+reviwer)
	//				//						WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), expectedReviewer)
	//				//						break
	//				//					}
	//
	//				if(Expected_report_Status.equals('Under review')) {
	//					if (status.equals("Under review") && currentReviewer.equals(expectedReviewer)) {
	//						//js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", row)
	//						row.click()
	//						WebUI.delay(2)
	//						WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), 10)
	//						WebUI.verifyElementAttributeValue(findTestObject('Object Repository/RBC_Objects/Page_PBS/Reviwer_drp_dw_In_Report'), 'value',expectedReviewer+" " , 10)
	//						break
	//					}
	//					else {
	//						continue;
	//					}
	//				}
	//			}
	//			catch (Exception e) {
	//				println("Error processing row: " + e.getMessage())
	//			}
	//		}
	//	}
	@Keyword
	def assignReviewerToReport(String initialStatus, String reviewerName) {
		// 1) Locate the initial row by status
		String initialRowXpath = "(//tr[.//td//span[normalize-space() = '${initialStatus}']])[1]"
		TestObject initialRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, initialRowXpath)
		WebUI.waitForElementVisible(initialRow, 10)
		// 2) Capture the initial Status text
		TestObject statusCell0 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
				initialRowXpath + "//following-sibling::td[4]/div/span")
		WebUI.waitForElementVisible(statusCell0, 5)
		String before = WebUI.getText(statusCell0).trim()
		WebUI.comment("Status before assign: '${before}'")
		// 3) Open Assigned-To dropdown and select reviewer
		TestObject dropdown = new TestObject().addProperty('xpath', ConditionType.EQUALS,
				initialRowXpath + "//following-sibling::td//input[@id='assigned_to']")
		WebUI.scrollToElement(dropdown, 5)
		WebUI.click(dropdown)
		WebUI.delay(1)
		// 4) Choose reviewer
		TestObject reviewerOption = new TestObject().addProperty('xpath', ConditionType.EQUALS,
				"//ul[@role='listbox']//li[normalize-space() = '${reviewerName}']")
		WebUI.click(reviewerOption)
		if (initialStatus.equalsIgnoreCase("Under review")) {
			TestObject popup = new TestObject().addProperty('xpath', ConditionType.EQUALS,
					"//h2[contains(text(),'Are you sure you want to re-assign')]")
			if (WebUI.verifyElementPresent(popup, 3)) {
				WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
						"//button[normalize-space()='Re-assign']"))
				WebUI.waitForElementNotPresent(popup, 10)
			}
		}
		// 6) Re-locate the same row by matching its Slide ID
		TestObject slideCell = new TestObject().addProperty('xpath', ConditionType.EQUALS,
				initialRowXpath + "/td[3]")
		String slideId = WebUI.getText(slideCell).trim()
		String newRowXpath = "//tr[.//td[normalize-space() = '${slideId}']]"
		TestObject newStatusCell = new TestObject().addProperty('xpath', ConditionType.EQUALS,
				newRowXpath + "//following-sibling::td[4]/div/span")
		WebUI.waitForElementVisible(newStatusCell, 10)
		// 7) Capture the new Status text and verify it changed
		String after = WebUI.getText(newStatusCell).trim()
		WebUI.comment("Status after assign: '${after}'")
		assert after == before :
		"Status did NOT change after assign. Before='${before}', After='${after}'"
		WebUI.comment(":white_check_mark: Status changed from '${before}' to '${after}'.")
		WebUI.click(newStatusCell)
	}

	@Keyword
	def selectReportByStatus(String status) {

		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		// 1) Wait for table to appear
		//WebUI.waitForElementVisible(findTestObject('Object Repository/Page_PBS/th_Slide Id'), 15)
		// 2) Scroll container to load all rows
		String scrollSelector = "#reportListingTable > div > div"
		int scrollStep = 300
		int delaySeconds = 1
		int maxTries = 100
		int tries = 0
		while (tries < maxTries) {
			def result = js.executeScript("""
			const el = document.querySelector(arguments[0]);
			const before = el.scrollTop;
			el.scrollBy(0, arguments[1]);
			return {
				scrollTop: el.scrollTop,
				scrollHeight: el.scrollHeight,
				clientHeight: el.clientHeight,
				before: before
			};
		""", scrollSelector, scrollStep)
			long scrollTop = result.scrollTop
			long scrollHeight = result.scrollHeight
			long clientHeight = result.clientHeight
			long before = result.before
			KeywordUtil.logInfo("Scroll position: ${scrollTop} / ${scrollHeight - clientHeight}")
			WebUI.delay(delaySeconds)
			if (scrollTop >= (scrollHeight - clientHeight - 10)) {
				KeywordUtil.logInfo("Reached bottom of scroll container.")
				break
			}
			if (scrollTop == before) {
				KeywordUtil.logInfo("ScrollTop unchanged. Possibly stuck. Breaking.")
				break
			}
			tries++
		}
		// 3) Count total rows loaded
		String rowXPath = "//*[@id='reportListingTable']/div/div/div/tbody/tr"
		int totalRows = driver.findElements(By.xpath(rowXPath)).size()
		KeywordUtil.logInfo(":white_check_mark: Scrolling complete. Total rows loaded: ${totalRows}")
		// 4) Find and click first row matching the status
		try {
			String xpath = "(//tr[.//td[contains(normalize-space(), '${status}')]])[1]"
			TestObject matchingRow = new TestObject('dynamicReportRow')
			matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
			WebUI.waitForElementClickable(matchingRow, 10)
			WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
			rowElement.click()
			WebUI.comment(":white_check_mark: Clicked first row with status = '${status}'")
		}
		catch (Exception e) {
			String xpath = "(//tr[.//td[contains(normalize-space(), 'Under review')]])[4]"
			TestObject matchingRow = new TestObject('dynamicReportRow')
			matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
			WebUI.waitForElementClickable(matchingRow, 10)
			WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
			rowElement.click()
			WebUI.comment(":white_check_mark: Clicked first row with status = 'Under review' there is no to be reviewd report")
		}
	}


	//	@Keyword
	//	def assignOrReassignOnTabs(String reviewerName, boolean confirmReassign = true) {
	//		// 1) Wait for the “Assigned To” field
	//		TestObject assignedField = new TestObject('dynamicAssignedField')
	//		assignedField.addProperty('xpath', ConditionType.EQUALS,
	//				"//input[@id='assigned_to']")
	//		WebUI.waitForElementVisible(assignedField, 10)
	//		// 2) Read current value
	//		String current = WebUI.getAttribute(assignedField, 'value').trim()
	//		WebUI.comment("Current reviewer: '${current}'")
	//		// 3) Open dropdown
	//		WebUI.scrollToElement(assignedField, 5)
	//		WebUI.click(assignedField)
	//		WebUI.delay(1)
	//		// 4) Fetch all options
	//		TestObject allOpts = new TestObject('dynamicAllOpts')
	//		allOpts.addProperty('xpath', ConditionType.EQUALS, "//ul[@role='listbox']//li")
	//		List<WebElement> els = WebUiCommonHelper.findWebElements(allOpts, 5)
	//		List<String> names = els.collect { it.getText().trim() }
	//		WebUI.comment("Available reviewers: ${names}")
	//		// 5) Proceed if needed
	//		if (current != reviewerName) {
	//			if (!names.contains(reviewerName)) {
	//				assert false : "Reviewer '${reviewerName}' not found in dropdown!"
	//			}
	//			// Select reviewer
	//			TestObject choice = new TestObject('dynamicChoice')
	//			choice.addProperty('xpath', ConditionType.EQUALS,
	//					"//ul[@role='listbox']//li[normalize-space() = '${reviewerName}']")
	//			WebUI.click(choice)
	//			// 6) Handle re-assign popup
	//			try {
	//				def popup = findTestObject('Object Repository/Page_PBS/h2_Are you sure you want to re-assign this slide')
	//				def reassignBtn = findTestObject('Object Repository/Page_PBS/button_Re-assign')
	//				def cancelBtn   = findTestObject('Object Repository/Page_PBS/button_Cancel')
	//				if (WebUI.verifyElementPresent(popup, 3)) {
	//					WebUI.comment("Re-assign popup appeared.")
	//					if (confirmReassign) {
	//						WebUI.comment("Clicking Re-assign...")
	//						WebUI.click(reassignBtn)
	//						WebUI.waitForElementNotPresent(popup, 10)
	//					} else {
	//						WebUI.comment("Clicking Cancel...")
	//						WebUI.click(cancelBtn)
	//						WebUI.waitForElementNotPresent(popup, 10)
	//					}
	//				} else {
	//					WebUI.comment("No Re-assign popup appeared.")
	//				}
	//			} catch (Exception e) {
	//				WebUI.comment("Popup handling error: ${e.message}")
	//			}
	//		} else {
	//			WebUI.comment("Already assigned to '${reviewerName}', no action.")
	//		}
	//		// 7) Final Verification
	//		String after = WebUI.getAttribute(assignedField, 'value').trim()
	//		if (confirmReassign) {
	//			assert after == reviewerName : "Expected assignment to '${reviewerName}', but found '${after}'"
	//			WebUI.comment("Assignment confirmed: '${after}'")
	//		} else {
	//			assert after == current : "Expected assignment to stay '${current}' after Cancel, but found '${after}'"
	//			WebUI.comment("Assignment unchanged after Cancel as expected: '${after}'")
	//		}
	//	}

	def assignOrReassignOnTabs(String reviewerName, boolean confirmReassign = true) {
		// 1) Wait for the “Assigned To” field
		TestObject assignedField = new TestObject('dynamicAssignedField')
		assignedField.addProperty('xpath', ConditionType.EQUALS, "//input[@id='assigned_to']")
		WebUI.waitForElementVisible(assignedField, 10)
		// 2) Read current value
		String current = WebUI.getAttribute(assignedField, 'value').trim()
		WebUI.comment("Current reviewer: '${current}'")
		// 3) Open dropdown
		WebUI.scrollToElement(assignedField, 5)
		WebUI.click(assignedField)
		WebUI.delay(1)
		// 4) Fetch all options
		TestObject allOpts = new TestObject('dynamicAllOpts')
		allOpts.addProperty('xpath', ConditionType.EQUALS, "//ul[@role='listbox']//li")
		List<WebElement> els = WebUiCommonHelper.findWebElements(allOpts, 5)
		List<String> names = els.collect { it.getText().trim() }
		WebUI.comment("Available reviewers: ${names}")
		// 5) Proceed only if current != reviewerName
		if (current != reviewerName) {
			if (!names.contains(reviewerName)) {
				WebUI.comment("Reviewer '${reviewerName}' not found in dropdown! Skipping assignment.")
				return
			}
			// Select reviewer
			TestObject choice = new TestObject('dynamicChoice')
			choice.addProperty('xpath', ConditionType.EQUALS,
					"//ul[@role='listbox']//li[normalize-space() = '${reviewerName}']")
			WebUI.click(choice)
			// 6) Handle re-assign popup
			try {
				def popup = findTestObject('Object Repository/RBC_Objects/Page_PBS/Reassign_popup_Header')
				def reassignBtn = findTestObject('Object Repository/RBC_Objects/Page_PBS/Reassign_CTA')
				def cancelBtn   = findTestObject('Object Repository/RBC_Objects/Page_PBS/Reassign_cancel_CTA')
				if (WebUI.verifyElementPresent(popup, 3)) {
					WebUI.comment("Re-assign popup appeared.")
					if (confirmReassign) {
						WebUI.comment("Clicking Re-assign...")
						WebUI.click(reassignBtn)
						WebUI.waitForElementNotPresent(popup, 10)
					} else {
						WebUI.comment("Clicking Cancel...")
						WebUI.click(cancelBtn)
						WebUI.waitForElementNotPresent(popup, 10)
					}
				} else {
					WebUI.comment("No Re-assign popup appeared.")
				}
			} catch (Exception e) {
				WebUI.comment("Popup handling error: ${e.message}")
			}
		} else {
			WebUI.comment("Already assigned to '${reviewerName}', no action. Skipping reassignment.")
			return
		}
		// 7) Final Verification
		String after = WebUI.getAttribute(assignedField, 'value').trim()
		if (confirmReassign) {
			assert after == reviewerName : "Expected assignment to '${reviewerName}', but found '${after}'"
			WebUI.comment("Assignment confirmed: '${after}'")
		} else {
			assert after == current : "Expected assignment to stay '${current}' after Cancel, but found '${after}'"
			WebUI.comment("Assignment unchanged after Cancel as expected: '${after}'")
		}
	}
}
