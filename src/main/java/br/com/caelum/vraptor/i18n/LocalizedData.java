package br.com.caelum.vraptor.i18n;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizedData implements LocalizedInfo {

	private DateFormat formatter;
	private final Date key;
	private final Locale locale;
	private final ResourceBundle bundle;

	public LocalizedData(Date parsedKey, ResourceBundle bundle) {
		this.key = parsedKey;
		this.bundle = bundle;
		this.locale = bundle.getLocale();
		this.formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
	}

	@Override
	public String toString() {
		return this.formatter.format(key);
	}

	public String format(String format){
		format = format.toUpperCase();
		try {
			Field field = DateFormat.class.getDeclaredField(format);
			this.formatter = DateFormat.getDateInstance(field.getInt(field.getName()), locale);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.toString();
	}

	public LocalizedData pattern(String pattern){
		this.formatter = new SimpleDateFormat(pattern, locale);
		return this;
	}

	public LocalizedData custom(String customFormat) {
		String pattern = bundle.getString("formats.time." + customFormat);
		return pattern(pattern);
	}
}
