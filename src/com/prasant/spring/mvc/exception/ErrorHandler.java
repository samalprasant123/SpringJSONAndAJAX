package com.prasant.spring.mvc.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(DataAccessException.class)
	public String handleDatabaseException(DataAccessException e, Model model) {
		model.addAttribute("errorMsg", "Error - " + e.getMessage());
		e.printStackTrace();
		return "error";
	}
	
	/*@ExceptionHandler(IllegalArgumentException.class)
	public String handleHibernate5Exception(IllegalArgumentException e, Model model) {
		model.addAttribute("errorMsg", "Error - " + e.getMessage());
		e.printStackTrace();
		return "error";
	}*/
	
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessException(AccessDeniedException e) {
		return "accessDenied";
	}
	
}
