/**
 * 
 */
package org.zetta1985.core.dci;

/**
 * @author t_hara
 *
 */
public interface RoleBuilder<T> {

	<R extends Role<T>> R to(Class<R> type);
}
