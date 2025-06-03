package pdfutils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pdfbox.rendering.PDFRenderer

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File
import java.io.FilenameFilter
import java.io.FileNotFoundException

class PdfReader {

	static File getLatestPdfReport(String downloadsDirPath) {
		File downloadsDir = new File(downloadsDirPath)

		File[] matchingFiles = downloadsDir.listFiles(new FilenameFilter() {
					boolean accept(File dir, String name) {
						return name.toLowerCase().startsWith("pdfreport") && name.toLowerCase().endsWith(".pdf")
					}
				})

		if (matchingFiles == null || matchingFiles.length == 0) {
			throw new FileNotFoundException("No PDF reports found in " + downloadsDirPath)
		}

		// Sort files by last modified descending
		matchingFiles = matchingFiles.sort { a, b -> b.lastModified() <=> a.lastModified() }

		return matchingFiles[0]
	}

	static String readText(String filePath) {
		PDDocument document = PDDocument.load(new File(filePath))
		PDFTextStripper stripper = new PDFTextStripper()
		String text = stripper.getText(document)
		document.close()
		return text
	}

	static void extractImages(String filePath, String outputFolder) {
		PDDocument document = PDDocument.load(new File(filePath))
		PDFRenderer renderer = new PDFRenderer(document)

		for (int i = 0; i < document.getNumberOfPages(); i++) {
			BufferedImage image = renderer.renderImageWithDPI(i, 300)
			File outputFile = new File("$outputFolder/image_page_${i + 1}.png")
			ImageIO.write(image, "png", outputFile)
			println "Saved image: ${outputFile.absolutePath}"
		}

		document.close()
	}
}
