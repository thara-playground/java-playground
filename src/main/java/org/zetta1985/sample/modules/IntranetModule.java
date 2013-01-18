/**
 * 
 */
package org.zetta1985.sample.modules;

import org.zetta1985.sample.intranet.interfaces.PageResource;
import org.zetta1985.sample.intranet.interfaces.impl.PageResourceImpl;

import com.google.inject.AbstractModule;

/**
 * @author t_hara
 *
 */
public class IntranetModule extends AbstractModule {

	@Override
	protected void configure() {
		
		// interfaces
		bind(PageResource.class).to(PageResourceImpl.class);
	}
}
