/**
 * 
 */
package org.zetta1985.pizza;

import org.zetta1985.core.rea.Resource;

/**
 * @author t_hara
 *
 */
public class Pizza implements Resource {

	private long id;
	
	private String name;
	
	private int value;

	/**
	 * @param id
	 * @param name
	 * @param value
	 */
	public Pizza(long id, String name, int value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
