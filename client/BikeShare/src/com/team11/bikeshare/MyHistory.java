package com.team11.bikeshare;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.Calendar;
import java.util.List;


import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.User;
import com.team11.beans.RentHistory;
import com.team11.beans.RentDetails;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;  
import android.widget.ListView;
import android.app.ListActivity;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.team11.beans.GlobalClass;

public class MyHistory extends CommonMenu{
	
	private ListView listView;
	private ArrayAdapter<String> listAdapter ; 
	RentHistory rent_history = new RentHistory(); 
	private TextView history;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhistory);
		
		//get user name from previous screen
		GlobalClass gv=(GlobalClass) getApplicationContext();
		String user_name_str=gv.getUsername();
		
		//get user account information from DB
		RequestParams params=new RequestParams();
		params.put("user_id", user_name_str);
		System.out.println("Param user_id: " + params);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://10.185.237.62:8080/get_my_history",params,new AsyncHttpResponseHandler(){
			public void onSuccess(int statuscode,String response)
			{	
				Gson gson = new Gson();
				System.out.println("response: " + response);
				rent_history=gson.fromJson(response, RentHistory.class);
				System.out.println(rent_history.getRent_history().size());
				if(rent_history.getRent_history().size()==0)
				{
					Toast.makeText(getApplicationContext(), "No History", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "My History", Toast.LENGTH_LONG).show();
					ArrayList<String> list = new ArrayList<String>();
					List<RentDetails> rlist=rent_history.getRent_history();
				    for(int i=0;i<rlist.size();i++)
					{	RentDetails r=rlist.get(i);
						DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");	          		
						Calendar scal = Calendar.getInstance();
						scal.setTimeInMillis(Long.parseLong(r.getStart_time()));
						Calendar ecal = Calendar.getInstance();
						ecal.setTimeInMillis(Long.parseLong(r.getEnd_time()));
						String history="Bike Id : "+r.getBike_id()+"\n"+
										"Start Time : " + formatter.format(scal.getTime())+ "\n"+
										"End Time : " + formatter.format(scal.getTime()) + "\n";
						list.add(history);
					}
					listView = (ListView) findViewById(R.id.listView); 
					listAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerows,list) ; 
					listView.setAdapter(listAdapter); 
				   
				}}
		});
	
	}
	
	
	
	
	public String getUserName(Bundle savedInstanceState){
		//get user name from last screen
		String userid;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
		    if (extras != null)
		    {
		    	userid = extras.getString("user_name");
		    }
		    else
		    {
		        userid = null;
		    }
		}
		else{
			userid= (String) savedInstanceState.getSerializable("user_name");
		}
		return userid;
	}
}