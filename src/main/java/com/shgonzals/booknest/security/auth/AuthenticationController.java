package com.shgonzals.booknest.security.auth;

import com.shgonzals.booknest.commons.Constants;
import com.shgonzals.booknest.security.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = Constants.AUTH_API_DESC, description = Constants.AUTH_API)
public class AuthenticationController {

	private final static String LOGIN_VALUE = "Login";
	private final static String LOGIN_NOTES = "Permitimos el acceso a un usuario regitrado";

	private final static String REGISTER_VALUE = "Registro";
	private final static String REGISTER_NOTES = "Registramos un usuario para que pueda hacer login";

	private final static String GET_VALUE = "Listado";
	private final static String GET_NOTES = "Obtenemos un listado de todos los usuarios registrados";

	private final static String UPDATE_VALUE = "Actualizar";
	private final static String UPDATE_NOTES = "Actualizamos los datos de un usuario";

	private final static String DELETE_VALUE = "Borrado";
	private final static String DELETE_NOTES = "Borramos un usuario existente";

	private final static String API_PARAM = "Parámetros del proceso: \r\n- Firstname: Nombre"
											+ "\r\n- Lastname: Apellidos"
											+ "\r\n- Email: Email. Con esto accederemos a la aplicación junto con la contraseña"
											+ "\r\n- Password: Contraseña";

	private final static String AUTH_API_PARAM = "Parámetros del proceso: "
											+ "\r\n- Email: Email. Con esto accederemos a la aplicación junto con la contraseña"
											+ "\r\n- Password: Contraseña";



	private final AuthenticationService service;

	@Operation(summary = REGISTER_VALUE, description = REGISTER_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@Parameter(description = AUTH_API_PARAM, required = true) @RequestBody RegisterRequest request
	){
		return ResponseEntity.ok(service.register(request));
	}

	@Operation(summary = LOGIN_VALUE, description = LOGIN_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@Parameter(description = AUTH_API_PARAM, required = true) @RequestBody AuthenticateRequest request
	){
		return ResponseEntity.ok(service.authenticate(request));
	}

	@Operation(summary = GET_VALUE, description = GET_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getUsers(){
		return ResponseEntity.ok(service.getAllUsers());
	}

	@Operation(summary = UPDATE_VALUE, description = UPDATE_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@DeleteMapping("/updateUser")
	public ResponseEntity<String> updateUser(
			@Parameter(description = API_PARAM, required = true) @RequestBody UserRequest request
	){
		service.update(request);
		return ResponseEntity.ok("");
	}
	@Operation(summary = DELETE_VALUE, description = DELETE_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam String email
	){
		service.delete(email);
		return ResponseEntity.ok("");
	}
}
