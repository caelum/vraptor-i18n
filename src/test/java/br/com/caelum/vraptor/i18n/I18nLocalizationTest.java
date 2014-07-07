package br.com.caelum.vraptor.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class I18nLocalizationTest {

	@Mock MutableRequest request;
	@Mock Environment env;
	@Mock ServletContext context;
	HttpSession session;
	I18nLocalization i18n;
	private Object defaultLocale;
	
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
		defaultLocale = Locale.getDefault();
		when(request.getSession()).thenReturn(session);
		when(request.getServletContext()).thenReturn(context);
		when(env.get(anyString(), anyString())).thenReturn("true");
		i18n = new I18nLocalization(request, env);
	}

	private void assertDefaultLocale() {
		assertEquals(defaultLocale, i18n.getLocale());
	}
	
	private void setLocaleEn() {
		session.setAttribute(Config.FMT_LOCALE + ".session", new Locale("en"));
	}
	
	@Test
	public void shouldNotSetLocaleFromParameterIfNull() {
		when(request.getParameter("_locale")).thenReturn(null);
		assertDefaultLocale();
		setLocaleEn();
		
		i18n.getBundle();
		Locale locale = getLocale();
		assertNotNull(locale);
		assertEquals("en", locale.getLanguage());
	}
	
	@Test
	public void shouldSetLocaleFromParameterIfExists() {
		when(request.getParameter("_locale")).thenReturn("pt_BR");
		assertDefaultLocale();
		setLocaleEn();
		assertEquals(getLocale().getLanguage(), "en");
		
		i18n.getBundle();
		Locale locale = getLocale();
		assertNotNull(locale);
		assertEquals("pt", locale.getLanguage());
	}
	
	@Test
	public void shouldDisableFeatureSetFromParameterByEnv() {
		when(env.get("locale.enableFromParameter", "true")).thenReturn("false");
		when(request.getParameter("_locale")).thenReturn("pt");
		
		assertDefaultLocale();
		
		i18n.getBundle();
		assertDefaultLocale();
	}
	
	private Locale getLocale() {
		return i18n.getLocale();
	}

}
