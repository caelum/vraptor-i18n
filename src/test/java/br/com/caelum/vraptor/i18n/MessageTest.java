package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {

	private MockResourceBundle bundle;

	@Before
	public void setUp() {
		this.bundle =  new MockResourceBundle();
	}

	@Test
	public void should_concatenate_list_count_to_message() {
		bundle.addWord("messages_found.zero", "No messages found");
		bundle.addWord("messages_found.one", "One message found");
		bundle.addWord("messages_found.other", "{0} messages found");

		String zero = new Message(bundle ,"messages_found").count(0).toString();
		assertEquals("No messages found", zero);

		String one = new Message(bundle ,"messages_found").count(1).toString();
		assertEquals("One message found", one);

		String five = new Message(bundle ,"messages_found").count(5).toString();
		assertEquals("5 messages found", five);
	}

	@Test
	public void should_accept_params_after_count() {
		bundle.addWord("messages_found.other", "{0} messages found for user {1}");
		String five = new Message(bundle, "messages_found").count(5).args("Guilherme").toString();
		assertEquals("5 messages found for user Guilherme", five);
	}

}
