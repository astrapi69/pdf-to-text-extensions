package io.github.astrapisixtynine.pdf.to.text;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
	@Disabled("fails on github-actions")
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
	@Disabled
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

}
