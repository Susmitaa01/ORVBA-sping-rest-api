package com.capgemini.vehiclebreakdown.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.vehiclebreakdown.jwtfilter.JwtFilter;
import com.capgemini.vehiclebreakdown.serviceimpl.UserServiceImpl;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private JwtFilter filter;
	
	//to configure how Spring Security will authenticate users
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userService);
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	//to authenticate users
	@Bean(name=BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean()throws Exception
	{
			return super.authenticationManagerBean();
	}
	
	//enabling CORS,to receive requests from other ports
	@Override
	protected void configure(HttpSecurity http)throws Exception
	{
	http.csrf().disable().cors().and()
	.authorizeRequests()
	.antMatchers("/users/login","/users/register","/mechanic/login","/mechanic/register")
	.permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	.anyRequest().authenticated().and().exceptionHandling()
	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
	}
}
