/**
 * 
 */
package org.axonframework.guice.command;

import java.lang.annotation.Annotation;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author t_hara
 * 
 */
class RealSubscriber implements Subscriber {

	private static final AtomicInteger nextUniqueId = new AtomicInteger(1);

	private final int uniqueId;
	private final String setName;

	RealSubscriber(String setName) {
		uniqueId = nextUniqueId.getAndIncrement();
		this.setName = setName;
	}

	public String setName() {
		return setName;
	}

	public int uniqueId() {
		return uniqueId;
	}

	public Class<? extends Annotation> annotationType() {
		return Subscriber.class;
	}

	@Override
	public String toString() {
		return "@" + Subscriber.class.getName() + "(setName=" + setName
				+ ",uniqueId=" + uniqueId + ")";
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Subscriber
				&& ((Subscriber) o).setName().equals(setName())
				&& ((Subscriber) o).uniqueId() == uniqueId();
	}

	@Override
	public int hashCode() {
		return 127 * ("setName".hashCode() ^ setName.hashCode()) + 127
				* ("uniqueId".hashCode() ^ uniqueId);
	}
}
