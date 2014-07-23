package br.com.caelum.vraptor.i18n.routes;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.controller.BeanClass;
import br.com.caelum.vraptor.controller.HttpMethod;
import br.com.caelum.vraptor.http.route.PathAnnotationRoutesParser;
import br.com.caelum.vraptor.http.route.Route;
import br.com.caelum.vraptor.http.route.RouteBuilder;
import br.com.caelum.vraptor.http.route.Router;

@ApplicationScoped @Specializes
public class I18nRoutesParser extends PathAnnotationRoutesParser {

	private final List<Route> routes;
	private final RoutesResources routesResource;
	private final Router router;
	
	/** 
	 * @deprecated CDI eyes only
	 */
	protected I18nRoutesParser() {
		this(null, null);
	}

	@Inject
	public I18nRoutesParser(Router router, RoutesResources routesResource) {
		super(router);
		this.router = router;
		this.routesResource = routesResource;
		routes = new ArrayList<>();
	}
	
	@Override
	public List<Route> rulesFor(BeanClass controller) {
		routes.addAll(super.rulesFor(controller));
		return routes;
	}
	
	@Override
	protected String[] getURIsFor(Method javaMethod, Class<?> type) {
		String[] urIsFor = super.getURIsFor(javaMethod, type);
		
		for (int i = 0; i < urIsFor.length; i++) {
			for (ResourceBundle bundle : routesResource.getAvailableBundles()) {
				if(bundle.containsKey(urIsFor[i])) {
					Route translatedRouteWithLocalePrefix = buildWithLocalePrefix(javaMethod, type, bundle.getString(urIsFor[i]), bundle.getLocale());
					routes.add(translatedRouteWithLocalePrefix);
				}
			}
			
			Route defaultRouteWithLocalePrefix = buildWithLocalePrefix(javaMethod, type, urIsFor[i], Locale.getDefault());
			routes.add(defaultRouteWithLocalePrefix);
		}
		
		return urIsFor;
	}
	
	private Route buildWithLocalePrefix(Method javaMethod, Class<?> type, String uri, Locale locale) {
		String localePrefix = "(?i)/" + locale.getLanguage();
		
		if(!locale.getCountry().isEmpty()) {
			 localePrefix += "[\\-|_]" + locale.getCountry();
		}
		
		RouteBuilder rule = router.builderFor(localePrefix + uri);

		EnumSet<HttpMethod> methods = getHttpMethods(javaMethod);
		EnumSet<HttpMethod> typeMethods = getHttpMethods(type);

		rule.with(methods.isEmpty() ? typeMethods : methods);

		if(javaMethod.isAnnotationPresent(Path.class)){
			rule.withPriority(javaMethod.getAnnotation(Path.class).priority());
		}

		if (getUris(javaMethod).length > 0) {
			rule.withPriority(Path.DEFAULT);
		}

		rule.is(type, javaMethod);
		return rule.build();
	}
	
	private EnumSet<HttpMethod> getHttpMethods(AnnotatedElement annotated) {
		EnumSet<HttpMethod> methods = EnumSet.noneOf(HttpMethod.class);
		for (HttpMethod method : HttpMethod.values()) {
			if (annotated.isAnnotationPresent(method.getAnnotation())) {
				methods.add(method);
			}
		}
		return methods;
	}

}