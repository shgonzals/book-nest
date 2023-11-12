package com.shgonzals.booknest.service;

import com.shgonzals.booknest.controller.dto.BookRequest;
import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository repository;

	public void insertBook(BookRequest request){
		Objects.requireNonNull(request, "request cannot be null");

		var book = Book.builder()
						.title(request.getTitle())
						.author(request.getAuthor())
						.rate(request.getRate())
						.readDate(request.getReadDate())
						.shelf(request.getShelf())
						.build();
			repository.save(book);
	}

	public void updateBook(BookRequest request){
		Objects.requireNonNull(request, "request cannot be null");

		repository.findByTitle(request.getTitle()).ifPresentOrElse(book -> {
			book.setTitle(request.getTitle());
			book.setAuthor(request.getAuthor());
			book.setRate(request.getRate());
			book.setReadDate(request.getReadDate());
			book.setShelf(request.getShelf());
			repository.save(book);
		}, () -> {
			throw new RuntimeException("book not found: " + request.getTitle());
		});
	}

	public void deleteBook(String title){
		Objects.requireNonNull(title, "title cannot be null");

		repository.findByTitle(title).ifPresentOrElse(book -> {
			repository.delete(book);
		}, () -> {
			throw new RuntimeException("book not found: " + title);
		});
	}

	public List<Book> getBooksByAuthor(String author){
		Objects.requireNonNull(author, "author cannot be null");
		return repository.findByAuthor(author);
	}

	public List<Book> getAllBooks(){
		return repository.findAll();
	}
}
