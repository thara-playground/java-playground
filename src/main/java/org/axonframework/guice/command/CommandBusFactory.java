/**
 * 
 */
package org.axonframework.guice.command;

import org.axonframework.commandhandling.CommandBus;

/**
 * @author t_hara
 *
 */
public interface CommandBusFactory {
	
	CommandBus create();
}
