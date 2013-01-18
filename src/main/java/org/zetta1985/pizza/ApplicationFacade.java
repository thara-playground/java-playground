/**
 * 
 */
package org.zetta1985.pizza;

import java.util.List;

/**
 * @author t_hara
 *
 */
public class ApplicationFacade {

	Repository repository;
	
	Order order;
	
	public int addOrderLine(Customer customer, long pizzaId, int amount) {
		
		OrderLine orderLine = new OrderLine();
		orderLine.setResource(repository.getPizzas().get(pizzaId));
		orderLine.setAmount(amount);
		orderLine.setProvider(customer);

		order.getDecrementCommitments().add(orderLine);
		
		return order.getTotal();
	}
	
	public void commitOrder(Customer customer) {
		order.addPayment(customer, new Currency());
		
		repository.saveCustomer(customer);
		repository.saveOrder(order);
	}
	
	public void saveChangeForAdmin(List<Integer> orderLines, List<Integer> paymentLines) {
		
		for (Integer no : orderLines) {
			OrderLine orderLine = order.getDecrementCommitments().get(no);
			if (!orderLine.isFulfilled()) {
				orderLine.fulfill();
			}
		}
		
		for (Integer no : paymentLines) {
			PaymentLine paymentLine = order.getIncrementCommitments().get(no);
			if (!paymentLine.isFulfilled()) {
				paymentLine.fulfill();
			}
		}
		
		repository.saveOrder(order);
	}
}
