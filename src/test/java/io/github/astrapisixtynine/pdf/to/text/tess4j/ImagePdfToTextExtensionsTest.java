package io.github.astrapisixtynine.pdf.to.text.tess4j;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

import net.sourceforge.tess4j.TesseractException;

/**
 * Test class for {@link ImagePdfToTextExtensions}
 */
class ImagePdfToTextExtensionsTest
{

	/**
	 * Tests the method {@link ImagePdfToTextExtensions#extractTextFromImage(File, String, String)}
	 */
	@Test
	void extractTextFromImage() throws TesseractException
	{
		String language = "deu";
		File imageFile = new File("src/test/resources/sample-image.png");
		String datapath = "/usr/share/tessdata";
		String result = ImagePdfToTextExtensions.extractTextFromImage(imageFile, datapath,
			language);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	/**
	 * Tests if the method correctly detects when Tesseract is installed. This test will pass if
	 * Tesseract is installed and available in the system's PATH.
	 */
	@Test
	@Disabled("""
		only for local use:
		if tesseract is installed
		""")
	public void testIsTesseractInstalledWhenInstalled()
	{
		// This test assumes that Tesseract is installed on the system.
		boolean isInstalled = ImagePdfToTextExtensions.isTesseractInstalled();

		// Tesseract should be installed, so the result should be true.
		assertTrue(isInstalled, "Tesseract should be installed on the system.");
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