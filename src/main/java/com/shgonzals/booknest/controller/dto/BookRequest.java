package com.shgonzals.booknest.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequest {

	private String title;
	private String author;
	private String rate;
	private LocalDate readDate;
	private String shelf;

}
