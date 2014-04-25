package com.kayzee.axiomzentest;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;

import android.net.http.AndroidHttpClient;

public class NetworkManager {

	// Authentication Keys
	public static String x_api_key= "DummyXAPIKey";
	public static String authToken = "DummyAuthToken";
	
	// Get Request URI
	public static String persommURI = "https://persomm-api.herokuapp.com/api/top10?latitude=37.7651&longitude=122.4197";
	
	// Keep one client to avoid having to reopen a client for every request
	private static HttpClient httpClient;
	
	// Create httpclient if it has not been created yet, and then return it
	public static HttpClient getAndroidHttpClient() {
		if (httpClient == null){
			httpClient = AndroidHttpClient.newInstance("KayZee");
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 30000);
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), 45000);
		}
		return httpClient;
	}
	
	// Create a httpGet request with appropriate headers
	public static HttpGet createGetRequest(){
		HttpGet httpGet = new HttpGet();
		httpGet.setHeader("x-api-key", x_api_key);
		httpGet.setHeader("x-authentication-token", authToken);
		
		try {
			httpGet.setURI(new URI(persommURI));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return httpGet;
	}
}
