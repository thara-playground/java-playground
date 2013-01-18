/**
 * 
 */
package org.zetta1985.core.rea;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t_hara
 *
 */
public abstract class Contract<I extends IncrementCommitment<?,?>, D extends DecrementCommitment<?,?>> {

	private long id;
	
	private List<I> incrementCommitments = new ArrayList<I>();
	
	private List<D> decrementCommitments = new ArrayList<D>();
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	protected void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return the incrementCommitments
	 */
	public List<I> getIncrementCommitments() {
		return incrementCommitments;
	}
	
	/**
	 * @return the decrementCommitments
	 */
	public List<D> getDecrementCommitments() {
		return decrementCommitments;
	}
}
