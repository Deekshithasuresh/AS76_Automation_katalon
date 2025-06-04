
package imageutils
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import javax.imageio.ImageIO
import java.util.Base64
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
class BlurChecker {
	/**
	 * Checks if the canvas image is blurry.
	 * @param jsCanvasSelector JavaScript selector string, e.g. "document.querySelector('canvas')" or "document.getElementById('myCanvas')"
	 * @param threshold Edge variance threshold to consider image blurry. Default = 100.0
	 * @return true if blurry, false if sharp
	 */
	@Keyword
	def boolean isCanvasImageBlurry(String jsCanvasSelector = "document.querySelector('canvas')", double threshold = 5.0) {
		println("inside def boolean")
		try {
			// Build JavaScript to extract Base64 image data from the canvas
			String js = "return ${jsCanvasSelector}.toDataURL('image/png').split(',')[1];"
			// Execute JavaScript to get the Base64-encoded image
			String base64Data = WebUI.executeJavaScript(js, null)
			println("converted to grayscale")
			// Decode Base64 to BufferedImage
			byte[] imageBytes = Base64.decoder.decode(base64Data)
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes))
			// Convert to grayscale
			BufferedImage grayImage = new BufferedImage(image.width, image.height, BufferedImage.TYPE_BYTE_GRAY)
			grayImage.graphics.drawImage(image, 0, 0, null)
			println("grayscale done")
			// Calculate edge variance
			double variance = calcEdgeVariance(grayImage)
			WebUI.comment("Edge variance: $variance")
			if (variance < threshold) {
				WebUI.comment("Image is likely BLURRY")
				return true
			} else {
				WebUI.comment("Image is likely SHARP")
				return false
			}
		} catch (Exception e) {
			WebUI.comment("Error checking canvas blur: ${e.message}")
			return true // Assume blurry if error occurs
			println("completed catch")
		}
	}
	private double calcEdgeVariance(BufferedImage img) {
		int width = img.getWidth()
		int height = img.getHeight()
		def edgeValues = []
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				int rgbCenter = img.getRGB(x, y) & 0xFF
				int rgbRight = img.getRGB(x + 1, y) & 0xFF
				int rgbDown = img.getRGB(x, y + 1) & 0xFF
				int dx = rgbCenter - rgbRight
				int dy = rgbCenter - rgbDown
				edgeValues << (dx * dx + dy * dy)
			}
		}
		return edgeValues.sum() / edgeValues.size()
	}
}


