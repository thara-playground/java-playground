package org.axonframework.guice;

import org.axonframework.commandhandling.CommandBus;

/**
 * @author t_hara
 *
 */
public interface CommandSubscriber extends Subscriber<CommandBus>{
	
	/**
	 * @inheritDoc
	 */
	void subscribe(CommandBus commandBus);
}
