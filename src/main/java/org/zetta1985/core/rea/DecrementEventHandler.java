/**
 * 
 */
package org.zetta1985.core.rea;

/**
 * @author t_hara
 *
 */
public interface DecrementEventHandler<R extends Resource, A extends Agent> {

	void handle(DecrementEvent<R,A> event);
}
