/**
 * 
 */
package org.zetta1985.core.dci;

/**
 * @author t_hara
 *
 */
public interface RoleApplier {

	<T> RoleBuilder<T> from(T target);
}
