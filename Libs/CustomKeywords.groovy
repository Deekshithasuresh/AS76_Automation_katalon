
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.String

import org.openqa.selenium.WebDriver

import java.util.List



def static "generic.history.verifyReportDeletionFormat"() {
    (new generic.history()).verifyReportDeletionFormat()
}


def static "generic.history.verifyRetentionDurationUpdates"() {
    (new generic.history()).verifyRetentionDurationUpdates()
}


def static "generic.Reclacification.classifyFromCellToCell"(
    	String fromCellName	
     , 	String toCellName	) {
    (new generic.Reclacification()).classifyFromCellToCell(
        	fromCellName
         , 	toCellName)
}


def static "generic.PBSAutomationKeywords.loginToAdminConsole"(
    	String username	
     , 	String encryptedPassword	) {
    (new generic.PBSAutomationKeywords()).loginToAdminConsole(
        	username
         , 	encryptedPassword)
}


def static "generic.PBSAutomationKeywords.createReviewerUser"(
    	String name	
     , 	String email	
     , 	String username	
     , 	String password	) {
    (new generic.PBSAutomationKeywords()).createReviewerUser(
        	name
         , 	email
         , 	username
         , 	password)
}


def static "generic.PBSAutomationKeywords.openPBSAndLogin"(
    	String username	
     , 	String password	) {
    (new generic.PBSAutomationKeywords()).openPBSAndLogin(
        	username
         , 	password)
}


def static "generic.PBSAutomationKeywords.acceptTermsAndSetPassword"(
    	String newPassword	) {
    (new generic.PBSAutomationKeywords()).acceptTermsAndSetPassword(
        	newPassword)
}


def static "generic.PBSAutomationKeywords.assignSlideToReviewer"(
    	String reviewerName	) {
    (new generic.PBSAutomationKeywords()).assignSlideToReviewer(
        	reviewerName)
}


def static "generic.PBSAutomationKeywords.getScanDateFromSlide"(
    	String slideID	) {
    (new generic.PBSAutomationKeywords()).getScanDateFromSlide(
        	slideID)
}


def static "generic.PBSAutomationKeywords.modifyRBCMorphology"(
    	String noteText	) {
    (new generic.PBSAutomationKeywords()).modifyRBCMorphology(
        	noteText)
}


def static "generic.PBSAutomationKeywords.deleteUserByUsername"(
    	String username	) {
    (new generic.PBSAutomationKeywords()).deleteUserByUsername(
        	username)
}


def static "generic.PBSAutomationKeywords.searchReportBySlideID"(
    	String slideID	) {
    (new generic.PBSAutomationKeywords()).searchReportBySlideID(
        	slideID)
}


def static "generic.PBSAutomationKeywords.verifyScanDateAndRBCNote"(
    	String slideID	
     , 	String expectedScanDate	
     , 	String expectedNote	) {
    (new generic.PBSAutomationKeywords()).verifyScanDateAndRBCNote(
        	slideID
         , 	expectedScanDate
         , 	expectedNote)
}


def static "generic.myReoort.myreportCheckbox"(
    	String user	) {
    (new generic.myReoort()).myreportCheckbox(
        	user)
}


def static "generic.myReoort.setRetentionPolicy"(
    	int newRetentionDays	) {
    (new generic.myReoort()).setRetentionPolicy(
        	newRetentionDays)
}


def static "generic.myReoort.setRetentionPolicyScheduleTime"(
    	int newRetentionDays	) {
    (new generic.myReoort()).setRetentionPolicyScheduleTime(
        	newRetentionDays)
}


def static "applyFilterAndVerify.ApplyFilterAndVerifyTheList.createUser"(
    	String name	
     , 	String email	
     , 	String username	
     , 	String password	
     , 	String role	) {
    (new applyFilterAndVerify.ApplyFilterAndVerifyTheList()).createUser(
        	name
         , 	email
         , 	username
         , 	password
         , 	role)
}


def static "generic.custumFunctions.login"() {
    (new generic.custumFunctions()).login()
}


def static "generic.custumFunctions.selectReportByStatus"(
    	String status	) {
    (new generic.custumFunctions()).selectReportByStatus(
        	status)
}


def static "generic.custumFunctions.assignReviewerToReport"(
    	String initialStatus	
     , 	String reviewerName	) {
    (new generic.custumFunctions()).assignReviewerToReport(
        	initialStatus
         , 	reviewerName)
}


def static "generic.custumFunctions.verifyCancelButtonDuringReassign"(
    	String reportStatus	
     , 	String originalReviewer	) {
    (new generic.custumFunctions()).verifyCancelButtonDuringReassign(
        	reportStatus
         , 	originalReviewer)
}


def static "generic.custumFunctions.assignOrReassignOnTabs"(
    	String reviewerName	
     , 	boolean confirmReassign	) {
    (new generic.custumFunctions()).assignOrReassignOnTabs(
        	reviewerName
         , 	confirmReassign)
}


def static "generic.custumFunctions.unassignOrCancel"(
    	String reportStatus	
     , 	boolean confirm	) {
    (new generic.custumFunctions()).unassignOrCancel(
        	reportStatus
         , 	confirm)
}


def static "generic.custumFunctions.assignOrReassignOnTabs"(
    	String reviewerName	) {
    (new generic.custumFunctions()).assignOrReassignOnTabs(
        	reviewerName)
}


def static "chida.wbcFunctions.classifyFromCellToCell"(
    	String fromCellName	
     , 	String toCellName	) {
    (new chida.wbcFunctions()).classifyFromCellToCell(
        	fromCellName
         , 	toCellName)
}


def static "chida.wbcFunctions.getCellCountInCurrentTab"(
    	WebDriver driver	
     , 	String cellName	) {
    (new chida.wbcFunctions()).getCellCountInCurrentTab(
        	driver
         , 	cellName)
}


def static "chida.wbcFunctions.selectReportByStatus"(
    	String status	) {
    (new chida.wbcFunctions()).selectReportByStatus(
        	status)
}


def static "chida.wbcFunctions.assignOrReassignOnTabs"(
    	String reviewerName	
     , 	boolean confirmReassign	) {
    (new chida.wbcFunctions()).assignOrReassignOnTabs(
        	reviewerName
         , 	confirmReassign)
}


def static "chida.wbcFunctions.assignOrReassignOnTabs"(
    	String reviewerName	) {
    (new chida.wbcFunctions()).assignOrReassignOnTabs(
        	reviewerName)
}


def static "generic.FilterFunctions.applyAndVerifyDateFilter"(
    	String startDateStr	
     , 	String endDateStr	) {
    (new generic.FilterFunctions()).applyAndVerifyDateFilter(
        	startDateStr
         , 	endDateStr)
}


def static "generic.FilterFunctions.assignedToFilter"(
    	java.util.List<String> reviewers	) {
    (new generic.FilterFunctions()).assignedToFilter(
        	reviewers)
}


def static "generic.FilterFunctions.statusFilter"(
    	String RStatus	) {
    (new generic.FilterFunctions()).statusFilter(
        	RStatus)
}


def static "generic.FilterFunctions.verifyCombineFilters"(
    	String status	
     , 	String assignedReviewer	) {
    (new generic.FilterFunctions()).verifyCombineFilters(
        	status
         , 	assignedReviewer)
}


def static "generic.FilterFunctions.verifyAllFiltersApplied"(
    	String startDate	
     , 	String endDate	
     , 	java.util.List<String> assignedUsers	
     , 	java.util.List<String> statuses	) {
    (new generic.FilterFunctions()).verifyAllFiltersApplied(
        	startDate
         , 	endDate
         , 	assignedUsers
         , 	statuses)
}


def static "generic.FilterFunctions.verifySlideIdSearch"() {
    (new generic.FilterFunctions()).verifySlideIdSearch()
}


def static "generic.FilterFunctions.verifySearchClearFunctionality"() {
    (new generic.FilterFunctions()).verifySearchClearFunctionality()
}


def static "generic.Reclassification.reclassifyAllWBCtoPlateletInBatches"(
    	String toPlateletCell	
     , 	int batchSize	) {
    (new generic.Reclassification()).reclassifyAllWBCtoPlateletInBatches(
        	toPlateletCell
         , 	batchSize)
}


def static "generic.Reclassification.reclassifyPlateletToWBC"(
    	String fromPlateletCell	
     , 	String toWbcCell	
     , 	int patchCount	) {
    (new generic.Reclassification()).reclassifyPlateletToWBC(
        	fromPlateletCell
         , 	toWbcCell
         , 	patchCount)
}


def static "generic.Reclassification.reclassifyWBCToPlatelet"(
    	String fromCellName	
     , 	String toPlateletCell	
     , 	int patchCount	) {
    (new generic.Reclassification()).reclassifyWBCToPlatelet(
        	fromCellName
         , 	toPlateletCell
         , 	patchCount)
}


def static "generic.Reclassification.getCellCountInCurrentTab"(
    	WebDriver driver	
     , 	String cellName	) {
    (new generic.Reclassification()).getCellCountInCurrentTab(
        	driver
         , 	cellName)
}


def static "generic.Reclassification.classifyMultipleSelectedPatches"(
    	String fromCellName	
     , 	String toSubCellName	
     , 	int numberOfPatches	) {
    (new generic.Reclassification()).classifyMultipleSelectedPatches(
        	fromCellName
         , 	toSubCellName
         , 	numberOfPatches)
}


def static "generic.Reclassification.classifyMultiplePatchesToCell"(
    	String fromCellName	
     , 	String toCellName	
     , 	int patchCount	) {
    (new generic.Reclassification()).classifyMultiplePatchesToCell(
        	fromCellName
         , 	toCellName
         , 	patchCount)
}


def static "generic.Reclassification.classifyToSubCell"(
    	String mainCell	
     , 	String subCell	) {
    (new generic.Reclassification()).classifyToSubCell(
        	mainCell
         , 	subCell)
}


def static "generic.Reclassification.classifyFromCellToCellMultiple"(
    	String fromCellName	
     , 	String toCellName	
     , 	int times	) {
    (new generic.Reclassification()).classifyFromCellToCellMultiple(
        	fromCellName
         , 	toCellName
         , 	times)
}


def static "generic.Reclassification.classifyFromCellToCell"(
    	String fromCellName	
     , 	String toCellName	) {
    (new generic.Reclassification()).classifyFromCellToCell(
        	fromCellName
         , 	toCellName)
}


def static "generic.Reclassification.classifyFromCellToSubCellAny"(
    	String fromCellName	
     , 	String toSubCellName	) {
    (new generic.Reclassification()).classifyFromCellToSubCellAny(
        	fromCellName
         , 	toSubCellName)
}


def static "generic.Reclassification.dragAndDropFromCellToCell"(
    	String fromCellName	
     , 	String toCellName	) {
    (new generic.Reclassification()).dragAndDropFromCellToCell(
        	fromCellName
         , 	toCellName)
}


def static "generic.Reclassification.dragAndDropMultipleSelectedPatches"(
    	String fromCellName	
     , 	String toCellName	
     , 	int numberOfPatches	) {
    (new generic.Reclassification()).dragAndDropMultipleSelectedPatches(
        	fromCellName
         , 	toCellName
         , 	numberOfPatches)
}


def static "generic.Reclassification.classifyFromCellToCellMultiplePlatelet"(
    	String fromCellName	
     , 	String toCellName	
     , 	int times	) {
    (new generic.Reclassification()).classifyFromCellToCellMultiplePlatelet(
        	fromCellName
         , 	toCellName
         , 	times)
}


def static "generic.Reclassification.reclassifyAllWBCtoPlateletInBatches"(
    	String toPlateletCell	) {
    (new generic.Reclassification()).reclassifyAllWBCtoPlateletInBatches(
        	toPlateletCell)
}


def static "generic.bookmark.verifyBookmarkLifecycle"(
    	int rowIndex	) {
    (new generic.bookmark()).verifyBookmarkLifecycle(
        	rowIndex)
}


def static "generic.bookmark.manageCommentOnBookmarkedReport"(
    	String reportStatus	
     , 	String initialComment	
     , 	String updatedComment	) {
    (new generic.bookmark()).manageCommentOnBookmarkedReport(
        	reportStatus
         , 	initialComment
         , 	updatedComment)
}


def static "generic.bookmark.verifyBookmarkLifecycle"() {
    (new generic.bookmark()).verifyBookmarkLifecycle()
}


def static "generic.Reclassificationvj.reclassifyAllWBCtoPlateletInBatches"(
    	String toPlateletCell	
     , 	int batchSize	) {
    (new generic.Reclassificationvj()).reclassifyAllWBCtoPlateletInBatches(
        	toPlateletCell
         , 	batchSize)
}


def static "generic.Reclassificationvj.reclassifyPlateletToWBC"(
    	String fromPlateletCell	
     , 	String toWbcCell	
     , 	int patchCount	) {
    (new generic.Reclassificationvj()).reclassifyPlateletToWBC(
        	fromPlateletCell
         , 	toWbcCell
         , 	patchCount)
}


def static "generic.Reclassificationvj.reclassifyWBCToPlatelet"(
    	String fromCellName	
     , 	String toPlateletCell	
     , 	int patchCount	) {
    (new generic.Reclassificationvj()).reclassifyWBCToPlatelet(
        	fromCellName
         , 	toPlateletCell
         , 	patchCount)
}


def static "generic.Reclassificationvj.getCellCountInCurrentTab"(
    	WebDriver driver	
     , 	String cellName	) {
    (new generic.Reclassificationvj()).getCellCountInCurrentTab(
        	driver
         , 	cellName)
}


def static "generic.Reclassificationvj.classifyMultipleSelectedPatches"(
    	String fromCellName	
     , 	String toSubCellName	
     , 	int numberOfPatches	) {
    (new generic.Reclassificationvj()).classifyMultipleSelectedPatches(
        	fromCellName
         , 	toSubCellName
         , 	numberOfPatches)
}


def static "generic.Reclassificationvj.classifyMultiplePatchesToCell"(
    	String fromCellName	
     , 	String toCellName	
     , 	int patchCount	) {
    (new generic.Reclassificationvj()).classifyMultiplePatchesToCell(
        	fromCellName
         , 	toCellName
         , 	patchCount)
}


def static "generic.Reclassificationvj.classifyToSubCell"(
    	String mainCell	
     , 	String subCell	) {
    (new generic.Reclassificationvj()).classifyToSubCell(
        	mainCell
         , 	subCell)
}


def static "generic.Reclassificationvj.classifyFromCellToCellMultiple"(
    	String fromCellName	
     , 	String toCellName	
     , 	int times	) {
    (new generic.Reclassificationvj()).classifyFromCellToCellMultiple(
        	fromCellName
         , 	toCellName
         , 	times)
}


def static "generic.Reclassificationvj.classifyFromCellToCell"(
    	String fromCellName	
     , 	String toCellName	) {
    (new generic.Reclassificationvj()).classifyFromCellToCell(
        	fromCellName
         , 	toCellName)
}


def static "generic.Reclassificationvj.classifyFromCellToSubCellAny"(
    	String fromCellName	
     , 	String toSubCellName	) {
    (new generic.Reclassificationvj()).classifyFromCellToSubCellAny(
        	fromCellName
         , 	toSubCellName)
}


def static "generic.Reclassificationvj.dragAndDropFromCellToCell"(
    	String fromCellName	
     , 	String toCellName	) {
    (new generic.Reclassificationvj()).dragAndDropFromCellToCell(
        	fromCellName
         , 	toCellName)
}


def static "generic.Reclassificationvj.dragAndDropMultipleSelectedPatches"(
    	String fromCellName	
     , 	String toCellName	
     , 	int numberOfPatches	) {
    (new generic.Reclassificationvj()).dragAndDropMultipleSelectedPatches(
        	fromCellName
         , 	toCellName
         , 	numberOfPatches)
}


def static "generic.Reclassificationvj.classifyPlateletToWbcSubCell"(
    	String fromPlateletCell	
     , 	String toWbcSubCell	
     , 	int patchCount	) {
    (new generic.Reclassificationvj()).classifyPlateletToWbcSubCell(
        	fromPlateletCell
         , 	toWbcSubCell
         , 	patchCount)
}


def static "generic.Reclassificationvj.reclassifyPlateletToAllWBCMainCells"(
    	String fromPlateletCell	
     , 	String toWbcCell	
     , 	int patchCount	) {
    (new generic.Reclassificationvj()).reclassifyPlateletToAllWBCMainCells(
        	fromPlateletCell
         , 	toWbcCell
         , 	patchCount)
}


def static "generic.Reclassificationvj.reclassifyAllWBCtoPlateletInBatches"(
    	String toPlateletCell	) {
    (new generic.Reclassificationvj()).reclassifyAllWBCtoPlateletInBatches(
        	toPlateletCell)
}


def static "generic.dataManagement.loginAdmin"() {
    (new generic.dataManagement()).loginAdmin()
}


def static "generic.helper.login"() {
    (new generic.helper()).login()
}


def static "generic.helper.selectReportByStatus"(
    	String status	) {
    (new generic.helper()).selectReportByStatus(
        	status)
}


def static "generic.helper.assignReviewerToReport"(
    	String initialStatus	
     , 	String reviewerName	) {
    (new generic.helper()).assignReviewerToReport(
        	initialStatus
         , 	reviewerName)
}


def static "generic.helper.verifyCancelButtonDuringReassign"(
    	String reportStatus	
     , 	String originalReviewer	) {
    (new generic.helper()).verifyCancelButtonDuringReassign(
        	reportStatus
         , 	originalReviewer)
}


def static "generic.helper.assignOrReassignOnTabs"(
    	String reviewerName	
     , 	boolean confirmReassign	) {
    (new generic.helper()).assignOrReassignOnTabs(
        	reviewerName
         , 	confirmReassign)
}


def static "generic.helper.unassignOrCancel"(
    	String reportStatus	
     , 	boolean confirm	) {
    (new generic.helper()).unassignOrCancel(
        	reportStatus
         , 	confirm)
}


def static "generic.helper.assignOrReassignOnTabs"(
    	String reviewerName	) {
    (new generic.helper()).assignOrReassignOnTabs(
        	reviewerName)
}


def static "generic.custumFunctionsvj.selectReportByStatus"(
    	String status	) {
    (new generic.custumFunctionsvj()).selectReportByStatus(
        	status)
}


def static "generic.custumFunctionsvj.verifyCancelButtonDuringReassign"(
    	String reportStatus	
     , 	String originalReviewer	) {
    (new generic.custumFunctionsvj()).verifyCancelButtonDuringReassign(
        	reportStatus
         , 	originalReviewer)
}


def static "generic.custumFunctionsvj.assignOrReassignOnTabs"(
    	String reviewerName	
     , 	boolean confirmReassign	) {
    (new generic.custumFunctionsvj()).assignOrReassignOnTabs(
        	reviewerName
         , 	confirmReassign)
}


def static "generic.custumFunctionsvj.unassignOrCancel"(
    	String reportStatus	
     , 	boolean confirm	) {
    (new generic.custumFunctionsvj()).unassignOrCancel(
        	reportStatus
         , 	confirm)
}


def static "generic.custumFunctionsvj.assignOrReassignOnTabs"(
    	String reviewerName	) {
    (new generic.custumFunctionsvj()).assignOrReassignOnTabs(
        	reviewerName)
}

 /**
	 * Checks if a canvas image is blurry using edge variance.
	 * Accepts optional selector and sharpness threshold.
	 *s
	 * @param jsCanvasSelector JS string to locate canvas element. Defaults to "#pbs-volumeViewport canvas".
	 * @param threshold Variance threshold below which image is considered blurry. Default is 5.0.
	 * @return true if blurry, false if sharp
	 */ 
def static "imageutils.blurChecker.isCanvasImageBlurry"(
    	String jsCanvasSelector	
     , 	double threshold	) {
    (new imageutils.blurChecker()).isCanvasImageBlurry(
        	jsCanvasSelector
         , 	threshold)
}


def static "imageutils.blurChecker.isCanvasImageBlurry"(
    	String jsCanvasSelector	) {
    (new imageutils.blurChecker()).isCanvasImageBlurry(
        	jsCanvasSelector)
}


def static "imageutils.blurChecker.isCanvasImageBlurry"() {
    (new imageutils.blurChecker()).isCanvasImageBlurry()
}


def static "generic.scroll.checkCellImageCountExceedsLimit"(
    	int threshold	) {
    (new generic.scroll()).checkCellImageCountExceedsLimit(
        	threshold)
}


def static "generic.scroll.checkScrollBehaviorBasedOnPatchCount"() {
    (new generic.scroll()).checkScrollBehaviorBasedOnPatchCount()
}


def static "generic.scroll.checkScrollBehaviorBasedOnPatchCountForSplitView"() {
    (new generic.scroll()).checkScrollBehaviorBasedOnPatchCountForSplitView()
}


def static "generic.scroll.checkCellImageCountExceedsLimit"() {
    (new generic.scroll()).checkCellImageCountExceedsLimit()
}


def static "loginPackage.Login.assignReviewerToReport"(
    	String initialStatus	
     , 	String reviewerName	) {
    (new loginPackage.Login()).assignReviewerToReport(
        	initialStatus
         , 	reviewerName)
}


def static "loginPackage.Login.selectReportByStatus"(
    	String status	) {
    (new loginPackage.Login()).selectReportByStatus(
        	status)
}


def static "generic.Wbc_helper.verifyMainWBCCountAndPercentage"() {
    (new generic.Wbc_helper()).verifyMainWBCCountAndPercentage()
}


def static "generic.Wbc_helper.verifyScrollOptionForPatches"(
    	String patchesContainerXPath	
     , 	String scrollableElementXPath	
     , 	int thresholdPatchCount	) {
    (new generic.Wbc_helper()).verifyScrollOptionForPatches(
        	patchesContainerXPath
         , 	scrollableElementXPath
         , 	thresholdPatchCount)
}


def static "generic.Wbc_helper.validateNLR"(
    	double neutrophilCount	
     , 	double lymphocyteCount	
     , 	String expectedRoundedNLR	) {
    (new generic.Wbc_helper()).validateNLR(
        	neutrophilCount
         , 	lymphocyteCount
         , 	expectedRoundedNLR)
}


def static "generic.Wbc_helper.verifySplitViewOnDoubleClick"() {
    (new generic.Wbc_helper()).verifySplitViewOnDoubleClick()
}


def static "generic.Wbc_helper.verifyRenderedAndOriginalPatchSize"() {
    (new generic.Wbc_helper()).verifyRenderedAndOriginalPatchSize()
}


def static "generic.Wbc_helper.getWbcDifferentialFromUI"() {
    (new generic.Wbc_helper()).getWbcDifferentialFromUI()
}


def static "generic.Wbc_helper.getRbcGradesFromUI"() {
    (new generic.Wbc_helper()).getRbcGradesFromUI()
}
