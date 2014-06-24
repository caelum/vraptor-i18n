package br.com.caelum.vraptor.i18n;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;

@Intercepts
public class LocaleInterceptor {

	private final HttpServletRequest request;

	/** 
	 * @deprecated CDI eyes only
	 */
	public LocaleInterceptor() {
		this(null);
	}

	@Inject
	public LocaleInterceptor(HttpServletRequest request) {
		this.request = request;
	}
	
	@BeforeCall
	public void setLocale() {
		setLocaleFromBrowser();
		setLocaleFromRequestParameter();
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
