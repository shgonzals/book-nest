package com.shgonzals.booknest.service;

import com.shgonzals.booknest.controller.dto.BookRequest;
import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository repository;

	public void insertBook(BookRequest book){
		var b = Book.builder()
				.title(book.getTitle())
				.author(book.getAuthor())
				.rate(book.getRate())
				.readDate(book.getReadDate())
				.shelf(book.getShelf())
				.build();
		repository.save(b);
	}

	public void updateBook(BookRequest book){
		var existingBook = repository.findByTitle(book.getTitle()).orElseThrow();
		if(existingBook != null){
			existingBook.setTitle(book.getTitle());
			existingBook.setAuthor(book.getAuthor());
			existingBook.setRate(book.getRate());
			existingBook.setReadDate(book.getReadDate());
			existingBook.setShelf(book.getShelf());
		}
		repository.save(existingBook);
	}

	public void deleteBook(String title){
		var book = repository.findByTitle(title).orElseThrow();
		if(book != null){
			repository.delete(book);
		}
	}

	public List<Book> getBooksByAuthor(String author){
		return repository.findByAuthor(author);
	}

	public List<Book> getAllBooks(){
		return repository.findAll();
	}
}
