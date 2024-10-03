package io.github.astrapisixtynine.pdf.to.text.info;

import java.io.File;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversionResult
{
	List<File> imageFiles;
	List<File> textFiles;
	File resultTextFile;
}
