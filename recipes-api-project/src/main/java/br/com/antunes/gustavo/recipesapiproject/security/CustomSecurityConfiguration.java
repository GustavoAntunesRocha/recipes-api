package br.com.antunes.gustavo.recipesapiproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfiguration {
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	
	private final AuthenticationProvider authenticationProvider;

	@Bean
	SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
				
		http
			.cors().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.formLogin().disable()
			.securityMatcher("/**")
			.authorizeHttpRequests(registry -> registry
					.requestMatchers("/user/create").permitAll()
					.requestMatchers("/user/login").permitAll()
					.anyRequest().authenticated()
					
			);
		
		http.headers((headers) -> headers.frameOptions().sameOrigin());
		return http.build();
	}
	
}
