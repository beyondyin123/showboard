package com.shouchuang_property.showboard.common.utils;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
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
}
