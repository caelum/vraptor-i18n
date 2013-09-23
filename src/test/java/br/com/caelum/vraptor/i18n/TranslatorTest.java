package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.core.Localization;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorTest {
	
	@Mock
	private Localization localization;
	
	@Test
	public void should_return_span_if_key_does_not_exist() {
		Translator map = new Translator(localization);
		when(localization.getMessage("must_be_not_empty")).thenReturn("???must_be_not_empty???");
		assertFalse(map.containsKey("must_be_not_empty"));
		assertEquals("<span class='i18n_missing_key'>must_be_not_empty</span>", map.get("must_be_not_empty").toString());
	}
	
	@Test
	public void should_return_key_if_it_exists() {
		String message = "Must be not empty";

		Translator map = new Translator(localization);
		when(localization.getMessage("must_be_not_empty")).thenReturn(message);
		assertTrue(map.containsKey("must_be_not_empty"));
		assertEquals(message, map.get("must_be_not_empty").toString());
	}
	
	@Test
	public void should_return_interpolated_string() {
		String message = "Field {0} must be not empty for component {1}";

		Translator map = new Translator(localization);
		when(localization.getMessage("must_be_not_empty")).thenReturn(message);
		assertEquals("Field name must be not empty for component User", map.get("must_be_not_empty").args("name", "User").toString());
	}

}
