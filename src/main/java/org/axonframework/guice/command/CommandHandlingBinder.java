/**
 * 
 */
package org.axonframework.guice.command;

import java.lang.annotation.Annotation;

import org.axonframework.guice.command.CommandBusConfig.RealCommandBusBinder;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.binder.LinkedBindingBuilder;

/**
 * @author t_hara
 *
 */
public abstract class CommandHandlingBinder extends CommandBinder {

	public static CommandHandlingBinder newBinder(Binder binder) {
		return newBinder(binder, null);
	}
	
	public static CommandHandlingBinder newBinder(Binder binder, Class<? extends Annotation> annotationType) {
		binder = binder.skipSources(RealCommandBusBinder.class, CommandBusConfig.class);
		RealCommandHandlingBinder instance = new RealCommandHandlingBinder(binder, annotationType);
		return instance;
	}
	
	CommandHandlingBinder(Class<? extends Annotation> annotationType) {
		super(annotationType);
	}

	public abstract LinkedBindingBuilder<CommandHandlerSubscriber> addHandler();

	static class RealCommandHandlingBinder extends CommandHandlingBinder {
		
		private final Binder binder;
		
		RealCommandHandlingBinder(Binder binder, Class<? extends Annotation> annotationType) {
			super(annotationType);
			this.binder = binder;
		}

		@Override
		public LinkedBindingBuilder<CommandHandlerSubscriber> addHandler() {
			return binder.bind(Key.get(CommandHandlerSubscriber.class, new RealSubscriber(commandBusName)));
		}
	}
	
}
