package com.kayzee.axiomzentest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;


import org.json.JSONObject;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.widget.DrawerLayout;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	
	private String response;
	private JSONObject jsonResponse;
    private String[] mTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
	private CharSequence mTitle;
	
	HttpClient androidHttpClient;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the Navigation Drawer
        mTitles = getResources().getStringArray(R.array.items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        // Execute the network call for the list off the main thread
        // URI and headers currently hard coded in the NetworkManager class
        new GetListFromURITask().execute();
		
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
	
	/* Code for location. Currently not working. To implement, requires pemissions as well
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
	
	
	/**
	 * 
	 * Helper Classes
	 *
	 */
	
	// Item click listener for the navigation drawer
	// Will change the title to the selected item and then close the drawer
	public class DrawerItemClickListener implements ListView.OnItemClickListener {
		

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
			
		}
		
		// Highlight the selected item, update the title, and close the drawer
		private void selectItem(int position) {
		    mDrawerList.setItemChecked(position, true);
		    setTitle(mTitles[position]);
		    mDrawerLayout.closeDrawer(mDrawerList);
		}

		public void setTitle(CharSequence title) {
		    mTitle = title;
		    getSupportActionBar().setTitle(mTitle);
		}
	}
	

	// Helper AsyncTask Class
	// Takes no parameters in the constructor, nor the execute
	// Populates the response string and the jsonResponse JSON object
	private class GetListFromURITask extends AsyncTask<Void, Void, Integer> {
		// Declare variables needed for this AsyncTask
		HttpClient httpClient;
	    StringBuffer stringBuffer;
	    BufferedReader bufferedReader;
		InputStream inputStream;
	    HttpGet httpGet;
	    
		// Initial set up & initialize variables
		protected void onPreExecute(){
			stringBuffer = new StringBuffer("");
	        bufferedReader = null;
	        httpClient = NetworkManager.getAndroidHttpClient();
	        httpGet =  NetworkManager.createGetRequest(); 
		}
		
	    protected Integer doInBackground(Void... voids) { 
	    	int numTries = 0;
		    while (numTries<2){
			    numTries++;
			    try {
			    	// Display URI to console to make sure we're sending to the right place
			    	URI uri=httpGet.getURI();
					System.out.println("Host: "+uri.getHost()
							+" Path:"+uri.getPath()
							+" Query:"+uri.getQuery());
					
					// Prepare response and execute
					HttpResponse httpResponse;
					response="";
		            httpResponse = httpClient.execute(httpGet);
		            if (httpResponse==null){
		            	return 0;
		            }
		            
		            // Recieve body of the response with an input stream
					inputStream = httpResponse.getEntity().getContent();
			        System.out.println("Get Response");
			        bufferedReader = new BufferedReader(
			        		new InputStreamReader(inputStream));
			        System.out.println("Read from the string buffer");
			        String readLine = bufferedReader.readLine();
			        
			        while (readLine != null) {
			            stringBuffer.append(readLine);
			            stringBuffer.append("\n");
			            readLine = bufferedReader.readLine();
			        }
			        
			        // Response in both string and JSON formats
			        response = stringBuffer.toString();
			        jsonResponse = new JSONObject(response);
			        
		            int statusCode = httpResponse.getStatusLine().getStatusCode();
		
			        httpResponse.getEntity().consumeContent();

			    	return statusCode;
				} catch (IOException e) {
			    	e.printStackTrace();
				} catch (Exception e){
					System.out.println("Non IOException");
					e.printStackTrace();
				} finally {
		            if (bufferedReader != null) {
		                try {
		                	System.out.println("closing bufferedReader");
		                    bufferedReader.close();
		                } catch (IOException e) {
		                	e.printStackTrace();
		                }
		            }
		        }
			}
	        return 0;
	    }
	    
	    // Do something with the response here, such as display it to the screen
	    protected void onPostExecute(Integer result) {
	        super.onPostExecute(result);
	        if (result!=0){
	        	// No error, do something with response or JSONresponse here
	        	System.out.println("Response:"+response);
	        }

	    }
	}
	

	
}
