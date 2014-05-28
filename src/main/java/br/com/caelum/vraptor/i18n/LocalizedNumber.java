package br.com.caelum.vraptor.i18n;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizedNumber implements LocalizedInfo {

	private final Number key;
	private final ResourceBundle bundle;
	private final Locale locale;

	private NumberFormat formatter;

	public LocalizedNumber(Number key, ResourceBundle bundle, Locale locale) {
		this.key = key;
		this.bundle = bundle;
		this.locale = locale;
		this.formatter = NumberFormat.getNumberInstance(locale);
	}

	@Override
	public String toString() {
		return formatter.format(key);
	}

	public LocalizedNumber pattern(String pattern) {
		this.formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(locale));
		return this;
	}

	public LocalizedNumber custom(String name) {
		String key = "formats.number." + name;
		String pattern = bundle.getString(key);
		if(String.format("???%s???", name).equals(pattern)) {
			throw new IllegalArgumentException("Custom formatter " + key + " does not exist in your resource bundle.");
		} else {
			this.formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(locale));
			return this;
		}
	}
}
