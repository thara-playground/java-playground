package org.axonframework.guice.provider;

import java.util.Set;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.guice.EventSubscriber;

import com.google.inject.Inject;

/**
 * @author t_hara
 */
public class DefaultEventBusProvider implements EventBusProvider {

	private Set<EventSubscriber> subscribers;
	
	@Override
	public EventBus get() {
		EventBus eventBus = getEventBus();
		
		if (subscribers != null) {
			for (EventSubscriber eventSubscriber : subscribers) {
				eventSubscriber.subscribe(eventBus);
			}
		}
		
		return eventBus;
	}
	
	protected EventBus getEventBus() {
		SimpleEventBus simpleEventBus = new SimpleEventBus(false);
		return simpleEventBus;
	}

	/**
	 * @param subscribers the subscribers to set
	 */
	@Inject
	public void setSubscribers(Set<EventSubscriber> subscribers) {
		this.subscribers = subscribers;
	}
}
