package com.shouchuang_property.showboard.entity;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListValueNotNullValidatorImpl implements ConstraintValidator<ListValueNotNull, List> {

    private int value;

    @Override
    public void initialize(ListValueNotNull constraintAnnotation) {
        //传入value 值，可以在校验中使用
        this.value = constraintAnnotation.value();
    }

    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
    	if (list == null || list.size() == 0) {
    		return false;
    	}
        for (Object object : list) {
            if (object == null) {
                //如果List集合中含有Null元素，校验失败
                return false;
            }
        }
        return true;
    }

}