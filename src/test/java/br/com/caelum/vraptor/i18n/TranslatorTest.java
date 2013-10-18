package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.core.Localization;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorTest {
	
	@Mock
	private Localization localization;
	private Translator map;
	
	@Before
	public void setUp() {
		map = new Translator(localization);
	}
	
	@Test
	public void should_return_span_if_key_does_not_exist() {
		when(localization.getMessage("must_be_not_empty")).thenReturn("???must_be_not_empty???");
		assertFalse(map.containsKey("must_be_not_empty"));
		assertEquals("<span class='i18n_missing_key'>must_be_not_empty</span>", map.get("must_be_not_empty").toString());
	}
	
	@Test
	public void should_return_key_if_it_exists() {
		String message = "Must be not empty";

		when(localization.getMessage("must_be_not_empty")).thenReturn(message);
		assertTrue(map.containsKey("must_be_not_empty"));
		assertEquals(message, map.get("must_be_not_empty").toString());
	}
	
	@Test
	public void should_return_interpolated_string() {
		String message = "Field {0} must be not empty for component {1}";

		when(localization.getMessage("must_be_not_empty")).thenReturn(message);
		assertEquals("Field name must be not empty for component User", map.get("must_be_not_empty").args("name", "User").toString());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void containsValueShouldThrowException() {
		map.containsValue(null);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void entrySetShouldThrowException() {
		map.entrySet();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void isEmptyShouldThrowException() {
		map.isEmpty();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void keySetShouldThrowException() {
		map.keySet();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void putShouldThrowException() {
		map.put(null, null);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void putAllShouldThrowException() {
		map.putAll(null);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void removeShouldThrowException() {
		map.remove(null);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void sizeShouldThrowException() {
		map.size();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void valueShouldThrowException() {
		map.values();
	}

}
