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