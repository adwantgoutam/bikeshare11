package com.team11.bikeshare;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.Bikes;
import com.team11.beans.User;
import com.team11.beans.UserContext;

public class Registration extends Activity{
	private EditText user_name;
	private EditText paswd;
	private EditText mobile;
	private EditText bikeName;
	private EditText bikeModel;
	private EditText email;
	private CheckBox checkbox;
	private EditText SSN;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		Button b = (Button ) findViewById(R.id.register1);
		b.setOnClickListener(register);
		user_name=(EditText)findViewById(R.id.uname);
		paswd=(EditText)findViewById(R.id.paswd);
		mobile=(EditText)findViewById(R.id.mobile);
		email=(EditText)findViewById(R.id.email);
		bikeName=(EditText)findViewById(R.id.bikeName);
		bikeModel=(EditText)findViewById(R.id.bikeModel);
		checkbox = (CheckBox) findViewById(R.id.isBike);
		SSN = (EditText)findViewById(R.id.SSN);
		checkbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkbox.isChecked()) {
					bikeName.setVisibility(View.VISIBLE);
					bikeModel.setVisibility(View.VISIBLE);
				}
				else {
					bikeName.setVisibility(View.INVISIBLE);
					bikeModel.setVisibility(View.INVISIBLE);
				}
			}
		});

		
		
	}
	OnClickListener register = new OnClickListener() {
        public void onClick(View v) {
        	
        	AsyncHttpClient client = new AsyncHttpClient();
        	RequestParams params=new RequestParams();
    		UserContext uc=new UserContext();
    		User user=new User();
    		user.setEmail_id(email.getText().toString());
    		user.setUser_name(user_name.getText().toString());
    		user.setMobile_number(mobile.getText().toString());
    		user.setPassword(paswd.getText().toString());
    		user.setSsn(SSN.getText().toString());
    		//user.setSsn(ssn.)
       		uc.setUser(user);
       		Bikes bike = new Bikes();
       		//bike.setIsBikeAvailable("Yes");
       		//bike.setPincode("95112");
       		bike.setBike_id(bikeName.getText().toString());
       		bike.setBikeModel(bikeModel.getText().toString());
       		bike.setUser_id(user_name.getText().toString());
 
       		uc.setBikes(bike);
       		//bike.se
    		GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String str=gson.toJson(uc);
            params.put("userContext", str);
    		//DataObject obj = gson.fromJson(br, DataObject.class);

    		client.post("http://192.168.1.108:8080/register_user",params, new AsyncHttpResponseHandler(){
            //client.post("http://192.168.2.9:8080/register_user",params, new AsyncHttpResponseHandler(){
    			
    			public void onSuccess(int statuscode,String response)
    			{
    				System.out.println("Response: "+response);
    				//Toast.makeText(getApplicationContext(), statuscode+"Success registration", Toast.LENGTH_LONG).show();
    					if(response.contains("Success"))
    					{
    						//Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
    						Intent in = new Intent().setClass(getApplicationContext(), Home.class);
    						String userid=user_name.getText().toString();
    						in.putExtra("userid", userid);
    						startActivity(in);
    						
        					    					}
    					else
    					{
    						Toast.makeText(getApplicationContext(), "Username Exist! Please choose another UserName.", Toast.LENGTH_LONG).show();

    					}
    				
    			}
    		}
    		);
    		/*
    		Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
			Intent in = new Intent().setClass(getApplicationContext(), Home.class);
			String userid=user_name.getText().toString();
			in.putExtra("userid", userid);
			startActivity(in);
			*/




      }
	};
	
	
	}
