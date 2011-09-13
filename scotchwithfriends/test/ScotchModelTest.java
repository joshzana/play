import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class ScotchModelTest extends UnitTest {
    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
    
    @Test
    public void testCreate() {
    	new Scotch("Balvenie Doublewood", "12", "Balvenie", "Very smooth", "http://www.thedrinkshop.com/images/products/main/2708/2708.jpg").save();
    	
    	Scotch found = Scotch.find("byName", "Balvenie Doublewood").first();
    	
    	assertNotNull(found);
    	assertEquals("Balvenie", found.distilery);
    }
    
    @Test
    public void testManyToMany() {
    	
    	Scotch s1 = new Scotch("Balvenie Doublewood", "12", "Balvenie", "Very smooth", "http://www.thedrinkshop.com/images/products/main/2708/2708.jpg").save();
    	Scotch s2 = new Scotch("Glenlivet", "12", "Glenlivet", "Not so bad", "http://www.thedrinkshop.com/images/products/main/306/306.jpg").save();
    	
    	User testUser = new User("foo@bar.com", "foo bar", "12345", "XXXYYY");
    	testUser.scotches.add(s1);
    	testUser.scotches.add(s2);
    	testUser.save();
    	
    	User found = User.find("byEmail", "foo@bar.com").first();
    	assertNotNull(found);
    	assertEquals(2, found.scotches.size());
    }
}
