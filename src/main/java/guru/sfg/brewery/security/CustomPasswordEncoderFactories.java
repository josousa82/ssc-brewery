package guru.sfg.brewery.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.security
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomPasswordEncoderFactories {

	// Creating our own password encoder factory with the password encoders the application needs
	public static PasswordEncoder createDelegatingPasswordEncoder (){
		String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("bcrypt15", new BCryptPasswordEncoder(10));
		encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
		encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
		encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
		return new DelegatingPasswordEncoder(encodingId, encoders);
	}
}
