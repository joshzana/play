package com.scotchwithfriends.android;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private static final String TAG = "MAIN";
	private static final String SCOTCH_LIST_DATA_KEY = "Scotches";
	ArrayList<Scotch> scotchListData;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.i(TAG, "onCreate " + savedInstanceState);

		if(null != savedInstanceState) {
			scotchListData = savedInstanceState.getParcelableArrayList(SCOTCH_LIST_DATA_KEY);
			Log.i(TAG, "got array from bundle " + scotchListData);
		}

		if(scotchListData != null) {
			setListAdapter(new ScotchListAdapter(scotchListData));
		}
		else {
			ScotchListLoader loader = new ScotchListLoader(new ScotchListLoader.ScotchListLoaderCallback() {
				public void handleListData(ArrayList<Scotch> listData) {
					scotchListData = listData;
					setListAdapter(new ScotchListAdapter(scotchListData));
				}
			});

			loader.loadScotches("test");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.i(TAG, "Saving InstanceState " + scotchListData);
		outState.putParcelableArrayList(SCOTCH_LIST_DATA_KEY, scotchListData);
		super.onSaveInstanceState(outState);
	}

	class ScotchListAdapter extends BaseAdapter {

		private final ImageDownloader imageDownloader = new ImageDownloader();
		private final ArrayList<Scotch> listData = new ArrayList<Scotch>();

		public ScotchListAdapter(ArrayList<Scotch> scotchListData) {
			// Copy. TODO: Not sure is needed
			listData.addAll(scotchListData);
		}

		public int getCount() {
			return listData.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listData.get(position);
		}

		public long getItemId(int position) {
			return listData.get(position).hashCode();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Scotch item = listData.get(position);

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.listitem, parent, false);
			}

			TextView textView = (TextView)convertView.findViewById(R.id.textView1);
			textView.setText(item.name);

			ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView1);

			imageDownloader.download(item.imageUrl, imageView);

			return convertView;
		}
	}
}