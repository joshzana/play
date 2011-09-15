package com.scotchwithfriends.android;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.json.*;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
        
        LoadScotchesTask task = new LoadScotchesTask();
        task.execute("Josh");
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
				 response = client.execute(new HttpGet("http://192.168.1.139:9000/Scotches"));
				
				 HttpEntity entity = response.getEntity();
				 String contentEncoding = entity.getContentEncoding() == null ? "UTF-8" : entity.getContentEncoding().getValue();
				 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), contentEncoding));
			     StringBuilder builder = new StringBuilder();
			     for (String line = null; (line = reader.readLine()) != null;) {
			    	    builder.append(line).append("\n");
			     }
			     
				JSONTokener tokener = new JSONTokener(builder.toString());
				JSONArray array = (JSONArray)tokener.nextValue();
				Log.i("Main", array.toString());
			
				return new ArrayList<Scotch>();

			 } catch(IOException e) {
					e.printStackTrace();
					return null;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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