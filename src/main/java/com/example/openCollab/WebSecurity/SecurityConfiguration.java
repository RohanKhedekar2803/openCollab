package com.example.openCollab.WebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.openCollab.WebSecurity.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	 @Autowired
	 private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	 @Bean
	 public BCryptPasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 public UserDetailsService customUserDetails() {
		 return new CustomUserDetailsService();
	 }
	
	 //configures all security related stuff
    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.
//        csrf()
//        .disable()
//        .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/home/admin").hasRole("ADMIN") //only users with role admin can access
//                .requestMatchers("/home/user").hasRole("USER")
//                .anyRequest().authenticated()
//            ).           
//            httpBasic(withDefaults());
        
        		http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests()
                .requestMatchers("home/**").permitAll()
                .requestMatchers("lock/**").hasAuthority("ADMIN") //only users with role admin can access
                .anyRequest().authenticated()
                .and()
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //as we are using jwt so make session policy statless
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
       
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(customUserDetails());
    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    	return daoAuthenticationProvider;
    }
    
    
   
    
  
    
    //just override customeUserDetailsService for that create 
    
}
