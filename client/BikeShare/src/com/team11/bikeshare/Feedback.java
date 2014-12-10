package com.team11.bikeshare;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.team11.beans.FeedbackBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class Feedback extends Activity {

    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private RadioGroup radioBDamage;
    private RadioGroup radioBReturn;
    private RadioButton radioDamageButton;
    private RadioButton radioReturnBikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent in=getIntent();
		String bikeid=in.getStringExtra("bike_id");
        addListenerOnRatingBar();
        addListenerOnButton();
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);



      //  txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

             //   txtRatingValue.setText(String.valueOf(rating));
                //int TRating = Integer.p .ParseInt(rating);
                System.out.print("Value"+rating);

            }
        });
    }

    public void addListenerOnButton() {


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        // find the radiobutton by returned id


        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                /*
                Toast.makeText(MainActivity.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();
                        */
            	FeedbackBean fb = new FeedbackBean();
            	RequestParams params=new RequestParams();
            	AsyncHttpClient client = new AsyncHttpClient();
            	
                radioBDamage = (RadioGroup) findViewById(R.id.radioBikeDamage);
                radioBReturn = (RadioGroup) findViewById(R.id.radioReturnBike);

                int selectedId = radioBDamage.getCheckedRadioButtonId();
                int returnId = radioBReturn.getCheckedRadioButtonId();

                radioDamageButton = (RadioButton) findViewById(selectedId);
                radioReturnBikeButton = (RadioButton) findViewById(selectedId);
                
                fb.setBikeDamage(String.valueOf(radioDamageButton.getText()));
                fb.setReturnBike(String.valueOf(radioReturnBikeButton.getText()));
                fb.setRatings(String.valueOf(ratingBar.getRating()));
                fb.setUser_id_renter("1234");
                
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String str=gson.toJson(fb);
                params.put("userFeedbackStr", str);
                params.put("user_id_renter","12345");
                
                //client.post("http://192.168.2.9:8080/feedback",params, new AsyncHttpResponseHandler(){
                client.post("http://10.0.0.23:8080/feedback",params, new AsyncHttpResponseHandler(){
                
        			
        			public void onSuccess(int statuscode,String response)
        			{
        				Toast.makeText(getApplicationContext(), statuscode+"Success Feedback", Toast.LENGTH_LONG).show();
        					if(response.contains("Successfully submitted feedback"))
        					{
        						Intent in1 = new Intent().setClass(getApplicationContext(), Home.class);
        	    				startActivity(in1);
            					    					}
        					else
        					{
        						Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();

        					}
        				
        			}
        		}
        		);
                

               String newStr=String.valueOf(ratingBar.getRating())+""+radioDamageButton.getText()+radioReturnBikeButton.getText();

                Toast.makeText(Feedback.this,
                        newStr,
                        Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    
}

