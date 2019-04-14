/**
 * 
 */
package com.imaginea.java.training.services;

import com.imaginea.java.training.data.AuthenticationData;

/**
 * @author srinivask
 *
 */
public class AuthenticationService {

	public boolean authenticate(String username, String password) {

		return AuthenticationData.INSTANCE.isUserNamePasswordMatching(username, password);
	}

	public boolean logout(String username) {
		return true;
	}
}
