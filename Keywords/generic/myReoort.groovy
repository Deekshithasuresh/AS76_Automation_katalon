package generic

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
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
			TestObject deleteNowBtn = new TestObject("deleteNowButton")
			deleteNowBtn.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='Delete now']")
			WebUI.click(deleteNowBtn)

			WebUI.comment("Confirmed deletion of old reports.")
		}

		WebUI.delay(2) // Optional delay
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
}
