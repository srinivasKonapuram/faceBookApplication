package com.imaginea.java.training.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.imaginea.java.training.domain.Person;
import com.imaginea.java.training.domain.PersonData;

public class UserRepository {

	private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());
	private static Map<String, Person> usersMap = new ConcurrentHashMap<>();
	private static Map<Person, String> usersPost = new HashMap<>();
	private static Map<String, List<String>> userRequest = new HashMap<>();
	private static UserRepository INSTANCE = new UserRepository();

	public synchronized void addUser(final Person person) {
		LOGGER.info("Adding new user to the user repository." + person.toString());
		usersMap.put(person.uniqueIdentifier(), person);
	}

	public synchronized void addpost(final Person person, String message) {
		usersPost.put(person.getInstacne(), message);
	}

	public synchronized static void addRequest(String username, List<String> friendlist) {
		userRequest.put(username, friendlist);
	}

	private UserRepository() {
		super();
	}

	public static UserRepository getInstance() {
		return INSTANCE;
	}

	public synchronized Collection<Person> availableUsers() {
		return Collections.unmodifiableCollection(usersMap.values());
	}

	public boolean removeUser(final String email) {
		LOGGER.log(Level.INFO, "Removing user with email:" + email);

		if (isEmpty(email))
			throw new RuntimeException("Empty email - Can not remove user.");

		synchronized (usersMap) {
			if (usersMap.containsKey(email))
				usersMap.remove(email);
		}

		return false;
	}

	private boolean isEmpty(final String incomingStringvalue) {
		return PredicateContainer.STRING_EMPTY_CHECK_PREDICATE.test(incomingStringvalue);
	}

	public Optional<Person> retrievePersonBasedOnUsername(final String username) {
		if (isEmpty(username))
			throw new RuntimeException("Empty username - Can not find user.");

		synchronized (this) {
			for (Person person : usersMap.values()) {
				if (person.isUserNameMatchingTo(username))
					return Optional.of(person);
			}
		}

		return Optional.empty();
	}

	public synchronized int totalNumberOfUsers() {
		return usersMap.size();

	}

	public synchronized List<Person> ListofUsers() {
		Iterator<Person> itr = usersMap.values().iterator();
		List<Person> person = new ArrayList<>();
		while (itr.hasNext()) {
			person.add(itr.next());
		}
		return person;
	}

}
