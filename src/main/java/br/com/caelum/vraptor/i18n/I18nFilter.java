package br.com.caelum.vraptor.i18n;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.http.VRaptorRequest;
import br.com.caelum.vraptor.http.VRaptorResponse;

@WebFilter(filterName="vraptor-i18n")
public class I18nFilter implements Filter {
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		RequestInfo info = new RequestInfo(
				req.getServletContext(), chain, new VRaptorRequest(request),
				new VRaptorResponse(response));
		Localization localization = new JstlLocalization(info);
		request.setAttribute("t", new Translator(localization));
		request.setAttribute("l", new LocalizedFormatter(localization));
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
