package br.com.caelum.vraptor.i18n.routes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class I18nRoutesResource implements RoutesResources {
	
	private static final String ROUTES_BASE_NAME = "routes";
	private final List<ResourceBundle> bundles;
	private Logger LOGGER = LoggerFactory.getLogger(I18nRoutesResource.class);

	public I18nRoutesResource() {
		bundles = new ArrayList<>();
	}
	
	@PostConstruct
	public void bindResourcesBundle() {
		LOGGER.debug("Default locale for routes is {}", Locale.getDefault());
		
		Locale[] availableLocalesFromSystem = Locale.getAvailableLocales();
		List<Locale> notFound = new ArrayList<>();
		for (Locale locale : availableLocalesFromSystem) {
			try {
				bundles.add(ResourceBundle.getBundle(ROUTES_BASE_NAME, locale));
				LOGGER.info("Found resource bundle routes_{}.properties", locale);
			} catch (MissingResourceException e) {
				notFound.add(locale);
			}
		}
		
		LOGGER.debug("Can't found locale routes_{}.properties", notFound);
	}

	@Override
	public List<ResourceBundle> getAvailableBundles() {
		return bundles;
	}

}