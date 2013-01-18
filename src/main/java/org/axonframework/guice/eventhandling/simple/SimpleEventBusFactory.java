/**
 * 
 */
package org.axonframework.guice.eventhandling.simple;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.guice.eventhandling.EventBusFactory;

/**
 * @author t_hara
 *
 */
public class SimpleEventBusFactory implements EventBusFactory {

	@Override
	public EventBus create() {
		SimpleEventBus eventBus = new SimpleEventBus(false);
		return eventBus;
	}

}
