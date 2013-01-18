/**
 * 
 */
package org.axonframework.guice.command.simple;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandHandlerInterceptor;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.guice.command.CommandBusFactory;
import org.axonframework.unitofwork.DefaultUnitOfWorkFactory;
import org.axonframework.unitofwork.UnitOfWorkFactory;

/**
 * @author t_hara
 *
 */
public class SimpleCommandBusFactory implements
		CommandBusFactory {

	private final List<CommandHandlerInterceptor> interceptors = new ArrayList<CommandHandlerInterceptor>();
	
	private UnitOfWorkFactory unitOfWorkFactory;

	@Override
	public CommandBus create() {
		SimpleCommandBus commandBus = createInstance();
		configure(commandBus);
		return commandBus;
	}

	/**
	 * @param commandBus
	 */
	protected void configure(SimpleCommandBus commandBus) {
		if (!this.interceptors.isEmpty()) {
			commandBus.setInterceptors(this.interceptors);
		}
		
		UnitOfWorkFactory unitOfWorkFactory = getUnitOfWorkFactory();
		if (unitOfWorkFactory != null) {
			commandBus.setUnitOfWorkFactory(unitOfWorkFactory);
		}
	}

	/**
	 * @return
	 */
	protected SimpleCommandBus createInstance() {
		return new SimpleCommandBus(false);
	}

	public void setup(SimpleCommandBus commandBus) {

		if (!this.interceptors.isEmpty()) {
			commandBus.setInterceptors(this.interceptors);
		}
		
		UnitOfWorkFactory unitOfWorkFactory = getUnitOfWorkFactory();
		if (unitOfWorkFactory != null) {
			commandBus.setUnitOfWorkFactory(unitOfWorkFactory);
		}
	}
	
	/**
	 * @return the interceptors
	 */
	public List<CommandHandlerInterceptor> getInterceptors() {
		return new ArrayList<CommandHandlerInterceptor>(interceptors);
	}
	
	/**
	 * @param interceptors the interceptors to set
	 */
	public void addInterceptor(CommandHandlerInterceptor interceptor) {
		if (interceptor == null) throw new IllegalArgumentException("interceptor is null.");
		this.interceptors.add(interceptor);
	}
	
	/**
	 * @return the unitOfWorkFactory
	 */
	public UnitOfWorkFactory getUnitOfWorkFactory() {
		
		if (unitOfWorkFactory == null) {
			unitOfWorkFactory = new DefaultUnitOfWorkFactory();
		}
		
		return unitOfWorkFactory;
	}
	
	/**
	 * @param unitOfWorkFactory the unitOfWorkFactory to set
	 */
	public void setUnitOfWorkFactory(UnitOfWorkFactory unitOfWorkFactory) {
		if (unitOfWorkFactory == null) throw new IllegalArgumentException("unitOfWorkFactory is null.");
		this.unitOfWorkFactory = unitOfWorkFactory;
	}
}
