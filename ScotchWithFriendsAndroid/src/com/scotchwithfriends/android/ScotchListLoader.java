package com.scotchwithfriends.android;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

public class ScotchListLoader {
	private static final String TAG = "ScotchListLoader";

	public interface ScotchListLoaderCallback {
		public abstract void handleListData(ArrayList<Scotch> listData);
	}

	private ScotchListLoaderCallback callback;
	public ScotchListLoader(ScotchListLoaderCallback callback) {
		this.callback = callback;
	}

	public void loadScotches(String user) {
		LoadScotchesTask task = new LoadScotchesTask();
		task.execute(user);
	}

	class LoadScotchesTask extends AsyncTask<String, Void, ArrayList<Scotch>>
	{
		@Override
		protected ArrayList<Scotch> doInBackground(String... params) {
			Log.i(TAG, "Starting service fetch");
			ArrayList<Scotch> result =  ServiceHelper.fetchScotchList();
			Log.i(TAG, "Service fetch complete");
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<Scotch> result) {
			super.onPostExecute(result);
			callback.handleListData(result);
		}
	}
}