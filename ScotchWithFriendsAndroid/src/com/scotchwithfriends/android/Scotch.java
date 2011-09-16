package com.scotchwithfriends.android;

import org.json.*;

import android.os.Parcel;
import android.os.Parcelable;

public class Scotch implements Parcelable{
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