package org.axonframework.guice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.guice.provider.RepositoryProvider;
import org.axonframework.repository.Repository;

import com.google.inject.Injector;

/**
 * @author t_hara
 */
public class AggregateFactoriesHolder {

	private final Map<String, AggregateFactory<?>> aggregateFactories 
		= new ConcurrentHashMap<String, AggregateFactory<?>>();
	
	private List<RepositoryProvider<? extends EventSourcedAggregateRoot>> providers;

	Injector injector;
	
	public AggregateFactoriesHolder() {
		this.providers = Collections.synchronizedList(
				new ArrayList<RepositoryProvider<? extends EventSourcedAggregateRoot>>());
	}
	
	protected void register(RepositoryProvider<? extends EventSourcedAggregateRoot> provider) {
		providers.add(provider);
	}
	
	@SuppressWarnings("rawtypes")
	public AggregateFactory<?> getFactory(String typeIdentifier) {
		synchronized (aggregateFactories) {
			if (aggregateFactories.isEmpty()) {
				for (RepositoryProvider<? extends EventSourcedAggregateRoot> provider : providers) {
					Repository<? extends EventSourcedAggregateRoot> repository = provider.get();
					if (repository instanceof AggregateFactory) {
						AggregateFactory factory = (AggregateFactory)repository;
						aggregateFactories.put(factory.getTypeIdentifier(), factory);
					}
				}
			}
		}
		return aggregateFactories.get(typeIdentifier);
	}
}
