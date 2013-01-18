/**
 * 
 */
package org.zetta1985.sample.addressbook.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author t_hara
 *
 */
public class Contact {
	
	private long id;

	private Map<AddressType, Address> addresses = new HashMap<AddressType, Address>();
}
