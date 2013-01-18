/**
 * 
 */
package org.tomochika1985.sample.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.Length;
import org.tomochika1985.core.domain.BaseEntityBuilder;
import org.tomochika1985.core.domain.FieldErrorHandler;

/**
 * @author t_hara
 *
 */
public class Employee {

	private String id;
	
	@NotNull(message="{error.required},{employee.id}")
	@Length(min=4, max=4, message="{error.length},{employee.id}")
	private String name;
	
	public Employee() {
		super();
	}
	
	/**
	 * @param id
	 */
	public Employee(String id) {
		super();
		this.id = id;
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public Employee(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	void setName(String name) {
		this.name = name;
	}
	
	public static class Builder extends BaseEntityBuilder<Employee, FieldErrorHandler<Builder>> {

		private final Validator validator;
		
		private Set<ConstraintViolation<Builder>> violations;

		@NotNull(message="error.required")
		@Length(min=4, max=4, message="error.length")
		private String id;
		
		@NotNull(message="error.required")
		@Length(min=4, max=4, message="error.length")
		private String name;
		
		/**
		 * @param validator
		 */
		public Builder(Validator validator) {
			super();
			Validate.notNull(validator);
			this.validator = validator;
		}
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		@Override
		public Employee build(FieldErrorHandler<Builder> errorHandler) {
			Employee result = isForCreation() ? new Employee() : getTarget();
			
			this.violations = this.validator.validate(this);
			notifyErrorTo(errorHandler);
			
			result.setId(id);
			result.setName(name);
			return result;
		}

		@Override
		public void notifyErrorTo(FieldErrorHandler<Builder> errorHandler) {
			if (violations != null) {
				errorHandler.notifyError(violations);
			}
		}
	}
}
