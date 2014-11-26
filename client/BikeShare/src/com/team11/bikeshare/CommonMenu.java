package com.team11.bikeshare;


import android.app.Activity;
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
		Toast.makeText(getApplicationContext(), "you've been helped",
				Toast.LENGTH_SHORT).show();
		return true;
	case R.id.myhistory:
		Toast.makeText(getApplicationContext(), "you've been helped more",
				Toast.LENGTH_SHORT).show();
		return true;
	case R.id.myaccount:
		return true;
	default:
		return false;
	}

}

}
