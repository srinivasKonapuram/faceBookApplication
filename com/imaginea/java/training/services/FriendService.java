/**
 * 
 */
package com.imaginea.java.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import com.imaginea.java.training.data.PredicateContainer;
import com.imaginea.java.training.data.UserRepository;
import com.imaginea.java.training.domain.Person;
import com.imaginea.java.training.services.exceptions.UserNameMustNotBeEmptyException;
import com.imaginea.java.training.services.exceptions.UsernameDoesntExistException;
import com.imaginea.java.training.social.network.SocialNetworkEndPoint;

/**
 * @author srinivask
 *
 */
public class FriendService {
	SocialNetworkEndPoint sc = new SocialNetworkEndPoint();
	Scanner scanner = new Scanner(System.in);
	private static FriendService INSTANCE = new FriendService();
	private static Map<String, String> myFriendRequests = new ConcurrentHashMap<>();
	private static Map<String, List<String>> mySentRequests = new ConcurrentHashMap<>();
	private static Map<String, List<String>> friendsListMap = new ConcurrentHashMap<>();
	List<String> friendsListOfUser = new ArrayList<>();
	List<String> friendsListOfFriend = new ArrayList<>();

	public Optional<List<String>> nameOfTheFriendsOf(final String username) {

		if (PredicateContainer.STRING_EMPTY_CHECK_PREDICATE.test(username))
			throw new UserNameMustNotBeEmptyException(" user identifier is needed");
		Optional<Person> personOptionalWrapper = UserRepository.getInstance().retrievePersonBasedOnUsername(username);
		if (!personOptionalWrapper.isPresent())
			throw new UsernameDoesntExistException(" user doesn't exit in the system");
		Person person = personOptionalWrapper.get();
		return Optional.ofNullable(person.namesOfTheFriends());

	}

	public Optional<List<Person>> friends(String username) {
		if (PredicateContainer.STRING_EMPTY_CHECK_PREDICATE.test(username))
			throw new UserNameMustNotBeEmptyException(" user identifier is needed");
		Optional<Person> personOptionalWrapper = UserRepository.getInstance().retrievePersonBasedOnUsername(username);
		if (!personOptionalWrapper.isPresent())
			throw new UsernameDoesntExistException(" user doesn't exit in the system");
		Person person = personOptionalWrapper.get();
		return Optional.ofNullable(person.friends());
	}

	public void sendingRequestForUser(String username) {
		if (PredicateContainer.STRING_EMPTY_CHECK_PREDICATE.test(username))
			throw new UserNameMustNotBeEmptyException(" user identifier is needed");
		System.out.println("Choose friend name from below list of friends");
		usersList();
		do {
			System.out.println("Enter friend name to request....");
			String friendName = scanner.next();
			int count = 0;
			System.out.println(" send requet(Y/N)");
			switch (scanner.next()) {
			case "Y":
				List<Person> personlist = UserRepository.getInstance().ListofUsers();
				for (Person person : personlist) {
					if (friendName.equals(person.getPersonalInfo().getUsername())) {
						count++;
						friendsListOfUser.add(friendName);
						myFriendRequests.put(friendName, username);

					}
				}

				System.out.println(" sent sucessfully to ...!" + friendName);
				break;
			case "N":
				sc.postLogin(username);
				break;
			default:
				System.out.println(" enter right choice");
				// sendRequest(username, friendName);
				break;
			}
			if (count == 0)
				System.out.println("user not exists");
			System.out.println("For next  friend Request (Y/N)");

		} while (scanner.next().equals("Y"));

		mySentRequests.put(username, friendsListOfUser);
		sc.postLogin(username);
	}

	public void accept(String username) {
		String friend = myFriendRequests.get(username);
		print(friend);
		System.out.println(" 1. TO Accept \n 2. TO reject ");
		int answer = scanner.nextInt();
		if (answer == 1) {
			friendsListOfUser.add(friend);
			friendsListMap.put(username, friendsListOfUser);
			friendsListOfFriend.add(username);
			friendsListMap.put(friend, friendsListOfFriend);
			System.out.println("you  Both are friends now....!");
			sc.postLogin(username);
		} else
			System.out.println("rejected");
	}

	private void print(String friend) {
		System.out.println(friend);

	}

	private static void usersList() {
		List<Person> personlist = UserRepository.getInstance().ListofUsers();
		for (Person person : personlist) {
			System.out.println(person.getPersonalInfo().getUsername());
		}

	}

	public List<String> retriveMyfriends(String username) {
		if (PredicateContainer.STRING_EMPTY_CHECK_PREDICATE.test(username))
			throw new UserNameMustNotBeEmptyException(" YOU   have no  friends YET");
		return friendsListMap.get(username);

	}

	public List<String> MySendRequests(String username) {
		return mySentRequests.get(username);
	}

	public static FriendService getInstance() {
		return INSTANCE();
	}

	private static FriendService INSTANCE() {
		return INSTANCE();
	}

}
