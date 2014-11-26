package com.team11.bikeshare;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.User;
import com.team11.beans.UserContext;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity{
	private EditText user_name;
	private EditText paswd;
	private EditText mobile;
	private EditText bikeName;
	private EditText bikeModel;
	private EditText email;
	private CheckBox checkbox;
	
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
       		uc.setUser(user);
    		GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String str=gson.toJson(uc);
            params.put("userContext", str);
    		//DataObject obj = gson.fromJson(br, DataObject.class);

    		client.post("http://10.0.0.9:8080/register_user",params, new AsyncHttpResponseHandler(){
    			
    			public void onSuccess(int statuscode,String response)
    			{
    				Toast.makeText(getApplicationContext(), statuscode+"Success registration", Toast.LENGTH_LONG).show();
    					if(response.contains("User Registered Successfully"))
    					{
        					    					}
    					else
    					{
    						Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();

    					}
    				
    			}
    		}
    		);
    		Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
			Intent in = new Intent().setClass(getApplicationContext(), ShowBikeLocations.class);
			String userid=user_name.getText().toString();
			in.putExtra("userid", userid);
			startActivity(in);




      }
	};
	
	
	}
