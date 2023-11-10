package com.shgonzals.booknest.controller;

import com.shgonzals.booknest.controller.dto.BookRequest;
import com.shgonzals.booknest.document.Book;
import com.shgonzals.booknest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService service;

	@GetMapping("/getAll")
	private ResponseEntity<List<Book>> getAllBooks(){
		return ResponseEntity.ok(service.getAllBooks());
	}

	@GetMapping("/getByAuthor")
	private ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author){
		return ResponseEntity.ok(service.getBooksByAuthor(author));
	}

	@PostMapping("/create")
	private ResponseEntity<String> createBook(@RequestBody BookRequest book){
		service.insertBook(book);
		return ResponseEntity.ok("");
	}

	@PostMapping("/update")
	private ResponseEntity<String> updateBook(@RequestBody BookRequest book){
		service.updateBook(book);
		return ResponseEntity.ok("");
	}

	@DeleteMapping("/delete")
	private ResponseEntity<String> deleteBook(@RequestParam String title){
		service.deleteBook(title);
		return ResponseEntity.ok("");
	}
}
