package com.kayzee.axiomzentest;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
    private String[] mTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
	public CharSequence mTitle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
        mTitles = new  String[] {"a","b","c"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTitles));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/*
	private void getLocation(){
	    LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location==null)
			location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location!=null){
		} else {
	    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,500.0f,this);
	    	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,500.0f,this);
		}
	}
	*/
	public class DrawerItemClickListener implements ListView.OnItemClickListener {
		

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
			
		}
		/** Swaps fragments in the main content view */
		private void selectItem(int position) {


		    // Highlight the selected item, update the title, and close the drawer
		    mDrawerList.setItemChecked(position, true);
		    setTitle(mTitles[position]);
		    mDrawerLayout.closeDrawer(mDrawerList);
		}

		public void setTitle(CharSequence title) {
		    mTitle = title;
		    
		    getSupportActionBar().setTitle(mTitle);
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
