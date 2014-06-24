package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TranslatorTest {

	private MockResourceBundle bundle;
	private Translator map;

	@Before
	public void setUp() {
		bundle = new MockResourceBundle();
		map = new Translator(bundle);
	}

	@Test
	public void should_return_span_if_key_does_not_exist() {
		assertFalse(map.containsKey("must_be_not_empty"));
		assertEquals("<span class='i18n_missing_key'>must_be_not_empty</span>", map.get("must_be_not_empty").toString());
	}

	@Test
	public void should_return_key_if_it_exists() {
		String message = "Must be not empty";
		bundle.addWord("must_be_not_empty", message);
		assertEquals(message, map.get("must_be_not_empty").toString());
	}

	@Test
	public void should_return_interpolated_string() {
		String message = "Field {0} must be not empty for component {1}";
		bundle.addWord("must_be_not_empty", message);
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