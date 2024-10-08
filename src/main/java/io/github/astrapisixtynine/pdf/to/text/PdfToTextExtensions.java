package io.github.astrapisixtynine.pdf.to.text;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.read.ReadFileExtensions;
import io.github.astrapi69.file.write.StoreFileExtensions;
import io.github.astrapi69.io.file.FileExtension;
import io.github.astrapi69.io.file.FilenameExtensions;
import io.github.astrapi69.io.shell.LinuxShellExecutor;
import io.github.astrapisixtynine.pdf.to.text.info.ConversionResult;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * The class {@link PdfToTextExtensions} provides functionality to convert a PDF file into a text
 * file using either direct text extraction or Optical Character Recognition (OCR)
 */
public final class PdfToTextExtensions
{

	/**
	 * Private constructor to prevent instantiation
	 */
	private PdfToTextExtensions()
	{
	}

	/**
	 * Converts a PDF file to a text file extracting text from all pages
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
	 * Converts a PDF file to a text file, with the option to specify a range of pages to extract
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
	 * Converts a PDF file to text using image processing and OCR
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
		concatenateAll(textFiles, resultTextFile);

		return ConversionResult.builder().imageFiles(imageFiles).textFiles(textFiles)
			.resultTextFile(resultTextFile).build();
	}

	/**
	 * Converts image files into text files using Tesseract OCR
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
			output = LinuxShellExecutor.execute(shellPath, executionPath, command);
			System.out.println(output);
			File textFile = new File(resultDir, textFileName + ".txt");
			textFiles.add(textFile);
		}
		return textFiles;
	}

	/**
	 * Converts a PDF file into image files for each page
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

	/**
	 * Concatenates the contents of all text files into a single text file
	 *
	 * @param textFiles
	 *            the list of text files to concatenate
	 * @param resultTextFile
	 *            the final result text file that will contain the concatenated content
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void concatenateAll(List<File> textFiles, File resultTextFile) throws IOException
	{
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < textFiles.size(); ++i)
		{
			File textFile = textFiles.get(i);
			String content = ReadFileExtensions.fromFile(textFile);
			text.append(content);
		}
		StoreFileExtensions.toFile(resultTextFile, text.toString());
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
}
