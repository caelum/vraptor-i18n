package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.core.Localization;

@RunWith(MockitoJUnitRunner.class)
public class LocalizedNumberTest {
	
	private static final Locale PT_BR = new Locale("pt", "br");
	@Mock
	private Localization localization;
	@Test
	public void should_convert_int_with_default_locale() {
		when(localization.getLocale()).thenReturn(PT_BR);
		assertEquals("15", new LocalizedNumber(15, localization).toString());
		assertEquals("1.500", new LocalizedNumber(1500, localization).toString());
		
		when(localization.getLocale()).thenReturn(Locale.ENGLISH);
		assertEquals("15", new LocalizedNumber(15, localization).toString());
		assertEquals("1,500", new LocalizedNumber(1500, localization).toString());
	}

	@Test
	public void should_convert_double_with_default_locale() {
		when(localization.getLocale()).thenReturn(PT_BR);
		assertEquals("15,357", new LocalizedNumber(15.357, localization).toString());
		assertEquals("1.500,357", new LocalizedNumber(1500.357, localization).toString());
		assertEquals("15,3", new LocalizedNumber(15.30, localization).toString());
		
		when(localization.getLocale()).thenReturn(Locale.ENGLISH);
		assertEquals("15.357", new LocalizedNumber(15.357, localization).toString());
		assertEquals("1,500.357", new LocalizedNumber(1500.357, localization).toString());
		assertEquals("15.3", new LocalizedNumber(15.30, localization).toString());
	}

	@Test
	public void should_convert_big_decimal_with_default_locale() {
		when(localization.getLocale()).thenReturn(PT_BR);
		assertEquals("15,357", new LocalizedNumber(new BigDecimal(15.357), localization).toString());
		assertEquals("1.500,357", new LocalizedNumber(new BigDecimal(1500.357), localization).toString());
		
		when(localization.getLocale()).thenReturn(Locale.ENGLISH);
		assertEquals("15.357", new LocalizedNumber(new BigDecimal(15.357), localization).toString());
		assertEquals("1,500.357", new LocalizedNumber(new BigDecimal(1500.357), localization).toString());
	}


	@Test
	public void should_convert_numbers_with_patterns() {
		when(localization.getLocale()).thenReturn(PT_BR);
		assertEquals("BRL 0.015,30", new LocalizedNumber(new BigDecimal(15.3), localization).pattern("'BRL' 0,000.00").toString());
		assertEquals("BRL 11.500,30", new LocalizedNumber(new BigDecimal(11500.3), localization).pattern("'BRL' 0,000.00").toString());
		
		when(localization.getLocale()).thenReturn(Locale.ENGLISH);
		assertEquals("BRL 0,015.30", new LocalizedNumber(new BigDecimal(15.3), localization).pattern("'BRL' 0,000.00").toString());
		assertEquals("BRL 11,500.30", new LocalizedNumber(new BigDecimal(11500.3), localization).pattern("'BRL' 0,000.00").toString());
	}

	@Test
	public void should_convert_percentage() {
		when(localization.getLocale()).thenReturn(PT_BR);
		assertEquals("15%", new LocalizedNumber(new BigDecimal(0.153), localization).pattern("#%").toString());
		assertEquals("15,30%", new LocalizedNumber(new BigDecimal(0.153), localization).pattern("#.00%").toString());
	}

	@Test
	public void should_convert_numbers_with_custom_pattern() {
		when(localization.getLocale()).thenReturn(PT_BR);
		when(localization.getMessage("formats.number.billionaire")).thenReturn("0,000,000,000");
		assertEquals("0.000.000.015", new LocalizedNumber(new BigDecimal(15.3), localization).custom("billionaire").toString());
	}

	@Test(expected=IllegalArgumentException.class)
	public void should_complain_if_custom_pattern_is_not_configured() {
		when(localization.getLocale()).thenReturn(PT_BR);
		new LocalizedNumber(new BigDecimal(15.3), localization).custom("billionaire");
	}
}
