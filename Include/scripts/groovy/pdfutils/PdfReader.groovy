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

		File[] matchingFiles = downloadsDir.listFiles({ File dir, String name ->
			name.toLowerCase().startsWith("pdfreport") && name.toLowerCase().endsWith(".pdf")
		} as FilenameFilter)

		if (!matchingFiles || matchingFiles.length == 0) {
			throw new FileNotFoundException("No PDF reports found in $downloadsDirPath")
		}

		matchingFiles = matchingFiles.sort { a, b -> b.lastModified() <=> a.lastModified() }
		return matchingFiles[0]
	}

	static String readText(String filePath) {
		if (!new File(filePath).exists()) {
			throw new FileNotFoundException("PDF file not found: $filePath")
		}

		// PDFBox 3.x – load using String path
		PDDocument document = PDDocument.load(filePath)
		try {
			return new PDFTextStripper().getText(document)
		} finally {
			document.close()
		}
	}

	static boolean containsText(String filePath, String expected) {
		return readText(filePath).contains(expected)
	}

	static void extractImages(String filePath, String outputFolder) {
		File file = new File(filePath)
		if (!file.exists()) {
			throw new FileNotFoundException("PDF file not found: $filePath")
		}

		File folder = new File(outputFolder)
		if (!folder.exists()) {
			folder.mkdirs()
		}

		// PDFBox 3.x – load using String path
		PDDocument document = PDDocument.load(filePath)
		try {
			PDFRenderer renderer = new PDFRenderer(document)
			for (int i = 0; i < document.getNumberOfPages(); i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 300)
				File out = new File("${outputFolder}/image_page_${i+1}.png")
				ImageIO.write(image, "png", out)
				println "Saved image: ${out.absolutePath}"
			}
		} finally {
			document.close()
		}
	}
}
