package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class User extends Model {
 
    public String email;
    public String fullname;
    public String facebookId;
    public String facebookToken;
    public boolean isAdmin;
    
    @ManyToMany
    public List<Scotch> scotches = new ArrayList<Scotch>();
    
    public User(String email, String fullname, String facebookId, String facebookToken) {
        this.email = email;
        this.fullname = fullname;
        this.facebookId = facebookId;
        this.facebookToken = facebookToken;
    }
}