package com.scotchwithfriends.android;

import org.json.*;

public class Scotch {
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
}