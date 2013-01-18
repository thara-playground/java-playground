/**
 * 
 */
package org.zetta1985.pizza;

import org.zetta1985.core.rea.IncrementCommitment;

/**
 * @author t_hara
 *
 */
public class PaymentLine extends IncrementCommitment<Currency, Customer> {

	private int amount;

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
