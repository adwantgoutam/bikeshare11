package com.voldy.DBImpl;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class BikeShareDB {
	final private String mongoURIString = "mongodb://bikeshare:bikeshare@ds051160.mongolab.com:51160/bikeshare";
	final private String bikeShareDBName = "bikeshare";
	private MongoClient mongoClient ;
	protected DB bikeShareDB ;
	protected Jongo jongo;
	protected MongoCollection userCollection;
	protected MongoCollection paymentDetailsCollection;
	protected MongoCollection docksCollection;
	public BikeShareDB() {
		 try {
			mongoClient = new MongoClient(new MongoClientURI(mongoURIString));
			bikeShareDB = mongoClient.getDB(bikeShareDBName);
			jongo = new Jongo(bikeShareDB);
			userCollection = jongo.getCollection("user");
			paymentDetailsCollection = jongo.getCollection("payment_details");
			docksCollection = jongo.getCollection("docks");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	    
	}
	

}
