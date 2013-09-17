package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.core.Localization;

@RunWith(MockitoJUnitRunner.class)
public class LocalizedFormatterTest {

	@Mock
	private Localization localization;
	private Date data;
	private LocalizedFormatter locator;
	
	@Before
	public void setup(){
		data = new Date();
		locator = new LocalizedFormatter(localization);
		when(localization.getLocale()).thenReturn(Locale.US);
	}

	@Test
	public void should_format_date_using_default_format() {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, localization.getLocale());
		assertEquals(formatter.format(data), locator.get(data).toString());
	}
	
	@Test
	public void should_format_date_using_short_format() {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, localization.getLocale());
		assertEquals(formatter.format(data), locator.get(data).format("short").toString());
	}
	
	@Test
	public void should_format_date_using_full_format() {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL, localization.getLocale());
		assertEquals(formatter.format(data), locator.get(data).format("full").toString());
	}

}
