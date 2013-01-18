package org.axonframework.guice.provider;

import java.util.List;
import java.util.Set;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandHandlerInterceptor;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.guice.CommandSubscriber;
import org.axonframework.unitofwork.DefaultUnitOfWorkFactory;
import org.axonframework.unitofwork.UnitOfWorkFactory;

import com.google.inject.Inject;

/**
 * @author t_hara
 */
public class DefaultCommandBusProvider implements CommandBusProvider {

	private Set<CommandSubscriber> subscribers;
	
	@Override
	public CommandBus get() {
		CommandBus commandBus = createCommandBus();
		
		if (subscribers != null) {
			for (CommandSubscriber commandSubscriber : subscribers) {
				commandSubscriber.subscribe(commandBus);
			}
		}
		
		
		return commandBus;
	}
	
	protected CommandBus createCommandBus() {
		SimpleCommandBus commandBus = new SimpleCommandBus(false);	// not register the MBeans
		
		List<CommandHandlerInterceptor> interceptors = getInterceptors();
		if (interceptors != null && interceptors.size() > 0) {
			commandBus.setInterceptors(interceptors);
		}
		
		UnitOfWorkFactory unitOfWorkFactory = getUnitOfWorkFactory();
		commandBus.setUnitOfWorkFactory(unitOfWorkFactory);
		
		return commandBus; 
	}
	
	protected UnitOfWorkFactory getUnitOfWorkFactory() {
		return new DefaultUnitOfWorkFactory();
	}
	
	protected List<CommandHandlerInterceptor> getInterceptors() {
		return null;
	}
	
	/**
	 * @param subscribers the subscribers to set
	 */
	@Inject
	public void setSubscribers(Set<CommandSubscriber> subscribers) {
		this.subscribers = subscribers;
	}
}
