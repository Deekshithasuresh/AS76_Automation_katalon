import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// ========== STEP 1: Login to PBS Portal ==========
WebUI.openBrowser('')
WebUI.navigateToUrl('https://pbsreview.as76.local/login')
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))


def driver = DriverFactory.getWebDriver()
int bookmarkCount = 0
Set<String> uniqueDates = new HashSet<>()

// ========== STEP 2: Bookmark reports and count unique dates ==========
List<WebElement> rows = driver.findElements(By.cssSelector("tbody.MuiTableBody-root tr"))
totalReportsBefore = rows.size()

for (WebElement row : rows) {
	List<WebElement> cells = row.findElements(By.tagName("td"))
	if (cells.size() >= 4) {
		String scanDate = cells[3].getText().split(',')[0].trim()
		if (scanDate && !scanDate.isEmpty()) {
			uniqueDates.add(scanDate)
		}
	}

//	try {
//		WebElement bookmarkIcon = row.findElement(By.cssSelector("td:nth-child(1) img"))
//		String src = bookmarkIcon.getAttribute("src")
//		if (src.contains("bookmark.svg")) {
//			bookmarkIcon.click()
//			WebUI.delay(0.5)
//			bookmarkCount++
//		}
//	} catch (Exception e) {
//		WebUI.comment("⚠️ Bookmark icon not found or clickable in a row: " + e.message)
//	}
}

// ========== STEP 3: Calculate Retention Days ==========
int retentionDays = uniqueDates.size()
println "🧮 Retention days (unique): $retentionDays"

Date today = new Date()
Date thresholdDate = new Date(today.getTime() - (retentionDays * 24 * 60 * 60 * 1000))

List<String> allStatusDropdowns = [ 'Reviewed','Preparing', 'Ready for review']

// Step 1: Fetch old reports count before deletion
Map<String, Integer> beforeCounts = CustomKeywords.'generic.StatusReportUtils.getStatusWiseReportCount'(allStatusDropdowns, thresholdDate)



// ========== STEP 4: Logout and Go to Admin ==========
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_PBS_icon-img'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'))

WebUI.executeJavaScript("window.open('https://admin.as76.local/login','_blank');", null)
WebUI.switchToWindowIndex(1)

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Sign In'))

// ========== STEP 5: Navigate to Retention Policy ==========
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_Storage management'))
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'), 'Configure retention policy')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'))
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Edit Policy'), 'Edit Policy')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Edit Policy'))

// ========== STEP 6: Set Retention ==========
CustomKeywords.'generic.myReoort.setRetentionPolicy'(retentionDays-1)


TestObject closeBtn = new TestObject("closeBtn")
closeBtn.addProperty("xpath", ConditionType.EQUALS, "//div[@class='close-icon']")
WebUI.click(closeBtn)

TestObject profileIcon = findTestObject('Object Repository/Report_Listing/Page_Admin Console/profile_img')
WebUI.click(profileIcon)
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/li_Logout (1)'))

// ========== STEP 7: Re-login to PBS ==========
WebUI.switchToWindowIndex(0)
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))



// Step 4: Fetch reports again
Map<String, Integer> afterCounts =  CustomKeywords.'generic.StatusReportUtils.getStatusWiseReportCount'(allStatusDropdowns, thresholdDate)

// Step 5: Compare before and after
allStatusDropdowns.each { status ->
    int before = beforeCounts[status]
    int after = afterCounts[status]
    println "Status: ${status} | Before: ${before} | After: ${after}"

    assert after == 0 : "❌ Old reports under '${status}' were not deleted. Count: ${after}"
}
