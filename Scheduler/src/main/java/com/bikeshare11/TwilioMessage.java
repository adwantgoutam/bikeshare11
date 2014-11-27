package com.bikeshare11;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;


public class TwilioMessage {
	
	public static final String ACCOUNT_SID = "AC4cb10d4afc95bd06309900a4857361c7";
	public static final String AUTH_TOKEN = "d566bb75c5fde3b6a7cca5ace5d35985";

	
	public void sendMessage(String user,String mobilenumber) throws TwilioRestException
	{
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		 
	    // Build a filter for the MessageList
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    String body="Hello "+user+", Your rental time is about to end in less than 15 minutes.Kindly return the bike to the owner.Hope you enjoyed our service.Thanks :)";
	    params.add(new BasicNameValuePair("Body",body));
	    params.add(new BasicNameValuePair("To",mobilenumber));
	    params.add(new BasicNameValuePair("From", "+12015747858"));
	    //params.add(new BasicNameValuePair("MediaUrl", "http://www.example.com/hearts.png"));
	     
	     
	    MessageFactory messageFactory = client.getAccount().getMessageFactory();
	    Message message = messageFactory.create(params);
	    //System.out.println(message.getSid());

	}

}


