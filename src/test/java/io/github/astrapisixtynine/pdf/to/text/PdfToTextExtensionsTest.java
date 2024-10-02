package io.github.astrapisixtynine.pdf.to.text;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;

import io.github.astrapi69.file.search.PathFinder;
import net.sourceforge.tess4j.TesseractException;

class PdfToTextExtensionsTest
{

	@Test
	void extractTextFromImage()
	{
		String pdfFilePath;
		File pdfFile = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(),
			"RaptisRA27_08_2024.pdf");
		File resultDir = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(),
			"text-result");
		String resultDirPath = resultDir.getAbsolutePath();
		String outputDirectory = resultDirPath + "/";

		pdfFilePath = pdfFile.getAbsolutePath();

		try
		{
			// Schritt 1: PDF in Bilder konvertieren
			PDDocument document = Loader.loadPDF(new File(pdfFilePath));

			PDFRenderer pdfRenderer = new PDFRenderer(document);

			for (int page = 0; page < document.getNumberOfPages(); ++page)
			{
				BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
				File imageFile = new File(outputDirectory + "page_" + (page + 1) + ".png");
				ImageIO.write(image, "png", imageFile);

				// Schritt 2: Texterkennung mit Tesseract durchführen
				String extractedText = PdfToTextExtensions.extractTextFromImage(imageFile,
					resultDirPath, "deu");
				System.out.println("Erkannter Text auf Seite " + (page + 1) + ":");
				System.out.println(extractedText);
			}

			document.close();
		}
		catch (IOException | TesseractException e)
		{
			e.printStackTrace();
		}
	}

}