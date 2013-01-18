/**
 * 
 */
package org.axonframework.guice.support;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerAdapter;

/**
 * @author t_hara
 *
 */
public class SubscribingSupport {

	protected void subscribe(Object commandHandler, CommandBus commandBus) {
		new AnnotationCommandHandlerAdapter(commandHandler, commandBus).subscribe();
	}
}
