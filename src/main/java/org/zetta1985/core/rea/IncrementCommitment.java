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
public abstract class IncrementCommitment<R extends Resource, A extends Agent> implements Commitment {

	private R resource;
	
	private A provider;
	
	private boolean fulfilled;
	
	private final List<IncrementEventHandler<R,A>> eventHandlers = new ArrayList<IncrementEventHandler<R,A>>();
	
	@Override
	public void fulfill() {
		this.fulfilled = true;
		IncrementEvent<R,A> event = new IncrementEvent<R,A>(this);
		eventCreated(event);
	}
	
	protected void eventCreated(IncrementEvent<R,A> event) {
		for (IncrementEventHandler<R,A> eventHandler : this.eventHandlers) {
			eventHandler.handle(event);
		}
	}
	
	public void addEventHandler(IncrementEventHandler<R,A> eventHandler) {
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
		return provider;
	}
	
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(A provider) {
		this.provider = provider;
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
