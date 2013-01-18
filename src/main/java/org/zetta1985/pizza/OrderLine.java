/**
 * 
 */
package org.zetta1985.pizza;

import org.zetta1985.core.rea.DecrementCommitment;

/**
 * @author t_hara
 *
 */
public class OrderLine extends DecrementCommitment<Pizza, Customer> {

	private int amount;
	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
}
