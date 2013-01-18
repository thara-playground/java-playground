/**
 * 
 */
package org.zetta1985.core.rea;

import java.util.Date;

/**
 * @author t_hara
 *
 */
public class DecrementEvent<R extends Resource, A extends Agent> {

	private final long date;
	
	private final R resource;
	
	private final A recipient;

	/**
	 * @param date
	 * @param resource
	 * @param recipient
	 */
	public DecrementEvent(DecrementCommitment<R, A> commitment) {
		super();
		this.date = new Date().getTime();
		this.resource = commitment.getResource();
		this.recipient = commitment.getProvider();
	}
	
	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @return the resource
	 */
	public R getResource() {
		return resource;
	}
	
	/**
	 * @return the provider
	 */
	public A getProvider() {
		return recipient;
	}
}
