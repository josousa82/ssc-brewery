package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.security
 **/
@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

	public RestHeaderAuthFilter (RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication (HttpServletRequest httpServletRequest,
												 HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

		String userName = getUsername(httpServletRequest);
		String password = getPassword(httpServletRequest);

		if (userName == null ) userName = "" ;

		if (password == null ) password = "" ;

		log.info("Authentication User: {}", userName);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
		return this.getAuthenticationManager().authenticate(token);
	}

	private String getPassword (HttpServletRequest httpServletRequest) {
		return httpServletRequest.getHeader("Api-Key");
	}

	private String getUsername (HttpServletRequest httpServletRequest) {
		return httpServletRequest.getHeader("Api-secret");
	}
}
