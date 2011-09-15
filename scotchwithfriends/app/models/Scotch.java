package models;


import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;

@Entity
public class Scotch extends Model {
	public String name;
	public String age;
	public String distillery;
	public String description;
	public String imageUrl;
	
	public Scotch(String name, String age, String distillery, String description, String imageUrl) {
		this.name = name;
		this.age = age;
		this.distillery = distillery;
		this.description = description;
		this.imageUrl = imageUrl;
	}
}
