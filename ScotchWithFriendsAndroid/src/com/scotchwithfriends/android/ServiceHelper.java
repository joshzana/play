package com.scotchwithfriends.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.json.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class ServiceHelper {

	private static final String BASE_URL = "http://192.168.1.139:9000/";
	private static final String SCOTCHES_URL = BASE_URL + "Scotches";
	private static final String USER_AGENT = "Scotch With Friends Android";
	private static final String TAG = "ServiceHelper";

	public static ArrayList<Scotch> fetchScotchList() {
		final ArrayList<Scotch> result = new ArrayList<Scotch>();
		final AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT);
		final HttpGet getRequest = new HttpGet(SCOTCHES_URL);

		try {
			final HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != HttpStatus.SC_OK) { 
	            Log.w(TAG, "Error " + statusCode + " while retrieving data from " + SCOTCHES_URL); 
	            return null;
	        }
			
			final HttpEntity entity = response.getEntity();
			
			if(null != entity) {
				InputStream stream = null;
				try {
					stream = entity.getContent();
					String contentEncoding = entity.getContentEncoding() == null ? "UTF-8" : entity.getContentEncoding().getValue();
					BufferedReader reader = new BufferedReader(new InputStreamReader(stream, contentEncoding));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;) {
						builder.append(line).append("\n");
					}

					JSONTokener tokener = new JSONTokener(builder.toString());
					JSONArray array = (JSONArray)tokener.nextValue();
					for(int i=0; i< array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						result.add(new Scotch(obj));
					}
					
				} finally {
					if(null != stream) {
						stream.close();
					}
					
					entity.consumeContent();
				}
			}
		} catch(IOException e) {
			getRequest.abort();
			e.printStackTrace();
		} catch (JSONException e) {
			getRequest.abort();
			e.printStackTrace();
		} finally {
			if(null != client) {
				client.close();
			}
		}

		return result;
	}
	
	public static Bitmap downloadImage(String imageUrl) {
		Bitmap result = null;
		final AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT);
		final HttpGet getRequest = new HttpGet(imageUrl);

		try {
			final HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != HttpStatus.SC_OK) { 
	            Log.w(TAG, "Error " + statusCode + " while retrieving image from " + imageUrl); 
	            return null;
	        }
	        
			final HttpEntity entity = response.getEntity();
			
			if(null != entity) {
				InputStream stream = null;
				try {
					stream = entity.getContent();
					result = BitmapFactory.decodeStream(stream);
				} finally {
					if(null != stream) {
						stream.close();
					}
					
					entity.consumeContent();
				}
			}
		} catch(IOException e) {
			getRequest.abort();

			e.printStackTrace();
		} finally {
			if(null != client) {
				client.close();
			}
		}

		return result;
	}
}
