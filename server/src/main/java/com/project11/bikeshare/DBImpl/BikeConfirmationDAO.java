package com.project11.bikeshare.DBImpl;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.RentDetails;
import com.project11.bikeshare.Beans.User;

public class BikeConfirmationDAO extends BikeShareDB{

	public Bikes confirmRent(String bike_id) {
		bikesCollectionJongo.update("{bikeid: '"+bike_id+"'}").with("{$set:{isbikeavailable: 'no'}}");
		Bikes bikes= bikesCollectionJongo.findOne("{bikeid: '"+bike_id+"'}").as(Bikes.class);
	return bikes;
	}

	public User findUser(String user_id) {
		User user = userCollectionJongo.findOne("{user_id: '"+user_id+"'}").as(User.class);
		return user;
	}
	
	public Bikes findBike(String bikeid) throws UnknownHostException {
		MongoClient client = new MongoClient(new ServerAddress("ds051160.mongolab.com",51160));
		DB database = client.getDB("bikeshare");
	    database.authenticate("bikeshare","bikeshare".toCharArray());
	    DBCollection collection = database.getCollection("bikes");
	    BasicDBObject query = new BasicDBObject("bikeid",bikeid);
	    DBCursor cursor = collection.find(query);
	    Bikes b=new Bikes();
	    while(cursor.hasNext())
	    {
	    	DBObject obj=cursor.next();
	    	b.setBike_id(String.valueOf(obj.get("bikeid")));
	    	b.setBikeModel(String.valueOf(obj.get("bike_model")));
	    	b.setStart_time(String.valueOf(obj.get("start_time")));
	    	b.setEnd_time(String.valueOf(obj.get("end_time")));
	    	b.setAccessCode(String.valueOf(obj.get("accesscode")));
	    	
	    }
	     return b;		

	}

	public RentDetails updateRentDetails(String bike_id,String username) {
		rentDetailsCollectionJongo.update("{username: '"+username+"'}").with("{$set:{bikeid: '"+bike_id+"'},{received:'nyr'}}");
		return rentDetailsCollectionJongo.findOne("{bikeid: '"+bike_id+"'},{received:'nyr'}").as(RentDetails.class);
	}
	

}
