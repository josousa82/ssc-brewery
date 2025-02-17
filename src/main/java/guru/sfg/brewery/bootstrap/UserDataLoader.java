package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.*;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.bootstrap
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

	private final AuthorityRepository authorityRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public void run (String... args) throws Exception {
		if(authorityRepository.count() == 0){
			loadSecurityData();
		}
	}

  @Transient
  private void loadSecurityData() {
    // Beers Auths
    Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
    Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
    Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
    Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

    // Customer Auths
    Authority createCustomer = authorityRepository.save(Authority.builder().permission("customer.create").build());
    Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
    Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
    Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());

    // Brewery Auths
    Authority createBrewery = authorityRepository.save(Authority.builder().permission("brewery.create").build());
    Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
    Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
    Authority deleteBrewery = authorityRepository.save(Authority.builder().permission("brewery.delete").build());

    Role adminRole = roleRepository.save(Role.builder().roleName("ADMIN").build());
    Role customerRole = roleRepository.save(Role.builder().roleName("CUSTOMER").build());
    Role userRole = roleRepository.save(Role.builder().roleName("USER").build());

    adminRole.setAuthorities(
			new HashSet<>(Set.of(createBeer, updateBeer, readBeer, deleteBeer,
            createCustomer, readCustomer, updateCustomer, deleteCustomer,
            createBrewery, readBrewery, updateBrewery, deleteBrewery)));

    customerRole.setAuthorities(new HashSet<>(Set.of(readBeer, readCustomer, readBrewery)));

    userRole.setAuthorities(new HashSet<>(Set.of(readBeer)));

    roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

   userRepository.save(
        User.builder()
            .username("spring")
			.role(adminRole)
            .password(passwordEncoder.encode("guru"))
            .build());


    userRepository.save(
        User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .role(userRole)
            .build());

    userRepository.save(
        User.builder()
            .username("scott")
            .password(passwordEncoder.encode("tiger"))
            .role(customerRole)
            .build());

    log.info("Users Loaded: " + userRepository.count());
}

}
