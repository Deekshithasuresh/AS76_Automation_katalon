

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import pdfutils.PdfReader
import platelet_Package.Platelet_P as Platelet_P


Platelet_P plt = new Platelet_P()

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")


WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'))

WebDriver driver = DriverFactory.getWebDriver()

driver.findElement(By.xpath('(//input[@type=\'radio\'])[1]')).click()

String Calculated_estimate_desc = driver.findElement(By.xpath('(//div[@class=\'plt-lvl-desc\'])[1]')).getText().replaceAll(
    '.*(\\(x 10\\^9/L\\))', '$1')

assert Calculated_estimate_desc.equals('(x 10^9/L)')

driver.findElement(By.xpath("//span[text()='Approve report']")).click()

driver.findElement(By.xpath("//button[text()='Confirm']")).click()

WebUI.scrollToElement(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_header_in_Preview_report'), 10)

String Platelet_estimate_desc_in_preview_rpt=driver.findElement(By.xpath("//td[text()='Platelet count (x 10^9/L)']")).getText().replaceAll('.*(\\(x 10\\^9/L\\))', '$1')

assert Platelet_estimate_desc_in_preview_rpt.equals('(x 10^9/L)')

driver.findElement(By.xpath("//span[text()='Approve report']")).click()// clicking on approve report in preview report page

driver.findElement(By.xpath("//button[text()='Approve report']")).click()

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 30)

WebUI.delay(3)

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 

wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='appBar_container__right__PwWvg']/button")));

driver.findElement(By.xpath("//div[@class='appBar_container__right__PwWvg']/button")).click()

driver.findElement(By.xpath("//ul[@class='appBar_popover__list__bNpZi']/li/span[text()='Download PDF report']")).click()

WebUI.delay(5)


// Step 1: Get Downloads folder path
String downloadsPath = System.getProperty('user.home') + '/Downloads'

// Step 2: Find the latest 'pdfReport*.pdf'
File latestPdf = pdfutils.PdfReader.getLatestPdfReport(downloadsPath)
if (latestPdf == null || !latestPdf.exists()) {
	WebUI.comment("❌ No PDF report found in Downloads.")
	assert false : "No PDF file found."
}
WebUI.comment("✅ Latest PDF found: ${latestPdf.absolutePath}")

// Step 3: Read text from PDF
String pdfText = pdfutils.PdfReader.readText(latestPdf.absolutePath)
println("📄 PDF Content:\n$pdfText")

// Step 4: Validate text content
if (!pdfText.contains('(x 10^9/L)')) {
	WebUI.comment("❌ '(x 10^9/L)' not found in PDF report!")
	assert false
}
if (!pdfText.contains('Detected') && !pdfText.contains('Not detected')) {
	WebUI.comment("❌ Neither 'Detected' nor 'Not detected' found!")
	assert false
}
WebUI.comment("✅ Required text found in PDF.")



//assert pdfText.contains('Not detected') : "❌ 'Not detected' text is not found in PDF report!"
// === Step 5: Validate percentage is shown next to 'Others*' ===
//def othersLine = pdfText.split('\n').find { it.contains("Others*") }
//assert othersLine != null : "❌ No line with 'Others*' found in PDF!"
//def percentMatch = othersLine =~ /Others\*\s+(\d{1,2}\.\d+)%/
//assert percentMatch.find() : "❌ Percentage value not found for 'Others*'!"
//println(":white_check_mark: Found 'Others*' with percentage: ${percentMatch[0][1]}%")

