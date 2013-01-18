/*
 * Copyright (c) 2010. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.zetta1985.sample.addressbook.domain.axon;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.zetta1985.sample.addressbook.api.event.AddressAddedEvent;
import org.zetta1985.sample.addressbook.api.event.AddressChangedEvent;
import org.zetta1985.sample.addressbook.api.event.AddressRegisteredEvent;
import org.zetta1985.sample.addressbook.api.event.AddressRemovedEvent;
import org.zetta1985.sample.addressbook.api.event.ContactCreatedEvent;
import org.zetta1985.sample.addressbook.api.event.ContactDeletedEvent;
import org.zetta1985.sample.addressbook.api.event.ContactNameChangedEvent;
import org.zetta1985.sample.addressbook.domain.Address;
import org.zetta1985.sample.addressbook.domain.AddressType;

/**
 * <p>The Aggregate root component of the sample application. This component handles all contact as well as address
 * domain events.</p>
 *
 * @author Allard Buijze
 */
class Contact extends AbstractAnnotatedAggregateRoot {

    private Map<AddressType, Address> addresses = new HashMap<AddressType, Address>();

    public Contact(AggregateIdentifier identifier, String name) {
        super(identifier);
        apply(new ContactCreatedEvent(name));
    }

    public Contact(AggregateIdentifier identifier) {
        super(identifier);
    }

    /**
     * Register the provided address with the provided type. If a contact already has an address of the provided type,
     * that address is changed.
     *
     * @param type    AddressType of the address to add or change
     * @param address Address to add or change
     */
    public void registerAddress(AddressType type, Address address) {
        if (addresses.containsKey(type)) {
            apply(new AddressChangedEvent(type, address));
        } else {
            apply(new AddressAddedEvent(type, address));
        }
    }

    /**
     * Removes the address with the provided type if it exists.
     *
     * @param type AddressType of the address that needs to be removed
     */
    public void removeAddress(AddressType type) {
        if (addresses.containsKey(type)) {
            apply(new AddressRemovedEvent(type));
        }
    }

    /**
     * Change the name of the contact
     *
     * @param name String containing the new name
     */
    public void changeName(String name) {
        apply(new ContactNameChangedEvent(name));
    }

    public void delete() {
        apply(new ContactDeletedEvent());
    }

    @EventHandler
    protected void handleContactCreatedEvent(ContactCreatedEvent event) {
    }

    @EventHandler
    protected void handleContactNameChangedEvent(ContactNameChangedEvent event) {
    }

    @EventHandler
    protected void handleAddressRegisteredEvent(AddressRegisteredEvent event) {
        addresses.put(event.getType(), event.getAddress());
    }

    @EventHandler
    protected void handleAddressRemovedEvent(AddressRemovedEvent event) {
        addresses.remove(event.getType());
    }
}
