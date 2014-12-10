package com.team11.bikeshare;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.BikeContext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GotBikeRevoke extends FragmentActivity {
	 EditText accessCode;
	 EditText bikeModel;
	 EditText starttime;
	 EditText endtime;
	 Button revoke;
	 Button gotmybike;
	 String user_id_renter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gotbikerevoke);
		gotmybike = (Button ) findViewById(R.id.gotmybike);
		revoke = (Button ) findViewById(R.id.revoke);
		
		accessCode=(EditText)findViewById(R.id.accessCode);
		bikeModel=(EditText)findViewById(R.id.bikeModel);
		starttime=(EditText)findViewById(R.id.startTime);
		endtime=(EditText)findViewById(R.id.endTime);

		
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params=new RequestParams();
		Intent in=getIntent();
		String bikeid=in.getStringExtra("bike_id");
		String received=in.getStringExtra("received");
		String isBikeAvailable=in.getStringExtra("isBikeAvailable");
		System.out.println(bikeid);
		System.out.println(received);
		if(received.equals("no") && isBikeAvailable.equals("no"))
		{
			System.out.println(bikeid);
			gotmybike.setVisibility(View.VISIBLE);
		}
		if (received.equals("nyr") && isBikeAvailable.equals("yes") )
		{
			
			revoke.setVisibility(View.VISIBLE);
		}
		
		params.put("bikeid",bikeid);
		client.get("http://192.168.1.108.62:8080/getBike",params, new AsyncHttpResponseHandler(){
			public void onSuccess(int statuscode,String response)
			{
				Gson gson = new Gson();
				BikeContext bc = new BikeContext();
				bc=gson.fromJson(response, BikeContext.class);
				bikeModel.setText(bc.getBike().getBikeModel());
				accessCode.setText(bc.getBike().getAccessCode());
				DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
				Calendar scal = Calendar.getInstance();
				scal.setTimeInMillis(Long.parseLong(bc.getRentdetails().getStart_time()));
				Calendar ecal = Calendar.getInstance();
				ecal.setTimeInMillis(Long.parseLong(bc.getRentdetails().getEnd_time()));
				starttime.setText("Start Time : "+formatter.format(scal.getTime()));
				endtime.setText("End Time : "+formatter.format(ecal.getTime()));
				starttime.setText(formatter.format(scal.getTime()));
				endtime.setText(formatter.format(ecal.getTime()));
				user_id_renter=bc.getRentdetails().getUser_id_renter();
				
				
			}});
		
		gotmybike.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	
	        	AsyncHttpClient client = new AsyncHttpClient();
	        	RequestParams params=new RequestParams();
	        	Intent in=getIntent();
	    		String bikeid=in.getStringExtra("bike_id");
	        	params.put("bikeid", bikeid);

	    		client.post("http://10.0.0.36:8080/gotMyBike",params, new AsyncHttpResponseHandler(){
	    			
	    			public void onSuccess(int statuscode,String response)
	    			{
	    				Intent in2 = new Intent().setClass(getApplicationContext(), Feedback.class);
	    				in2.putExtra("user_id_renter",user_id_renter);
	    				startActivity(in2);
	    			}
	    		}
	    		);
	    		//add feedback intent here
	    	    
	    	     	
	        }
	    });
		revoke.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	
	        	AsyncHttpClient client = new AsyncHttpClient();
	        	RequestParams params=new RequestParams();
	        	Intent in=getIntent();
	    		String bikeid=in.getStringExtra("bike_id");
	        	params.put("bikeid", bikeid);

	    		client.post("http://10.0.0.36:8080/revoke",params, new AsyncHttpResponseHandler(){
	    			
	    			public void onSuccess(int statuscode,String response)
	    			{
	    				Intent in1 = new Intent().setClass(getApplicationContext(), Home.class);
	    				startActivity(in1);
	    			}
	    		}
	    		);
	    	    
	        }
	    });
	}}
