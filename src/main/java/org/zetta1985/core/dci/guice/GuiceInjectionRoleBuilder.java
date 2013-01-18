/**
 * 
 */
package org.zetta1985.core.dci.guice;

import org.zetta1985.core.dci.Role;
import org.zetta1985.core.dci.impl.RoleBuilderImpl;

import com.google.inject.Injector;

/**
 * @author t_hara
 *
 */
public class GuiceInjectionRoleBuilder<T> extends RoleBuilderImpl<T> {

	private Injector injector;
	
	/**
	 * @param target
	 */
	public GuiceInjectionRoleBuilder(T target) {
		super(target);
	}

	@Override
	public <R extends Role<T>> R to(Class<R> type) {
		R role = super.to(type);
		injector.injectMembers(role);
		return role;
	}
	
	/**
	 * @param injector the injector to set
	 */
	public void setInjector(Injector injector) {
		this.injector = injector;
	}
}
