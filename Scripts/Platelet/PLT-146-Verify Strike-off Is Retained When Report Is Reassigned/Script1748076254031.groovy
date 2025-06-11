import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import platelet_Package.Platelet_P
Login lg = new Login()


Platelet_P plt = new Platelet_P()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

lg.assignOrReassignOnTabs('premkumar')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'))

WebDriver driver =DriverFactory.getWebDriver()

plt.verifyModifingPlateletDetetctedNotDetecetdStatus()

String Slide_id_opened=WebUI.getText(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Slide_Id_Text_In_report'))

lg.assignOrReassignOnTabs('pawan')

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Back_CTA'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Slide_id_header'),20)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/User_Icon'),20)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/User_Icon'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Log_out_CTA'),20)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Log_out_CTA'))

lg.loginAgainWithDifferentUser()//passed a different user name and password iside this method

List<WebElement> slide_ids=driver.findElements(By.xpath("//tbody/tr/td[3]"))

for (int i = 0; i < slide_ids.size(); i++) {
    try {
        // Refetch the element before using it
        WebElement slide_id = driver.findElements(By.xpath("//tbody/tr/td[3]")).get(i);
        
        if (slide_id.getText().equals(Slide_id_opened)) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(slide_id));
            slide_id.click();
            break; // Optional: exit the loop after successful click
        }
    } catch (StaleElementReferenceException e) {
        System.out.println("Stale element caught, retrying... Index: " + i);
        i--; // retry the current index
    }
}

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 20)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 20)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'), 20)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'))

verifyStrikeOffStatus()

public void verifyStrikeOffStatus() {
	WebDriver driver = DriverFactory.getWebDriver()
	Actions act = new Actions(driver)
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5))

	List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
			findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Cell_rows'), 10)

	for (WebElement cellRow : cellRows) {
		String cellName = cellRow.findElement(By.xpath(".//div[1]")).getText()
		WebElement count_ele = cellRow.findElement(By.xpath(".//div[2]"))
		WebElement dropDown = cellRow.findElement(By.xpath(".//div[3]"))
		String currentValue = dropDown.getText().trim()
		
			String strike_off_CSS=count_ele.getCssValue('text-decoration').split(" ")[0]
			println(strike_off_CSS)
			assert strike_off_CSS.equals('line-through')

	}
}