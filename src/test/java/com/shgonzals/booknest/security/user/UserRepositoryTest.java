package com.shgonzals.booknest.security.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

	@Mock
	private UserRepository repository;

	@Test
	void findByEmailWhenExists() {
		//1. Genero el usuario que voy a devolver cuando vaya a ejecutar "findByEmail"
		String email = "admin";
		User expectedUser = User.builder()
				.email(email)
				.password("admin")
				.firstname("admin")
				.lastname("admin")
				.role(Role.USER)
				.build();
		when(repository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

		//2. Ejecuto "findByEmail"
		Optional<User> actualUser = repository.findByEmail(email);

		//3. Compruebo que el objeto devuelto por el repositorio es el mismo que he generado
		assertEquals(Optional.of(expectedUser), actualUser);
	}

	@Test
	void findByEmailWhenNotExists() {
		//given
		String email = "adminNotExists";
		when(repository.findByEmail(email)).thenReturn(Optional.empty());

		//when
		Optional<User> actualUser = repository.findByEmail(email);

		//then
		assertEquals(Optional.empty(), actualUser);
		verify(repository, times(1)).findByEmail(email);
	}

	@Test
	void findByEmailWhenEmailIsNull(){
		//given
		//String email = null;

		//when
		Optional<User> actualUser = repository.findByEmail(null);
		
		//then
		assertTrue(actualUser.isEmpty());
	}
}