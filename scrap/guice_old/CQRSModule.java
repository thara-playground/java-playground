package org.zetta1985;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandHandlerInterceptor;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventCountSnapshotterTrigger;
import org.axonframework.eventsourcing.SnapshotterTrigger;
import org.axonframework.eventstore.EventSerializer;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.eventstore.XStreamEventSerializer;
import org.axonframework.guice.AggregateFactoriesHolder;
import org.axonframework.guice.provider.DefaultCommandBusProvider;
import org.axonframework.guice.provider.DefaultEventBusProvider;
import org.zetta1985.framework.CommandDispatcher;
import org.zetta1985.framework.appengine.AppEngineXStream;
import org.zetta1985.framework.axon.DefaultCommandDispatcher;
import org.zetta1985.framework.axon.appengine.AppEngineEventBus;
import org.zetta1985.framework.axon.appengine.AppEngineEventStore;
import org.zetta1985.framework.axon.appengine.TaskQueueSnapshotter;
import org.zetta1985.framework.axon.interceptor.MethodInterceptorAdapter;
import org.zetta1985.framework.transaction.Slim3SessionManager;
import org.zetta1985.framework.transaction.Slim3TransactionInterceptor;
import org.zetta1985.framework.transaction.Slim3TransactionManager;
import org.zetta1985.framework.validation.ArgumentsValidationInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.core.util.ClassLoaderReference;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author t_hara
 *
 */
public class CQRSModule extends AbstractModule {

	private String snapshotTaskUrl = "/service/task/snapshot";
	
	/**
	 * @inheritDoc
	 */
	@Override
	protected void configure() {
		XStream xStream = new AppEngineXStream(
                new PureJavaReflectionProvider(),
                new XppDriver(),
                new ClassLoaderReference(new CompositeClassLoader()));
		
		EventSerializer xStreamSerializer = new XStreamEventSerializer(xStream);
		bind(EventSerializer.class).toInstance(xStreamSerializer);
		
		AppEngineEventStore eventStore = new AppEngineEventStore(xStreamSerializer);
		bind(EventStore.class).toInstance(eventStore);
		bind(SnapshotEventStore.class).toInstance(eventStore);
		
		bind(AggregateFactoriesHolder.class).in(Scopes.SINGLETON);
		
		bind(CommandBus.class).toProvider(AppEngineCommandBusProvider.class).in(Scopes.SINGLETON);
		bind(EventBus.class).toProvider(AppEngineEventBusProvider.class).in(Scopes.SINGLETON);
	}

	@Provides
	@Singleton
	public TaskQueueSnapshotter provideSnapshotter(SnapshotEventStore eventStore, AggregateFactoriesHolder aggregateFactoryHolders) {
		TaskQueueSnapshotter snapshotter = new TaskQueueSnapshotter(snapshotTaskUrl, eventStore, aggregateFactoryHolders);
		return snapshotter;
	}

	@Provides
	@Singleton
	public SnapshotterTrigger providedSnapshotterTrigger(TaskQueueSnapshotter snapshotter) {
		EventCountSnapshotterTrigger snapshotterTrigger = new EventCountSnapshotterTrigger();
		snapshotterTrigger.setSnapshotter(snapshotter);
		snapshotterTrigger.setTrigger(5);
		return snapshotterTrigger;
	}
	
	@Provides
	@Singleton	
	public CommandDispatcher providedCommandDispacher(CommandBus commandBus) {
		CommandDispatcher commandDispacher = new DefaultCommandDispatcher(commandBus);
		return commandDispacher;
	}
	
	public static class AppEngineCommandBusProvider extends DefaultCommandBusProvider {

		@Inject
		ArgumentsValidationInterceptor validationInterceptor;
		@Inject
		Slim3SessionManager sessionManager;
		@Inject
		Slim3TransactionManager transactionManager;
		
		@Override
		protected List<CommandHandlerInterceptor> getInterceptors() {
			List<CommandHandlerInterceptor> interceptors = new ArrayList<CommandHandlerInterceptor>();
			interceptors.add(new MethodInterceptorAdapter(validationInterceptor));
			interceptors.add(new Slim3TransactionInterceptor(transactionManager, sessionManager));
			
			return interceptors;
		}
	}
	
	public static class AppEngineEventBusProvider extends DefaultEventBusProvider {
		
		@Inject
		EventSerializer eventSerializer;
		
		@Override
		protected EventBus getEventBus() {
			EventBus eventBus = new AppEngineEventBus(eventSerializer);
			return eventBus;
		}
	}
}
