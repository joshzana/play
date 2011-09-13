package models;


import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;

@Entity
public class Scotch extends Model {
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
