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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapi69.io.shell.ProcessBuilderFactory;
import io.github.astrapisixtynine.pdf.to.text.info.ConversionResult;
import net.sourceforge.tess4j.TesseractException;

/**
 * Test class for {@link ImagePdfToTextExtensions}
 */
class ImagePdfToTextExtensionsTest
{

	@Disabled("""
		only for local use:
		if tesseract is installed
		""")
	@Test
	void testGetTesseractSupportedLanguages() throws Exception
	{
		// Mock Tesseract output for languages
		String mockOutput = "eng\ndeu\nfra\n";
		InputStream inputStream = new ByteArrayInputStream(mockOutput.getBytes());

		// Mock the process builder to return the mocked input stream as output
		try (MockedStatic<ProcessBuilderFactory> mockedFactory = mockStatic(
			ProcessBuilderFactory.class))
		{
			Process mockProcess = Mockito.mock(Process.class);
			Mockito.when(mockProcess.getInputStream()).thenReturn(inputStream);

			ProcessBuilder mockBuilder = Mockito.mock(ProcessBuilder.class);
			Mockito.when(mockBuilder.start()).thenReturn(mockProcess);

			mockedFactory
				.when(() -> ProcessBuilderFactory.newProcessBuilder(any(), any(), any(), any()))
				.thenReturn(mockBuilder);

			// Expected languages supported by the mocked output
			List<String> expectedLanguages = Arrays.asList("eng", "deu", "fra");

			// Call the method to test
			List<String> actualLanguages = ImagePdfToTextExtensions
				.getTesseractSupportedLanguages();

			// Verify that the expected and actual lists match
			assertEquals(expectedLanguages, actualLanguages);
		}
	}

	/**
	 * Tests the {@link ImagePdfToTextExtensions#convertPdfToTextfile(File, File, String, String)}
	 * method by converting a sample PDF file to text and verifying the conversion result.
	 *
	 * @throws IOException
	 *             if an I/O error occurs during file handling
	 * @throws TesseractException
	 *             if an error occurs during OCR processing
	 */
	// @Disabled("""
	// only for local use:
	// if tesseract is installed
	// """)
	@Test
	void testConvertPdfToTextfile() throws IOException, TesseractException, IOException
	{

		String pdfFileName;
		String fileName;
		fileName = "program-image";
		pdfFileName = fileName + ".pdf";

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
		DeleteFileExtensions.delete(conversionResult.getResultTextFile());
		DeleteFileExtensions.delete(conversionResult.getImageFiles());
		DeleteFileExtensions.delete(conversionResult.getTextFiles());
	}

	/**
	 * Tests the method {@link ImagePdfToTextExtensions#getTextContent(List, String, String)} with
	 * multiple image files.
	 */
	@Test
	@Disabled("""
		only for local use:
		if tesseract is installed
		""")
	void testGetTextContent() throws TesseractException
	{
		// Arrange
		String language = "deu";
		String datapath = "/usr/share/tessdata";
		List<File> imageFiles = new ArrayList<>();
		imageFiles.add(new File("src/test/resources/test-result/sample-image-1.png"));
		imageFiles.add(new File("src/test/resources/test-result/sample-image-2.png"));
		imageFiles.add(new File("src/test/resources/test-result/sample-image-3.png"));

		// Act
		String result = ImagePdfToTextExtensions.getTextContent(imageFiles, datapath, language);

		// Assert
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	/**
	 * Tests the method {@link ImagePdfToTextExtensions#extractTextFromImage(File, String, String)}
	 */
	@Test
	@Disabled("""
		only for local use:
		if tesseract is installed
		""")
	void extractTextFromImage() throws TesseractException
	{
		String language;
		File imageFile;
		String datapath;
		String result;

		language = "deu";
		imageFile = new File("src/test/resources/sample-image.png");
		datapath = "/usr/share/tessdata";
		result = ImagePdfToTextExtensions.extractTextFromImage(imageFile, datapath, language);
		assertNotNull(result);
		assertFalse(result.isEmpty());

		// imageFile = new File("src/test/resources/img-doc-01.jpeg");
		// result = ImagePdfToTextExtensions.extractTextFromImage(imageFile, datapath, language);
		//
		// assertNotNull(result);
		// assertFalse(result.isEmpty());
	}

	/**
	 * Tests if the method correctly detects when Tesseract is installed. This test will pass if
	 * Tesseract is installed and available in the system's PATH
	 */
	@Test
	@Disabled("""
		only for local use:
		if tesseract is installed
		""")
	public void testIsTesseractInstalledWhenInstalled()
	{
		boolean isInstalled = ImagePdfToTextExtensions.isTesseractInstalled();
		assertTrue(isInstalled, "Tesseract should be installed on the system");
	}

	/**
	 * Test method for {@link ImagePdfToTextExtensions} with {@link BeanTester}
	 */
	@Test
	public void testWithBeanTester()
	{
		final BeanTester beanTester = new BeanTester();
		beanTester.testBean(ImagePdfToTextExtensions.class);
	}

}
