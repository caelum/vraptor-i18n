package br.com.caelum.vraptor.i18n;

import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.ForwardingMap;

@Named("t")
@RequestScoped
public class Translator extends ForwardingMap<Class<?>, Object> {

	private ResourceBundle bundle;

	/**
	 * @deprecated CDI eyes only
	 */
	public Translator() {
		this(null);
	}

	@Inject
	public Translator(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public boolean containsKey(Object key) {
		return !bundle.getString(key.toString()).equals("???" + key.toString() + "???");
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
