package com.project11.bikeshare.DBImpl;

import org.jongo.MongoCollection;

import com.project11.bikeshare.Beans.User;

public class RegistrationDAO extends BikeShareDB{

	public void registerUser(User user){
		//save this user in mongo
		System.out.println("Inserting user");
		userCollection.save(user);
		
		
	}
}
