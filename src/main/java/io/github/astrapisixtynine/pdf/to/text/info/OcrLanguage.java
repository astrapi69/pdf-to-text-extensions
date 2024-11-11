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

/**
 * Enum representing available languages for Tesseract OCR.
 */
public enum OcrLanguage
{
	ENGLISH("English", "eng"), GERMAN("German", "deu"), FRENCH("French", "fra"), SPANISH("Spanish",
		"spa"), ITALIAN("Italian", "ita"), DUTCH("Dutch", "nld"), PORTUGUESE("Portuguese",
			"por"), RUSSIAN("Russian", "rus"), CHINESE_SIMPLIFIED("Chinese (Simplified)",
				"chi_sim"), CHINESE_TRADITIONAL("Chinese (Traditional)", "chi_tra"), JAPANESE(
					"Japanese", "jpn"), KOREAN("Korean", "kor"), GREEK("Greek", "ell");

	private final String displayName;
	private final String code;

	/**
	 * Constructs an {@code OcrLanguage} enum with the specified display name and language code.
	 *
	 * @param displayName
	 *            the name displayed in the UI
	 * @param code
	 *            the Tesseract language code
	 */
	OcrLanguage(String displayName, String code)
	{
		this.displayName = displayName;
		this.code = code;
	}

	/**
	 * Gets the display name of the language.
	 *
	 * @return the display name of the language
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * Gets the Tesseract language code for this language.
	 *
	 * @return the Tesseract language code
	 */
	public String getCode()
	{
		return code;
	}

	@Override
	public String toString()
	{
		return displayName;
	}
}
