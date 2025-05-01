package generic

import static com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords.*
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper



public class custumFunctions {

	@Keyword
	def login() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
		WebUI.setText(findTestObject('Object Repository/Login_page/input_username_loginId'), 'manju')
		WebUI.setEncryptedText(findTestObject('Object Repository/Login_page/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
		WebUI.click(findTestObject('Object Repository/Login_page/button_Sign In'))
		WebUI.waitForPageLoad(10)
	}

	@Keyword
	def selectReportByStatus(String status) {
		// 1) Wait for the table (or any container) to be visible
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_PBS/th_Slide Id'), 15)

		// 2) Build a dynamic TestObject targeting the FIRST row matching that status
		String xpath = "(" +"//tr[.//td[contains(normalize-space(), '${status}')]]" +")[1]"
		TestObject firstMatchingRow = new TestObject('dynamicReportRow')
				.addProperty('xpath', ConditionType.EQUALS, xpath)

		// 3) Scroll to ensure it is in view
		WebUI.scrollToElement(firstMatchingRow, 10)

		// 4) Click it
		WebUI.click(firstMatchingRow)

		WebUI.comment("Selected first report with status = '${status}'")
	}

	 @Keyword
    def assignReviewerToReport(String initialStatus, String reviewerName) {
        // 1) Locate the initial row by status
        String initialRowXpath = "(//tr[.//td//span[normalize-space() = '${initialStatus}']])[1]"
        TestObject initialRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, initialRowXpath)
        WebUI.waitForElementVisible(initialRow, 10)

        // 2) Capture the initial Status text
        TestObject statusCell0 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
            initialRowXpath + "//following-sibling::td[2]//span")
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

        // 5) Handle reassign popup if present
        TestObject popup = new TestObject().addProperty('xpath', ConditionType.EQUALS,
            "//h2[contains(text(),'Are you sure you want to re-assign')]")
        if (WebUI.verifyElementPresent(popup, 3)) {
            WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
                "//button[normalize-space()='Re-assign']"))
            WebUI.waitForElementNotPresent(popup, 10)
        }

        // 6) Re-locate the same row by matching its Slide ID
        TestObject slideCell = new TestObject().addProperty('xpath', ConditionType.EQUALS,
            initialRowXpath + "/td[3]")
        String slideId = WebUI.getText(slideCell).trim()

        String newRowXpath = "//tr[.//td[normalize-space() = '${slideId}']]"
        TestObject newStatusCell = new TestObject().addProperty('xpath', ConditionType.EQUALS,
            newRowXpath + "//following-sibling::td[2]//span")
        WebUI.waitForElementVisible(newStatusCell, 10)

        // 7) Capture the new Status text and verify it changed
        String after = WebUI.getText(newStatusCell).trim()
        WebUI.comment("Status after assign: '${after}'")
        assert after == before : 
            "Status did NOT change after assign. Before='${before}', After='${after}'"
        WebUI.comment("✅ Status changed from '${before}' to '${after}'.")
    }


	@Keyword
	def verifyCancelButtonDuringReassign(String reportStatus, String originalReviewer) {

		// Step 1: Wait for table
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_PBS/th_Slide Id'), 10)

		// Step 2: Find report by status
		String reportRowXpath = "(//tr[.//td//span[contains(text(), '${reportStatus}')]])[1]"
		TestObject reportRow = new TestObject('dynamicReportRow')
		reportRow.addProperty('xpath', ConditionType.EQUALS, reportRowXpath)

		// Step 3: Capture current assigned reviewer
		String assignedReviewerXpath = reportRowXpath + "//following-sibling::td//input[@id='assigned_to']"
		TestObject assignedReviewerInput = new TestObject('dynamicAssignedReviewer')
		assignedReviewerInput.addProperty('xpath', ConditionType.EQUALS, assignedReviewerXpath)

		String currentReviewer = WebUI.getAttribute(assignedReviewerInput, 'value').trim()
		WebUI.comment("Current Reviewer: " + currentReviewer)

		// Step 4: Click on Assigned To dropdown
		WebUI.scrollToElement(assignedReviewerInput, 5)
		WebUI.click(assignedReviewerInput)

		// Step 5: Select a different reviewer (not original)
		String newReviewerXpath = "//ul//li[normalize-space(text())!='${originalReviewer}'][1]"  // pick first different reviewer
		TestObject newReviewerOption = new TestObject('dynamicNewReviewerOption')
		newReviewerOption.addProperty('xpath', ConditionType.EQUALS, newReviewerXpath)

		WebUI.click(newReviewerOption)

		// Step 6: Wait for popup and click Cancel
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PBS/h2_Are you sure you want to re-assign this slide'), 5)) {
			WebUI.comment('Reassign confirmation popup appeared.')

			WebUI.click(findTestObject('Object Repository/Page_PBS/button_Cancel'))  // Click Cancel button
			WebUI.comment('Clicked Cancel on the reassign popup.')
		} else {
			assert false : "Reassign popup did not appear."
		}

		// Step 7: Verify reviewer is still original (no change)
		WebUI.delay(1)  // wait for cancel to complete

		String reviewerAfterCancel = WebUI.getAttribute(assignedReviewerInput, 'value').trim()
		WebUI.comment("Reviewer after cancel: " + reviewerAfterCancel)

		assert reviewerAfterCancel == currentReviewer : "Reviewer changed after clicking Cancel! Expected: ${currentReviewer}, but found: ${reviewerAfterCancel}"

		WebUI.comment('Cancel button functionality verified successfully.')
	}

	@Keyword
	def assignOrReassignOnTabs(String reviewerName, boolean confirmReassign = true) {
		// 1) Wait for the “Assigned To” field
		TestObject assignedField = new TestObject('dynamicAssignedField')
		assignedField.addProperty('xpath', ConditionType.EQUALS,
				"//input[@id='assigned_to']")
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

		// 5) Proceed if needed
		if (current != reviewerName) {
			if (!names.contains(reviewerName)) {
				assert false : "Reviewer '${reviewerName}' not found in dropdown!"
			}

			// Select reviewer
			TestObject choice = new TestObject('dynamicChoice')
			choice.addProperty('xpath', ConditionType.EQUALS,
					"//ul[@role='listbox']//li[normalize-space() = '${reviewerName}']")
			WebUI.click(choice)

			// 6) Handle re-assign popup
			try {
				def popup = findTestObject('Object Repository/Page_PBS/h2_Are you sure you want to re-assign this slide')
				def reassignBtn = findTestObject('Object Repository/Page_PBS/button_Re-assign')
				def cancelBtn   = findTestObject('Object Repository/Page_PBS/button_Cancel')

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
			WebUI.comment("Already assigned to '${reviewerName}', no action.")
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

	@Keyword
def unassignOrCancel(String reportStatus, boolean confirm) {
    // 1) Locate the first report row by status
    String rowXpath = "(//tr[.//td//span[normalize-space() = '${reportStatus}']])[1]"
    TestObject row = new TestObject('row')
        .addProperty('xpath', ConditionType.EQUALS, rowXpath)
    WebUI.waitForElementVisible(row, 10)

    // 1a) Capture status BEFORE action
    TestObject statusBeforeCell = new TestObject('statusBefore')
        .addProperty('xpath', ConditionType.EQUALS,
            rowXpath + "//following-sibling::td[2]//span")
    WebUI.waitForElementVisible(statusBeforeCell, 5)
    String statusBefore = WebUI.getText(statusBeforeCell).trim()
    WebUI.comment("Status before action: '${statusBefore}'")

    // 2) If status locked, verify clear-icon disabled and exit
    List<String> lockedStatuses = ['Approved', 'Rejected']
    if (lockedStatuses.contains(reportStatus)) {
        TestObject clearBtnLocked = new TestObject('clearBtnLocked')
            .addProperty('xpath', ConditionType.EQUALS,
                rowXpath + "//following-sibling::td//input")
        WebUI.verifyElementNotClickable(clearBtnLocked)
        WebUI.comment("✅ Clear-icon is disabled for status '${reportStatus}'.")
        return
    }

    // 3) Capture Slide ID so we can re-find the row later
    TestObject slideCell = new TestObject('slideCell')
        .addProperty('xpath', ConditionType.EQUALS,
            "(//span[normalize-space() = '${reportStatus}'])[1]/ancestor::tr//td[3]")
    String slideId = WebUI.getText(slideCell).trim()

    // 4) Click the clear-icon (×)
    TestObject clearBtn = new TestObject('clearBtn')
        .addProperty('xpath', ConditionType.EQUALS,
            rowXpath + "//following-sibling::td//button[not(contains(@title,'Open'))]")
    WebUI.click(clearBtn)

    // 5) Handle popup
    TestObject popup = new TestObject('popup')
        .addProperty('xpath', ConditionType.EQUALS,
            "//span[@class='modal-title']")
    WebUI.waitForElementVisible(popup, 5)
    String buttonText = confirm ? 'Unassign' : 'Cancel'
    TestObject actionBtn = new TestObject('actionBtn')
        .addProperty('xpath', ConditionType.EQUALS,
            "//div[contains(@class,'modal-actions')]//button[normalize-space() = '${buttonText}']")
    WebUI.click(actionBtn)
    WebUI.waitForElementNotPresent(popup, 10)

    // 6) Re-locate the row by Slide ID
    String rowById = "//tr[.//td[normalize-space() = '${slideId}']]"
    TestObject statusAfterCell = new TestObject('statusAfter')
        .addProperty('xpath', ConditionType.EQUALS,
            rowById + "//following-sibling::td[2]//span")
    WebUI.waitForElementVisible(statusAfterCell, 5)
    String statusAfter = WebUI.getText(statusAfterCell).trim()
    WebUI.comment("Status after action: '${statusAfter}'")

    // 7) Final outcome checks
    if (confirm) {
        // Unassign: status should change
        assert statusAfter != statusBefore :
            "Expected status to change after Unassign, but remained '${statusAfter}'"
        WebUI.comment("✅ Unassign succeeded: status changed from '${statusBefore}' to '${statusAfter}'.")
    } else {
        // Cancel: status should stay the same
        assert statusAfter == statusBefore :
            "Expected status to remain '${statusBefore}' after Cancel, but was '${statusAfter}'"
        WebUI.comment("✅ Cancel succeeded: status remains '${statusAfter}'.")
    }
}

}
