package org.axonframework.guice;


/**
 * @author t_hara
 *
 */
public interface Subscriber<T> {

	void subscribe(T target);
}
