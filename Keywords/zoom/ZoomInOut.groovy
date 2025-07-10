package zoom
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage
import java.time.Duration
import java.util.regex.*;

import javax.imageio.ImageIO

import org.openqa.selenium.By
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.asserts.SoftAssert

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import CustomKeywords




public class ZoomInOut {

	public void zoominout(int num_itr, TestObject button) {
		//def blurCheck = new blurChecker()

		WebDriver driver =DriverFactory.getWebDriver()
		//WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Patches'),20)
		WebUI.delay(10)
		// Get all cell rows
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)


		for (WebElement row : cellRows) {
			try {
				WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
				WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
				String cellname = cellname_ele.getText().trim()
				println("🧬 Checking cell: " + cellname)

				String percentageText = percentageElement.getText().trim()
				if (percentageText == null || percentageText.isEmpty()) {
					WebUI.comment("⚠️ Skipping row due to empty percentage value.")
					continue
				}

				float percentage_flaot_value = Float.parseFloat(percentageText)

				// Click the row first (to attempt patch loading)
				row.click()
				WebUI.delay(2)

				// Check if patch elements are present
				List<WebElement> patchCards = driver.findElements(By.xpath("//div[@class='Card patches-container']"))

				if (patchCards == null || patchCards.size() == 0) {
					WebUI.comment("⛔ No patches found for " + cellname + ". Skipping this row.")
					continue
				}

				// Skip certain cell types regardless of patch count
				if (cellname.equals("Anisocytosis") || cellname.equals("Acanthocytes*") ||
				cellname.equals("Sickle Cells*") || cellname.equals("Stomatocytes")) {
					WebUI.comment("🔕 Skipping manually graded cell: " + cellname)
					continue
				}

				// Click "Home Zoom" before any pan/zoom if current zoom is Zoom In
				String zoomId = button.getObjectId().toLowerCase()
				if (zoomId.contains("zoom_in")) {
					WebUI.comment("🏠 Clicking Home Zoom before starting iterations for: $cellname")
					WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/Home_zoom_CTA'))
					WebUI.delay(5)
				}

				// Proceed to patch zoom check
				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 30)
				WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))
				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas'), 30)
				WebUI.delay(5)

				//=== Function to get base64 image from canvas ===
				def getCanvasImageBase64 = {
					JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
					String base64 = js.executeScript("""
	       const canvas = document.querySelector('#pbs-volumeViewport canvas');
	       return canvas.toDataURL('image/png');
	   """)
					return base64
				}


				for(int i=1; i<=num_itr;i++) {
					WebUI.waitForElementVisible(button, 30)

					// === Step 1: Capture canvas before zoom ===
					WebUI.comment("Capturing canvas before zoom in...")
					String beforePanBase64 = getCanvasImageBase64()

					TestObject scaleBar = findTestObject('Object Repository/RBC_Objects/Page_PBS/Scale_bar')
					String scaleBarValue = WebUI.getText(scaleBar)
					WebUI.comment("Scale bar value: " + scaleBarValue)

					// === Step 2:
					WebUI.click(button)
					WebUI.delay(5) // Let canvas settle

					// === Step 3: Capture canvas after zoom ===
					WebUI.comment("Capturing canvas after zoom in...")
					String afterPanBase64 = getCanvasImageBase64()

					String scaleBarValue1 = WebUI.getText(scaleBar)
					WebUI.comment("Scale bar value: " + scaleBarValue1)

					// === Step 4: Compare base64 data ===
					if (beforePanBase64 == afterPanBase64) {
						WebUI.comment(":x: Canvas did NOT change ")
					} else {
						WebUI.comment(":white_check_mark: Canvas has changed ")
					}

					if (scaleBarValue == scaleBarValue1) {
						WebUI.comment(":x: Scale bar value did not change ")
					} else {
						WebUI.comment(":white_check_mark: Scale bar value has changed from "+scaleBarValue+" to "+scaleBarValue1)
					}

					// === Step 5: Blur check ===
					//boolean isBlurry = blurCheck.isCanvasImageBlurry(afterPanBase64)
					boolean isBlurry = blurCheck.isCanvasImageBlurry()
					if (isBlurry) {
						WebUI.comment("⚠️ Canvas image is blurry after zoom.")
					} else {
						WebUI.comment("✅ Canvas image is sharp after zoom.")
					}
				}
			}
			catch (Exception e) {
				WebUI.comment("❌ Error processing row: " + e.getMessage())
			}
		}
	}

	// Place this outside the zoominout method, at class-level if applicable
	def saveBase64Image(String base64, String filePath) {
		String base64Data = base64.split(",")[1]
		byte[] imageBytes = Base64.decoder.decode(base64Data)
		InputStream is = new ByteArrayInputStream(imageBytes)
		BufferedImage image = ImageIO.read(is)
		ImageIO.write(image, "png", new File(filePath))
	}

	//	public void checkPanningFunctionality() {
	//		WebDriver driver = DriverFactory.getWebDriver()
	//		WebUI.delay(10)
	//
	//		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
	//			findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'), 1)
	//
	//		for (WebElement row : cellRows) {
	//			try {
	//				WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
	//				WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
	//				String cellname = cellname_ele.getText().trim()
	//				println("🧬 Checking cell: " + cellname)
	//
	//				String percentageText = percentageElement.getText().trim()
	//				if (percentageText == null || percentageText.isEmpty() || percentageText.equals('0.0')) {
	//					WebUI.comment("⚠️ Skipping row due to empty percentage value.")
	//					continue
	//				}
	//
	//				float percentage_float_value = Float.parseFloat(percentageText)
	//
	//				row.click()
	//				WebUI.delay(2)
	//
	//				List<WebElement> patchCards = driver.findElements(By.xpath("//div[@class='Card patches-container']"))
	//				if (patchCards == null || patchCards.size() == 0) {
	//					WebUI.comment("⛔ No patches found for " + cellname + ". Skipping this row.")
	//					continue
	//				}
	//
	//				if (cellname.equals("Anisocytosis") || cellname.equals("Acanthocytes*") ||
	//					cellname.equals("Sickle Cells*") || cellname.equals("Stomatocytes")) {
	//					WebUI.comment("🔕 Skipping manually graded cell: " + cellname)
	//					continue
	//				}
	//
	//				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 30)
	//				WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))
	//				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas'), 30)
	//				WebUI.delay(2)
	//
	//				def getCanvasImageBase64 = {
	//					JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
	//					String base64 = js.executeScript("""
	//					const canvas = document.querySelector('#pbs-volumeViewport canvas');
	//					return canvas.toDataURL('image/png');
	//				""")
	//					return base64
	//				}
	//
	//
	//					// === Inserted Pan Functionality ===
	//
	//					// === Function to get base64 image from canvas ===
	//					def getCanvasImageBase64_1 = {
	//						JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
	//						String base64 = js.executeScript("""
	//						const canvas = document.querySelector('#pbs-volumeViewport canvas');
	//						return canvas.toDataURL('image/png');
	//					""")
	//						return base64
	//					}
	//
	////					// === Function to save base64 PNG to file (optional) ===
	////					def saveBase64Image(String base64, String filePath) {
	////						String base64Data = base64.split(",")[1]
	////						byte[] imageBytes = Base64.decoder.decode(base64Data)
	////						InputStream is = new ByteArrayInputStream(imageBytes)
	////						BufferedImage image = ImageIO.read(is)
	////						ImageIO.write(image, "png", new File(filePath))
	////					}
	//
	//					// === Step 1: Capture canvas before pan ===
	//					WebUI.comment("Capturing canvas before pan...")
	//					String beforePanBase64 = getCanvasImageBase64_1()
	//					saveBase64Image(beforePanBase64, "before_pan.png")
	//					println(beforePanBase64)
	//
	//					// === Step 2: Perform pan with Robot ===
	//					int startX = 1100
	//					int startY = 550
	//					int dragX = 200
	//					int dragY = 100
	//					int steps = 50
	//
	//					Robot robot = new Robot()
	//					robot.setAutoDelay(10)
	//					robot.mouseMove(startX, startY)
	//					Thread.sleep(200)
	//					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
	//					Thread.sleep(100)
	//					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
	//					Thread.sleep(300)
	//
	//					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
	//					Thread.sleep(200)
	//					for (int j = 0; j <= steps; j++) {
	//						int moveX = startX + (dragX * j / steps)
	//						int moveY = startY + (dragY * j / steps)
	//						robot.mouseMove(moveX, moveY)
	//						Thread.sleep(10)
	//					}
	//					Thread.sleep(100)
	//					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
	//					WebUI.delay(1)
	//
	//					// === Step 3: Capture canvas after pan ===
	//					WebUI.comment("Capturing canvas after pan...")
	//					String afterPanBase64 = getCanvasImageBase64_1()
	//					saveBase64Image(afterPanBase64, "after_pan.png")
	//
	//					// === Step 4: Compare base64 data ===
	//					if (beforePanBase64 == afterPanBase64) {
	//						WebUI.comment(":x: Canvas did NOT change — pan may have failed.")
	//					} else {
	//						WebUI.comment(":white_check_mark: Canvas has changed — pan likely succeeded.")
	//					}
	//
	//			} catch (Exception e) {
	//				WebUI.comment("❌ Error processing row: " + e.getMessage())
	//			}
	//		}
	//	}
	//

	//multiple iterration
	public void checkPanningFunctionality2(TestObject zoomCTA, int iterations) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebUI.delay(10)

		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'), 1)

		for (WebElement row : cellRows) {
			try {
				WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
				WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
				String cellname = cellname_ele.getText().trim()
				println("🧬 Checking cell: " + cellname)

				String percentageText = percentageElement.getText().trim()
				if (percentageText == null || percentageText.isEmpty() || percentageText.equals('0.0')) {
					WebUI.comment("⚠️ Skipping row due to empty percentage value.")
					continue
				}

				float percentage_float_value = Float.parseFloat(percentageText)

				row.click()
				WebUI.delay(2)

				List<WebElement> patchCards = driver.findElements(By.xpath("//div[@class='Card patches-container']"))
				if (patchCards == null || patchCards.size() == 0) {
					WebUI.comment("⛔ No patches found for " + cellname + ". Skipping this row.")
					continue
				}

				if (cellname.equals("Anisocytosis") || cellname.equals("Acanthocytes*") ||
				cellname.equals("Sickle Cells*") || cellname.equals("Stomatocytes")) {
					WebUI.comment("🔕 Skipping manually graded cell: " + cellname)
					continue
				}

				// Click "Home Zoom" before any pan/zoom if current zoom is Zoom In
				String zoomId = zoomCTA.getObjectId().toLowerCase()
				if (zoomId.contains("zoom_in")) {
					WebUI.comment("🏠 Clicking Home Zoom before starting iterations for: $cellname")
					WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/Home_zoom_CTA'))
					WebUI.delay(1)
				}

				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 30)
				WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))
				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas'), 30)
				WebUI.delay(2)

				// Function to get base64 image of canvas
				def getCanvasImageBase64 = {
					JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
					return js.executeScript("""
					const canvas = document.querySelector('#pbs-volumeViewport canvas');
					return canvas.toDataURL('image/png');
				""")
				}

				// Function to save canvas image
				def saveBase64Image = {
					String base64, String filePath ->
					String base64Data = base64.split(",")[1]
					byte[] imageBytes = Base64.decoder.decode(base64Data)
					InputStream is = new ByteArrayInputStream(imageBytes)
					BufferedImage image = ImageIO.read(is)
					ImageIO.write(image, "png", new File(filePath))
				}


				if (iterations == 0) {
					WebUI.comment("📌 Performing pan without any zoom click")

					// Capture before pan
					String beforePanBase64 = getCanvasImageBase64()
					saveBase64Image(beforePanBase64, "before_pan_${cellname}_nozoom.png")

					// Pan code (same as before)
					int startX = 1100, startY = 550, dragX = 200, dragY = 100, steps = 50
					Robot robot = new Robot()
					robot.setAutoDelay(10)
					robot.mouseMove(startX, startY)
					Thread.sleep(200)
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
					Thread.sleep(100)
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
					Thread.sleep(300)

					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
					Thread.sleep(200)
					for (int j = 0; j <= steps; j++) {
						int moveX = startX + (dragX * j / steps)
						int moveY = startY + (dragY * j / steps)
						robot.mouseMove(moveX, moveY)
						Thread.sleep(10)
					}
					Thread.sleep(100)
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
					WebUI.delay(1)

					// Capture after pan
					String afterPanBase64 = getCanvasImageBase64()
					saveBase64Image(afterPanBase64, "after_pan_${cellname}_nozoom.png")

					// Compare
					if (beforePanBase64 == afterPanBase64) {
						WebUI.comment(":x: Canvas did NOT change — pan may have failed.")
					} else {
						WebUI.comment(":white_check_mark: Canvas changed — pan succeeded.")
					}
					continue;
				}

				// Perform zoom + pan for each iteration
				for (int i = 1; i <= iterations; i++) {
					WebUI.comment("🔍 Zoom iteration #$i")

					// Click zoom button
					WebUI.click(zoomCTA)
					WebUI.delay(1)

					// Capture canvas before pan
					String beforePanBase64 = getCanvasImageBase64()
					saveBase64Image(beforePanBase64, "before_pan_${cellname}_${i}.png")

					// Pan using Robot
					int startX = 1100, startY = 550, dragX = 200, dragY = 100, steps = 50
					Robot robot = new Robot()
					robot.setAutoDelay(10)
					robot.mouseMove(startX, startY)
					Thread.sleep(200)
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
					Thread.sleep(100)
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
					Thread.sleep(300)

					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
					Thread.sleep(200)
					for (int j = 0; j <= steps; j++) {
						int moveX = startX + (dragX * j / steps)
						int moveY = startY + (dragY * j / steps)
						robot.mouseMove(moveX, moveY)
						Thread.sleep(10)
					}
					Thread.sleep(100)
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
					WebUI.delay(1)

					// Capture canvas after pan
					String afterPanBase64 = getCanvasImageBase64()
					saveBase64Image(afterPanBase64, "after_pan_${cellname}_${i}.png")

					// Compare
					if (beforePanBase64 == afterPanBase64) {
						WebUI.comment(":x: Canvas did NOT change after pan in iteration #$i — pan may have failed.")
					} else {
						WebUI.comment(":white_check_mark: Canvas changed after pan in iteration #$i — pan succeeded.")
					}
				}
			} catch (Exception e) {
				WebUI.comment("❌ Error processing row: " + e.getMessage())
			}
		}
	}

	public void verifyZoomInOutLimits(String zoomType, int num_itr, TestObject button) {

		WebUI.delay(10)

		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'), 1)

		WebDriver driver = DriverFactory.getWebDriver()

		// Define expected scale bars
		List<String> zoomInScales = [
			'500 μm',
			'500 μm',
			'200 μm',
			'100 μm',
			'50 μm',
			'20 μm',
			'10 μm',
			'5 μm'
		]
		List<String> zoomOutScales = [
			'20 μm',
			'50 μm',
			'100 μm',
			'200 μm',
			'500 μm',
			'1000 μm',
			'1000 μm'
		]

		for (WebElement row : cellRows) {
			try {
				WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
				WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
				String cellname = cellname_ele.getText().trim()
				println("🔬 Processing cell: " + cellname)

				float percentage_flaot_value = Float.parseFloat(percentageElement.getText().trim())

				// Click the row to reveal patches
				row.click()
				WebUI.delay(2)

				List<WebElement> patches = driver.findElements(By.xpath("//div[@class='Card patches-container']"))
				int total_no_of_patches = patches.size()
				println("📦 Total number of patches: " + total_no_of_patches)

				if (total_no_of_patches > 0 && !cellname.equals("Anisocytosis") &&
				!cellname.equals("Acanthocytes*") &&
				!cellname.equals("Sickle Cells*") &&
				!cellname.equals("Stomatocytes")) {

					// Open patch image
					WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 30)
					WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))
					WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas'), 30)
					WebUI.delay(5)

					def getCanvasImageBase64 = {
						JavascriptExecutor js = (JavascriptExecutor) driver
						return js.executeScript("""
						const canvas = document.querySelector('#pbs-volumeViewport canvas');
						return canvas.toDataURL('image/png');
					""")
					}

					TestObject scaleBar = findTestObject('Object Repository/RBC_Objects/Page_PBS/Scale_bar')

					// ==== HOME ZOOM TEST ====
					if (zoomType.equalsIgnoreCase("home")) {
						WebUI.comment("🏠 Testing home zoom...")

						WebUI.click(button) // Click the home button
						WebUI.delay(2)
						String scale = WebUI.getText(scaleBar)
						WebUI.comment("🔍 Scale bar after home zoom: " + scale)
						assert scale == "1000 μm" : "Expected scale bar to be 1000 μm but got: " + scale
						continue
					}

					// ==== ZOOM IN TEST ====
					if (zoomType.equalsIgnoreCase("in")) {
						// Only reset zoom for zoom-in tests
						TestObject homeBtn = findTestObject('Object Repository/RBC_Objects/Page_PBS/Home_zoom_CTA')
						WebUI.comment("🔄 Resetting zoom via home button...")
						WebUI.click(homeBtn)
						WebUI.delay(5)
					}

					List<String> expectedScales = zoomType.equalsIgnoreCase("in") ? zoomInScales : zoomOutScales

					for (int i = 0; i < num_itr && i < expectedScales.size(); i++) {
						WebUI.waitForElementVisible(button, 30)
						WebUI.comment("🔁 [" + zoomType + "] Attempt " + (i + 1))

						String beforeImage = getCanvasImageBase64()
						String beforeScale = WebUI.getText(scaleBar)

						WebUI.click(button)
						WebUI.delay(5)

						String afterImage = getCanvasImageBase64()
						String afterScale = WebUI.getText(scaleBar)

						// Canvas check
						if (beforeImage == afterImage) {
							WebUI.comment("⚠️ Canvas did NOT change")
						} else {
							WebUI.comment("✅ Canvas changed")
						}

						// Scale bar assertion
						String expectedScale = expectedScales[i]
						WebUI.comment("Expected: " + expectedScale + " | Actual: " + afterScale)
						assert afterScale == expectedScale : "❌ Scale bar mismatch at step " + (i + 1) + ": expected " + expectedScale + ", got " + afterScale
					}
				}
			} catch (Exception e) {
				WebUI.comment("❌ Error in row processing: " + e.getMessage())
			}
		}
	}

	public void zoominoutCheckDigitalZoomOnlyMsg(TestObject button) {

		WebUI.delay(10)

		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'), 1)

		WebDriver driver = DriverFactory.getWebDriver()

		for (WebElement row : cellRows) {
			try {
				WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
				WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
				String cellname = cellname_ele.getText().trim()
				println("🧬 Checking cell: " + cellname)

				String percentageText = percentageElement.getText().trim()
				if (percentageText == null || percentageText.isEmpty()) {
					WebUI.comment("⚠️ Skipping row due to empty percentage value.")
					continue
				}

				float percentage_flaot_value = Float.parseFloat(percentageText)

				// Click the row first (to attempt patch loading)
				row.click()
				WebUI.delay(2)

				// Check if patch elements are present
				List<WebElement> patchCards = driver.findElements(By.xpath("//div[@class='Card patches-container']"))

				if (patchCards == null || patchCards.size() == 0) {
					WebUI.comment("⛔ No patches found for " + cellname + ". Skipping this row.")
					continue
				}

				// Skip certain cell types regardless of patch count
				if (cellname.equals("Anisocytosis") || cellname.equals("Acanthocytes*") ||
				cellname.equals("Sickle Cells*") || cellname.equals("Stomatocytes")) {
					WebUI.comment("🔕 Skipping manually graded cell: " + cellname)
					continue
				}

				// Proceed to patch zoom check
				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'), 30)
				WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))
				WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas'), 30)
				WebUI.delay(2)

				def getCanvasImageBase64 = {
					JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
					String base64 = js.executeScript("""
					const canvas = document.querySelector('#pbs-volumeViewport canvas');
					return canvas.toDataURL('image/png');
				""")
					return base64
				}

				int maxAttempts = 10
				boolean digitalZoomDetected = false

				for (int attempt = 1; attempt <= maxAttempts; attempt++) {

					WebUI.waitForElementVisible(button, 30)
					WebUI.comment("🔍 Zoom attempt " + attempt)

					String beforeZoom = getCanvasImageBase64()
					String beforeScale = WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/Scale_bar'))

					WebUI.click(button)
					WebUI.delay(3)

					String afterZoom = getCanvasImageBase64()
					String afterScale = WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/Scale_bar'))

					// Log differences
					if (!beforeZoom.equals(afterZoom)) {
						WebUI.comment("✅ Canvas changed.")
					} else {
						WebUI.comment("❌ Canvas did not change.")
					}

					if (!beforeScale.equals(afterScale)) {
						WebUI.comment("✅ Scale bar changed from " + beforeScale + " to " + afterScale)
					} else {
						WebUI.comment("❌ Scale bar did not change.")
					}

					// Check for digital zoom message and icon
					boolean zoomMsg = WebUI.verifyElementPresent(findTestObject('RBC_Objects/Page_PBS/p_Digital zoom only'), 3, FailureHandling.OPTIONAL)
					boolean zoomIcon = WebUI.verifyElementPresent(findTestObject('RBC_Objects/Page_PBS/i_icon_digital_zoom_only'), 3, FailureHandling.OPTIONAL)

					if (zoomMsg && zoomIcon) {
						WebUI.comment("✅ Digital zoom only message and icon detected.")
						KeywordUtil.markPassed("Digital zoom detected for cell: " + cellname)
						digitalZoomDetected = true
						break
					} else {
						WebUI.comment("🔁 Digital zoom not detected yet.")
					}
				}

				if (!digitalZoomDetected) {
					KeywordUtil.markFailed("❌ Digital zoom NOT detected after max attempts for " + cellname)
				}
			} catch (Exception e) {
				WebUI.comment("❌ Error processing row: " + e.getMessage())
			}
		}
	}






	public void iterateThroughTheCellRows() {
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)
		ArrayList<String> al_RBC_cellnames= new ArrayList<>(Arrays.asList("Microcytes", "Macrocytes", "Ovalocytes","Elliptocytes","Teardrop Cells","Fragmented Cells","Target Cells","Echinocytes"));
		for (WebElement row : cellRows) {
			// Get the percentage element (last div inside the row)
			//WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			println(cellname)
			if(al_RBC_cellnames.contains(cellname)) {
				println(cellname+"is present")
				assert true;
			}
			else {
				println("found a diffrent cell"+cellname)
				assert false;
			}
		}
	}
	public void iterateThroughPathes(String TestCase) {
		//WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Patches'),20)
		WebUI.delay(10)
		// Get all cell rows
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)

		for (WebElement row : cellRows) {
			int count=0;
			// Get the percentage element (last div inside the row)
			row.click()
			WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			println(cellname)


			def getCanvasImageBase64 = {
				JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
				String base64 = js.executeScript("""
					const canvas = document.querySelector('#pbs-volumeViewport canvas');
					return canvas.toDataURL('image/png');
				""")
				return base64
			}


			if(cellname.equals("Acanthocytes*") || cellname.equals("Sickle Cells*") || cellname.equals("Stomatocytes*")) {
				KeywordUtil.logInfo("Skipping iteration because as "+ cellname +"is manuvally graded")
				continue;
			}

			// Get the text value
			float percentage_flaot_value = Float.parseFloat(percentageElement.getText())
			println(percentage_flaot_value)
			List<WebElement> patches = WebUiCommonHelper.findWebElements(
			findTestObject('Object Repository/RBC_Objects/Page_PBS/Patches'),20)
			WebDriver driver = DriverFactory.getWebDriver()
			String no_patches_msg=driver.findElement(By.className("patches-section")).getText()

			Actions act= new Actions(driver)

			int total_no_of_patches=patches.size()
			println(total_no_of_patches)
			println(no_patches_msg)
			if (patches.size() == 0) {
				KeywordUtil.logInfo("No patch elements found for "+cellname+" Skipping iteration.")
				return // or throw new StepFailedException("No patch elements found for")
			}
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

			if(no_patches_msg.contains("No patches available") || no_patches_msg.contains("Please review individual cell types for viewing patches") || total_no_of_patches<0) {

				break;
			}
			else{
				for (int i = 1; i < total_no_of_patches; i++) {
					// ✅ Re-locate element during each iteration
					WebElement patch = driver.findElements(By.xpath('(//div[@class="Card patches-container"]/div)')).get(i)

					// Example interaction
					if(TestCase.equals("checking consistancy on patches between patch view and split_View")) {
						act.doubleClick(patch).build().perform()
						WebUI.delay(8)
						WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/patch_view_button'))
					}
					else {

						WebUI.click(findTestObject('RBC_Objects/Page_PBS/split-view_button'))
						String beforeChangingPatch = getCanvasImageBase64()
						act.doubleClick(patch).build().perform()
						WebUI.delay(8)
						String afterChangingPatch = getCanvasImageBase64()

						// Log differences
						if (!beforeChangingPatch.equals(afterChangingPatch)) {
							WebUI.comment("✅ Canvas changed after selecting the different patch .")
						} else {
							WebUI.comment("❌ Canvas did not changed after selecting the different patch .")
						}
					}
					if(i==2) {
						break;
					}
				}
			}
		}
	}


	public void verifyRegrade() {
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)
		for (WebElement row : cellRows) {
			//Get the percentage element (last div inside the row)
			row.click()
			String cellname=row.findElement(By.xpath(".//div")).getText()
			String cellname_removed=cellname.replace("*", "")

			println(cellname)

			//Getting the color of an significant/non-significant dot
			//			WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
			//			String dotColor = dot.getCssValue("background-color");
			//			System.out.println("Dot color: " + dotColor);

			List<WebElement> grades= row.findElements(By.xpath(".//input[@type='radio']"))
			println(grades.size())

			for (WebElement option : grades) {
				if (option.isSelected()) {
					System.out.println("Selected option value: " + option.getAttribute("value"));
					break;
				}
			}
			if(cellname.equals('Acanthocytes*') || cellname.equals('Stomatocytes*') || cellname.equals('Howell-Jolly Bodies*') || cellname.equals('Pappenheimer Bodies*') || cellname.equals('Basophilic Stippling*')) {
				for(int i=0; i<grades.size();i++) {
					grades.get(i).click()

					try {
						TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
						WebUI.waitForElementVisible(toastMsg, 10)


						TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
						WebUI.waitForElementVisible(regradeMsgObj, 10)
						String regrade_msg = WebUI.getText(regradeMsgObj).trim()



						TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
						WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
						String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim()

						println("Regrade message: " + regrade_msg)
						println("Signifcant status message: " + signifiacnt_status_msg)
						if(i==0) {
							assert regrade_msg == (cellname_removed + ' regraded from - to ' + i)
							if(i==0 || i==1) {
								assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
								//Getting the color of an non-significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(50, 152, 93, 1)"//this green-color-code is as per the requirement
							}
							else {
								assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
								//Getting the color of an significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(198, 27, 28, 1)"//this red-color-code is as per the requirement
							}


							CustomKeywords.'zoom.ZoomInOut.closeToastIfVisible'()


							println('closed a toast msg')
						}
						else {
							assert regrade_msg == (cellname_removed + ' regraded from ' + (i - 1) + ' to ' + i)
							if(i==0 || i==1) {
								assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
								//Getting the color of an non-significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(50, 152, 93, 1)"
							}
							else {
								assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
								//Getting the color of an significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(198, 27, 28, 1)"
							}


							CustomKeywords.'zoom.ZoomInOut.closeToastIfVisible'()


							println('closed a toast msg')
						}
					} catch (Exception e) {
						println('⚠️ toast message is not visible: ' + e.getMessage())
					}
					//Assertion for significant and non-significant
				}

				//reserve loop to regrade from 3 to 0
				for(int i=(grades.size()-2); i>=0;i--) {

					grades.get(i).click()

					try {
						TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
						WebUI.waitForElementVisible(toastMsg, 10)


						TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
						WebUI.waitForElementVisible(regradeMsgObj, 10)
						String regrade_msg = WebUI.getText(regradeMsgObj).trim()

						TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
						WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
						String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim()

						println("Regrade message: " + regrade_msg)
						println("Signifcant status message: " + signifiacnt_status_msg)
						if(i==0) {
							assert regrade_msg == (cellname_removed + ' regraded from '+(i+1) +' to '+i)
							if(i==0 || i==1) {
								assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
								//Getting the color of an non-significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(50, 152, 93, 1)"
							}
							else {
								assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
								//Getting the color of an significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(198, 27, 28, 1)"
							}

							CustomKeywords.'zoom.ZoomInOut.closeToastIfVisible'()


							println('closed a toast msg')
						}
						else {
							assert regrade_msg == (cellname_removed + ' regraded from ' + (i+1) + ' to ' + i)
							if(i==0 || i==1) {
								assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
								//Getting the color of an non-significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(50, 152, 93, 1)"
							}
							else {
								assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
								//Getting the color of an significant dot
								WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
								String dotColor = dot.getCssValue("background-color");
								System.out.println("Dot color: " + dotColor);
								assert dotColor=="rgba(198, 27, 28, 1)"
							}

							CustomKeywords.'zoom.ZoomInOut.closeToastIfVisible'()



							println('closed a toast msg')
						}
					} catch (Exception e) {
						println('⚠️ toast message is not visible: ' + e.getMessage())
					}
				}
			}

			else {
				boolean grade_selection_status=grades[0].isSelected() //checking is 0 grade radio button selected
				if(!grade_selection_status) {
					grades[0].click()
				}
				for(int i=1; i<grades.size();i++) {
					grades.get(i).click()
					WebUI.delay(1)
					try {
						TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
						WebUI.waitForElementVisible(toastMsg, 10)


						TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
						WebUI.waitForElementVisible(regradeMsgObj, 10)
						String regrade_msg = WebUI.getText(regradeMsgObj).trim()

						TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
						WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
						String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim()

						println("Regrade message: " + regrade_msg)
						println("Signifcant status message: " + signifiacnt_status_msg)
						assert regrade_msg == (cellname_removed + ' regraded from ' + (i - 1) + ' to ' + i)
						boolean isSpecialCell = cellname.equalsIgnoreCase("Fragmented Cells") || cellname.equalsIgnoreCase("Sickle Cells*")

						if ((isSpecialCell && i == 0) || (!isSpecialCell && (i == 0 || i == 1))) {
							// Non-significant
							assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"))
							String dotColor = dot.getCssValue("background-color")
							System.out.println("Dot color: " + dotColor)
							assert dotColor == "rgba(50, 152, 93, 1)" // Green
						} else {
							// Significant
							assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"))
							String dotColor = dot.getCssValue("background-color")
							System.out.println("Dot color: " + dotColor)
							assert dotColor == "rgba(198, 27, 28, 1)" // Red
						}


						WebUI.delay(1)
						CustomKeywords.'zoom.ZoomInOut.closeToastIfVisible'()
					} catch (Exception e) {
						println('⚠️ toast message is not visible: ' + e.getMessage())
					}
					println('closed a toast msg')
				}
				//reserve loop to regrade from 3 to 0
				for(int i=(grades.size()-2); i>=0;i--) {
					grades.get(i).click()
					WebUI.delay(1)
					try {
						TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
						WebUI.waitForElementVisible(toastMsg, 10)


						TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
						WebUI.waitForElementVisible(regradeMsgObj, 10)
						String regrade_msg = WebUI.getText(regradeMsgObj)

						TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
						WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
						String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele)

						println("Regrade message: " + regrade_msg)
						println("Signifcant status message: " + signifiacnt_status_msg)

						assert regrade_msg == (cellname_removed + ' regraded from ' + (i+1) + ' to ' + i)
						// Check for special cell types
						boolean isSpecialCell = cellname.equalsIgnoreCase("Fragmented Cells") || cellname.equalsIgnoreCase("Sickle Cells*")

						if ((isSpecialCell && i == 0) || (!isSpecialCell && (i == 0 || i == 1))) {
							// Non-significant
							assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"))
							String dotColor = dot.getCssValue("background-color")
							System.out.println("Dot color: " + dotColor)
							assert dotColor == "rgba(50, 152, 93, 1)" // Green
						} else {
							// Significant
							assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"))
							String dotColor = dot.getCssValue("background-color")
							System.out.println("Dot color: " + dotColor)
							assert dotColor == "rgba(198, 27, 28, 1)" // Red
						}

						CustomKeywords.'zoom.ZoomInOut.closeToastIfVisible'()
					} catch (Exception e) {
						println('⚠️ toast message is not visible: ' + e.getMessage())
					}
					println('closed a toast msg')
				}
			}
		}
	}
	@Keyword
	def closeToastIfVisible() {
		TestObject toastCloseTO = findTestObject('Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon')
		WebDriver driver = DriverFactory.getWebDriver()

		try {
			if (WebUI.waitForElementPresent(toastCloseTO, 5, FailureHandling.OPTIONAL)) {
				if (WebUI.verifyElementVisible(toastCloseTO, FailureHandling.OPTIONAL)) {
					WebElement toastCloseIcon = null
					int retries = 0
					boolean clicked = false

					while (retries < 2 && !clicked) {
						try {
							toastCloseIcon = WebUiCommonHelper.findWebElement(toastCloseTO, 3)
							toastCloseIcon.click()
							println('✅ Toast closed via standard click')
							clicked = true
						} catch (StaleElementReferenceException se) {
							println("🔁 Retry #${retries + 1}: StaleElementReferenceException: ${se.message}")
							retries++
							WebUI.delay(0.5)
						} catch (Exception e) {
							println("⚠️ Error clicking toast icon normally: ${e.message}")
							break
						}
					}

					if (!clicked && toastCloseIcon != null) {
						try {
							((JavascriptExecutor) driver).executeScript("arguments[0].click();", toastCloseIcon)
							println('✅ Toast closed via JavaScript click')
						} catch (Exception jsEx) {
							println("❌ JavaScript click failed: ${jsEx.message}")
						}
					}
				} else {
					println('ℹ️ Toast is present but not visible')
				}
			} else {
				println('ℹ️ Toast close icon not found')
			}
		} catch (Exception e) {
			println('⚠️ Safe exit from closeToastIfVisible(): ' + e.getMessage())
		}
	}




	public void verifyPercentageCalculation() {
		WebDriver driver =DriverFactory.getWebDriver()

		WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/Microscopic_view_CTA'))
		List<WebElement> cellRows=driver.findElements(By.xpath("//tbody/tr"))

		for (WebElement row : cellRows) {
			// Get the percentage element (last div inside the row)
			//row.click()

			WebElement cellname_ele = row.findElement(By.xpath(".//td[1]"))
			String cellname = cellname_ele.getText()
			println(cellname)

			WebElement count_ele = row.findElement(By.xpath(".//td[2]"))
			Integer patch_count = Integer.parseInt(count_ele.getText())
			println(patch_count)

			WebElement percentageElement = row.findElement(By.xpath(".//td[3]"))
			float actual_percentage_flaot_value = Float.parseFloat(percentageElement.getText())
			println(actual_percentage_flaot_value)

			String foot_note_msg =WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Total number of RBCs counted for color _9562ea'))

			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(foot_note_msg);
			Integer total_patch_count_from_foot_note
			if (matcher.find()) {
				String number = matcher.group();
				total_patch_count_from_foot_note=Integer.parseInt(number)
				System.out.println("Extracted number: " + number);
			} else {
				System.out.println("No number found.");
			}

			//float calculated_percentage=(float) (patch_count/total_patch_count_from_foot_note)
			float calculated_percentage=(float) (((patch_count/total_patch_count_from_foot_note))*100)
			float rounded_calculated_percentage = Math.round(calculated_percentage * 10) / 10.0
			println ('Rounded: ' + rounded_calculated_percentage)
			assert actual_percentage_flaot_value==rounded_calculated_percentage
		}
	}

	public void verifyCorrectnessOfGradeAccordingToPercentageValue() {
		WebDriver driver =DriverFactory.getWebDriver()
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),10)

		for (WebElement row : cellRows) {

			WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			println(cellname)
			float percentage_flaot_value = Float.parseFloat(percentageElement.getText())
			println(percentage_flaot_value)

			List<WebElement> grades= row.findElements(By.xpath(".//input[@type='radio']"))
			for(int i=0; i<=grades.size();i++) {
				if(grades.get(i).isSelected()) {
					int selected_grade=Integer.parseInt(grades.get(i).getAttribute("value"))

					System.out.println("Selected option value: " + selected_grade);
					if(selected_grade==0) {
						assert percentage_flaot_value==0
						break;
					}
					else if(selected_grade==1) {
						assert percentage_flaot_value > 0 && percentage_flaot_value <= 3
						break;
					}
					else if(selected_grade==2) {
						if(cellname.equals("Teardrop Cells")) {
							assert percentage_flaot_value > 3 && percentage_flaot_value <= 6
							break;
						}
						else {
							assert percentage_flaot_value > 3 && percentage_flaot_value <= 5
							break;
						}
					}
					else if(selected_grade==3) {
						if(cellname.equals("Teardrop Cells")) {
							assert percentage_flaot_value >6
							break;
						}
						else {
							assert percentage_flaot_value >5
							break;
						}
					}
					else {
						println("Grade value is not as per the percentage criteria")
					}
				}
			}
		}
	}

	public void valueGettingStrikeOfAfterRegrading() {
		WebDriver driver =DriverFactory.getWebDriver()
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),10)

		for (WebElement row : cellRows) {
			//WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			//println(cellname)
			List<WebElement> grades= row.findElements(By.xpath(".//input[@type='radio']"))

			if(cellname.equals("Acanthocytes*") || cellname.equals("Sickle Cells*") ||cellname.equals("Stomatocytes*") ||cellname.equals("Pappenheimer Bodies*") ||cellname.equals("Howell-Jolly Bodies*") ||cellname.equals("Basophilic Stippling*")) {
				grades[1].click()
				WebUI.delay(2)
				continue;
			}
			else {
				for(int i=0; i<=grades.size();i++) {
					if(!grades.get(i).isSelected()) {
						grades.get(i).click()
						WebUI.delay(1)
						WebElement percentageElement = row.findElement(By.xpath(".//div[3]/del"))
						String textDecoration = percentageElement.getCssValue("text-decoration-line");
						assert textDecoration.contains("line-through")
						println(cellname+" got regraded and percentage value got strike off and verified")
						break;
					}
				}
			}
		}
	}



	public HashMap<String,String> verifyRegradingChangesAutoSaved() {
		WebDriver driver =DriverFactory.getWebDriver()
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),10)
		Map<String,String> hm = new HashMap<>();
		for (WebElement row : cellRows) {
			//WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			//println(cellname)
			List<WebElement> grades= row.findElements(By.xpath(".//input[@type='radio']"))
			for (WebElement grade : grades) {
				if (grade.isSelected()) {
					String actual_selected_grade = grade.getAttribute("value");
					hm.put(cellname, actual_selected_grade);
					break;
				}
				else {
					continue;
				}
			}
		}
		println(hm)
		return hm
	}

	public boolean checkUserNotAbleRegradeForDifferentStatusOfReport() {
		boolean flag_not_clickable_status=true
		WebDriver driver =DriverFactory.getWebDriver()
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),10)
		for (WebElement row : cellRows) {
			//WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
			WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			//println(cellname)
			List<WebElement> grades= row.findElements(By.xpath(".//input[@type='radio']"))
			for (WebElement grade : grades) {
				try {
					if (grade.isDisplayed() && grade.isEnabled()) {
						grade.click(); // Try clicking it
						flag_not_clickable_status= false; // Click successful
						println(cellname+grade+"is selected")
					}
				}
				catch (Exception e) {
					//Handle cases like ElementClickInterceptedException
					flag_not_clickable_status=true;
					System.out.println("Radio button not clickable: " + e.getMessage());
				}
			}
		}
		return flag_not_clickable_status
	}

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





	public void verifyRbcPatchSizes() {
		// Locate all RBC patches (update selector to match your page's structure)
		//List<WebElement> patches = driver.findElements(By.xpath("//div[@class='cell-image-container']//img")); // example XPath
		WebDriver driver =DriverFactory.getWebDriver()


		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),10)
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
				Dimension size = patch.getSize();
				int width = size.getWidth();
				int height = size.getHeight();
				System.out.println(cellname+ " Patch " + (i + 1) + ": Width = " + width + " px, Height = " + height + " px");

				assert width == 86 : cellname+" Patch " + (i + 1) + " has incorrect width!"
				assert height == 86 : cellname+" Patch " + (i + 1) + " has incorrect height!"
			}
		}
	}

	public void checkRankOfThePatch() {
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),10)

		for(WebElement cellrow:cellRows) {
			WebElement cellname_ele = cellrow.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			cellrow.click()
			WebUI.delay(2)


			// Execute JavaScript and retrieve result
			//		def result = WebUI.executeJavaScript(jsCode, null)
			//		if (result instanceof Map && result.containsKey('error')) {
			//			WebUI.comment("❌ Error: " + result.error)
			//		} else {
			//			WebUI.comment("✅ Total unique images loaded: " + result.count)
			//			WebUI.comment("🖼️ Unique image URLs: " + result.images)
			//		}
			List<WebElement> patch_eles = WebUiCommonHelper.findWebElements(
			findTestObject('Object Repository/RBC_Objects/Page_PBS/Patches'),30)
			for (int i=0; i<patch_eles.size();i++) {
				String patch_rank = patch_eles.get(i).findElement(By.xpath(".//div//img")).getAttribute('class');
				int Actual_patch_Rank=Integer.parseInt(patch_rank.replaceAll("[^0-9]",''))

				println(Actual_patch_Rank)
				assert (Actual_patch_Rank-1)==i :cellname +" with patch" +(i+1)+" has worng patch rank"
			}
		}
	}


	public void verifyPatchesAreDisplayedAlligned() {
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),10)
		LinkedHashMap<String, Integer> hm =new  LinkedHashMap<>()
		for(WebElement cellrow:cellRows) {
			hm.clear()
			cellrow.click()
			WebElement cellname_ele = cellrow.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()
			if(cellname.equals("Acanthocytes*") || cellname.equals("Sickle Cells*") ||cellname.equals("Stomatocytes*") ||cellname.equals("Pappenheimer Bodies*") ||cellname.equals("Howell-Jolly Bodies*") ||cellname.equals("Basophilic Stippling*") ||cellname.equals("Anisocytosis") || cellname.equals("Poikilocytosis")) {
				//String no_patch_text=WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/No_pathes_available_text'),10)
				WebUI.comment("Skipping the iterartion as the "+cellname+" is the manuvally graded cell")
				List<WebElement> pathess=driver.findElements(By.xpath(".//div[@class='Card patches-container']"))
				assert pathess.size()==0
				continue;
			}

			WebUI.delay(2)
			List<WebElement> patch_row = WebUiCommonHelper.findWebElements(
			findTestObject('Object Repository/RBC_Objects/Page_PBS/patch_row'),30)

			println("Number of rows are "+patch_row.size())
			if(patch_row.size()<=2) {
				WebUI.comment(cellname+" has less number of rows to check the patches are diaplyed and aligned properly, atleast it should have 2 rows")
				continue;
			}

			for (int i=0; i<=2;i++) {
				List <WebElement> patches_in_row= patch_row.get(i).findElements(By.xpath(".//div[@class='Card patches-container']"))
				int no_of_patches=patches_in_row.size()
				hm.put(cellname+(i+1)+" patch row",no_of_patches)
			}
			println(hm)
			def values=hm.values()
			def firstValue = values[0]
			// Assert all values are equal to the first one
			assert values.every {
				it == firstValue
			}
		}
	}

	public void checkGradePercentageSignificantLegendAreNotPresentForManuallyGradedcell() {
		SoftAssert softAssert = new SoftAssert()

		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'), 10)
		LinkedHashMap<String, Integer> hm = new LinkedHashMap<>()

		for (WebElement cellrow : cellRows) {
			hm.clear()
			cellrow.click()

			WebElement cellname_ele = cellrow.findElement(By.xpath(".//div[1]"))
			String cellname = cellname_ele.getText()

			if (cellname.equals("Acanthocytes*") || cellname.equals("Sickle Cells*") ||
			cellname.equals("Stomatocytes*") || cellname.equals("Pappenheimer Bodies*") ||
			cellname.equals("Howell-Jolly Bodies*") || cellname.equals("Basophilic Stippling*")) {

				WebElement significant_legend_ele = cellrow.findElement(By.xpath(".//div[1]//div"))
				String significant_color = significant_legend_ele.getCssValue("background")

				def matcher = (significant_color =~ /rgba\([^)]*\)/)
				if (matcher.find()) {
					String rgba = matcher.group()
					WebUI.comment(rgba)
					softAssert.assertEquals(rgba, 'rgba(0, 0, 0, 0)', cellname + " background color mismatch")
				}

				WebElement percentageElement = cellrow.findElement(By.xpath(".//div[3]"))
				String percentageElement_text = percentageElement.getText()
				WebUI.comment(percentageElement_text)

				softAssert.assertEquals(percentageElement_text, '', cellname + " percentage text not empty")

				List<WebElement> grades = cellrow.findElements(By.xpath(".//input[@type='radio']"))
				boolean gradeSelection_status = false

				for (WebElement grade : grades) {
					if (grade.isSelected()) {
						WebUI.comment(cellname + " has some grade value already selected ")
						gradeSelection_status = true
						break
					}
				}

				if (!gradeSelection_status) {
					WebUI.comment(cellname + " has no grade values selected")
				}

				softAssert.assertFalse(gradeSelection_status, cellname + " should not have any grade selected")
			}
		}

		softAssert.assertAll() // Triggers failure if any soft assertion failed
	}

	public void verifyRegradeeee() {
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
		findTestObject("Object Repository/RBC_Objects/Page_PBS/Cell_rows"),1);
		for (WebElement row : cellRows) {
			row.click();
			String cellname = row.findElement(By.xpath(".//div")).getText();
			String cellname_removed = cellname.replace("*", "");

			System.out.println(cellname);

			List<WebElement> grades = row.findElements(By.xpath(".//input[@type='radio']"));
			System.out.println(grades.size());

			for (WebElement option : grades) {
				if (option.isSelected()) {
					System.out.println("Selected option value: " + option.getAttribute("value"));
					break;
				}
			}

			boolean isSpecialCell = cellname.equals("Acanthocytes*") || cellname.equals("Sickle Cells*") || cellname.equals("Stomatocytes*") || cellname.equals("Howell-Jolly Bodies*") || cellname.equals("Pappenheimer Bodies*") || cellname.equals("Basophilic Stippling*") || cellname.equals("Fragmented Cells*");
			boolean isFragmentedOrSickle = cellname.equals("Fragmented Cells*") || cellname.equals("Sickle Cells*");

			if (isSpecialCell) {
				for(int i = 0; i < grades.size(); i++) {
					grades.get(i).click();

					TestObject toastMsg = findTestObject("Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele");
					WebUI.waitForElementVisible(toastMsg, 10);

					TestObject regradeMsgObj = findTestObject("Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg");
					WebUI.waitForElementVisible(regradeMsgObj, 10);
					String regrade_msg = WebUI.getText(regradeMsgObj).trim();

					TestObject significant_non_sign_Msg_ele = findTestObject("Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status");
					WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10);
					String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim();

					System.out.println("Regrade message: " + regrade_msg);
					System.out.println("Significant status message: " + signifiacnt_status_msg);

					if (i == 0) {
						assert regrade_msg.equals(cellname_removed + " regraded from - to " + i);
					} else {
						assert regrade_msg.equals(cellname_removed + " regraded from " + (i - 1) + " to " + i);
					}

					boolean isSignificant = isFragmentedOrSickle ? i > 0 : i > 1;
					String expectedStatusMsg = cellname_removed + (isSignificant ? " is now significant" : " is now non significant");
					assert signifiacnt_status_msg.equals(expectedStatusMsg);

					WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')])"))
					String dotColor = dot.getCssValue("background-color");
					System.out.println("Dot color: " + dotColor);
					assert dotColor.equals(isSignificant ? "rgba(198, 27, 28, 1)" : "rgba(50, 152, 93, 1)");

					try {
						TestObject toastCloseIcon = findTestObject("Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon");
						if (WebUI.waitForElementVisible(toastCloseIcon, 5, FailureHandling.OPTIONAL)) {
							WebUI.click(toastCloseIcon);
							System.out.println("✅ Toast message closed");
						} else {
							System.out.println("ℹ️ Toast message close icon not found or already disappeared");
						}
					} catch (Exception e) {
						System.out.println("⚠️ Exception while closing toast message: " + e.getMessage());
					}
					System.out.println("closed a toast msg");
				}
			}
		}
	}
}
