package br.com.caelum.vraptor.i18n;

import java.util.Map;

import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.Component;

import com.google.common.collect.ForwardingMap;

@Component
public class Translator extends ForwardingMap<Class<?>, Object> {
	
	private final Localization localization;

	Translator(Localization localization) {
		this.localization = localization;
	}

	@Override
	public boolean containsKey(Object key) {
		return !localization.getMessage(key.toString()).equals("???" + key.toString() + "???");
	}

	@Override
	public Message get(Object key) {
		return new Message(localization, key.toString());
	}

	/**
	 * All methods from {@link Map} that were not override by {@link Translator} will call {@link #delegate()}
	 * This way all methods that were not override will throw {@link UnsupportedOperationException}
	 */
	@Override
	protected Map<Class<?>, Object> delegate() {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

}
