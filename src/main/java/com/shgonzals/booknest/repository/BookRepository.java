package com.shgonzals.booknest.repository;

import com.shgonzals.booknest.document.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

	Optional<Book> findByTitle(String title);

	List<Book> findByAuthor(String author);
}
