/**
 * 
 */
package org.zetta1985.sample.pizza.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t_hara
 *
 */
public class Order {

	private long id;
	
	private Customer customer;
	
	private List<OrderLine> orderLines = new ArrayList<OrderLine>();
	
	private List<PaymentLine> paymentLines = new ArrayList<PaymentLine>();
	
	
}
