package com.shgonzals.booknest.service;

import com.shgonzals.booknest.controller.dto.BookRequest;
import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

	private BookRepository repository;

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
		var b = Book.builder()
					.title(book.getTitle())
					.author(book.getAuthor())
					.rate(book.getRate())
					.readDate(book.getReadDate())
					.shelf(book.getShelf())
					.build();
		repository.save(b);
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
