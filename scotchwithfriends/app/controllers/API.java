package controllers;

import play.*;
import play.db.jpa.GenericModel;
import play.mvc.*;

import java.util.*;

import models.*;

public class API extends Controller {

    public static void listScotches() {
    	List<GenericModel> result = Scotch.findAll();
    	renderJSON(result);
    }
    public static void listUsers() {
    	List<GenericModel> result = User.findAll();
    	renderJSON(result);
    }

}