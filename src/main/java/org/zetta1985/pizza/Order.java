/**
 * 
 */
package org.zetta1985.pizza;

import org.zetta1985.core.rea.Contract;

/**
 * @author t_hara
 *
 */
public class Order extends Contract<PaymentLine, OrderLine> {

	public void addPayment(Customer customer, Currency currency) {
		getIncrementCommitments().clear();
		PaymentLine line = new PaymentLine();
		line.setAmount(getTotal());
		line.setResource(currency);
		line.setProvider(customer);
		getIncrementCommitments().add(line);
	}
	
	public int getTotal() {
		int total = 0;
		
		for (OrderLine line : getDecrementCommitments()) {
			total += line.getAmount() * line.getResource().getValue();
		}
		
		return total;
	}
}
