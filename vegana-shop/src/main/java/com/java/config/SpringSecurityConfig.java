package com.java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.java.service.VeganaService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private VeganaService veganaService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Configure a service to search for User in the Database.
		// And set up a PasswordEncoder.
		auth.userDetailsService(veganaService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		// Pages that do not require login.
		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();

		// The /userInfo page requires logging in with the roles ROLE_USER or ROLE_ADMIN.
		// If not logged in, it will redirect to the /login page.
		http.authorizeRequests().antMatchers("/checkout").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		// The page is for ADMIN only
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");

		// When the user is logged in, with the role XX
		// When the user is logged in, with the role XX
		// An AccessDeniedException will be thrown.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Configure for the Login Form.
		http.authorizeRequests().and().formLogin()//
			// The submit URL of the login page
			.loginProcessingUrl("/doLogin") // Submit URL
			.loginPage("/login")//
			.defaultSuccessUrl("/?login_success")//
			.successHandler(new SuccessHandler()).failureUrl("/login?error=true")//
			.usernameParameter("customerId")//
			
			.passwordParameter("password")
			// Configuration for the Logout Page.
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
		
		http.rememberMe()
		.rememberMeParameter("remember"); // [remember-me]
		
		//oauth2- Login from social media
//		http.oauth2Login()
//			.loginPage("/login")
//			.defaultSuccessUrl("/", true)
//			.failureUrl("/login")
//			.authorizationEndpoint()
//				.baseUri("/oauth2/authorization");
	}

}
