/**
 * 
 */
package org.zetta1985.sample.pizza.domain;

/**
 * @author t_hara
 *
 */
public class PaymentLine {

	private long id;
	
	private int value;

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
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
