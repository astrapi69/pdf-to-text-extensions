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
package io.github.astrapisixtynine.pdf.to.text.info;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit test class for {@link OcrLanguage}
 */
public class OcrLanguageTest
{

	/**
	 * Test for {@link OcrLanguage#getDisplayName()}
	 */
	@Test
	@DisplayName("Test getDisplayName method for all enum values")
	void testGetDisplayName()
	{
		assertEquals("English", OcrLanguage.ENGLISH.getDisplayName());
		assertEquals("German", OcrLanguage.GERMAN.getDisplayName());
	}

	/**
	 * Test for {@link OcrLanguage#getCode()}
	 */
	@Test
	@DisplayName("Test getCode method for all enum values")
	void testGetCode()
	{
		assertEquals("eng", OcrLanguage.ENGLISH.getCode());
		assertEquals("deu", OcrLanguage.GERMAN.getCode());
	}

	/**
	 * Test for {@link OcrLanguage#getLanguageCodes()}
	 */
	@Test
	@DisplayName("Test getLanguageCodes method returns all codes")
	void testGetLanguageCodes()
	{
		List<String> codes = OcrLanguage.getLanguageCodes();
		assertTrue(codes.contains("eng"));
		assertTrue(codes.contains("deu"));
	}

	/**
	 * Parameterized test for {@link OcrLanguage#filterLanguagesByCodes(List)}
	 *
	 * @param code
	 *            the code to filter
	 * @param expectedDisplayName
	 *            the expected display name
	 */
	@ParameterizedTest
	@CsvSource({ "eng,English", "deu,German", "fra,French" })
	@DisplayName("Test filterLanguagesByCodes with parameterized codes")
	void testFilterLanguagesByCodes(String code, String expectedDisplayName)
	{
		List<OcrLanguage> result = OcrLanguage.filterLanguagesByCodes(Arrays.asList(code));
		assertEquals(1, result.size());
		assertEquals(expectedDisplayName, result.get(0).getDisplayName());
	}

	/**
	 * Test for {@link OcrLanguage#toString()}
	 */
	@Test
	@DisplayName("Test toString method returns display name")
	void testToString()
	{
		assertEquals("English", OcrLanguage.ENGLISH.toString());
		assertEquals("German", OcrLanguage.GERMAN.toString());
	}
}
