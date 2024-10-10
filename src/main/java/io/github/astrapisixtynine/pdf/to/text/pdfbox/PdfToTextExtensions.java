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
package io.github.astrapisixtynine.pdf.to.text.pdfbox;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.modify.ModifyFileExtensions;
import io.github.astrapi69.io.file.FileExtension;
import io.github.astrapi69.io.file.FilenameExtensions;
import io.github.astrapi69.io.shell.LinuxShellExecutor;
import io.github.astrapisixtynine.pdf.to.text.info.ConversionResult;
import lombok.extern.java.Log;

/**
 * The class {@link PdfToTextExtensions} provides functionality to convert a PDF file into a text
 * file using either direct text extraction or Optical Character Recognition (OCR)
 */
@Log
public final class PdfToTextExtensions
{

	/**
	 * Private constructor to prevent instantiation
	 */
	private PdfToTextExtensions()
	{
	}

	/**
	 * Converts a text PDF file to a text file extracting text from all pages
	 *
	 * @param pdfFile
	 *            the input PDF file
	 * @param resultDir
	 *            the directory where the output text file will be stored
	 * @return the generated text file
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static File pdfToText(File pdfFile, File resultDir) throws IOException
	{
		int numberOfPages;
		try (PDDocument document = Loader.loadPDF(pdfFile))
		{
			numberOfPages = document.getNumberOfPages();
		}
		return pdfToText(pdfFile, resultDir, 0, numberOfPages);
	}

	/**
	 * Converts a text PDF file to a text file, with the option to specify a range of pages to
	 * extract
	 *
	 * @param pdfFile
	 *            the input PDF file
	 * @param resultDir
	 *            the directory where the output text file will be stored
	 * @param startPageValue
	 *            the starting page number
	 * @param endPageValue
	 *            the ending page number
	 * @return the generated text file
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static File pdfToText(File pdfFile, File resultDir, int startPageValue, int endPageValue)
		throws IOException
	{
		String txtFileName;
		String fileName;
		fileName = FilenameExtensions.getFilenameWithoutExtension(pdfFile);
		txtFileName = fileName + FileExtension.TXT.getExtension();
		File resultTextFile = FileFactory.newFile(resultDir, txtFileName);

		try (PDDocument document = Loader.loadPDF(pdfFile);
			BufferedWriter wr = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(resultTextFile))))
		{
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setStartPage(startPageValue); // Start extracting from first page
			stripper.setEndPage(endPageValue); // Extract till the end
			stripper.writeText(document, wr);
		}
		return resultTextFile;
	}

	/**
	 * Converts a text or image PDF file to text using image processing and OCR
	 *
	 * @param pdfFile
	 *            the input PDF file
	 * @param outputDir
	 *            the directory where the output files will be stored
	 * @return the result of the conversion containing image files, text files, and the final result
	 *         text file
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws InterruptedException
	 *             if the process is interrupted
	 */
	public static ConversionResult convertPdfToTextfile(File pdfFile, File outputDir)
		throws IOException, InterruptedException
	{
		String txtFileName;
		String fileName;
		fileName = FilenameExtensions.getFilenameWithoutExtension(pdfFile);
		txtFileName = fileName + FileExtension.TXT.getExtension();
		File resultTextFile = FileFactory.newFile(outputDir, txtFileName);

		// step 1: convert the pdf file to image files
		List<File> imageFiles = getImageFiles(pdfFile, outputDir);

		// step 2: convert the image files to text files
		String shellPath = "/bin/sh";
		List<File> textFiles = getTextFiles(imageFiles, outputDir, shellPath);

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
	 * @param shellPath
	 *            the shell path for executing commands
	 * @return the list of generated text files
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws InterruptedException
	 *             if the process is interrupted
	 */
	public static List<File> getTextFiles(List<File> imageFiles, File resultDir, String shellPath)
		throws IOException, InterruptedException
	{
		String output;
		String command;
		String commandPrefix;
		commandPrefix = "tesseract";
		String executionPath = resultDir.toString();
		List<File> textFiles = new ArrayList<>();
		for (int page = 0; page < imageFiles.size(); ++page)
		{
			File imageFile = imageFiles.get(page);
			String imageFileName = imageFile.getName();
			String textFileName = FilenameExtensions.getFilenameWithoutExtension(imageFile);
			command = commandPrefix + " " + imageFileName + " " + textFileName + " -l deu";
			log.log(Level.INFO, "Executing command: " + command);
			output = LinuxShellExecutor.execute(shellPath, executionPath, command);
			log.log(Level.INFO, "Output from command: " + output);
			File textFile = new File(resultDir, textFileName + ".txt");
			textFiles.add(textFile);
		}
		return textFiles;
	}

	/**
	 * Converts a text or image PDF file into image files for each page
	 *
	 * @param pdfFile
	 *            the input PDF file
	 * @param outputDir
	 *            the directory where the image files will be stored
	 * @return the list of generated image files
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static List<File> getImageFiles(File pdfFile, File outputDir) throws IOException
	{
		List<File> imageFiles = new ArrayList<>();
		String fileName = FilenameExtensions.getFilenameWithoutExtension(pdfFile);
		try (PDDocument document = Loader.loadPDF(pdfFile))
		{
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			for (int page = 0; page < document.getNumberOfPages(); ++page)
			{
				BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
				File imageFile = new File(outputDir, fileName + "page_" + (page + 1) + ".png");
				ImageIO.write(image, "png", imageFile);
				imageFiles.add(imageFile);
			}
		}
		return imageFiles;
	}

}
