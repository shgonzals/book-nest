package com.shgonzals.booknest.controller;

import com.shgonzals.booknest.controller.dto.BookRequest;
import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

	@Mock
	private BookService service;

	private BookController controller;

	@BeforeEach
	void setUp(){
		controller = new BookController(service);
	}

	@Test
	void getAllBooksWhenBooksExists() {
		// Arrange
		Book book = Book.builder()
						.title("1984")
						.author("George Orwell")
						.rate("1")
						.readDate(LocalDate.now())
						.shelf("Test")
						.build();
		List<Book> expectedBooks = Arrays.asList(book);
		when(service.getAllBooks()).thenReturn(expectedBooks);

		// Act
		ResponseEntity<List<Book>> responseEntity = controller.getAllBooks();

		// Assert
		assertEquals(ResponseEntity.ok(expectedBooks), responseEntity);
		verify(service, times(1)).getAllBooks();
	}

	@Test
	void getAllBooksWhenBooksNotExists() {
		//Arrange
		List<Book> expectedBooks = Arrays.asList();
		when(service.getAllBooks()).thenReturn(expectedBooks);

		// Act
		ResponseEntity<List<Book>> responseEntity = controller.getAllBooks();

		// Assert
		assertEquals(ResponseEntity.ok(expectedBooks), responseEntity);
		verify(service, times(1)).getAllBooks();
	}

	@Test
	void getBooksByAuthorWhenAuthorExists() {
		// Arrange
		String author = "George Orwell";
		Book book = Book.builder()
						.title("1984")
						.author(author)
						.rate("1")
						.readDate(LocalDate.now())
						.shelf("Test")
						.build();
		List<Book> expectedBooks = Arrays.asList(book);
		when(service.getBooksByAuthor(author)).thenReturn(expectedBooks);

		// Act
		ResponseEntity<List<Book>> responseEntity = controller.getBooksByAuthor(author);

		// Assert
		assertEquals(ResponseEntity.ok(expectedBooks), responseEntity);
		verify(service, times(1)).getBooksByAuthor(author);
	}

	@Test
	void getBooksByAuthorWhenAuthorNotExists() {
		// Arrange
		String author = "authorNotExists";
		List<Book> expectedBooks = Arrays.asList();
		when(service.getBooksByAuthor(author)).thenReturn(expectedBooks);

		List<Book> actualBooks = service.getBooksByAuthor(author);
		assertTrue(actualBooks.isEmpty());

		verify(service, times(1)).getBooksByAuthor(author);
	}

	@Test
	void getBooksByAuthorWhenAuthorIsNull() {
		// Act
		ResponseEntity<List<Book>> responseEntity = controller.getBooksByAuthor(null);

		// Assert
		assertEquals(ResponseEntity.ok(Arrays.asList()), responseEntity);
	}

	@Test
	void canCreateBookWhenBookIsNotNull(){
		// Arrange
		BookRequest bookRequest = BookRequest.builder()
				 .title("Sample Title")
				 .author("Sample Author")
				 .rate("5")
				 .readDate(LocalDate.now())
				 .shelf("Sample Shelf")
				 .build();

		// Act
		ResponseEntity<String> responseEntity = controller.createBook(bookRequest);

		// Assert
		assertEquals(ResponseEntity.ok(""), responseEntity);
		verify(service, times(1)).insertBook(bookRequest);
	}

	@Test
	void canCreateBookWhenBookIsNull(){
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			controller.createBook(null);
		});

		// Assert
		assertEquals("request cannot be null", exception.getMessage());
		verify(service, times(0)).insertBook(null);
	}

	@Test
	void canUpdateBookWhenBookExists(){}

	@Test
	void canUpdateBookWhenBookNotExists(){}

	@Test
	void canUpdateBookWhenBookIsNull(){}

	@Test
	void canDeleteBookWhenBookExists(){}

	@Test
	void canDeleteBookWhenBookNotExists(){}

	@Test
	void canDeleteBookWhenBookIsNull(){}
}