/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapisixtynine.pdf.to.text.tess4j;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapisixtynine.pdf.to.text.info.ConversionResult;
import net.sourceforge.tess4j.TesseractException;

/**
 * Test class for the {@link ImagePdfToTextExtensions} class, specifically the
 * {@link ImagePdfToTextExtensions#convertPdfToTextfile(File, File, String, String)} method
 */
class ImagePdfToTextExtensionsConvertPdfToTextFileTest
{

	/**
	 * Tests the {@link ImagePdfToTextExtensions#convertPdfToTextfile(File, File, String, String)}
	 *
	 * @throws IOException
	 *             if an I/O error occurs during file handling
	 * @throws TesseractException
	 *             if an error occurs during OCR processing
	 */
	@Disabled("""
		only for local use:
		if
		    tesseract is installed
		than
		          Replace 'fileName' with the name of your PDF file name without the extension.
		          The location of the PDF file must be the 'test resource folder'
		          The output is generated in the subfolder 'text-result' of the 'test resource folder'
		""")
	@Test
	void testConvertPdfToTextfile() throws IOException, TesseractException
	{
		String pdfFileName;
		String fileName;
		fileName = "program-image";
		pdfFileName = fileName + ".pdf";
		boolean cleanUp = true;

		// cleanUp = false;

		File pdfFile = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(), pdfFileName);

		File outputDirectory = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(),
			"test-result");
		String datapath = "/usr/share/tessdata";
		String language = "deu";

		// Act
		ConversionResult conversionResult = ImagePdfToTextExtensions.convertPdfToTextfile(pdfFile,
			outputDirectory, datapath, language);

		// Assert
		assertNotNull(conversionResult, "Conversion result should not be null");
		assertNotNull(conversionResult.getImageFiles(), "Image files should not be null");
		assertNotNull(conversionResult.getTextFiles(), "Text files should not be null");
		assertNotNull(conversionResult.getResultTextFile(), "Result text file should not be null");

		assertFalse(conversionResult.getImageFiles().isEmpty(),
			"Image files list should not be empty");
		assertFalse(conversionResult.getTextFiles().isEmpty(),
			"Text files list should not be empty");
		assertTrue(conversionResult.getResultTextFile().exists(), "Result text file should exist");

		// Verify contents
		assertTrue(conversionResult.getResultTextFile().length() > 0,
			"Result text file should contain data");
		// clean up
		if (cleanUp)
		{
			DeleteFileExtensions.delete(conversionResult.getResultTextFile());
			DeleteFileExtensions.delete(conversionResult.getImageFiles());
			DeleteFileExtensions.delete(conversionResult.getTextFiles());
		}
	}
}
