/**
 * 
 */
package org.zetta1985.pizza;

import java.util.HashMap;
import java.util.Map;

import org.zetta1985.core.rea.DecrementEvent;
import org.zetta1985.core.rea.DecrementEventHandler;
import org.zetta1985.core.rea.IncrementEvent;
import org.zetta1985.core.rea.IncrementEventHandler;

/**
 * @author t_hara
 *
 */
public class Repository {

	public Map<Long, Pizza> getPizzas() {
		Map<Long, Pizza> pizzas = new HashMap<Long, Pizza>();
		
		pizzas.put(1L, new Pizza(1L, "Sample", 100));
		pizzas.put(2L, new Pizza(2L, "Sample2", 70));
		pizzas.put(3L, new Pizza(3L, "Sample3", 150));
		
		return pizzas;
	}
	
	public Map<Long, Order> getOrders() {
		Map<Long, Order> orders = new HashMap<Long, Order>();
		
		Order order1 = new Order();
		
		OrderLine orderLine1 = new OrderLine();
		orderLine1.addEventHandler(new DecrementEventHandler<Pizza, Customer>() {
			@Override
			public void handle(DecrementEvent<Pizza, Customer> event) {
				// save
			}
		});
		
		PaymentLine paymentLine1 = new PaymentLine();
		paymentLine1.addEventHandler(new IncrementEventHandler<Currency, Customer>() {
			@Override
			public void handle(IncrementEvent<Currency, Customer> event) {
				// save
			}
		});
		
		order1.getDecrementCommitments().add(orderLine1);
		order1.getIncrementCommitments().add(paymentLine1);
		
		orders.put(order1.getId(), order1);
		
		return orders;
	}
	
	public void saveCustomer(Customer customer) {
		
	}
	
	public void saveOrder(Order order) {
		
	}
}
