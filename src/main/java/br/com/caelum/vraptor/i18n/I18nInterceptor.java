package br.com.caelum.vraptor.i18n;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.ForwardToDefaultViewInterceptor;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Component
@Intercepts(before=ForwardToDefaultViewInterceptor.class)
public class I18nInterceptor implements Interceptor {

	private final Result result;
	private final Translator translator;
	private final LocalizedFormatter localizedFormatter;

	public I18nInterceptor(Result result, Translator translator, LocalizedFormatter localizedFormatter) {
		this.result = result;
		this.translator = translator;
		this.localizedFormatter = localizedFormatter;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object instance) throws InterceptionException {
		result.include("t", translator);
		result.include("l", localizedFormatter);
		stack.next(method, instance);
	}

}
