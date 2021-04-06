package es.urjc.etsii.dad.Components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*
@SuppressWarnings("deprecation")
@Configuration
public class CSRFHandlerConfiguration implements WebMvcConfigurer {
	
	@Override
 	public void addInterceptors(InterceptorRegistry registry) {
	 	registry.addInterceptor(new CSRFHandlerInterceptor());
 	}
}

@SuppressWarnings("deprecation")
class CSRFHandlerInterceptor implements HandlerInterceptor {
	
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			 ModelAndView modelAndView) throws Exception {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		modelAndView.addObject("token", token.getToken());
	}
}
*/
