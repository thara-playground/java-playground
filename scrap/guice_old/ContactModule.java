package org.zetta1985.addressbook;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerAdapter;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventListener;
import org.axonframework.guice.AxonSubcribingModule;
import org.axonframework.guice.CommandSubscriber;
import org.axonframework.guice.EventSubscriber;
import org.axonframework.repository.Repository;
import org.zetta1985.addressbook.domain.Contact;
import org.zetta1985.addressbook.domain.ContactCommandHandler;
import org.zetta1985.addressbook.domain.ContactNameRepository;
import org.zetta1985.addressbook.domain.ContactNameRepositoryImpl;
import org.zetta1985.addressbook.query.ContactRepository;
import org.zetta1985.addressbook.query.ContactRepositoryImpl;
import org.zetta1985.addressbook.query.listener.AddressTableUpdater;
import org.zetta1985.framework.axon.appengine.TaskQueueEventListener;

import com.google.inject.Inject;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;

/**
 * @author t_hara
 * 
 */
public class ContactModule extends AxonSubcribingModule {

	private static String taskQueueUrl = "/service/task/publishEvent";
	
	/**
	 * @inheritDoc
	 */
	@Override
	protected void configure() {

		bindRepository(new TypeLiteral<Repository<Contact>>() {}, Contact.class);
		
		bind(ContactNameRepository.class).to(ContactNameRepositoryImpl.class);
		
		getCommandBinder().addBinding().to(ContactCommandSubscriber.class).in(Scopes.SINGLETON);
		getEventBinder().addBinding().to(ContactEventSubscriber.class).in(Scopes.SINGLETON);
		
		// query
		bind(ContactRepository.class).to(ContactRepositoryImpl.class);
		
		bind(AddressTableUpdater.class);
	}
	
	public static class ContactCommandSubscriber implements CommandSubscriber {

		@Inject
		ContactCommandHandler contactCommandHandler;

		@Override
		public void subscribe(CommandBus commandBus) {
			AnnotationCommandHandlerAdapter handler = createHandler(contactCommandHandler, commandBus);
			handler.subscribe();
		}
	}

	public static class ContactEventSubscriber implements EventSubscriber {

		@Inject
		AddressTableUpdater addressTableUpdater;
		
		/**
		 * @inheritDoc
		 */
		@Override
		public void subscribe(EventBus eventBus) {
			EventListener listener = new TaskQueueEventListener(addressTableUpdater, eventBus, taskQueueUrl);
			eventBus.subscribe(listener);
		}
	}
}
