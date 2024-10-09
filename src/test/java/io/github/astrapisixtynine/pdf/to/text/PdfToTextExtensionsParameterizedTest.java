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
package io.github.astrapisixtynine.pdf.to.text;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.astrapi69.file.create.DirectoryFactory;
import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;

/**
 * Parameterized test class for {@link PdfToTextExtensions}
 */
class PdfToTextExtensionsParameterizedTest
{

	private File pdfFile;
	private File outputDir;

	/**
	 * Sets up test data before each test
	 */
	@BeforeEach
	void setUp()
	{
		pdfFile = FileFactory.newFileQuietly(PathFinder.getSrcTestResourcesDir(), "sample.pdf");
		outputDir = DirectoryFactory.newDirectory(PathFinder.getSrcTestResourcesDir(), "output");
	}

	/**
	 * cleans up after each test
	 */
	@AfterEach
	void tearDown() throws IOException
	{
		if (outputDir != null)
		{
			DeleteFileExtensions.delete(outputDir);
		}
	}

	/**
	 * Parameterized test for extracting text from a range of pages
	 *
	 * @param startPage
	 *            starting page for text extraction
	 * @param endPage
	 *            ending page for text extraction
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/page-range-tests.csv", numLinesToSkip = 1)
	void testPdfToTextWithPageRange(int startPage, int endPage) throws IOException
	{
		File resultFile = PdfToTextExtensions.pdfToText(pdfFile, outputDir, startPage, endPage);
		assertNotNull(resultFile);
		assertTrue(resultFile.exists());
	}


	/**
	 * Parameterized test for extracting text from image files using various languages
	 *
	 * @param language
	 *            the language code to use for OCR
	 */
	@ParameterizedTest
	@ValueSource(strings = { "deu", "eng" })
	@Disabled("produces a fatal error has been detected by the Java Runtime Environment")
	void testExtractTextFromImageWithDifferentLanguages(String language) throws Exception
	{
		File imageFile = new File("src/test/resources/sample-image.png");
		String datapath = "/usr/share/tessdata";
		String result = PdfToTextExtensions.extractTextFromImage(imageFile, datapath, language);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

}