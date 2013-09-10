package br.com.caelum.vraptor.i18n;

import java.util.Collections;
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
		String message = localization.getMessage(key.toString());
		if(message.equals("???" + key.toString() + "???")) {
			return new Message("<span class='i18n_missing_key'>" + key + "</span>");
		}
		return new Message(message);
	}

	@Override
	protected Map<Class<?>, Object> delegate() {
		return Collections.emptyMap();
	}

}
