package com.shouchuang_property.showboard.common.utils;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import com.shouchuang_property.showboard.entity.EmployeeValidInfo;

import javax.validation.Validator;

public class ValidationUtils {
    static Validator validator;
    static{
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
	}
    public static Validator getValidator(){
        return validator;
    }
    
    public <T> Set<ConstraintViolation<T>> validate(T t) {
    	Set<ConstraintViolation<T>> violations =  this.validator.validate(t);
    	return violations;
    }
}
