package br.com.caelum.vraptor.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.vraptor.core.Localization;

public class Message {

	private static final String[] ZERO = { "0" };
	private static final String[] ONE = { "1" };
	private String key;
	private final Localization localization;
	private String[] args;

	public Message(Localization localization, String key) {
		this.localization = localization;
		this.key = key;
	}

	public Message count(int count) {
		if (count == 0) {
			key += ".zero";
			args = ZERO;
		} else if (count == 1) {
			key += ".one";
			args = ONE;
		} else {
			key += ".other";
			args = new String[] { "" + count };
		}
		return this;
	}

	public Message args(String... parameters) {
		if (this.args == null) {
			this.args = parameters;
			return this;
		}
		int pos = args.length;
		this.args = Arrays.copyOf(this.args, this.args.length
				+ parameters.length);
		for (int i = 0; i < parameters.length; i++) {
			this.args[pos++] = parameters[i];
		}
		return this;
	}

	private String getValue() {
		String message = localization.getMessage(key.toString());
		if (message.equals("???" + key.toString() + "???")) {
			return "<span class='i18n_missing_key'>" + key + "</span>";
		}
		if (this.args == null)
			return message;
		return MessageFormat.format(message, args);
	}

	@Override
	public String toString() {
		return getValue();
	}

}
