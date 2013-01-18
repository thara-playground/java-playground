package org.axonframework.guice;

import org.axonframework.eventhandling.EventBus;

/**
 * @author t_hara
 *
 */
public interface EventSubscriber extends Subscriber<EventBus>{
	
	/**
	 * @inheritDoc
	 */
	void subscribe(EventBus eventBus);
}
