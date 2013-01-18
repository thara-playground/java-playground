/**
 * 
 */
package org.zetta1985.core.cqrs;

/**
 * @author t_hara
 *
 */
public interface CommandReceiver {

	void send(Object command);
}
