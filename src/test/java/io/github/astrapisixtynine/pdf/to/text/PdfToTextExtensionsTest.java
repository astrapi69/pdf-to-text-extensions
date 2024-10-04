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
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

import io.github.astrapi69.file.create.DirectoryFactory;
import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapisixtynine.pdf.to.text.info.ConversionResult;

/**
 * Test class for {@link PdfToTextExtensions}
 */
class PdfToTextExtensionsTest
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
	 * Tests the method {@link PdfToTextExtensions#pdfToText(File, File)}
	 */
	@Test
	void testPdfToText() throws IOException
	{
		File resultFile = PdfToTextExtensions.pdfToText(pdfFile, outputDir);
		assertNotNull(resultFile);
		assertTrue(resultFile.exists());
	}

	/**
	 * Tests the method {@link PdfToTextExtensions#pdfToText(File, File)}
	 */
	@Test
	@Disabled
	void extractTextFromImageSOF() throws IOException
	{
		String pdfFileName;
		String fileName;
		fileName = "sample";
		pdfFileName = fileName + ".pdf";

		File pdfFile = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(), pdfFileName);

		File resultDir = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(),
			"text-result");
		File resultTextFile = PdfToTextExtensions.pdfToText(pdfFile, resultDir);
	}

	/**
	 * Tests the method {@link PdfToTextExtensions#convertPdfToTextfile(File, File)}
	 */
	@Test
	// @Disabled("fails on github-actions")
	void testConvertPdfToTextfile() throws IOException, InterruptedException
	{
		ConversionResult result = PdfToTextExtensions.convertPdfToTextfile(pdfFile, outputDir);
		assertNotNull(result);
		assertFalse(result.getImageFiles().isEmpty());
		assertFalse(result.getTextFiles().isEmpty());
		assertTrue(result.getResultTextFile().exists());
	}

	/**
	 * Tests the method {@link PdfToTextExtensions#convertPdfToTextfile(File, File)}
	 */
	@Test
	@Disabled("""
		only for local use:
		Replace 'fileName' with the name of your PDF file name without the extension.
		The location of the PDF file must be the 'test resource folder'
		The output is generated in the subfolder 'text-result' of the 'test resource folder'
		""")
	void extractTextFromImage() throws IOException, InterruptedException
	{
		String pdfFileName;
		String fileName;
		fileName = "sample";
		pdfFileName = fileName + ".pdf";

		File pdfFile = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(), pdfFileName);
		File resultDir = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(),
			"text-result");

		ConversionResult conversionResult = PdfToTextExtensions.convertPdfToTextfile(pdfFile,
			resultDir);

		DeleteFileExtensions.delete(conversionResult.getImageFiles());
		DeleteFileExtensions.delete(conversionResult.getTextFiles());
	}

	/**
	 * Test method for {@link PdfToTextExtensions} with {@link BeanTester}
	 */
	@Test
	public void testWithBeanTester()
	{
		final BeanTester beanTester = new BeanTester();
		beanTester.testBean(PdfToTextExtensions.class);
	}
}
