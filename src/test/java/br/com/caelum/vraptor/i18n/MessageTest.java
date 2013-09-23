package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.core.Localization;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {
	
	@Mock
	private Localization localization;
	
	@Test
	public void should_concatenate_list_count_to_message() {
		when(localization.getMessage("messages_found.zero")).thenReturn("No messages found");
		String zero = new Message(localization ,"messages_found").count(0).toString();
		assertEquals("No messages found", zero);

		when(localization.getMessage("messages_found.one")).thenReturn("One message found");
		String one = new Message(localization ,"messages_found").count(1).toString();
		assertEquals("One message found", one);

		when(localization.getMessage("messages_found.other")).thenReturn("{0} messages found");
		String five = new Message(localization ,"messages_found").count(5).toString();
		assertEquals("5 messages found", five);
	}

	@Test
	public void should_accept_params_after_count() {
		when(localization.getMessage("messages_found.other")).thenReturn("{0} messages found for user {1}");
		String five = new Message(localization ,"messages_found").count(5).args("Guilherme").toString();
		assertEquals("5 messages found for user Guilherme", five);
	}

}
