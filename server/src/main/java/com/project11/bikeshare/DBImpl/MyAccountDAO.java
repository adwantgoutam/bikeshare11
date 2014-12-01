package com.project11.bikeshare.DBImpl;

import com.mongodb.*;
import com.project11.bikeshare.Beans.User;

import java.net.UnknownHostException;

public class MyAccountDAO extends BikeShareDB {

    public User getUserFromDB(String username) throws UnknownHostException{
        //return user information
        MongoClient client = new MongoClient(new ServerAddress("ds051160.mongolab.com",51160));
        DB database = client.getDB("bikeshare");
        database.authenticate("bikeshare","bikeshare".toCharArray());
        DBCollection collection = database.getCollection("user");
        BasicDBObject query = new BasicDBObject("username",username);
        DBCursor cursor = collection.find(query);
        User u=new User();
        while(cursor.hasNext())
        {

            DBObject obj=cursor.next();
            u.setUser_name(String.valueOf(obj.get("username")));
            u.setPassword(String.valueOf(obj.get("password")));
            u.setEmail_id(String.valueOf(obj.get("email")));
            u.setMobile_number(String.valueOf(obj.get("mobilenumber")));
            //u.setSsn(String.valueOf(obj.get("ssn")));
        }

        return u;

    }

    public void updateAccount(User user) {
        //update and save this user information in mongo

        try {

            MongoClient client = new MongoClient(new ServerAddress("ds051160.mongolab.com",51160));
            DB database = client.getDB("bikeshare");
            database.authenticate("bikeshare","bikeshare".toCharArray());

            DBCollection user_collection = database.getCollection("user");
            BasicDBObject query = new BasicDBObject("username",user.getUser_name());
            DBCursor cursor = user_collection.find(query);
            if (cursor.hasNext())
            {
                DBObject user_in_db = cursor.next();
                user_in_db.put("password", user.getPassword());
                user_in_db.put("email", user.getEmail_id());
                user_in_db.put("mobilenumber", user.getMobile_number());
                //user_in_db.put("ssn", user.getSsn());
                user_collection.save(user_in_db);
            }

        }
        catch (Exception e) {

        }


    }


}
