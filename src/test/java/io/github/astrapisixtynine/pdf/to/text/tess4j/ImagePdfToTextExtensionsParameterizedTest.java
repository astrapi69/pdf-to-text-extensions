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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


/**
 * Parameterized test class for {@link ImagePdfToTextExtensions}
 */
class ImagePdfToTextExtensionsParameterizedTest
{

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
		String result = ImagePdfToTextExtensions.extractTextFromImage(imageFile, datapath,
			language);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

}