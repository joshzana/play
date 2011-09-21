package com.scotchwithfriends.android;

import org.json.*;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Scotch implements Parcelable{
	
	public static final class ScotchColumns implements BaseColumns{
		
		private ScotchColumns() {}
		
		public static final String AUTHORITY = "com.scotchwithfriends.android";
		public static final Uri SCOTCH_URI = Uri.parse("content://" + AUTHORITY + "/" + ScotchColumns.SCOTCH);
		public static final Uri CONTENT_URI = SCOTCH_URI;
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.scotchwithfriends.scotch";
		public static final String CONTENT_SCOTCH_TYPE = "vnd.android.cursor.item/vnd.scotchwithfriends.scotch";

		public static final String SCOTCH = "scotch";
		
		// Column names
		public static final String NAME = "name";
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String DISTILLERY = "distillery";
		public static final String IMAGE_URL = "imageUrl";
		public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
	}
	
	
	public int id;
	public String name;
	public String age;
	public String distillery;
	public String description;
	public String imageUrl;

	public Scotch(JSONObject obj) throws JSONException {
		this(obj.getInt("id"),
				obj.getString("name"), 
				obj.getString("age"), 
				obj.getString("distillery"), 
				obj.getString("description"), 
				obj.getString("imageUrl"));
	}

	public Scotch(int id, String name, String age, String distillery, String description, String imageUrl) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.distillery = distillery;
		this.description = description;
		this.imageUrl = imageUrl;
	}
    
    private Scotch(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readString();
        distillery = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }
    
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(age);
		dest.writeString(distillery);
		dest.writeString(description);
		dest.writeString(imageUrl);
	}
	

    public static final Parcelable.Creator<Scotch> CREATOR
            = new Parcelable.Creator<Scotch>() {
        public Scotch createFromParcel(Parcel in) {
            return new Scotch(in);
        }

        public Scotch[] newArray(int size) {
            return new Scotch[size];
        }
    };
}