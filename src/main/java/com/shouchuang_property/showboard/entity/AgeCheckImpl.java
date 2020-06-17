package com.shouchuang_property.showboard.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeCheckImpl implements ConstraintValidator<AgeCheck, Integer> {
	private Integer min;
	private Integer max;
	
	@Override
	public void initialize(AgeCheck constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value < min || value > max) {
			return false;
		}
		return true;
	}

}
