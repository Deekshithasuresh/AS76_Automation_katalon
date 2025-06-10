package platelet_Package

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration
import java.util.concurrent.TimeoutException

import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;


public class Platelet_P {
	//WebDriver driver = DriverFactory.getWebDriver()
	// Define JavaScript code as a single string
	String jsCode = '''
  return (async () => {
    const SCROLL_STEP = 500;
    const SCROLL_DELAY = 1000;
    const scrollContainer = document.querySelector('.List');
    if (!scrollContainer) {
      return { error: 'Scroll container not found!' };
    }
    const uniqueImages = new Set();
    let lastHeight = -1;
    const sleep = ms => new Promise(resolve => setTimeout(resolve, ms));
    const getVisibleImageSrcs = () => {
      const imgs = scrollContainer.querySelectorAll('img');
      imgs.forEach(img => {
        const src = img.getAttribute('src');
        if (src) {
          uniqueImages.add(src);
        }
      });
    };
    while (scrollContainer.scrollTop < scrollContainer.scrollHeight) {
      scrollContainer.scrollTop += SCROLL_STEP;
      await sleep(SCROLL_DELAY);
      getVisibleImageSrcs();
      if (scrollContainer.scrollTop === lastHeight) {
        break;
      }
      lastHeight = scrollContainer.scrollTop;
    }
    getVisibleImageSrcs();
    return {
      count: uniqueImages.size,
      images: [...uniqueImages]
    };
  })();
'''


	public void getAPlateletCellPatchSize() {
		// Locate all RBC patches (update selector to match your page's structure)
		//List<WebElement> patches = driver.findElements(By.xpath("//div[@class='cell-image-container']//img")); // example XPath
		WebDriver driver =DriverFactory.getWebDriver()


		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Cell_rows'),10)
		for(WebElement cellrow:cellRows) {
			WebElement cellname_ele = cellrow.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			cellrow.click()
			WebUI.delay(2)
			// Execute JavaScript and retrieve result
			def result = WebUI.executeJavaScript(jsCode, null)
			if (result instanceof Map && result.containsKey('error')) {
				WebUI.comment("❌ Error: " + result.error)
			} else {
				WebUI.comment("✅ Total unique images loaded: " + result.count)
				WebUI.comment("🖼️ Unique image URLs: " + result.images)
			}

			List<WebElement> patch_eles = WebUiCommonHelper.findWebElements(
					findTestObject('Object Repository/RBC_Objects/Page_PBS/Patches'),30)
			for (int i = 0; i < patch_eles.size(); i++) {
				WebElement patch = patch_eles.get(i).findElement(By.xpath(".//div//img[contains(@class, 'qa_patch_rank')]"));

				// Use JS to get intrinsic size
				//			JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
				//			int Width = (int) js.executeScript("return arguments[0].naturalWidth;", patch)
				//			int lHeight = (int) js.executeScript("return arguments[0].naturalHeight;", patch)


				Dimension size = patch.getSize();
				int width = size.getWidth();
				int height = size.getHeight();
				System.out.println(cellname+ " Patch " + (i + 1) + ": Width = " + width + " px, Height = " + height + " px");

				assert width == 162 : cellname+" Patch " + (i + 1) + " has incorrect width!"
				assert height == 162 : cellname+" Patch " + (i + 1) + " has incorrect height!"
			}
		}
	}


	//public void verifyModifingPlateletDetetctedNotDetecetdStatus()
	//{
	//	WebDriver driver =DriverFactory.getWebDriver()
	//	Actions act = new Actions(driver);
	//	List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
	//		findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Cell_rows'),10)
	//
	//	List<WebElement> drop_downs = driver.findElements(By.xpath("//div[contains(@class,'dropdown-container')]"));
	//	for(WebElement cellrow:cellRows)
	//	{
	//		String cellname=cellrow.findElement(By.xpath(".//div[1]")).getText()
	//		String count=cellrow.findElement(By.xpath(".//div[2]")).getText()
	//		WebElement drop_down=cellrow.findElement(By.xpath(".//div[3]"))
	//		String current_status=drop_down.getAttribute('class')
	//
	//		act.moveToElement(drop_down).click().perform()
	//
	//		// Wait for the options to become visible
	//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5))
	//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']//li[@role='option']")))
	//
	//		List<WebElement> options = drop_down.findElements(By.xpath(".//ul[@role='listbox']//li[@role='option']"))
	//
	//		if (options.size() < 2) {
	//			println("❌ Not enough options found in dropdown for: " + cellname)
	//			continue // or throw error or fail test
	//		}
	//
	//		if (current_status.contains("detected-label")) {
	//
	//			options[1].click() // Click "Not Detected"
	//		} else {
	//
	//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']//li[@role='option']")))
	//			options[2].click() // Click "Detected"
	//		}
	//	}
	//}

	public void verifyModifingPlateletDetetctedNotDetecetdStatus() {
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

			// Click dropdown to expand options
			act.moveToElement(dropDown).click().perform()

			// Wait for options to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//ul[@role='listbox']//li[@role='option']")))

			// Get all options
			List<WebElement> options = driver.findElements(
					By.xpath("//ul[@role='listbox']//li[@role='option']"))

			if (options.size() < 2) {
				println("❌ Not enough options found for cell: " + cellName)
				continue
			}

			// Toggle based on current value
			if (currentValue.equalsIgnoreCase("Detected")) {
				options.find { it.getText().trim().equalsIgnoreCase("Not detected") }?.click()
				String changed_value = dropDown.getText().trim()
				assert changed_value.equals("Not detected")
				WebElement dot_ele=dropDown.findElement(By.xpath('./div[1]'))
				String dot_color=dot_ele.getCssValue('background-color')
				println("Not detected color code "+dot_color)
				assert dot_color=='rgba(22, 177, 28, 1)'
				String strike_off_CSS=count_ele.getCssValue('text-decoration').split(" ")[0]
				println(strike_off_CSS)
				assert strike_off_CSS.equals('line-through')
			} else if (currentValue.equalsIgnoreCase("Not detected")) {
				options.find { it.getText().trim().equalsIgnoreCase("Detected") }?.click()
				String changed_value = dropDown.getText().trim()
				assert changed_value.equals("Detected")
				WebElement dot_ele=dropDown.findElement(By.xpath('./div[1]'))
				String dot_color=dot_ele.getCssValue('background-color')
				println("detected color code "+dot_color)
				assert dot_color=='rgba(255, 0, 0, 1)'
				String strike_off_CSS=count_ele.getCssValue('text-decoration').split(" ")[0]
				println(strike_off_CSS)
				assert strike_off_CSS.equals('line-through')
			} else {
				println("⚠️ Unknown option value: " + currentValue)
			}
		}
	}


	public void verifyThePresenceOfNoteMessageForPlateLetClumps() {
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
			if(cellName.equals('Large Platelets')) {
				continue;
			}


			// Toggle based on current value


			if(cellName.equals('Platelet Clumps') && currentValue.equalsIgnoreCase("Detected")){

				WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'))
				WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg'), 10)
				WebUI.verifyElementText(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg'), 'Platelet clumps are detected. Platelet count might be underestimated.')

				WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/button_Summary'))
				WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg_in_summary'), 10)
				WebUI.verifyElementText(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg_in_summary'), 'Platelet clumps are detected. Platelet count might be underestimated.')

				break;
			} else if(cellName.equals('Platelet Clumps') && currentValue.equalsIgnoreCase("Not detected")) {

				// Click dropdown to expand options
				act.moveToElement(dropDown).click().perform()

				// Wait for options to appear
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//ul[@role='listbox']//li[@role='option']")))

				// Get all options
				List<WebElement> options = driver.findElements(
						By.xpath("//ul[@role='listbox']//li[@role='option']"))

				if (options.size() < 2) {
					println("❌ Not enough options found for cell: " + cellName)
					continue
				}

				options.find { it.getText().trim().equalsIgnoreCase("Detected") }?.click()
				String changed_value = dropDown.getText().trim()
				assert changed_value.equals("Detected")
				WebElement dot_ele=dropDown.findElement(By.xpath('./div[1]'))
				String dot_color=dot_ele.getCssValue('background-color')
				println("detected color code "+dot_color)
				assert dot_color=='rgba(255, 0, 0, 1)'
				String strike_off_CSS=count_ele.getCssValue('text-decoration').split(" ")[0]
				println(strike_off_CSS)
				assert strike_off_CSS.equals('line-through')

				WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'))
				WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg'), 10)
				WebUI.verifyElementText(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg'), 'Platelet clumps are detected. Platelet count might be underestimated.')

				WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/button_Summary'))
				WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg_in_summary'), 10)
				WebUI.verifyElementText(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_detected_Note_msg_in_summary'), 'Platelet clumps are detected. Platelet count might be underestimated.')

				break;
			}
		}
	}
	
	public void selectRadioOption(String labelText) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement radio = driver.findElement(By.xpath("//span[text()='" + labelText + "']/preceding::input[1]"));
		if (!radio.isSelected() && radio.isEnabled()) {
			radio.click();
		}
	}
	
	public boolean isOptionDisabled(String labelText) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement radio = driver.findElement(By.xpath("//span[text()='" + labelText + "']/preceding::input[1]"));
		return !radio.isEnabled();
	}
public boolean isCalculatedLevelTextVisible() {
    boolean isVisible = false;
    try {
        WebDriver driver = DriverFactory.getWebDriver();
        By levelTextLocator = By.xpath("(//div[contains(@class,'plt-lvl-desc')]/following-sibling::div//span)[2]");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement levelTextElement = wait.until(ExpectedConditions.presenceOfElementLocated(levelTextLocator));

        isVisible = levelTextElement.isDisplayed();
        System.out.println("Calculated level text visibility: " + isVisible);
        return isVisible;

    } catch (TimeoutException | NoSuchElementException e) {
        System.out.println("Calculated level text not found or not visible. Exception: " + e.getMessage());
        return false;  // Safely return false if element isn't found or visible
    }
}
public boolean isManualLevelDropDownVisible() {
	boolean isVisible
	try {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement levelTextElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='combobox' and contains(@class,'MuiSelect-select')]")));

		isVisible = levelTextElement.isDisplayed();
		System.out.println("Manual level drop down visibility: " + isVisible);
		
	} catch (TimeoutException | NoSuchElementException e) {
		System.out.println("Calculated level text not found.");
		 isVisible=false
		 return isVisible
	}
}


public ArrayList<String> checkTheCurrentDetectedNotDetectedStatus() {
	WebDriver driver = DriverFactory.getWebDriver()
	Actions act = new Actions(driver)
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5))

	List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
			findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Cell_rows'), 10)
ArrayList<String> status= new ArrayList<>()
	for (WebElement cellRow : cellRows) {
		String cellName = cellRow.findElement(By.xpath(".//div[1]")).getText()
		WebElement count_ele = cellRow.findElement(By.xpath(".//div[2]"))
		WebElement dropDown = cellRow.findElement(By.xpath(".//div[3]"))
		String currentStatus = dropDown.getText().trim()
		status.add(currentStatus)
		}
		println(status)
		return status
	}

}
