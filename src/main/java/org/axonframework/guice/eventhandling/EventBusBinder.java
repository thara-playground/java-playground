/**
 * 
 */
package org.axonframework.guice.eventhandling;

import com.google.inject.binder.LinkedBindingBuilder;

/**
 * @author t_hara
 *
 */
public abstract class EventBusBinder {

	public abstract LinkedBindingBuilder<EventBusFactory> setup();
}
