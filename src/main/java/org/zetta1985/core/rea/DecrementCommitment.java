/**
 * 
 */
package org.zetta1985.core.rea;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @author t_hara
 *
 */
public class DecrementCommitment<R extends Resource, A extends Agent> implements Commitment {

	private R resource;
	
	private A recipient;
	
	private boolean fulfilled;
	
	private final List<DecrementEventHandler<R,A>> eventHandlers = new ArrayList<DecrementEventHandler<R,A>>();
	
	@Override
	public void fulfill() {
		this.fulfilled = true;
		DecrementEvent<R,A> event = new DecrementEvent<R,A>(this);
		eventCreated(event);
	}
	
	protected void eventCreated(DecrementEvent<R,A> event) {
		for (DecrementEventHandler<R,A> eventHandler : this.eventHandlers) {
			eventHandler.handle(event);
		}
	}
	
	public void addEventHandler(DecrementEventHandler<R,A> eventHandler) {
		Validate.notNull(eventHandler);
		eventHandlers.add(eventHandler);
	}
	
	@Override
	public boolean isFulfilled() {
		return fulfilled;
	}
	
	
	/**
	 * @return the provider
	 */
	public A getProvider() {
		return recipient;
	}
	
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(A provider) {
		this.recipient = provider;
	}
	
	/**
	 * @return the resource
	 */
	public R getResource() {
		return resource;
	}
	
	/**
	 * @param resource the resource to set
	 */
	public void setResource(R resource) {
		this.resource = resource;
	}
}
