package br.com.caelum.vraptor.i18n.routes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.enterprise.inject.Vetoed;
import javax.servlet.ServletContext;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.core.JstlLocalization;
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
	private final ServletContext context;
	private Class<?> controller;
	private String methodName;
	private List<Object> args;
	
	public I18nLinker(ServletContext context, Router router,
			Class<?> controller, String methodName, List<Object> args, JstlLocalization localization) {
		super(context, router, controller, methodName, args);
		this.context = context;
		this.router = router;
		this.controller = controller;
		this.methodName = methodName;
		this.args = args;
		this.localization = localization;
	}
	
	@Override
	protected String getLink() {
		Method method = getMethod();
		
		ImmutableList<Route> routes = FluentIterable.from(router.allRoutes()).filter(canHandle(controller, method)).toList();
		for (Route route : routes) {
			Locale locale = localization.getLocale();
			String prefix = "/" + locale.getLanguage();
			if(!locale.getCountry().isEmpty()) {
				prefix += "-" + locale.getCountry().toLowerCase();
			}
			if(route.getOriginalUri().startsWith(prefix)) {
				return context.getContextPath() + route.urlFor(controller, method, getArgs(method));
			}
		}
		
		return super.getLink();
	}

	private Method getMethod() {
		Method method = null;

		if (countMethodsWithSameName() > 1) {
			method = new Mirror().on(controller).reflect().method(methodName).withArgs(getClasses(args));
			if (method == null && args.isEmpty()) {
				throw new IllegalArgumentException("Ambiguous method '" + methodName + "' on " + controller + ". Try to add some parameters to resolve ambiguity, or use different method names.");
			}
		} else {
			method = findMethodWithName(controller, methodName);
		}

		if(method == null) {
			throw new IllegalArgumentException(
				String.format("There are no methods on %s named '%s' that receives args of types %s",
						controller, methodName, Arrays.toString(getClasses(args))));
		}
		return method;
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
