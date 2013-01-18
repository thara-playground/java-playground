/**
 * 
 */
package org.zetta1985.transfer;

import org.zetta1985.core.dci.RoleApplier;
import org.zetta1985.core.dci.impl.RoleApplierImpl;

/**
 * @author t_hara
 *
 */
public class Facade {

	RoleApplier roleApplier = new RoleApplierImpl();
	
	public void transfar(Account src, Account target, int amount) {
		
		SourceAccount source = roleApplier.from(src).to(SourceAccount.class);
		TargetAccount targetAccount = roleApplier.from(target).to(TargetAccount.class);
		
		source.transferTo(targetAccount, amount);
	}
}
