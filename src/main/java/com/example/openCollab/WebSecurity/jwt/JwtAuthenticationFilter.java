package com.example.openCollab.WebSecurity.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.openCollab.WebSecurity.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader=request.getHeader("Authorization");
		final String jwt;
		String username="";
		
		if(authHeader==null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwt=authHeader.split(" ")[1].trim();
		username=jwtService.extractUsername(jwt);
		
		if (username !=null &&  SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails = this.userDetailsService.loadUserByUsername(username);
			
			if(jwtService.validateToken(jwt, userdetails)) {
				
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(username,null,userdetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
				
				// This line creates an Authentication object (authToken) of type UsernamePasswordAuthenticationToken. It sets the principal (in this case, the username extracted from the JWT)
				//and the authorities obtained from userdetails. Here, null is passed as the credentials, as JWT tokens typically do not contain passwords.
				//WebAuthenticationDetailsSource to gather information from the incoming request and builds the authentication details. 
				//This could include, for example, the IP address, session ID, or other request-specific details.
			    // step effectively authenticates the user within the Spring Security context, allowing access to secured resources based on the user's authorities.
			}
			
			
		}
		filterChain.doFilter(request, response);
		
	}

}
