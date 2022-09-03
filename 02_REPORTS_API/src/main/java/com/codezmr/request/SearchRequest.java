package com.codezmr.request;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SearchRequest {
	
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	
}
