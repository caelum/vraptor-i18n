package br.com.caelum.vraptor.i18n;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.caelum.vraptor.core.Localization;

public class LocalizedData implements LocalizedInfo {

	private DateFormat formatter;
	private final Date key;
	private final Locale locale;
	private final Localization localization;

	public LocalizedData(Date parsedKey, Localization localization) {
		this.key = parsedKey;
		this.localization = localization;
		this.locale = localization.getLocale();
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
		String pattern = localization.getMessage("formats.time." + customFormat);
		return pattern(pattern);
	}
}
