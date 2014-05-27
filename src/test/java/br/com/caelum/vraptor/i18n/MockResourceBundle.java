package br.com.caelum.vraptor.i18n;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class MockResourceBundle extends ResourceBundle {

	private Map<String, String> map = new HashMap<>();
	private final Locale locale;

	public MockResourceBundle() {
		this(new Locale("pt", "br"));
	}

	public MockResourceBundle(Locale locale) {
		this.locale = locale;
	}

	@Override
	protected Object handleGetObject(String key) {
		if(map.containsKey(key)) {
			return map.get(key);
		} else {
			return String.format("???%s???", key);
		}
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(map.keySet());
	}

	public void addWord(String key, String value) {
		map.put(key, value);
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

}
