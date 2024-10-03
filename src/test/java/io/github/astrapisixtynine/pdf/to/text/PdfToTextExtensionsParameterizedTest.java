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

import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;

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
		pdfFile = new File(PathFinder.getSrcTestResourcesDir(), "sample.pdf");
		outputDir = PathFinder.getRelativePath(PathFinder.getSrcTestResourcesDir(), "output");
		outputDir.mkdirs();
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
		String datapath = "/usr/share/tesseract-ocr/4.00/tessdata";
		String result = PdfToTextExtensions.extractTextFromImage(imageFile, datapath, language);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}


}