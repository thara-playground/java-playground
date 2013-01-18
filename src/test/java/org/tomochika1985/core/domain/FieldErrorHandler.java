/**
 * 
 */
package org.tomochika1985.core.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * @author t_hara
 *
 */
public interface FieldErrorHandler<E> extends ErrorHandler {

	void notifyError(Set<ConstraintViolation<E>> violations);
}
