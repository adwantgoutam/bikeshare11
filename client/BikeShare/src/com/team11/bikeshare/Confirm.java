package com.team11.bikeshare;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.team11.beans.User;
import com.team11.beans.Bikes;
import com.team11.beans.UserContext;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import com.team11.beans.GlobalClass;

public class Confirm extends CommonMenu {
	private TextView bike_model;
	private TextView start_time;
	private TextView end_time;
	private String bikeid;
	private Bikes bike;
	private String userid;
	private TextView accessCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm);
		
		//get bike id from last screen
		GlobalClass gv=(GlobalClass) getApplicationContext();
		userid=gv.getUsername();
		bikeid = getBikeId(savedInstanceState);
		
		//confirm button
		Button b = (Button) findViewById(R.id.confirm);
		b.setOnClickListener(Confirm);
		
		//image
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageResource(R.drawable.image1);
		
		
		//get bike information from DB
		RequestParams params=new RequestParams();
		params.put("bikeid", bikeid);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://192.168.1.108:8080/bike",params, new AsyncHttpResponseHandler(){
			public void onSuccess(int statuscode,String response)
			{
				if(response.contains("bike_id"))
				{
					Gson gson = new Gson();
					bike=gson.fromJson(response, Bikes.class);
					DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	          		Calendar scal = Calendar.getInstance();
	        		scal.setTimeInMillis(Long.parseLong(bike.getStartTime()));
	        		Calendar ecal = Calendar.getInstance();
	        		ecal.setTimeInMillis(Long.parseLong(bike.getEndTime()));
	        		bike_model = (TextView)findViewById(R.id.bikeModel);
					start_time = (TextView)findViewById(R.id.startTime);
					end_time = (TextView)findViewById(R.id.endTime);
					bike_model.setText(bike.getBikeModel());
					start_time.setText(formatter.format(scal.getTime()));
					end_time.setText(formatter.format(ecal.getTime()));
				}
				else
				{
					System.out.println("failed");
					Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();

				}	
			}
		});
		}
	
	
	OnClickListener Confirm = new OnClickListener() {
        public void onClick(View v) {
        	System.out.println("confirm clicked");
        	RequestParams params=new RequestParams();
        	params.put("bike_id", bikeid);
        	params.put("user_id", userid);
        	AsyncHttpClient client = new AsyncHttpClient();
    		client.post("http://192.168.1.108:8080/confirm_rent",params, new AsyncHttpResponseHandler(){
    			public void onSuccess(int statuscode,String response)
    			{
    				System.out.println(response);
    				if(response.contains("success"))
    				{
    					Intent in = new Intent().setClass(getApplicationContext(), AccessCode.class);
    		    	    in.putExtra("bikeid", bikeid);
    		    	    startActivity(in);
    				}
    				else
    				{
    					System.out.println("failed");
    					Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();

    				}	
    			}
    		});
    		
    	 
        }
    };
    
    
    
    
    
    private String getBikeId(Bundle savedInstanceState){
		//get user name from last screen
		String bikeid;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
		    if (extras != null)
		    {
		    	bikeid = extras.getString("bikeid");
		    }
		    else
		    {
		        bikeid = null;
		    }
		}
		else{
			bikeid= (String) savedInstanceState.getSerializable("bikeid");
		}
		return bikeid;
	}
			
		
}


