package generic

import java.text.SimpleDateFormat

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory


public class history {
	
	@Keyword
    def verifyReportDeletionFormat() {
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> stepContainers = driver.findElements(By.xpath("//div[contains(@class,'stepper-container')]"))

        boolean reportFound = false

        for (WebElement step : stepContainers) {
            try {
                String title = step.findElement(By.xpath(".//div[contains(@class,'stepper-title')]/div[1]")).getText().trim()

                if (title.equalsIgnoreCase("Reports deleted")) {
                    reportFound = true

                    // ✅ 1. Timestamp Validation
                    String rawTime = step.findElement(By.xpath(".//div[contains(@class,'time')]")).getText().replace(" (GMT)", "").trim()
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)

                    try {
                        sdf.parse(rawTime)
                        KeywordUtil.logInfo("✅ Timestamp format is valid: $rawTime")
                    } catch (Exception e) {
                        KeywordUtil.markFailed("❌ Invalid timestamp format: $rawTime")
                    }

                    // ✅ 2. Deletion Message Format Validation
                    String descText = step.findElement(By.xpath(".//div[contains(@class,'desc')]")).getText().trim()
                    if (descText ==~ /Deletion of \d+ reports from storage was successful/) {
                        KeywordUtil.logInfo("✅ Deletion message is correctly formatted: $descText")
                    } else {
                        KeywordUtil.markFailed("❌ Deletion message format incorrect: $descText")
                    }

                    // ✅ 3. Copy Slide ID Text and Icon
                    try {
                        WebElement slideText = step.findElement(By.xpath(".//span[contains(text(),'Copy list Slide IDs')]"))
                        WebElement copyIcon = step.findElement(By.xpath(".//img[contains(@src,'copy')]"))

                        if (slideText.isDisplayed() && copyIcon.isDisplayed()) {
                            KeywordUtil.logInfo("✅ 'Copy list Slide IDs' text and copy icon found")
                        } else {
                            KeywordUtil.markFailed("❌ 'Copy list Slide IDs' text or icon not visible")
                        }
                    } catch (Exception e) {
                        KeywordUtil.markFailed("❌ 'Copy list Slide IDs' section or icon not found")
                    }

                    break // No need to continue loop after finding it
                }
            } catch (Exception e) {
                KeywordUtil.logInfo("Skipping malformed history entry")
                continue
            }
        }

        if (!reportFound) {
            KeywordUtil.markFailed("❌ 'Reports deleted' event not found in history")
        }
    }
	
	@Keyword
	def verifyRetentionDurationUpdates() {
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> steps = driver.findElements(By.xpath("//div[contains(@class,'stepper-container')]"))
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)

		boolean found = false
		int retentionEventCount = 0

		for (WebElement step : steps) {
			try {
				String title = step.findElement(By.xpath(".//div[contains(@class,'stepper-title')]/div[1]")).getText().trim()

				if (title.equalsIgnoreCase("Retention duration")) {
					found = true
					retentionEventCount++

					// Validate timestamp format
					String rawTime = step.findElement(By.xpath(".//div[contains(@class,'time')]")).getText().replace(" (GMT)", "").trim()
					try {
						sdf.parse(rawTime)
						KeywordUtil.logInfo("✅ Valid timestamp format: $rawTime")
					} catch (Exception e) {
						KeywordUtil.markFailed("❌ Invalid timestamp format: $rawTime")
					}

					// Validate description
					String descText = step.findElement(By.xpath(".//div[contains(@class,'desc')]")).getText().trim()
					if (descText.equalsIgnoreCase("retentionUpdated")) {
						KeywordUtil.logInfo("✅ Correct description text: $descText")
					} else {
						KeywordUtil.markFailed("❌ Unexpected description text: $descText")
					}

					// Validate value change format
					List<WebElement> spanElements = step.findElements(By.xpath(".//div[contains(@class,'updated-container')]//span"))
					if (spanElements.size() == 2) {
						String oldValue = spanElements.get(0).getText().trim()
						String newValue = spanElements.get(1).getText().trim()

						if (oldValue.isInteger() && newValue.isInteger()) {
							KeywordUtil.logInfo("✅ Retention updated from $oldValue to $newValue")
						} else {
							KeywordUtil.markFailed("❌ Values are not valid integers: $oldValue ➜ $newValue")
						}
					} else {
						KeywordUtil.markFailed("❌ Could not find two values for retention change")
					}
				}
			} catch (Exception e) {
				KeywordUtil.logInfo("Skipping malformed entry")
				continue
			}
		}

		if (!found) {
			KeywordUtil.markFailed("❌ No 'Retention duration' update events found in history")
		} else {
			KeywordUtil.logInfo("✅ Found $retentionEventCount 'Retention duration' update event(s)")
		}
	}
	
}
