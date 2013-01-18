/**
 * 
 */
package org.zetta1985.sample.pizza.api.impl;

import org.zetta1985.sample.pizza.api.PizzaOrderFacade;
import org.zetta1985.sample.pizza.domain.Customer;
import org.zetta1985.sample.pizza.domain.Order;

/**
 * @author t_hara
 *
 */
public class PizzaOrderFacadeImpl implements PizzaOrderFacade {

	@Override
	public Order createOrder(Customer customer) {
		return null;
	}

	@Override
	public void addOrderLine(Order order, long pizzaId, int amount) {

	}

	@Override
	public void addPaymentLine(Order order, int value) {

	}

	@Override
	public void commitOrder(Order order) {

	}

}
