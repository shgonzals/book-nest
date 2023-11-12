package com.shgonzals.booknest.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookRequest {

	private String title;
	private String author;
	private String rate;
	private LocalDate readDate;
	private String shelf;

}
