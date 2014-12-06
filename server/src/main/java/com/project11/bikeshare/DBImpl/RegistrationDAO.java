package com.project11.bikeshare.DBImpl;

import java.net.UnknownHostException;

import org.jongo.MongoCollection;









import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.Beans.UserContext;
import com.project11.bikeshare.Beans.UserFeedback;

public class RegistrationDAO extends BikeShareDB{

	public void registerUser(UserContext userContext){
		//save this user in mongo
		System.out.println("Inserting user");
		userCollectionJongo.save(userContext.getUser());
		bikesCollectionJongo.save(userContext.getBike());
		
		
	}

	public User login(String user) throws UnknownHostException{
		MongoClient client = new MongoClient(new ServerAddress("ds051160.mongolab.com",51160));
		DB database = client.getDB("bikeshare");
	    database.authenticate("bikeshare","bikeshare".toCharArray());
	    DBCollection collection = database.getCollection("user");
	    BasicDBObject query = new BasicDBObject("username",user);
	    DBCursor cursor = collection.find(query);
	    User u=new User();
	    while(cursor.hasNext())
	    {
	    	DBObject obj=cursor.next();
	    	u.setUser_name(String.valueOf(obj.get("username")));
	    	u.setPassword(String.valueOf(obj.get("password")));
	    }
	     return u;		
		
	}

	public void feedback(UserFeedback uf) {
		// TODO Auto-generated method stub
		System.out.println("Inserting feedback");
		 feedbackCollectionJongo.save(uf);
	}

}
