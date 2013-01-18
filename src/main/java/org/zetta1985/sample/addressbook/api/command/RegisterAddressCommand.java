/*
 * Copyright (c) 2010. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.zetta1985.sample.addressbook.api.command;

import org.axonframework.util.Assert;
import org.zetta1985.sample.addressbook.domain.AddressType;

/**
 * <p>Registers the provided address with the contact as represented by the provided contact identifier. If the contact
 * already has an address with the provided type it is overwritten.</p>
 *
 * @author Jettro Coenradie
 */
public class RegisterAddressCommand extends AbstractOrderCommand {
    private AddressType addressType;
    private String streetAndNumber;
    private String zipCode;
    private String city;

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        Assert.notNull(addressType, "An Address type must be provided.");
        this.addressType = addressType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
