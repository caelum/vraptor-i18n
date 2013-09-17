package br.com.caelum.vraptor.i18n;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Map;

import com.google.common.collect.ForwardingMap;

import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class LocalizedFormatter extends ForwardingMap<Class<?>, Object> {
	
	private final Localization localization;
	private DateFormat formatter;
	private Object key;

	public LocalizedFormatter(Localization localization) {
		this.localization = localization;
	}

	@Override
	public LocalizedFormatter get(Object key) {
		this.key = key;
		this.formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, localization.getLocale());
		return this;
	}
	
	@Override
	public String toString() {
		return this.formatter.format(key);
	}
	
	public String format(String format){
		format = format.toUpperCase();
		try {
			Field field = DateFormat.class.getDeclaredField(format);
			this.formatter = DateFormat.getDateInstance(field.getInt(field.getName()), localization.getLocale());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return this.toString();
	}
	
	/**
	 * All methods from {@link Map} that were not override by {@link LocalizedFormatter} will call {@link #delegate()}
	 * This way all methods that were not override will throw {@link UnsupportedOperationException}
	 */
	@Override
	protected Map<Class<?>, Object> delegate() {
		throw new UnsupportedOperationException("A i18n locator does not support this method");
	}

}
