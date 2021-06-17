package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by sousaJ on 17/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@SpringBootTest
public class BreweryViewControllerIT extends BaseIT{

	private static final String TEST_URL_1 = "/brewery/breweries";
	private static final String TEST_URL_2 = "/brewery/breweries/index";
	private static final String TEST_URL_3 = "/brewery/breweries/index.html";
	private static final String TEST_URL_4 = "/brewery/breweries.html";
	private static final String VIEW = "breweries/index";

	@Test
	void listBreweriesAdmin() throws Exception {
		mockMvc.perform(get(TEST_URL_1, TEST_URL_2, TEST_URL_3, TEST_URL_4).with(httpBasic(ADMIN, ADMIN_PASSWORD)))
			   .andExpect(status().is2xxSuccessful());
	}

	@Test
	void listBreweriesCustomer() throws Exception {
		mockMvc.perform(get(TEST_URL_1, TEST_URL_2, TEST_URL_3, TEST_URL_4).with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
			   .andExpect(view().name(VIEW))
			   .andExpect(model().attributeExists("breweries"))
			   .andExpect(status().is2xxSuccessful());
	}

	@Test
	void listBreweriesUser() throws Exception {
		mockMvc.perform(get(TEST_URL_1, TEST_URL_2, TEST_URL_3, TEST_URL_4).with(httpBasic(USER, USER_PASSWORD)))
			   .andExpect(status().isForbidden());
	}

	@Test
	void listBreweriesNOAUTH() throws Exception {
		mockMvc.perform(get(TEST_URL_1, TEST_URL_2, TEST_URL_3, TEST_URL_4))
			   .andExpect(status().isUnauthorized());
	}
}
