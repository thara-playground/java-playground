package org.zetta1985.addressbook.domain;

import java.util.UUID;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.Repository;
import org.axonframework.unitofwork.UnitOfWork;
import org.axonframework.unitofwork.UnitOfWorkListenerAdapter;
import org.springframework.util.Assert;
import org.zetta1985.addressbook.api.Address;
import org.zetta1985.addressbook.api.ContactNameAlreadyTakenException;
import org.zetta1985.addressbook.api.command.ChangeContactNameCommand;
import org.zetta1985.addressbook.api.command.CreateContactCommand;
import org.zetta1985.addressbook.api.command.RegisterAddressCommand;
import org.zetta1985.addressbook.api.command.RemoveAddressCommand;
import org.zetta1985.addressbook.api.command.RemoveContactCommand;
import org.zetta1985.addressbook.query.ContactEntry;
import org.zetta1985.addressbook.query.ContactRepository;

import com.google.inject.Inject;

/**
 * @author t_hara
 * 
 */
public class ContactCommandHandler {

	private Repository<Contact> repository;

	private ContactNameRepository contactNameRepository;
	
	private ContactRepository contactRepository;
	
    /**
	 * @param repository
	 * @param contactNameRepository
	 * @param contactRepository
	 */
	@Inject
	public ContactCommandHandler(Repository<Contact> repository,
			ContactNameRepository contactNameRepository,
			ContactRepository contactRepository) {
		super();
		this.repository = repository;
		this.contactNameRepository = contactNameRepository;
		this.contactRepository = contactRepository;
	}

	/**
     * Creates a new contact based on the provided data. The provided user name is tested for uniqueness before
     * continuing.
     * <p/>
     * BEWARE
     * The mechanism to guarantee uniqueness is not a best practice for axon. This is a pretty expensive operation
     * especially when the number of contacts increases. It is better to make the client responsible for guaranteeing
     * unique contact names and make an explicit process to overcome the very rare situation where a duplicate contact
     * name is entered.
     *
     * @param command    CreateContactCommand object that contains the needed data to create a new contact
     * @param unitOfWork Unit of work for the current running thread
     */
	@CommandHandler
	public void handle(final CreateContactCommand command, UnitOfWork unitOfWork) {
		String newContactName = command.getNewContactName();

		Assert.hasText(newContactName, "Name may not be null");

		if (contactNameRepository.claimContactName(newContactName)) {
			registerUnitOfWorkListenerToCancelClaimingName(command.getNewContactName(), unitOfWork);
			String contactId = command.getContactId();
			if (contactId == null) contactId = UUID.randomUUID().toString();
			Contact contact = new Contact(new StringAggregateIdentifier(contactId), command.getNewContactName());
			repository.add(contact);
		}else {
			throw new RuntimeException();
		}
	}
	
    /**
     * Changes the provided data for the contact found based on the provided identifier
     * <p/>
     * An {@code AggregateNotFoundException} is thrown if the identifier does not represent a valid contact.
     *
     * @param command    ChangeContactNameCommand that contains the identifier and the data to be updated
     * @param unitOfWork Unit of work for the current running thread
     */
    @CommandHandler
    public void handle(final ChangeContactNameCommand command, UnitOfWork unitOfWork) {
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");
        Assert.notNull(command.getContactNewName(), "Name may not be null");
        if (contactNameRepository.claimContactName(command.getContactNewName())) {
            registerUnitOfWorkListenerToCancelClaimingName(command.getContactNewName(), unitOfWork);
            Contact contact = repository.load(new StringAggregateIdentifier(command.getContactId()));
            contact.changeName(command.getContactNewName());

            cancelClaimedContactName(command.getContactId(), unitOfWork);
        } else {
            throw new ContactNameAlreadyTakenException(command.getContactNewName());
        }
    }
    
    /**
     * Removes the contact belonging to the contactId as provided by the command.
     *
     * @param command    RemoveContactCommand containing the identifier of the contact to be removed
     * @param unitOfWork Unit of work for the current running thread
     */
    @CommandHandler
    public void handle(RemoveContactCommand command, UnitOfWork unitOfWork) {
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");
        Contact contact = repository.load(new StringAggregateIdentifier(command.getContactId()));
        contact.delete();

        cancelClaimedContactName(command.getContactId(), unitOfWork);
    }
    
    /**
     * Registers an address for the contact with the provided identifier. If the contact already has an address with the
     * provided type, this address will be updated. An {@code AggregateNotFoundException} is thrown if the provided
     * identifier does not exist.
     *
     * @param command RegisterAddressCommand that contains all required data
     */
    @CommandHandler
    public void handle(RegisterAddressCommand command) {
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");
        Assert.notNull(command.getAddressType(), "AddressType may not be null");
        Address address = new Address(command.getStreetAndNumber(), command.getZipCode(), command.getCity());
        Contact contact = repository.load(new StringAggregateIdentifier(command.getContactId()));
        contact.registerAddress(command.getAddressType(), address);
    }
    
    /**
     * Removes the address with the specified type from the contact with the provided identifier. If the identifier
     * does not exist, an {@code AggregateNotFoundException} is thrown. If the contact does not have an address with
     * specified type nothing happens.
     *
     * @param command RemoveAddressCommand that contains all required data to remove an address from a contact
     */
    @CommandHandler
    public void handle(RemoveAddressCommand command) {
        Assert.notNull(command.getContactId(), "ContactIdentifier may not be null");
        Assert.notNull(command.getAddressType(), "AddressType may not be null");
        Contact contact = repository.load(new StringAggregateIdentifier(command.getContactId()));
        contact.removeAddress(command.getAddressType());
    }

    private void cancelClaimedContactName(String contactIdentifier, UnitOfWork unitOfWork) {
        final ContactEntry contactEntry = contactRepository.loadContactDetails(contactIdentifier);
        unitOfWork.registerListener(new UnitOfWorkListenerAdapter() {
        	@Override
        	public void afterCommit() {
        		contactNameRepository.cancelContactName(contactEntry.getName());
        	}
        });
    }

	private void registerUnitOfWorkListenerToCancelClaimingName(
			final String name, UnitOfWork unitOfWork) {
		unitOfWork.registerListener(new UnitOfWorkListenerAdapter() {
			@Override
			public void onRollback(Throwable failureCause) {
				contactNameRepository.cancelContactName(name);
			}
		});
	}
}
