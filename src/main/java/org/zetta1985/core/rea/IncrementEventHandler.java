/**
 * 
 */
package org.zetta1985.core.rea;

/**
 * @author t_hara
 *
 */
public interface IncrementEventHandler<R extends Resource, A extends Agent> {

	void handle(IncrementEvent<R,A> event);
}
