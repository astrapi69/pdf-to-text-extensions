package io.github.astrapisixtynine.pdf.to.text;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public final class PdfToTextExtensions
{

	public static String extractTextFromImage(File imageFile, String datapath, String language)
		throws TesseractException
	{
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath(datapath);
		tesseract.setLanguage(language);
		return tesseract.doOCR(imageFile);
	}
}
