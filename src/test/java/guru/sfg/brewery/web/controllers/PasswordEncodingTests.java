package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@Disabled
class PasswordEncodingTests {
	static final String PASSWORD = "password";


	@Test
	void testBcrypt() {
		PasswordEncoder bcrypt = new BCryptPasswordEncoder();
		System.out.println("Bcrypt 1 same pass different string after encrypt = " + bcrypt.encode(PASSWORD));
		System.out.println("Bcrypt 2 same pass different string after encrypt = " + bcrypt.encode(PASSWORD));
		System.out.println("Bcrypt 3 guru pass different string after encrypt = " + bcrypt.encode("guru"));
		System.out.println("Bcrypt 4 tiger pass different string after encrypt = " + bcrypt.encode("tiger"));
		System.out.println("Bcrypt15 5 tiger pass different string after encrypt = " +
								   new BCryptPasswordEncoder(15).encode("tiger"));
		String encodedPwd = bcrypt.encode(PASSWORD);
		assertTrue(bcrypt.matches(PASSWORD, encodedPwd));
	}

	@Test
	void testSha256test() {
		PasswordEncoder sha256 = new StandardPasswordEncoder();
    	System.out.println("sha256 1 same pass different string after encrypt = " + sha256.encode(PASSWORD));
		System.out.println("sha256 2 same pass different string after encrypt = " + sha256.encode(PASSWORD));
		System.out.println("Bcrypt 3 guru pass different string after encrypt = " + sha256.encode("guru"));
		String encodedPwd = sha256.encode(PASSWORD);
		assertTrue(sha256.matches(PASSWORD, encodedPwd));

	}

	@Test
	void testLdap () {
		PasswordEncoder ldap = new LdapShaPasswordEncoder();
		System.out.println("ldap.encode(PASSWORD) 1 = " + ldap.encode(PASSWORD));
		System.out.println("ldap.encode(PASSWORD) 2 = " + ldap.encode(PASSWORD));
		System.out.println("Bcrypt 3 guru pass different string after encrypt = " + ldap.encode("tiger"));
		String encodedPwd = ldap.encode(PASSWORD);
		assertTrue(ldap.matches(PASSWORD, encodedPwd));
	}

	@Test
	void TestNoOp(){
		PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
		System.out.println("noOp.encode(PASSWORD) = " + noOp.encode(PASSWORD));
	}

	@Test
	void hashingExample(){
		System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
		System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

		String salted = PASSWORD + "ThIsIsmYSAltValue";
		System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
	}


}
