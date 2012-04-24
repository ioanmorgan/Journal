package controllers;
import java.util.List;

import play.mvc.Before;
import models.*;
 
public class Security extends Secure.Security {
    
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            Users user = Users.find("email", Security.connected()).first();
            renderArgs.put("user", user.email);
        }
    }
	
	static Users getConnectedUser() {
        if(Security.isConnected()) {
            Users user = Users.find("email", Security.connected()).first();
            return user;
        }
		return null;
    }
	
    static boolean authenticate(String username, String password) {
        Users user = Users.find("byEmail", username).first();
        return user != null && user.password.equals(password);
    }   
    
    //returns true if user is an auditor
    public static boolean isAuditor() {
    	return isUserOfRole("auditor");
    }
    
    //returns true if user is a reviewer
    public static boolean isReviewer() {
    	return isUserOfRole("reviewer");
    }
    
    //returns true if user is an editor
    public static boolean isEditor() {
    	return isUserOfRole("editor");
    }
    
    //Returns true if user has admin privalages
    public static boolean isAdmin() {
    	return isUserOfRole("admin");
    }
    
    //Determines if a user is of the inputed role
    private static boolean isUserOfRole(String role){
    	List<Roles> roles = getRoles();
        boolean isOfRole = false;
        for(int x = 0; roles.size() > x; x++){
        	if(roles.get(x).role.contentEquals(role)){
        		isOfRole = true;
        	}
        }
        return isOfRole;
    }
    
    private static List<Roles> getRoles(){
        Users user = Users.find("email", Security.connected()).first();
        User_Roles userRole = User_Roles.find("user_ID", user).first();
        List<Roles> roles = Roles.find("role_ID", userRole.role_ID.role_ID).fetch();
		return roles;
    }
    
}