package com.team11.bikeshare;


import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
public class CommonMenu extends Activity{
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.common_menu, menu);
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.mybikes:
        Intent in = new Intent().setClass(getApplicationContext(), MyBikes.class);
        startActivity(in);
        return true;
    case R.id.myhistory:
    	Intent historyIntent = new Intent().setClass(getApplicationContext(), MyHistory.class);
        startActivity(historyIntent);
        return true;
    case R.id.myaccount:
    	Intent myAccountIntent = new Intent().setClass(getApplicationContext(), MyAccount.class);
        startActivity(myAccountIntent);
        return true;
    default:
        return false;
    }

}

}
