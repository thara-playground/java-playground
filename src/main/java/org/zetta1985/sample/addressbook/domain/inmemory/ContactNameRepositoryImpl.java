/**
 * 
 */
package org.zetta1985.sample.addressbook.domain.inmemory;

import org.zetta1985.sample.addressbook.domain.ContactNameRepository;

/**
 * @author t_hara
 *
 */
public class ContactNameRepositoryImpl implements ContactNameRepository {

	@Override
	public boolean claimContactName(String contactName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelContactName(String contactName) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean vacantContactName(String contactName) {
		// TODO Auto-generated method stub
		return false;
	}

}
