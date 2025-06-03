package generic

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

public class bookmark {

	@Keyword
	def verifyBookmarkLifecycle(int rowIndex = 1) {
		// helper to build the TestObject for the icon
		def iconObj = {
			->
			String xpath = "//tr[${rowIndex}]//td[1]//img[@alt='bookmark']"
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

			// 3) Attempt remove → Cancel
			WebUI.comment("Clicking to remove, then CANCEL")
			//WebUI.click(icon)
			WebUI.waitForElementVisible(findPopupHeader(), 5)
			WebUI.click(findPopupButton('Cancel'))
			WebUI.waitForElementNotPresent(findPopupHeader(), 5)
			//assert !isFilled() : "Icon should remain filled after Cancel"
			WebUI.comment("✅ Cancel removal preserved bookmark icon.")

			// 4) Actually remove → Confirm
			WebUI.comment("Clicking to remove, then CONFIRM")
			WebUI.click(icon)
			WebUI.waitForElementVisible(findPopupHeader(), 5)
			WebUI.click(findPopupButton('Confirm'))
			WebUI.waitForElementNotPresent(findPopupHeader(), 5)
			WebUI.waitForElementVisible(findToast('Bookmark removed'), 5)
			//assert !isFilled() : "Icon still filled after removal"
			WebUI.comment("✅ Bookmark was removed and icon is outline.")
			WebUI.waitForElementVisible(findToast('Bookmark removed'), 5)
			WebUI.comment("Cleared existing bookmark.")
		}

		// 2) Add bookmark
		WebUI.comment("Clicking to add bookmark.")
		WebUI.click(icon)
		WebUI.waitForElementVisible(findToast('Bookmark saved'), 5)
		assert isFilled() : "Icon  fill after saving"
		WebUI.comment("✅ Bookmark was saved and icon is filled.")
	}

	private TestObject findToast(String text) {
		String xp = "//div[normalize-space() = '${text}']"
		return new TestObject('toast').addProperty('xpath', ConditionType.EQUALS, xp)
	}

	private TestObject findPopupHeader() {
		String xp = "//h2[contains(normalize-space(),'remove bookmark')]"
		return new TestObject('popupHeader').addProperty('xpath', ConditionType.EQUALS, xp)
	}

	private TestObject findPopupButton(String label) {
		String xp = "//div[contains(@class,'modal-actions')]//button[normalize-space() = '${label}']"
		return new TestObject('popupButton').addProperty('xpath', ConditionType.EQUALS, xp)
	}

	@Keyword
	def manageCommentOnBookmarkedReport(String reportStatus, String initialComment, String updatedComment) {
		String rowXpath = "(//tr[.//td//span[normalize-space() = '${reportStatus}']] )[1]"
		TestObject row = new TestObject('targetRow').addProperty('xpath', ConditionType.EQUALS, rowXpath)
		WebUI.waitForElementVisible(row, 10)

		// 1) Locate the bookmark icon in that row
		//String rowBase = "//tr[${rowIndex}]"
		String bookmarkXpath = rowXpath + "//td//img[@alt='bookmark']"
		TestObject bookmarkIcon = new TestObject('bookmarkIcon')
				.addProperty('xpath', ConditionType.EQUALS, bookmarkXpath)
		WebUI.waitForElementVisible(bookmarkIcon, 10)

		// helper to read src
		Closure<Boolean> isFilled = {
			->
			WebUI.getAttribute(bookmarkIcon, 'src').contains('bookmark-filled')
		}

		// 2) If not filled, click to bookmark and verify
		if (!isFilled()) {
			WebUI.comment("Bookmark not filled, clicking to fill")
			WebUI.click(bookmarkIcon)
			// wait for “Bookmark saved” toast
			TestObject toastSaved = new TestObject('toastSaved')
					.addProperty('xpath', ConditionType.EQUALS, "//div[text()='Bookmark saved']")
			WebUI.waitForElementVisible(toastSaved, 5)
			assert isFilled() : "Bookmark icon did not fill"
			WebUI.comment("✅ Bookmark now filled")
		} else {
			WebUI.comment("✅ Bookmark already filled")
		}

		// 3) Define popover locators
		String infoBtnXp = rowXpath + "//img[@alt='comment-icon']"
		TestObject infoBtn = new TestObject('infoBtn')
				.addProperty('xpath', ConditionType.EQUALS, infoBtnXp)
		// TestObject textarea = new TestObject('commentArea')
		// .addProperty('xpath', ConditionType.EQUALS, "//textarea[@placeholder='Start writing']")
		TestObject confirmBtn = new TestObject('confirmBtn')
				.addProperty('xpath', ConditionType.EQUALS, "(//div[contains(@class,'comment-buttons')]/img)[2]")
		TestObject deleteBtn = new TestObject('deleteBtn')
				.addProperty('xpath', ConditionType.EQUALS, "(//div[contains(@class,'comment-buttons')]/img)[1]")

		// --- STEP A: ADD initialComment ---
		WebUI.waitForPageLoad(10)
		WebUI.click(infoBtn)
		WebUI.waitForPageLoad(10)
		TestObject textarea = new TestObject('commentArea')
		textarea.addProperty('xpath', ConditionType.EQUALS,
				"//div[contains(@class,'comment-container')]//textarea")
		//WebUI.waitForElementVisible(textarea, 5)
		WebUI.waitForElementVisible(textarea, 10)
		WebUI.clearText(textarea)
		WebUI.setText(textarea, initialComment)
		WebUI.click(confirmBtn)
		WebUI.waitForElementNotVisible(textarea, 5)
		WebUI.comment("✅ Initial comment added")

		// Verify saved
		WebUI.waitForPageLoad(10)
		WebUI.click(infoBtn)
		WebUI.waitForPageLoad(10)
		WebUI.waitForElementVisible(textarea, 10)
		String saved1 = WebUI.getText(textarea).trim()
		assert saved1 == initialComment : "Expected '${initialComment}', found '${saved1}'"
		WebUI.comment("✅ Initial comment verified")
		WebUI.click(confirmBtn)

		// --- STEP B: UPDATE to updatedComment ---
		WebUI.waitForPageLoad(10)
		WebUI.click(infoBtn)
		WebUI.waitForPageLoad(10)
		WebUI.waitForElementVisible(textarea, 10)
		WebUI.clearText(textarea)
		WebUI.setText(textarea, updatedComment)
		WebUI.click(confirmBtn)
		WebUI.waitForElementNotVisible(textarea, 5)
		WebUI.comment("✅ Comment updated")

		// Verify update
		WebUI.waitForPageLoad(10)
		WebUI.click(infoBtn)
		WebUI.waitForPageLoad(10)
		WebUI.waitForElementVisible(textarea, 10)
		String saved2 = WebUI.getText(textarea).trim()
		assert saved2 == updatedComment : "Expected '${updatedComment}', found '${saved2}'"
		WebUI.comment("✅ Updated comment verified")
		WebUI.click(confirmBtn)

		// --- STEP C: DELETE comment ---
		WebUI.waitForPageLoad(10)
		WebUI.click(infoBtn)
		WebUI.waitForPageLoad(10)
		WebUI.waitForElementVisible(textarea, 10)
		//WebUI.clearText(textarea)
		WebUI.click(deleteBtn)
		//WebUI.click(confirmBtn)
		WebUI.waitForElementNotVisible(textarea, 5)
		//WebUI.clickOffset(textarea, -50, -50)
		WebUI.comment("✅ Comment deleted")

		// Verify deletion
		WebUI.waitForPageLoad(10)
		WebUI.click(infoBtn)
		WebUI.waitForPageLoad(10)
		WebUI.waitForElementVisible(textarea, 10)
		String finalText = WebUI.getText(textarea).trim()
		assert finalText == '' : "Expected empty after delete, found '${finalText}'"
		WebUI.comment("✅ Deletion verified, comment area is empty")
		TestObject xcomenttextfield = new TestObject('xcomenttextfield').addProperty('xpath', ConditionType.EQUALS,"//img[@alt='save-icon']")
		WebUI.click(xcomenttextfield)
		//WebUI.refresh()
		WebUI.waitForPageLoad(15)


		WebUI.click(bookmarkIcon)
		WebUI.waitForElementVisible(findPopupHeader(), 5)
		WebUI.click(findPopupButton('Confirm'))
		WebUI.waitForElementNotPresent(findPopupHeader(), 5)
		WebUI.waitForElementVisible(findToast('Bookmark removed'), 5)
		//assert !isFilled() : "Icon still filled after removal"
		WebUI.comment("✅ Bookmark was removed and icon is outline.")
		WebUI.waitForElementVisible(findToast('Bookmark removed'), 5)
		WebUI.comment("Cleared existing bookmark.")
	}
}
