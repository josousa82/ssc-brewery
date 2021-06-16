package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.bootstrap
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

	private final AuthorityRepository authorityRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public void run (String... args) throws Exception {
		if(authorityRepository.count() == 0){
			loadSecurityData();
		}
	}


	@Transient
	private void loadSecurityData(){
		Authority admin = Authority.builder().role("ROLE_ADMIN").build();
		Authority userRole = Authority.builder().role("ROLE_USER").build();
		Authority customer = Authority.builder().role("ROLE_CUSTOMER").build();

		userRepository.save(User.builder()
						   .username("spring")
						   .password(passwordEncoder.encode("guru"))
						   .authority(admin)
						   .build());

		userRepository.save(User.builder()
								.username("user")
								.password(passwordEncoder.encode("password"))
								.authority(userRole)
								.build());

		userRepository.save(User.builder()
								.username("scott")
								.password(passwordEncoder.encode("tiger"))
								.authority(customer)
								.build());
		log.info("Users Loaded: " + userRepository.count());

    userRepository
        .findAll()
        .forEach(
            user -> {
        		  System.out.println(user.getUsername());
            });

    authorityRepository
        .findAll()
        .forEach(authority -> System.out.println("authority = " + authority.role()));
	}


}
