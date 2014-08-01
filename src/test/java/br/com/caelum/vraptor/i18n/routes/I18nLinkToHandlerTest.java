package br.com.caelum.vraptor.i18n.routes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.ServletContext;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.controller.DefaultBeanClass;
import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.http.route.Route;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.proxy.JavassistProxifier;
import br.com.caelum.vraptor.view.LinkToHandler;

public class I18nLinkToHandlerTest {

	private @Mock ServletContext context;
	private @Mock Router router;
	private LinkToHandler handler;
	private Method method;
	private @Mock JstlLocalization localization;
	private @Mock Route en;
	private @Mock Route pt;
	private @Mock Route route;
	private String a;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		method = new Mirror().on(TestController.class).reflect().method("method").withArgs(String.class);
		
		a = "test";
		when(route.getOriginalUri()).thenReturn("/expectedURL");
		when(route.canHandle(TestController.class, method)).thenReturn(true);
		when(route.urlFor(TestController.class, method, a)).thenReturn("/expectedURL");
		
		when(en.getOriginalUri()).thenReturn("/en-us/expectedURL");
		when(en.canHandle(TestController.class, method)).thenReturn(true);
		when(en.urlFor(TestController.class, method, a)).thenReturn("/en-us/expectedURL");
		
		when(pt.getOriginalUri()).thenReturn("/pt-br/urlExperada");
		when(pt.canHandle(TestController.class, method)).thenReturn(true);
		when(pt.urlFor(TestController.class, method, a)).thenReturn("/pt-br/urlExperada");
		
		when(router.urlFor(TestController.class, method, a)).thenReturn("/expectedURL");
		when(context.getContextPath()).thenReturn("/path");
		
		handler = new I18nLinkToHandler(context, router, new JavassistProxifier(), localization);
	}

	@Test
	public void shouldReturnWantedUrlDefault() throws Throwable {
		when(localization.getLocale()).thenReturn(new Locale("en", "US"));
		
		when(router.allRoutes()).thenReturn(Arrays.asList(pt, route));
		
		//${linkTo[TestController].method('test')}
		String uri = invoke(handler.get(new DefaultBeanClass(TestController.class)), "method", a);
		assertEquals("/path/expectedURL", uri);
	}
	
	@Test
	public void shouldReturnWantedUrlEnUS() throws Throwable {
		when(localization.getLocale()).thenReturn(new Locale("en", "US"));
		
		when(router.allRoutes()).thenReturn(Arrays.asList(pt, en, route));
		
		//${linkTo[TestController].method('test')}
		String uri = invoke(handler.get(new DefaultBeanClass(TestController.class)), "method", a);
		assertEquals("/path/en-us/expectedURL", uri);
	}
	
	@Test
	public void shouldReturnWantedUrlPtBR() throws Throwable {
		when(localization.getLocale()).thenReturn(new Locale("pt", "BR"));

		when(router.allRoutes()).thenReturn(Arrays.asList(pt, en, route));
		
		//${linkTo[TestController].method('test')}
		String uri = invoke(handler.get(new DefaultBeanClass(TestController.class)), "method", a);
		assertEquals("/path/pt-br/urlExperada", uri);
	}
	
	private String invoke(Object obj, String methodName, Object...args) throws Throwable {
		Class<?>[] types = extractTypes(args);
		
		try {
			Method method = null;
			for (int length = types.length; length >= 0; length--) {
				method = new Mirror().on(obj.getClass()).reflect().method(methodName)
					.withArgs(Arrays.copyOf(types, length));
				if (method != null) 
					break;
			}
			
			if (methodName.startsWith("get")) {
				return method.invoke(obj).toString();
			}
			return new Mirror().on(obj).invoke().method(method).withArgs(args).toString();
		} catch (MirrorException | InvocationTargetException e) {
			throw e.getCause() == null? e : e.getCause();
		}
	}

	private Class<?>[] extractTypes(Object... args) {
		Class<?>[] classes = new Class<?>[args.length];
		
		for (int i = 0; i < classes.length; i++) {
			classes[i] = args[i].getClass();
		}
		return classes;
	}
	
	static class TestController {
		void method(String a) {
		}
	}

}
