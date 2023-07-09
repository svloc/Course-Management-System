package com.cms.exception;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExceptionResponse {
	private LocalDate timestamp;
	private String message;
	private String details;
	private String httpCodeMessage;
}
