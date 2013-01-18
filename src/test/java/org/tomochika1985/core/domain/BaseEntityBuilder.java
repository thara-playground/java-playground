/**
 * 
 */
package org.tomochika1985.core.domain;

import org.apache.commons.lang.Validate;

/**
 * @author t_hara
 *
 */
public abstract class BaseEntityBuilder<E, H extends ErrorHandler> implements EntityBuilder<E, H> {

	private E target;
	
	public BaseEntityBuilder<E, H> target(E target) {
		Validate.notNull(target);
		this.target = target;
		return this;
	}
	
	public abstract void notifyErrorTo(H errorHandler);
	
	protected boolean isForCreation() {
		return this.target == null;
	}
	
	protected boolean isForEdition() {
		return this.target != null;
	}
	
	protected E getTarget() {
		if (isForCreation()) throw new IllegalStateException();
		return target;
	}
}
