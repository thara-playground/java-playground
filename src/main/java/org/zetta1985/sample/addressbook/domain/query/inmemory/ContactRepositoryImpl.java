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

package org.zetta1985.sample.addressbook.domain.query.inmemory;

import java.util.ArrayList;
import java.util.List;

import org.zetta1985.sample.addressbook.domain.query.AddressEntry;
import org.zetta1985.sample.addressbook.domain.query.ContactEntry;
import org.zetta1985.sample.addressbook.domain.query.ContactRepository;

/**
 * @author Allard Buijze
 */
public class ContactRepositoryImpl implements ContactRepository {

	private List<ContactEntry> contactEntries = new ArrayList<ContactEntry>();
	private List<AddressEntry> addressEntries = new ArrayList<AddressEntry>();
	
    @Override
    public List<ContactEntry> findAllContacts() {
        return contactEntries;
    }

    @Override
    public List<AddressEntry> findAllAddressesForContact(String contactIdentifier) {
    	List<AddressEntry> results = new ArrayList<AddressEntry>();
    	
    	for (AddressEntry entry : addressEntries) {
    		if (entry.getIdentifier().equals(contactIdentifier)) {
    			results.add(entry);
    		}
    	}
    	
        return results;
    }

    @Override
    public List<AddressEntry> findAllAddressesInCityForContact(String name, String city) {
    	
    	List<AddressEntry> results = new ArrayList<AddressEntry>();
    	
    	for (AddressEntry entry : addressEntries) {
    		
    		if (entry.getName().startsWith(name)
    				&& entry.getCity().startsWith(city)) {
    			results.add(entry);
    		}
    	}
    	
        return results;
    }

    @Override
    public ContactEntry loadContactDetails(String contactIdentifier) {

    	for (ContactEntry entry : contactEntries) {
    		if (entry.getIdentifier().equals(contactIdentifier)) {
    			return entry;
    		}
    	}
    	
        return null;
    }
}
