package com.todolist.security.Service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolist.security.Enum.Role;
import com.todolist.security.Repository.UserRepository;
import com.todolist.security.Security.JwtUtils;
import com.todolist.security.dto.LoginRequest;
import com.todolist.security.dto.RegisterRequest;
import com.todolist.security.entity.User;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired private UserRepository repo;
 	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 		User user = repo.findByEmail(username)
 	            .orElseThrow(() ->
 	                    new UsernameNotFoundException("User not found: " + username));

 	    return User.builder()
 	            .id(user.getId())
 	            .name(user.getName())
 	            .email(user.getEmail())
 	            .password(user.getPassword())
 	            .authorities(user.getAuthorities().stream().map(role -> Role.valueOf(role.getAuthority())).collect(Collectors.toSet()))   // ✅ PASS Set<Role> DIRECTLY
 	            .enabled(user.isEnabled())
 	            .accountNonExpired(user.isAccountNonExpired())
 	            .accountNonLocked(user.isAccountNonLocked())
 	            .credentialsNonExpired(user.isCredentialsNonExpired())
 	            .build();
 	}
 	public String login(LoginRequest login) {
 		//TODO: process POST request
 				Authentication authentication = null;
 				try {
 					authentication = authenticationManager.authenticate(
 							new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
 				} 
 				catch(AuthenticationException e) {
 					return "Unauthorized";
 				}
 				SecurityContextHolder.getContext().setAuthentication(authentication);
 				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 				String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
 				return jwtToken;
 	}
 	public String register(RegisterRequest req) {
 		if (repo.existsByEmail(req.getEmail())) {
			return "Email already exists";
		}

		Role role = Role.ROLE_USER;
		if (req.getRole() != null) {
			try {
				role = Role.valueOf(req.getRole());
			} catch (IllegalArgumentException ex) {
				return "Invalid role";
			}
		}

		User user = User.builder()
				.name(req.getName())
				.username(req.getEmail())
				.email(req.getEmail())
				.password(passwordEncoder.encode(req.getPassword()))
				.authorities(Set.of(role))
				.enabled(true)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.build();

		User saved = repo.save(user);
		String token = jwtUtils.generateTokenFromUsername(saved);
		return token;
 	}
}
