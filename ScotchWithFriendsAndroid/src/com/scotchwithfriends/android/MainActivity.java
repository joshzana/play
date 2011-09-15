package com.scotchwithfriends.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

	public class LoadScotchesTask extends AsyncTask<String, Void, List<Scotch>>
	{
		@Override
		protected List<Scotch> doInBackground(String... params) {
			return ServiceHelper.fetchScotchList();
		}


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<Scotch> result) {
			super.onPostExecute(result);
			
			List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
			
			for(Scotch item : result) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", item.name);
				map.put("description", item.description);
				maps.add(map);
			}
			
			String[] from = {"name", "description"};
			int[] to = {R.id.textView1, R.id.textView2 };
			
			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), maps, R.layout.listitem, from, to);
			list.setAdapter(adapter);
		}
	}
}