/**
 * 
 */
package org.zetta1985.core.dci.impl;

import org.zetta1985.core.dci.Role;
import org.zetta1985.core.dci.RoleBuilder;

/**
 * @author t_hara
 *
 */
public class RoleBuilderImpl<T> implements RoleBuilder<T> {

	private final T target;

	/**
	 * @param target
	 */
	public RoleBuilderImpl(T target) {
		super();
		this.target = target;
	}
	
	public <R extends Role<T>> R to(Class<R> type) {
		try {
			Role<T> r = type.newInstance().apply(getTarget());
			return type.cast(r);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return the target
	 */
	protected T getTarget() {
		return target;
	}
}
