/**
 * 
 */
package org.zetta1985.transfer;

import org.zetta1985.core.dci.Role;

/**
 * @author t_hara
 *
 */
public class TargetAccount implements Role<Account> {

	private Account account;
	
	@Override
	public Role<Account> apply(Account t) {
		this.account = t;
		return this;
	}
	
	public void deposit(int amount) {
		account.setBalance(account.getBalance() + amount);
	}
}
