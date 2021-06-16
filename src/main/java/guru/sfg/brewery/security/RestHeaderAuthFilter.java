package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.security
 **/
@Slf4j
public class RestHeaderAuthFilter extends AbstractRestAuthFilter {

	public RestHeaderAuthFilter (RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	public String getUsername (HttpServletRequest httpServletRequest) {
		return httpServletRequest.getHeader("Api-Key");
	}

	public String getPassword (HttpServletRequest httpServletRequest) {
		return httpServletRequest.getHeader("Api-Secret");
	}
}
