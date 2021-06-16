package guru.sfg.brewery.security;

import org.springframework.security.web.util.matcher.RequestMatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.security
 **/

public class RestUrlAuthFilter extends AbstractRestAuthFilter{

	public RestUrlAuthFilter (RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public String getUsername (HttpServletRequest httpServletRequest) {
		return httpServletRequest.getParameter("apiKey");
	}

	@Override
	public String getPassword (HttpServletRequest httpServletRequest) {
		return httpServletRequest.getParameter("apiSecret");
	}
}
