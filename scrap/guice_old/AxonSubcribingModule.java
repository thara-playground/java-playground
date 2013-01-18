package org.axonframework.guice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerAdapter;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventListener;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.guice.provider.RepositoryProvider;
import org.axonframework.repository.Repository;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * @author t_hara
 *
 */
public abstract class AxonSubcribingModule extends AbstractModule {

	protected Multibinder<CommandSubscriber> commandBinder;
	protected Multibinder<EventSubscriber> eventBinder;
	
	/**
	 * @return the commandBinder
	 */
	public Multibinder<CommandSubscriber> getCommandBinder() {
		if (commandBinder == null) {
			commandBinder = Multibinder.newSetBinder(binder(), CommandSubscriber.class);
		}
		return commandBinder;
	}
	
	/**
	 * @return the eventBinder
	 */
	public Multibinder<EventSubscriber> getEventBinder() {
		if (eventBinder == null) {
			eventBinder = Multibinder.newSetBinder(binder(), EventSubscriber.class);
		}
		return eventBinder;
	}
	
	protected <T extends AggregateRoot> void bindRepository(TypeLiteral<Repository<T>> typeLiteral, RepositoryProvider<T> provider) {
		bind(typeLiteral).toProvider(provider).in(Scopes.SINGLETON);
	}
	
	protected <T extends EventSourcedAggregateRoot> void bindRepository(TypeLiteral<Repository<T>> typeLiteral, Class<T> aggregateClass) {
		bindRepository(typeLiteral, createRepository(aggregateClass));
	}

	/**
	 * @param <T>
	 * @param aggregateClass
	 * @return
	 */
	private <T extends EventSourcedAggregateRoot> RepositoryProvider<T> createRepository(
			Class<T> aggregateClass) {
		EventSourcedAggregateRepositoryProvider<T> provider = new EventSourcedAggregateRepositoryProvider<T>(aggregateClass);
		return provider;
	}
	
	protected static AnnotationCommandHandlerAdapter createHandler(Object handler, CommandBus commandBus) {
		return new AnnotationCommandHandlerAdapter(handler, commandBus);
	}
	
	protected static EventListener createListener(Object handler, EventBus eventBus) {
		return new AnnotationEventListenerAdapter(handler, eventBus);
	}
}
