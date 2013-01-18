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

package org.zetta1985.sample.addressbook.api.event;

/**
 * @author Allard Buijze
 */
public class ContactCreatedEvent extends AbstractContactDomainEvent {

    private final String name;

    public ContactCreatedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
