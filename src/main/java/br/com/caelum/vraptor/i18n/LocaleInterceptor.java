package br.com.caelum.vraptor.i18n;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.environment.Environment;

/**
 * Interceptor to locale configuration 
 * @author Denilson Telaroli
 */

@Intercepts
public class LocaleInterceptor {

	private static final String DEFAULT_DISABLED = "false";
	private static final String DEFAULT_ENABLED = "true";
	private static final String ENABLED = DEFAULT_ENABLED;
	private final HttpServletRequest request;
	private final Environment env;

	/** 
	 * @deprecated CDI eyes only
	 */
	public LocaleInterceptor() {
		this(null, null);
	}

	@Inject
	public LocaleInterceptor(HttpServletRequest request, Environment env) {
		this.request = request;
		this.env = env;
	}
	
	@BeforeCall
	public void setLocale() {
		if(env.get("locale.enableFromBrowser", DEFAULT_DISABLED).equals(ENABLED)) {
			setLocaleFromBrowser();
		}
		
		if(env.get("locale.enableFromParameter", DEFAULT_ENABLED).equals(ENABLED)) {
			setLocaleFromRequestParameter();
		}
	}
	
	private void setLocaleFromBrowser() {
		if(!hasSessionLocale()) {
			configLocale(request, request.getLocale());
		}
	}

	private void setLocaleFromRequestParameter() {
		String locale = request.getParameter("_locale");
		
		if(locale != null) {
			Locale languageTag = localeForLanguageTag(locale);
			configLocale(request, languageTag);
		}
	}

	private Locale localeForLanguageTag(String locale) {
		Locale forLanguageTag = Locale.forLanguageTag(locale.replace("_", "-"));
		if(validateLocale(forLanguageTag)) {
			throw new IllegalArgumentException("Invalid locale " + locale);
		}
		return forLanguageTag;
	}

	private boolean validateLocale(Locale forLanguageTag) {
		return forLanguageTag.getISO3Language() == null || forLanguageTag.getISO3Language().isEmpty();
	}

	private boolean hasSessionLocale() {
		return Config.get(request.getSession(), Config.FMT_LOCALE) != null;
	}
	
	private void configLocale(HttpServletRequest request, Locale locale) {
		Config.set(request.getSession(), Config.FMT_LOCALE, locale);
	}
	
}
