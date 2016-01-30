package br.com.caelum.vraptor.i18n.routes;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import javax.enterprise.inject.Vetoed;
import javax.servlet.ServletContext;

import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.core.ReflectionProvider;
import br.com.caelum.vraptor.http.route.Route;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.view.Linker;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

@Vetoed
public class I18nLinker extends Linker {
	
	private final JstlLocalization localization;
	private final Router router;
	private final Class<?> controller;
	
	public I18nLinker(ServletContext context, Router router, Class<?> controller, String methodName, List<Object> args,
			JstlLocalization localization, ReflectionProvider reflectionProvider) {
		super(context, router, controller, methodName, args, reflectionProvider);
		this.router = router;
		this.controller = controller;
		this.localization = localization;
	}
	
	@Override
	protected String getLink() {
		Method method = getMethod();
		
		ImmutableList<Route> routes = FluentIterable.from(router.allRoutes()).filter(canHandle(controller, method)).toList();
		for (Route route : routes) {
			String localizedPrefix = getLocalizedPrefix();
			if(route.getOriginalUri().startsWith(localizedPrefix)) {
				return getPrefix() + route.urlFor(controller, method, getArgs(method));
			}
		}
		
		return super.getLink();
	}

	private String getLocalizedPrefix() {
		Locale locale = localization.getLocale();
		String prefix = "/" + locale.getLanguage();
		if(!locale.getCountry().isEmpty()) {
			prefix += "-" + locale.getCountry().toLowerCase();
		}
		return prefix;
	}

	private Predicate<Route> canHandle(final Class<?> type, final Method method) {
		return new Predicate<Route>() {
			@Override
			public boolean apply(Route route) {
				return route.canHandle(type, method);
			}
		};
	}
}	
