/**
 * 
 */
package com.imaginea.java.training.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import com.imaginea.java.training.data.UserRepository;
import com.imaginea.java.training.domain.Person;

/**
 * @author srinivask
 *
 */
public class PostService {
	private static PostService INSTANCE = new PostService();
	private static 		Map<String ,List<String>> userPost= new HashMap<>();
	public void addingPost(String username) {
		Scanner scanner = new Scanner(System.in);
		List<String> messagelist = new ArrayList<>();
		
			System.out.println(" please enter the post message");
			String message = scanner.next();
			messagelist.add(message);
			userPost.put(username, messagelist);

	}

	public List<String> retrivePost(String username) {
		return  userPost.get(username);
	}
	public List<String> allPost(String username){
		FriendService myservice= new FriendService().getInstance();
		return null;
		
	}

	public static PostService getInstance() {
		return INSTANCE;
	}

}
