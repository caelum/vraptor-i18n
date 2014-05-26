package br.com.caelum.vraptor.i18n;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ForwardingMap;

@Named("t")
public class Translator extends ForwardingMap<Class<?>, Object> {

	@Inject
	private ResourceBundle bundle;

	@Deprecated
	public Translator() { }

	@VisibleForTesting
	public Translator(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public boolean containsKey(Object key) {
		try {
			bundle.getString(key.toString());
			return true;
		} catch(MissingResourceException e) {
			return false;
		}
	}

	@Override
	public Message get(Object key) {
		return new Message(bundle, key.toString());
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
