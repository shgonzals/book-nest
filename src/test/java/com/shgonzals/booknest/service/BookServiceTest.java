package com.shgonzals.booknest.service;

import com.shgonzals.booknest.controller.dto.BookRequest;
import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository repository;

	private BookService underTest;

	@BeforeEach
	void setUp(){
		underTest = new BookService(repository);
	}


	@Test
	void canInsertBookWhenBookExists() {
		LocalDate now = LocalDate.now();
		BookRequest request = BookRequest.builder()
				.title("1984")
				.author("George Orwell")
				.rate("1")
				.readDate(now)
				.shelf("Test")
				.build();

		Book book = Book.builder()
				.title("1984")
				.author("George Orwell")
				.rate("1")
				.readDate(now)
				.shelf("Test")
				.build();

		underTest.insertBook(request);

		ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
		verify(repository).save(bookArgumentCaptor.capture());

		Book capturedBook = bookArgumentCaptor.getValue();
		assertEquals(capturedBook, book);
	}

	@Test
	void canInsertBookWhenBookIsNull() {
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			underTest.insertBook(null);
		});

		assertEquals("request cannot be null", exception.getMessage());

		// Verifica que los métodos nunca se llamen
		verify(repository, never()).save(any(Book.class));
	}

	@Test
	void canUpdateBookWhenBookExists() {
		LocalDate now = LocalDate.now();
		BookRequest request = BookRequest.builder()
					 .title("1984")
					 .author("George Orwell")
					 .rate("1")
					 .readDate(now)
					 .shelf("Test")
					 .build();

		Book expectedBook = Book.builder()
					.title("1984")
					.author("George Orwell")
					.rate("1")
					.readDate(now)
					.shelf("Test")
					.build();
		when(repository.findByTitle(request.getTitle())).thenReturn(Optional.of(expectedBook));
		Optional<Book> actualBook = repository.findByTitle(request.getTitle());
		assertEquals(Optional.of(expectedBook), actualBook);

		underTest.updateBook(request);
		verify(repository, times(1)).save(expectedBook);
	}

	@Test
	void canUpdateBookWhenBookNotExists() {
		LocalDate now = LocalDate.now();
		BookRequest request = BookRequest.builder()
					 .title("1984notExists")
					 .author("George Orwell")
					 .rate("1")
					 .readDate(now)
					 .shelf("Test")
					 .build();
		when(repository.findByTitle(request.getTitle())).thenReturn(Optional.empty());

		Optional<Book> actualBook = repository.findByTitle(request.getTitle());
		assertEquals(Optional.empty(), actualBook);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			underTest.updateBook(request);
		});

		assertEquals("book not found: 1984notExists", exception.getMessage());
		verify(repository, never()).save(any(Book.class));
	}

	@Test
	void canUpdateBookWhenBookIsNull() {
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			underTest.updateBook(null);
		});

		assertEquals("request cannot be null", exception.getMessage());

		// Verifica que los métodos nunca se llamen
		verify(repository, never()).findByTitle(any(String.class));
		verify(repository, never()).save(any(Book.class));
	}

	@Test
	void canDeleteBookWhenBookExists() {
		String title = "1984";
		Book expectedBook = Book.builder()
					.title("1984")
					.author("George Orwell")
					.rate("1")
					.readDate(LocalDate.now())
					.shelf("Test")
					.build();
		when(repository.findByTitle(title)).thenReturn(Optional.of(expectedBook));
		Optional<Book> actualBook = repository.findByTitle(title);
		assertEquals(Optional.of(expectedBook), actualBook);

		underTest.deleteBook(title);
		verify(repository, times(1)).delete(expectedBook);
	}

	@Test
	void canDeleteBookWhenBookNotExists() {
		String title = "1984notExists";
		when(repository.findByTitle(title)).thenReturn(Optional.empty());

		//when
		Optional<Book> actualBook = repository.findByTitle(title);
		assertEquals(Optional.empty(), actualBook);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			underTest.deleteBook(title);
		});
		assertEquals("book not found: 1984notExists", exception.getMessage());

		verify(repository, never()).delete(any(Book.class));
	}

	@Test
	void canDeleteBookWhenBookIsNull() {
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			underTest.deleteBook(null);
		});

		assertEquals("title cannot be null", exception.getMessage());

		// Verifica que los métodos nunca se llamen
		verify(repository, never()).findByTitle(any(String.class));
		verify(repository, never()).delete(any(Book.class));
	}

	@Test
	void canGetBooksByAuthorWhenAuthorExists() {
		//when
		String author = "George Orwell";
		underTest.getBooksByAuthor(author);

		Book book = Book.builder()
						.title("1984")
						.author(author)
						.rate("1")
						.readDate(LocalDate.now())
						.shelf("Test")
						.build();
		List<Book> expectedBooks = Arrays.asList(book);
		when(repository.findByAuthor(author)).thenReturn(expectedBooks);

		//then
		List<Book> actualBooks = repository.findByAuthor(author);
		assertEquals(expectedBooks, actualBooks);
	}

	@Test
	void canGetBooksByAuthorWhenAuthorNotExists() {
		String author = "NoAuthor";
		when(repository.findByAuthor(author)).thenReturn(Collections.emptyList());
		List<Book> actualBooks = repository.findByAuthor(author);
		assertTrue(actualBooks.isEmpty());
	}

	@Test
	void canGetBooksByAuthorWhenAuthorIsNull() {
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			underTest.getBooksByAuthor(null);
		});

		assertEquals("author cannot be null", exception.getMessage());

		// Verifica que los métodos nunca se llamen
		verify(repository, never()).findByAuthor(any(String.class));
	}

	@Test
	void canGetAllBooksWhen() {
		underTest.getAllBooks();
		verify(repository).findAll();
	}
}