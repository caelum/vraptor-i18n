package br.com.caelum.vraptor.i18n.routes;

import java.lang.reflect.Method;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.http.route.PathAnnotationRoutesParser;
import br.com.caelum.vraptor.http.route.Router;

@ApplicationScoped @Specializes
public class I18nRoutesParser extends PathAnnotationRoutesParser {

	protected static final String _LOCALE = "/?{_locale}";
	/** 
	 * @deprecated CDI eyes only
	 */

	protected I18nRoutesParser() {
		this(null);
	}

	@Inject
	public I18nRoutesParser(Router router) {
		super(router);
	}
	
	@Override
	protected String[] getURIsFor(Method javaMethod, Class<?> type) {
		String[] urIsFor = super.getURIsFor(javaMethod, type);
		
		for (int i = 0; i < urIsFor.length; i++) {
			urIsFor[i] = _LOCALE + urIsFor[i];
		}
		
		return urIsFor;
	}

}