package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;



/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/

@WebMvcTest
class BeerControllerIT {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@MockBean
	BeerRepository beerRepository;

	@MockBean
	BeerInventoryRepository beerInventoryRepository;

	@MockBean
	BreweryService breweryService;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	BeerService beerService;

	@BeforeEach
	void setUp(){
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.apply(springSecurity())
				.build();
	}

	// @WithMockUser("spring") tells spring the user is authenticated, Mocks an authenticated user
	// testing security logic
	@WithMockUser("spring")
	@Test
	void findBeers() throws Exception {
		mockMvc.perform(get("/beers/find"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/findBeers"))
			   .andExpect(model().attributeExists("beer"));
	}

	// authentication with specific user, if user doesn't exists, or is not permitted in the endpoint it fails
	// testing security logic and authentication logic
	@Test
	void findBeersWithHttpBasic() throws Exception {
		mockMvc.perform(get("/beers/find").with(httpBasic("spring", "guru")))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/findBeers"))
			   .andExpect(model().attributeExists("beer"));
	}




}
