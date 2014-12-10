package com.team11.bikeshare;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.Bikes;
import com.team11.beans.User;
import com.team11.beans.GlobalClass;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class AccessCode extends CommonMenu{
	private TextView accessCode;
	private TextView bike_model;
	private TextView start_time;
	private TextView end_time;
	private String bikeid;
	private Bikes bike;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accesscode);

		
		//get bike id from last screen
		bikeid = getBikeId(savedInstanceState);
		
		
		//get bike information from DB
		RequestParams params=new RequestParams();
		params.put("bikeid", bikeid);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://10.185.237.62:8080/bike",params, new AsyncHttpResponseHandler(){
			
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
						
						accessCode=(TextView)findViewById(R.id.accessCode);
						start_time=(TextView)findViewById(R.id.startTime);
						end_time=(TextView)findViewById(R.id.endTime);
						bike_model = (TextView)findViewById(R.id.bikeModel);
						
						bike_model.setText(bike.getBikeModel());
						start_time.setText(formatter.format(scal.getTime()));
						end_time.setText(formatter.format(ecal.getTime()));
						accessCode.setText(bike.getAccessCode());
					}
					else
					{
						System.out.println("failed");
						Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();

					}	
			}
		});
		
	}
	 private String getBikeId(Bundle savedInstanceState){
			//get user name from last screen
			String bikeid;
			if (savedInstanceState == null) {
				Bundle extras = getIntent().getExtras();
			    if (extras != null)
			    {
			    	bikeid = extras.getString("bike_id");
			    }
			    else
			    {
			        bikeid = null;
			    }
			}
			else{
				bikeid= (String) savedInstanceState.getSerializable("bike_id");
			}
			return bikeid;
		}
}