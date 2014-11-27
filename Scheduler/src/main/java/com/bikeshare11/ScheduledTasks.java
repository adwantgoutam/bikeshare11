package com.bikeshare11;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bikeshare11.beans.User;
import com.twilio.sdk.TwilioRestException;



@EnableScheduling
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 300000)
    public void NotifyUser() throws TwilioRestException, UnknownHostException{
        //System.out.println("The time is now " + dateFormat.format(new Date()));
        List<User> list=new FindUsers().findUsers();
        if(list==null || list.size()==0)
        {
        	System.out.println("no records");
        	//System.out.println(new Date().getTime());
        }
        else
        {
        	for(int i=0;i<list.size();i++)
        	{
        		User user=list.get(i);
        		new TwilioMessage().sendMessage(user.getUser_name(),user.getMobile_number());
        		
        	}
        }
        
    	
    }
}
