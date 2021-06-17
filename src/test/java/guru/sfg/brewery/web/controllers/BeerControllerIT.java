package guru.sfg.brewery.web.controllers;


import guru.sfg.brewery.Util;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.RequestPostProcessor;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
// web mvc test annotation will only bring the minimal web layer (controllers and so on) to the context
//@WebMvcTest
@SpringBootTest
class BeerControllerIT extends BaseIT{

	@Autowired
	BeerRepository beerRepository;

	@Autowired
	BeerOrderRepository beerOrderRepository;


	@DisplayName("Find beer by Id Beer MVC Integration Tests")
	@Nested
	class FindBeerByIdTests {

		private static final String VIEW_NAME = "beers/beerDetails";
		private static final String MODEL_ATTRIBUTE = "beer";
		private final String FIND_BEER_BY_ID_URL =  BEER_BASE_VIEWS_URL + "/" + util.saveAndFlushUtilBeer().getId();


		@ParameterizedTest(name = "#index with [{arguments}]" )
		@MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAllUsers")
		void testFindBeerByIdWithHttpBasicAUTH(String user, String password) throws Exception {
			findBeersTestGeneric(user, password,FIND_BEER_BY_ID_URL,VIEW_NAME,MODEL_ATTRIBUTE);
		}

		@Test
		void testFindBeerByIdWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(get(FIND_BEER_BY_ID_URL))
				   .andExpect(status().isUnauthorized());
		}

	}

	@DisplayName("List Beers MVC Integration Tests")
	@Nested
	class FindBeersTests {

    protected final String FIND_BEERS_URL = BEER_BASE_VIEWS_URL + "/find";
    private final String VIEW_NAME = "beers/findBeers";
    private final String MODEL_ATTRIBUTE = "beer";


		@ParameterizedTest(name = "#index with [{arguments}]" )
		@MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAllUsers")
		void TestFindBeersWithHttpBasicAUTH(String user, String password) throws Exception {
			findBeersTestGeneric(user, password,FIND_BEERS_URL,VIEW_NAME,MODEL_ATTRIBUTE);
		}

		@Test
		void findBeersTestWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(get(FIND_BEERS_URL))
				   .andExpect(status().isUnauthorized());
		}

	}

	@DisplayName("Create Beer MVC Integration Tests")
	@Nested
	class IntiCreationForm {

		protected final String CREATE_BEER_URL = BEER_BASE_VIEWS_URL + "/new";
		private final String VIEW_NAME = "beers/createBeer";
		private final String MODEL_ATTRIBUTE = "beer";


		@ParameterizedTest(name = "#index with [{arguments}]" )
		@MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAllUsers")
		void testCreateBeerWithHttpBasicAUTH(String user, String password) throws Exception {
			findBeersTestGeneric(user, password,CREATE_BEER_URL,VIEW_NAME,MODEL_ATTRIBUTE);
		}

		@Test
		void testCreateBeerWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(get(CREATE_BEER_URL))
				   .andExpect(status().isUnauthorized());
		}

	}

}
