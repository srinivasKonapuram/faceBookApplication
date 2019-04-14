/**
 * 
 */
package com.imaginea.java.training.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.imaginea.java.training.data.UserRepository;
import com.imaginea.java.training.registration.Registration;
import com.imaginea.java.training.services.FriendService;
import com.imaginea.java.training.services.PostService;

/**
 * @author srinivask
 * @param <FriendRequest>
 *
 */
public class Person {

	private List<String> interests;
	private List<String> postofOwn;
	private List<String> hobbies;
	private Address address;
	private List<Person> friends;
	private List<AcademicDetails> academicinfo;
	private List<WorkExperience> workexperience;
	private PersonData persondata;
	private static  Person  INSTANCE = new Person();

	public static  Person getInstacne() {
		return INSTANCE;
	}
	public void addFriend(Person person) {
		if (friends == null)
			friends = new ArrayList<>();

		friends.add(person);

	}
	public Person(List<String> interests, List<String> hobbies, Address address, List<AcademicDetails> academicinfo,List<WorkExperience> workexperience) {
		super();
		this.interests = interests;
		this.hobbies = hobbies;
		this.address=address;
		this.academicinfo = academicinfo;
		this.workexperience = workexperience;
	}
	public String uniqueIdentifier() {
		return persondata.getEmail();
	}

	public boolean doYouHaveFriends() {
		return null != friends && !friends.isEmpty();
	}

	public List<String> namesOfTheFriends() {
		if (!Objects.isNull(friends))
			return friends.stream().map(p -> p.persondata.name()).collect(Collectors.toList());
		return null;

	}

	public List<Person> friends() {
		return friends;
	}

	public Person(Registration registration) {
		this.persondata = registration.getPersonalInfo();
	}
public Person(PersonData persondata) {
	this.persondata=persondata;
}
	public Person() {
	}

	
	public PersonData getPersonalInfo() {
		return persondata;
	}

	public boolean isUserNameMatchingTo(final String username) {
		return this.persondata.isUserNameMatchingTo(username);
	}

	public boolean isEmailEqualTo(String email) {
		if (this.persondata == null)
			return false;
		return this.persondata.isEmailEqualTo(email);
	}
	@Override
	public String toString() {
		return "Person [personData=" + persondata + ", address=" + address + ", interests=" + interests
				+ ", hobbies=" + hobbies + ", acadamicDetails=" + academicinfo + ", workDetails=" + workexperience
				+ ", friends=" + friends + "]";
	}
	public List<Person> PostOfOwn(String message) {
		 return PostOfOwn(message);
	}
	public List<String> retrievePost() {
		return postofOwn;
	}
//	public List<Person> PostOfOwn(String username) {
//		// TODO Auto-generated method stub
//		return username;
//	}
	public static List<String> retrivefriends(String username) {
        if(username==null)
            throw new RuntimeException("give username");
        return FriendService.getInstance().retriveMyfriends(username);

    }

public  static List<String> retrivePosts(String username) {
        if(username==null)
            throw new RuntimeException("give username");
        return PostService.getInstance().retrivePost(username);
    }


}

