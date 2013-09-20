package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
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
	private DateTime joda;
	private Calendar cal;
	private DateFormat formatter;

	@Before
	public void setup(){
		when(localization.getLocale()).thenReturn(Locale.US);
		joda = new DateTime(2013, 2, 1, 15, 45, 13, 111);
		data = joda.toDate();
		cal = joda.toGregorianCalendar();
		formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, localization.getLocale());
		locator = new LocalizedFormatter(localization);
	}

	@Test
	public void should_format_date_using_default_format() {
		assertEquals(formatter.format(data), locator.get(data).toString());
	}

	@Test
	public void should_format_date_using_custom_format() {
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", localization.getLocale());
		assertEquals(formatter.format(data), locator.get(data).custom("dd/MM/yyyy hh:mm:ss").toString());
	}

	@Test
	public void should_format_date_using_full_format() {
		formatter = DateFormat.getDateInstance(DateFormat.FULL, localization.getLocale());
		assertEquals(formatter.format(data), locator.get(data).format("full").toString());
	}

	@Test
	public void should_format_calendar_using_default_format(){
		assertEquals(formatter.format(cal.getTime()), locator.get(cal).toString());
	}

	@Test
	public void should_format_calendar_using_custom_format(){
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", localization.getLocale());
		assertEquals(formatter.format(cal.getTime()), locator.get(cal).custom("dd/MM/yyyy hh:mm:ss").toString());
	}

	@Test
	public void should_format_calendar_using_full_format() {
		formatter = DateFormat.getDateInstance(DateFormat.FULL, localization.getLocale());
		assertEquals(formatter.format(cal.getTime()), locator.get(cal).format("full").toString());
	}

	@Test
	public void should_format_joda_time_using_default_format(){
		assertEquals(formatter.format(joda.toDate()), locator.get(joda).toString());
	}

	@Test
	public void should_format_joda_time_using_custom_format(){
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", localization.getLocale());
		assertEquals(formatter.format(joda.toDate()), locator.get(joda).custom("dd/MM/yyyy hh:mm:ss").toString());
	}

	@Test
	public void should_format_joda_time_using_full_format() {
		formatter = DateFormat.getDateInstance(DateFormat.FULL, localization.getLocale());
		assertEquals(formatter.format(joda.toDate()), locator.get(joda).format("full").toString());
	}
}
