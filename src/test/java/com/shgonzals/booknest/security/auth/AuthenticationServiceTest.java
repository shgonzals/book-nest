package com.shgonzals.booknest.security.auth;

import com.shgonzals.booknest.security.config.JwtService;
import com.shgonzals.booknest.security.user.Role;
import com.shgonzals.booknest.security.user.User;
import com.shgonzals.booknest.security.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

	@Mock
	private UserRepository repository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtService jwtService;
	@Mock
	private AuthenticationManager authenticationManager;


	private AuthenticationService underTest;

	@BeforeEach
	void setUp(){
		underTest = new AuthenticationService(repository, passwordEncoder, jwtService, authenticationManager);
	}

	@Test
	void canRegisterWhenUserNotExists() {
		AuthenticationResponse response = new AuthenticationResponse();
		//Llamamos al servicio
		underTest.register(null);

		// Verifica que el token sea una cadena vacía para un RegisterRequest nulo
		assertEquals(null, response.getToken());

		// Verifica que el método save nunca se llame con un User
		verify(repository, never()).save(any(User.class));
	}

	@Test
	void canRegisterWhenUserExists() {
		RegisterRequest request = new RegisterRequest("admin", "admin", "admin", "admin");
		underTest.register(request);

		User user = User.builder()
				.email("admin")
				//.password("admin")
				.firstname("admin")
				.lastname("admin")
				.role(Role.USER)
				.build();

		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		verify(repository).save(userArgumentCaptor.capture());

		User capturedUser = userArgumentCaptor.getValue();
		assertEquals(capturedUser, user);
	}

	@Test
	void canAuthenticateWhenUserExists() {
		AuthenticateRequest request = new AuthenticateRequest("admin", "admin");

		String email = "admin";
		User expectedUser = User.builder()
						.email(email)
						.password("admin")
						.firstname("admin")
						.lastname("admin")
						.role(Role.USER)
						.build();
		when(repository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
		Optional<User> actualUser = repository.findByEmail(email);
		assertEquals(Optional.of(expectedUser), actualUser);

		//Tener en cuenta el orden de las llamadas
		underTest.authenticate(request);

		String expectedToken = "generatedToken";
		when(jwtService.generateToken(actualUser.get())).thenReturn(expectedToken);
		String actualToken = jwtService.generateToken(actualUser.get());
		assertEquals(expectedToken, actualToken);
	}

	@Test
	void canAuthenticateWhenUserNotExists() {
		AuthenticateRequest request = new AuthenticateRequest("adminNotExists", "admin");
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
		when(repository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			underTest.authenticate(request);
		});

		assertEquals("user not found: adminNotExists", exception.getMessage()); // Verifica el mensaje de la excepción
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class)); // Verifica que el método authenticate se llame una vez
	}

	@Test
	void canAuthenticateWhenUserIsNull() {
		AuthenticationResponse response = new AuthenticationResponse();
		//Llamamos al servicio
		underTest.authenticate(null);

		// Verifica que el token sea una cadena vacía para un RegisterRequest nulo
		assertEquals(null, response.getToken());

		// Verifica que los métodos nunca se llamen
		verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(repository, never()).findByEmail(any(String.class));
		verify(jwtService, never()).generateToken(any(User.class));
	}

	@Test
	void updateWhenUserExists() {
		UserRequest request = new UserRequest("admin", "admin", "admin", "admin");
		String email = "admin";
		User expectedUser = User.builder()
								.email(email)
								.password("admin")
								.firstname("admin")
								.lastname("admin")
								.role(Role.USER)
								.build();
		when(repository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
		Optional<User> actualUser = repository.findByEmail(email);
		assertEquals(Optional.of(expectedUser), actualUser);

		underTest.update(request);
		verify(repository, times(1)).save(expectedUser);

	}

	@Test
	void updateWhenUserNotExists() {
		UserRequest request = new UserRequest("adminNotExists", "adminNotExists", "adminNotExists", "adminNotExists");

		//given
		String email = "adminNotExists";
		when(repository.findByEmail(email)).thenReturn(Optional.empty());

		//when
		Optional<User> actualUser = repository.findByEmail(email);
		assertEquals(Optional.empty(), actualUser);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			underTest.update(request);
		});
		assertEquals("User not found: adminNotExists", exception.getMessage());

		verify(repository, never()).save(any(User.class));

	}

	@Test
	void updateWhenUserIsNull() {
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			underTest.update(null);
		});

		assertEquals("request cannot be null", exception.getMessage());

		// Verifica que los métodos nunca se llamen
		verify(repository, never()).findByEmail(any(String.class));
		verify(repository, never()).save(any(User.class));
	}

	@Test
	void canGetAllUsers() {
		//when
		underTest.getAllUsers();
		//then
		verify(repository).findAll();

	}

	@Test
	void deleteWhenUserExists() {
		String email = "admin";
		User existingUser = User.builder()
				.email(email)
				.password("admin")
				.firstname("admin")
				.lastname("admin")
				.role(Role.USER)
				.build();
		when(repository.findByEmail(email)).thenReturn(Optional.of(existingUser));
		assertDoesNotThrow(() -> {
			underTest.delete(email);
		});
		verify(repository, times(1)).delete(existingUser);
	}

	@Test
	void deleteWhenUserNotExists() {
		String email = "adminNotExists";
		when(repository.findByEmail(email)).thenReturn(Optional.empty());

		// Act and Assert
		assertDoesNotThrow(() -> {
			underTest.delete(email);
		});

		verify(repository, never()).delete(any(User.class));

	}

	@Test
	void deleteWhenUserIsNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			underTest.delete(null);
		});

		assertEquals("email cannot be null or empty", exception.getMessage());

		// Verifica que los métodos nunca se llamen
		verify(repository, never()).findByEmail(any(String.class));
		verify(repository, never()).delete(any(User.class));
	}
}