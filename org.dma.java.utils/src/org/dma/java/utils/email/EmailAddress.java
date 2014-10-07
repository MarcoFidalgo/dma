/*******************************************************************************
 * 2008-2014 Public Domain
 * Contributors
 * Marco Lopes (marcolopes@netc.pt)
 *******************************************************************************/
package org.dma.java.utils.email;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailAddress {

	private final String email;
	private String name;
	private InternetAddress address;

	public EmailAddress(String email){
		this(email,"");
	}

	public EmailAddress(String email, String name){
		this.email=email;
		this.name=name;
		try{
			address=new InternetAddress(email);
		}catch(AddressException e){}
	}


	public boolean isValid() {
		try{
			address.validate();
			return true;

		}catch(AddressException e){
			System.out.println(e.getMessage());
		}catch(Exception e){}

		return false;
	}



	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + " <"+email+">";
	}



	/*
	 * Getters and setters
	 */
	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public InternetAddress getAddress() {
		return address;
	}


}
