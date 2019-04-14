package com.imaginea.java.training.social.network;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import com.imaginea.java.training.data.*;
import com.imaginea.java.training.domain.AcademicDetails;
import com.imaginea.java.training.domain.Address;
import com.imaginea.java.training.domain.Gender;
import com.imaginea.java.training.domain.Person;
import com.imaginea.java.training.domain.PersonData;
import com.imaginea.java.training.domain.WorkExperience;
import com.imaginea.java.training.registration.Registration;
import com.imaginea.java.training.services.AuthenticationService;
import com.imaginea.java.training.services.FriendService;
import com.imaginea.java.training.services.PostService;
import com.imaginea.java.training.services.exceptions.UsernameDoesntExistException;

public class SocialNetworkEndPoint {
	static Scanner scanner = new Scanner(System.in);
	private static AuthenticationService authenticationService = new AuthenticationService();

	public static void main(String args[]) {

		while (true) {
			chooseTheOption();
			operationOnInput();
		}
	}

	private static void chooseTheOption() {

		System.out.println(" choose option from the menu....!");
		System.out.println(" 1. Login \n 2. Registration");
	}

	private static void operationOnInput() {
		int input = scanner.nextInt();
		switch (input) {
		case 1:
			loginServices();
			break;
		case 2:
			registrationProcess();
			break;
		}
	}

	private static void loginServices() {
		System.out.println(" please enter the uername and password");
		String username = scanner.next();
		String password = scanner.next();
		if (authenticationService.authenticate(username, password)) {
			postLogin(username);
		} else {

			System.out.println("  Login failed..!");
		}
	}

	public static void postLogin(String username) {
		System.out.println("Welocome....!");

		System.out.println("Browse through feed  OR chose one of the options from below.");
		System.out.println("1. Home");
		System.out.println("2.Send Requests");
		System.out.println("3. Friends");
		System.out.println("4. Feed ");
		System.out.println("5.add Post");
		System.out.println("6. profile ");
		System.out.println("7. showfriendRequest");
		System.out.println("8. Logout");
		switch (scanner.nextInt()) {
		case 1:
			defaultHome(username);
			break;
		case 2:
			inputForSendFriendRequest(username);
			break;
		case 3:
			showAllFriends(username);
			break;
		case 4:
			feed(username);
			break;
		case 5:
			addingPost(username);
			break;
		case 6:
			ViewProfile(username);
			break;
		case 7:
			acceptRequest(username);
			break;
		case 8:
			logout(username);
			break;
		default:
			System.out.println(" enter valid choice");
			break;
		}
	}

	private static void feed(String username) {
		PostService mypost = new PostService();
		System.out.println(" post of " + username);
		print(mypost.retrivePost(username));
		postLogin(username);
	}

	private static void acceptRequest(String username) {
		FriendService friendService = new FriendService();
		friendService.accept(username);
	}

	private static void usersList() {
		List<Person> personlist = UserRepository.getInstance().ListofUsers();
		for (Person person : personlist) {
			System.out.println(person.getPersonalInfo().getUsername());
		}

	}

	private static void ViewProfile(String username) {

		Optional<Person> personOptionalWrapper = UserRepository.getInstance().retrievePersonBasedOnUsername(username);
		System.out.println("firstname is: " + personOptionalWrapper.get().getPersonalInfo().getFirstName());
		System.out.println("lastname is: " + personOptionalWrapper.get().getPersonalInfo().getLastName());
		System.out.println("Email is: " + personOptionalWrapper.get().getPersonalInfo().getEmail());
		System.out.println("Phone is: " + personOptionalWrapper.get().getPersonalInfo().getPhone());
		System.out.println("DataOfBrith is: " + personOptionalWrapper.get().getPersonalInfo().getDateOfBirth());
		System.out.println("To go  main menu.(Y/N)");
		switch (scanner.next()) {
		case "Y":
			postLogin(username);
			break;
		case "N":
			ViewProfile(username);
		}
	}

	private static void logout(String username) {
		if (authenticationService.logout(username)) {
			System.out.println(" logout sucess.!");

		} else {
			System.out.println(" logout failed....!");
		}

	}
	private static void showAllFriends(String username) {
		FriendService friendService = new FriendService();
		friendService.retriveMyfriends(username);
		print(friendService.toString());
		postLogin(username);
	}

	private static void print(String string) {
		System.out.println(string);

	}

	private static void print(List<String> strings) {
		System.out.println(strings);

	}

	private static void inputForSendFriendRequest(String username) {
		FriendService myService = new FriendService();
		myService.sendingRequestForUser(username);
	}

	private static void addingPost(String username) {
		PostService mypost = new PostService();
		mypost.addingPost(username);
		System.out.println(" sucess...");
		postLogin(username);
	}
	public static void defaultHome(String username) {
		if (username == null)
			throw new RuntimeException("give username");
		List<String> ListOfFriends = Person.retrivefriends(username);
		for (String list : ListOfFriends) {
			String usernameOffriends = list;
			List<String> postsOfFriends = Person.retrivePosts(usernameOffriends);
			print(postsOfFriends);
		}

	}
	private static void registrationProcess() {
		System.out.println("Registration process is initiated. Please provide below details");
		System.out.println("Phone:");
		String phone = scanner.next();
		System.out.println("E-Mail:");
		String email = scanner.next();
		System.out.println("firstName:");
		String firstName = scanner.next();
		System.out.println("lastName:");
		String lastName = scanner.next();
		System.out.println("Gender M/F:");
		String userGenderInput = scanner.next();
		Gender gender = Gender.MALE;
		if (userGenderInput.equals("F"))
			gender = Gender.FEMALE;
		System.out.println("Date of Birth (dd/mm/YYYY):");
		String dob = scanner.next();
		System.out.println("Username:");
		String username = scanner.next();
		System.out.println("password:");
		String password = scanner.next();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		Date dateOfBirth = null;
		try {
			dateOfBirth = dateFormat.parse(dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PersonData personInfo = new PersonData(phone, email, username, firstName, lastName, gender, dateOfBirth);
		Registration registration = new Registration(personInfo, password);
		register(registration, username, password);

	}

	private static void register(Registration registration, String username, String password) {

		// validate for unique information and respond with errors if any.
		List<String> errors = validate(registration);

		if (!errors.isEmpty()) {
			errors.forEach(System.out::println);
			return;
		}
		// if validation is success, then store the user
		saveToRepository(registration);
		storeUserCredentials(username, password);
		System.out.println("Account created successfully!!");
	}
	private static void storeUserCredentials(String username, String password) {
		AuthenticationData.INSTANCE.storageForCheckingInput(username, password);
	}
	private static UserRepository saveToRepository(Registration registration) {
		UserRepository usersRepository = UserRepository.getInstance();
		usersRepository.addUser(new Person(registration));
		return usersRepository;
	}
	private static List<String> validate(Registration registration) {
		List<String> errors = new ArrayList<>();
		if (isUsernameAlreadyExists(registration.getPersonalInfo().getUsername()))
			errors.add("Username already exists");

		if (isEmailAlreadyExists(registration.getPersonalInfo().getEmail()))
			errors.add("Email already exists");
		return errors;
	}

	private static boolean isEmailAlreadyExists(String email) {
		return false;
	}
	private static boolean isUsernameAlreadyExists(final String username) {
		return AuthenticationData.INSTANCE.isUserNameAlreadyExists(username);

	}

}
