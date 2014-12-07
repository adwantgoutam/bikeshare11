package com.project11.bikeshare.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import scala.util.parsing.combinator.token.StdTokens.Keyword;

import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.BikesList;
import com.project11.bikeshare.DBImpl.OfflineModeDAO;
import com.project11.bikeshare.util.TwilioMessage;
import com.twilio.sdk.TwilioRestException;

public class OfflineModeService {
	
	public void offlineMode(String From,String Body) throws TwilioRestException, UnknownHostException
	{
		String content[]=Body.split(" ");
		String keyword=content[0];
		String username=content[1];
		String var=content[2];
		String message="";
		List<Bikes> blist=new ArrayList<Bikes>();
		if(Body==null || Body=="")
		{
			message="Message should not be empty.Please try searching again";
			new TwilioMessage().sendMessage(From, message);
		}
		else if(keyword.equalsIgnoreCase("search") || keyword.equalsIgnoreCase("rent"))
		{
			if(keyword.equalsIgnoreCase("search"))
			{
				blist=new OfflineModeDAO().findByZipCode(var);
				for(int i=0;i<blist.size();i++)
				{
					Bikes b=blist.get(i);
					message=message+"\n"+"Bike Id : "+b.getBike_id()+"\n"+
								"Bike Model : "+b.getBikeModel()+"\n"+
								"Start Time : "+b.getStart_time()+"\n"+
								"End Time : "+b.getEnd_time()+"\n";
				}
				new TwilioMessage().sendMessage(From, message);
			}
			else if(keyword.equalsIgnoreCase("rent"))
			{
				
			}
		}
		else
		{
			message="Please search using correct keywords";
			new TwilioMessage().sendMessage(From, message);
		}
	}

	
}
