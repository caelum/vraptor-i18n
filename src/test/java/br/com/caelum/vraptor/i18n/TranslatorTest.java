package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorTest {

	private MockResourceBundle bundle;

	@Before
	public void setUp() {
		bundle = new MockResourceBundle();
	}

	@Test
	public void should_return_span_if_key_does_not_exist() {
		T map = new T(bundle);

		assertFalse(map.containsKey("must_be_not_empty"));
		assertEquals("<span class='i18n_missing_key'>must_be_not_empty</span>", map.get("must_be_not_empty").toString());
	}

	@Test
	public void should_return_key_if_it_exists() {
		String message = "Must be not empty";
		T map = new T(bundle);
		bundle.addWord("must_be_not_empty", message);

		assertTrue(map.containsKey("must_be_not_empty"));
		assertEquals(message, map.get("must_be_not_empty").toString());
	}

	@Test
	public void should_return_interpolated_string() {
		String message = "Field {0} must be not empty for component {1}";

		T map = new T(bundle);
		bundle.addWord("must_be_not_empty", message);

		assertEquals("Field name must be not empty for component User", map.get("must_be_not_empty").args("name", "User").toString());
	}

}
