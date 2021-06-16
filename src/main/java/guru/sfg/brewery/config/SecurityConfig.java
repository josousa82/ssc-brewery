package guru.sfg.brewery.config;

import guru.sfg.brewery.security.CustomPasswordEncoderFactories;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestUrlAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.config
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){
		RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	public RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager) {
		RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/api/**"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
							 UsernamePasswordAuthenticationFilter.class)
		.csrf().disable();

		http.addFilterBefore(restUrlAuthFilter(authenticationManager()),
							 UsernamePasswordAuthenticationFilter.class);

    http.authorizeRequests(
            authorize -> {
              authorize
                  .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                  .antMatchers("/beers/find").permitAll()
                  .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                  .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
            })
        .authorizeRequests()
        .anyRequest().authenticated().and()
        .formLogin().and()
        .httpBasic();
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	//Fluent API for the method below
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("spring")
			.password("{bcrypt}$2a$10$griEHiks8s6qfvqxP/QtvOTldfD79XghlieY/ImXsHNsMEyCLggx2")
			.roles("ADMIN")
			.and()
			.withUser("user")
			.password("{sha256}6b93431b031fb0ee57e45371077fd3dc7aefa4b4f2999a2e164d94bbcb1300c6342bc993dbc3928d")
			.roles("USER")
			.and()
			.withUser("scott")
			.password("{bcrypt15}$2a$15$hYRPFG9dnCCPbUu61m9eVOYkRpIhrMI6Xc9.5r.MhPe6mYSvDK.ES")
			.roles("CUSTOMER");
	}




//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService () {
//		UserDetails admin = User.withDefaultPasswordEncoder()
//								.username("spring")
//								.password("guru")
//								.roles("ADMIN")
//								.build();
//
//		UserDetails user = User.withDefaultPasswordEncoder()
//								.username("user")
//								.password("password")
//								.roles("USER")
//								.build();
//
//		return new InMemoryUserDetailsManager(admin, user);
//	}

}
