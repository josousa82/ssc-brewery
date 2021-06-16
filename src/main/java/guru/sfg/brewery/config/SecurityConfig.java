package guru.sfg.brewery.config;

import guru.sfg.brewery.security.CustomPasswordEncoderFactories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetails;
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

	@Override
	protected void configure (HttpSecurity http) throws Exception {

    http.authorizeRequests(
            authorize -> {
              authorize
					  .antMatchers("/h2-console/**").permitAll() // do not use in production
					  .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
					  .antMatchers("/beers/find").permitAll()
					  .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
					  .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
            })
        .authorizeRequests()
        .anyRequest().authenticated().and()
        .formLogin().and()
        .httpBasic();

    	// H2 console config
		http.headers().frameOptions().sameOrigin();

	}


	@Bean
	PasswordEncoder passwordEncoder(){
		return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
