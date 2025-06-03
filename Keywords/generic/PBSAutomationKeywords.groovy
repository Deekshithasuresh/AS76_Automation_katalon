package generic

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class PBSAutomationKeywords {

    @Keyword
    def loginToAdminConsole(String username, String encryptedPassword) {
        WebUI.navigateToUrl("https://as76-admin-portal.azurewebsites.net/")
        WebUI.setText(findTestObject('AdminLogin/input_Username'), username)
        WebUI.setEncryptedText(findTestObject('AdminLogin/input_Password'), encryptedPassword)
        WebUI.click(findTestObject('AdminLogin/button_Sign in'))
        WebUI.waitForElementVisible(findTestObject('AdminDashboard/div_AdminPanel'), 10)
    }

    @Keyword
    def createReviewerUser(String name, String email, String username, String password) {
        WebUI.click(findTestObject('AdminDashboard/tab_Users'))
        WebUI.click(findTestObject('UserPage/button_Create'))

        WebUI.setText(findTestObject('UserPage/input_Name'), name)
        WebUI.setText(findTestObject('UserPage/input_Email'), email)
        WebUI.setText(findTestObject('UserPage/input_Username'), username)
        WebUI.setText(findTestObject('UserPage/input_Password'), password)
        WebUI.setText(findTestObject('UserPage/input_ConfirmPassword'), password)

        WebUI.click(findTestObject('UserPage/select_Role'))
        WebUI.click(findTestObject('UserPage/option_Reviewer'))
        WebUI.click(findTestObject('UserPage/button_Save'))
        WebUI.verifyElementPresent(findTestObject('UserPage/toast_Success'), 10)
    }

    @Keyword
    def openPBSAndLogin(String username, String password) {
        WebUI.openNewWindow()
        WebUI.switchToWindowIndex(1)
        WebUI.navigateToUrl("https://as76-frontend-pbs.azurewebsites.net/login")
        WebUI.setText(findTestObject('PBSLogin/input_Username'), username)
        WebUI.setText(findTestObject('PBSLogin/input_Password'), password)
        WebUI.click(findTestObject('PBSLogin/button_Login'))
    }

    @Keyword
    def acceptTermsAndSetPassword(String newPassword) {
        if (WebUI.verifyElementPresent(findTestObject('PBSLogin/checkbox_Terms'), 5, FailureHandling.OPTIONAL)) {
            WebUI.click(findTestObject('PBSLogin/checkbox_Terms'))
            WebUI.click(findTestObject('PBSLogin/button_Accept'))
        }

        if (WebUI.verifyElementPresent(findTestObject('PBSLogin/input_NewPassword'), 5, FailureHandling.OPTIONAL)) {
            WebUI.setText(findTestObject('PBSLogin/input_NewPassword'), newPassword)
            WebUI.setText(findTestObject('PBSLogin/input_ConfirmNewPassword'), newPassword)
            WebUI.click(findTestObject('PBSLogin/button_SavePassword'))
        }
    }

    @Keyword
    def assignSlideToReviewer(String reviewerName) {
        WebUI.click(findTestObject('PBSDashboard/tab_Slides'))
        WebUI.setText(findTestObject('SlidePage/input_Search'), '')
        WebUI.waitForElementVisible(findTestObject('SlidePage/row_FirstUnassignedSlide'), 10)

        String slideID = WebUI.getText(findTestObject('SlidePage/row_FirstUnassignedSlide/td_SlideID'))
        WebUI.click(findTestObject('SlidePage/row_FirstUnassignedSlide/button_Assign'))
        WebUI.setText(findTestObject('SlidePage/dialog_Assign/input_ReviewerSearch'), reviewerName)
        WebUI.click(findTestObject('SlidePage/dialog_Assign/option_ReviewerResult'))
        WebUI.click(findTestObject('SlidePage/dialog_Assign/button_Assign'))

        return slideID
    }

    @Keyword
    def getScanDateFromSlide(String slideID) {
        WebUI.setText(findTestObject('SlidePage/input_Search'), slideID)
        WebUI.click(findTestObject('SlidePage/row_FirstSlide/button_Edit'))
        WebUI.waitForElementVisible(findTestObject('SlideDetail/text_ScanDate'), 10)
        return WebUI.getText(findTestObject('SlideDetail/text_ScanDate'))
    }

    @Keyword
    def modifyRBCMorphology(String noteText) {
        WebUI.click(findTestObject('SlideDetail/tab_RBCMorphology'))
        WebUI.setText(findTestObject('SlideDetail/textarea_RBCNotes'), noteText)
        WebUI.click(findTestObject('SlideDetail/button_SaveAndExit'))
    }

    @Keyword
    def deleteUserByUsername(String username) {
        WebUI.click(findTestObject('AdminDashboard/tab_Users'))
        WebUI.setText(findTestObject('UserPage/input_Search'), username)
        WebUI.click(findTestObject('UserPage/button_Delete'))
        WebUI.click(findTestObject('UserPage/button_ConfirmDelete'))
        WebUI.verifyElementPresent(findTestObject('UserPage/toast_Deleted'), 10)
    }

    @Keyword
    def searchReportBySlideID(String slideID) {
        WebUI.click(findTestObject('PBSDashboard/tab_Slides'))
        WebUI.setText(findTestObject('SlidePage/input_Search'), slideID)
        WebUI.waitForElementVisible(findTestObject('SlidePage/row_FirstSlide'), 10)
        WebUI.click(findTestObject('SlidePage/row_FirstSlide/button_Edit'))
    }

    @Keyword
    def verifyScanDateAndRBCNote(String slideID, String expectedScanDate, String expectedNote) {
        WebUI.verifyMatch(WebUI.getText(findTestObject('SlideDetail/text_ScanDate')), expectedScanDate, false)
        WebUI.click(findTestObject('SlideDetail/tab_RBCMorphology'))
        WebUI.verifyMatch(WebUI.getText(findTestObject('SlideDetail/textarea_RBCNotes')), expectedNote, false)
    }
}
