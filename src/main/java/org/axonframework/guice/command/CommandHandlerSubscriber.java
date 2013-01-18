/**
 * 
 */
package org.axonframework.guice.command;

import org.axonframework.commandhandling.CommandBus;

/**
 * @author t_hara
 *
 */
public interface CommandHandlerSubscriber {

	void subscribe(CommandBus commandBus);
}
