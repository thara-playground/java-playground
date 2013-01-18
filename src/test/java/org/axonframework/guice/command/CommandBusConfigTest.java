/**
 * 
 */
package org.axonframework.guice.command;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.guice.command.CommandBusConfig;
import org.axonframework.guice.command.CommandHandlerSubscriber;
import org.axonframework.guice.command.CommandHandlingBinder;
import org.axonframework.guice.command.simple.SimpleCommandBusFactory;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import static junit.framework.Assert.*;

/**
 * @author t_hara
 *
 */
public class CommandBusConfigTest {
	
	@Target({ FIELD })
	@Retention(RUNTIME)
	@BindingAnnotation
	public @interface TestCommand{}

	private static boolean subscribing1;
	private static boolean subscribing2;
	
	@Inject
	@TestCommand
	CommandBus commandBus;
	
	@Test
	public void test() {
		Injector injector = Guice.createInjector(new TestModule());
		injector.injectMembers(this);
		assertNotNull(commandBus);
		assertTrue(subscribing1);
		assertTrue(subscribing2);
	}

	static class TestModule implements Module {
		public void configure(Binder binder) {
			CommandBusConfig commandConfig = CommandBusConfig.newBinder(binder, TestCommand.class);
			commandConfig.setup().toInstance(new SimpleCommandBusFactory());
			
			CommandHandlerSubscriber subscriber1 = new CommandHandlerSubscriber() {
				@Override
				public void subscribe(CommandBus commandBus) {
					subscribing1 = true;
				}
			};
			
			CommandHandlerSubscriber subscriber2 = new CommandHandlerSubscriber() {
				@Override
				public void subscribe(CommandBus commandBus) {
					subscribing2 = true;
				}
			};
			
			CommandHandlingBinder commandHandlings = CommandHandlingBinder.newBinder(binder, TestCommand.class);
			commandHandlings.addHandler().toInstance(subscriber1);
			commandHandlings.addHandler().toInstance(subscriber2);
		}
	}
}
