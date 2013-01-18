/**
 * 
 */
package org.zetta1985.sample.pizza.domain;

/**
 * @author t_hara
 *
 */
public class OrderLine {

	private long id;
	
	private long pizzaId;
	
	private int amount;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the pizzaId
	 */
	public long getPizzaId() {
		return pizzaId;
	}

	/**
	 * @param pizzaId the pizzaId to set
	 */
	public void setPizzaId(long pizzaId) {
		this.pizzaId = pizzaId;
	}

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
