package com.scotchwithfriends.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.json.*;

import android.net.http.AndroidHttpClient;

public class ServiceHelper {
	
	private static final String BASE_URL = "http://10.82.172.91:9000/";
	private static final String SCOTCHES_URL = BASE_URL + "Scotches";
	private static final String USER_AGENT = "Scotch With Friends Android";

	public static List<Scotch> fetchScotchList() {
		ArrayList<Scotch> result = new ArrayList<Scotch>();
		AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT);
		HttpResponse response = null;

		try {
			response = client.execute(new HttpGet(SCOTCHES_URL));

			HttpEntity entity = response.getEntity();
			String contentEncoding = entity.getContentEncoding() == null ? "UTF-8" : entity.getContentEncoding().getValue();
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), contentEncoding));
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
		} catch(IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
