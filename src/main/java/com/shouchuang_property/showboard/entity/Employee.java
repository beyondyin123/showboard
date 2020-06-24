package com.shouchuang_property.showboard.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	// add zhangsan
	// departName
	private String departName;
	// departId
	private Integer departId;
	
	@ListValueNotNull
	private List<String> hobbies;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		if (other.getId() != this.id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((age == null) ? 0 : age.hashCode());
//		result = prime * result + ((departId == null) ? 0 : departId.hashCode());
//		result = prime * result + ((departName == null) ? 0 : departName.hashCode());
//		result = prime * result + ((hobbies == null) ? 0 : hobbies.hashCode());
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		long temp;
//		temp = Double.doubleToLongBits(score);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		return result;
		return id;
	}

	@Override
	public String toString() {
		return "This is a Employee [name=" + name + ", score=" + score + ", id=" + id + ", age=" + age + ", departName="
				+ departName + ", departId=" + departId + ", hobbies=" + hobbies + "]";
	}	
}
