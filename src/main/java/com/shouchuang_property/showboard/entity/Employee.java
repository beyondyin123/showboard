package com.shouchuang_property.showboard.entity;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class Employee {
	@NotNull(message = "员工姓名不能为空")
	@NotEmpty(message = "员工姓名不能为空1")
	@Size(min = 1,max = 10,message = "员工名字长度必须在10个字母以内")
	private String name;
	@DecimalMin(value = "0.1", message="分数最低0.1")
	private double score;
	
	@NotNull(message = "员工ID不能为空")
	private Integer id;
	
	@AgeCheck
	private Integer age;
	
	private String departName;
	// departId
	private Integer departId;
	
	@ListValueNotNull
	private List<String> hobbies;
}
