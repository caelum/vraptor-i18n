package br.com.caelum.vraptor.i18n;

import java.text.MessageFormat;

public class Message {

	private final String message;

	public Message(String message) {
		this.message = message;
	}

	public String args(String... parameters) {
		return MessageFormat.format(message, parameters);
	}
	
	@Override
	public String toString() {
		return message;
	}

}
