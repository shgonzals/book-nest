package com.shgonzals.booknest.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
	@Field("readDate")
	private LocalDate readDate;
	private String shelf;
}
