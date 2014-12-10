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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.team11.beans.GlobalClass;

public class UpdateAccount extends CommonMenu{
	
	private TextView user_name;
	private TextView paswd;
	private TextView mobile;
	private TextView ssn;
	private TextView email;
	
	private EditText paswd1;
	private EditText mobile1;
	private EditText ssn1;
	private EditText email1;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateaccount);
		Button b = (Button ) findViewById(R.id.update);
		b.setOnClickListener(Update);
		
		user_name=(TextView)findViewById(R.id.uname);
		paswd1=(EditText)findViewById(R.id.paswd1);
		mobile1=(EditText)findViewById(R.id.mobile1);
		email1=(EditText)findViewById(R.id.email1);
		ssn1=(EditText)findViewById(R.id.ssn1);

		//receive user information from previous screen 
		String[] str_user_infor = getUserInformation(savedInstanceState); 
		
		user_name.setText(str_user_infor[0]);
		email1.setText(str_user_infor[1]);
		paswd1.setText(str_user_infor[2]);
		mobile1.setText(str_user_infor[3]);
		ssn1.setText(str_user_infor[4]);
	}
	

	
	OnClickListener Update = new OnClickListener() {
		
        public void onClick(View v) {
    		User user=new User();
    		user.setUser_name(user_name.getText().toString());
    		user.setEmail_id(email1.getText().toString());
    		user.setMobile_number(mobile1.getText().toString());
    		user.setPassword(paswd1.getText().toString());
    		user.setSsn(ssn1.getText().toString());
    		
    		//convert User to gson
    		GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String str=gson.toJson(user);
            //System.out.println("user in update account" + str);
            
        	RequestParams params=new RequestParams();
            params.put("user", str);
           
            
            AsyncHttpClient client = new AsyncHttpClient();
    		client.post("http://192.168.1.108:8080/edit_my_account",params,new AsyncHttpResponseHandler(){
    			public void onSuccess(int statuscode,String response)
    			{	
    				if(response.contains("Successfully updated"))
    				{
    					Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_LONG).show();
        				
    				}
    				else
    				{
    					Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();
    				}	
    			}
    		});
  
			Intent in = new Intent().setClass(getApplicationContext(), MyAccount.class);
			in.putExtra("user_name", user.getUser_name());
			startActivity(in);

      }
	};
	
	public String[] getUserInformation(Bundle savedInstanceState){
		//receive user information from previous screen 
		String[] str_user_infor = new String[5]; 
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
		    if (extras != null)
		    {
		    	str_user_infor[0] = extras.getString("username");
		    	str_user_infor[1] = extras.getString("email");
		    	str_user_infor[2] = extras.getString("password");
		    	str_user_infor[3] = extras.getString("mobile");
		    	str_user_infor[4]= extras.getString("ssn");
		    }
		    else
		    {
		    	str_user_infor[0] = null;
		    	str_user_infor[1] = null;
		    	str_user_infor[2] = null;
		    	str_user_infor[3] = null;
		    	str_user_infor[4]= null;
		    }
		}
		else{
			str_user_infor[0]= (String) savedInstanceState.getSerializable("username");
			str_user_infor[1]= (String) savedInstanceState.getSerializable("email");
			str_user_infor[2]= (String) savedInstanceState.getSerializable("password");
			str_user_infor[3]= (String) savedInstanceState.getSerializable("mobile");
			str_user_infor[4]= (String) savedInstanceState.getSerializable("ssn");	
			
		}
		return str_user_infor;
	}
	
	
}
