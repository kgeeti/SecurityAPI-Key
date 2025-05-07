/**
 * https://www.baeldung.com/spring-boot-api-key-secret
 * 
 * 00 Aonde tudo começa...
 * 
 * 
 */


package br.eti.kge.SecurityAPIKEY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

// Esta configuração descarta a auto-configuração de segurança do Spring
// Fazendo isso, descartamos as classes SecusityAutoConfiguration e UserDetailServiceAutoConfiguration

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
//@SpringBootApplication
public class SecurityApikeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApikeyApplication.class, args);
	}

}
