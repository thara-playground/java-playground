/**
 * 
 */
package org.axonframework.guice.eventhandling;

import org.axonframework.eventhandling.EventBus;

/**
 * @author t_hara
 *
 */
public interface EventBusFactory {

	EventBus create();
}
