/**
 * 
 */
package org.tomochika1985.core.domain;

/**
 * @author t_hara
 *
 */
public interface EntityBuilder<E, H extends ErrorHandler> {

	E build(H handler);
}
