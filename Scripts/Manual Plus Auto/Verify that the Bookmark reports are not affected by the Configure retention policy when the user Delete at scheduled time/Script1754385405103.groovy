import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import java.text.SimpleDateFormat
import java.util.Date

// ========== STEP 1: Login to PBS Portal ==========
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
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

	try {
		WebElement bookmarkIcon = row.findElement(By.cssSelector("td:nth-child(1) img"))
		String src = bookmarkIcon.getAttribute("src")
		if (src.contains("bookmark.svg")) {
			bookmarkIcon.click()
			WebUI.delay(0.5)
			bookmarkCount++
		}
	} catch (Exception e) {
		WebUI.comment("⚠️ Bookmark icon not found or clickable in a row: " + e.message)
	}
}

// ========== STEP 3: Calculate Retention Days ==========
int retentionDays = uniqueDates.size()

println "🧮 Retention days (unique): $retentionDays"
println "🔖 Total reports bookmarked: $bookmarkCount"

// ========== STEP 4: Logout and Go to Admin ==========
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_PBS_icon-img'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'))

WebUI.executeJavaScript("window.open('https://as76-admin.sigtuple.com/login','_blank');", null)
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
//CustomKeywords.'generic.myReoort.setRetentionPolicy'(retentionDays)
CustomKeywords.'generic.myReoort.setRetentionPolicyScheduleTimeWithScheduleTimeAhead'(retentionDays-1)


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

WebUI.delay(180) // Wait 3 minutes for scheduled delete
WebUI.refresh()

// ========== STEP 8: Verify Deletion Based on Retention ==========
driver = DriverFactory.getWebDriver()
SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy")
Date today = new Date()
Date thresholdDate = new Date(new Date().time - ((retentionDays - 1) * 24L * 60 * 60 * 1000))

println "📅 Reports before ${sdf.format(thresholdDate)} should be deleted (unless bookmarked)"
boolean deletionSuccess = true

rows = driver.findElements(By.cssSelector("tbody.MuiTableBody-root tr"))
totalReportsAfter = rows.size()


for (WebElement row : rows) {
	List<WebElement> cells = row.findElements(By.tagName("td"))
	if (cells.size() >= 4) {
		String dateStr = cells[3].getText().split(',')[0].trim()
		if (dateStr && !dateStr.isEmpty()) {
			try {
				Date scanDate = sdf.parse(dateStr)
				if (scanDate.before(thresholdDate)) {
					WebElement bookmarkIcon = row.findElement(By.cssSelector("td:nth-child(1) img"))
					String src = bookmarkIcon.getAttribute("src")
					boolean isBookmarked = !src.contains("bookmark.svg")
					
					if (!isBookmarked) {
						deletionSuccess = false
						println "❌ Found undeleted & unbookmarked report scanned on ${dateStr}"
					} else {
						println "️ Report ${dateStr} is bookmarked — so it is not deleted"
					}
				}
			} catch (Exception e) {
				WebUI.comment("⚠️ Unable to parse scan date: '${dateStr}', skipping row.")
			}
		}
	}
}

if (deletionSuccess) {
	println "✅ All old unbookmarked reports deleted as expected"
} else {
	WebUI.comment("❌ Deletion failed for one or more unbookmarked reports")
}

if (totalReportsBefore.equals( totalReportsAfter)) {
	WebUI.comment("✅ All old unbookmarked reports deleted as expected")
}
else {
	WebUI.comment("❌ Deletion failed for one or more unbookmarked reports")
}

println "📊 Total reports BEFORE retention policy: $totalReportsBefore"
println "📉 Total reports AFTER retention policy:  $totalReportsAfter"
int deletedCount = totalReportsBefore - totalReportsAfter
println "🗑️ Reports deleted (approx): $deletedCount"
