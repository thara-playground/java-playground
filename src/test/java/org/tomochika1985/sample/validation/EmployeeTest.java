/**
 * 
 */
package org.tomochika1985.sample.validation;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.engine.ResourceBundleMessageInterpolator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tomochika1985.core.domain.FieldErrorHandler;
import org.tomochika1985.sample.validation.Employee.Builder;

/**
 * @author t_hara
 *
 */
public class EmployeeTest {

	static ResourceBundle resource = ResourceBundle.getBundle("TestMessages");
	static Validator validator;
	
	@BeforeClass
	public static void init() {
		Configuration<?> configure = Validation.byDefaultProvider().configure();
		((HibernateValidatorConfiguration)configure).messageInterpolator(new ResourceBundleMessageInterpolator(resource));
		
		ValidatorFactory validatorFactory = configure.buildValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void validIdForBuilder() {
		Employee.Builder builder = new Employee.Builder(validator);
		builder.id("123").name("");
		
		FieldErrorHandler<Employee.Builder> errorHandler = new FieldErrorHandler<Employee.Builder>() {
			@Override
			public void notifyError(Set<ConstraintViolation<Builder>> violations) {
				for (ConstraintViolation<Builder> v : violations) {
					System.out.println(v.getPropertyPath().toString());
					System.out.println(v.getLeafBean().getClass().getSimpleName());
					System.out.println(v.getMessage());
				}
			}
		};
		builder.build(errorHandler);
		
		Employee result = builder.id("123").name("HOGE").build(errorHandler);
		System.out.println(result.getId());
	}
	
	@Test
	public void validId() {
		Employee emp = new Employee("123", "");
	
		Set<ConstraintViolation<Employee>> results = validator.validate(emp);
		assertEquals(1, results.size());
		
		Iterator<ConstraintViolation<Employee>> violations = results.iterator();
		ConstraintViolation<Employee> v = violations.next();
		System.out.println(v.getPropertyPath());
		assertEquals("id", v.getPropertyPath().toString());
		assertEquals("Employee", v.getLeafBean().getClass().getSimpleName());
		assertEquals("従業員IDは4桁で入力してください。", v.getMessage());
	}
	
	@Test
	public void validName() {
		Employee emp = new Employee("1234", null);
		
		Set<ConstraintViolation<Employee>> results = validator.validate(emp);
		assertEquals(1, results.size());
		
		Iterator<ConstraintViolation<Employee>> violations = results.iterator();
		assertEquals("従業員名は必須です。", violations.next().getMessage());
	}
}
