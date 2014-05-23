package br.com.caelum.vraptor.i18n;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.events.RequestStarted;

@ApplicationScoped
public class I18nObserver {

	public void handle(@Observes RequestStarted event) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) event.getRequest();
		HttpServletResponse response = (HttpServletResponse) event.getResponse();
		FilterChain chain = event.getChain();

		if(request.getAttribute("t")!=null) {
			chain.doFilter(request, response);
			return;
		}
		ResourceBundle bundle= new JstlLocalization(request).getBundle();
		request.setAttribute("t", new T(bundle));
		request.setAttribute("l", new L(bundle));
	}

}
