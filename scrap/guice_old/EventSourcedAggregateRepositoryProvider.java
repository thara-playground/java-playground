package org.axonframework.guice;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.GenericEventSourcingRepository;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventStore;
import org.axonframework.guice.provider.RepositoryProvider;
import org.axonframework.repository.Repository;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author t_hara
 */
public class EventSourcedAggregateRepositoryProvider<T extends EventSourcedAggregateRoot> implements RepositoryProvider<T> {

	private static final Object lock = new Object();
	
	@Inject
	EventBus eventBus;
	
	@Inject
	EventStore eventStore;
	
	@Inject
	Injector injector;
	
	private final Class<T> aggregateClass;
	
	private volatile Repository<T> repository;
	
	/**
	 * @param aggregateClass
	 * @param snaoshotterTrigger
	 */
	public EventSourcedAggregateRepositoryProvider(Class<T> aggregateClass) {
		super();
		this.aggregateClass = aggregateClass;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Repository<T> get() {
		
		//TODO
		synchronized (lock) {
			if (repository == null) {
				EventSourcingRepository<T> repos = createEventSourcingRepository();
				repos.setEventBus(eventBus);
				repos.setEventStore(eventStore);
				
				SnapshotterTrigger snapshotterTrigger = injector.getInstance(SnapshotterTrigger.class);
				repos.setSnapshotterTrigger(snapshotterTrigger);
				repository = repos; 
			}
		}
		
		return repository;
	}

	/**
	 * @return
	 */
	protected EventSourcingRepository<T> createEventSourcingRepository() {
		return new GenericEventSourcingRepository<T>(aggregateClass);
	}

	@Inject
	public void setAggregateFactoriesHolder(AggregateFactoriesHolder aggregateFactoriesHolder) {
		aggregateFactoriesHolder.register(this);
	}
}
