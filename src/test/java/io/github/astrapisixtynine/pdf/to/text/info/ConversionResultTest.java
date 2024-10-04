package io.github.astrapisixtynine.pdf.to.text.info;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.BeanVerifier;

/**
 * Test class for {@link ConversionResult}
 */
class ConversionResultTest
{

	/**
	 * Test method for {@link ConversionResult} with {@link BeanTester}
	 */
	@Test
	@DisplayName("Test ConversionResult with BeanTester")
	public void testWithBeanTester()
	{
		ConversionResult conversionResult = ConversionResult.builder().imageFiles(new ArrayList<>())
			.textFiles(new ArrayList<>()).resultTextFile(new File(".")).build();
		ConversionResult.ConversionResultBuilder builder = conversionResult.toBuilder();
		BeanVerifier.forClass(ConversionResult.class).editSettings()
			.registerFactory(ConversionResult.class, builder::build).edited().verify();
	}
}