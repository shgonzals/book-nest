package com.shgonzals.booknest.repository;

import com.shgonzals.booknest.document.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

	@Mock
	private BookRepository repository;

	@Test
	void findByTitleWhenExists() {
		String title = "1984";

		Book expectedBook = Book.builder()
				.title(title)
				.author("George Orwell")
				.rate("1")
				.readDate(LocalDate.now())
				.shelf("Test")
				.build();
		when(repository.findByTitle(title)).thenReturn(Optional.of(expectedBook));

		Optional<Book> actualBook = repository.findByTitle(title);
		assertEquals(Optional.of(expectedBook), actualBook);
	}

	@Test
	void findByTitleWhenNotExists() {
		String title = "1985";
		when(repository.findByTitle(title)).thenReturn(Optional.empty());
		Optional<Book> actualBook = repository.findByTitle(title);
		assertEquals(Optional.empty(), actualBook);
		verify(repository, times(1)).findByTitle(title);
	}

	@Test
	void findByTitleWhenTitleIsNull() {
		Optional<Book> actualBook = repository.findByTitle(null);
		assertTrue(actualBook.isEmpty());
	}


	@Test
	void findByAuthorWhenExists() {
		String author = "George Orwell";
		Book book = Book.builder()
								.title("1984")
								.author(author)
								.rate("1")
								.readDate(LocalDate.now())
								.shelf("Test")
								.build();
		List<Book> expectedBooks = Arrays.asList(book);
		when(repository.findByAuthor(author)).thenReturn(expectedBooks);

		List<Book> actualBooks = repository.findByAuthor(author);
		assertEquals(expectedBooks, actualBooks);
	}

	@Test
	void findByAuthorWhenNotExists() {
		String author = "NoAuthor";
		when(repository.findByAuthor(author)).thenReturn(Collections.emptyList());
		List<Book> actualBooks = repository.findByAuthor(author);
		assertTrue(actualBooks.isEmpty());
	}

	@Test
	void findByAuthorWhenAuthorIsNull() {
		List<Book> books = repository.findByAuthor(null);
		assertTrue(books.isEmpty());
	}
}