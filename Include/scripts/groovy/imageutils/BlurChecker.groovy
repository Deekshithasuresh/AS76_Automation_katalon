//package imageUtils
//import java.awt.image.BufferedImage
//
//import javax.imageio.ImageIO
//
//import java.util.Base64
//import com.kms.katalon.core.annotation.Keyword
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//
//
//class blurChecker {
//	/**
//	 * Checks if the canvas image is blurry.
//	 * @param jsCanvasSelector JavaScript selector string, e.g. "document.querySelector('canvas')" or "document.getElementById('myCanvas')"
//	 * @param threshold Edge variance threshold to consider image blurry. Default = 100.0
//	 * @return true if blurry, false if sharp
//	 */
//	
//	
//	@Keyword
//	public boolean isCanvasImageBlurry(String jsCanvasSelector = "document.querySelector('canvas')", double threshold = 5.0) {
//		println("inside def boolean")
//		try {
//			// Build JavaScript to extract Base64 image data from the canvas
//			String js = "return ${jsCanvasSelector}.toDataURL('image/png').split(',')[1];"
//			// Execute JavaScript to get the Base64-encoded image
//			String base64Data = WebUI.executeJavaScript(js, null)
//			println("converted to grayscale")
//			// Decode Base64 to BufferedImage
//			byte[] imageBytes = Base64.decoder.decode(base64Data)
//			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes))
//			// Convert to grayscale
//			BufferedImage grayImage = new BufferedImage(image.width, image.height, BufferedImage.TYPE_BYTE_GRAY)
//			grayImage.graphics.drawImage(image, 0, 0, null)
//			println("grayscale done")
//			// Calculate edge variance
//			double variance = calcEdgeVariance(grayImage)
//			WebUI.comment("Edge variance: $variance")
//			if (variance < threshold) {
//				WebUI.comment("Image is likely BLURRY")
//				return true
//			} else {
//				WebUI.comment("Image is likely SHARP")
//				return false
//			}
//		} catch (Exception e) {
//			WebUI.comment("Error checking canvas blur: ${e.message}")
//			return true // Assume blurry if error occurs
//			println("completed catch")
//		}
//	}
//	private double calcEdgeVariance(BufferedImage img) {
//		int width = img.getWidth()
//		int height = img.getHeight()
//		def edgeValues = []
//		for (int y = 1; y < height - 1; y++) {
//			for (int x = 1; x < width - 1; x++) {
//				int rgbCenter = img.getRGB(x, y) & 0xFF
//				int rgbRight = img.getRGB(x + 1, y) & 0xFF
//				int rgbDown = img.getRGB(x, y + 1) & 0xFF
//				int dx = rgbCenter - rgbRight
//				int dy = rgbCenter - rgbDown
//				edgeValues << (dx * dx + dy * dy)
//			}
//		}
//		return edgeValues.sum() / edgeValues.size()
//	}
//}
//



//suggested code
package imageUtils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.JavascriptExecutor

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.util.Base64

class blurChecker {

    /**
     * Checks if a canvas image is blurry using edge variance.
     * Accepts optional selector and sharpness threshold.
     *
     * @param jsCanvasSelector JS string to locate canvas element. Defaults to "#pbs-volumeViewport canvas".
     * @param threshold Variance threshold below which image is considered blurry. Default is 5.0.
     * @return true if blurry, false if sharp
     */
    @Keyword
    boolean isCanvasImageBlurry(String jsCanvasSelector = "document.querySelector('#pbs-volumeViewport canvas')", double threshold = 5.0) {
        try {
            KeywordUtil.logInfo("🧪 Checking canvas sharpness using selector: " + jsCanvasSelector)

            // Build and execute JS to get Base64 image from canvas
            String js = "let canvas = ${jsCanvasSelector}; if (!canvas) return null; return canvas.toDataURL('image/png').split(',')[1];"
            String base64Data = WebUI.executeJavaScript(js, null)

            if (base64Data == null) {
                WebUI.comment("⚠️ No canvas element found or returned null. Assuming blurry.")
                return true
            }

            // Decode image and convert to grayscale
            byte[] imageBytes = Base64.decoder.decode(base64Data)
            BufferedImage colorImage = ImageIO.read(new ByteArrayInputStream(imageBytes))

            if (colorImage == null) {
                WebUI.comment("❌ Could not decode canvas image. Assuming blurry.")
                return true
            }

            BufferedImage grayImage = new BufferedImage(colorImage.width, colorImage.height, BufferedImage.TYPE_BYTE_GRAY)
            grayImage.graphics.drawImage(colorImage, 0, 0, null)

            // Compute edge variance
            double variance = calcEdgeVariance(grayImage)
            WebUI.comment("🔍 Edge Variance: $variance")

            if (variance < threshold) {
                WebUI.comment("📉 Image is likely BLURRY (variance < $threshold)")
                return true
            } else {
                WebUI.comment("📈 Image is likely SHARP")
                return false
            }

        } catch (Exception e) {
            WebUI.comment("❌ Error checking canvas blur: ${e.message}")
            return true // Assume blurry on exception
        }
    }

    /**
     * Calculates edge variance in a grayscale image using a simple gradient-based metric.
     */
    private double calcEdgeVariance(BufferedImage img) {
        int width = img.getWidth()
        int height = img.getHeight()
        def edgeValues = []

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int center = img.getRGB(x, y) & 0xFF
                int right = img.getRGB(x + 1, y) & 0xFF
                int down = img.getRGB(x, y + 1) & 0xFF
                int dx = center - right
                int dy = center - down
                edgeValues << (dx * dx + dy * dy)
            }
        }

        return edgeValues.isEmpty() ? 0.0 : edgeValues.sum() / edgeValues.size()
    }
}
