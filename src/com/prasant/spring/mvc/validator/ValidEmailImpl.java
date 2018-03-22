package com.prasant.spring.mvc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidEmailImpl implements ConstraintValidator<ValidEmail, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (EmailValidator.getInstance(false).isValid(value)) {
			return true;
		}
		return false;
	}

}
