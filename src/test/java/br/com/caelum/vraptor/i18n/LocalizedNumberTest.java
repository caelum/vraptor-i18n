package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalizedNumberTest {

	private ResourceBundle bundle;
	private ResourceBundle englishBundle;

	@Before
	public void before() {
		this.bundle = new MockResourceBundle();
		this.englishBundle = new MockResourceBundle(Locale.ENGLISH);
	}

	@Test
	public void should_convert_int_with_default_locale() {
		assertEquals("15", new LocalizedNumber(15, bundle).toString());
		assertEquals("1.500", new LocalizedNumber(1500, bundle).toString());

		assertEquals("15", new LocalizedNumber(15, englishBundle).toString());
		assertEquals("1,500", new LocalizedNumber(1500, englishBundle).toString());
	}

	@Test
	public void should_convert_double_with_default_locale() {
		assertEquals("15,357", new LocalizedNumber(15.357, bundle).toString());
		assertEquals("1.500,357", new LocalizedNumber(1500.357, bundle).toString());
		assertEquals("15,3", new LocalizedNumber(15.30, bundle).toString());

		assertEquals("15.357", new LocalizedNumber(15.357, englishBundle).toString());
		assertEquals("1,500.357", new LocalizedNumber(1500.357, englishBundle).toString());
		assertEquals("15.3", new LocalizedNumber(15.30, englishBundle).toString());
	}

	@Test
	public void should_convert_big_decimal_with_default_locale() {
		assertEquals("15,357", new LocalizedNumber(new BigDecimal(15.357), bundle).toString());
		assertEquals("1.500,357", new LocalizedNumber(new BigDecimal(1500.357), bundle).toString());

		assertEquals("15.357", new LocalizedNumber(new BigDecimal(15.357), englishBundle).toString());
		assertEquals("1,500.357", new LocalizedNumber(new BigDecimal(1500.357), englishBundle).toString());
	}


	@Test
	public void should_convert_numbers_with_patterns() {
		assertEquals("BRL 0.015,30", new LocalizedNumber(new BigDecimal(15.3), bundle).pattern("'BRL' 0,000.00").toString());
		assertEquals("BRL 11.500,30", new LocalizedNumber(new BigDecimal(11500.3), bundle).pattern("'BRL' 0,000.00").toString());

		assertEquals("BRL 0,015.30", new LocalizedNumber(new BigDecimal(15.3), englishBundle).pattern("'BRL' 0,000.00").toString());
		assertEquals("BRL 11,500.30", new LocalizedNumber(new BigDecimal(11500.3), englishBundle).pattern("'BRL' 0,000.00").toString());
	}

	@Test
	public void should_convert_percentage() {
		assertEquals("15%", new LocalizedNumber(new BigDecimal(0.153), bundle).pattern("#%").toString());
		assertEquals("15,30%", new LocalizedNumber(new BigDecimal(0.153), bundle).pattern("#.00%").toString());
	}

	@Test
	public void should_convert_numbers_with_custom_pattern() {
		MockResourceBundle bundle = new MockResourceBundle();
		bundle.addWord("formats.number.billionaire", "0,000,000,000");
		assertEquals("0.000.000.015", new LocalizedNumber(new BigDecimal(15.3), bundle).custom("billionaire").toString());
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_complain_if_custom_pattern_is_not_configured() {
		new LocalizedNumber(new BigDecimal(15.3), bundle).custom("billionaire");
	}
}
