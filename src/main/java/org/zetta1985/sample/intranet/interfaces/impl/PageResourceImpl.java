/**
 * 
 */
package org.zetta1985.sample.intranet.interfaces.impl;

import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.zetta1985.sample.intranet.interfaces.PageResource;

/**
 * @author t_hara
 *
 */
public class PageResourceImpl implements PageResource {

	@Override
	public IContext showTopPage() {
		Context context = new Context();
		context.setVariable("name", "Tomochika Hara");
		return context;
	}
}
