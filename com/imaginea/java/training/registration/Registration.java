/**
 * 
 */
package com.imaginea.java.training.registration;
import com.imaginea.java.training.data.*;
import com.imaginea.java.training.domain.*;

public class Registration {
	private final PersonData personData;
	public   String password;

	public Registration(PersonData personalInfo, String password) {
		super();
		this.personData = personalInfo;
		this.password = password;
	}

	public PersonData getPersonalInfo() {
		return personData;
	}
	
}
