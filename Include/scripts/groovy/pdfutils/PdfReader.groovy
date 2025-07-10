package pdfutils

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.text.PDFTextStripper

class PdfReader {

    /**
     * Returns the most recently modified PDF file starting with 'pdfreport' from the given directory.
     */
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

    /**
     * Reads and returns the full text content from a PDF file.
     */
    static String readText(String filePath) {
        File file = new File(filePath)
        if (!file.exists()) {
            throw new FileNotFoundException("PDF file not found: $filePath")
        }

        PDDocument document = PDDocument.load(file) // ✅ FIXED: Use File directly
        try {
            PDFTextStripper stripper = new PDFTextStripper()
            return stripper.getText(document)
        } finally {
            document.close()
        }
    }

    /**
     * Returns true if the PDF contains the expected text.
     */
    static boolean containsText(String filePath, String expected) {
        return readText(filePath).contains(expected)
    }

    /**
     * Extracts all pages from a PDF file as images and saves them as PNG files in the given folder.
     */
    static void extractImages(String filePath, String outputFolder) {
        File file = new File(filePath)
        if (!file.exists()) {
            throw new FileNotFoundException("PDF file not found: $filePath")
        }

        File folder = new File(outputFolder)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        PDDocument document = PDDocument.load(file) // ✅ FIXED
        try {
            PDFRenderer renderer = new PDFRenderer(document)

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300)
                File outputFile = new File("${outputFolder}/image_page_${i + 1}.png")
                ImageIO.write(image, "png", outputFile)
                println "✅ Saved image: ${outputFile.absolutePath}"
            }
        } finally {
            document.close()
        }
    }
}
