/**
 * 
 */
package org.zetta1985.sample.addressbook.domain;

/**
 * @author t_hara
 *
 */
public interface ContactNameRepository {

    /**
     * Claims the provided contact name, if the name is not available anymore false is returned
     *
     * @param contactName String containing the contact name to claim
     * @return boolean indicating whether the claim was successful
     */
    boolean claimContactName(String contactName);

    /**
     * Release the claim for the provided name
     *
     * @param contactName String containing the name to release the claim for
     */
    void cancelContactName(String contactName);

    /**
     * Returns true if the contact name is available, and false if it has been taken.
     *
     * @param contactName String containing the contact name to check for vacancy
     * @return true if the provided contact name has not been taken, false otherwise
     */
    boolean vacantContactName(String contactName);
}
