package br.com.caelum.vraptor.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.environment.Environment;

/**
 * ResourceBundle producer to locale configuration 
 * @author Denilson Telaroli
 */
@Specializes
public class I18nLocalization extends JstlLocalization {

	private static final String DEFAULT_ENABLED = "true";
	private static final String ENABLED = DEFAULT_ENABLED;
	private final HttpServletRequest request;
	private final Environment env;

	/** 
	 * @deprecated CDI eyes only
	 */
	protected I18nLocalization() {
		this(null, null);
	}

	@Inject
	public I18nLocalization(HttpServletRequest request, Environment env) {
		super(request);
		this.request = request;
		this.env = env;
	}
	
	@Override
	@Produces
	public ResourceBundle getBundle() {
		if(env.get("locale.enableFromParameter", DEFAULT_ENABLED).equals(ENABLED)) {
			setLocaleFromRequestParameter();
		}
		return super.getBundle();
	}
	
	@Override
	@Produces
	public Locale getLocale() {
		return super.getLocale();
	}
	
	private void setLocaleFromRequestParameter() {
		String locale = request.getParameter("_locale");
		
		if(locale != null) {
			Config.set(request.getSession(), Config.FMT_LOCALE, locale);
		}
	}

}