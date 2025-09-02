package manual

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



public class helper {
	@Keyword
	public void verifyCorrectnessOfGradeAccordingToPercentageValue() {
		WebDriver driver = DriverFactory.getWebDriver()

		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'), 10
				)

		for (WebElement row : cellRows) {
			WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText().trim()

			String percentageText = percentageElement.getText().trim()

			// Skip if empty or invalid
			if (percentageText == null || percentageText.isEmpty()) {
				WebUI.comment("⚠️ Skipping '$cellname' - Percentage is empty.")
				continue
			}

			float percentageValue = 0.0
			try {
				percentageValue = Float.parseFloat(percentageText)
			} catch (NumberFormatException e) {
				WebUI.comment("❌ Skipping '$cellname' - Invalid format: '${percentageText}'")
				continue
			}

			List<WebElement> grades = row.findElements(By.xpath(".//input[@type='radio']"))

			for (int i = 0; i < grades.size(); i++) {
				if (grades.get(i).isSelected()) {
					int selectedGrade = Integer.parseInt(grades.get(i).getAttribute("value"))
					WebUI.comment("🧬 Cell: ${cellname} | %: ${percentageValue}")
					WebUI.comment("🎯 Selected Grade: ${selectedGrade}")

					switch (selectedGrade) {
						case 0:
							assert percentageValue == 0.0 : "❌ ${cellname}: Expected 0% for Grade 0, found ${percentageValue}"
							break
						case 1:
							assert percentageValue >= 0.0 && percentageValue <= 10.0 : "❌ ${cellname}: Expected [0-10]% for Grade 1, found ${percentageValue}"
							break
						case 2:
							assert percentageValue > 10.0 && percentageValue <= 20.0 : "❌ ${cellname}: Expected (10-20]% for Grade 2, found ${percentageValue}"
							break
						case 3:
							assert percentageValue > 20.0 : "❌ ${cellname}: Expected >20% for Grade 3, found ${percentageValue}"
							break
						default:
							WebUI.comment("⚠️ ${cellname}: Unexpected grade value ${selectedGrade}")
					}
					break // Grade is selected, move to next row
				}
			}
		}
	}
}



