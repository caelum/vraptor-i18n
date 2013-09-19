package br.com.caelum.vraptor.i18n;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.Component;

import com.google.common.collect.ForwardingMap;

@Component
public class LocalizedFormatter extends ForwardingMap<Class<?>, Object> {
	
	private final Localization localization;
	private DateFormat formatter;
	private Date key;

	public LocalizedFormatter(Localization localization) {
		this.localization = localization;
	}

	@Override
	public LocalizedFormatter get(Object key) {
		if(key instanceof Calendar)
			this.key = ((Calendar) key).getTime();
		else if(key instanceof Date)
			this.key = (Date) key;
		else{
			try {
				if (isJodaTime(key))
					this.key = convertJodaTime(key);
				else
					throw new IllegalArgumentException("Cannot format given Object as a Date");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		this.formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, localization.getLocale());
		return this;
	}

	private boolean isJodaTime(Object key){
		Class<?> clazz;
		try {
			clazz = Class.forName("org.joda.time.base.AbstractInstant");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return clazz.isAssignableFrom(key.getClass());
	}

	private Date convertJodaTime(Object key) throws Exception{
		return (Date) key.getClass().getMethod("toDate").invoke(key);
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.toString();
	}

	public String custom(String pattern){
		this.formatter = new SimpleDateFormat(pattern, localization.getLocale());
		return this.toString();
	}

	/**
	 * All methods from {@link Map} that were not override by {@link LocalizedFormatter} will call {@link #delegate()}
	 * This way all methods that were not override will throw {@link UnsupportedOperationException}
	 */
	@Override
	protected Map<Class<?>, Object> delegate() {
		throw new UnsupportedOperationException("A i18n localized formatter does not support this method");
	}

}
