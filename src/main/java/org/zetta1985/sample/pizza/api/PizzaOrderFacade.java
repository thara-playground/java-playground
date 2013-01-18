/**
 * 
 */
package org.zetta1985.sample.pizza.api;

import org.zetta1985.sample.pizza.domain.Customer;
import org.zetta1985.sample.pizza.domain.Order;

/**
 * @author t_hara
 *
 */
public interface PizzaOrderFacade {

	Order createOrder(Customer customer);

	void addOrderLine(Order order, long pizzaId, int amount);
	
	void addPaymentLine(Order order, int value);
	
	void commitOrder(Order order);
}
