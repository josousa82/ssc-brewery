package guru.sfg.brewery.web.controllers;


import guru.sfg.brewery.domain.Customer;
import guru.sfg.brewery.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/**
 * Created by sousaJ on 17/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@SpringBootTest
class CustomerControllerIT extends BaseIT {

	@DisplayName("List Customers views integration tests")
	@Nested
	class TestListCustomers{
		@ParameterizedTest(name = "#index with [{arguments}]" )
		@MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAdminCustomer")
		void testListCustomersAuth(String user, String password) throws Exception {
			mockMvc.perform(get("/customers")
									.with(httpBasic(user, password)))
				   .andExpect(status().isOk());
		}

		@Test
		void testListCustomersNoAuthUSER() throws Exception {
			mockMvc.perform(get("/customers")
									.with(httpBasic(USER, USER_PASSWORD)))
				   .andExpect(status().isForbidden());
		}

		@Test
		void testListCustomersNotLoggedIn() throws Exception {
			mockMvc.perform(get("/customers"))
				   .andExpect(status().isUnauthorized());
		}
	}

	@DisplayName("Initiate new Customer form view integration tests")
	@Nested
	class TestCreateNewCustomer {

		private final String NEW_CUSTOMER_URL ="/customers/new";

		@Test
		void initNewCustomerNoAuthUSER() throws Exception {
			mockMvc.perform(post(NEW_CUSTOMER_URL)
					.param("customerName", "Foo Customer")
									.with(httpBasic(USER, USER_PASSWORD)))
				   .andExpect(status().isForbidden());
		}


		@Test
		void initNewCustomerWithAdmin() throws Exception {
			mockMvc.perform(get(NEW_CUSTOMER_URL)
									.with(httpBasic(ADMIN, ADMIN_PASSWORD)))
				   .andExpect(view().name("customers/createCustomer"))
				   .andExpect(status().isOk());
		}

		@Test
		void initPostNewCustomerWithAdmin() throws Exception {
			mockMvc.perform(post(NEW_CUSTOMER_URL)
									.param("customerName", "Foo Customer2")
									.with(httpBasic(ADMIN, ADMIN_PASSWORD)))
				   .andExpect(status().is3xxRedirection());
		}

		@Test
		void testListCustomersNotLoggedIn() throws Exception {
			mockMvc.perform(post(NEW_CUSTOMER_URL)
						.param("customerName", "Foo Customer2"))
				   .andExpect(status().isUnauthorized());
		}
	}

	
}
