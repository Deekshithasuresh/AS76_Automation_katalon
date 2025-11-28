import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Map<String, String> abbreviationToOffset = [('IST') : '+05:30', ('UTC') : '+00:00', ('GMT') : '+00:00', ('CET') : '+01:00'
    , ('EET') : '+02:00', ('MSK') : '+03:00', ('AST') : '-04:00', ('EST') : '-05:00', ('CST') : '-06:00', ('MST') : '-07:00'
    , ('PST') : '-08:00']

// === FUNCTION TO NORMALIZE HISTORY ZONE TEXT TO OFFSET ===
// Case 1: Format like "UTC+03:00"
// Case 2: Abbreviation (IST, CET etc.)
// Case 3: Already "+03:00"
// ======================================================
// Function: Change timezone, verify against history
// ======================================================
//WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_icon_of_history'))
// ======================================================
// MAIN TEST CASE
// ======================================================
WebUI.openBrowser('')

WebUI.navigateToUrl('https://admin.as76.local/login')

// Login
WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

// Pass abbreviationToOffset into verifyTimeZone()
verifyTimeZone('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0300) AsiaBahrain', 'Object Repository/Session management reporting/Page_Admin Console/li_(UTC0300) AsiaBahrain', 
    'Object Repository/Session management reporting/Page_Admin Console/div_07-May-2025, 0752 AM (03)', abbreviationToOffset)

//verifyTimeZone(
//	'Object Repository/Session management reporting/Page_Admin Console/li_(UTC0530) AsiaCalcutta',
//	'Object Repository/Session management reporting/Page_Admin Console/li_(UTC0530) AsiaCalcutta',
//	'Object Repository/Session management reporting/Page_Admin Console/div_08-May-2025, 1246 PM (IST)',
//	abbreviationToOffset
//)
WebUI.closeBrowser()

String normalizeHistoryZone(String historyZone, Map<String, String> abbrMap) {
    if ((historyZone == null) || historyZone.trim().isEmpty()) {
    }
    
    historyZone = historyZone.trim()

    if (historyZone.startsWith('UTC')) {
        return historyZone.replace('UTC', '').trim()
    }
    
    if (abbrMap.containsKey(historyZone)) {
        return abbrMap[historyZone]
    }
    
    if (historyZone ==~ '[+-]\\d{2}:\\d{2}') {
        return historyZone
    }
    
    throw new AssertionError('❌ Unknown history zone format: ' + historyZone)
}

def verifyTimeZone(String timeZoneOptionTestObject, String selectedZoneTO, String historyEntryTO, Map<String, String> abbrMap) {
    WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

    WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

    WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

    WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'))

    WebUI.click(findTestObject(timeZoneOptionTestObject))

    WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))

    String selectedZoneText = WebUI.getText(findTestObject(selectedZoneTO))

    def selectedOffsetMatch = selectedZoneText =~ '\\(UTC([+-]\\d{2}:\\d{2})\\)'

    String selectedOffset = selectedOffsetMatch ? (selectedOffsetMatch[0])[1] : null

    println("✅ Selected Offset: $selectedOffset")

    WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

    WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/svg_Admin Portal_MuiSvgIcon-root MuiSvgIcon_5f2eb8'))

    WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_History'))

    String historyText = WebUI.getText(findTestObject(historyEntryTO))

    println("🕓 History Entry: $historyText")

    def historyZoneMatch = historyText =~ '\\(([^)]+)\\)'

    String historyZone = historyZoneMatch ? (historyZoneMatch[0])[1] : null

    println("History Zone Raw: $historyZone")

    String normalizedHistoryOffset = normalizeHistoryZone(historyZone, abbrMap)

    println("Normalized History Offset: $normalizedHistoryOffset")

    assert normalizedHistoryOffset == selectedOffset

    println("✅ Time zone match verified → $selectedOffset")
}

