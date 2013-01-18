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

package org.zetta1985.sample.addressbook.domain.query;

import org.zetta1985.sample.addressbook.api.event.AddressAddedEvent;
import org.zetta1985.sample.addressbook.api.event.AddressChangedEvent;
import org.zetta1985.sample.addressbook.api.event.AddressRemovedEvent;
import org.zetta1985.sample.addressbook.api.event.ContactCreatedEvent;
import org.zetta1985.sample.addressbook.api.event.ContactDeletedEvent;
import org.zetta1985.sample.addressbook.api.event.ContactNameChangedEvent;

/**
 * @author Allard Buijze
 */
public interface AddressTableUpdater {

    void handleContactCreatedEvent(ContactCreatedEvent event);

    void handleContactNameChangedEvent(ContactNameChangedEvent event);
    
    void handleContactDeletedEvent(ContactDeletedEvent event);

    public void handleAddressDeletedEvent(AddressRemovedEvent event);

    void handleAddressChangedEvent(AddressChangedEvent event);

    void handleAddressAddedEvent(AddressAddedEvent event);

}
