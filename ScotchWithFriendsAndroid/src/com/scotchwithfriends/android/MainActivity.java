package com.scotchwithfriends.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.json.*;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	ListView list;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list = (ListView)findViewById(R.id.list);
    }
    
    public class Scotch {
    	public String name;
    	public String age;
    	public String distilery;
    	public String description;
    	public String imageUrl;
    	
    	public Scotch(String name, String age, String distillery, String description, String imageUrl) {
    		this.name = name;
    		this.age = age;
    		this.distilery = distillery;
    		this.description = description;
    		this.imageUrl = imageUrl;
    	}
    }
    
    public class LoadScotchesTask extends AsyncTask<String, Void, List<Scotch>>
    {

		@Override
		protected List<Scotch> doInBackground(String... params) {
			
			 AndroidHttpClient client = AndroidHttpClient.newInstance(getLocalClassName());
			 HttpResponse response = null;
			 
			 try {
				 response = client.execute(new HttpGet("http://localhost:9000/Scotches"));
				
				 InputStream contentStream = response.getEntity().getContent();
				 InputStreamReader  reader = new InputStreamReader(contentStream);
				 int read;
				 int offset = 0;
				 int length = 500;
				 char[] buffer = new char[(int) response.getEntity().getContentLength()];
				 do {
					 read = reader.read(buffer, offset, length);
					 offset += read;
				 }while(read != -1);
				 
				 String str = new String(buffer);
  				 try {
  					JSONTokener tokener = new JSONTokener(str);
					JSONArray array = (JSONArray)tokener.nextValue();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
				return new ArrayList<Scotch>();

			 } catch(IOException e) {
					e.printStackTrace();
					return null;
			 }
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(List<Scotch> result) {
			super.onPostExecute(result);
		}
    }
}