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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.modify.ModifyFileExtensions;
import io.github.astrapi69.file.write.StoreFileExtensions;
import io.github.astrapi69.io.file.FileExtension;
import io.github.astrapi69.io.file.FilenameExtensions;
import io.github.astrapisixtynine.pdf.to.text.info.ConversionResult;
import io.github.astrapisixtynine.pdf.to.text.pdfbox.PdfToTextExtensions;
import lombok.extern.java.Log;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * The class {@link ImagePdfToTextExtensions} provides functionality to convert a PDF file into a
 * text file using either direct text extraction or Optical Character Recognition (OCR) through
 * Tesseract OCR
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
	 * Converts a text or image PDF file to text using image processing and OCR
	 *
	 * @param pdfFile
	 *            the input PDF file
	 * @param outputDir
	 *            the directory where the output files will be stored
	 * @param datapath
	 *            the path to Tesseract data files
	 * @param language
	 *            the language to use for OCR
	 * @return the result of the conversion containing image files, text files, and the final result
	 *         text file
	 * @return the list of generated text files
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws TesseractException
	 *             if an error occurs during OCR
	 */
	public static ConversionResult convertPdfToTextfile(File pdfFile, File outputDir,
		String datapath, String language) throws IOException, TesseractException
	{

		String txtFileName;
		String fileName;
		fileName = FilenameExtensions.getFilenameWithoutExtension(pdfFile);
		txtFileName = fileName + FileExtension.TXT.getExtension();
		File resultTextFile = FileFactory.newFile(outputDir, txtFileName);

		// step 1: convert the pdf file to image files
		List<File> imageFiles = PdfToTextExtensions.getImageFiles(pdfFile, outputDir);

		// step 2: convert the image files to text files
		List<File> textFiles = getTextFiles(imageFiles, outputDir, datapath, language);

		// step 3: concatenate all text files to one
		ModifyFileExtensions.concatenateAll(textFiles, resultTextFile);

		return ConversionResult.builder().imageFiles(imageFiles).textFiles(textFiles)
			.resultTextFile(resultTextFile).build();
	}

	/**
	 * Converts text or image PDF files into text files using Tesseract OCR
	 *
	 * @param imageFiles
	 *            the list of image files to be processed
	 * @param resultDir
	 *            the directory where the text files will be stored
	 * @param datapath
	 *            the path to Tesseract data files
	 * @param language
	 *            the language to use for OCR
	 * @return the list of generated text files
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws TesseractException
	 *             if an error occurs during OCR
	 */
	public static List<File> getTextFiles(List<File> imageFiles, File resultDir, String datapath,
		String language) throws IOException, TesseractException
	{
		List<File> textFiles = new ArrayList<>();
		for (int page = 0; page < imageFiles.size(); ++page)
		{
			File imageFile = imageFiles.get(page);
			String textFileName = FilenameExtensions.getFilenameWithoutExtension(imageFile);
			String string = extractTextFromImage(imageFile, datapath, language);
			File textFile = new File(resultDir, textFileName + ".txt");
			StoreFileExtensions.toFile(textFile, string);
			textFiles.add(textFile);
		}
		return textFiles;
	}

	/**
	 * Converts image files into text using Tesseract OCR
	 *
	 * @param imageFiles
	 *            the list of image files to be processed
	 * @param datapath
	 *            the path to Tesseract data files
	 * @param language
	 *            the language to use for OCR
	 * @return the {@link String} object that contains the result
	 * @throws TesseractException
	 *             if an error occurs during OCR
	 */
	public static String getTextContent(List<File> imageFiles, String datapath, String language)
		throws TesseractException
	{
		StringBuilder stringBuffer = new StringBuilder();
		for (int page = 0; page < imageFiles.size(); ++page)
		{
			File imageFile = imageFiles.get(page);
			String string = extractTextFromImage(imageFile, datapath, language);
			stringBuffer.append(string);

		}
		return stringBuffer.toString();
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
	 * command
	 *
	 * @return true if Tesseract is installed and available in the system's PATH; false otherwise
	 */
	public static boolean isTesseractInstalled()
	{
		try
		{
			ProcessBuilder processBuilder = new ProcessBuilder("tesseract", "--version");
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
			String output = reader.readLine();

			int exitCode = process.waitFor();
			return exitCode == 0 && output != null && output.contains("tesseract");
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
