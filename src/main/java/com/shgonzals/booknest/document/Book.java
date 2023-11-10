package com.shgonzals.booknest.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@Builder
public class Book {
	@Id
	private String id;
	@Indexed(unique = true)
	private String title;
	private String author;
	private String rate;
	private LocalDate readDate;
	private String shelf;
}
