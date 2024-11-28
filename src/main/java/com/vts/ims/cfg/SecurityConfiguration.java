package com.vts.ims.cfg;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


	public static final String ADMIN = "admin";
	public static final String USER = "user";
	private final JwtConverter jwtConverter;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) ->
				authz.requestMatchers(HttpMethod.GET, "/api/hello").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/admin/**").hasRole(ADMIN)
						.requestMatchers(HttpMethod.GET, "/api/user/**").hasRole(USER)
						.requestMatchers(HttpMethod.GET, "/api/admin-and-user/**").hasAnyRole(ADMIN,USER)
						.requestMatchers(HttpMethod.GET, "/lab-logo").permitAll()
						.anyRequest().authenticated());
					
		http.cors(cors -> cors.configurationSource(request -> {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("*"));
	        configuration.setAllowedHeaders(Arrays.asList("*"));
	        return configuration;
	    }));
		http.sessionManagement(sess -> sess.sessionCreationPolicy(
				SessionCreationPolicy.STATELESS));
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

		return http.build();
	}


}
