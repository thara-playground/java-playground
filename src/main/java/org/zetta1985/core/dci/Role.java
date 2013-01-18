/**
 * 
 */
package org.zetta1985.core.dci;

/**
 * @author t_hara
 *
 */
public interface Role<T> {

	Role<T> apply(T t);
}
