package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalizedFormatterTest {

	private MockResourceBundle bundle;

	private Date data;
	private L locator;
	private DateTime joda;
	private Calendar cal;
	private DateFormat formatter;

	@Before
	public void setup(){
		bundle = new MockResourceBundle(Locale.US);
		joda = new DateTime(2013, 2, 1, 15, 45, 13, 111);
		data = joda.toDate();
		cal = joda.toGregorianCalendar();
		formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, bundle.getLocale());
		locator = new L(bundle);
	}

	@Test
	public void should_format_date_using_default_format() {
		assertEquals(formatter.format(data), locator.get(data).toString());
	}

	@Test
	public void should_format_date_using_custom_format() {
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", bundle.getLocale());
		assertEquals(formatter.format(data), locator.get(data).pattern("dd/MM/yyyy hh:mm:ss").toString());
	}

	@Test
	public void should_format_date_using_full_format() {
		formatter = DateFormat.getDateInstance(DateFormat.FULL, bundle.getLocale());
		assertEquals(formatter.format(data), ((LocalizedData)locator.get(data)).format("full").toString());
	}

	@Test
	public void should_format_calendar_using_default_format(){
		assertEquals(formatter.format(cal.getTime()), locator.get(cal).toString());
	}

	@Test
	public void should_format_calendar_using_custom_format(){
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", bundle.getLocale());
		assertEquals(formatter.format(cal.getTime()), locator.get(cal).pattern("dd/MM/yyyy hh:mm:ss").toString());
	}

	@Test
	public void should_format_calendar_using_full_format() {
		formatter = DateFormat.getDateInstance(DateFormat.FULL, bundle.getLocale());
		assertEquals(formatter.format(cal.getTime()), ((LocalizedData)locator.get(cal)).format("full").toString());
	}

	@Test
	public void should_format_joda_time_using_default_format(){
		assertEquals(formatter.format(joda.toDate()), locator.get(joda).toString());
	}

	@Test
	public void should_format_joda_time_using_custom_format(){
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", bundle.getLocale());
		assertEquals(formatter.format(joda.toDate()), locator.get(joda).pattern("dd/MM/yyyy hh:mm:ss").toString());
	}

	@Test
	public void should_format_joda_time_using_full_format() {
		formatter = DateFormat.getDateInstance(DateFormat.FULL, bundle.getLocale());
		assertEquals(formatter.format(joda.toDate()), ((LocalizedData) locator.get(joda)).format("full").toString());
	}

	@Test
	public void should_format_date_using_properties() {
		String message = "yyyy.MM.dd G 'at' HH:mm:ss z";
		bundle.addWord("formats.time.pirate", message);

		formatter = new SimpleDateFormat(message);
		assertEquals(formatter.format(data), locator.get(data).custom("pirate").toString());
	}
}
