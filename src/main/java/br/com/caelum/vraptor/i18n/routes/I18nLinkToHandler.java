package br.com.caelum.vraptor.i18n.routes;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.core.ReflectionProvider;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.view.LinkToHandler;
import br.com.caelum.vraptor.view.Linker;

@RequestScoped @Specializes
public class I18nLinkToHandler extends LinkToHandler {

	private JstlLocalization localization;
	private ServletContext context;
	private Router router;
	private ReflectionProvider reflectionProvider;

	/**
	 * @deprecated CDI eyes only
	 */
	protected I18nLinkToHandler() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public I18nLinkToHandler(ServletContext context, Router router, Proxifier proxifier, JstlLocalization localization,
			ReflectionProvider reflectionProvider) {
		super(context, router, proxifier, reflectionProvider);
		this.context = context;
		this.router = router;
		this.localization = localization;
		this.reflectionProvider = reflectionProvider;
	}
	
	@Override
	protected Linker linker(Class<?> controller, String methodName,
			List<Object> params) {
		return new I18nLinker(context, router, controller, methodName, params, localization, reflectionProvider);
	}
	
}
