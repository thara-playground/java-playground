/**
 * 
 */
package org.zetta1985.core.cqrs.axon;

import org.axonframework.commandhandling.CommandCallback;

/**
 * @author t_hara
 *
 */
public class SimpleCommandCallback implements CommandCallback<Object> {

	@Override
	public void onSuccess(Object result) {
		;// do nothing
	}

	@Override
	public void onFailure(Throwable t) {
		if (t instanceof RuntimeException) {
			throw (RuntimeException)t;
		}
		
		Throwable cause = t.getCause();
		if (cause != null) {
			onFailure(cause);
		}
		
		throw new RuntimeException(t);
	}
}
