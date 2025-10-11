import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import java.text.SimpleDateFormat
import java.util.*


// ========== STEP 1: Login to PBS Portal ==========
WebUI.openBrowser('')
WebUI.navigateToUrl('https://pbsreview.as76.local/login')
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))


def driver = DriverFactory.getWebDriver()
List<WebElement> rows = driver.findElements(By.cssSelector("tbody.MuiTableBody-root tr"))

SimpleDateFormat scanDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)
SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("dd MMM yyyy")

Date oldestDate = new Date() // start with today
boolean foundOlder = false

for (WebElement row : rows) {
	List<WebElement> cells = row.findElements(By.tagName("td"))
	if (cells.size() >= 4) {
		String scanDateText = cells[3].getText().trim()
		if (scanDateText && !scanDateText.isEmpty()) {
			try {
				Date fullDate = scanDateFormat.parse(scanDateText)
				if (fullDate.before(oldestDate)) {
					oldestDate = fullDate
					foundOlder = true
				}
			} catch (Exception e) {
				println "⚠️ Error parsing scan date: $scanDateText"
			}
		}
	}
}
int retentionDays=0
if (foundOlder) {
	Date today = new Date()
	long diffMillis = today.time - oldestDate.time
	 retentionDays = (diffMillis / (1000 * 60 * 60 * 24)).intValue()

	println "📅 Oldest report date: ${dateOnlyFormat.format(oldestDate)}"
	println "🕒 Reports go back $retentionDays days from today"
} else {
	println "❌ No valid scan dates found in table"
}

// ========== STEP 3: Calculate Retention Days ==========
println "🧮 Retention days (unique): $retentionDays"

Date today = new Date()

// Full datetime format: e.g., "06 Aug 2025 05:35 PM"
SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH)

// Subtract retention days and retain time (accurate to the minute)
Date thresholdDate = new Date(today.getTime() - ((retentionDays - 1) * 24L * 60 * 60 * 1000))

println "🕒 Threshold Date-Time: ${dateTimeFormat.format(thresholdDate)}"


List<String> allStatusDropdowns = [ 'Reviewed','Preparing', 'Ready for review']

// Step 1: Fetch old reports count before deletion
Map<String, Integer> beforeCounts = CustomKeywords.'generic.StatusReportUtils.getStatusWiseReportCount'(allStatusDropdowns, thresholdDate)



// ========== STEP 4: Logout and Go to Admin ==========
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_PBS_icon-img'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'))

WebUI.delay(retentionDays)
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


