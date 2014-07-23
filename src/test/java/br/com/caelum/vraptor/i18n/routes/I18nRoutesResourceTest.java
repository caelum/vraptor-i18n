package br.com.caelum.vraptor.i18n.routes;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

public class I18nRoutesResourceTest {

	private I18nRoutesResource bundles;

	@Before
	public void setUp() throws Exception {
		bundles = new I18nRoutesResource();
	}

	@Test
	public void shouldReturnResourceBundlePtBR() {
		bundles.bindResourcesBundle();
		
		List<ResourceBundle> availableBundles = bundles.getAvailableBundles();
		ResourceBundle resourceBundle = availableBundles.get(0);
		
		assertEquals(availableBundles.size(), 1);
		assertEquals(resourceBundle.getLocale().getLanguage(), "pt");
		assertEquals(resourceBundle.getLocale().getCountry(), "BR");
		assertEquals(resourceBundle.getString("/absolutePath"), "/absoluto");
	}

}
