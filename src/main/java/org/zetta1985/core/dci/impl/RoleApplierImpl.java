/**
 * 
 */
package org.zetta1985.core.dci.impl;

import org.zetta1985.core.dci.RoleApplier;
import org.zetta1985.core.dci.RoleBuilder;

/**
 * @author t_hara
 *
 */
public class RoleApplierImpl implements RoleApplier {

	public <T> RoleBuilder<T> from(T target) {
		return new RoleBuilderImpl<T>(target);
	}
}
