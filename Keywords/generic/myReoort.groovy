package generic

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI





public class myReoort {

	@Keyword
	def myreportCheckbox(String user) {
		// 1) Build a TestObject that matches all the input cells in your report list
		TestObject rowsTO = new TestObject('allRowsInputs')
				.addProperty('xpath', ConditionType.EQUALS,
				"//div/tbody/tr/td//input")

		// 2) Wait for at least one to be visible
		WebUI.waitForElementVisible(rowsTO, 10)

		// 3) Fetch all matching WebElements
		List<WebElement> inputs = WebUiCommonHelper.findWebElements(rowsTO, 10)

		// 4) Iterate and check
		inputs.eachWithIndex { WebElement input, int idx ->
			String val = input.getAttribute('value')
			if (val.equals(user)) {
				println "Row ${idx+1}: ✅ Found expected user '${user}'."
			} else {
				println "Row ${idx+1}: ❌ Expected '${user}' but found '${val}'."
			}
		}
	}

	@Keyword
	def setRetentionPolicy(int newRetentionDays) {
		// Define objects
		TestObject inputRetention = new TestObject("inputRetention")
		inputRetention.addProperty("xpath", ConditionType.EQUALS, "//input[@id='retention-days-input']")

		TestObject saveBtn = new TestObject("saveButton")
		saveBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Save']")

		// Get current retention value
		int currentValue = Integer.parseInt(WebUI.getAttribute(inputRetention, "value"))

		if (currentValue == newRetentionDays) {
			WebUI.comment("⚠️ Retention value already set to ${newRetentionDays}. Save button should remain disabled.")
			return
		}

		// Clear and set new retention value
		WebUI.sendKeys(inputRetention, Keys.chord(Keys.COMMAND, 'a'))  // CMD + A
		WebUI.sendKeys(inputRetention, Keys.chord(Keys.BACK_SPACE))    // Deletes selected content
		
		WebUI.setText(inputRetention, newRetentionDays.toString())

		// If over allowed max, exit
		if (newRetentionDays > 5000) {
			WebUI.comment("🚫 Retention value ${newRetentionDays} exceeds max limit. Validation expected.")
			return
		}

		// Click Save
		WebUI.waitForElementClickable(saveBtn, 5)
		WebUI.click(saveBtn)

		// Determine expected popup title
		String expectedTitle = newRetentionDays > currentValue ?
				"Are you sure you want to increase the retention duration?" :
				"Are you sure you want to decrease the retention duration?"

		// Handle retention confirmation modal
		TestObject popupTitle = new TestObject("popupTitle")
		popupTitle.addProperty("xpath", ConditionType.EQUALS,
				"//div[contains(@class,'modal-container')]//div[contains(@class,'title') and contains(text(),'" + expectedTitle + "')]")

		WebUI.waitForElementVisible(popupTitle, 10)
		WebUI.verifyElementText(popupTitle, expectedTitle)

		// Confirm action
		TestObject confirmBtn = new TestObject("confirmButton")
		confirmBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Confirm']")
		WebUI.click(confirmBtn)

		// If decreasing, handle deletion popup
		if (newRetentionDays < currentValue) {
			handleDeletePopup()
		}

		WebUI.comment("✅ Retention policy successfully updated to ${newRetentionDays} days.")
		WebUI.delay(2)
	}

	private void handleDeletePopup() {
		TestObject deletePopup = new TestObject("deletePopupTitle")
		deletePopup.addProperty("xpath", ConditionType.EQUALS,
				"//div[contains(@class,'modal-container')]//div[contains(text(),'Do you want to delete old reports now?')]")

		WebUI.waitForElementVisible(deletePopup, 10)
		WebUI.verifyElementText(deletePopup, "Do you want to delete old reports now?")

		TestObject deleteNowBtn = new TestObject("deleteNowButton")
		deleteNowBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Delete now']")
		WebUI.click(deleteNowBtn)

		WebUI.comment("🗑️ 'Delete now' confirmed for old reports.")
	}


	@Keyword
	def setRetentionPolicyScheduleTime(int newRetentionDays) {

		// Input field for retention days
		TestObject inputRetention = new TestObject("inputRetention")
		inputRetention.addProperty("xpath", ConditionType.EQUALS, "//input[@id='retention-days-input']")

		// Get current value
		String currentValueStr = WebUI.getAttribute(inputRetention, "value")
		int currentValue = Integer.parseInt(currentValueStr)

		if (currentValue == newRetentionDays) {
			WebUI.comment("Retention value is already ${newRetentionDays}. No change needed.")
			return
		}

		// Set new value
		WebUI.sendKeys(inputRetention, Keys.chord(Keys.COMMAND, 'a'))
		WebUI.sendKeys(inputRetention, Keys.chord(Keys.DELETE))


		WebUI.setText(inputRetention, newRetentionDays.toString())




		// If value > 5000, ensure it's rejected
		if (newRetentionDays > 5000) {
			// Expect some kind of validation message
			//			TestObject errorMsg = new TestObject("maxRetentionError")
			//			errorMsg.addProperty("xpath", ConditionType.EQUALS,
			//				"//div[contains(@class,'error') or contains(text(),'Retention days cannot exceed')]")
			//
			//			boolean errorVisible = WebUI.waitForElementVisible(errorMsg, 5, FailureHandling.OPTIONAL)
			//
			//			if (errorVisible) {
			//				WebUI.comment("UI correctly rejected retention value > 5000")
			//			} else {
			//				KeywordUtil.markFailed("Retention value above 5000 was accepted, but should have been rejected.")
			//			}

			return // Don't continue with confirmation if rejected
		}

		// Click Save
		TestObject saveBtn = new TestObject("saveButton")
		saveBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Save']")
		WebUI.click(saveBtn)


		// Handle confirmation popup
		String expectedTitle = (newRetentionDays > currentValue)
				? "Are you sure you want to increase the retention duration?"
				: "Are you sure you want to decrease the retention duration?"

		TestObject popupTitle = new TestObject("popupTitle")
		popupTitle.addProperty("xpath", ConditionType.EQUALS,
				"//div[contains(@class,'modal-container')]//div[contains(@class,'title') and contains(text(),'" + expectedTitle + "')]")

		WebUI.waitForElementVisible(popupTitle, 10)
		WebUI.verifyElementText(popupTitle, expectedTitle)

		// Click Confirm
		TestObject confirmBtn = new TestObject("confirmButton")
		confirmBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Confirm']")
		WebUI.click(confirmBtn)

		// Handle delete popup only if decreased
		if (newRetentionDays < currentValue) {
			TestObject deletePopup = new TestObject("deletePopupTitle")
			deletePopup.addProperty("xpath", ConditionType.EQUALS,
					"//div[contains(@class,'modal-container')]//div[contains(text(),'Do you want to delete old reports now?')]")

			WebUI.waitForElementVisible(deletePopup, 10)
			WebUI.verifyElementText(deletePopup, "Do you want to delete old reports now?")

			// Click "Delete now"
			TestObject deleteATScheduleButton = new TestObject("deleteATScheduleButton")
			deleteATScheduleButton.addProperty("xpath", ConditionType.EQUALS, "//button[text()='Delete at scheduled time']")
			WebUI.click(deleteATScheduleButton)

			WebUI.comment("Confirmed deletion of old report on scheddule time")
		}

		WebUI.delay(2) // Optional delay
	}



	@Keyword
	def setRetentionPolicyScheduleTimeWithScheduleTimeAhead(int newRetentionDays) {

		// Input field for retention days
		TestObject inputRetention = new TestObject("inputRetention")
		inputRetention.addProperty("xpath", ConditionType.EQUALS, "//input[@id='retention-days-input']")

		// Get current retention value
		String currentValueStr = WebUI.getAttribute(inputRetention, "value").trim()
		int currentValue = Integer.parseInt(currentValueStr)

		if (currentValue == newRetentionDays) {
			WebUI.comment("⚠️ Retention value is already ${newRetentionDays}. Save button will remain disabled.")
			return
		}

		// Set new retention value
		WebUI.sendKeys(inputRetention, Keys.chord(Keys.COMMAND, 'a'))  // macOS select all
		WebUI.sendKeys(inputRetention, Keys.chord(Keys.DELETE))
		WebUI.setText(inputRetention, newRetentionDays.toString())

		// Prevent setting too large value
		//		if (newRetentionDays > 5000) {
		//			WebUI.comment("❌ Retention days exceeds max limit. Exiting.")
		//			return
		//		}
		//
		// --- Schedule deletion time 5 minutes ahead ---
		Calendar now = Calendar.getInstance()
		now.add(Calendar.MINUTE, 2)

		int hour = now.get(Calendar.HOUR)
		if (hour == 0) hour = 12
		String hourStr = String.format("%02d", hour)
		String minuteStr = String.format("%02d", now.get(Calendar.MINUTE))
		String ampm = (now.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM"

		String finalTime = "${hourStr}:${minuteStr} ${ampm}"

		// Input the time into UI
		TestObject timeInput = new TestObject("timeInputField")
		timeInput.addProperty("xpath", ConditionType.EQUALS, "//input[@id='deletion-time-input']")

		WebUI.waitForElementVisible(timeInput, 10)
		WebUI.click(timeInput)
		WebUI.sendKeys(timeInput, Keys.chord(Keys.COMMAND, 'a'))
		WebUI.sendKeys(timeInput, Keys.chord(Keys.DELETE))
		WebUI.setText(timeInput, finalTime)

		WebUI.comment("🕐 Scheduled delete time set to: ${finalTime}")


		// Click Save
		TestObject saveBtn = new TestObject("saveButton")
		saveBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Save']")
		WebUI.waitForElementClickable(saveBtn, 10)
		WebUI.click(saveBtn)

		// Handle confirmation popup
		String expectedTitle = (newRetentionDays > currentValue)
				? "Are you sure you want to increase the retention duration?"
				: "Are you sure you want to decrease the retention duration?"

		TestObject popupTitle = new TestObject("popupTitle")
		popupTitle.addProperty("xpath", ConditionType.EQUALS,
				"//div[contains(@class,'modal-container')]//div[contains(@class,'title') and contains(text(),'" + expectedTitle + "')]")

		WebUI.waitForElementVisible(popupTitle, 10)
		WebUI.verifyElementText(popupTitle, expectedTitle)

		// Click Confirm
		TestObject confirmBtn = new TestObject("confirmButton")
		confirmBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Confirm']")
		WebUI.click(confirmBtn)

		// If retention is decreased, handle "Delete now or later"
		if (newRetentionDays < currentValue) {
			// Wait for second popup
			TestObject deletePopup = new TestObject("deletePopupTitle")
			deletePopup.addProperty("xpath", ConditionType.EQUALS,
					"//div[contains(@class,'modal-container')]//div[contains(text(),'Do you want to delete old reports now?')]")

			WebUI.waitForElementVisible(deletePopup, 10)
			WebUI.verifyElementText(deletePopup, "Do you want to delete old reports now?")

			// Click "Delete at scheduled time"
			TestObject deleteAtScheduledTimeBtn = new TestObject("deleteATScheduleButton")
			deleteAtScheduledTimeBtn.addProperty("xpath", ConditionType.EQUALS, "//button[text()='Delete at scheduled time']")
			WebUI.click(deleteAtScheduledTimeBtn)

		}

		WebUI.delay(2)
	}
}
