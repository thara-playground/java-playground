/**
 * 
 */
package org.axonframework.guice.command;

import java.lang.annotation.Annotation;

import org.axonframework.commandhandling.CommandBus;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Annotations;

/**
 * @author t_hara
 *
 */
abstract class CommandBinder {

	protected final String commandBusName;
	
	protected final Key<CommandBus> commandBusKey;
	
	protected final TypeLiteral<CommandHandlerSubscriber> subscriberType;
	
	CommandBinder(Class<? extends Annotation> annotationType) {
		
		commandBusKey = (annotationType != null) ?
				Key.get(CommandBus.class, annotationType) : Key.get(CommandBus.class);
		commandBusName = nameOf(commandBusKey);
		
		subscriberType = new TypeLiteral<CommandHandlerSubscriber>() {};
	}
	
	/**
	 * Returns the name the set should use. This is based on the annotation.
	 * If the annotation has an instance and is not a marker annotation, we
	 * ask the annotation for its toString. If it was a marker annotation or
	 * just an annotation type, we use the annotation's name. Otherwise, the
	 * name is the empty string.
	 */
	private String nameOf(Key<?> key) {
		Annotation annotation = key.getAnnotation();
		Class<? extends Annotation> annotationType = key.getAnnotationType();
		if (annotation != null && !Annotations.isMarker(annotationType)) {
			return key.getAnnotation().toString();
		} else if (key.getAnnotationType() != null) {
			return "@" + key.getAnnotationType().getName();
		} else {
			return "";
		}
	}
}
