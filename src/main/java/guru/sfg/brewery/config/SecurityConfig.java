package guru.sfg.brewery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;

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
                  .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                  .antMatchers("/beers/find").permitAll()
                  .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                  .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
              ;
            })
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
	}
}
