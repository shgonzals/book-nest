package com.shgonzals.booknest.controller;

import com.shgonzals.booknest.commons.Constants;
import com.shgonzals.booknest.controller.dto.BookRequest;
import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtToken")
@Tag(name = Constants.BOOK_API_DESC, description = Constants.BOOK_API)
public class BookController {

	private final BookService service;

	private final static String GET_VALUE = "Listado";
	private final static String GET_NOTES = "Obtenemos un listado de todos los usuarios registrados";

	private final static String REGISTER_VALUE = "Registro";
	private final static String REGISTER_NOTES = "Registramos un usuario para que pueda hacer login";

	private final static String UPDATE_VALUE = "Actualizar";
	private final static String UPDATE_NOTES = "Actualizamos los datos de un usuario";

	private final static String DELETE_VALUE = "Borrado";
	private final static String DELETE_NOTES = "Borramos un usuario existente";


	@Operation(summary = GET_VALUE, description = GET_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@GetMapping("/getAll")
	ResponseEntity<List<Book>> getAllBooks(){
		return ResponseEntity.ok(service.getAllBooks());
	}

	@Operation(summary = GET_VALUE, description = GET_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@GetMapping("/getByAuthor")
	ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam @NotNull String author){
		return ResponseEntity.ok(service.getBooksByAuthor(author));
	}

	@Operation(summary = REGISTER_VALUE, description = REGISTER_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@PostMapping("/create")
	ResponseEntity<String> createBook(@RequestBody @NotNull BookRequest request){
		Objects.requireNonNull(request, "request cannot be null");

		service.insertBook(request);
		return ResponseEntity.ok("");
	}

	@Operation(summary = UPDATE_VALUE, description = UPDATE_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@PostMapping("/update")
	ResponseEntity<String> updateBook(@RequestBody @NotNull BookRequest book){
		service.updateBook(book);
		return ResponseEntity.ok("");
	}

	@Operation(summary = DELETE_VALUE, description = DELETE_NOTES, security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = Constants.MESSAGE_200)
	@ApiResponse(responseCode = "400", description = Constants.MESSAGE_400)
	@ApiResponse(responseCode = "401", description = Constants.MESSAGE_401)
	@ApiResponse(responseCode = "403", description = Constants.MESSAGE_403)
	@ApiResponse(responseCode = "404", description = Constants.MESSAGE_404)
	@ApiResponse(responseCode = "500", description = Constants.MESSAGE_500)
	@DeleteMapping("/delete")
	ResponseEntity<String> deleteBook(@RequestParam @NotNull String title){
		service.deleteBook(title);
		return ResponseEntity.ok("");
	}
}
