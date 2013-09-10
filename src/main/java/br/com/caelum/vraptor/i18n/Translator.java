package br.com.caelum.vraptor.i18n;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class Translator implements Map<String, Message> {
	
	private final Localization localization;

	Translator(Localization localization) {
		this.localization = localization;
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public boolean containsKey(Object key) {
		return !localization.getMessage(key.toString()).equals("???" + key.toString() + "???");
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public Message get(Object key) {
		String message = localization.getMessage(key.toString());
		if(message.equals("???" + key.toString() + "???")) {
			return new Message("<span class='i18n_missing_key'>" + key + "</span>");
		}
		return new Message(message);
	}


	@Override
	public void clear() {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public Set<String> keySet() {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public Message put(String key, Message value) {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public Message remove(Object key) {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public void putAll(Map<? extends String, ? extends Message> m) {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public Collection<Message> values() {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}

	@Override
	public Set<java.util.Map.Entry<String, Message>> entrySet() {
		throw new UnsupportedOperationException("A i18n translator does not support this method");
	}


}
