package org.axonframework.guice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.guice.provider.CommandBusProvider;
import org.axonframework.guice.provider.EventBusProvider;
import org.axonframework.guice.provider.SnapshotterProvider;
import org.axonframework.guice.provider.SnapshotterTriggerProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * @author t_hara
 */
public abstract class AxonConfigModule extends AbstractModule {

	/**
	 * @inherited
	 */
	@Override
	protected void configure() {
		EventStore eventStore = createEventStore();

		bind(EventStore.class).toInstance(eventStore);
		if (eventStore instanceof SnapshotEventStore) {
			bind(SnapshotEventStore.class).toInstance((SnapshotEventStore)eventStore);
		}
		
		bind(AggregateFactoriesHolder.class).in(Scopes.SINGLETON);
		
		bind(CommandBus.class).toProvider(getCommandBusProvider()).in(Scopes.SINGLETON);
		bind(EventBus.class).toProvider(getEventBusProvider()).in(Scopes.SINGLETON);
		
		SnapshotterProvider snapshotterProvider = getSnapshotterProvider();
		if (snapshotterProvider == null) {
			bind(Snapshotter.class).toProvider(snapshotterProvider).in(Scopes.SINGLETON);
			bind(SnapshotterTrigger.class).toProvider(getSnapshotterTriggerProvider()).in(Scopes.SINGLETON);
		}
	}
	
	protected abstract EventStore createEventStore();
	
	protected abstract CommandBusProvider getCommandBusProvider();
	
	protected abstract EventBusProvider getEventBusProvider();
	
	protected SnapshotterProvider getSnapshotterProvider() {
		return null;
	} 
	
	protected SnapshotterTriggerProvider getSnapshotterTriggerProvider() {
		return null;
	}
}
