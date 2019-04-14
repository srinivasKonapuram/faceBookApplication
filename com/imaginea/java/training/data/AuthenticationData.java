package com.imaginea.java.training.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public enum AuthenticationData {

	INSTANCE;
	private Map<String, String> usercredentials = new HashMap<>();
	private static final String MD5 = "MD5";

	public void storageForCheckingInput(String username, String password) {
		usercredentials.put(username, encrypt(password));
	}
	
	private String encrypt(String password) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(MD5);
			byte[] byteArray = digest.digest(password.getBytes());
			return new String(byteArray);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return password;
	}

	public boolean isUserNameAlreadyExists(final String username) {
		return usercredentials.containsKey(username);
	}

	public boolean isUserNamePasswordMatching(final String username, final String password) {

		if (isUserNameAlreadyExists(username))
			return encrypt(password).equals(usercredentials.get(username));

		return false;
	}

}
