
package com.team11.bikeshare;
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.List;


import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.BikeContext;
import com.team11.beans.Bikes;
import com.team11.beans.BikesList;
import com.team11.beans.GlobalClass;
import com.team11.beans.RentDetails;
  
import android.app.Activity;  
import android.os.Bundle;  
import android.widget.ArrayAdapter;  
import android.widget.ListView;  
  
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class MyBikes extends CommonMenu {
 
	private ListView listView ;
	private ArrayAdapter<String> listAdapter ; 
	BikesList bikeslist;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybikes);
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params=new RequestParams();
		GlobalClass globalVariable=(GlobalClass) getApplicationContext();
		params.put("user_id",globalVariable.getUsername());
		System.out.println(globalVariable.getUsername());
		client.get("http://10.0.0.36:8080/allbikes",params, new AsyncHttpResponseHandler(){
			public void onSuccess(int statuscode,String response)
			{
				Gson gson = new Gson();
				bikeslist=gson.fromJson(response, BikesList.class);
				if(bikeslist.getList().size()==0)
				{
					Toast.makeText(getApplicationContext(), "There are no bikes available on your name", Toast.LENGTH_SHORT).show();
				}
				else{
				ArrayList<String> list = new ArrayList<String>();
				List<Bikes> blist=bikeslist.getList();
			    for(int i=0;i<blist.size();i++)
				{	Bikes b=blist.get(i);
					list.add(b.getBike_id());
				}
				listView = (ListView) findViewById(R.id.listView); 
				listAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simplerows,list) ; 
				listView.setAdapter(listAdapter); 
			}}
		});
		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){ 

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		    	
		    	//Toast.makeText(getApplicationContext(),txt.getText(), Toast.LENGTH_SHORT).show();
				final TextView txt=(TextView)arg1;
		    	AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params=new RequestParams();
				params.put("bikeid",txt.getText());
				client.get("http://10.0.0.36:8080/getBike",params, new AsyncHttpResponseHandler(){
					public void onSuccess(int statuscode,String response)
					{
						Gson gson = new Gson();
						BikeContext bc =gson.fromJson(response, BikeContext.class);
						System.out.println(response);
						RentDetails rd=bc.getRentdetails();
						//System.out.println(bc.getRentdetails().getReceived());
						if(bc.getBike().getIsBikeAvailable().equals("no") && (rd==null || rd.getReceived().equals("yes") || rd.getReceived().equals("nyr")))
						{
							Intent in=new Intent().setClass(getApplicationContext(), LendBike.class);
					    	in.putExtra("bikeid", txt.getText().toString());
							startActivity(in);
						}
						else 
						{
							//revoke
							
							Intent in=new Intent().setClass(getApplicationContext(), GotBikeRevoke.class);
					    	in.putExtra("bike_id", txt.getText().toString());
					    	System.out.println(txt.getText().toString());
					    	System.out.println(bc.getRentdetails().getReceived());
					    	in.putExtra("isBikeAvailable", bc.getBike().getIsBikeAvailable());
					    	in.putExtra("received",bc.getRentdetails().getReceived());
					    	System.out.println(bc.getRentdetails().getReceived());
							startActivity(in);
						}
						
						
						
				
						
					}});	
		    }
		});

	}
}