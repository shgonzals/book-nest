package com.shgonzals.booknest.security.auth;

import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.security.config.JwtService;
import com.shgonzals.booknest.security.user.Role;
import com.shgonzals.booknest.security.user.User;
import com.shgonzals.booknest.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		if(request != null){
			var user = User.builder()
						   .firstname(request.getFirstname())
						   .lastname(request.getLastname())
						   .email(request.getEmail())
						   .password(passwordEncoder.encode(request.getPassword()))
						   .role(Role.USER)
						   .build();
			repository.save(user);
			var jwtToken = jwtService.generateToken(user);
			return AuthenticationResponse.builder()
										 .token(jwtToken).build();
		}
		return AuthenticationResponse.builder().build();
	}

	public AuthenticationResponse authenticate(AuthenticateRequest request) {
		if (request != null){
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(),
							request.getPassword()
					)
			);
			var user = repository.findByEmail(request.getEmail())
								 .orElseThrow(() -> new RuntimeException("user not found: " + request.getEmail()));
			if(user != null){
				var jwtToken = jwtService.generateToken(user);
				return AuthenticationResponse.builder()
											 .token(jwtToken).build();
			}
		}
		return AuthenticationResponse.builder().build();
	}

	public void update(UserRequest request){

		Objects.requireNonNull(request, "request cannot be null");

		repository.findByEmail(request.getEmail())
				  .ifPresentOrElse(user -> {
					  user.setFirstname(request.getFirstname());
					  user.setLastname(request.getLastname());
					  user.setPassword(request.getPassword());

					  repository.save(user);
				  }, () -> {
					  throw new RuntimeException("User not found: " + request.getEmail());
				  });
	}

	public List<User> getAllUsers(){
		return repository.findAll();
	}

	public void delete(String email){
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("email cannot be null or empty");
		}

		repository.findByEmail(email).ifPresent(user -> {
			repository.delete(user);
		});
	}
}
