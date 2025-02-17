package guru.sfg.brewery.security.services;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.security.services
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	// Use @Transactional annotation is the same to set fetch eager in the user authorities list
//	@Transactional
	@Override
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {

		log.warn("Getting User info via JPA");

		User user = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UsernameNotFoundException("User name: " + username + " not found.");
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), convertToSpringAuthorities(user.getAuthorities()));
	}

	private Collection<? extends GrantedAuthority> convertToSpringAuthorities (Set<Authority> authorities) {
		if(authorities != null && !authorities.isEmpty()){
			return authorities.stream()
					   .map(Authority::getPermission)
					   .map(SimpleGrantedAuthority::new)
					   .collect(Collectors.toSet());
		}else {
			return new HashSet<>();
		}
	}
}
