package br.com.caelum.vraptor.i18n;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizedNumber implements LocalizedInfo {

	private final Number key;
	private NumberFormat formatter;
	private ResourceBundle bundle;

	public LocalizedNumber(Number key, ResourceBundle bundle) {
		this.key = key;
		this.bundle = bundle;
		this.formatter = NumberFormat.getNumberInstance(bundle.getLocale());
	}

	@Override
	public String toString() {
		return formatter.format(key);
	}

	public LocalizedNumber pattern(String pattern) {
		this.formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(bundle.getLocale()));
		return this;
	}

	public LocalizedNumber custom(String name) {
		String key = "formats.number." + name;
		try {
			String pattern = bundle.getString(key);
			this.formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(bundle.getLocale()));
			return this;
		} catch (MissingResourceException e) {
			throw new IllegalArgumentException("Custom formatter " + key + " does not exist in your resource bundle.");
		}
	}
}
