package generic

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class scroll {
	
	
	@Keyword
	def checkCellImageCountExceedsLimit(int threshold = 9) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver

		
		List<WebElement> cellRows = driver.findElements(By.xpath(
		    "//div[contains(@class,'cell-table-tab')]//tr[td[1][not(contains(., 'Total'))] and td[2][text() != '-' and normalize-space(text()) != '']]"
		))
		
		for (WebElement row : cellRows) {
		    String cellName = row.findElement(By.xpath("./td[1]")).getText().trim()
		    String countText = row.findElement(By.xpath("./td[2]")).getText().trim()
		
		    // Only continue if count is numeric
		    if (!(countText ==~ /^\d+$/)) continue
		
		    // Click the row
		    row.click()
		    WebUI.comment("✅ Clicked on cell: ${cellName} with count ${countText}")
		

			// Scroll and collect images via JS
			String jsScrollAndCount = """
            var SCROLL_STEP = 200;
            var scrollContainer = document.querySelector('.List');
            if (!scrollContainer) return { error: 'Scroll container not found!' };

            var uniqueImages = new Set();
            var lastHeight = -1;

            function getVisibleImageSrcs() {
                var imgs = scrollContainer.querySelectorAll('img');
                imgs.forEach(function(img) {
                    var src = img.getAttribute('src');
                    if (src) {
                        uniqueImages.add(src);
                    }
                });
            }

            getVisibleImageSrcs();
            while (scrollContainer.scrollTop < scrollContainer.scrollHeight) {
                scrollContainer.scrollTop += SCROLL_STEP;
                getVisibleImageSrcs();
                if (scrollContainer.scrollTop === lastHeight) break;
                lastHeight = scrollContainer.scrollTop;
            }

            return {
                count: uniqueImages.size,
                images: Array.from(uniqueImages)
            };
            """

			def result = js.executeScript(jsScrollAndCount)

			if (result instanceof Map && result.containsKey('count')) {
				int imgCount = result.count as int
				WebUI.comment("🔬 ${cellName} - Images loaded: ${imgCount}")
				if (imgCount > threshold) {
					WebUI.comment("⚠️ ${cellName} has more than ${threshold} images!")
				}
			} else {
				WebUI.comment("❌ Could not scroll ${cellName} - Error: ${result?.error}")
			}
		}}
	
	
	@Keyword
	def checkScrollBehaviorBasedOnPatchCount() {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
	
		// Find all WBC rows (excluding "Total" and empty/‘-’ counts)
		List<WebElement> cellRows = driver.findElements(By.xpath(
			"//div[contains(@class,'cell-table-tab')]//tr[td[1][not(contains(., 'Total'))] and td[2][text() != '-' and normalize-space(text()) != '']]"
		))
	
		for (WebElement row : cellRows) {
			String cellName = row.findElement(By.xpath("./td[1]")).getText().trim()
			String countText = row.findElement(By.xpath("./td[2]")).getText().trim()
	
			// Only continue if count is numeric
			if (!(countText ==~ /^\d+$/)) continue
			int count = Integer.parseInt(countText)
	
			row.click()
			WebUI.delay(3)
			WebUI.comment("🔍 Checking cell: ${cellName} with count ${count}")
	
			String jsScrollAndCount = """
			const SCROLL_STEP = 200;
			const scrollContainer = document.querySelector('.List');
			if (!scrollContainer) return { error: 'Scroll container not found!' };

			const uniqueImages = new Set();
			let lastHeight = -1;

			function getVisibleImageSrcs() {
				const imgs = scrollContainer.querySelectorAll('img');
				imgs.forEach(img => {
					const src = img.getAttribute('src');
					if (src) uniqueImages.add(src);
				});
			}

			getVisibleImageSrcs();

			let scrollAttempts = 0;
			while (scrollContainer.scrollTop < scrollContainer.scrollHeight && scrollAttempts < 200) {
				scrollContainer.scrollTop += SCROLL_STEP;
				getVisibleImageSrcs();
				if (scrollContainer.scrollTop === lastHeight) break;
				lastHeight = scrollContainer.scrollTop;
				scrollAttempts++;
			}

			return {
				count: uniqueImages.size,
				scrollTop: scrollContainer.scrollTop,
				scrollHeight: scrollContainer.scrollHeight,
				clientHeight: scrollContainer.clientHeight,
				canScroll: scrollContainer.scrollHeight > scrollContainer.clientHeight,
				scrollAttempts: scrollAttempts
			};
		"""
	
			def result = js.executeScript(jsScrollAndCount)
	
			if (result instanceof Map && result.containsKey('count')) {
				int imgCount = result.count as int
				boolean canScroll = result.canScroll as boolean
				int attempts = result.scrollAttempts as int
				int scrollHeight = result.scrollHeight as int
				int clientHeight = result.clientHeight as int
	
				WebUI.comment("🧬 ${cellName} - Images: ${imgCount}, Can Scroll: ${canScroll}, Scroll Attempts: ${attempts}, Scroll Height: ${scrollHeight}, Client Height: ${clientHeight}")
	
				// Decision logic based on image count
				if (imgCount < 18) {
					if (!canScroll) {
						WebUI.comment("✅ ${cellName}: No scroll expected for <18 patches.")
					} else {
						WebUI.comment("❌ ${cellName}: Unexpected scroll for <18 patches.")
					}
				} else if (imgCount >= 18 && imgCount <= 50) {
					if (canScroll) {
						WebUI.comment("✅ ${cellName}: Scroll present as expected for 18–50 patches.")
					} else {
						WebUI.comment("❌ ${cellName}: Scroll missing where it should exist for 18–50 patches.")
					}
				} else if (imgCount > 50) {
					if (canScroll) {
						WebUI.comment("⚠️ ${cellName}: Infinite scroll expected and scrolling is working for >50 patches.")
					} else {
						WebUI.comment("❌ ${cellName}: Infinite scroll expected but scroll not detected for >50 patches.")
					}
				}
			} else {
				WebUI.comment("❌ ${cellName}: Error executing scroll JS - ${result?.error}")
			}
		}
	}
	
	
	
	
	
	
	@Keyword
	def checkScrollBehaviorBasedOnPatchCountForSplitView() {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
	
		// Find all WBC rows (excluding "Total" and empty/‘-’ counts)
		List<WebElement> cellRows = driver.findElements(By.xpath(
			"//div[contains(@class,'cell-table-tab')]//tr[td[1][not(contains(., 'Total'))] and td[2][text() != '-' and normalize-space(text()) != '']]"
		))
	
		for (WebElement row : cellRows) {
			String cellName = row.findElement(By.xpath("./td[1]")).getText().trim()
			String countText = row.findElement(By.xpath("./td[2]")).getText().trim()
	
			if (!(countText ==~ /^\d+$/)) continue
			int count = Integer.parseInt(countText)
	
			row.click()
			WebUI.delay(3)
			WebUI.comment("🔍 Checking cell: ${cellName} with count ${count}")
	
			String jsScrollAndCount = """
			const SCROLL_STEP = 200;
			const scrollContainer = document.querySelector('.List');
			if (!scrollContainer) return { error: 'Scroll container not found!' };

			const uniqueImages = new Set();
			let lastHeight = -1;

			function getVisibleImageSrcs() {
				const imgs = scrollContainer.querySelectorAll('img');
				imgs.forEach(img => {
					const src = img.getAttribute('src');
					if (src) uniqueImages.add(src);
				});
			}

			getVisibleImageSrcs();
			let scrollAttempts = 0;

			while (scrollContainer.scrollTop < scrollContainer.scrollHeight && scrollAttempts < 200) {
				scrollContainer.scrollTop += SCROLL_STEP;
				getVisibleImageSrcs();
				if (scrollContainer.scrollTop === lastHeight) break;
				lastHeight = scrollContainer.scrollTop;
				scrollAttempts++;
			}

			return {
				count: uniqueImages.size,
				scrollTop: scrollContainer.scrollTop,
				scrollHeight: scrollContainer.scrollHeight,
				clientHeight: scrollContainer.clientHeight,
				canScroll: scrollContainer.scrollHeight > scrollContainer.clientHeight,
				scrollAttempts: scrollAttempts
			};
		"""
	
			def result = js.executeScript(jsScrollAndCount)
	
			if (result instanceof Map && result.containsKey('count')) {
				int imgCount = result.count as int
				boolean canScroll = result.canScroll as boolean
				int scrollHeight = result.scrollHeight as int
				int clientHeight = result.clientHeight as int
	
				WebUI.comment("🔍 ${cellName} - Images: ${imgCount}, Can Scroll: ${canScroll}, Scroll Height: ${scrollHeight}, Client Height: ${clientHeight}")
	
				if (imgCount < 6) {
					if (!canScroll) {
						WebUI.comment("✅ ${cellName}: No scroll as expected for <6 patches.")
					} else {
						WebUI.comment("❌ ${cellName}: Unexpected scroll for <6 patches.")
					}
				} else if (imgCount == 6) {
					WebUI.comment("🔶 ${cellName}: Borderline case (=6 patches), Can Scroll: ${canScroll}")
				 
				} else if (imgCount > 6 && imgCount <= 50) {
					if (canScroll) {
						WebUI.comment("✅ ${cellName}: Scroll expected for >28 and <=50, and scroll is working.")
					} else {
						WebUI.comment("❌ ${cellName}: Scroll expected for >28 and <=50, but scrolling is not working.")
					}
				} else if (imgCount > 50) {
					if (canScroll) {
						WebUI.comment("⚠️ ${cellName}: Infinite scroll expected for >50, and scroll is working.")
					} else {
						WebUI.comment("❌ ${cellName}: Infinite scroll expected for >50, but scrolling is not working.")
					}
				}
			} else {
				WebUI.comment("❌ ${cellName}: Failed to execute JS - ${result?.error}")
			}
		}
	}
	
	}
