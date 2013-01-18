/**
 * 
 */
package org.zetta1985.core.dci.guice;

import org.zetta1985.core.dci.RoleApplier;
import org.zetta1985.core.dci.RoleBuilder;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author t_hara
 *
 */
public class GuiceRoleApplier implements RoleApplier {

	@Inject
	Injector injector;
	
	@Override
	public <T> RoleBuilder<T> from(T target) {
		GuiceInjectionRoleBuilder<T> builder = new GuiceInjectionRoleBuilder<T>(target);
		builder.setInjector(injector);
		return builder;
	}

}
