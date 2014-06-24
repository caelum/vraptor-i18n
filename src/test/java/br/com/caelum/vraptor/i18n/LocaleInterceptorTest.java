package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.jsp.jstl.core.Config;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.http.MutableRequest;

@SuppressWarnings("deprecation") 
public class LocaleInterceptorTest {

	LocaleInterceptor interceptor;
	@Mock MutableRequest request;
	HttpSession session;
	@Mock Environment env;
	
	class MockHttpSession implements HttpSession {
		private HashMap<String, Object> params = new HashMap<>();

		@Override public Enumeration<String> getAttributeNames() { return null; }
		@Override public long getCreationTime() { return 0; }
		@Override public String getId() { return null; }
		@Override public long getLastAccessedTime() { return 0; }
		@Override public int getMaxInactiveInterval() { return 0; }
		@Override public ServletContext getServletContext() { return null; }
		@Override public HttpSessionContext getSessionContext() { return null; }
		@Override public Object getValue(String arg0) { return null; }
		@Override public String[] getValueNames() {	return null; }
		@Override public void invalidate() {}
		@Override public boolean isNew() { return false; }
		@Override public void putValue(String arg0, Object arg1) {}
		@Override public void removeAttribute(String arg0) {}
		@Override public void removeValue(String arg0) {}
		@Override public void setMaxInactiveInterval(int arg0) {}
		
		@Override
		public Object getAttribute(String name) {		
			return params.get(name);
		}
		@Override
		public void setAttribute(String name, Object value) {
			params.put(name, value);
		}		
	}
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);

		session = new MockHttpSession();
		when(request.getSession()).thenReturn(session);
		when(request.getLocale()).thenReturn(new Locale("pt"));
		when(env.get(anyString(), anyString())).thenReturn("true");
		interceptor = new LocaleInterceptor(request, env);
	}

	@Test
	public void shouldSetLocaleFromBrowserIfNull() {
		assertNull(getLocaleFromSession());
		
		interceptor.setLocale();
		Locale locale = getLocaleFromSession();
		assertNotNull(locale);
		assertEquals("pt", locale.getLanguage());
	}
	
	@Test
	public void shouldNotSetLocaleFromBrowserIfNotNull() {
		setLocaleEn();
		
		assertNotNull(getLocaleFromSession());
		
		interceptor.setLocale();
		Locale locale = getLocaleFromSession();
		assertNotNull(locale);
		assertEquals("en", locale.getLanguage());
	}

	private void setLocaleEn() {
		session.setAttribute(Config.FMT_LOCALE + ".session", new Locale("en"));
	}
	
	@Test
	public void shouldNotSetLocaleFromParameterIfNull() {
		assertNull(getLocaleFromSession());
		setLocaleEn();
		
		interceptor.setLocale();
		Locale locale = getLocaleFromSession();
		assertNotNull(locale);
		assertEquals("en", locale.getLanguage());
	}
	
	@Test
	public void shouldSetLocaleFromParameterIfExists() {
		when(request.getParameter("_locale")).thenReturn("pt");
		assertNull(getLocaleFromSession());
		setLocaleEn();
		assertEquals(getLocaleFromSession().getLanguage(), "en");
		
		interceptor.setLocale();
		Locale locale = getLocaleFromSession();
		assertNotNull(locale);
		assertEquals("pt", locale.getLanguage());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSetLocaleIfInvalidParameter() {
		when(request.getParameter("_locale")).thenReturn("12-12");
		assertNull(getLocaleFromSession());
		setLocaleEn();
		assertEquals(getLocaleFromSession().getLanguage(), "en");
		
		interceptor.setLocale();
	}
	
	@Test
	public void shouldReplaceLocaleFromIsoToTag() {
		when(request.getParameter("_locale")).thenReturn("pt_BR");
		assertNull(getLocaleFromSession());
		setLocaleEn();
		assertEquals(getLocaleFromSession().getLanguage(), "en");
		
		interceptor.setLocale();
		Locale locale = getLocaleFromSession();
		assertNotNull(locale);
		assertEquals("pt", locale.getLanguage());
		assertEquals("BR", locale.getCountry());
		
		when(request.getParameter("_locale")).thenReturn("pt-br");
		interceptor.setLocale();
		locale = getLocaleFromSession();
		assertNotNull(locale);
		assertEquals("pt", locale.getLanguage());
		assertEquals("BR", locale.getCountry());
	}

	@Test
	public void shouldDisableFeatureSetFromBrowserByEnv() {
		when(env.get("locale.enableFromBrowser", "false")).thenReturn("false");
		when(env.get("locale.enableFromParameter", "false")).thenReturn("false");
		assertNull(getLocaleFromSession());
		
		interceptor.setLocale();
		assertNull(getLocaleFromSession());
	}
	
	@Test
	public void shouldDisableFeatureSetFromParameterByEnv() {
		when(env.get("locale.enableFromBrowser", "false")).thenReturn("false");
		when(env.get("locale.enableFromParameter", "true")).thenReturn("false");
		when(request.getParameter("_locale")).thenReturn("pt");
		
		assertNull(getLocaleFromSession());
		
		interceptor.setLocale();
		assertNull(getLocaleFromSession());
	}
	
	private Locale getLocaleFromSession() {
		return (Locale) Config.get(session, Config.FMT_LOCALE);
	}

}
