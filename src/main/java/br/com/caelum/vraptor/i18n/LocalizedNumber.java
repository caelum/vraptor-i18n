package br.com.caelum.vraptor.i18n;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import br.com.caelum.vraptor.core.Localization;

public class LocalizedNumber implements LocalizedInfo {

	private final Number key;
	private final Localization localization;
	private NumberFormat formatter;

	public LocalizedNumber(Number key, Localization localization) {
		this.key = key;
		this.localization = localization;
		this.formatter = NumberFormat.getNumberInstance(localization.getLocale());
	}
	
	@Override
	public String toString() {
		return formatter.format(key); 
	}

	public LocalizedNumber pattern(String pattern) {
		this.formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(localization.getLocale()));
		return this;
	}

	public LocalizedNumber custom(String name) {
		String key = "formats.number." + name;
		String pattern = localization.getMessage(key);
		if(pattern == null) {
			throw new IllegalArgumentException("Custom formatter " + key + " does not exist in your resource bundle.");
		}
		this.formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(localization.getLocale()));
		return this;
	}

}
