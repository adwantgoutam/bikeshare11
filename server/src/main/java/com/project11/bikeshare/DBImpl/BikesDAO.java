package com.project11.bikeshare.DBImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.Coordinates;
import com.project11.bikeshare.Beans.Location;

public class BikesDAO extends BikeShareDB{

	private Class<Object> Bikes = null;

	public List<Bikes> getLocations(Coordinates coordinates) throws UnknownHostException
	{
		MongoClient client = new MongoClient(new ServerAddress("ds051160.mongolab.com",51160));
		DB database = client.getDB("bikeshare");
	    database.authenticate("bikeshare","bikeshare".toCharArray());
	    DBCollection collection = database.getCollection("bikes");
	    BasicDBList myLocation = new BasicDBList();
		myLocation.put(1, Double.parseDouble(coordinates.getLatitude()));
		myLocation.put(0, Double.parseDouble(coordinates.getLongitude()));
		ArrayList<Bikes> list=new ArrayList<Bikes>();
		DBCursor cursor= collection.find(
	            new BasicDBObject("location",
	                new BasicDBObject("$near",
	                        new BasicDBObject("$geometry",
	                                new BasicDBObject("type", "Point")
	                                    .append("coordinates", myLocation))
	                             .append("$maxDistance",  5000)
	                        )
	                )
	            );
		while(cursor.hasNext())
		{
			//System.out.println("in while");
			DBObject obj=cursor.next();
			Bikes b=new Bikes();
			b.setUser_id(String.valueOf(obj.get("user_id")));
			b.setBike_id(String.valueOf(obj.get("bike_id")));
			b.setAccessCode(String.valueOf(obj.get("accessCode")));
			Location loc=new Location();
			BasicDBObject location=(BasicDBObject) obj.get("location");
			BasicDBList coord1=(BasicDBList) location.get("coordinates");
			Coordinates coord=new Coordinates();
			coord.setLatitude(String.valueOf(coord1.get(1)));
			coord.setLongitude(String.valueOf(coord1.get(0)));
			loc.setCoordinates(coord);
			b.setLocation(loc);
			b.setIsBikeAvailable((String) obj.get("isBikeAvailable"));
			b.setPincode(String.valueOf(obj.get("pincode")));
			b.setBikeModel(String.valueOf(obj.get("bikeModel")));
			b.setStart_time(String.valueOf(obj.get("start_time")));
			b.setEnd_time(String.valueOf(obj.get("end_time")));
	    	list.add(b);
		}
		
		return list;


	}
	public Bikes getBikeDetails(String bid) throws UnknownHostException
	{
		//Bikes b = new Bikes();
		//MongoClientURI uri  = new MongoClientURI("mongodb://bikeshare:bikeshare@ds051160.mongolab.com:51160/bikeshare"); 
		//MongoClient client = new MongoClient(uri);
		//DB db = client.getDB("bikeshare");
		//DBCollection coll = db.getCollection("bikes");
		//BasicDBObject query = new BasicDBObject("bikeid",bid);
		//bikesCollection.find("{bikeid:"+ bid+"}");
		//DBCursor cursor = coll.find(query);
		Bikes bike = bikesCollectionJongo.findOne("{bike_id:'"+ bid+"'}").projection("{_id: 0,location:0}").as(Bikes.class);
		/*
		try {
			   while(cursor.hasNext() ) {
				   System.out.println("entered");
				   DBObject obj = cursor.next();
				   b.setAccessCode(obj.get("accesscode"));
				   
			   }
			} finally {
			   cursor.close();
			}
			*/
		return bike;
	}
}
