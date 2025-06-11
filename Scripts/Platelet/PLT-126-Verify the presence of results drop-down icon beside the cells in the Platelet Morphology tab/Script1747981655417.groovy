import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.apple.laf.AquaTabbedPaneCopyFromBasicUI.Actions
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.interactions.Actions;
import loginPackage.Login
import zoom.ZoomInOut
import zoom.ZoomInOut as Platelet_P

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

Platelet_P plt = new Platelet_P()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

lg.assignOrReassignOnTabs('jyothi')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected_1'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected_1'), 10)

//WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected'))

WebDriver driver =DriverFactory.getWebDriver()
//List<WebElement> options = driver.findElements(By.xpath("//ul[@role='listbox']//li[@role='option']"));
//
//
//assert options[0].getText().equals("Detected")	
//assert options[1].getText().equals("Not detected")
//
//WebUI.delay(2)
//
//WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected'))
//
//WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected_1'))
//
//List<WebElement> options1 = driver.findElements(By.xpath("//ul[@role='listbox']//li[@role='option']"));
//
//assert options1[0].getText().equals("Detected")
//assert options1[1].getText().equals("Not detected")
//
//
//WebDriver driver =DriverFactory.getWebDriver()
List<WebElement> drop_downs = driver.findElements(By.xpath("//div[contains(@class,'dropdown-container')]"));

for (WebElement dropdown : drop_downs) {
    Actions act = new Actions(driver);
    act.moveToElement(dropdown).click().perform();

    // Wait for the options to be visible if needed
    Thread.sleep(500); // use WebDriverWait in real test

    List<WebElement> options3 = dropdown.findElements(By.xpath(".//ul[@role='listbox']//li[@role='option']"));
    
    if (options3.size() >= 2) {
        String Text_Detected = options3.get(0).getAttribute("data-value");
        String Text_Not_Detected = options3.get(1).getAttribute("data-value");

        assert Text_Detected.equals("Detected");
        assert Text_Not_Detected.equals("Not detected");
    } else {
        System.out.println("❌ Dropdown did not contain enough options.");
    }

    act.moveToElement(dropdown).click().perform(); // close the dropdown
}



//div[contains(@class,"dropdown-container")]
