package com.shouchuang_property.showboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shouchuang_property.showboard.common.utils.ValidationUtils;
import com.shouchuang_property.showboard.entity.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest {
	@Test
	public void valid() {
        Employee employee = new Employee();
        employee.setName("");
        employee.setAge(15);
        List<String> hobbies = new ArrayList();
        employee.setHobbies(hobbies);
        Set<ConstraintViolation<Employee>> violations = ValidationUtils.getValidator().validate(employee);
        for (ConstraintViolation constraintViolation : violations) {
            System.out.println("当前有问题属性为:"+constraintViolation.getPropertyPath().toString());
            System.out.println("报错原因为:"+constraintViolation.getMessage());
        }
	}
}
