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

	public void updateBook(BookRequest request){
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
		repository.findByTitle(title).ifPresentOrElse(book -> {
			repository.delete(book);
		}, () -> {
			throw new RuntimeException("book not found: " + title);
		});
	}

	public List<Book> getBooksByAuthor(String author){
		return repository.findByAuthor(author);
	}

	public List<Book> getAllBooks(){
		return repository.findAll();
	}
}
