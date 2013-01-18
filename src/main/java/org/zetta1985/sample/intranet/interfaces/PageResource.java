/**
 * 
 */
package org.zetta1985.sample.intranet.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.thymeleaf.context.IContext;
import org.zetta1985.core.jaxrs.Template;

/**
 * @author t_hara
 *
 */
@Path("/intranet")
public interface PageResource {

	@GET
	@Path("/index.html")
	@Template("/intranet/index.html")
	public IContext showTopPage();
}
