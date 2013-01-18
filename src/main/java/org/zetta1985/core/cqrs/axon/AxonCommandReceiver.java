/**
 * 
 */
package org.zetta1985.core.cqrs.axon;

import org.apache.commons.lang.Validate;
import org.axonframework.commandhandling.CommandBus;
import org.zetta1985.core.cqrs.CommandReceiver;

/**
 * @author t_hara
 *
 */
public class AxonCommandReceiver implements CommandReceiver {

	private final CommandBus commandBus;
	
	/**
	 * @param commandBus
	 */
	public AxonCommandReceiver(CommandBus commandBus) {
		super();
		Validate.notNull(commandBus);
		this.commandBus = commandBus;
	}

	@Override
	public void send(Object command) {
		SimpleCommandCallback callback = new SimpleCommandCallback();
		commandBus.dispatch(command, callback);
	}
}
