package com.scotchwithfriends.android;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.net.Uri;
import android.text.TextUtils;

public class ScotchContentProvider extends ContentProvider {

	public static final String AUTHORITY = "com.scotchwithfriends.android";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Scotch.ScotchColumns.SCOTCH);

	public static final String DATABASE_NAME = "scotch.db";
	public static final int DATABASE_VERSION = 1;
	public static final String SCOTCH_TABLE_NAME="scotch";
	private DatabaseHelper databaseHelper;

	// UriMatcher and associated constants
	private static UriMatcher uriMatcher;
	private static final int SCOTCH = 1;
	private static final int SCOTCH_ID = 2;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, Scotch.ScotchColumns.SCOTCH, SCOTCH);
		uriMatcher.addURI(AUTHORITY, Scotch.ScotchColumns.SCOTCH + "/#", SCOTCH_ID);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {

			// calls the super constructor, requesting the default cursor factory.
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String qs = "CREATE TABLE" + SCOTCH_TABLE_NAME + "("
					+ Scotch.ScotchColumns._ID + " INTEGER PRIMARY KEY, "
					+ Scotch.ScotchColumns.NAME + " TEXT, "
					+ Scotch.ScotchColumns.DESCRIPTION + " TEXT, "
					+ Scotch.ScotchColumns.DISTILLERY + " TEXT, "
					+ Scotch.ScotchColumns.IMAGE_URL + " TEXT);";
			db.execSQL(qs);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}


	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)) {
		case SCOTCH:
			return Scotch.ScotchColumns.CONTENT_TYPE;
		case SCOTCH_ID:
			return Scotch.ScotchColumns.CONTENT_SCOTCH_TYPE;
		default:
			throw new IllegalArgumentException("Unknown type");
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		if(uriMatcher.match(uri) != SCOTCH) {
			throw new IllegalArgumentException("unknown uri");
		}
		
		ContentValues realValues;
		if(values != null) {
			realValues= new ContentValues(values);
		} else {
			realValues = new ContentValues();
		}
		
		// TODO: Verify values are valid
		long rowId = databaseHelper.getWritableDatabase().insert(SCOTCH_TABLE_NAME, Scotch.ScotchColumns.NAME, realValues);
		if(rowId > 0) {
			Uri createdUri = ContentUris.withAppendedId(CONTENT_URI,rowId);
			getContext().getContentResolver().notifyChange(createdUri, null);
			return createdUri;
		}

		// TODO: Exception?
		
		return null;
	}

	@Override
	public boolean onCreate() {
		databaseHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		String orderBy;
		if(TextUtils.isEmpty(sortOrder)) {
			orderBy = Scotch.ScotchColumns.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		Cursor c;


		switch(uriMatcher.match(uri)) {
		case SCOTCH:
			c = databaseHelper.getReadableDatabase().query(SCOTCH_TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
			break;
		case SCOTCH_ID:
			long scotchId = ContentUris.parseId(uri);
			String idSelection = Scotch.ScotchColumns._ID + " = " + scotchId + 
					(!TextUtils.isEmpty(selection) ? "AND ( " + selection  + " ) ": "");
			c = databaseHelper.getReadableDatabase().query(SCOTCH_TABLE_NAME, projection, idSelection, selectionArgs, null, null, orderBy);
		default:
			throw new IllegalArgumentException("unsupported uri");
		}
		c.setNotificationUri(getContext().getContentResolver(), Scotch.ScotchColumns.CONTENT_URI);

		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
