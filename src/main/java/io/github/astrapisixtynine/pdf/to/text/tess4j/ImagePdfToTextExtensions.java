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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import lombok.extern.java.Log;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * The class {@link ImagePdfToTextExtensions} provides functionality to convert a PDF file into a
 * text file using either direct text extraction or Optical Character Recognition (OCR)
 */
@Log
public final class ImagePdfToTextExtensions
{

	/**
	 * Private constructor to prevent instantiation
	 */
	private ImagePdfToTextExtensions()
	{
	}

	/**
	 * Extracts text from a single image file using Tesseract OCR
	 *
	 * @param imageFile
	 *            the image file to process
	 * @param datapath
	 *            the path to Tesseract data files
	 * @param language
	 *            the language to use for OCR
	 * @return the extracted text
	 * @throws TesseractException
	 *             if an error occurs during OCR
	 */
	public static String extractTextFromImage(File imageFile, String datapath, String language)
		throws TesseractException
	{
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath(datapath);
		tesseract.setLanguage(language);
		return tesseract.doOCR(imageFile);
	}

	/**
	 * Checks if Tesseract OCR is installed on the system by executing the "tesseract --version"
	 * command.
	 *
	 * @return true if Tesseract is installed and available in the system's PATH; false otherwise.
	 */
	public static boolean isTesseractInstalled()
	{
		try
		{
			// Try to execute the "tesseract" command
			ProcessBuilder processBuilder = new ProcessBuilder("tesseract", "--version");
			Process process = processBuilder.start();

			// Read the output
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
			String output = reader.readLine();

			// Wait for the process to complete and check the exit value
			int exitCode = process.waitFor();

			// Check if the command was successful and if the output contains Tesseract version
			// information
			return exitCode == 0 && output != null && output.contains("tesseract");
		}
		catch (Exception e)
		{
			// If any exception occurs, Tesseract is likely not installed or not in the system PATH
			return false;
		}
	}

}
