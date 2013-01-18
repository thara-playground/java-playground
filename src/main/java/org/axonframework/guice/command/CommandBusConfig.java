/**
 * 
 */
package org.axonframework.guice.command;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandBus;

import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.spi.Toolable;

/**
 * @author t_hara
 * 
 */
public abstract class CommandBusConfig extends CommandBinder {

	public static CommandBusConfig newBinder(Binder binder) {
		return newBinder(binder, null);
	}
	
	public static <T extends CommandBus> CommandBusConfig newBinder(Binder binder, Class<? extends Annotation> annotationType) {
		binder = binder.skipSources(RealCommandBusBinder.class, CommandBusConfig.class);
		RealCommandBusBinder instance = new RealCommandBusBinder(binder, annotationType);
		binder.install(instance);
		return instance;
	}

	CommandBusConfig(Class<? extends Annotation> annotationType) {
		super(annotationType);
	}

	public abstract LinkedBindingBuilder<CommandBusFactory> setup();

	static class RealCommandBusBinder
			extends CommandBusConfig implements Provider<CommandBus>, Module {
		
		private final Binder binder;
		
		private final Key<CommandBusFactory> factoryKey;
		
		private final List<Binding<CommandHandlerSubscriber>> bindings = 
				new ArrayList<Binding<CommandHandlerSubscriber>>();
		
		private Binding<CommandBusFactory> factoryBinding;
		
		RealCommandBusBinder(Binder binder, Class<? extends Annotation> annotationType) {
			super(annotationType);
			this.binder = binder;
			factoryKey = (annotationType != null) ? 
					Key.get(CommandBusFactory.class, annotationType) : Key.get(CommandBusFactory.class);
		}

		@Override
		public LinkedBindingBuilder<CommandBusFactory> setup() {
			return binder.bind(factoryKey);
		}

		@Override
		public void configure(Binder binder) {
			binder.bind(commandBusKey).toProvider(this);
		}

		@Toolable
		@Inject
		void initialize(Injector injector) {
			List<Binding<CommandHandlerSubscriber>> bindings = new ArrayList<Binding<CommandHandlerSubscriber>>();

			List<Binding<CommandHandlerSubscriber>> bindingsByType = injector.findBindingsByType(subscriberType);
			for (Binding<CommandHandlerSubscriber> entry : bindingsByType) {
				if (keyMatches(entry.getKey())) {
					Binding<CommandHandlerSubscriber> binding = (Binding<CommandHandlerSubscriber>) entry;
					bindings.add(binding);
				}
			}

			factoryBinding = injector.getBinding(factoryKey);

			this.bindings.addAll(bindings);
		}

		@Override
		public CommandBus get() {
			List<CommandHandlerSubscriber> subscribers = new ArrayList<CommandHandlerSubscriber>();

			for (Binding<CommandHandlerSubscriber> binding : bindings) {
				CommandHandlerSubscriber subscriber = binding.getProvider()
						.get();
				subscribers.add(subscriber);
			}

			CommandBusFactory factory = factoryBinding.getProvider().get();
			CommandBus commandBus = factory.create();

			for (CommandHandlerSubscriber subscriber : subscribers) {
				subscriber.subscribe(commandBus);
			}

			return commandBus;
		}

		private boolean keyMatches(Key<?> key) {
			return key.getTypeLiteral().equals(subscriberType)
					&& key.getAnnotation() instanceof Subscriber
					&& ((Subscriber) key.getAnnotation()).setName().equals(commandBusName);
		}
	}
}
