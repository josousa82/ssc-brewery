package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.Util;
import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
public abstract class BaseIT {
	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected Util util;

	protected MockMvc mockMvc;


	protected String BEER_BASE_API_URL = "/api/v1/";

	protected String BEER_BASE_VIEWS_URL = "/beers";

	protected static final String ADMIN = "spring";
	protected static final String ADMIN_PASSWORD = "guru";

	protected static final String CUSTOMER = "scott";
	protected static final String CUSTOMER_PASSWORD = "tiger";

	protected static final String USER = "user";
	protected static final String USER_PASSWORD = "password";



	@BeforeEach
	protected void setUp(){
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.apply(springSecurity())
				.build();

	}

	public static Stream<Arguments> getStreamAllUsers() {
		return Stream.of(
				Arguments.of(ADMIN, ADMIN_PASSWORD),
				Arguments.of(USER, USER_PASSWORD),
				Arguments.of(CUSTOMER, CUSTOMER_PASSWORD)
				);
	}

	public static Stream<Arguments> getStreamAdminCustomer() {
		return Stream.of(
				Arguments.of(ADMIN, ADMIN_PASSWORD),
				Arguments.of(CUSTOMER, CUSTOMER_PASSWORD)
						);
	}


	public static Stream<Arguments> getStreamNotAdmin() {
		return Stream.of(
				Arguments.of(CUSTOMER, CUSTOMER_PASSWORD),
				Arguments.of(USER, USER_PASSWORD)
						);
	}

	public static Stream<Arguments> getStreamResultMatchers() {
		return Stream.of(
				Arguments.of(status().isOk()),
				Arguments.of(status().isForbidden()),
				Arguments.of(status().isForbidden())
						);
	}


	protected void beersTestGeneric (String admin, String admin_password, String url, String viewName, String attribute) throws Exception {
		mockMvc.perform(get(url).with(httpBasic(admin, admin_password)))
			   .andExpect(status().isOk())
			   .andExpect(view().name(viewName))
			   .andExpect(model().attributeExists(attribute));
	}

}
