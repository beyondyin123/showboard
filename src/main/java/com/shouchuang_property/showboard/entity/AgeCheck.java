package com.shouchuang_property.showboard.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {AgeCheckImpl.class})
public @interface AgeCheck {
	/**
     * 添加value属性，可以作为校验时的条件,若不需要，可去掉此处定义
     */
    int min() default 18;
    
    int max() default 65;
    
    String message() default "年龄必须在适合的工作范围内";

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
