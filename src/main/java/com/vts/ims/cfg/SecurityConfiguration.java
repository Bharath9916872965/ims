package com.vts.ims.cfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private  AuthenticationProvider authenticationProvider;
	
	

		

	

	
	
	  @SuppressWarnings("removal")
	@Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf()
	                .disable()
	                .authorizeHttpRequests()
	                .requestMatchers("/authenticate/**")
	                .permitAll()
	                .requestMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**","/swagger-ui/**").permitAll()
	                .anyRequest()
	                .authenticated()
	                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authenticationProvider(authenticationProvider)
	                .addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
	
	
	
	
//	public void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.authorizeRequests().antMatchers("/helloadmin").hasRole("USER")
//		.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**","/swagger-ui/**").permitAll()
//		.antMatchers("/hellouser").hasAnyRole("USER","ADMIN")
//		.antMatchers("/authenticate", "/register").permitAll().anyRequest().authenticated()
//		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
//		and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//		and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//	}



}
